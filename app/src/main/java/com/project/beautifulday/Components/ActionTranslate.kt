package com.project.beautifulday.Components

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.project.beautifulday.Meal.ui.States.Traduction
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.ViewmodelAplication


/**
 * Función composable para manejar la traducción de texto.
 *
 * @param actionTranslate Indicador de si se debe traducir el texto.
 * @param text El texto a traducir.
 * @param viewmodelA El ViewModel que maneja las acciones de traducción.
 * @param context El contexto de la actividad actual.
 * @param state El estado de traducción que contiene el texto a ser traducido y el texto traducido.
 */
@Composable
fun ActionTransalate(
    actionTranslate: Boolean,
    text: String,
    viewmodelA: ViewmodelAplication,
    context: ComponentActivity,
    state: Traduction
) {
    if (actionTranslate) {
        // Si se requiere traducir el texto
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            color = colorResource(id = R.color.silver),
            textAlign = TextAlign.Center
        )

        // Actualizar el texto a ser traducido en el ViewModel
        viewmodelA.onTextToBeTranslatedChange(text)

        // Solicitar la traducción desde el ViewModel cuando se hace clic en el botón
        viewmodelA.onTranslateButtonClick(
            text = state.textToBeTranslated,
            context = context
        )
    } else {
        // Si el texto ya está traducido, mostrar el texto traducido
        state.translatedText.let { translatedText ->
            val processedText = translatedText.replace(":", "\n")
            Text(
                text = processedText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                color = colorResource(id = R.color.silver),
                textAlign = TextAlign.Center
            )
        }
    }
}

