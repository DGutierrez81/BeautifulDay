@file:Suppress("DEPRECATION")

package com.project.beautifulday.Components

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.R

/**
 * Composable para la cámara de captura de fotos y vídeos.
 *
 * @param navController Controlador de navegación de Compose.
 * @param viewmodelA ViewModel de la aplicación.
 * @param context Contexto de la actividad.
 * @param email Correo electrónico del usuario.
 * @param numb Número de identificación.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Camera(navController: NavController, viewmodelA: ViewmodelAplication, context: ComponentActivity, email: String, numb: String){


    var uri: Uri? by rememberSaveable{ mutableStateOf(null) }
    var showDialog: Boolean by remember{ mutableStateOf(false) }
    var showDialog2: Boolean by remember{ mutableStateOf(false) }
    val resultUri = viewmodelA.resultUri
    val uriFoto = viewmodelA.uriFoto
    val uriVideo = viewmodelA.uriVideo
    val loading: Boolean by viewmodelA.isLoading.collectAsState()
    val namePhoto = viewmodelA.namePhoto
    val focusRequester = viewmodelA.focusRequest
    val focusManager = LocalFocusManager.current

    val metada = viewmodelA.metada

    val intentCameraLaucher = viewmodelA.intentCameraLaucher(uri, focusManager, email)

    val intentCameraLaucherVideo = viewmodelA.intentCameraLaucherVideo(result = uri, focusManager = focusManager, email)


    if(showDialog){
        Dialog(onDismissRequest = { showDialog2 = false }) {
            Card(shape = RoundedCornerShape(12), modifier = Modifier.background(color = Color.Transparent)) {
                Column(modifier = Modifier
                    //.padding(24.dp)
                    .background(color = colorResource(id = R.color.paynesGray))) {
                    OutlinedButton(onClick = {
                        uri = viewmodelA.generateUri(context, namePhoto, "jpg")
                        intentCameraLaucher.launch(uri)
                        showDialog = false
                    }, modifier = Modifier
                        .fillMaxWidth()
                        //.padding(horizontal = 8.dp)
                        .align(Alignment.CenterHorizontally),
                        //.background(color = colorResource(id = R.color.electricBlue)),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.silver)),
                        border = BorderStroke(2.dp, colorResource(id = R.color.paynesGray))
                    ) {
                        Text(text = "Foto", color = colorResource(id = R.color.paynesGray))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(onClick = {
                        uri = viewmodelA.generateUri(context, namePhoto, "mp4")
                        intentCameraLaucherVideo.launch(uri)
                        showDialog = false
                    }, modifier = Modifier
                        .fillMaxWidth()
                        //.padding(horizontal = 8.dp)
                        .align(Alignment.CenterHorizontally),
                        //.background(color = colorResource(id = R.color.paynesGray)),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.silver)),
                        border = BorderStroke(2.dp, colorResource(id = R.color.paynesGray))
                    ) {
                        Text(text = "Video", color = colorResource(id = R.color.paynesGray))
                    }
                }
            }

        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(id = R.color.electricBlue))){
        Spacer(modifier = Modifier.height(16.dp))
        Card(elevation = CardDefaults.cardElevation(10.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(colorResource(id = R.color.selectiveYellow)),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(horizontal = 36.dp)
                .shadow(
                    elevation = 25.dp,
                    ambientColor = colorResource(id = R.color.paynesGray),
                    spotColor = colorResource(id = R.color.silver),
                    shape = RoundedCornerShape(20.dp)
                )){
            if(uriFoto == resultUri.toString()){
                AsyncImage(model = resultUri, contentDescription = "image selected by user", contentScale = ContentScale.Crop)

            }
            if(uriVideo == resultUri.toString()){

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Icon(
                        painterResource(id = R.drawable.video_camera_back),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        tint = colorResource(id = R.color.paynesGray)
                    )
                }

            }

            if(loading){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator(modifier = Modifier.size(50.dp), color = colorResource(
                        id = R.color.paynesGray
                    )
                    )
                }
            }



            if(!loading && resultUri == null){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Icon(
                        painterResource(id = R.drawable.ic_image),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        tint = colorResource(id = R.color.paynesGray)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Column {
                FloatingActionButton(onClick = {
                    showDialog = true
                    viewmodelA.choose(metada)
                    viewmodelA.changeIsLoading(false)
                },
                    containerColor = colorResource(R.color.paynesGray),
                    contentColor = colorResource(id = R.color.electricBlue)
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_camera), contentDescription = "icono cámara" )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        TextField(value = namePhoto, onValueChange = {viewmodelA.changeNamePhoto(it)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 36.dp)
                .border(
                    2.dp,
                    color = colorResource(id = R.color.paynesGray),
                    RoundedCornerShape(22)
                )
                .focusRequester(focusRequester),

            colors = TextFieldDefaults.textFieldColors(

                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
            shape = RoundedCornerShape(22)
        )
        Spacer(modifier = Modifier.weight(1f))
        OutlinedButton(onClick = { navController.navigate("registroM/$email?numb=$numb") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 36.dp)
                .align(Alignment.CenterHorizontally),
            border = BorderStroke(2.dp, colorResource(id = R.color.paynesGray)),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = colorResource(id = R.color.paynesGray),
                containerColor = colorResource(id = R.color.selectiveYellow)
            )
        ) {
            Text(text = "Lista de fotos", color = colorResource(id = R.color.paynesGray))
        }
        Spacer(modifier = Modifier.weight(1f))
        OutlinedButton(onClick = {
            if(numb == "1"){
                navController.navigate("createNewMeal")
            }else navController.navigate("createNewLocal")
                                 },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 36.dp)
                .align(Alignment.CenterHorizontally),
            border = BorderStroke(2.dp, colorResource(id = R.color.paynesGray)),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = colorResource(id = R.color.paynesGray),
                containerColor = colorResource(id = R.color.selectiveYellow)
            )
        ) {
            Text(text = "Enviar datos", color = colorResource(id = R.color.paynesGray))
        }

        Spacer(modifier = Modifier.weight(2f))
    }

}

