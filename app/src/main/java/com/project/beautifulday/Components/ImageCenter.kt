package com.project.beautifulday.Components

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
 * Muestra una imagen centrada con el contenido alineado en la parte inferior.
 *
 * @param model El modelo de la imagen.
 * @param s La descripción de contenido de la imagen.
 */
@Composable
fun ImageCenter(model: Any?, s: String) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = model,
            contentDescription = "Logo",
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
                .padding(bottom = 10.dp),
            contentScale = ContentScale.Crop
        )
    }
}

