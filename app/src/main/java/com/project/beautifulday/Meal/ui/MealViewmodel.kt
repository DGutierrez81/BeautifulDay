package com.project.beautifulday.Meal.ui

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
import com.google.relay.compose.ColumnScopeInstanceImpl.weight
import com.project.beautifulday.Meal.MealUseCases.UseCaseListArea
import com.project.beautifulday.Meal.MealUseCases.UseCaseListIngredient
import com.project.beautifulday.Meal.MealUseCases.UseCaseMealName
import com.project.beautifulday.Meal.MealUseCases.UseCaseRandom
import com.project.beautifulday.Meal.MealUseCases.UseListCategoy
import com.project.beautifulday.Meal.ui.States.IngredientState
import com.project.beautifulday.Meal.ui.States.MealState
import com.project.beautifulday.Meal.ui.States.MealUser
import com.project.beautifulday.R
import com.project.beautifulday.inicio2.jotiOne
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealViewmodel@Inject constructor(private val useCaseMealName: UseCaseMealName,
    private val useCaseRandom: UseCaseRandom, private val useListCategoy: UseListCategoy,
    private val useCaseListArea: UseCaseListArea, private val useCaseListIngredient: UseCaseListIngredient): ViewModel() {

    private val _mealsData = MutableStateFlow<List<MealState>>(emptyList())
    val mealsData: StateFlow<List<MealState>> = _mealsData

    private val _ingredientData = MutableStateFlow<List<IngredientState>>(emptyList())
    val ingredientData: StateFlow<List<IngredientState>> = _ingredientData

    var mealList by mutableStateOf(mutableListOf<MealState>())
        private set

    var meal by mutableStateOf(MealUser())
        private set
    var mealName by mutableStateOf("")
        private set

    var showOutLineText by mutableStateOf(false)
        private set

    var showViewCenter by mutableStateOf(true)
        private set

    var ingredients by mutableStateOf<MutableList<String>>(mutableListOf())
        private set

    var measures by mutableStateOf<MutableList<String>>(mutableListOf())
        private set



    fun getMealName(name: String){
        viewModelScope.launch {
            _mealsData.value = useCaseMealName(name).meals?: mutableListOf()
        }
    }

    fun getRandom(){
        viewModelScope.launch {
            mealList = (useCaseRandom().meals?: mutableListOf()).toMutableList()
            getMeal()
        }
    }


    fun getMeal(){
        for(meal in mealList){
            saveMeal(idMeal = meal?.idMeal ?: "",
                strMeal = meal?.strMeal ?: "",
                strCategory = meal?.strCategory ?: "",
                strArea = meal?.strArea ?: "",
                strInstructions = meal?.strInstructions ?: "",
                strMealThumb = meal?.strMealThumb ?: "",
                strTags = meal?.strTags ?: "",
                strYoutube = meal?.strYoutube ?: "",
                strIngredients = meal.strIngredients,
                strMeasures = meal.strMeasures)
        }
        /*
        LazyColumn(){
            itemsIndexed(meals){index, item ->
                saveMeal(idMeal = meal?.idMeal ?: "",
                    strMeal = meal?.strMeal ?: "",
                    strCategory = meal?.strCategory ?: "",
                    strArea = meal?.strArea ?: "",
                    strInstructions = meal?.strInstructions ?: "",
                    strMealThumb = meal?.strMealThumb ?: "",
                    strTags = meal?.strTags ?: "",
                    strYoutube = meal?.strYoutube ?: "",
                    strIngredients = meal.strIngredients,
                    strMeasures = meal.strMeasures)
            }
        }

         */
    }

    fun getListCategory(){
        viewModelScope.launch {
            _mealsData.value = useListCategoy().meals?: mutableListOf()
        }
    }

    fun getListArea(){
        viewModelScope.launch{
            _mealsData.value = useCaseListArea().meals?: mutableListOf()
        }
    }

    fun getListIngredient(){
        viewModelScope.launch {
            _ingredientData.value = useCaseListIngredient().meals?: mutableListOf()
        }
    }
    @Composable
    fun ShowMealsName(meals: List<MealState>, navController: NavController){
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            itemsIndexed(meals){index, item ->
                if(index % 2 == 0){
                    val nextIndex = index + 1
                    val hasNextItem = nextIndex < meals.size
                    val nextItem = if(hasNextItem) meals[nextIndex] else null

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
    
    @Composable
    fun GetImages(meal: MealState?, navController: NavController){
        meal?.strMealThumb.let{ url ->
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
                                navController.navigate("myCard")
                            }
                    )
                    Text(
                        text = meal?.strMeal?:"", modifier = Modifier
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
        strMeasures: MutableList<String>
    ){
        ingredients.clear()
        measures.clear()

        strIngredients.forEach{if(it != "") ingredients.add(it)}
        strMeasures.forEach { if(it != "") measures.add(it) }

        meal = MealUser(idMeal, strMeal, strCategory, strArea, strInstructions, strMealThumb, strTags, strYoutube, ingredients, measures, "")

    }



    fun changeMealName(name: String){
        mealName = name
    }

    fun changeshowOutLineText(result: Boolean){
        showOutLineText = !result
    }

    fun changeViewCenter(result: Boolean){
        showViewCenter = result
    }

}