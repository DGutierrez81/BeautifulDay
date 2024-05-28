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

@Composable
fun ActionTransalate(actionTranslate: Boolean, text: String, viewmodelA: ViewmodelAplication, context: ComponentActivity, state: Traduction){
    if (actionTranslate) {

        Text(
            text = (text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            color = colorResource(id = R.color.silver),
            textAlign = TextAlign.Center
        )

        viewmodelA.onTextToBeTranslatedChange(text)
        viewmodelA.onTranslateButtonClick(
            text = state.textToBeTranslate,
            context = context
        )
    } else {
        state.translatedText.let { translatedText ->
            val processedText = translatedText.toList().joinToString(separator = "") { if (it == ':') "\n" else it.toString() }
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