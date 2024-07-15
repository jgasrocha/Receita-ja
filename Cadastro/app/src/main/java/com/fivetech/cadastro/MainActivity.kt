package com.fivetech.cadastro

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fivetech.cadastro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: RecipeDatabaseHelper
    private lateinit var recipesAdapter: RecipesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = RecipeDatabaseHelper(this)
        recipesAdapter = RecipesAdapter(db.getAllRecipes(), this)

        binding.receitasRecycleview.layoutManager = LinearLayoutManager(this)
        binding.receitasRecycleview.adapter = recipesAdapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddRecipeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume(){
        super.onResume()
        recipesAdapter.refreshData(db.getAllRecipes())
    }
}