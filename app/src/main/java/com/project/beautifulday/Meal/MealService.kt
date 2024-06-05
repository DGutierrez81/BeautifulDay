package com.project.beautifulday.Meal

import com.project.beautifulday.Meal.Data.Response.ListCategories
import com.project.beautifulday.Meal.Data.Response.ListMeals
import com.project.beautifulday.Util.Constans.Companion.ALLCATEGORY
import com.project.beautifulday.Util.Constans.Companion.AREA
import com.project.beautifulday.Util.Constans.Companion.CATEGORY
import com.project.beautifulday.Util.Constans.Companion.FILTER
import com.project.beautifulday.Util.Constans.Companion.ID
import com.project.beautifulday.Util.Constans.Companion.RANDOM
import com.project.beautifulday.Util.Constans.Companion.SEARCH
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interfaz que define los métodos para interactuar con el servicio de comidas.
 */
interface MealService {

    /**
     * Obtiene una lista de comidas que coincidan con el nombre proporcionado.
     * @param meal El nombre de la comida a buscar.
     * @return La respuesta que contiene la lista de comidas.
     */
    @GET(SEARCH)
    suspend fun getNameMeal(@Query("s") meal: String): Response<ListMeals>

    /**
     * Obtiene una comida aleatoria.
     * @return La respuesta que contiene la comida aleatoria.
     */
    @GET(RANDOM)
    suspend fun getRandomMeal(): Response<ListMeals>

    /**
     * Obtiene una lista de comidas basada en el área proporcionada.
     * @param area El área de la comida.
     * @return La respuesta que contiene la lista de comidas.
     */
    @GET(FILTER)
    suspend fun getMealArea(@Query("a") area: String): Response<ListMeals>

    /**
     * Obtiene una lista de comidas basada en la categoría proporcionada.
     * @param category La categoría de la comida.
     * @return La respuesta que contiene la lista de comidas.
     */
    @GET(FILTER)
    suspend fun getMealCategory(@Query("c") category: String): Response<ListCategories>

    /**
     * Obtiene una lista de áreas.
     * @return La respuesta que contiene la lista de áreas.
     */
    @GET(AREA)
    suspend fun getListArea(): Response<ListMeals>

    /**
     * Obtiene una lista de categorías.
     * @return La respuesta que contiene la lista de categorías.
     */
    @GET(ALLCATEGORY)
    suspend fun getListCategory(): Response<ListCategories>

    /**
     * Obtiene una lista de categorías.
     * @return La respuesta que contiene la lista de categorías.
     */
    @GET(CATEGORY)
    suspend fun getListCategories(): Response<ListMeals>

    /**
     * Obtiene una comida basada en el ID proporcionado.
     * @param id El ID de la comida.
     * @return La respuesta que contiene la comida.
     */
    @GET(ID)
    suspend fun getMealById(@Query("i") id: String): Response<ListMeals>
}