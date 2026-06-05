package com.ycany.prefinals.data.models

data class Recipe(
    val id: Int,
    val title: String,
    val image: String,
    val servings: Int,
    val readyInMinutes: Int,
    val sourceName: String = "Spoonacular",
    val chef: String = sourceName,
    val ingredients: List<String>,
    val instructions: List<String>,
    val calories: String,
    val protein: String,
    val carbs: String,
    val fat: String,
    val usedIngredientCount: Int = 0,
    val missedIngredientCount: Int = 0
)