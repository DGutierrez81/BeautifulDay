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
 * Composable function to handle text translation.
 *
 * @param actionTranslate Flag indicating whether to translate the text.
 * @param text The text to be translated.
 * @param viewmodelA The ViewModel that handles translation actions.
 * @param context The current activity context.
 * @param state The translation state containing text to be translated and translated text.
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
        // If text translation is required
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            color = colorResource(id = R.color.silver),
            textAlign = TextAlign.Center
        )

        // Update the text to be translated in the ViewModel
        viewmodelA.onTextToBeTranslatedChange(text)

        // Request translation from the ViewModel when the button is clicked
        viewmodelA.onTranslateButtonClick(
            text = state.textToBeTranslated,
            context = context
        )
    } else {
        // If the text is already translated, display the translated text
        state.translatedText?.let { translatedText ->
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
