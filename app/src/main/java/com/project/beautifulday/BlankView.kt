package com.project.beautifulday

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.project.beautifulday.Meal.ui.ViewModels.LogViewmodel

@Composable
fun BlankView(navController: NavController, LgViewModel: LogViewmodel){

    LaunchedEffect(key1 = true){
        if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
            LgViewModel.changeLogin(true)
            navController.navigate("principal")
        }else{
            navController.navigate("principal")
        }
    }

}