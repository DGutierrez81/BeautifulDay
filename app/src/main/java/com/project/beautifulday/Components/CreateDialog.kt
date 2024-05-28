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


@Composable
fun CreateDialog(
    showAlert: Boolean,
    tittle: String,
    text: String,
    onDismiss: () -> Unit,
    onConfirm: ()-> Unit
){

    if(showAlert){
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = { TextButton(onClick = { onConfirm() }) {
                Text(text = "Si")
            }},
            icon = { AsyncImage(model = R.drawable.logo, contentDescription = null) },
            shape = RoundedCornerShape(50.dp),
            dismissButton = { TextButton(onClick = { onDismiss() }) {
                Text(text = "No", color = colorResource(id = R.color.silver))

            }
            },
            title = { Text(text = tittle)},
            text= { Text(text = text)},
            containerColor = colorResource(id = R.color.paynesGray),
            modifier = Modifier.background(Color.Transparent)
        )
    }

}