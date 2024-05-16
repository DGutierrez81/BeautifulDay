package com.project.beautifulday.Meal.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import com.project.beautifulday.Meal.ui.States.Traduction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ViewmodelAplication@Inject constructor() : ViewModel() {
    private val _state = mutableStateOf(Traduction())
    val state: State<Traduction> = _state

    var actionTranslate by mutableStateOf(true)
        private set

    var slide by mutableStateOf(false)
        private set


    fun onTextToBeTranslatedChange(text: String){
        _state.value = state.value.copy(textToBeTranslate = text)
    }
    fun onTranslateButtonClick(text: String, context: Context){
        val options = TranslatorOptions
            .Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.SPANISH)
            .build()

        val languageTranslator = Translation
            .getClient(options)

        languageTranslator.translate(text)
            .addOnSuccessListener { translatedText ->
                _state.value = state.value.copy(
                    translatedText = translatedText
                )
            }
            .addOnFailureListener {
                //Toast.makeText(context,"traducción en curso", Toast.LENGTH_SHORT).show()
                downloadModelIfNotAviable(languageTranslator, context)
            }
    }

    private fun downloadModelIfNotAviable(languageTranslator: Translator, conext: Context){
        _state.value = state.value.copy(
            isButtonEnabled = false
        )

        val conditions = DownloadConditions
            .Builder()
            .requireWifi()
            .build()

        languageTranslator
            .downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                _state.value = state.value.copy(
                    isButtonEnabled = true
                )
            }
            .addOnFailureListener {
                Toast.makeText(conext,"No se ha podidio traducir",Toast.LENGTH_SHORT).show()
            }
    }

    fun changeActionTranslate(value: Boolean){
        actionTranslate = value
    }

    fun changeSlide(value: Boolean){
        slide = !value
    }
}