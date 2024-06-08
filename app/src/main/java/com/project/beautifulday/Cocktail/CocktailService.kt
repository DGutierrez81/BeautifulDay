package com.project.beautifulday.Cocktail

import com.project.beautifulday.Cocktail.Data.Cocktail
import com.project.beautifulday.Util.Constans.Companion.FILTER
import com.project.beautifulday.Util.Constans.Companion.ID
import com.project.beautifulday.Util.Constans.Companion.RANDOM
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interfaz para definir las llamadas a la API relacionadas con los cócteles.
 */
interface CocktailService {
    /**
     * Obtiene la información de un cóctel por su nombre.
     *
     * @param cocktailName El nombre del cóctel a buscar.
     * @return Un objeto Response que contiene la información del cóctel.
     */
    @GET("search.php")
    suspend fun getCocktail(@Query("s") cocktailName: String): Response<Cocktail>

    /**
     * Obtiene un cóctel aleatorio.
     *
     * @return Un objeto Response que contiene la información de un cóctel aleatorio.
     */
    @GET(RANDOM)
    suspend fun getRandom(): Response<Cocktail>

    /**
     * Obtiene cócteles filtrados por tipo de alcohol.
     *
     * @param election El tipo de alcohol por el que filtrar los cócteles.
     * @return Un objeto Response que contiene la información de los cócteles filtrados.
     */
    @GET(FILTER)
    suspend fun getAlcoholics(@Query("a") election: String): Response<Cocktail>

    /**
     * Obtiene cócteles filtrados por categoría.
     *
     * @param category La categoría por la que filtrar los cócteles.
     * @return Un objeto Response que contiene la información de los cócteles filtrados.
     */
    @GET(FILTER)
    suspend fun getCategory(@Query("c") category: String): Response<Cocktail>

    /**
     * Obtiene la información de un cóctel por su ID.
     *
     * @param id El ID del cóctel a buscar.
     * @return Un objeto Response que contiene la información del cóctel.
     */
    @GET(ID)
    suspend fun getCocktailById(@Query("i") id: String): Response<Cocktail>
}
