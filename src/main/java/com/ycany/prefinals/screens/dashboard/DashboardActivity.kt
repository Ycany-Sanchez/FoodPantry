package com.ycany.prefinals.screens.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ycany.prefinals.R
import com.ycany.prefinals.data.models.Recipe
import com.ycany.prefinals.data.repositories.InMemoryUserAccountRepository
import com.ycany.prefinals.data.repositories.SpoonacularIngredientRepository
import com.ycany.prefinals.screens.addingredients.AddIngredientsActivity
import com.ycany.prefinals.screens.profile.ProfileActivity
import com.ycany.prefinals.screens.login.LoginActivity
import com.ycany.prefinals.screens.viewrecipe.ViewRecipeActivity
import com.ycany.prefinals.utils.toast

class DashboardActivity : AppCompatActivity(), DashboardContract.View {

    private val presenter: DashboardContract.Presenter = DashboardPresenter(
        InMemoryUserAccountRepository,
        SpoonacularIngredientRepository
    )

    private lateinit var textviewWelcome: TextView
    private lateinit var layoutSuggestions: LinearLayout
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        presenter.attachView(this)

        textviewWelcome = findViewById(R.id.textviewWelcome)
        layoutSuggestions = findViewById(R.id.layoutSuggestions)
        progressBar = findViewById(R.id.progressBar)

        findViewById<Button>(R.id.buttonAddIngredients).apply {
            setOnClickListener { presenter.onAddIngredientsClicked() }
        }

        findViewById<TextView>(R.id.textviewProfile).apply {
            setOnClickListener { presenter.onProfileClicked() }
        }

        findViewById<Button>(R.id.buttonSignOut).apply {
            setOnClickListener { presenter.onSignOutClicked() }
        }

        presenter.onViewReady(intent.getStringExtra("USERNAME"))
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun renderUsername(username: String) {
        textviewWelcome.text = "Welcome, Chef $username!"
    }

    override fun renderSuggestedRecipes(recipes: List<Recipe>) {
        layoutSuggestions.removeAllViews()
        recipes.forEach { recipe ->
            val recipeView = layoutInflater.inflate(R.layout.item_recipe_suggestion, layoutSuggestions, false)
            recipeView.findViewById<TextView>(R.id.textviewRecipeTitle).text = recipe.title
            recipeView.findViewById<TextView>(R.id.textviewRecipeChef).text = recipe.chef
            recipeView.findViewById<Button>(R.id.buttonViewRecipe).apply {
                setOnClickListener { presenter.onViewRecipeClicked(recipe.id) }
            }
            layoutSuggestions.addView(recipeView)
        }
    }

    override fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun navigateToLogin() {
        Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(this)
        }
    }

    override fun navigateToProfile(username: String) {
        Intent(this, ProfileActivity::class.java).apply {
            putExtra("USERNAME", username)
            startActivity(this)
        }
    }

    override fun navigateToAddIngredients() {
        startActivity(Intent(this, AddIngredientsActivity::class.java))
    }

    override fun navigateToViewRecipe(recipeId: Int) {
        Intent(this, ViewRecipeActivity::class.java).apply {
            putExtra("RECIPE_ID", recipeId)
            startActivity(this)
        }
    }
}