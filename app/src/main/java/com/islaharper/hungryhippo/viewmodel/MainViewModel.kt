package com.islaharper.hungryhippo.viewmodel

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.islaharper.hungryhippo.models.RestaurantModel
import com.islaharper.hungryhippo.repository.MainRepository

class MainViewModel constructor(private val repository: MainRepository) : ViewModel() {
    val movieList = MutableLiveData<RestaurantModel>()

    fun getRestaurants(resources: Resources) {
        val restaurants = repository.getAllRestaurants(resources)
        restaurants.let {
            movieList.postValue(it)
        }
    }
}