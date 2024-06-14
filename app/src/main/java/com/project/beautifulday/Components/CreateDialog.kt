package com.project.beautifulday.Components



import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.project.beautifulday.R


/**
 * Crea un diálogo de alerta personalizado que se muestra o se oculta según el estado de [showAlert].
 *
 * @param showAlert Indica si se debe mostrar el diálogo de alerta.
 * @param tittle El título del diálogo de alerta.
 * @param text El texto del cuerpo del diálogo de alerta.
 * @param onDismiss La acción a realizar cuando se descarta el diálogo de alerta.
 * @param onConfirm La acción a realizar cuando se confirma el diálogo de alerta.
 */
@Composable
fun CreateDialog(
    showAlert: Boolean,
    tittle: String,
    text: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showAlert) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text(text = "Si", color = colorResource(id = R.color.silver))
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(text = "No", color = colorResource(id = R.color.electricBlue))
                }
            },
            title = { Text(text = tittle, color = colorResource(id = R.color.silver))},
            text = { Text(text = text, color = colorResource(id = R.color.silver))},
            icon = { AsyncImage(model = R.drawable.logo, contentDescription = null) },
            shape = RoundedCornerShape(50.dp),
            containerColor = colorResource(id = R.color.paynesGray),
            modifier = Modifier.background(Color.Transparent)
        )
    }
}
