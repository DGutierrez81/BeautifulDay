package com.project.beautifulday.Components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.project.beautifulday.ViewModels.LogViewmodel

@Composable
fun BlankView(navController: NavController, LgViewModel: LogViewmodel){

    LaunchedEffect(key1 = true){
        val isUserLogged = LgViewModel.isUserLogged()
        if (isUserLogged){
            LgViewModel.changeLogin(true)
            navController.navigate("principal")
        }else{
            navController.navigate("principal")
        }
    }

}