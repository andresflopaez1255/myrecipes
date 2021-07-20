package com.andres.myrecipes.ui.favorities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.andres.myrecipes.R
import com.andres.myrecipes.adapters.FavoritiesAdapter
import com.andres.myrecipes.adapters.MealsCategoryAdapter
import com.andres.myrecipes.databinding.FragmentCategoryBinding
import com.andres.myrecipes.databinding.FragmentFavoriteBinding
import com.andres.myrecipes.db.FavoritiesDB
import com.andres.myrecipes.models.CategoryResponse
import com.andres.myrecipes.models.FavoriteMeal
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

                initRecycler(list)
            }

        }


    }



    fun initRecycler(list: List<FavoriteMeal>) {


        binding.listFavorite.adapter = FavoritiesAdapter(list)
        binding.listFavorite.layoutManager = LinearLayoutManager(requireContext())


    }
}