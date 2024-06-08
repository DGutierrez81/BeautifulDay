package com.project.beautifulday.ViewModels

import androidx.activity.ComponentActivity
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.android.gms.tasks.Tasks
import com.google.relay.compose.ColumnScopeInstanceImpl.weight
import com.project.beautifulday.Cocktail.CoktailUseCases.NameUseCase
import com.project.beautifulday.Cocktail.CoktailUseCases.UseCaseAlcholic
import com.project.beautifulday.Cocktail.CoktailUseCases.UseCaseCategoryCocktail
import com.project.beautifulday.Cocktail.CoktailUseCases.UseCaseCocktailRandom
import com.project.beautifulday.Cocktail.CoktailUseCases.UseCaseGetCocktailById
import com.project.beautifulday.Cocktail.ui.States.CocktailUser
import com.project.beautifulday.Cocktail.ui.States.drinkState
import com.project.beautifulday.Components.RatingBarImage
import com.project.beautifulday.Firebase.AuthService
import com.project.beautifulday.Firebase.FirestoreService
import com.project.beautifulday.R
import com.project.beautifulday.androidsmall1.jotiOne
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel para manejar la lógica de la vista relacionada con los cócteles.
 *
 * @constructor Crea un ViewModel inyectado con varios casos de uso y servicios.
 * @param nameUseCase Caso de uso para obtener cócteles por nombre.
 * @param authService Servicio de autenticación.
 * @param firestore Servicio para interactuar con Firestore.
 * @param useCaseGetCocktailById Caso de uso para obtener cócteles por ID.
 * @param useCaseCocktailRandom Caso de uso para obtener un cóctel aleatorio.
 * @param useCaseCategoryCocktail Caso de uso para obtener cócteles por categoría.
 * @param useCaseAlcholic Caso de uso para obtener cócteles alcohólicos.
 */
