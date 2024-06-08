package com.project.beautifulday.ViewModels

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.location.Location
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import com.google.relay.compose.ColumnScopeInstanceImpl.weight
import com.project.beautifulday.Components.RatingBarImage
import com.project.beautifulday.Firebase.AuthService
import com.project.beautifulday.Firebase.FirestoreService
import com.project.beautifulday.Firebase.StorageService
import com.project.beautifulday.ListUiState
import com.project.beautifulday.Local
import com.project.beautifulday.Meal.ui.States.Traduction
import com.project.beautifulday.R
import com.project.beautifulday.User
import com.project.beautifulday.androidsmall1.jotiOne
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects
import javax.inject.Inject


/**
 * ViewModel para manejar la lógica de la aplicación.
 * Utiliza Hilt para la inyección de dependencias y administra los estados y operaciones relacionados con la traducción,
 * la carga de imágenes y videos, y otras funcionalidades de la aplicación.
 */
@HiltViewModel
class ViewmodelAplication @Inject constructor(
    private val storageService: StorageService,
    private val authService: AuthService,
    private val firestore: FirestoreService
) : ViewModel() {

    // Estado de traducción
    private val _state = mutableStateOf(Traduction())
    val state: MutableState<Traduction> = _state

    // Estado para manejar la acción de traducir
    private val _actionTranslate = MutableLiveData<Boolean>()
    val actionTranslate: LiveData<Boolean> = _actionTranslate

    // Otros estados y variables observables
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
    var name by mutableStateOf("")
        private set

    var pais by mutableStateOf("")
        private set
    var ciudad by mutableStateOf("")
        private set
    var id by mutableStateOf("")
        private set
    private val validIds = (0..99999).map { it.toString() }.toSet()
    var ingrediente by mutableStateOf("")
        private set
    var web by mutableStateOf("")
        private set
    var showAlert by mutableStateOf(false)
        private set
    var screen by mutableStateOf("")
        private set
    var showCreateAlert by mutableStateOf(false)
        private set
    private var _uiState = MutableStateFlow(ListUiState(false, emptyList()))
    val uiState: StateFlow<ListUiState> = _uiState
    var email by mutableStateOf("")
        private set
    var user by mutableStateOf(User())
        private set
    var dots by mutableStateOf("")
        private set
    var progress by mutableStateOf(false)
        private set

    var showVotes by mutableStateOf(false)
        private set

    var currentRating by mutableStateOf(0.0)
        private set

    var averageRating by mutableStateOf(0.0)
        private set

    var puntuacion by mutableStateOf(0.0)
        private set

    var votes by mutableStateOf(0)
        private set

    var listVotes by mutableStateOf(listOf<Double>())
        private set

    var local by mutableStateOf(Local())
        private set

    private val _progrees = MutableLiveData(true)
    val progrees: LiveData<Boolean> = _progrees

    private val _localData = MutableStateFlow<List<Local>>(emptyList())
    val localData: StateFlow<List<Local>> = _localData

    private val _location = MutableStateFlow<LatLng?>(null)
    val location: StateFlow<LatLng?> = _location

    private val _savedLocation = MutableStateFlow<LatLng?>(null)
    val savedLocation: StateFlow<LatLng?> get() = _savedLocation

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    private val _localizacion = MutableStateFlow<LatLng?>(null)
    val localizacion: StateFlow<LatLng?> get() = _localizacion

    fun changeLocalizacion(result: LatLng?){
        _localizacion.value = result
    }


    fun requestLocation(context: Context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        getLastLocation()
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        val cancellationTokenSource = CancellationTokenSource()
        fusedLocationClient.getCurrentLocation(
            com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        ).addOnSuccessListener { location: Location? ->
            location?.let {
                _location.value = LatLng(it.latitude, it.longitude)
            }
        }
    }

    fun saveLocation(location: LatLng) {
        _savedLocation.value = location
    }


    // Método para obtener el usuario actual del servicio de autenticación
    fun fetchUser() {
        val email = authService.email()
        viewModelScope.launch {
            user = firestore.fetchUser(email)
        }
    }

    // Método para actualizar el texto a traducir
    fun onTextToBeTranslatedChange(text: String) {
        _state.value = state.value.copy(textToBeTranslated = text)
    }

    // Método para manejar el botón de traducción
    fun onTranslateButtonClick(text: String, context: Context) {
        val options = TranslatorOptions
            .Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.SPANISH)
            .build()

        val languageTranslator = Translation.getClient(options)

        languageTranslator.translate(text)
            .addOnSuccessListener { translatedText ->
                _state.value = state.value.copy(translatedText = translatedText)
            }
            .addOnFailureListener {
                downloadModelIfNotAviable(languageTranslator, context)
            }
    }

    // Método para descargar el modelo si no está disponible
    private fun downloadModelIfNotAviable(languageTranslator: Translator, context: Context) {
        _state.value = state.value.copy(isButtonEnabled = false)
        val conditions = DownloadConditions.Builder().requireWifi().build()

        languageTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                _state.value = state.value.copy(isButtonEnabled = true)
            }
            .addOnFailureListener {
                Toast.makeText(context, "No se ha podidio traducir", Toast.LENGTH_SHORT).show()
            }
    }

    // Método para cargar y obtener la URI de una imagen
    fun uploadAndGetImage(uri: Uri, email: String, onSuccessDownload: (Uri) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result: Uri = withContext(Dispatchers.IO) { storageService.uploadAndDownloadImage(uri, email) }
                onSuccessDownload(result)
            } catch (e: Exception) {
                Log.i("error", e.message.orEmpty())
            }
            _isLoading.value = false
        }
    }

    // Launchers para la cámara y la galería
    @Composable
    fun intentCameraLaucher(result: Uri?, focusManager: FocusManager, email: String): ManagedActivityResultLauncher<Uri, Boolean> {
        return rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it && result?.path?.isNotEmpty() == true) {
                uploadAndGetImage(result, email) { newUri ->
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
    fun intentCameraLaucherVideo(result: Uri?, focusManager: FocusManager, email: String): ManagedActivityResultLauncher<Uri, Boolean> {
        return rememberLauncherForActivityResult(ActivityResultContracts.CaptureVideo()) {
            if (it && result?.path?.isNotEmpty() == true) {
                uploadAndGetImage(result, email) { newUri ->
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
        return rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            if (it?.path?.isNotEmpty() == true) {
                uploadAndGetImage(it, "") { newUri ->
                    resultUri = newUri
                    uriFoto = newUri.toString()
                    imageName = newUri.lastPathSegment.toString()
                    getMetadata(imageName)
                }
            }
        }
    }

    @Composable
    fun intentGalleryLaucherVideo(): ManagedActivityResultLauncher<String, Uri?> {
        return rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            if (it?.path?.isNotEmpty() == true) {
                uploadAndGetImage(it, "") { newUri ->
                    resultUri = newUri
                    uriVideo = newUri.toString()
                    imageName = newUri.lastPathSegment.toString()
                    getMetadata(imageName)
                }
            }
        }
    }

    // Método para elegir entre una imagen o un video basado en los metadatos
    fun choose(metadata: String) {
        when (metadata) {
            "image/jpeg" -> {
                changeUriFoto(resultUri.toString())
            }
            "video/mp4" -> {
                changeUriVideo(resultUri.toString())
            }
        }
    }

    // Método para obtener los metadatos de un archivo
    fun getMetadata(reference: String) {
        viewModelScope.launch {
            metada = storageService.readMetadata(reference) ?: ""
        }
    }

    // Método para obtener todas las imágenes
    fun getAllImages(dir: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result: List<String> = withContext(Dispatchers.IO) {
                storageService.getAllImages(dir).map { it.toString() }
            }
            _uiState.value = _uiState.value.copy(isLoading = false, images = result)
        }
    }

    // Método para eliminar una imagen
    fun deleteImage(imageName: String, context: ComponentActivity) {
        viewModelScope.launch {
            val success = storageService.removeImage(imageName)
            if (success) {
                showMessage(context, "Imagen eliminada satisfactoriamente")
            } else {
                showMessage(context, "No se pudo eliminar la imagen")
            }
        }
    }

    // Método para mostrar un mensaje en un AlertDialog
    fun showMessage(context: Context, message: String) {
        AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton("Aceptar", null)
            .show()
    }

    // Método para obtener puntos animados mientras se carga
    @Composable
    fun getAnimatedDots(isLoading: Boolean): String {
        LaunchedEffect(isLoading) {
            while (isLoading) {
                dots = when (dots) {
                    "" -> "."
                    "." -> ".."
                    ".." -> "..."
                    else -> ""
                }
                delay(600)
            }
        }
        return dots
    }

    // Método para generar una URI
    fun generateUri(context: ComponentActivity, nombre: String, sufijo: String): Uri {
        return FileProvider.getUriForFile(
            Objects.requireNonNull(context),
            "com.project.beautifulday.provider",
            createFile(context, nombre, sufijo)
        )
    }

    // Método para crear un archivo temporal
    fun createFile(context: ComponentActivity, nombre: String, sufijo: String): File {
        val name: String = nombre.ifEmpty { SimpleDateFormat("yyyyMMdd_hhmmss").format(Date()) + "image" }
        return File.createTempFile(name, sufijo, context.externalCacheDir)
    }


    // Método para eliminar un registro en Firestore
    fun deleteRegister(documento: String, colec: String, onSuccess: () -> Unit) {
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

    fun newLocal(
        nombre: String,
        fotoLocal: String,
        comentario: String,
        pais: String,
        ciudad: String,
        nameUser: String,
        web: String,
        ubicacion: LatLng?
    ){
        val email = authService.email()
        local = Local(nombreLocal = nombre, emailUser= email,fotoLocal = fotoLocal,  comentario = comentario, pais = pais, ciudad = ciudad, web = web, nameUser = nameUser, latitud = ubicacion!!.latitude, longitud = ubicacion.longitude)
    }


    fun saveNewLocal(colec: String, context: ComponentActivity, onOk:() -> Unit, onSuccess: () -> Unit) {
        _progrees.value = true
        val email = authService.email()
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                Tasks.await(
                    firestore.saveNewLocal(
                        colec,
                        "idLocal",
                        local.idLocal,
                        local,
                        email,
                        context
                    )
                )
            }
            if (result) {
                onOk()
                delay(3000)
                _progrees.value = false
                delay(3000)
                onSuccess()
            } else {
                // Manejo del caso cuando result es false
                _progrees.value = false
                Log.d(
                    "ERROR",
                    "Hubo un error al guardar el registro o el registro ya existe"
                )

            }
        }
    }

    fun fetchLocal(colec: String){
        _progrees.value = true

        viewModelScope.launch {
            _localData.value = firestore.fetchLocal(colec)
            delay(3000)
            _progrees.value = false
        }
    }


    fun getLocalById(document: String, colec: String){
        _progrees.value = true
        viewModelScope.launch {
            local = firestore.getLocalById(document, colec)?: Local()
            delay(3000)
            _progrees.value = false
        }
    }


    @Composable
    fun ShowLocal(localData: List<Local>, navController: NavController, colec: String){
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(localData) { index, item ->
                if (index % 2 == 0) {
                    val nextIndex = index + 1
                    val hasNextItem = nextIndex < localData.size
                    val nextItem = if (hasNextItem) localData[nextIndex] else null

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .height(250.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            GetImagesLocal(local = item, navController, colec)
                            GetImagesLocal(local = nextItem, navController, colec)
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun GetImagesLocal(local: Local?, navController: NavController, colec: String) {
        local?.fotoLocal?.let { url ->
            Box(
                modifier = Modifier
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    AsyncImage(
                        model = url,
                        contentDescription = "Local Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(120.dp)
                            .height(120.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .clickable {
                                navController.navigate("cardLocalM/${local.idDocument}?colec=$colec")
                            }
                    )
                    Text(
                        text = local.nombreLocal ?: "",
                        modifier = Modifier
                            .height(75.dp)
                            .width(120.dp)
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center,
                        fontFamily = jotiOne,
                        color = colorResource(id = R.color.paynesGray)
                    )
                    Spacer(modifier = Modifier.height(1.dp))
                    val average = calculateAverage(local.votes ?: 0, local.puntuacion ?: 0.0)

                    if (colec == "Locales $screen") {
                        Box(
                            modifier = Modifier.width(120.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            RatingBarImage(rating = average)
                        }
                    }
                }
            }
        }
    }

    // Métodos para cambiar los estados
    fun changeActionTranslate(value: Boolean) {
        _actionTranslate.value = value
    }

    fun calculateAverage(votes: Int, valueVote: Double): Double = valueVote/votes

    fun changeSlide(value: Boolean) {
        _slide.value = !value
    }

    fun changeShowVotes(result: Boolean){
        showVotes = result
    }

    fun changeIsLoading(result: Boolean){
        _isLoading.value = result
    }

    fun chageShowDialog(result: Boolean) {
        showDialog = !result
    }

    fun changeAlert(result: Boolean) {
        showAlert = result
    }

    fun changeCreateAlerte(result: Boolean) {
        showCreateAlert = result
    }

    fun changeIngrediente(ingredient: String) {
        ingrediente = ingredient
    }

    fun changeWeb(result: String){
        web = result
    }

    fun changeName(result: String) {
        name = result
    }

    fun changePais(result: String) {
        pais = result
    }

    fun changeCiudad(result: String) {
        ciudad = result
    }


    fun changeDescripcion(desc: String) {
        descripcion = desc
    }

    fun changeUriFoto(uriFo: String) {
        uriFoto = uriFo
    }

    fun changeNamePhoto(name: String) {
        namePhoto = name
    }

    fun changeUriVideo(uriVi: String) {
        uriVideo = uriVi
    }

    fun changeScreen(result: String) {
        screen = result
    }

    // Método para obtener el email del usuario autenticado
    fun getEmail() {
        email = authService.email().toString()
    }

    fun changeValueVotes(value: Double, text: String, email: String) {
        when (text) {
            "puntuacion" -> local = local.copy(puntuacion = value + local.puntuacion!!)
            "votes" -> local = local.copy(votes = local.votes!! + value.toInt())
            "listVotes" -> {
                val updatedListVotes = local.listVotes?.toMutableList() ?: mutableListOf()
                if (!updatedListVotes.contains(email)) {
                    updatedListVotes.add(email)
                    local = local.copy(listVotes = updatedListVotes)
                }
            }
        }
    }

    fun updateStars(colec: String, iDoc: String, onSuccess: () -> Unit){
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                Tasks.await(firestore.updateStarsLocalM(colec, iDoc, local))
            }
            if (result) {
                onSuccess()
            } else {
                // Manejo del caso cuando result es false
                Log.d("ERROR", "Hubo un error al guardar el registro")
            }
        }
        cleanVotes()
    }

    fun changeCurrentRating(current: Double){
        currentRating = current
    }

    fun updateListVotes(newRating: Double){
        listVotes = listVotes + newRating
    }

    fun calculateAverageRating(){
        averageRating = if (listVotes.isNotEmpty()) {
            listVotes.sum() / listVotes.size
        } else {
            0.0
        }
    }

    fun cleanVotes(){
        puntuacion = 0.0
        votes = 0
        currentRating = 0.0
    }

    // Método para limpiar todos los estados
    fun clean() {
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

