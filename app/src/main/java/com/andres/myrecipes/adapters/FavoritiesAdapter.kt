package com.andres.myrecipes.adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andres.myrecipes.R
import com.andres.myrecipes.models.FavoriteMeal
import com.squareup.picasso.Picasso

class FavoritiesAdapter (private val list:List<FavoriteMeal>): RecyclerView.Adapter<FavoritiesAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layourtInflater = LayoutInflater.from(parent.context)
        return Holder(layourtInflater.inflate(R.layout.favorities_item,parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener { view->
            val bundle= Bundle()
            bundle.putString("recipeID",list[position].idMeal)

            //view.findNavController().navigate(R.id.CategoryToDetail,bundle)


        }
        Log.d("favorities", "adapter ${list.toString()}")
        holder.render(list[position])
    }

    override fun getItemCount(): Int = list.size



    class Holder(view: View):RecyclerView.ViewHolder(view) {
        private val image = view.findViewById<ImageView>(R.id.imgFavorite)!!
        val title = view.findViewById<TextView>(R.id.txtTitle)
        val categoryText = view.findViewById<TextView>(R.id.TextCategory)
        fun render(favorite: FavoriteMeal){

            categoryText.text= favorite.strCategory
            title.text= favorite.strMeal
            Picasso.get().load(favorite.strMealThumb).resize(400, 400).into(image)
        }
    }
}