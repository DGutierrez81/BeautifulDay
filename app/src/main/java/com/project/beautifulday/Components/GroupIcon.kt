package com.project.beautifulday.Components

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.ViewmodelAplication

/**
 * Muestra un grupo de iconos según el orden proporcionado, permitiendo la navegación y acciones específicas.
 *
 * @param order El orden del grupo de iconos.
 * @param navController El controlador de navegación.
 * @param LgViewModel El ViewModel de inicio de sesión.
 * @param viewmodelA El ViewModel de la aplicación.
 */
@Composable
fun GroupIcon(
    order: Int,
    navController: NavController,
    LgViewModel: LogViewmodel,
    viewmodelA: ViewmodelAplication
) {
    when (order) {
        1 -> {
            // Icono para iniciar sesión
            Icon(
                painter = painterResource(id = R.drawable.inicio2_key),
                contentDescription = null,
                tint = colorResource(id = R.color.silver),
                modifier = Modifier.clickable { navController.navigate("login") }
            )
            // Icono para registrarse
            Icon(
                painter = painterResource(id = R.drawable.inicio2_plus),
                contentDescription = null,
                tint = colorResource(id = R.color.silver),
                modifier = Modifier.clickable { navController.navigate("register") }
            )
        }
        2 -> {
            // Icono para iniciar sesión
            Icon(
                painter = painterResource(id = R.drawable.inicio2_key),
                contentDescription = null,
                tint = colorResource(id = R.color.silver),
                modifier = Modifier.clickable {
                    LgViewModel.clean()
                    navController.navigate("login")
                }
            )
            // Icono para registrarse
            Icon(
                painter = painterResource(id = R.drawable.inicio2_plus),
                contentDescription = null,
                tint = colorResource(id = R.color.silver),
                modifier = Modifier.clickable {
                    LgViewModel.clean()
                    navController.navigate("register")
                }
            )
            // Icono para ver cócteles
            AsyncImage(
                model = R.drawable.glass,
                contentDescription = null,
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
                    .clickable {
                        viewmodelA.clean()
                        viewmodelA.changeScreen("cocktail")
                        navController.navigate("cocktail")
                    }
            )
        }
        3 -> {
            // Icono para cerrar sesión
            Icon(
                painter = painterResource(id = R.drawable.android_small_1_vectorete),
                contentDescription = null,
                tint = colorResource(id = R.color.silver),
                modifier = Modifier.clickable {
                    LgViewModel.logOut {
                        LgViewModel.clean()
                        navController.navigate("principal") {
                            // Limpia la pila de navegación hasta el destino inicial
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                            // Esto asegura que la pantalla principal sea la única en la pila de backstack
                            launchSingleTop = true
                        }
                    }
                }
            )
            // Icono para ver cócteles
            AsyncImage(
                model = R.drawable.glass,
                contentDescription = null,
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
                    .clickable {
                        viewmodelA.clean()
                        viewmodelA.changeScreen("cocktail")
                        navController.navigate("cocktail")
                    }
            )
        }
        4 -> {
            // Icono para iniciar sesión
            Icon(
                painter = painterResource(id = R.drawable.inicio2_key),
                contentDescription = null,
                tint = colorResource(id = R.color.silver),
                modifier = Modifier.clickable { navController.navigate("login") }
            )
            // Icono para registrarse
            Icon(
                painter = painterResource(id = R.drawable.inicio2_plus),
                contentDescription = null,
                tint = colorResource(id = R.color.silver),
                modifier = Modifier.clickable { navController.navigate("register") }
            )
            // Icono para ver comidas
            AsyncImage(
                model = R.drawable.olla,
                contentDescription = null,
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
                    .clickable {
                        viewmodelA.clean()
                        viewmodelA.changeScreen("meal")
                        navController.navigate("meal")
                    }
            )
        }
        5 -> {
            // Icono para cerrar sesión
            Icon(
                painter = painterResource(id = R.drawable.android_small_1_vectorete),
                contentDescription = null,
                tint = colorResource(id = R.color.silver),
                modifier = Modifier.clickable {
                    LgViewModel.logOut {
                        LgViewModel.clean()
                        navController.navigate("principal") {
                            // Limpia la pila de navegación hasta el destino inicial
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                            // Esto asegura que la pantalla principal sea la única en la pila de backstack
                            launchSingleTop = true
                        }
                    }
                }
            )
            // Icono para ver comidas
            AsyncImage(
                model = R.drawable.olla,
                contentDescription = null,
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
                    .clickable {
                        viewmodelA.clean()
                        viewmodelA.changeScreen("meal")
                        navController.navigate("meal")
                    }
            )
        }
        6 -> {
            // Icono para iniciar sesión mediante correo electrónico y contraseña
            Icon(
                painter = painterResource(id = R.drawable.inicio2_vector),
                contentDescription = null,
                tint = colorResource(id = R.color.silver),
                modifier = Modifier.clickable {
                    LgViewModel.login(LgViewModel.email, LgViewModel.password) { navController.navigate("principal") }
                }
            )
        }
        7 -> {
            // Icono para crear una nueva cuenta de usuario
            Icon(
                painter = painterResource(id = R.drawable.inicio2_vector),
                contentDescription = null,
                tint = colorResource(id = R.color.silver),
                modifier = Modifier.clickable {
                    LgViewModel.creauteUser(LgViewModel.email, LgViewModel.password) { navController.navigate("principal") }
                }
            )
        }
        8 -> {
            // Icono para cerrar sesión y regresar a la pantalla principal
            Icon(
                painter = painterResource(id = R.drawable.android_small_1_vectorete),
                contentDescription = null,
                tint = colorResource(id = R.color.silver),
                modifier = Modifier.clickable {
                    LgViewModel.clean()
                    navController.navigate("principal") {
                        // Limpia la pila de navegación hasta el destino inicial
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        // Esto asegura que la pantalla principal sea la única en la pila de backstack
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
