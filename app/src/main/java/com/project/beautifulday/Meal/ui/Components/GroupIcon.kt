package com.project.beautifulday.Meal.ui.Components

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
import com.project.beautifulday.Meal.ui.ViewModels.LogViewmodel
import com.project.beautifulday.R

@Composable
fun GroupIcon(order: Int, navController: NavController, LgViewModel: LogViewmodel){
    when(order){
        1 -> {
            Icon(
                painter = painterResource(id = R.drawable.inicio2_key),
                contentDescription = null,
                tint = colorResource(id = R.color.silver),
                modifier = Modifier.clickable { navController.navigate("login") }
            )
            Icon(
                painter = painterResource(id = R.drawable.inicio2_plus),
                contentDescription = null,
                tint = colorResource(id = R.color.silver),
                modifier = Modifier.clickable { navController.navigate("register") }
            )
        }
        2 -> {
            Icon(
                painter = painterResource(id = R.drawable.inicio2_key),
                contentDescription = null,
                tint = colorResource(id = R.color.silver),
                modifier = Modifier.clickable { navController.navigate("login") }
            )
            Icon(
                painter = painterResource(id = R.drawable.inicio2_plus),
                contentDescription = null,
                tint = colorResource(id = R.color.silver),
                modifier = Modifier.clickable { navController.navigate("register") }
            )
            AsyncImage(model = R.drawable.glass,
                contentDescription = null,
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp))
        }
        3 -> {
            Icon(
                painter = painterResource(id = R.drawable.android_small_1_vectorete),
                contentDescription = null,
                tint = colorResource(id = R.color.silver),
                modifier = Modifier.clickable {
                    LgViewModel.logOut(){
                        LgViewModel.clean()
                        navController.navigate("principal")
                    }
                }
            )
            AsyncImage(model = R.drawable.glass,
                contentDescription = null,
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp))
        }
        4 -> {
            Icon(
                painter = painterResource(id = R.drawable.inicio2_key),
                contentDescription = null,
                tint = colorResource(id = R.color.silver),
                modifier = Modifier.clickable { navController.navigate("login") }
            )
            Icon(
                painter = painterResource(id = R.drawable.inicio2_plus),
                contentDescription = null,
                tint = colorResource(id = R.color.silver),
                modifier = Modifier.clickable { navController.navigate("register") }
            )
            AsyncImage(model = R.drawable.olla,
                contentDescription = null,
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp))
        }
        5 -> {
            Icon(
                painter = painterResource(id = R.drawable.android_small_1_vectorete),
                contentDescription = null,
                tint = colorResource(id = R.color.silver),
                modifier = Modifier.clickable {
                    LgViewModel.logOut(){
                        LgViewModel.clean()
                        navController.navigate("principal")
                    }
                }
            )
            AsyncImage(model = R.drawable.olla,
                contentDescription = null,
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp))
        }
        6 -> {
            Icon(
                painter = painterResource(id = R.drawable.inicio2_vector),
                contentDescription = null,
                tint = colorResource(id = R.color.silver),
                modifier = Modifier.clickable {
                    //LgViewModel.creauteUser(LgViewModel.email, LgViewModel.password) {navController.navigate("principal")}
                    LgViewModel.login(LgViewModel.email, LgViewModel.password) {navController.navigate("principal")}
                }
            )
        }
        7 -> {
            Icon(
                painter = painterResource(id = R.drawable.inicio2_vector),
                contentDescription = null,
                tint = colorResource(id = R.color.silver),
                modifier = Modifier.clickable {
                    LgViewModel.creauteUser(LgViewModel.email, LgViewModel.password) {navController.navigate("principal")}
                    //LgViewModel.login(LgViewModel.email, LgViewModel.password) {navController.navigate("principal")}
                }
            )
        }
    }
}