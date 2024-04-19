package com.hefestsoft.myrecipes
import com.hefestsoft.myrecipes.models.*
import retrofit2.Call
import  retrofit2.http.GET

import retrofit2.http.Query

interface ApiService {

    @GET("search.php")
    fun getRecipes(@Query("f")letter : String):Call<RecipesList>
    @GET("lookup.php")
    fun getDetailsById(@Query("i") recipeId:String):Call<RecipesList>
    @GET("list.php?c=list")
    fun getCategories():Call<Category>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") category:String):Call<CategoryList>

}