package com.project.beautifulday.Meal

import com.project.beautifulday.Meal.Data.Response.ListMeals
import com.project.beautifulday.Util.Constans.Companion.AREA
import com.project.beautifulday.Util.Constans.Companion.CATEGORY
import com.project.beautifulday.Util.Constans.Companion.FILTER
import com.project.beautifulday.Util.Constans.Companion.INGREDIENT
import com.project.beautifulday.Util.Constans.Companion.RANDOM
import com.project.beautifulday.Util.Constans.Companion.SEARCH
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealService {

    @GET(SEARCH)
    suspend fun getNameMeal(@Query("s") meal: String): Response<ListMeals>

    @GET(RANDOM)
    suspend fun getRandomMeal(): Response<ListMeals>

    @GET(FILTER)
    suspend fun getMealArea(@Query("a") area: String): Response<ListMeals>

    @GET(FILTER)
    suspend fun getMealCategory(@Query("c") category: String): Response<ListMeals>

    @GET(FILTER)
    suspend fun getMealIngredient(@Query("i") ingredient: String): Response<ListMeals>

    @GET(INGREDIENT)
    suspend fun getListIngredient(): Response<ListMeals>

    @GET(AREA)
    suspend fun getListArea(): Response<ListMeals>

    @GET(CATEGORY)
    suspend fun getListCategory(): Response<ListMeals
            >
}