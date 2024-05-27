package com.project.beautifulday.Cocktail

import com.project.beautifulday.Cocktail.Data.Cocktail
import com.project.beautifulday.Util.Constans.Companion.FILTER
import com.project.beautifulday.Util.Constans.Companion.ID
import com.project.beautifulday.Util.Constans.Companion.RANDOM
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailService {
    @GET("search.php")
    //suspend fun getCocktail():Response<Cocktail>
    suspend fun getCocktail(@Query("s") cocktailName: String): Response<Cocktail>

    @GET(RANDOM)
    suspend fun getRandom(): Response<Cocktail>

    @GET(FILTER)
    suspend fun getAlcoholics(@Query("a") election: String): Response<Cocktail>


    @GET(FILTER)
    suspend fun getCategory(@Query("c") category: String): Response<Cocktail>

    @GET(ID)
    suspend fun getCocktailById(@Query("i") id: String): Response<Cocktail>
}