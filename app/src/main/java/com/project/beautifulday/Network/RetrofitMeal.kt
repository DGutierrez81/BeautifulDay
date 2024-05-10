package com.project.beautifulday.Network

import com.project.beautifulday.Meal.MealService
import com.project.beautifulday.Util.Constans.Companion.URL_MEAL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitMeal {


    @Provides
    @Singleton
    fun Retrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(URL_MEAL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMealApiService(retrofit: Retrofit): MealService{
        return retrofit.create(MealService::class.java)
    }
}