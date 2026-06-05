package com.ycany.prefinals.data.remote

import com.ycany.prefinals.data.models.Ingredient
import com.ycany.prefinals.data.models.Recipe
import com.ycany.prefinals.data.remote.dto.IngredientResultDto
import com.ycany.prefinals.data.remote.dto.RecipeByIngredientItem
import com.ycany.prefinals.data.remote.dto.RecipeInformationResponse

fun RecipeByIngredientItem.toDomain(): Recipe {
    return Recipe(
        id = id,
        title = title,
        image = image,
        servings = 0,
        readyInMinutes = 0,
        ingredients = usedIngredients.map { it.original } + missedIngredients.map { it.original },
        instructions = emptyList(),
        calories = "—",
        protein = "—",
        carbs = "—",
        fat = "—",
        usedIngredientCount = usedIngredientCount,
        missedIngredientCount = missedIngredientCount
    )
}

fun RecipeInformationResponse.toDomain(): Recipe {
    val steps = analyzedInstructions
        .flatMap { it.steps }
        .sortedBy { it.number }
        .map { it.step }

    val nutrients = nutrition?.nutrients ?: emptyList()

    fun nutrientValue(name: String): String {
        val n = nutrients.find { it.name.equals(name, ignoreCase = true) }
        return if (n != null) "${n.amount.toInt()}${n.unit}" else "—"
    }

    return Recipe(
        id = id,
        title = title,
        image = image,
        servings = servings,
        readyInMinutes = readyInMinutes,
        sourceName = sourceName ?: "Spoonacular",
        ingredients = extendedIngredients.map { it.original },
        instructions = steps,
        calories = nutrientValue("Calories"),
        protein = nutrientValue("Protein"),
        carbs = nutrientValue("Carbohydrates"),
        fat = nutrientValue("Fat")
    )
}

fun IngredientResultDto.toDomain(): Ingredient {
    return Ingredient(
        name = name
    )
}