package com.hefestsoft.myrecipes.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hefestsoft.myrecipes.FavoriteDao
import com.hefestsoft.myrecipes.models.FavoriteMeal


@Database(
    entities = [FavoriteMeal::class],
    version = 3
)
  abstract  class FavoritiesDB:RoomDatabase() {
    abstract fun favoriteDao():FavoriteDao

    companion object{
        private var INSTANCE: FavoritiesDB? = null
        fun getInstance(context:Context): FavoritiesDB{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    FavoritiesDB::class.java,
                    "favorities.db").fallbackToDestructiveMigration().allowMainThreadQueries()
                    .build()
            }

            return INSTANCE as FavoritiesDB
        }
    }
}