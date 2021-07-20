package com.andres.myrecipes.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Category (
    val meals: ArrayList<CategoryChip>
)
@Parcelize
data class CategoryChip(
    val strCategory: String
):Parcelable
