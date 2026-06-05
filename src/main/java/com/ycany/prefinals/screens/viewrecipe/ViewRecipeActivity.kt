package com.ycany.prefinals.screens.viewrecipe

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ycany.prefinals.R
import com.ycany.prefinals.data.models.Recipe
import com.ycany.prefinals.data.repositories.InMemoryFavoritesRepository
import com.ycany.prefinals.data.repositories.SpoonacularRecipeRepository
import com.ycany.prefinals.utils.toast


class ViewRecipeActivity : AppCompatActivity(), ViewRecipeContract.View {

    private val presenter: ViewRecipeContract.Presenter = ViewRecipePresenter(
        recipeRepository = SpoonacularRecipeRepository(),
        favoritesRepository = InMemoryFavoritesRepository
    )

    private lateinit var textviewTitle: TextView
    private lateinit var textviewChef: TextView
    private lateinit var textviewReadyIn: TextView
    private lateinit var textviewServings: TextView
    private lateinit var textviewCalories: TextView
    private lateinit var textviewProtein: TextView
    private lateinit var textviewCarbs: TextView
    private lateinit var textviewFat: TextView
    private lateinit var layoutIngredients: LinearLayout
    private lateinit var layoutInstructions: LinearLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var buttonFavorite: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_recipe)

        presenter.attachView(this)

        textviewTitle = findViewById(R.id.textviewRecipeTitle)
        textviewChef = findViewById(R.id.textviewRecipeChef)
        textviewReadyIn = findViewById(R.id.textviewReadyIn)
        textviewServings = findViewById(R.id.textviewServings)
        textviewCalories = findViewById(R.id.textviewCalories)
        textviewProtein = findViewById(R.id.textviewProtein)
        textviewCarbs = findViewById(R.id.textviewCarbs)
        textviewFat = findViewById(R.id.textviewFat)
        layoutIngredients = findViewById(R.id.layoutIngredients)
        layoutInstructions = findViewById(R.id.layoutInstructions)
        progressBar = findViewById(R.id.progressBar)
        buttonFavorite = findViewById(R.id.buttonFavorite)

        findViewById<ImageButton>(R.id.buttonBack).apply {
            setOnClickListener { presenter.onBackClicked() }
        }

        buttonFavorite.setOnClickListener {
            presenter.onFavoriteClicked()
        }

        val recipeId = intent.getIntExtra("RECIPE_ID", -1)
        presenter.onViewReady(recipeId)
    }

    override fun onNewIntent(intent: android.content.Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            setIntent(intent)
            val recipeId = intent.getIntExtra("RECIPE_ID", -1)
            presenter.onViewReady(recipeId)
        }
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun renderRecipe(recipe: Recipe) {
        textviewTitle.text = recipe.title
        textviewChef.text = recipe.sourceName
        textviewReadyIn.text = "Ready in ${recipe.readyInMinutes} min"
        textviewServings.text = "Serves ${recipe.servings}"
        textviewCalories.text = recipe.calories
        textviewProtein.text = "Protein: ${recipe.protein}"
        textviewCarbs.text = "Carbs: ${recipe.carbs}"
        textviewFat.text = "Fat: ${recipe.fat}"

        layoutIngredients.removeAllViews()
        recipe.ingredients.forEach { ingredient ->
            val tv = TextView(this).apply {
                text = "• $ingredient"
                textSize = 14f
                setPadding(0, 4, 0, 4)
            }
            layoutIngredients.addView(tv)
        }

        layoutInstructions.removeAllViews()
        recipe.instructions.forEachIndexed { index, step ->
            val tv = TextView(this).apply {
                text = "Step ${index + 1}:\n$step"
                textSize = 14f
                setPadding(0, 8, 0, 8)
            }
            layoutInstructions.addView(tv)
        }
    }

    override fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun navigateToDashboard() {
        finish()
    }

    override fun updateFavoriteStatus(isFavorite: Boolean) {
        val iconRes = if (isFavorite) {
            android.R.drawable.btn_star_big_on
        } else {
            android.R.drawable.btn_star_big_off
        }
        buttonFavorite.setImageResource(iconRes)
    }
}