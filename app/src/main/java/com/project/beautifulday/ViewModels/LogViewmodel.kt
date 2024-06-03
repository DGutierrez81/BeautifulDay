package com.project.beautifulday.ViewModels

import android.util.Log
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.project.beautifulday.Firebase.AuthService
import com.project.beautifulday.Firebase.FirestoreService
import com.project.beautifulday.R
import com.project.beautifulday.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LogViewmodel@Inject constructor(private val authService: AuthService, private val firestore: FirestoreService): ViewModel() {

    val _isLoading = mutableStateOf(false)
    val isLoading: MutableState<Boolean> = _isLoading

    private val _showAlert = mutableStateOf(false)
    val showAlert: MutableState<Boolean> = _showAlert

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var userName by mutableStateOf("")
        private set

    var login by mutableStateOf(false)
        private set

    var passwordVisibility by mutableStateOf(false)
        private set







    fun login(user: String, password: String, onSuccess: () -> Unit){
        viewModelScope.launch() {
            try{
                _isLoading.value = true
                val result = withContext(Dispatchers.IO){
                    authService.login(user, password)
                }

                if(result != null){
                    login = true
                    onSuccess()
                }else{
                    showAlert(dialog = false)
                }
            }catch(e: Exception){
                showAlert(dialog = false)
                Log.d("ERROR EN JETPACK", "ERROR: ${e.localizedMessage}")
            }finally {
                _isLoading.value = false
            }

        }
    }





    fun creauteUser(email: String, password: String,  onSuccess: () -> Unit){
        viewModelScope.launch {
            _isLoading.value = true

            try{
                val result: FirebaseUser? = withContext(Dispatchers.IO){
                    authService.register(email, password)
                }
                if(result != null){
                    saveUser(userName)
                    login = true
                    onSuccess()
                }else{
                    showAlert(dialog = false)
                }
            }catch(e: Exception){
                showAlert(dialog = false)
                Log.d("ERROR EN JETPACK", "ERROR: ${e.localizedMessage}")
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun saveUser(username: String){
        val id = authService.id()
        val email = authService.email()
        viewModelScope.launch {
            val user = User(
                userId = id.toString(),
                email = email.toString(),
                userName = username
            )

            firestore.createUser(user)

            /*
            val result: FirebaseUser? =  withContext(Dispatchers.IO){
                firestore.createUser(user)
            }

             */
        }
    }


    fun isUserLogged(): Boolean {
        return authService.isUserLogged()
    }




    fun logOut(onSuccess: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            authService.logOut()
        }
        onSuccess()
    }






    /*
    @Composable
    fun Alert(onDismiss: () -> Unit, titulo: String, contenido: String){
        AlertDialog(onDismissRequest = { onDismiss() },
            title = { Text(text = titulo, modifier = Modifier.fillMaxWidth() ,textAlign = TextAlign.Center,fontFamily = jotiOne, fontSize = 24.sp, color = colorResource(
                id = R.color.silver
            )
            ) },
            text = { Text(text = contenido, modifier = Modifier.fillMaxWidth() ,textAlign = TextAlign.Center, fontFamily = jotiOne, fontSize = 24.sp, color = colorResource(
                id = R.color.silver
            )
            ) },
            confirmButton = {},
            icon = { AsyncImage(model = R.drawable.logo, contentDescription = null) },
            dismissButton = { TextButton(onClick = { onDismiss() }, colors = ButtonDefaults.buttonColors(contentColor = colorResource(
                id = R.color.silver
            )
            )) {
                Text(text = "Salir")
            }
            },
            shape = RoundedCornerShape(50.dp),
            containerColor = colorResource(id = R.color.paynesGray),
            modifier = Modifier.background(Color.Transparent))
    }
   
     */


    fun transformation(){
        if(!passwordVisibility)PasswordVisualTransformation() else VisualTransformation.None
    }

    @Composable
    fun changeIcon(){
        if(passwordVisibility){
            Icon(painter = painterResource(id = R.drawable.visibility), contentDescription = "Visisibility on", tint = colorResource(id = R.color.paynesGray))
        }else{
            Icon(painter = painterResource(id = R.drawable.visibility_off), contentDescription = "Visisibility of", tint = colorResource(id = R.color.paynesGray))
        }
    }
    fun showAlert(dialog: Boolean){
        _showAlert.value = !dialog
    }

    fun changeEmail(result: String){
        email = result
    }

    fun changePassword(result: String){
        password = result
    }

    fun changeUsername(result: String){
        userName = result
    }

    fun changeLogin(result: Boolean){
        login = result
    }

    fun changePasswordVisibility(result: Boolean){
        passwordVisibility = result
    }

    fun clean(){
        login = false
        userName = ""
        email = ""
        password = ""
    }

}