package com.ycany.prefinals.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RecipeByIngredientItem(
    @SerializedName("id")          val id: Int,
    @SerializedName("title")       val title: String,
    @SerializedName("image")       val image: String,
    @SerializedName("usedIngredientCount")   val usedIngredientCount: Int,
    @SerializedName("missedIngredientCount") val missedIngredientCount: Int,
    @SerializedName("usedIngredients")       val usedIngredients: List<UsedIngredientDto>,
    @SerializedName("missedIngredients")     val missedIngredients: List<UsedIngredientDto>
)

data class UsedIngredientDto(
    @SerializedName("id")       val id: Int,
    @SerializedName("name")     val name: String,
    @SerializedName("original") val original: String,
    @SerializedName("image")    val image: String
)

data class RecipeInformationResponse(
    @SerializedName("id")                    val id: Int,
    @SerializedName("title")                 val title: String,
    @SerializedName("image")                 val image: String,
    @SerializedName("servings")              val servings: Int,
    @SerializedName("readyInMinutes")        val readyInMinutes: Int,
    @SerializedName("sourceName")            val sourceName: String?,
    @SerializedName("extendedIngredients")   val extendedIngredients: List<ExtendedIngredientDto>,
    @SerializedName("analyzedInstructions")  val analyzedInstructions: List<AnalyzedInstructionDto>,
    @SerializedName("nutrition")             val nutrition: NutritionDto?
)

data class ExtendedIngredientDto(
    @SerializedName("id")       val id: Int,
    @SerializedName("name")     val name: String,
    @SerializedName("original") val original: String,
    @SerializedName("amount")   val amount: Double,
    @SerializedName("unit")     val unit: String
)

data class AnalyzedInstructionDto(
    @SerializedName("steps") val steps: List<StepDto>
)

data class StepDto(
    @SerializedName("number") val number: Int,
    @SerializedName("step")   val step: String
)

data class NutritionDto(
    @SerializedName("nutrients") val nutrients: List<NutrientDto>
)

data class NutrientDto(
    @SerializedName("name")   val name: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("unit")   val unit: String
)

data class IngredientSearchResponse(
    @SerializedName("results")         val results: List<IngredientResultDto>,
    @SerializedName("offset")          val offset: Int,
    @SerializedName("number")          val number: Int,
    @SerializedName("totalResults")    val totalResults: Int
)

data class IngredientResultDto(
    @SerializedName("id")    val id: Int,
    @SerializedName("name")  val name: String,
    @SerializedName("image") val image: String
)

data class RecipeSearchResponse(
    @SerializedName("results")      val results: List<RecipeSearchResultDto>,
    @SerializedName("offset")       val offset: Int,
    @SerializedName("number")       val number: Int,
    @SerializedName("totalResults") val totalResults: Int
)

data class RecipeSearchResultDto(
    @SerializedName("id")    val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: String
)