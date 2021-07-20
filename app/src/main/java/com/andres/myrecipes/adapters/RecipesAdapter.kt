package com.andres.myrecipes.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.andres.myrecipes.R
import com.andres.myrecipes.models.Meal
import com.squareup.picasso.Picasso



class RecipesAdapter(val recipes: ArrayList<Meal>): RecyclerView.Adapter<RecipesAdapter.RecipesHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesHolder {
        val layourtInflater = LayoutInflater.from(parent.context)
       return RecipesHolder(layourtInflater.inflate(R.layout.recipe_item,parent,false))
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: RecipesHolder, position: Int) {
        holder.itemView.setOnClickListener { view->
            val bundle= Bundle()
            bundle.putString("recipeID",recipes[position].idMeal.toString())

            view.findNavController().navigate(R.id.HomeToDetail,bundle)


        }
        holder.render(recipes[position])
    }


    class RecipesHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val img = view.findViewById<ImageView>(R.id.imgRecipe)

        val txtTitle = view.findViewById<TextView>(R.id.txt_tittle)
        val txtCategory = view.findViewById<TextView>(R.id.txt_category)



        fun  render(recipe: Meal){
            txtTitle.text= recipe.strMeal
            txtCategory.text= "Category: ${recipe.strCategory}"
            Picasso.get().load(recipe.strMealThumb).into(img)


      }

    }
}