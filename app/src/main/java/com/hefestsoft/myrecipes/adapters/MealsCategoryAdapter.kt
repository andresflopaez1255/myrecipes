package com.hefestsoft.myrecipes.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hefestsoft.myrecipes.R
import com.hefestsoft.myrecipes.models.CategoryChip
import com.hefestsoft.myrecipes.models.CategoryItem


class MealsCategoryAdapter(private val listmeals: ArrayList<CategoryChip>): RecyclerView.Adapter<MealsCategoryAdapter.Holder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layourtInflater = LayoutInflater.from(parent.context)
        return Holder(layourtInflater.inflate(R.layout.category_item,parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.render(listmeals[position])
    }

    override fun getItemCount(): Int = listmeals.size



    class Holder(view: View):RecyclerView.ViewHolder(view) {

        private val title: TextView = view.findViewById<TextView>(R.id.category_text)

        fun render( category:CategoryChip){


            title.text= if(category.strCategory?.length!! > 20) "${category.strCategory?.substring(0,20)}.." else category.strCategory;

        }
    }

}