package com.project.beautifulday.Components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.project.beautifulday.ViewModels.LogViewmodel

/**
 * Composable para una vista en blanco que redirige automáticamente al usuario a la pantalla principal
 * si ya ha iniciado sesión.
 *
 * @param navController Controlador de navegación de Compose.
 * @param LgViewModel ViewModel para el inicio de sesión.
 */
@Composable
fun BlankView(navController: NavController, LgViewModel: LogViewmodel) {
    LaunchedEffect(key1 = true) {
        val isUserLogged = LgViewModel.isUserLogged()
        if (isUserLogged) {
            LgViewModel.changeLogin(true)
            navController.navigate("principal")
        } else {
            navController.navigate("principal")
        }
    }
}
