package com.hefestsoft.myrecipes.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteMeal")
data class FavoriteMeal(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val strMeal: String,
    val strMealThumb: String,
    val strCategory: String,
    val idMeal: String
)
