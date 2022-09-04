package com.islaharper.hungryhippo.repository

import android.content.res.Resources
import com.google.gson.Gson
import com.islaharper.hungryhippo.R
import com.islaharper.hungryhippo.models.RestaurantModel
import java.io.BufferedReader

class MainRepository {

    fun getAllRestaurants(resources: Resources): RestaurantModel {
        val testFile = resources.openRawResource(R.raw.restaurants)
        var gson = Gson()
        val bufferedReader: BufferedReader = testFile.bufferedReader()
        val inputString = bufferedReader.use { it.readText() }

        return gson.fromJson(inputString, RestaurantModel::class.java)
    }

}