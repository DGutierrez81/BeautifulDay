package com.project.beautifulday.Meal.Data.Response

import com.project.beautifulday.Meal.MealService
import com.project.beautifulday.Meal.ui.States.CategoriesState
import com.project.beautifulday.Meal.ui.States.ListCategoriesState
import com.project.beautifulday.Meal.ui.States.ListMealsState
import com.project.beautifulday.Meal.ui.States.MealState
import javax.inject.Inject


/**
 * Clase Repository responsable de manejar las operaciones de datos de comidas.
 * Esta clase interactúa con MealService para obtener datos de comidas de una API remota.
 *
 * @param api La instancia MealService utilizada para realizar llamadas a la API.
 */
class MealRepository@Inject constructor(private val api: MealService) {

    /**
     * Recupera una lista de comidas por nombre desde la API.
     *
     * @param meal El nombre de la comida a buscar.
     * @return Un objeto ListMealsState que representa el estado de la operación.
     */
    suspend fun getNameMeal(meal: String): ListMealsState {
        val response = api.getNameMeal(meal)
        return if (response.isSuccessful) {
            response.body()?.getListMealState() ?: ListMealsState()
        } else ListMealsState()
    }

    /**
     * Recupera una comida aleatoria desde la API.
     *
     * @return Un objeto ListMealsState que representa el estado de la operación.
     */
    suspend fun getRandomMeal(): ListMealsState {
        val response = api.getRandomMeal()
        return if (response.isSuccessful) {
            response.body()?.getListMealState() ?: ListMealsState()
        } else ListMealsState()
    }

    /**
     * Recupera una lista de categorías de comidas desde la API.
     *
     * @return Un objeto ListCategoriesState que representa el estado de la operación.
     */
    suspend fun getListCategory(): ListCategoriesState {
        val response = api.getListCategory()
        return if (response.isSuccessful) {
            response.body()?.getListCategoiesState() ?: ListCategoriesState()
        } else ListCategoriesState()
    }

    /**
     * Recupera una lista de categorías desde la API.
     *
     * @return Un objeto ListMealsState que representa el estado de la operación.
     */
    suspend fun getListCategories(): ListMealsState {
        val response = api.getListCategories()
        return if (response.isSuccessful) {
            response.body()?.getListMealState() ?: ListMealsState()
        } else ListMealsState()
    }

    /**
     * Recupera una lista de áreas de comidas desde la API.
     *
     * @return Un objeto ListMealsState que representa el estado de la operación.
     */
    suspend fun getListArea(): ListMealsState {
        val response = api.getListArea()
        return if (response.isSuccessful) {
            response.body()?.getListMealState() ?: ListMealsState()
        } else ListMealsState()
    }

    /**
     * Recupera una comida por su ID desde la API.
     *
     * @param id El ID de la comida a recuperar.
     * @return Un objeto ListMealsState que representa el estado de la operación.
     */
    suspend fun getMealById(id: String): ListMealsState {
        val response = api.getMealById(id)
        return if (response.isSuccessful) {
            response.body()?.getListMealState() ?: ListMealsState()
        } else ListMealsState()
    }

    /**
     * Recupera comidas por una categoría específica desde la API.
     *
     * @param category La categoría de comidas a recuperar.
     * @return Un objeto ListCategoriesState que representa el estado de la operación.
     */
    suspend fun getMealCategory(category: String): ListCategoriesState {
        val response = api.getMealCategory(category)
        return if (response.isSuccessful) {
            response.body()?.getListCategoiesState() ?: ListCategoriesState()
        } else ListCategoriesState()
    }

    /**
     * Recupera comidas por un área específica desde la API.
     *
     * @param area El área de comidas a recuperar.
     * @return Un objeto ListMealsState que representa el estado de la operación.
     */
    suspend fun getMealArea(area: String): ListMealsState {
        val response = api.getMealArea(area)
        return if (response.isSuccessful) {
            response.body()?.getListMealState() ?: ListMealsState()
        } else ListMealsState()
    }

    /**
     * Convierte un objeto ListMeals a un objeto ListMealsState.
     *
     * @return Un objeto ListMealsState que representa el estado convertido.
     */
    private fun ListMeals.getListMealState(): ListMealsState {
        return ListMealsState(
            meals = this.meals?.map { it.getMealState() }
        )
    }

    /**
     * Convierte un objeto Meal a un objeto MealState.
     *
     * @return Un objeto MealState que representa el estado convertido.
     */
    private fun Meal.getMealState(): MealState {
        return MealState(
            idMeal = this.idMeal,
            strMeal = this.strMeal,
            strDrinkAlternate = this.strDrinkAlternate,
            strCategory = this.strCategory,
            strArea = this.strArea,
            strInstructions = this.strInstructions,
            strMealThumb = this.strMealThumb,
            strTags = this.strTags,
            strYoutube = this.strYoutube,
            strIngredients = mutableListOf(
                strIngredient1 ?: "", strIngredient2 ?: "", strIngredient3 ?: "", strIngredient4 ?: "", strIngredient5 ?: "",
                strIngredient6 ?: "", strIngredient7 ?: "", strIngredient8 ?: "", strIngredient9 ?: "", strIngredient10 ?: "",
                strIngredient11 ?: "", strIngredient12 ?: "", strIngredient13 ?: "", strIngredient14 ?: "", strIngredient15 ?: "",
                strIngredient16 ?: "", strIngredient17 ?: "", strIngredient18 ?: "", strIngredient19 ?: "", strIngredient20 ?: ""
            ),
            strMeasures = mutableListOf(
                strMeasure1 ?: "", strMeasure2 ?: "", strMeasure3 ?: "", strMeasure4 ?: "", strMeasure5 ?: "",
                strMeasure6 ?: "", strMeasure7 ?: "", strMeasure8 ?: "", strMeasure9 ?: "", strMeasure10 ?: "",
                strMeasure11 ?: "", strMeasure12 ?: "", strMeasure13 ?: "", strMeasure14 ?: "", strMeasure15 ?: "",
                strMeasure16 ?: "", strMeasure17 ?: "", strMeasure18 ?: "", strMeasure19 ?: "", strMeasure20 ?: ""
            )
        )
    }

    /**
     * Convierte un objeto ListCategories a un objeto ListCategoriesState.
     *
     * @return Un objeto ListCategoriesState que representa el estado convertido.
     */
    private fun ListCategories.getListCategoiesState(): ListCategoriesState {
        return ListCategoriesState(
            categories = this.categories?.map { it.getCategoriesState() }
        )
    }

    /**
     * Convierte un objeto Categories a un objeto CategoriesState.
     *
     * @return Un objeto CategoriesState que representa el estado convertido.
     */
    private fun Categories.getCategoriesState(): CategoriesState {
        return CategoriesState(
            idCategory = this.idCategory,
            strCategory = this.strCategory,
            strCategoryThumb = this.strCategoryThumb,
            strCategoryDescription = this.strCategoryDescription
        )
    }
}



