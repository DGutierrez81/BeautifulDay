package com.project.beautifulday.Components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

/**
 * Composable que muestra un campo de texto con borde y etiqueta.
 *
 * @param value Valor actual del campo de texto.
 * @param onValueChange Función de cambio de valor del campo de texto.
 * @param focusRequester Solicitante de enfoque para el campo de texto.
 * @param label Etiqueta del campo de texto.
 * @param keyboardActions Acciones de teclado del campo de texto.
 */
@Composable
fun MyOutlinedTectField(
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    label: String,
    keyboardActions: () -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 36.dp)
            .focusRequester(focusRequester),
        shape = RoundedCornerShape(22),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        label = { Text(text = label) },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = { keyboardActions() })
    )
}


