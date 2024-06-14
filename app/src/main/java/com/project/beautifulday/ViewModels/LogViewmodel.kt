package com.project.beautifulday.ViewModels

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
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
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseUser
import com.project.beautifulday.Firebase.AuthService
import com.project.beautifulday.Firebase.FirestoreService
import com.project.beautifulday.R
import com.project.beautifulday.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

/**
 * ViewModel para el manejo de inicio de sesión y registro de usuarios.
 *
 * @param authService Servicio de autenticación.
 * @param firestore Servicio de Firestore para interactuar con la base de datos.
 */
@HiltViewModel
class LogViewmodel@Inject constructor(private val authService: AuthService, private val firestore: FirestoreService): ViewModel() {

    // Estado del indicador de carga
    val _isLoading = mutableStateOf(false)
    val isLoading: MutableState<Boolean> = _isLoading

    // Estado para mostrar u ocultar la alerta
    private val _showAlert = mutableStateOf(false)
    val showAlert: MutableState<Boolean> = _showAlert

    // Estado para el email del usuario
    var email by mutableStateOf("")
        private set

    // Estado para la contraseña del usuario
    var password by mutableStateOf("")
        private set

    // Estado para el nombre de usuario
    var userName by mutableStateOf("")
        private set

    var idDoc by mutableStateOf("")
        private set

    var user by mutableStateOf(User())
        private set

    // Estado para indicar si el usuario ha iniciado sesión
    var login by mutableStateOf(false)
        private set

    // Estado para indicar la visibilidad de la contraseña
    var passwordVisibility by mutableStateOf(false)
        private set

    var updateUsers by mutableStateOf(false)
        private set

    /**
     * Función para iniciar sesión.
     *
     * @param user Nombre de usuario o email.
     * @param password Contraseña del usuario.
     * @param onSuccess Función de callback a ejecutar cuando el inicio de sesión es exitoso.
     */
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

    /**
     * Función para crear un nuevo usuario.
     *
     * @param email Email del nuevo usuario.
     * @param password Contraseña del nuevo usuario.
     * @param onSuccess Función de callback a ejecutar cuando la creación del usuario es exitosa.
     */
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

    /**
     * Función para guardar el usuario en la base de datos.
     *
     * @param username Nombre de usuario.
     */
    fun saveUser(username: String){
        val id = authService.id()
        val email = authService.email()
        viewModelScope.launch {
            user = User(
                userId = id.toString(),
                email = email.toString(),
                userName = username,
                password = password
            )

            firestore.createUser(user)
        }
    }


    /*
    fun updateUser(iDoc: String, context: ComponentActivity,onSuccess: () -> Unit) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                Tasks.await(firestore.updateUser("Users", iDoc, user, context))
            }
            if (result) {
                onSuccess()
            } else {
                Log.d("ERROR", "Hubo un error al guardar el registro")
            }
        }
    }

     */

    fun updateUser(iDoc: String, newPassword: String, context: ComponentActivity, onSuccess: () -> Unit) {

        viewModelScope.launch {
            _isLoading.value = true

            try {
                // Actualiza la contraseña del usuario autenticado
                authService.updatePassword(newPassword, context)

                // Actualiza los datos del usuario en Firestore
                val result = withContext(Dispatchers.IO) {
                    Tasks.await(firestore.updateUser("Users", iDoc, user, context))
                }

                if (result) {
                    onSuccess()
                } else {
                    Log.d("ERROR", "Hubo un error al guardar el registro")
                }
            } catch (e: Exception) {
                Log.d("ERROR EN JETPACK", "ERROR: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun fetchUser() {

        val email = authService.email()

        viewModelScope.launch {
            user = firestore.fetchUsers(email?: "")?: User()
            delay(3000)

        }
    }

    fun deleteUser(context: ComponentActivity){
        viewModelScope.launch {
            authService.deleteCurrentUser(context)
        }
    }

    fun changeUser(value: String, text: String) {
        when (text) {
            "userName" -> if(value != "") user = user.copy(userName = value)
            "email" -> if(value != "") user = user.copy(email = value)
            "password" -> if(value != "") user = user.copy(password = value)

        }
    }

    /**
     * Función para verificar si el usuario ha iniciado sesión.
     *
     * @return True si el usuario ha iniciado sesión, de lo contrario False.
     */
    fun isUserLogged(): Boolean {
        return authService.isUserLogged()
    }

    /**
     * Función para cerrar sesión.
     *
     * @param onSuccess Función de callback a ejecutar cuando el cierre de sesión es exitoso.
     */
    fun logOut(onSuccess: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            authService.logOut()
        }
        onSuccess()
    }

    /**
     * Composable para cambiar el icono de visibilidad de la contraseña.
     */
    @Composable
    fun changeIcon(){
        if(passwordVisibility){
            Icon(painter = painterResource(id = R.drawable.visibility), contentDescription = "Visisibility on", tint = colorResource(id = R.color.paynesGray))
        }else{
            Icon(painter = painterResource(id = R.drawable.visibility_off), contentDescription = "Visisibility of", tint = colorResource(id = R.color.paynesGray))
        }
    }

    /**
     * Función para mostrar u ocultar la alerta.
     *
     * @param dialog Booleano que indica si se debe mostrar la alerta.
     */
    fun showAlert(dialog: Boolean){
        _showAlert.value = !dialog
    }

    /**
     * Función para cambiar el email.
     *
     * @param result Nuevo email.
     */
    fun changeEmail(result: String){
        email = result
    }

    /**
     * Función para cambiar la contraseña.
     *
     * @param result Nueva contraseña.
     */
    fun changePassword(result: String){
        password = result
    }

    /**
     * Función para cambiar el nombre de usuario.
     *
     * @param result Nuevo nombre de usuario.
     */
    fun changeUsername(result: String){
        userName = result
    }

    /**
     * Función para cambiar el estado de inicio de sesión.
     *
     * @param result Nuevo estado de inicio de sesión.
     */
    fun changeLogin(result: Boolean){
        login = result
    }

    /**
     * Función para cambiar la visibilidad de la contraseña.
     *
     * @param result Nuevo estado de visibilidad de la contraseña.
     */
    fun changePasswordVisibility(result: Boolean){
        passwordVisibility = result
    }

    fun changeUpdateUser(result: Boolean){
        updateUsers = result
    }

    fun changeIdDoc(result: String){
        idDoc = result
    }

    /**
     * Función para limpiar los estados.
     */
    fun clean(){
        login = false
        userName = ""
        email = ""
        password = ""
    }
}