package com.andres.myrecipes

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitEngine {


    companion object{
        fun retrofit():Retrofit{

            return Retrofit.Builder()
                    .baseUrl("https://www.themealdb.com/api/json/v1/1/").
                     addConverterFactory(GsonConverterFactory.create()).
                     build()

        }
    }
}