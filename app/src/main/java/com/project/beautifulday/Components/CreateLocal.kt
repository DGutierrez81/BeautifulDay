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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
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

/**
 * Esta función representa la pantalla de creación local.
 *
 * @param navController El controlador de navegación utilizado para navegar entre las pantallas de la aplicación.
 * @param viewmodel El ViewModel utilizado para manejar los datos relacionados con las comidas.
 * @param viewmodelA El ViewModel de la aplicación que contiene datos globales y métodos útiles para la aplicación.
 * @param context El contexto de la actividad componente.
 * @param cocktailViewmodel El ViewModel utilizado para manejar los datos relacionados con los cócteles.
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CreateLocal(navController: NavController, viewmodel: MealViewmodel, viewmodelA: ViewmodelAplication, context: ComponentActivity, cocktailViewmodel: CocktailViewmodel) {

    // Obtiene los datos del ViewModel de la aplicación
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

    // Estado de los permisos de ubicación
    val locationPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    // Solicita los datos del usuario y la ubicación al ViewModel de la aplicación
    LaunchedEffect(key1 = true) {
        viewmodelA.fetchUser()
        if (locationPermissionState.hasPermission) {
            viewmodelA.requestLocation(context)
        } else {
            locationPermissionState.launchPermissionRequest()
        }
    }

    // Obtiene los datos del usuario del ViewModel de la aplicación
    val user = viewmodelA.user

    // Muestra la pantalla de creación local
    Scaffold(
        topBar = {
            // Barra superior de la pantalla
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.electricBlue)),
                contentAlignment = Alignment.TopCenter
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
            }
        },
        bottomBar = {
            // Barra inferior de la pantalla
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.selectiveYellow)),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Texto "DAY"
                    Text(
                        text = "DAY",
                        fontFamily = jotiOne,
                        fontSize = 32.sp,
                        color = colorResource(id = R.color.electricBlue)
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    // Imagen del logotipo de la aplicación
                    AsyncImage(
                        model = R.drawable.logo,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            navController.navigate("principal")
                            viewmodelA.clean()
                        })

                    // Botones para acciones de la aplicación
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        // Botón para hacer foto y video
                        Text(
                            text = "Hacer foto y video",
                            modifier = Modifier
                                .padding(10.dp)
                                .clickable {
                                    val numb = 2
                                    navController.navigate("camera/${user.email}?numb=2")
                                },
                            color = colorResource(id = R.color.paynesGray)
                        )
                        // Botón para enviar datos
                        Text(
                            text = "Enviar datos",
                            modifier = Modifier
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
        }
    ) { innerPadding ->
        // Columna verticalmente desplazable
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colorResource(id = R.color.electricBlue))
        ) {
            // Elementos de la columna
            item {
                // Campo de texto con borde personalizado para el nombre
                MyOutlinedTectField(
                    value = name,
                    onValueChange = viewmodelA::changeName, // Equivalente a {newName -> viewmodelA.changeName(newName)}
                    focusRequester = focusRequester,
                    label = "Nombre",
                    keyboardActions = { focusManager.moveFocus(FocusDirection.Down) }
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Campo de texto con borde personalizado para la descripción
                MyOutlinedTectField(
                    value = comentario,
                    onValueChange = viewmodelA::changeDescripcion,
                    focusRequester = focusRequester,
                    label = "Descripción",
                    keyboardActions = { focusManager.moveFocus(FocusDirection.Down) }
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Campo de texto con borde personalizado para la página web
                MyOutlinedTectField(
                    value = web,
                    onValueChange = viewmodelA::changeWeb,
                    focusRequester = focusRequester,
                    label = "Web",
                    keyboardActions = { focusManager.moveFocus(FocusDirection.Down) }
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Campo de texto con borde personalizado para el país
                MyOutlinedTectField(
                    value = pais,
                    onValueChange = viewmodelA::changePais, // Equivalente a {newName -> viewmodelA.changeName(newName)}
                    focusRequester = focusRequester,
                    label = "País",
                    keyboardActions = { focusManager.moveFocus(FocusDirection.Down) }
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Campo de texto con borde personalizado para la ciudad
                MyOutlinedTectField(
                    value = ciudad,
                    onValueChange = viewmodelA::changeCiudad, // Equivalente a {newName -> viewmodelA.changeName(newName)}
                    focusRequester = focusRequester,
                    label = "Ciudad",
                    keyboardActions = { focusManager.moveFocus(FocusDirection.Down) }
                )

                Spacer(modifier = Modifier.height(2.dp))

                Column {
                    // Campo de texto con borde personalizado para la foto del local
                    MyOutlinedTectField(
                        value = foto,
                        onValueChange = viewmodelA::changeUriFoto,
                        focusRequester = focusRequester,
                        label = "Foto",
                        keyboardActions = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                    // Fila que contiene el texto "Buscar en galería" y un ícono de búsqueda
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Buscar en galería",
                            modifier = Modifier
                                .clickable { intentGalleryLancher.launch("*/*") }
                                .padding(start = 36.dp)
                        )
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    // Botón para obtener la ubicación del local
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        OutlinedButton(onClick = {
                            location?.let {
                                viewmodelA.saveLocation(it)
                                Toast.makeText(context, "Ubicación guardada", Toast.LENGTH_SHORT)
                                    .show()
                            } ?: run {
                                Toast.makeText(
                                    context,
                                    "Ubicación no disponible",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }) {
                            Text(text = "Obtener ubicación")
                        }
                    }

                    Spacer(modifier = Modifier.height(15.dp))
                }

// Diálogo de creación
                CreateDialog(
                    showAlert = showCreateAlert,
                    tittle = "Aviso",
                    text = "Tiene registros sin rellenar\n¿Desea seguir?",
                    onDismiss = { viewmodelA.changeCreateAlerte(!showCreateAlert) }) {
                    // Acciones a realizar al crear un nuevo local
                    viewmodelA.newLocal(
                        name,
                        foto,
                        comentario = comentario,
                        pais = pais,
                        ciudad = ciudad,
                        nameUser = user.userName ?: "",
                        "https://$web",
                        savedLocation
                    )
                    viewmodelA.saveNewLocal(
                        colec = "Locales $screen",
                        context = context,
                        onOk = { navController.navigate("ok") },
                        onSuccess = {
                            Toast.makeText(
                                context,
                                "Local guardado correctamente",
                                Toast.LENGTH_SHORT
                            ).show()
                            viewmodelA.clean()
                            navController.navigate(screen)
                            viewmodelA.changeCreateAlerte(false)
                        }
                    )
                }
            }
        }
    }
}
