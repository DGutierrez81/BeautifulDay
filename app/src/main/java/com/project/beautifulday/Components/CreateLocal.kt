package com.project.beautifulday.Components

import android.Manifest
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
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
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.CocktailViewmodel
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.androidsmall1.jotiOne

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CreateLocal(navController: NavController, viewmodel: MealViewmodel, viewmodelA: ViewmodelAplication, context: ComponentActivity, cocktailViewmodel: CocktailViewmodel){

    val name = viewmodelA.name
    val comentario = viewmodelA.descripcion
    val foto = viewmodelA.uriFoto
    val web = viewmodelA.web
    val pais = viewmodelA.pais
    val ciudad = viewmodelA.ciudad
    val focusRequester = viewmodelA.focusRequest
    val screen = viewmodelA.screen
    val showCreateAlert = viewmodelA.showCreateAlert
    val intentGalleryLancher = viewmodelA.intentGalleryLaucher()
    val focusManager = LocalFocusManager.current
    val savedLocation by viewmodelA.savedLocation.collectAsState()
    val location by viewmodelA.location.collectAsState()

    val locationPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    LaunchedEffect(key1 = true) {
        viewmodelA.fetchUser()
        if (locationPermissionState.hasPermission) {
            viewmodelA.requestLocation(context)
        } else {
            locationPermissionState.launchPermissionRequest()
        }
    }

    val user = viewmodelA.user



    Column {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.electricBlue)),
            contentAlignment = Alignment.TopCenter
        ){
            Column(

            ) {

                Text(
                    text = "Beautiful",
                    fontSize = 32.sp,
                    fontFamily = jotiOne,
                    color = colorResource(id = R.color.selectiveYellow),
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                MyOutlinedTectField(
                    value = name,
                    onValueChange = viewmodelA::changeName, //Equivalente a {newName -> viewmodelA.changeName(newName)}
                    focusRequester = focusRequester,
                    label = "Nombre",
                    keyboardActions = {focusManager.moveFocus(FocusDirection.Down)}
                )

                Spacer(modifier = Modifier.padding(2.dp))

                MyOutlinedTectField(
                    value = comentario,
                    onValueChange = viewmodelA::changeDescripcion,
                    focusRequester = focusRequester,
                    label = "Descripción",
                    keyboardActions = {focusManager.moveFocus(FocusDirection.Down)}
                )


                Spacer(modifier = Modifier.padding(2.dp))

                MyOutlinedTectField(
                    value = web,
                    onValueChange = viewmodelA::changeWeb,
                    focusRequester = focusRequester,
                    label = "Web",
                    keyboardActions = {focusManager.moveFocus(FocusDirection.Down)}
                )

                Spacer(modifier = Modifier.padding(2.dp))

                MyOutlinedTectField(
                    value = pais,
                    onValueChange = viewmodelA::changePais, //Equivalente a {newName -> viewmodelA.changeName(newName)}
                    focusRequester = focusRequester,
                    label = "Pais",
                    keyboardActions = {focusManager.moveFocus(FocusDirection.Down)}
                )

                Spacer(modifier = Modifier.padding(2.dp))

                MyOutlinedTectField(
                    value = ciudad,
                    onValueChange = viewmodelA::changeCiudad, //Equivalente a {newName -> viewmodelA.changeName(newName)}
                    focusRequester = focusRequester,
                    label = "Ciudad",
                    keyboardActions = {focusManager.moveFocus(FocusDirection.Down)}
                )

                Spacer(modifier = Modifier.padding(2.dp))

                Column {
                    MyOutlinedTectField(
                        value = foto,
                        onValueChange = viewmodelA::changeUriFoto,
                        focusRequester = focusRequester,
                        label = "Foto",
                        keyboardActions = {focusManager.moveFocus(FocusDirection.Down)}
                    )
                    Row {
                        Text(text = "Buscar en galeria", modifier = Modifier
                            .clickable { intentGalleryLancher.launch("*/*") }
                            .padding(start = 36.dp))
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }
                }

                Spacer(modifier = Modifier.padding(2.dp))

                Box(modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center){
                    OutlinedButton(onClick = {
                        location?.let {
                            viewmodelA.saveLocation(it)
                            Toast.makeText(context, "Ubicación guardada", Toast.LENGTH_SHORT).show()
                        } ?: run {
                            Toast.makeText(context, "Ubicación no disponible", Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Text(text = "Obtener ubicación")

                    }
                }

                Spacer(modifier = Modifier.padding(15.dp))
            }
        }

        Box(modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.selectiveYellow)),
            contentAlignment = Alignment.TopCenter){
            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "DAY",
                    fontFamily = jotiOne,
                    fontSize = 32.sp,
                    color = colorResource(id = R.color.electricBlue)
                )

                Spacer(modifier = Modifier.height(2.dp))

                AsyncImage(model = R.drawable.logo, contentDescription = null, modifier = Modifier.clickable {
                    navController.navigate("principal")
                    viewmodelA.clean()
                })
                Spacer(modifier = Modifier.weight(1f))
                Row (modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.Bottom){
                    Text(text = "Hacer foto y video", modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            val numb = 2
                            navController.navigate("camera/${user.email}?numb=2")
                        },
                        color = colorResource(id = R.color.paynesGray)
                    )
                    Text(text = "Enviar datos", modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            createAlertLocal(
                                navController = navController,
                                viewmodelA = viewmodelA,
                                viewmodel = viewmodel,
                                cocktailViewmodel = cocktailViewmodel,
                                user = user.userName ?: "",
                                context = context
                            )
                        },
                        color = colorResource(id = R.color.paynesGray)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(2f))

        CreateDialog(
            showAlert = showCreateAlert,
            tittle = "Aviso",
            text = "Tiene registros sin rellenar\n¿Desea seguir?",
            onDismiss = { viewmodelA.changeCreateAlerte(!showCreateAlert) }) {
            viewmodelA.newLocal(name, foto, comentario = comentario, pais = pais, ciudad = ciudad,"https://$web", savedLocation)
            viewmodelA.saveNewLocal(
                colec = "Locales $screen",
                context = context,
                { navController.navigate("ok") }) {
                Toast
                    .makeText(
                        context,
                        "Local guardado correctamente",
                        Toast.LENGTH_SHORT
                    )
                    .show()
                viewmodelA.clean()
                navController.navigate(screen)
                viewmodelA.changeCreateAlerte(false)
            }
        }
    }
}