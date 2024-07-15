package com.fivetech.cadastro

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fivetech.cadastro.databinding.ActivityAddRecipeBinding

class AddRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRecipeBinding
    private lateinit var db: RecipeDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = RecipeDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val recipe = Recipe(0, title, content)
            db.insertRecipe(recipe)
            finish()
            Toast.makeText(this, "Receita salva", Toast.LENGTH_SHORT).show()
        }
    }
}