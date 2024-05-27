package com.project.beautifulday.ViewModels

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.project.beautifulday.Cocktail.ui.States.CocktailState
import com.project.beautifulday.Cocktail.ui.States.CocktailUser
import com.project.beautifulday.Cocktail.ui.States.drinkState
import com.project.beautifulday.Firebase.AuthService
import com.project.beautifulday.Firebase.FirestoreService
import com.project.beautifulday.Meal.ui.States.MealState
import com.project.beautifulday.Meal.ui.States.MealUser
import com.project.beautifulday.R
import com.project.beautifulday.androidsmall1.jotiOne
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CocktailViewmodel@Inject constructor(private val nameUseCase: NameUseCase, private val authService: AuthService,
                                           private val firestore: FirestoreService, private val useCaseGetCocktailById: UseCaseGetCocktailById,
                                           private val useCaseCocktailRandom: UseCaseCocktailRandom, private val useCaseCategoryCocktail: UseCaseCategoryCocktail,
                                           private val useCaseAlcholic: UseCaseAlcholic
): ViewModel() {

    private val _cocktailData = MutableStateFlow<List<drinkState>>(emptyList())
    val cocktailData: StateFlow<List<drinkState>> = _cocktailData.asStateFlow()

    private val _cocktailUser = MutableStateFlow<List<CocktailUser>>(emptyList())
    val cocktailUser: StateFlow<List<CocktailUser>> = _cocktailUser.asStateFlow()

    var cocktail by mutableStateOf(CocktailUser())
        private set
    var drink by mutableStateOf(CocktailUser())
        private set

    var nameCocktail by mutableStateOf("")
        private set

    var actionTranslate by mutableStateOf(true)
        private set

    var cocktailList by mutableStateOf(mutableListOf<drinkState>())
        private set

    var ingredients by mutableStateOf<MutableList<String>>(mutableListOf())
        private set

    fun getName(name: String) {
        viewModelScope.launch {
            _cocktailData.value = nameUseCase(name).drinks ?: mutableListOf()
        }
    }

    fun getRandom() {
        viewModelScope.launch {
           // _cocktail.value = useCaseCocktailRandom.drinks ?: mutableListOf()
            cocktailList = (useCaseCocktailRandom().drinks ?: mutableListOf()).toMutableList()
            getCocktail()
        }
    }

    fun getCategory(category: String){
        viewModelScope.launch {
            _cocktailData.value = useCaseCategoryCocktail(category).drinks ?: mutableListOf()
        }
    }

    fun getCocktailById(id: String){
        viewModelScope.launch {
            cocktailList = (useCaseGetCocktailById(id).drinks?: mutableListOf()).toMutableList()
            getCocktail()
        }
    }

    fun getAlcoholics(election: String){
        viewModelScope.launch{
            _cocktailData.value = useCaseAlcholic(election).drinks ?: mutableListOf()
        }
    }

    fun fetchCocktail(){
        val email = authService.email()

        viewModelScope.launch {
            _cocktailUser.value = firestore.fetchCocktail(email)
        }
    }

    fun fetchCocktailCreater(){

        viewModelScope.launch {
            _cocktailUser.value = firestore.fetchCocktailCreater()
        }
    }

    fun getCocktailUserById(document: String, colec: String){
        viewModelScope.launch {
            cocktail = firestore.getCocktailById(document, colec)?: CocktailUser()
        }
    }


    @Composable
    fun ShowCocktailNameUser(cocktailData: List<CocktailUser>, navController: NavController, colec: String){
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            itemsIndexed(cocktailData){index, item ->
                if(index % 2 == 0){
                    val nextIndex = index + 1
                    val hasNextItem = nextIndex < cocktailData.size
                    val nextItem = if(hasNextItem) cocktailData[nextIndex] else null

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


    @Composable
    fun GetImagesUser(cocktail: CocktailUser?, navController: NavController, colec: String){
        cocktail?.strDrinkThumb.let{ url ->
            Box(
                modifier = Modifier.
                weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    AsyncImage(model = url, contentDescription = "Cocktail Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(120.dp)
                            .height(120.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .clickable {
                                navController.navigate("cardCocktailUser/${cocktail?.idDocument}?colec=$colec")
                                actionTranslate = true
                            }
                    )
                    Text(
                        text = cocktail?.strDrink?:"", modifier = Modifier
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


    @Composable
    fun ShowCocktailsName(cocktail: List<drinkState>, navController: NavController){
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            itemsIndexed(cocktail){index, item ->
                if(index % 2 == 0){
                    val nextIndex = index + 1
                    val hasNextItem = nextIndex < cocktail.size
                    val nextItem = if(hasNextItem) cocktail[nextIndex] else null

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
    @Composable
    fun GetImages(cocktail: drinkState?, navController: NavController){
        cocktail?.strDrinkThumb.let{ url ->
            Box(
                modifier = Modifier.
                weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    AsyncImage(model = url, contentDescription = "Meal Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(120.dp)
                            .height(120.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .clickable {
                                SaveCocktail(
                                    idDrink = cocktail?.idDrink?: "",
                                    strDrink = cocktail?.strDrink?: "",
                                    strInstructions = cocktail?.strInstructions?: "",
                                    strDrinkThumb = cocktail?.strDrinkThumb?: "",
                                    strList = cocktail?.strIngredient?: mutableListOf(),
                                    strMedia = null
                                )
                                getCocktailById(cocktail?.idDrink?:"")
                                navController.navigate("cardCocktails")
                                actionTranslate = true
                            }
                    )
                    Text(
                        text = cocktail?.strDrink?:"", modifier = Modifier
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


    fun getCocktail() {
        for (drink in cocktailList) {
            SaveCocktail(
                idDrink = drink.idDrink,
                strDrink = drink.strDrink,
                strInstructions = drink.strInstructions?: "",
                strDrinkThumb = drink.strDrinkThumb?: "",
                strList = drink.strIngredient,
                strMedia = null
            )
        }
    }

    fun SaveCocktail(
        idDrink: String,
        strDrink: String,
        strInstructions: String,
        strDrinkThumb: String,
        strList: MutableList<String>,
        strMedia: String?
    ) {

        val email = authService.email()

        ingredients.clear()
        //measures.clear()

        strList.forEach { if (it != "") ingredients.add(it) }
        //strMeasures.forEach { if(it != "") measures.add(it) }

        drink = CocktailUser(email,idDrink, strDrink, strInstructions, strDrinkThumb, ingredients, strMedia)
    }


    fun saveNewCocktail(colec: String, context: ComponentActivity, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                Tasks.await(firestore.saveNewCocktail(colec, "idDrink", drink.idDrink, drink, context))
            }
            if (result) {
                onSuccess()
            } else {
                // Manejo del caso cuando result es false
                Log.d("ERROR", "Hubo un error al guardar el registro o el registro ya existe")
            }
        }
    }


    fun changeActionTranslate(value: Boolean){
        actionTranslate = value
    }

    fun changeNameCocktail(result: String){
        nameCocktail = result
    }

    fun clean(){
        nameCocktail = ""
    }
}