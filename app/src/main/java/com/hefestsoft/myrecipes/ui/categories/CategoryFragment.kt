package com.hefestsoft.myrecipes.ui.categories

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager

import com.hefestsoft.myrecipes.*
import com.hefestsoft.myrecipes.adapters.MealsCategoryAdapter

import com.hefestsoft.myrecipes.models.*
import com.hefestsoft.myrecipes.databinding.FragmentCategoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFragment : Fragment(R.layout.fragment_category) {
    lateinit var binding: FragmentCategoryBinding
    var listCategory = arrayListOf<CategoryChip>()
        var category = "Beef"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoryBinding.bind(view)
        getCategories(view)
        getMealsCategory()
        if (savedInstanceState !== null){
            category = savedInstanceState.getString("category").toString()
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("category", category)
    }

    private fun getCategories(view: View) {
        val service: ApiService = RetrofitEngine.retrofit()
            .create(ApiService::class.java)
        val result: Call<Category> = service.getCategories()
        result.enqueue(object : Callback<Category> {
            override fun onFailure(call: Call<Category>, t: Throwable) {
                Toast.makeText(requireContext(), "error al carga datos ${t}", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Category>, response: Response<Category>) {
                listCategory = response.body()!!.meals

            }


        })


    }


    private fun getMealsCategory() {

        val service: ApiService = RetrofitEngine.retrofit()
            .create(ApiService::class.java)
        val result: Call<Category> = service.getCategories()
        result.enqueue(object : Callback<Category> {


            override fun onFailure(call: Call<Category>, t: Throwable) {
                Toast.makeText(requireContext(), "error al carga datos", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Category>, response: Response<Category>) {
                val list = response.body()!!.meals

                binding.progressCategory.visibility = View.GONE
                binding.recycleViewResult.visibility = View.VISIBLE
                initRecycler(list,category)
            }


        })


    }

    private fun addCategoryNameTolist(list: ArrayList<CategoryResponse>, name:String): ArrayList<CategoryItem> {
        Log.d("namelist", list.toString())
        val listmeals = arrayListOf<CategoryItem>()
        list.map { item ->

            listmeals.add(CategoryItem(item.strMeal,item.strMealThumb,name, item.idMeal))

        }
        return listmeals

    }



    fun initRecycler(list: ArrayList<CategoryChip>, name:String) {
        Log.d("Init", list.toString())

        Log.i("listdd",list.toString())
        binding.recycleViewResult.adapter = MealsCategoryAdapter(list)
        binding.recycleViewResult.layoutManager = GridLayoutManager(requireContext(), 2)


    }

}