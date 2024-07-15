package com.fivetech.cadastro

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fivetech.cadastro.databinding.ActivityUpdateRecipeBinding

class UpdateRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateRecipeBinding
    private lateinit var db: RecipeDatabaseHelper
    private var recipeId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = RecipeDatabaseHelper(this)

        recipeId = intent.getIntExtra("recipe_id", -1)
        if(recipeId == -1){
            finish()
            return
        }

        val recipe = db.getRecipeById(recipeId)
        binding.updateTitleEditText.setText(recipe.title)
        binding.updateContentEditText.setText(recipe.content)

        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTitleEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()
            val updateRecipe = Recipe(recipeId, newTitle, newContent)
            db.updateRecipe(updateRecipe)
            finish()
            Toast.makeText(this, "Alterações Salvas", Toast.LENGTH_SHORT).show()
        }
    }
}