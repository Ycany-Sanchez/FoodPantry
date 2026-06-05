package com.ycany.prefinals.screens.addingredients

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ycany.prefinals.R
import com.ycany.prefinals.data.models.Ingredient
import com.ycany.prefinals.data.models.Recipe
import com.ycany.prefinals.data.repositories.SpoonacularIngredientRepository
import com.ycany.prefinals.screens.viewrecipe.ViewRecipeActivity
import com.ycany.prefinals.utils.toast

class AddIngredientsActivity : AppCompatActivity(), AddIngredientsContract.View {

    private val presenter: AddIngredientsContract.Presenter = AddIngredientsPresenter(SpoonacularIngredientRepository)

    private lateinit var layoutPantryIngredients: LinearLayout
    private lateinit var layoutSearchResults: LinearLayout
    private lateinit var layoutRecipeResults: LinearLayout
    private lateinit var edittextSearchIngredient: EditText
    private lateinit var buttonSearchIngredient: Button
    private lateinit var edittextSearchRecipe: EditText
    private lateinit var buttonSearchRecipe: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ingredients)

        presenter.attachView(this)

        layoutPantryIngredients = findViewById(R.id.layoutPantryIngredients)
        layoutSearchResults = findViewById(R.id.layoutSearchResults)
        layoutRecipeResults = findViewById(R.id.layoutRecipeResults)
        edittextSearchIngredient = findViewById(R.id.edittextSearchIngredient)
        buttonSearchIngredient = findViewById(R.id.buttonSearchIngredient)
        edittextSearchRecipe = findViewById(R.id.edittextSearchRecipe)
        buttonSearchRecipe = findViewById(R.id.buttonSearchRecipe)
        progressBar = findViewById(R.id.progressBar)

        findViewById<ImageButton>(R.id.buttonBack).apply {
            setOnClickListener { presenter.onBackClicked() }
        }

        buttonSearchIngredient.apply {
            setOnClickListener {
                val query = edittextSearchIngredient.text.toString()
                presenter.onSearchIngredientClicked(query)
            }
        }

        buttonSearchRecipe.apply {
            setOnClickListener {
                val query = edittextSearchRecipe.text.toString()
                presenter.onSearchRecipeClicked(query)
            }
        }

        presenter.onViewReady()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun renderPantryIngredients(ingredients: List<Ingredient>) {
        layoutPantryIngredients.removeAllViews()
        ingredients.forEach { ingredient ->
            val row = layoutInflater.inflate(R.layout.item_ingredients_removable, layoutPantryIngredients, false)
            row.findViewById<TextView>(R.id.textviewIngredientName).text = ingredient.name
            row.findViewById<ImageButton>(R.id.buttonRemove).apply {
                setOnClickListener { presenter.onRemoveIngredientClicked(ingredient) }
            }
            layoutPantryIngredients.addView(row)
        }
    }

    override fun renderSearchResults(ingredients: List<Ingredient>) {
        layoutSearchResults.removeAllViews()
        ingredients.forEach { ingredient ->
            val row = layoutInflater.inflate(R.layout.items_ingredients_addable, layoutSearchResults, false)
            row.findViewById<TextView>(R.id.textviewIngredientName).text = ingredient.name
            row.findViewById<Button>(R.id.buttonAdd).apply {
                setOnClickListener { presenter.onAddIngredientFromSearchClicked(ingredient) }
            }
            layoutSearchResults.addView(row)
        }
    }

    override fun renderRecipeSearchResults(recipes: List<Recipe>) {
        layoutRecipeResults.removeAllViews()
        recipes.forEach { recipe ->
            val row = layoutInflater.inflate(R.layout.item_recipe_searchable, layoutRecipeResults, false)
            row.findViewById<TextView>(R.id.textviewRecipeTitle).text = recipe.title
            row.apply {
                setOnClickListener { presenter.onRecipeClicked(recipe.id) }
            }
            layoutRecipeResults.addView(row)
        }
    }

    override fun showSearchLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun showRecipeSearchLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun navigateToDashboard() {
        finish()
    }

    override fun navigateToViewRecipe(recipeId: Int) {
        Intent(this, ViewRecipeActivity::class.java).apply {
            putExtra("RECIPE_ID", recipeId)
            startActivity(this)
        }
    }
}