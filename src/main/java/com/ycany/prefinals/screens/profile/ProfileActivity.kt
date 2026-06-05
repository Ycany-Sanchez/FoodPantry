package com.ycany.prefinals.screens.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ycany.prefinals.R
import com.ycany.prefinals.data.models.Recipe
import com.ycany.prefinals.data.models.UserAccount
import com.ycany.prefinals.data.repositories.InMemoryFavoritesRepository
import com.ycany.prefinals.data.repositories.InMemoryUserAccountRepository
import com.ycany.prefinals.screens.viewrecipe.ViewRecipeActivity

class ProfileActivity : AppCompatActivity(), ProfileContract.View {

    private val presenter: ProfileContract.Presenter = ProfilePresenter(
        accountRepository = InMemoryUserAccountRepository,
        favoritesRepository = InMemoryFavoritesRepository
    )

    private lateinit var textviewUsername: TextView
    private lateinit var textviewFullName: TextView
    private lateinit var textviewEmail: TextView
    private lateinit var textviewNoFavorites: TextView
    private lateinit var layoutFavoriteRecipes: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        presenter.attachView(this)

        textviewUsername = findViewById(R.id.textviewUsername)
        textviewFullName = findViewById(R.id.textviewFullName)
        textviewEmail = findViewById(R.id.textviewEmail)
        textviewNoFavorites = findViewById(R.id.textviewNoFavorites)
        layoutFavoriteRecipes = findViewById(R.id.layoutFavoriteRecipes)

        findViewById<Button>(R.id.buttonBackToDashboard).setOnClickListener {
            presenter.onBackClicked()
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh profile data and favorites every time the user navigates back to this screen
        presenter.onViewReady()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun renderProfile(account: UserAccount) {
        textviewUsername.text = account.username
        textviewFullName.text = account.fullName
        textviewEmail.text = account.email
    }

    override fun renderFavorites(favorites: List<Recipe>) {
        layoutFavoriteRecipes.removeAllViews()

        if (favorites.isEmpty()) {
            textviewNoFavorites.visibility = View.VISIBLE
        } else {
            textviewNoFavorites.visibility = View.GONE

            favorites.forEach { recipe ->
                // Inflate your existing item layout for clean visual consistency
                val recipeView = layoutInflater.inflate(R.layout.item_recipe_searchable, layoutFavoriteRecipes, false)

                val textviewTitle = recipeView.findViewById<TextView>(R.id.textviewRecipeTitle)
                textviewTitle.text = recipe.title

                recipeView.setOnClickListener {
                    presenter.onRecipeClicked(recipe.id)
                }

                layoutFavoriteRecipes.addView(recipeView)
            }
        }
    }

    override fun navigateToDashboard() {
        finish()
    }

    override fun navigateToRecipeDetails(recipeId: Int) {
        val intent = Intent(this, ViewRecipeActivity::class.java).apply {
            putExtra("RECIPE_ID", recipeId)
        }
        startActivity(intent)
    }
}