@HiltViewModel
class CocktailViewmodel@Inject constructor(
    private val nameUseCase: NameUseCase,
    private val authService: AuthService,
    private val firestore: FirestoreService,
    private val useCaseGetCocktailById: UseCaseGetCocktailById,
    private val useCaseCocktailRandom: UseCaseCocktailRandom,
    private val useCaseCategoryCocktail: UseCaseCategoryCocktail,
    private val useCaseAlcholic: UseCaseAlcholic
) : ViewModel() {

    // Estado mutable que contiene la lista de datos de cócteles
    private val _cocktailData = MutableStateFlow<List<drinkState>>(emptyList())
    val cocktailData: StateFlow<List<drinkState>> = _cocktailData.asStateFlow()

    // Estado mutable que contiene la lista de usuarios de cócteles
    private val _cocktailUser = MutableStateFlow<List<CocktailUser>>(emptyList())
    val cocktailUser: StateFlow<List<CocktailUser>> = _cocktailUser.asStateFlow()

    // Variables de estado para manejar la información del cóctel y su usuario
    var cocktail by mutableStateOf(CocktailUser())
        private set

    var nameCocktail by mutableStateOf("")
        private set

    var cocktailList by mutableStateOf(mutableListOf<drinkState>())
        private set

    var ingredients by mutableStateOf<MutableList<String>>(mutableListOf())
        private set

    var puntuacion by mutableStateOf(0.0)
        private set

    var votes by mutableStateOf(0)
        private set

    var currentRating by mutableStateOf(0.0)
        private set

    var showVotes by mutableStateOf(false)
        private set

    var listVotes by mutableStateOf(listOf<Double>())
        private set

    var averageRating by mutableStateOf(0.0)
        private set

    var average by mutableStateOf(0.0)
        private set

    // LiveData para manejar el progreso de carga de datos
    private val _progress = MutableLiveData(true)
    val progress: LiveData<Boolean> = _progress

    private val _progressCreated = MutableLiveData(true)
    val progressCreated: LiveData<Boolean> = _progressCreated

    /**
     * Método para obtener un cóctel por su nombre.
     * Actualiza el estado de [_cocktailData] con los datos obtenidos.
     *
     * @param name Nombre del cóctel a buscar.
     */
    fun getName(name: String) {
        _progress.value = true
        viewModelScope.launch {
            _cocktailData.value = nameUseCase(name).drinks ?: mutableListOf()
            delay(3000)
            _progress.value = false
        }
    }


    /**
     * Método para obtener un cóctel aleatorio.
     * Actualiza el estado de [cocktailList] con los datos obtenidos.
     */
    fun getRandom() {
        _progress.value = true
        viewModelScope.launch {
            cocktailList = (useCaseCocktailRandom().drinks ?: mutableListOf()).toMutableList()
            getCocktail()
            delay(3000)
            _progress.value = false
        }
    }

    /**
     * Método para obtener cócteles por categoría.
     * Actualiza el estado de [_cocktailData] con los datos obtenidos.
     *
     * @param category Categoría de cócteles a buscar.
     */
    fun getCategory(category: String) {
        _progress.value = true
        viewModelScope.launch {
            _cocktailData.value = useCaseCategoryCocktail(category).drinks ?: mutableListOf()
            delay(3000)
            _progress.value = false
        }
    }

    /**
     * Método para obtener un cóctel por su ID.
     * Actualiza el estado de [cocktailList] con los datos obtenidos.
     *
     * @param id ID del cóctel a buscar.
     */
    fun getCocktailById(id: String) {
        _progress.value = true
        viewModelScope.launch {
            cocktailList = (useCaseGetCocktailById(id).drinks ?: mutableListOf()).toMutableList()
            getCocktail()
            delay(3000)
            _progress.value = false
        }
    }

    /**
     * Método para obtener cócteles alcohólicos.
     * Actualiza el estado de [_cocktailData] con los datos obtenidos.
     *
     * @param election Tipo de elección para los cócteles alcohólicos.
     */
    fun getAlcoholics(election: String) {
        _progress.value = true
        viewModelScope.launch {
            _cocktailData.value = useCaseAlcholic(election).drinks ?: mutableListOf()
            delay(3000)
            _progress.value = false
        }
    }

    /**
     * Método para obtener cócteles del usuario autenticado.
     * Actualiza el estado de [_cocktailUser] con los datos obtenidos.
     */
    fun fetchCocktail() {
        _progress.value = true
        val email = authService.email()

        viewModelScope.launch {
            _cocktailUser.value = firestore.fetchCocktail(email)
            delay(3000)
            _progress.value = false
        }
    }

    /**
     * Método para obtener cócteles creados por el usuario.
     * Actualiza el estado de [_cocktailUser] con los datos obtenidos.
     *
     * @param colec Colección de cócteles creados.
     */
    fun fetchCocktailCreater(colec: String) {
        _progress.value = true
        viewModelScope.launch {
            _cocktailUser.value = firestore.fetchCocktailCreater(colec)
            delay(3000)
            _progress.value = false
        }
    }

    /**
     * Método para obtener un cóctel del usuario por su ID y colección.
     * Actualiza el estado de [cocktail] con los datos obtenidos.
     *
     * @param document ID del documento del cóctel.
     * @param colec Colección del cóctel.
     */
    fun getCocktailUserById(document: String, colec: String) {
        _progress.value = true
        viewModelScope.launch {
            cocktail = firestore.getCocktailById(document, colec) ?: CocktailUser()
            delay(3000)
            _progress.value = false
        }
    }

    /**
     * Composable para mostrar los nombres de los cócteles de los usuarios.
     *
     * @param cocktailData Lista de datos de cócteles de usuarios.
     * @param navController Controlador de navegación.
     * @param colec Colección de los cócteles.
     */
    @Composable
    fun ShowCocktailNameUser(
        cocktailData: List<CocktailUser>,
        navController: NavController,
        colec: String
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(cocktailData) { index, item ->
                if (index % 2 == 0) {
                    val nextIndex = index + 1
                    val hasNextItem = nextIndex < cocktailData.size
                    val nextItem = if (hasNextItem) cocktailData[nextIndex] else null

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
                            GetImagesUser(cocktail = item, navController, colec)
                            GetImagesUser(cocktail = nextItem, navController, colec)
                        }
                    }
                }
            }
        }
    }


    /**
     * Composable para mostrar la imagen de un cóctel del usuario.
     *
     * @param cocktail Cóctel del usuario.
     * @param navController Controlador de navegación.
     * @param colec Colección del cóctel.
     */
    @Composable
    fun GetImagesUser(cocktail: CocktailUser?, navController: NavController, colec: String) {
        cocktail?.strDrinkThumb?.let { url ->
            Box(
                modifier = Modifier
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    AsyncImage(
                        model = url,
                        contentDescription = "Cocktail Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(120.dp)
                            .height(120.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .clickable {
                                navController.navigate("cardCocktailUser/${cocktail.idDocument}?colec=$colec")
                            }
                    )
                    Text(
                        text = cocktail?.strDrink ?: "",
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
                    val average = calculateAverage(cocktail.votes ?: 0, cocktail.puntuacion ?: 0.0)

                    if (colec == "Create cocktail") {
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

    /**
     * Composable para mostrar los nombres de los cócteles.
     *
     * @param cocktail Lista de cócteles.
     * @param navController Controlador de navegación.
     */
    @Composable
    fun ShowCocktailsName(cocktail: List<drinkState>, navController: NavController) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(cocktail) { index, item ->
                if (index % 2 == 0) {
                    val nextIndex = index + 1
                    val hasNextItem = nextIndex < cocktail.size
                    val nextItem = if (hasNextItem) cocktail[nextIndex] else null

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
                            GetImages(cocktail = item, navController)
                            GetImages(cocktail = nextItem, navController)
                        }
                    }
                }
            }
        }
    }

    /**
     * Composable para mostrar la imagen de un cóctel.
     *
     * @param cocktail Cóctel.
     * @param navController Controlador de navegación.
     */
    @Composable
    fun GetImages(cocktail: drinkState?, navController: NavController) {
        cocktail?.strDrinkThumb.let { url ->
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    AsyncImage(
                        model = url,
                        contentDescription = "Meal Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(120.dp)
                            .height(120.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .clickable {
                                SaveCocktail(
                                    idDrink = cocktail?.idDrink ?: "",
                                    strDrink = cocktail?.strDrink ?: "",
                                    strInstructions = cocktail?.strInstructions ?: "",
                                    strDrinkThumb = cocktail?.strDrinkThumb ?: "",
                                    strList = cocktail?.strIngredient ?: mutableListOf(),
                                    strMedia = null,
                                    nameUser = ""
                                )
                                getCocktailById(cocktail?.idDrink ?: "")
                                navController.navigate("cardCocktails")
                            }
                    )
                    Text(
                        text = cocktail?.strDrink ?: "",
                        modifier = Modifier
                            .height(120.dp)
                            .width(120.dp)
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center,
                        fontFamily = jotiOne,
                        color = colorResource(id = R.color.paynesGray)
                    )
                }
            }
        }
    }

    /**
     * Guarda la información de un cóctel.
     *
     * @param idDrink ID del cóctel.
     * @param strDrink Nombre del cóctel.
     * @param strInstructions Instrucciones del cóctel.
     * @param strDrinkThumb URL de la imagen del cóctel.
     * @param strList Lista de ingredientes del cóctel.
     * @param strMedia URL de los medios del cóctel.
     * @param nameUser Nombre del usuario.
     */
    fun SaveCocktail(
        idDrink: String,
        strDrink: String,
        strInstructions: String,
        strDrinkThumb: String,
        strList: MutableList<String>,
        strMedia: String?,
        nameUser: String?
    ) {
        val email = authService.email()
        ingredients.clear()

        strList.forEach { if (it != "") ingredients.add(it) }

        cocktail = CocktailUser(
            emailUser = email,
            idDrink = idDrink,
            strDrink = strDrink,
            strInstructions = strInstructions,
            strDrinkThumb = strDrinkThumb,
            strList = ingredients,
            strmedia = strMedia,
            nameUser = nameUser,
        )
    }

    /**
     * Guarda la información de un cóctel creado por el usuario.
     *
     * @param strDrink Nombre del cóctel.
     * @param strInstructions Instrucciones del cóctel.
     * @param strDrinkThumb URL de la imagen del cóctel.
     * @param strList Lista de ingredientes del cóctel.
     * @param strMedia URL de los medios del cóctel.
     * @param nameUser Nombre del usuario.
     */
    fun SaveCocktailCreater(
        strDrink: String,
        strInstructions: String,
        strDrinkThumb: String,
        strList: MutableList<String>,
        strMedia: String?,
        nameUser: String?
    ) {
        val email = authService.email()
        ingredients.clear()

        strList.forEach { if (it != "") ingredients.add(it) }

        cocktail = CocktailUser(
            emailUser = email,
            strDrink = strDrink,
            strInstructions = strInstructions,
            strDrinkThumb = strDrinkThumb,
            strList = ingredients,
            strmedia = strMedia,
            nameUser = nameUser
        )
    }

    /**
     * Guarda un nuevo cóctel en Firestore.
     *
     * @param colec Colección donde se guardará el cóctel.
     * @param context Contexto de la actividad.
     * @param onOk Callback para manejar la confirmación.
     * @param onSuccess Callback para manejar el éxito.
     */
    fun saveNewCocktail(
        colec: String,
        context: ComponentActivity,
        onOk: () -> Unit,
        onSuccess: () -> Unit
    ) {
        _progressCreated.value = true
        val email = authService.email()
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                Tasks.await(
                    firestore.saveNewCocktail(
                        colec,
                        "idDrink",
                        cocktail.idDrink,
                        cocktail,
                        email,
                        context
                    )
                )
            }
            if (result) {
                onOk()
                delay(3000)
                _progressCreated.value = false
                delay(3000)
                onSuccess()
            } else {
                _progressCreated.value = false
                android.util.Log.d(
                    "ERROR",
                    "Hubo un error al guardar el registro o el registro ya existe"
                )
            }
        }
    }

    /**
     * Método para guardar los cócteles en la lista de cócteles.
     */
    fun getCocktail() {
        for (drink in cocktailList) {
            SaveCocktail(
                idDrink = drink.idDrink,
                strDrink = drink.strDrink,
                strInstructions = drink.strInstructions ?: "",
                strDrinkThumb = drink.strDrinkThumb ?: "",
                strList = drink.strIngredient,
                strMedia = null,
                nameUser = ""
            )
        }
    }

    /**
     * Actualiza la puntuación y el número de votos de un cóctel en Firestore.
     *
     * @param iDoc ID del documento del cóctel.
     * @param onSuccess Callback para manejar el éxito.
     */
    fun updateStars(iDoc: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                Tasks.await(firestore.updateStarsCocktail("Create cocktail", iDoc, cocktail))
            }
            if (result) {
                onSuccess()
            } else {
                android.util.Log.d("ERROR", "Hubo un error al guardar el registro")
            }
        }
        cleanVotes()
    }

    /**
     * Actualiza los valores de puntuación, votos y lista de votos de un cóctel.
     *
     * @param value Nuevo valor.
     * @param text Texto indicador.
     * @param email Email del usuario.
     */
    fun changeValueVotes(value: Double, text: String, email: String) {
        when (text) {
            "puntuacion" -> cocktail = cocktail.copy(puntuacion = value + cocktail.puntuacion!!)
            "votes" -> cocktail = cocktail.copy(votes = cocktail.votes!! + value.toInt())
            "listVotes" -> {
                val updatedListVotes = cocktail.listVotes?.toMutableList() ?: mutableListOf()
                if (!updatedListVotes.contains(email)) {
                    updatedListVotes.add(email)
                    cocktail = cocktail.copy(listVotes = updatedListVotes)
                }
            }
        }
    }

    /**
     * Calcula la puntuación media de un cóctel.
     */
    fun calculateAverageRating() {
        averageRating = if (listVotes.isNotEmpty()) {
            listVotes.sum() / listVotes.size
        } else {
            0.0
        }
    }

    /**
     * Calcula el promedio de puntuación de un cóctel.
     *
     * @param votes Número de votos.
     * @param valueVote Valor de la puntuación.
     * @return El promedio de la puntuación.
     */
    fun calculateAverage(votes: Int, valueVote: Double): Double = valueVote / votes

    /**
     * Cambia la puntuación actual de un cóctel.
     *
     * @param current Nueva puntuación actual.
     */
    fun changeCurrentRating(current: Double) {
        currentRating = current
    }

    /**
     * Actualiza la lista de votos de un cóctel con un nuevo valor de puntuación.
     *
     * @param newRating Nuevo valor de puntuación.
     */
    fun updateListVotes(newRating: Double) {
        listVotes = listVotes + newRating
    }

    /**
     * Cambia el nombre del cóctel.
     *
     * @param result Nuevo nombre del cóctel.
     */
    fun changeNameCocktail(result: String) {
        nameCocktail = result
    }

    /**
     * Limpia el nombre del cóctel.
     */
    fun clean() {
        nameCocktail = ""
    }

    /**
     * Cambia el estado para mostrar los votos.
     *
     * @param result Nuevo estado.
     */
    fun changeShowVotes(result: Boolean) {
        showVotes = result
    }

    /**
     * Limpia los valores de puntuación y votos.
     */
    fun cleanVotes() {
        puntuacion = 0.0
        votes = 0
        currentRating = 0.0
    }
}