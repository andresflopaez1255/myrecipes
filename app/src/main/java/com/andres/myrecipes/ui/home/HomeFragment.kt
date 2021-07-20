package com.andres.myrecipes.ui.home

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.andres.myrecipes.*
import com.andres.myrecipes.adapters.RecipesAdapter
import com.andres.myrecipes.databinding.FragmentHomeBinding
import com.andres.myrecipes.models.Meal
import com.andres.myrecipes.models.RecipesList
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
        getRecipes("a")
        renderChip(view)
    }


    fun renderChip(view: View) {
        var listABC = listOf<String>(
            "A",
            "B",
            "C",
            "D",
            "E",
            "F",
            "G",
            "J",
            "K",
            "L",
            "M",
            "N",
            "O",
            "P",
            "Q",
            "R",
            "S",
            "T",
            "U",
            "V",
            "W",
            "X",
            "Y",
            "Z"
        )
        for ((index, value) in listABC.withIndex()) {
            val chip = Chip(view.context)

            chip.setOnClickListener {
                getRecipes(value)
                binding.TxtTitle.text = "Meals by letter $value "
            }

            chip.text = value
            chip.width = 50
            chip.height = 50
            chip.chipStartPadding = 8f
            chip.chipEndPadding = 8f
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
        val carousel: ImageCarousel = binding.carousel

        val list = mutableListOf<CarouselItem>()




        for ((index, value) in images.withIndex()) {
            list.add(
                CarouselItem(
                    imageUrl = value.strMealThumb,
                    caption = value.strMeal
                )
            )
            carousel.onItemClickListener = object : OnItemClickListener {
                override fun onClick(position: Int, carouselItem: CarouselItem) {
                    val bundle = Bundle()

                    bundle.putString("recipeID", value.idMeal.toString())
                    view?.findNavController()?.navigate(R.id.HomeToDetail, bundle)
                }

                override fun onLongClick(position: Int, dataObject: CarouselItem) {
                    val bundle = Bundle()

                    bundle.putString("recipeID", value.idMeal.toString())
                    view?.findNavController()?.navigate(R.id.HomeToDetail, bundle)
                }

            }

        }



        carousel.addData(list)

    }


    private fun getRecipes(value: String ) {
        val service: ApiService = RetrofitEngine.retrofit()
            .create(ApiService::class.java)

        val result: Call<RecipesList> = service.getRecipes(value.toLowerCase())
        result.enqueue(object : Callback<RecipesList> {
            override fun onFailure(call: Call<RecipesList>, t: Throwable) {
                Toast.makeText(requireContext(), "error al carga datos", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<RecipesList>, response: Response<RecipesList>) {
                val body = response.body()
                var list: ArrayList<Meal>? = if (body != null) body.meals else null

                if (list?.isNotEmpty() == true){
                    binding.progressBar.visibility= View.GONE
                    binding.RVhome.visibility =  View.VISIBLE
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

                binding.RVhome.visibility = View.INVISIBLE
                binding.ImgNotFound.visibility = View.VISIBLE
                binding.textNotFound.visibility = View.VISIBLE
            }


        })


    }

    fun initRecycler(list: ArrayList<Meal>) {


        if (!list.isNullOrEmpty()) {

            binding.ImgNotFound.visibility = View.INVISIBLE
            binding.textNotFound.visibility = View.INVISIBLE
            binding.RVhome.layoutManager = GridLayoutManager(requireContext(), 2)

            binding.RVhome.adapter = RecipesAdapter(list)

        } else if(list.isNullOrEmpty()) {
            binding.RVhome.visibility = View.INVISIBLE
            binding.ImgNotFound.visibility = View.VISIBLE
            binding.textNotFound.visibility = View.VISIBLE
        }
    }
}