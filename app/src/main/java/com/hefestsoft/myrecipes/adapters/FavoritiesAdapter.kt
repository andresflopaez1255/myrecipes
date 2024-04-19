package com.hefestsoft.myrecipes.adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hefestsoft.myrecipes.R
import com.hefestsoft.myrecipes.db.FavoritiesDB
import com.hefestsoft.myrecipes.models.FavoriteMeal
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class FavoritiesAdapter (private val list:MutableList<FavoriteMeal>): RecyclerView.Adapter<FavoritiesAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layourtInflater = LayoutInflater.from(parent.context)
        return Holder(layourtInflater.inflate(R.layout.favorities_item,parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val deleteButton = holder.itemView.findViewById<LinearLayout>(R.id.delete)
        val  DB = FavoritiesDB.getInstance(holder.itemView.context)
        holder.itemView.setOnClickListener { view->
            val bundle= Bundle()
            bundle.putString("recipeID",list[position].idMeal)

            view.findNavController().navigate(R.id.favoriteToDetall,bundle)


        }


        deleteButton.setOnClickListener {

            DB.favoriteDao().deleteFavorite(list[position])
            list.removeAt(position)
            notifyItemRemoved(position)
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

            categoryText.text= "Category: ${favorite.strCategory}"
            title.text= favorite.strMeal
            Picasso.get().load(favorite.strMealThumb).resize(400, 400).into(image)
        }
    }
}