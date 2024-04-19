package com.hefestsoft.myrecipes.ui.home


import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hefestsoft.myrecipes.ApiService
import com.hefestsoft.myrecipes.R
import com.hefestsoft.myrecipes.RetrofitEngine
import com.hefestsoft.myrecipes.adapters.RecipesAdapter
import com.hefestsoft.myrecipes.databinding.FragmentHomeBinding
import com.hefestsoft.myrecipes.models.Meal
import com.hefestsoft.myrecipes.models.RecipesList
import com.google.android.material.chip.Chip
import org.imaginativeworld.whynotimagecarousel.CarouselItem
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.OnItemClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var binding: FragmentHomeBinding
    var letter = "A"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        binding.ImgNotFound.visibility = View.INVISIBLE
        binding.textNotFound.visibility = View.INVISIBLE
        if (savedInstanceState !==null){
            letter = savedInstanceState.getString("letter").toString()

        }
        getRecipes()

        renderChip(view)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("letter", letter)
    }

    fun renderChip(view: View) {
        var listABC = CharRange('A', 'Z').toList()
        for ((index, value) in listABC.withIndex()) {
            val chip = Chip(view.context)

            chip.setOnClickListener {
                binding.RVhome.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                letter = value.toString()
                getRecipes()


            }

            chip.text = value.toString()
            chip.width = 50
            chip.height = 50

            chip.chipBackgroundColor =
                ColorStateList.valueOf(ContextCompat.getColor(view.context, R.color.primary))
            chip.setTextColor(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        view.context,
                        R.color.icons
                    )
                )
            )

            binding.ChGroup.addView(chip)
        }
    }

    fun setImagesCarousel(images: MutableList<Meal>) {


        val list = mutableListOf<CarouselItem>()




        for ((index, value) in images.withIndex()) {
            list.add(
                CarouselItem(
                    imageUrl = value.strMealThumb,
                    caption = value.strMeal
                )
            )


        }





    }


    private fun getRecipes() {
        val service: ApiService = RetrofitEngine.retrofit()
            .create(ApiService::class.java)

        val result: Call<RecipesList> = service.getRecipes(letter)
        result.enqueue(object : Callback<RecipesList> {
            override fun onFailure(call: Call<RecipesList>, t: Throwable) {
                Log.e("retro", t.toString())
                Toast.makeText(requireContext(), "error al carga datos ${t}", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<RecipesList>, response: Response<RecipesList>) {
                val body = response.body()
                var list: ArrayList<Meal>? = if (body != null) body.meals else null
                binding.TxtTitle.text = "Meals by letter $letter "
                if (list?.isNotEmpty() == true) {
                    binding.progressBar.visibility = View.GONE
                    binding.RVhome.visibility = View.VISIBLE
                    var listCarouselImages = mutableListOf<Meal>()
                    var index = 6
                    if (list.size < 6) {
                        index = 2
                    }
                    for (i in 1..index) {
                        var idItem = ""

                        var item: Meal = list.random()

                        if (item.idMeal.toString() !== idItem) {
                            listCarouselImages.add(item)


                        }


                    }
                    setImagesCarousel(listCarouselImages)
                    initRecycler(list)
                    return
                }

                binding.RVhome.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.ImgNotFound.visibility = View.VISIBLE
                binding.textNotFound.visibility = View.VISIBLE
            }


        })


    }

    fun initRecycler(list: ArrayList<Meal>) {


        if (!list.isNullOrEmpty()) {

            binding.ImgNotFound.visibility = View.GONE
            binding.textNotFound.visibility = View.GONE
            binding.RVhome.layoutManager =  LinearLayoutManager(context)

            binding.RVhome.adapter = RecipesAdapter(list)

        } else if(list.isNullOrEmpty()) {
            binding.RVhome.visibility = View.GONE
            binding.ImgNotFound.visibility = View.VISIBLE
            binding.textNotFound.visibility = View.VISIBLE
        }
    }
}