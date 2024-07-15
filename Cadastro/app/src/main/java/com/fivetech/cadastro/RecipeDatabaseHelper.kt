package com.fivetech.cadastro

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RecipeDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "recipeapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "recipes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertRecipe(recipe: Recipe){
        val db = writableDatabase
        val values= ContentValues().apply {
            put(COLUMN_TITLE, recipe.title)
            put(COLUMN_CONTENT, recipe.content)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllRecipes(): List<Recipe> {
        val recipesList = mutableListOf<Recipe>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while(cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            val recipe = Recipe(id, title, content)
            recipesList.add(recipe)
        }
        cursor.close()
        db.close()
        return recipesList
    }

    fun updateRecipe(recipe: Recipe){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, recipe.title)
            put(COLUMN_CONTENT, recipe.content)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(recipe.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getRecipeById(recipeId: Int): Recipe{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $recipeId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return Recipe(id, title, content)
    }

    fun deleteRecipe(recipeId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(recipeId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }
}