package com.andres.myrecipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.andres.myrecipes.R.drawable.ic_baseline_favorite_24
import com.andres.myrecipes.R.drawable.ic_baseline_favorite_border_24
import com.andres.myrecipes.databinding.FragmentDetailRecipeBinding
import com.andres.myrecipes.db.FavoritiesDB
import com.andres.myrecipes.models.FavoriteMeal
import com.andres.myrecipes.models.Meal
import com.andres.myrecipes.models.RecipesList
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailRecipeFragment : Fragment(R.layout.fragment_detail_recipe) {
    lateinit var binding: FragmentDetailRecipeBinding
    private lateinit var DB: FavoritiesDB
    lateinit var recipe:ArrayList<Meal>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailRecipeBinding.bind(view)
        DB = FavoritiesDB.getInstance(requireContext())
        arguments?.let {
            var id = it.getString("recipeID" )
            Log.d("id", id.toString())
            getDetail(id!!)


        }


        binding.imgFav.setOnClickListener {
            val favorite = FavoriteMeal(
                0,
                recipe[0].strMeal.toString(),

                recipe[0].strMealThumb.toString(),
                recipe[0].strCategory.toString(),
                recipe[0].idMeal.toString(),
            )
            Log.d("ID", recipe[0].idMeal.toString())

            lifecycleScope.launch {
                val existMeal = DB.favoriteDao().getByID(recipe[0].idMeal)
               if (existMeal.isEmpty()){
                   DB.favoriteDao().insertFavorite(favorite)
                   binding.imgFav.setImageResource(ic_baseline_favorite_24)
                   Toast.makeText(requireContext(),"Meal added to favorities",Toast.LENGTH_SHORT).show()

               }
            }
        }


    }


    fun getDetail(id:String){
        val service: ApiService = RetrofitEngine.retrofit()
            .create(ApiService::class.java)
        val result: Call<RecipesList> = service.getDetailsById(id.toString())
    binding.progressBar.visibility= View.VISIBLE
        result.enqueue(object: Callback<RecipesList>{
            override fun onResponse(call: Call<RecipesList>, response: Response<RecipesList>) {
                 recipe = response.body()?.meals!!
                binding.txtTitle.text=  recipe!![0].strMeal

                binding.textCategory.text = "Category: ${recipe[0].strCategory}"
                binding.txtArea.text= "Area: ${recipe!![0].strArea}"
                Picasso.get().load(recipe!![0].strMealThumb).into(binding.imgPhoto)
                renderIngredients(recipe!![0])
                renderQuantities(recipe[0])

                binding.txtInstructions.text=recipe!![0].strInstructions

                binding.progressBar.visibility= View.GONE
                binding.scroll.visibility = View.VISIBLE
                lifecycleScope.launchWhenStarted {
                    val existMeal = DB.favoriteDao().getByID(recipe[0].idMeal)
                    if (existMeal.isNotEmpty())  binding.imgFav.setImageResource(ic_baseline_favorite_24)
                }

            }

            override fun onFailure(call: Call<RecipesList>, t: Throwable) {
                Toast.makeText(requireContext(),"error al carga datos", Toast.LENGTH_LONG).show()
            }

        })
    }

    fun renderQuantities(recipe: Meal){
        var textAdd:String=""
        var items = listOf(
            recipe.strMeasure1,recipe.strMeasure2,
            recipe.strMeasure3,recipe.strMeasure4,
            recipe.strMeasure5,recipe.strMeasure6,
            recipe.strMeasure7,recipe.strMeasure8,
            recipe.strMeasure9,recipe.strMeasure10,
            recipe.strMeasure11,recipe.strMeasure12,
            recipe.strMeasure13,recipe.strMeasure14,
            recipe.strMeasure15,recipe.strMeasure16,
            recipe.strMeasure17,recipe.strMeasure18,
            recipe.strMeasure19,recipe.strMeasure20,

            )
        items.map {
            if( !it.isNullOrBlank()){
                textAdd+=it+"\r \n"
            }
        }

        binding.txtquantities.text=textAdd
    }

    fun renderIngredients(recipe:Meal) {
        var textAdd:String=""
        var items = listOf(
            recipe.strIngredient1,recipe.strIngredient2,
            recipe.strIngredient3,recipe.strIngredient4,
            recipe.strIngredient5,recipe.strIngredient6,
            recipe.strIngredient7,recipe.strIngredient8,
            recipe.strIngredient9,recipe.strIngredient10,
            recipe.strIngredient11,recipe.strIngredient12,
            recipe.strIngredient13,recipe.strIngredient14,
            recipe.strIngredient15,recipe.strIngredient16,
            recipe.strIngredient17,recipe.strIngredient18,
            recipe.strIngredient19,recipe.strIngredient20,

            )
        items.map {
            if( !it.isNullOrBlank()){
                textAdd+=it+"\r \n"
            }
        }

        binding.txtIngredients.text=textAdd

    }


}