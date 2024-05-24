package com.project.beautifulday.Meal.Data.Response

import com.project.beautifulday.Meal.MealService
import com.project.beautifulday.Meal.ui.States.CategoriesState
import com.project.beautifulday.Meal.ui.States.IngredientState
import com.project.beautifulday.Meal.ui.States.ListCategoriesState
import com.project.beautifulday.Meal.ui.States.ListMealIngredientState
import com.project.beautifulday.Meal.ui.States.ListMealsState
import com.project.beautifulday.Meal.ui.States.MealState
import javax.inject.Inject


class MealRepository@Inject constructor(private val api: MealService) {

    suspend fun getNameMeal(meal: String): ListMealsState{
        val response = api.getNameMeal(meal)
        return if(response.isSuccessful){
            response.body()?.getListMealState() ?: ListMealsState()
        }else ListMealsState()
    }

    suspend fun getRandomMeal():ListMealsState{
        val response = api.getRandomMeal()
        return if(response.isSuccessful){
            response.body()?.getListMealState() ?: ListMealsState()
        }else ListMealsState()
    }

    suspend fun getListCategory(): ListCategoriesState{
        val response = api.getListCategory()
        return if(response.isSuccessful){
            response.body()?.getListCategoiesState() ?: ListCategoriesState()
        }else ListCategoriesState()
    }
    suspend fun getListCategories(): ListMealsState{
        val response = api.getListCategories()
        return if(response.isSuccessful){
            response.body()?.getListMealState() ?: ListMealsState()
        }else ListMealsState()
    }

    suspend fun getListArea(): ListMealsState{
        val response = api.getListArea()
        return if(response.isSuccessful){
            response.body()?.getListMealState() ?: ListMealsState()
        }else ListMealsState()
    }

    suspend fun getListIngredient(): ListMealIngredientState{
        val response = api.getListIngredient()
        return if(response.isSuccessful){
            response.body()?.getListIngredient() ?: ListMealIngredientState()
        }else ListMealIngredientState()
    }

    suspend fun getMealById(id: String): ListMealsState{
        val response = api.getMealById(id)
        return if(response.isSuccessful){
            response.body()?.getListMealState() ?: ListMealsState()
        }else ListMealsState()
    }

    suspend fun getMealCategory(category: String): ListCategoriesState{
        val response = api.getMealCategory(category)
        return if(response.isSuccessful){
            response.body()?.getListCategoiesState() ?: ListCategoriesState()
        }else ListCategoriesState()
    }

    suspend fun getMealArea(area: String):ListMealsState{
        val response = api.getMealArea(area)
        return if(response.isSuccessful){
            response.body()?.getListMealState() ?: ListMealsState()
        } else ListMealsState()
    }

    private fun ListMeals.getListMealState(): ListMealsState{
        return ListMealsState(
            meals = this.meals?.map { it.getMealState() }
        )
    }

    private fun Meal.getMealState(): MealState{
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
            strIngredients = mutableListOf(strIngredient1?: "", strIngredient2?: "", strIngredient3?: "", strIngredient4?: "", strIngredient5?: "",
                strIngredient6?: "", strIngredient7?: "", strIngredient8?: "", strIngredient9?: "", strIngredient10?: "", strIngredient11?: ""
            ,strIngredient12?: "",strIngredient13?: "",strIngredient14?: "",strIngredient15?: "",strIngredient16?: "",strIngredient17?: "",strIngredient18?: "",
                strIngredient19?: "", strIngredient20?: ""),
            strMeasures = mutableListOf(strMeasure1?:"", strMeasure2?:"", strMeasure3?:"", strMeasure4?:"", strMeasure5?:"", strMeasure6?:"",
                strMeasure7?:"", strMeasure8?:"", strMeasure9?:"", strMeasure10?:"", strMeasure11?:"", strMeasure12?:"", strMeasure13?:"",
                strMeasure14?:"", strMeasure15?:"", strMeasure16?:"", strMeasure17?:"", strMeasure18?:"", strMeasure19?:"", strMeasure20?:"",)
        )
    }

    private fun ListMealIngredient.getListIngredient(): ListMealIngredientState{
        return ListMealIngredientState(
            meals = this.meals?.map{it.getIngredient()}
        )
    }

    private fun Ingredient.getIngredient(): IngredientState{
        return IngredientState(
            idIngredient = this.idIngredient,
            strIngredient = this.strIngredient,
            strDescription = this.strDescription,
            strType = this.strType
        )
    }

    private fun ListCategories.getListCategoiesState(): ListCategoriesState{
        return ListCategoriesState(
            categories = this.categories?.map{it.getCategoriesState()}
        )
    }

    private fun Categories.getCategoriesState(): CategoriesState {
        return CategoriesState(
            idCategory = this.idCategory,
            strCategory = this.strCategory,
            strCategoryThumb = this.strCategoryThumb,
            strCategoryDescription = this.strCategoryDescription
        )
    }

}