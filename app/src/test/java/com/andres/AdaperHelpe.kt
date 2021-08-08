package com.andres

import android.content.Context
import com.andres.myrecipes.db.FavoritiesDB
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.coroutineScope


class AdaperHelper(context: Context) {


    val  DB = FavoritiesDB.getInstance(context)


}