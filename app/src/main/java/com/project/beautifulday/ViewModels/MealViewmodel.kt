package com.project.beautifulday.ViewModels

import android.util.Log
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
import com.project.beautifulday.Components.RatingBarImage
import com.project.beautifulday.Firebase.AuthService
import com.project.beautifulday.Firebase.FirestoreService
import com.project.beautifulday.Meal.MealUseCases.UseCaseListArea
import com.project.beautifulday.Meal.MealUseCases.UseCaseListCategories
import com.project.beautifulday.Meal.MealUseCases.UseCaseListCategory
import com.project.beautifulday.Meal.MealUseCases.UseCaseMealArea
import com.project.beautifulday.Meal.MealUseCases.UseCaseMealId
import com.project.beautifulday.Meal.MealUseCases.UseCaseMealName
import com.project.beautifulday.Meal.MealUseCases.UseCaseRandom
import com.project.beautifulday.Meal.MealUseCases.UseListCategoy
import com.project.beautifulday.Meal.ui.States.CategoriesState
import com.project.beautifulday.Meal.ui.States.MealState
import com.project.beautifulday.Meal.ui.States.MealUser
import com.project.beautifulday.R
import com.project.beautifulday.androidsmall1.jotiOne
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel para manejar la lógica relacionada con las comidas.
 *
 * @property useCaseMealName Caso de uso para obtener el nombre de una comida.
 * @property useCaseRandom Caso de uso para obtener una comida aleatoria.
 * @property useListCategoy Caso de uso para obtener una lista de categorías.
 * @property useCaseListArea Caso de uso para obtener una lista de áreas.
 * @property useCaseMealId Caso de uso para obtener una comida por ID.
 * @property useCaseListCategory Caso de uso para obtener una lista de categorías por nombre.
 * @property useCaseListCateries Caso de uso para obtener una lista de todas las categorías.
 * @property authService Servicio de autenticación.
 * @property firestore Servicio Firestore para manejar operaciones de base de datos.
 * @property useCaseMealArea Caso de uso para obtener comidas por área.
 */
