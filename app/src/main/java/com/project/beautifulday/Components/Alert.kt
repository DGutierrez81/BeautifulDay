package com.project.beautifulday.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.project.beautifulday.R
import com.project.beautifulday.androidsmall1.jotiOne


/**
 * Composable para mostrar un cuadro de diálogo de alerta personalizado.
 *
 * @param titulo Título del cuadro de diálogo.
 * @param contenido Contenido del cuadro de diálogo.
 * @param onDismiss Función de callback para cerrar el cuadro de diálogo.
 */
@Composable
fun Alert(
    titulo: String,
    contenido: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = titulo,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = jotiOne,
                fontSize = 24.sp,
                color = colorResource(id = R.color.silver)
            )
        },
        text = {
            Text(
                text = contenido,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = jotiOne,
                fontSize = 24.sp,
                color = colorResource(id = R.color.silver)
            )
        },
        confirmButton = { },
        icon = {
            AsyncImage(model = R.drawable.logo, contentDescription = null)
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() },
                colors = ButtonDefaults.buttonColors(
                    contentColor = colorResource(id = R.color.silver)
                )
            ) {
                Text(text = "Salir")
            }
        },
        shape = RoundedCornerShape(50.dp),
        containerColor = colorResource(id = R.color.paynesGray),
        modifier = Modifier.background(Color.Transparent)
    )
}
