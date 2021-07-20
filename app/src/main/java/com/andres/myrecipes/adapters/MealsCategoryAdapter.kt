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
import com.andres.myrecipes.models.CategoryItem
import com.squareup.picasso.Picasso


class MealsCategoryAdapter (private val listmeals:ArrayList<CategoryItem>): RecyclerView.Adapter<MealsCategoryAdapter.Holder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layourtInflater = LayoutInflater.from(parent.context)
        return Holder(layourtInflater.inflate(R.layout.category_item,parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener { view->
            val bundle= Bundle()
            bundle.putString("recipeID",listmeals[position].idMeal)

            view.findNavController().navigate(R.id.CategoryToDetail,bundle)


        }
        holder.render(listmeals[position])
    }

    override fun getItemCount(): Int = listmeals.size



    class Holder(view: View):RecyclerView.ViewHolder(view) {
        private val image = view.findViewById<ImageView>(R.id.imgRecipe)!!
        val title = view.findViewById<TextView>(R.id.txt_tittle)
        val categoryText = view.findViewById<TextView>(R.id.txt_category)
        fun render( category:CategoryItem){
            println(category.strMealThumb)
            categoryText.text= category.strCategory
            title.text= category.strMeal
            Picasso.get().load(category.strMealThumb).into(image)
        }
    }

}