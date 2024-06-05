package com.project.beautifulday.Meal.ui

import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.beautifulday.Components.ActionTransalate
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.R
import com.project.beautifulday.androidsmall1.jotiOne


/**
 * Composable que muestra una lista de categorías de comidas y sus detalles.
 *
 * @param navController Controlador de navegación utilizado para gestionar la navegación entre pantallas.
 * @param viewmodel ViewModel de tipo MealViewmodel que maneja los datos y la lógica relacionada con las comidas.
 * @param context Contexto de la actividad actual, utilizado para operaciones relacionadas con la UI.
 * @param viewmodelA ViewModel de tipo ViewmodelAplication que maneja la lógica general de la aplicación.
 */
@Composable
fun ListCategory(
    navController: NavController,
    viewmodel: MealViewmodel,
    context: ComponentActivity,
    viewmodelA: ViewmodelAplication
) {
    // Observa los datos de categorías de comidas del ViewModel
    val mealCategory by viewmodel.categoyData.collectAsState()
    // Observa el estado de actionTranslate del ViewmodelAplication
    val actionTranslate by viewmodelA.actionTranslate.observeAsState(true)
    // Obtiene el estado actual del ViewmodelAplication
    val state = viewmodelA.state.value
    // Observa el estado del slide del ViewmodelAplication
    val slide by viewmodelA.slide.observeAsState(false)
    // Obtiene la categoría actual del ViewModel
    val catagoria = viewmodel.categoria
    // Observa el estado de progreso del ViewModel
    val progrees by viewmodel.progress.observeAsState(true)

    // Columna que contiene el contenido principal de la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa todo el tamaño disponible
            .background(color = colorResource(id = R.color.paynesGray)), // Fondo gris
        horizontalAlignment = Alignment.CenterHorizontally // Alineación horizontal centrada
    ) {
        // Itera sobre las categorías de comidas
        for (category in mealCategory) {
            // Si la categoría actual coincide con la seleccionada, muestra los detalles
            if (category.strCategory == catagoria) {
                // Muestra el nombre de la categoría
                Text(
                    text = category.strCategory ?: "",
                    modifier = Modifier
                        .fillMaxWidth() // Ocupa todo el ancho disponible
                        .padding(5.dp), // Padding de 5 dp
                    fontFamily = jotiOne, // Fuente personalizada
                    fontSize = 24.sp, // Tamaño de fuente 24 sp
                    color = colorResource(id = R.color.silver), // Color plateado
                    textAlign = TextAlign.Center // Texto centrado
                )
                // Tarjeta que contiene la imagen y descripción de la categoría
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .background(colorResource(id = R.color.paynesGray)) // Fondo gris
                    ) {
                        // Si está en progreso, muestra un indicador de carga
                        if (progrees) {
                            Box(
                                Modifier
                                    .fillMaxWidth() // Ocupa todo el ancho disponible
                                    .height(300.dp), // Altura de 300 dp
                                contentAlignment = Alignment.Center // Alineación centrada
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally // Alineación horizontal centrada
                                ) {
                                    CircularProgressIndicator(color = colorResource(id = R.color.silver)) // Indicador de progreso plateado
                                    Spacer(modifier = Modifier.padding(3.dp)) // Espaciador
                                    Text(
                                        text = "Cargando" + viewmodelA.getAnimatedDots(progrees), // Texto de carga con puntos animados
                                        color = colorResource(id = R.color.silver) // Color plateado
                                    )
                                }
                            }
                        } else {
                            // Si no está en progreso, muestra la imagen de la categoría
                            AsyncImage(
                                model = category.strCategoryThumb, // URL de la imagen
                                contentDescription = null, // Descripción de contenido
                                modifier = Modifier
                                    .fillMaxWidth() // Ocupa todo el ancho disponible
                                    .height(300.dp), // Altura de 300 dp
                                contentScale = ContentScale.Crop // Escala de contenido recortada
                            )
                        }
                        Spacer(modifier = Modifier.padding(3.dp)) // Espaciador

                        // Lista perezosa que contiene la descripción traducida de la categoría
                        LazyColumn(
                            modifier = Modifier
                                .padding(20.dp) // Padding de 20 dp
                                .height(200.dp), // Altura de 200 dp
                            horizontalAlignment = Alignment.CenterHorizontally // Alineación horizontal centrada
                        ) {
                            item {
                                // Componente que muestra la descripción traducida
                                ActionTransalate(
                                    actionTranslate = actionTranslate, // Estado de traducción
                                    text = category.strCategoryDescription ?: "", // Descripción de la categoría
                                    viewmodelA = viewmodelA, // ViewModel de aplicación
                                    context = context, // Contexto de la actividad
                                    state = state // Estado actual
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    Column {
        // Icono que controla la visibilidad del menú deslizable
        Icon(
            painter = painterResource(id = R.drawable.density), // Icono de densidad
            contentDescription = null, // Descripción de contenido
            tint = colorResource(id = R.color.silver), // Color plateado
            modifier = Modifier.clickable {
                viewmodelA.changeSlide(slide) // Cambia el estado del slide
            }
                .padding(start = 5.dp, top = 8.dp) // Padding de 5 dp desde el inicio y 8 dp desde la parte superior
        )

        // Visibilidad animada del menú deslizable
        AnimatedVisibility(
            visible = slide, // Estado de visibilidad
            enter = slideInHorizontally(), // Animación de entrada deslizante horizontalmente
            exit = slideOutHorizontally() // Animación de salida deslizante horizontalmente
        ) {
            // Columna que contiene las opciones del menú deslizable
            Column(
                modifier = Modifier
                    .padding(3.dp) // Padding de 3 dp
                    .background(colorResource(id = R.color.silver)) // Fondo plateado
            ) {
                // Texto para activar/desactivar la traducción
                Text(
                    text = "Traducir",
                    modifier = Modifier
                        .padding(2.dp) // Padding de 2 dp
                        .clickable {
                            viewmodelA.changeActionTranslate(!actionTranslate) // Cambia el estado de la traducción
                            viewmodelA.changeSlide(slide) // Cierra el menú deslizable
                        },
                    color = colorResource(id = R.color.paynesGray) // Color gris Payne
                )
                // Texto para navegar hacia atrás
                Text(
                    text = "Atras",
                    modifier = Modifier
                        .padding(2.dp) // Padding de 2 dp
                        .clickable {
                            viewmodelA.clean() // Limpia el estado del ViewmodelAplication
                            navController.popBackStack() // Navega hacia atrás
                        },
                    color = colorResource(id = R.color.paynesGray) // Color gris Payne
                )
            }
        }
    }
}
