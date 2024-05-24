package com.project.beautifulday.Meal

import com.project.beautifulday.Meal.Data.Response.ListCategories
import com.project.beautifulday.Meal.Data.Response.ListMealArea
import com.project.beautifulday.Meal.Data.Response.ListMealIngredient
import com.project.beautifulday.Meal.Data.Response.ListMeals
import com.project.beautifulday.Meal.Data.Response.Meal
import com.project.beautifulday.Util.Constans.Companion.ALLCATEGORY
import com.project.beautifulday.Util.Constans.Companion.AREA
import com.project.beautifulday.Util.Constans.Companion.CATEGORY
import com.project.beautifulday.Util.Constans.Companion.FILTER
import com.project.beautifulday.Util.Constans.Companion.ID
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
    suspend fun getMealCategory(@Query("c") category: String): Response<ListCategories>

    @GET(FILTER)
    suspend fun getMealIngredient(@Query("i") ingredient: String): Response<ListMealIngredient>

    @GET(INGREDIENT)
    suspend fun getListIngredient(): Response<ListMealIngredient>

    @GET(AREA)
    suspend fun getListArea(): Response<ListMeals>

    @GET(ALLCATEGORY)
    suspend fun getListCategory(): Response<ListCategories>

    @GET(CATEGORY)
    suspend fun getListCategories(): Response<ListMeals>

    @GET(ID)
    suspend fun getMealById(@Query("i") id: String): Response<ListMeals>
}