@HiltViewModel
class MealViewmodel @Inject constructor(
    private val useCaseMealName: UseCaseMealName,
    private val useCaseRandom: UseCaseRandom,
    private val useListCategoy: UseListCategoy,
    private val useCaseListArea: UseCaseListArea,
    private val useCaseMealId: UseCaseMealId,
    private val useCaseListCategory: UseCaseListCategory,
    private val useCaseListCateries: UseCaseListCategories,
    private val authService: AuthService,
    private val firestore: FirestoreService,
    private val useCaseMealArea: UseCaseMealArea
) : ViewModel() {

    private val _mealsData = MutableStateFlow<List<MealState>>(emptyList())
    val mealsData: StateFlow<List<MealState>> = _mealsData

    private val _categoryData = MutableStateFlow<List<CategoriesState>>(emptyList())
    val categoyData: StateFlow<List<CategoriesState>> = _categoryData

    var mealList by mutableStateOf(mutableListOf<MealState>())
        private set

    var meal by mutableStateOf(MealUser())
        private set

    var mealName by mutableStateOf("")
        private set

    var showOutLineText by mutableStateOf(false)
        private set

    var ingredients by mutableStateOf<MutableList<String>>(mutableListOf())
        private set

    var measures by mutableStateOf<MutableList<String>>(mutableListOf())
        private set

    var actionTranslate by mutableStateOf(true)
        private set

    var categoria by mutableStateOf("")
        private set

    private val _mealData = MutableStateFlow<List<MealUser>>(emptyList())
    val mealData: StateFlow<List<MealUser>> = _mealData

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

    private val _progress = MutableLiveData(true)
    val progress: LiveData<Boolean> = _progress

    private val _progressCreated = MutableLiveData(true)
    val progressCreated: LiveData<Boolean> = _progressCreated

    /**
     * Obtiene las comidas por nombre.
     *
     * @param name Nombre de la comida a buscar.
     */
    fun getMealName(name: String) {
        _progress.value = true
        viewModelScope.launch {
            _mealsData.value = useCaseMealName(name).meals ?: mutableListOf()
            delay(3000)
            _progress.value = false
        }
    }

    /**
     * Obtiene una comida aleatoria.
     */
    fun getRandom() {
        _progress.value = true
        viewModelScope.launch {
            mealList = (useCaseRandom().meals ?: mutableListOf()).toMutableList()
            getMeal()
            delay(3000)
            _progress.value = false
        }
    }

    /**
     * Obtiene las categorías de comida por nombre de categoría.
     *
     * @param category Nombre de la categoría a buscar.
     */
    fun getMealCategory(category: String) {
        viewModelScope.launch {
            _categoryData.value = useCaseListCategory(category).categories ?: mutableListOf()
        }
    }

    /**
     * Obtiene una lista de todas las categorías de comida.
     */
    fun getListCategories() {
        _progress.value = true
        viewModelScope.launch {
            _mealsData.value = useCaseListCateries().meals ?: mutableListOf()
            delay(3000)
            _progress.value = false
        }
    }

    /**
     * Guarda una comida en la lista de comidas.
     */
    fun getMeal() {
        for (meal in mealList) {
            saveMeal(
                idMeal = meal.idMeal ?: "",
                strMeal = meal.strMeal ?: "",
                strCategory = meal.strCategory ?: "",
                strArea = meal.strArea ?: "",
                strInstructions = meal.strInstructions ?: "",
                strMealThumb = meal.strMealThumb ?: "",
                strTags = meal.strTags ?: "",
                strYoutube = meal.strYoutube ?: "",
                strIngredients = meal.strIngredients ?: mutableListOf(),
                strMeasures = meal.strMeasures ?: mutableListOf()
            )
        }
    }

    /**
     * Obtiene una lista de categorías.
     */
    fun getListCategory() {
        _progress.value = true
        viewModelScope.launch {
            _categoryData.value = useListCategoy().categories ?: mutableListOf()
            delay(3000)
            _progress.value = false
        }
    }

    /**
     * Obtiene una lista de áreas de comida.
     */
    fun getListArea() {
        viewModelScope.launch {
            _mealsData.value = useCaseListArea().meals ?: mutableListOf()
        }
    }

    /**
     * Obtiene comidas por área.
     *
     * @param area Nombre del área a buscar.
     */
    fun getMealArea(area: String) {
        _progress.value = true
        viewModelScope.launch {
            _mealsData.value = useCaseMealArea(area).meals ?: mutableListOf()
            delay(3000)
            _progress.value = false
        }
    }

    /**
     * Obtiene una comida por ID.
     *
     * @param id ID de la comida a buscar.
     */
    fun getMealById(id: String) {
        _progress.value = true
        viewModelScope.launch {
            mealList = (useCaseMealId(id).meals ?: mutableListOf()).toMutableList()
            getMeal()
            delay(3000)
            _progress.value = false
        }
    }

    /**
     * Composable para mostrar los nombres de las comidas.
     *
     * @param meals Lista de comidas a mostrar.
     * @param navController Controlador de navegación.
     */
    @Composable
    fun ShowMealsName(meals: List<MealState>, navController: NavController) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(meals) { index, item ->
                if (index % 2 == 0) {
                    val nextIndex = index + 1
                    val hasNextItem = nextIndex < meals.size
                    val nextItem = if (hasNextItem) meals[nextIndex] else null

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
                            GetImages(meal = item, navController)
                            GetImages(meal = nextItem, navController)
                        }
                    }
                }
            }
        }
    }

    /**
     * Composable para mostrar los nombres de las comidas de usuario.
     *
     * @param mealData Lista de comidas de usuario a mostrar.
     * @param navController Controlador de navegación.
     * @param colec Colección de la comida.
     */
    @Composable
    fun ShowMealsNameUser(mealData: List<MealUser>, navController: NavController, colec: String) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(mealData) { index, item ->
                if (index % 2 == 0) {
                    val nextIndex = index + 1
                    val hasNextItem = nextIndex < mealData.size
                    val nextItem = if (hasNextItem) mealData[nextIndex] else null

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
                            GetImagesUser(meal = item, navController, colec)
                            GetImagesUser(meal = nextItem, navController, colec)
                        }
                    }
                }
            }
        }
    }


    /**
     * Composable que muestra una imagen de una comida y maneja su navegación.
     * @param meal Un objeto `MealState?` que contiene los datos de la comida.
     * @param navController El controlador de navegación para manejar las rutas.
     */
    @Composable
    fun GetImages(meal: MealState?, navController: NavController) {
        meal?.strMealThumb.let { url ->
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
                                saveMeal(
                                    idMeal = meal?.idMeal ?: "",
                                    strMeal = meal?.strMeal ?: "",
                                    strCategory = meal?.strCategory ?: "",
                                    strArea = meal?.strArea ?: "",
                                    strInstructions = meal?.strInstructions ?: "",
                                    strMealThumb = meal?.strMealThumb ?: "",
                                    strTags = meal?.strTags ?: "",
                                    strYoutube = meal?.strYoutube ?: "",
                                    strIngredients = meal?.strIngredients ?: mutableListOf(),
                                    strMeasures = meal?.strMeasures ?: mutableListOf()
                                )
                                getMealById(meal?.idMeal ?: "")
                                navController.navigate("myCard")
                                actionTranslate = true
                            }
                    )
                    Text(
                        text = meal?.strMeal ?: "",
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
     * Composable que muestra una imagen de una comida de usuario y maneja su navegación.
     * @param meal Un objeto `MealUser?` que contiene los datos de la comida del usuario.
     * @param navController El controlador de navegación para manejar las rutas.
     * @param colec La colección a la que pertenece la comida.
     */
    @Composable
    fun GetImagesUser(meal: MealUser?, navController: NavController, colec: String) {
        meal?.strMealThumb?.let { url ->
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
                                navController.navigate("cardMealUser/${meal.idDocument}?colec=$colec")
                                actionTranslate = true
                            }
                    )
                    Text(
                        text = meal.strMeal ?: "",
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
                    val average = calculateAverage(meal.votes ?: 0, meal.points ?: 0.0)

                    if (colec == "Create meal") {
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
     * Guarda los datos de una comida.
     * @param idMeal ID de la comida.
     * @param strMeal Nombre de la comida.
     * @param strCategory Categoría de la comida.
     * @param strArea Área de origen de la comida.
     * @param strInstructions Instrucciones de preparación.
     * @param strMealThumb URL de la imagen de la comida.
     * @param strTags Etiquetas asociadas a la comida.
     * @param strYoutube URL del video de YouTube.
     * @param strIngredients Lista de ingredientes.
     * @param strMeasures Lista de medidas.
     * @param nameUser Nombre del usuario (opcional).
     */
    fun saveMeal(
        idMeal: String,
        strMeal: String,
        strCategory: String,
        strArea: String,
        strInstructions: String,
        strMealThumb: String,
        strTags: String,
        strYoutube: String,
        strIngredients: MutableList<String>,
        strMeasures: MutableList<String>,
        nameUser: String? = "",
    ) {
        ingredients.clear()
        measures.clear()

        val email = authService.email()

        strIngredients.forEach { if (it != "") ingredients.add(it) }
        strMeasures.forEach { if (it != "") measures.add(it) }

        meal = MealUser(
            idMeal = idMeal,
            strMeal = strMeal,
            strCategory = strCategory,
            strArea = strArea,
            strInstructions = strInstructions,
            strMealThumb = strMealThumb,
            strTags = strTags,
            strYoutube = strYoutube,
            strIngredients = ingredients,
            strMeasures = measures,
            emailUser = email.toString(),
            nameUser = nameUser
        )
    }

    /**
     * Guarda los datos de una comida creador.
     * @param strMeal Nombre de la comida.
     * @param strCategory Categoría de la comida.
     * @param strArea Área de origen de la comida.
     * @param strInstructions Instrucciones de preparación.
     * @param strMealThumb URL de la imagen de la comida.
     * @param strTags Etiquetas asociadas a la comida.
     * @param strYoutube URL del video de YouTube.
     * @param strIngredients Lista de ingredientes.
     * @param strMeasures Lista de medidas.
     * @param nameUser Nombre del usuario (opcional).
     */
    fun saveMealCreater(
        strMeal: String,
        strCategory: String,
        strArea: String,
        strInstructions: String,
        strMealThumb: String,
        strTags: String,
        strYoutube: String,
        strIngredients: MutableList<String>,
        strMeasures: MutableList<String>,
        nameUser: String? = "",
    ) {
        ingredients.clear()
        measures.clear()

        val email = authService.email()

        strIngredients.forEach { if (it != "") ingredients.add(it) }
        strMeasures.forEach { if (it != "") measures.add(it) }

        meal = MealUser(
            strMeal = strMeal,
            strCategory = strCategory,
            strArea = strArea,
            strInstructions = strInstructions,
            strMealThumb = strMealThumb,
            strTags = strTags,
            strYoutube = strYoutube,
            strIngredients = ingredients,
            strMeasures = measures,
            emailUser = email.toString(),
            nameUser = nameUser
        )
    }

    /**
     * Guarda una nueva comida en Firestore.
     * @param colec Colección a la que pertenece la comida.
     * @param context Contexto de la actividad.
     * @param onOk Función que se ejecuta al completar correctamente la operación.
     * @param onSuccess Función que se ejecuta al tener éxito la operación.
     */
    fun saveNewMeals(
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
                    firestore.saveNewMeal(
                        colec,
                        "idMeal",
                        meal.idMeal,
                        meal,
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
                Log.d("ERROR", "Hubo un error al guardar el registro o el registro ya existe")
            }
        }
    }

    /**
     * Obtiene las comidas del usuario actual.
     */
    fun fetchMeal() {
        _progress.value = true
        val email = authService.email()

        viewModelScope.launch {
            _mealData.value = firestore.fetchMeal(email)
            delay(3000)
            _progress.value = false
        }
    }

    /**
     * Obtiene las comidas creadas por el usuario.
     * @param colec Colección a la que pertenece la comida.
     */
    fun fetchMealCreater(colec: String) {
        _progress.value = true
        viewModelScope.launch {
            _mealData.value = firestore.fetchMealCreater(colec)
            delay(3000)
            _progress.value = false
        }
    }

    /**
     * Obtiene los datos de una comida de usuario por su ID.
     * @param document ID del documento de la comida.
     * @param colec Colección a la que pertenece la comida.
     */
    fun getMealUserById(document: String, colec: String) {
        _progress.value = true
        viewModelScope.launch {
            meal = firestore.getMealById(document, colec) ?: MealUser()
            delay(3000)
            _progress.value = false
        }
    }

    /**
     * Actualiza las estrellas de una comida.
     * @param iDoc ID del documento de la comida.
     * @param onSuccess Función que se ejecuta al tener éxito la operación.
     */
    fun updateStars(iDoc: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                Tasks.await(firestore.updateStarsMeal("Create meal", iDoc, meal))
            }
            if (result) {
                onSuccess()
            } else {
                Log.d("ERROR", "Hubo un error al guardar el registro")
            }
        }
        cleanVotes()
    }

    /**
     * Cambia el valor de los votos o puntuaciones de una comida.
     * @param value Valor a cambiar.
     * @param text Tipo de cambio ("puntuacion", "votes" o "listVotes").
     * @param email Email del usuario.
     */
    fun changeValueVotes(value: Double, text: String, email: String) {
        when (text) {
            "puntuacion" -> meal = meal.copy(points = value + meal.points!!)
            "votes" -> meal = meal.copy(votes = meal.votes!! + value.toInt())
            "listVotes" -> {
                val updatedListVotes = meal.listVotes?.toMutableList() ?: mutableListOf()
                if (!updatedListVotes.contains(email)) {
                    updatedListVotes.add(email)
                    meal = meal.copy(listVotes = updatedListVotes)
                }
            }
        }
    }

    /**
     * Cambia el nombre de la comida.
     * @param name Nuevo nombre de la comida.
     */
    fun changeMealName(name: String) {
        mealName = name
    }

    /**
     * Calcula el promedio de la puntuación.
     */
    fun calculateAverageRating() {
        averageRating = if (listVotes.isNotEmpty()) {
            listVotes.sum() / listVotes.size
        } else {
            0.0
        }
    }

    /**
     * Calcula el promedio dado los votos y la puntuación.
     * @param votes Número de votos.
     * @param valueVote Valor total de las puntuaciones.
     * @return Promedio calculado.
     */
    fun calculateAverage(votes: Int, valueVote: Double): Double = valueVote / votes

    /**
     * Cambia la puntuación actual.
     * @param current Nueva puntuación actual.
     */
    fun changeCurrentRating(current: Double) {
        currentRating = current
    }

    /**
     * Actualiza la lista de votos con una nueva puntuación.
     * @param newRating Nueva puntuación a agregar.
     */
    fun updateListVotes(newRating: Double) {
        listVotes = listVotes + newRating
    }

    /**
     * Cambia el estado de mostrar texto subrayado.
     * @param result Nuevo estado.
     */
    fun changeshowOutLineText(result: Boolean) {
        showOutLineText = !result
    }

    /**
     * Cambia el estado de la acción de traducción.
     * @param value Nuevo estado de la acción de traducción.
     */
    fun changeActionTranslate(value: Boolean) {
        actionTranslate = value
    }

    /**
     * Cambia la traducción de la categoría.
     * @param translate Nueva traducción de la categoría.
     */
    fun changeTraducir(translate: String) {
        categoria = translate
    }

    /**
     * Cambia el estado de mostrar votos.
     * @param result Nuevo estado de mostrar votos.
     */
    fun changeShowVotes(result: Boolean) {
        showVotes = result
    }

    /**
     * Limpia los votos, puntuaciones y la puntuación actual.
     */
    fun cleanVotes() {
        puntuacion = 0.0
        votes = 0
        currentRating = 0.0
    }
}