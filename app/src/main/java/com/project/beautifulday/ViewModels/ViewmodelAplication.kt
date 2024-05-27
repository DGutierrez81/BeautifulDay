package com.project.beautifulday.ViewModels

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import com.project.beautifulday.Firebase.AuthService
import com.project.beautifulday.Firebase.FirestoreService
import com.project.beautifulday.Firebase.StorageService
import com.project.beautifulday.ListUiState
import com.project.beautifulday.Meal.ui.States.Traduction

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects
import javax.inject.Inject


@HiltViewModel
class ViewmodelAplication@Inject constructor(private val storageService: StorageService, private val authService: AuthService, private val firestore: FirestoreService) : ViewModel() {
    private val _state = mutableStateOf(Traduction())
    val state: MutableState<Traduction> = _state

    /*
    var actionTranslate by mutableStateOf(true)
        private set

     */

    private val _actionTranslate = MutableLiveData<Boolean>()
    val actionTranslate: LiveData<Boolean> = _actionTranslate

    /*
    var slide by mutableStateOf(false)
        private set

     */

    var focusRequest by mutableStateOf(FocusRequester())

    var resultUri by mutableStateOf<Uri?>(null)
        private set


    var namePhoto by mutableStateOf("")
        private set

    var imageName by mutableStateOf("")
        private set

    var uriFoto by mutableStateOf("")
        private set

    var uriVideo by mutableStateOf("")
        private set

    var metada by mutableStateOf("")
        private set

    var descripcion by mutableStateOf("")
        private set

    private var _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _slide = MutableLiveData<Boolean>()
    val slide: LiveData<Boolean> = _slide

    var showDialog by mutableStateOf(false)
        private set

    var showDialog2 by mutableStateOf(false)
        private set

    var name by mutableStateOf("")

    var id by mutableStateOf("")
        private set

    private val validIds = (0..99999).map { it.toString() }.toSet()

    var ingrediente by mutableStateOf("")
        private set

    var showAlert by mutableStateOf(false)
        private set

    var screen by mutableStateOf("")
        private set

    var showCreateAlert by mutableStateOf(false)
        private set

    private var _uiState = MutableStateFlow(ListUiState(false, emptyList()))
    val uiState: StateFlow<ListUiState> = _uiState



    fun onTextToBeTranslatedChange(text: String){
        _state.value = state.value.copy(textToBeTranslate = text)
    }
    fun onTranslateButtonClick(text: String, context: Context){
        val options = TranslatorOptions
            .Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.SPANISH)
            .build()

        val languageTranslator = Translation
            .getClient(options)

