package com.andres.myrecipes.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class  CategoryList (
    val meals:ArrayList<CategoryResponse>
        )

@Parcelize
data class CategoryItem (
    val strMeal: String,
    val strMealThumb: String,
    val strCategory: String,
    val idMeal: String
):Parcelable


@Parcelize
data class CategoryResponse (
    val strMeal: String,
    val strMealThumb: String,

    val idMeal: String
):Parcelable
