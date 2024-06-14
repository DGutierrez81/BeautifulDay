package com.project.beautifulday.Components

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.androidsmall1.jotiOne

/**
 * Composable que muestra el centro de la pantalla.
 *
 * @param navController Controlador de navegación para manejar la navegación entre destinos.
 * @param viewmodelA ViewModel de la aplicación que contiene la lógica de negocio y los estados relacionados con la vista.
 * @param LgViewModel ViewModel para el inicio de sesión que contiene la lógica de autenticación y los estados relacionados con el inicio de sesión.
 * @param showCenter Entero que indica el estado del centro de la pantalla.
 */
@Composable
fun ScreenCenter(
    navController: NavController,
    viewmodelA: ViewmodelAplication,
    LgViewModel: LogViewmodel,
    showCenter: Int,
    context: ComponentActivity
){
    // Columna centrada horizontalmente
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Icono de nube en la esquina superior derecha
        Box(modifier = Modifier
            .fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ){
            Icon(painter = painterResource(id = R.drawable.inicio2_nube),
                contentDescription = null,
                modifier = Modifier.padding(top = 30.dp, end = 50.dp),
                tint = colorResource(id = R.color.white)
            )
        }
        // Texto "Beautiful"
        Text(
            text = "Beautiful",
            fontSize = 32.sp,
            fontFamily = jotiOne,
            color = colorResource(id = R.color.selectiveYellow),
            modifier = Modifier.padding(top = 50.dp)
        )
        // Contenido central variable según el estado
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter){
            Column {
                ViewCenter(showCenter = showCenter, navController = navController, viewmodelA, LgViewModel = LgViewModel, context)
                // Texto "DAY" con estilo
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorResource(id = R.color.selectiveYellow)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "DAY",
                        fontFamily = jotiOne,
                        fontSize = 32.sp,
                        color = colorResource(id = R.color.electricBlue),
                        modifier = Modifier.padding(20.dp)
                    )
                }
            }
        }
    }
}
