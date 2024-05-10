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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import com.google.relay.compose.ColumnScopeInstanceImpl.weight
import com.project.beautifulday.Meal.MealUseCases.UseCaseMealName
import com.project.beautifulday.Meal.ui.States.MealState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealViewmodel@Inject constructor(private val useCaseMealName: UseCaseMealName): ViewModel() {

    private val _mealsData = MutableStateFlow<List<MealState>>(emptyList())
    val mealsData: StateFlow<List<MealState>> = _mealsData

    var mealName by mutableStateOf("")

    var showOutLineText by mutableStateOf(false)

    fun getMealName(name: String){
        viewModelScope.launch {
            _mealsData.value = useCaseMealName(name).meals?: mutableListOf()
        }
    }

    @Composable
    fun ShowMealsName(meals: List<MealState>){
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
                            GetImages(meal = item)
                            GetImages(meal = nextItem)
                        }
                    }
                }
            }
        }
    }
    
    @Composable
    fun GetImages(meal: MealState?){
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

                            }
                    )
                    Text(
                        text = meal?.strMeal?:"", modifier = Modifier
                            .height(75.dp)
                            .width(120.dp)
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    fun changeMealName(name: String){
        mealName = name
    }

    fun changeshowOutLineText(result: Boolean){
        showOutLineText = !result
    }
}