        languageTranslator.translate(text)
            .addOnSuccessListener { translatedText ->
                _state.value = state.value.copy(
                    translatedText = translatedText
                )
            }
            .addOnFailureListener {
                //Toast.makeText(context,"traducción en curso", Toast.LENGTH_SHORT).show()
                downloadModelIfNotAviable(languageTranslator, context)
            }
    }

    private fun downloadModelIfNotAviable(languageTranslator: Translator, conext: Context){
        _state.value = state.value.copy(
            isButtonEnabled = false
        )

        val conditions = DownloadConditions
            .Builder()
            .requireWifi()
            .build()

        languageTranslator
            .downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                _state.value = state.value.copy(
                    isButtonEnabled = true
                )
            }
            .addOnFailureListener {
                Toast.makeText(conext,"No se ha podidio traducir",Toast.LENGTH_SHORT).show()
            }
    }

    fun uploadAndGetImage(uri: Uri, onSuccessDownload: (Uri) -> (Unit)) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result: Uri =
                    withContext(Dispatchers.IO) { storageService.uploadAndDownloadImage(uri) }
                onSuccessDownload(result)
            } catch (e: Exception) {
                Log.i("error", e.message.orEmpty())
            }
            _isLoading.value = false
        }
    }


    @Composable
    fun intentCameraLaucher(
        result: Uri?,
        focusManager: FocusManager, ): ManagedActivityResultLauncher<Uri, Boolean> {
        return rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it && result?.path?.isNotEmpty() == true) {
                //viewmodel.uploadBasicImage(uri!!)
                uploadAndGetImage(result) { newUri ->
                    namePhoto = ""
                    focusManager.clearFocus()
                    resultUri = newUri
                    uriFoto = newUri.toString()
                    imageName = newUri.lastPathSegment.toString()
                    getMetadata(imageName)
                }
            }
        }
    }

    @Composable
    fun intentCameraLaucherVideo(
        result: Uri?,
        focusManager: FocusManager, ): ManagedActivityResultLauncher<Uri, Boolean> {
        return rememberLauncherForActivityResult(ActivityResultContracts.CaptureVideo()) {
            if (it && result?.path?.isNotEmpty() == true) {
                //viewmodel.uploadBasicImage(uri!!)
                uploadAndGetImage(result) { newUri ->
                    namePhoto = ""
                    focusManager.clearFocus()
                    resultUri = newUri
                    uriVideo = newUri.toString()
                    imageName = newUri.lastPathSegment.toString()
                    getMetadata(imageName)
                }
            }
        }
    }






    @Composable
    fun intentGalleryLaucher(): ManagedActivityResultLauncher<String, Uri?> {
        return rememberLauncherForActivityResult(ActivityResultContracts.GetContent()){
            if(it?.path?.isNotEmpty() == true){
                //viewmodel.uploadBasicImage(it)
                uploadAndGetImage(it){ newUri ->
                    resultUri = newUri
                    imageName = newUri.lastPathSegment.toString()
                    getMetadata(imageName)
                }
            }
        }
    }


    fun choose(metadata: String){
        when (metadata) {
            "image/jpeg" -> {

                changeUriFoto(resultUri.toString())
            }
            "video/mp4" -> {
                changeUriVideo(resultUri.toString())
            }
        }
    }

    fun getMetadata(reference: String){
        viewModelScope.launch {
            metada = storageService.readMetadata(reference)?: ""
        }
    }

    fun getAllImages(){
        viewModelScope.launch{
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result: List<String> = withContext(Dispatchers.IO){
                storageService.getAllImages().map{it.toString()}
            }
            _uiState.value = _uiState.value.copy(isLoading = false, images = result)
        }
    }


    fun deleteImage(imageName: String, context: ComponentActivity) {
        viewModelScope.launch {
            val success = storageService.removeImage(imageName)
            if (success) {
                // Si la eliminación es exitosa, muestra un mensaje de confirmación
                showMessage(context, "Imagen eliminada satisfactoriamente")
            } else {
                // Si la eliminación falla, muestra un mensaje de error
                showMessage(context, "No se pudo eliminar la imagen")
            }
        }
    }

    fun showMessage(context: Context, message: String) {
        AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton("Aceptar", null) // Aquí puedes agregar un OnClickListener si lo necesitas
            .show()
    }


    /*
    var user by mutableStateOf(User())
        private set
    fun someFunction() {
        val email = authService.email()
        viewModelScope.launch {
            try {
                user = firestore.fetchUser(email)
            } catch (e: Exception) {
                //println("Error: ${e.message}")
            }
        }
    }

     */


    fun generateUri(context: ComponentActivity, nombre: String, sufijo: String): Uri {
        return FileProvider.getUriForFile(
            Objects.requireNonNull(context),
            "com.project.beautifulday.provider",
            createFile(context, nombre, sufijo)
        )
    }


    fun createFile(context: ComponentActivity, nombre: String, sufijo: String): File {
        val name: String = nombre.ifEmpty { SimpleDateFormat("yyyyMMdd_hhmmss").format(Date())+"image"}
        return File.createTempFile(name,sufijo, context.externalCacheDir)
    }

    fun deleteRegister(documento: String, colec: String,onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) { Tasks.await(firestore.deleteRegister(colec, documento)) }
                if (result) {
                    onSuccess()
                    Log.d("BIEN", "ESTA TODO BIEN")
                } else {
                    Log.d("ERROR", "Hubo un error al guardar el registro")
                }
            } catch (e: Exception) {
                Log.d("Error al borrar cocktail", "Error ${e.localizedMessage}")
            }
        }
    }


    /*
    fun updateStars(iDoc: String, onSuccess:() -> Unit){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val plusVotes = hashMapOf(
                    "puntuacion" to drink.puntuacion,
                    "votes" to drink.votes
                )
                firestore.collection("Cocktails").document(iDoc)
                    .update(plusVotes as Map<String, Any>)
                    .addOnSuccessListener {
                        onSuccess()
                        Log.d("Actualizacion OK", "Se ha actualizado correctamente")
                    }
                    .addOnFailureListener {
                        Log.d("Error al actualizar", "No se ha podido realizar la actualización.")
                    }
                cleanVotes()
            }catch (e: Exception){
                Log.d("Error subir", "Error subir puntos")
            }
        }
    }


     */

    fun changeActionTranslate(value: Boolean){
        _actionTranslate.value = value
    }

    fun changeSlide(value: Boolean){
        _slide.value = !value
    }

    fun chageShowDialog(result: Boolean){
        showDialog = !result
    }

    fun changeAlert(result: Boolean){
        showAlert = result
    }

    fun changeCreateAlerte(result: Boolean){
        showCreateAlert = result
    }

    fun changeIngrediente(ingredient: String){
        ingrediente = ingredient
    }

    fun changeName(result: String){
        name = result
    }

    fun changeShowDialog(result: Boolean){
        showDialog = result
    }

    fun changeShowDialog2(result: Boolean){
        showDialog2 = result
    }

    fun changeId(result: String, context: ComponentActivity){

        if(result in validIds || result == ""){
            id = result
        }else Toast.makeText(context, "Solo números enteros", Toast.LENGTH_SHORT).show()

    }

    fun changeDescripcion(desc: String){
        descripcion = desc
    }
    fun changeUriFoto(uriFo: String){
        uriFoto = uriFo
    }

    fun changeNamePhoto(name: String){
        namePhoto = name
    }


    fun changeUriVideo(uriVi: String){
        uriVideo = uriVi
    }

    fun changeScreen(result: String){
        screen = result
    }


    fun clean(){
        _slide.value = false
        _actionTranslate.value = true
        showDialog = false
        resultUri = null
        id = ""
        descripcion = ""
        uriFoto = ""
        uriVideo = ""
        ingrediente = ""
        name = ""
    }
}

