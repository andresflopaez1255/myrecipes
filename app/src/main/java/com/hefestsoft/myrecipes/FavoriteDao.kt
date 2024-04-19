package com.hefestsoft.myrecipes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hefestsoft.myrecipes.models.FavoriteMeal

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM FavoriteMeal")
   suspend fun getAll():List<FavoriteMeal>

    @Query("SELECT * FROM FavoriteMeal WHERE idMeal=:id")
    suspend fun getByID(id: String?):List<FavoriteMeal>
    @Insert
   suspend fun insertFavorite(meal: FavoriteMeal)

    @Delete
    fun deleteFavorite(meal:FavoriteMeal)
}