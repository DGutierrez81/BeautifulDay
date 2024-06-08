package com.project.beautifulday.Network

import com.project.beautifulday.Cocktail.CocktailService
import com.project.beautifulday.Meal.MealService
import com.project.beautifulday.Util.Constans.Companion.URL_COCKTAIL
import com.project.beautifulday.Util.Constans.Companion.URL_MEAL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

// Este módulo proporciona instancias de Retrofit y servicios de API para Meal y Cocktail.
@Module
@InstallIn(SingletonComponent::class)
object RetrofitMeal {

    // Proporciona una instancia de Retrofit configurada para Meal.
    @Provides
    @Singleton
    @Named("retrofit1")
    fun Retrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(URL_MEAL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Proporciona una instancia de Retrofit configurada para Cocktail.
    @Provides
    @Singleton
    @Named("retrofit2")
    fun Retrofit2():Retrofit{
        return Retrofit.Builder()
            .baseUrl(URL_COCKTAIL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Proporciona una instancia de MealService para interactuar con la API de Meal.
    @Provides
    @Singleton
    fun provideMealApiService(@Named("retrofit1") retrofit: Retrofit): MealService{
        return retrofit.create(MealService::class.java)
    }

    // Proporciona una instancia de CocktailService para interactuar con la API de Cocktail.
    @Provides
    @Singleton
    fun provideCoktailApiService(@Named("retrofit2") retrofit: Retrofit): CocktailService {
        return retrofit.create(CocktailService::class.java)
    }
}

