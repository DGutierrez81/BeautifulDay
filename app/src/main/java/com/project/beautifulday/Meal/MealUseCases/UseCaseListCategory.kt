package com.project.beautifulday.Meal.MealUseCases

import com.project.beautifulday.Meal.Data.Response.MealRepository
import com.project.beautifulday.Meal.ui.States.ListCategoriesState
import javax.inject.Inject

class UseCaseListCategory@Inject constructor(private val repository: MealRepository) {
    suspend operator fun invoke(category: String): ListCategoriesState = repository.getMealCategory(category)
}