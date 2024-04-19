package com.hefestsoft.myrecipes.ui.favorities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hefestsoft.myrecipes.R
import com.hefestsoft.myrecipes.adapters.FavoritiesAdapter
import com.hefestsoft.myrecipes.adapters.MealsCategoryAdapter
import com.hefestsoft.myrecipes.databinding.FragmentCategoryBinding
import com.hefestsoft.myrecipes.databinding.FragmentFavoriteBinding
import com.hefestsoft.myrecipes.db.FavoritiesDB
import com.hefestsoft.myrecipes.models.CategoryResponse
import com.hefestsoft.myrecipes.models.FavoriteMeal
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    lateinit var binding: FragmentFavoriteBinding
    private lateinit var DB: FavoritiesDB
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)
        DB = FavoritiesDB.getInstance(requireContext())
        var list:List<FavoriteMeal>  = emptyList()

        lifecycleScope.launch {
          list = DB.favoriteDao().getAll()
            this.runCatching {

                initRecycler(list as MutableList<FavoriteMeal>)
            }

        }




    }



    fun initRecycler(list: MutableList<FavoriteMeal>) {


        binding.listFavorite.adapter = FavoritiesAdapter(list)
        binding.listFavorite.layoutManager = LinearLayoutManager(requireContext())


    }
}