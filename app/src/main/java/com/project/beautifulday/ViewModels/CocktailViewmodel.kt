package com.project.beautifulday.ViewModels

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
import com.project.beautifulday.Cocktail.CoktailUseCases.NameUseCase
import com.project.beautifulday.Cocktail.ui.States.CocktailUser
import com.project.beautifulday.Cocktail.ui.States.drinkState
import com.project.beautifulday.Meal.ui.States.MealUser
import com.project.beautifulday.R
import com.project.beautifulday.androidsmall1.jotiOne
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailViewmodel@Inject constructor(private val nameUseCase: NameUseCase): ViewModel() {

    private val _nombre = MutableStateFlow<List<drinkState>>(emptyList())
    val nombre: StateFlow<List<drinkState>> = _nombre.asStateFlow()

    var actionTranslate by mutableStateOf(true)
        private set

    fun getName(name: String) {
        viewModelScope.launch {
            _nombre.value = nameUseCase(name).drinks ?: mutableListOf()
        }
    }


    @Composable
    fun ShowMealsNameUser(coktailData: List<CocktailUser>, navController: NavController, colec: String){
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            itemsIndexed(coktailData){index, item ->
                if(index % 2 == 0){
                    val nextIndex = index + 1
                    val hasNextItem = nextIndex < coktailData.size
                    val nextItem = if(hasNextItem) coktailData[nextIndex] else null

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
                    AsyncImage(model = url, contentDescription = "Meal Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(120.dp)
                            .height(120.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .clickable {
                                navController.navigate("cardMealUser/${cocktail?.idDocument}?colec=$colec")
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

    fun changeActionTranslate(value: Boolean){
        actionTranslate = value
    }
}