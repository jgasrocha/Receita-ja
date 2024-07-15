package com.fivetech.cadastro

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecipesAdapter(private var recipes: List<Recipe>, context: Context,
                     private val db: RecipeDatabaseHelper = RecipeDatabaseHelper(context)
) :
    RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
            val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
            val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
            val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.titleTextView.text = recipe.title
        holder.contentTextView.text = recipe.content

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateRecipeActivity::class.java).apply {
                putExtra("recipe_id", recipe.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.deleteRecipe(recipe.id)
            refreshData(db.getAllRecipes())
            Toast.makeText(holder.itemView.context, "Receita Deletada", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newRecipes: List<Recipe>){
        recipes = newRecipes
        notifyDataSetChanged()
    }
}