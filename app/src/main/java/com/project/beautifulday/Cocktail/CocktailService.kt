package com.project.beautifulday.Cocktail

import com.project.beautifulday.Cocktail.Data.Cocktail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailService {
    @GET("search.php")
    //suspend fun getCocktail():Response<Cocktail>
    suspend fun getCocktail(@Query("s") cocktailName: String): Response<Cocktail>

    @GET("random.php")
    suspend fun getRandom(): Response<Cocktail>

    @GET("filter.php?a=Alcoholic")
    suspend fun getAlcoholics(): Response<Cocktail>

    @GET("filter.php?a=Non_Alcoholic")
    suspend fun getNoAlcoholics(): Response<Cocktail>

    @GET("filter.php?c=Ordinary_Drink")
    suspend fun getOrdinary(): Response<Cocktail>

    @GET("filter.php?g=Champagne_flute")
    suspend fun getGlassChampagne(): Response<Cocktail>

    @GET("filter.php?g=Cocktail_glass")
    suspend fun getGlassCocktail(): Response<Cocktail>
}