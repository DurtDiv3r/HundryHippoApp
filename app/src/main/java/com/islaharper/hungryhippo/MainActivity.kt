package com.islaharper.hungryhippo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.islaharper.hungryhippo.adapters.RestaurantAdapter
import com.islaharper.hungryhippo.databinding.ActivityMainBinding
import com.islaharper.hungryhippo.models.RestaurantModelItem
import com.islaharper.hungryhippo.repository.MainRepository
import com.islaharper.hungryhippo.ui.MenuActivity
import com.islaharper.hungryhippo.viewmodel.MainViewModel
import com.islaharper.hungryhippo.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity(), RestaurantAdapter.RestaurantClickListener {

    private lateinit var binding: ActivityMainBinding
    val restaurantAdapter = RestaurantAdapter(this)

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Restaurant List"

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(MainRepository())
        ).get(MainViewModel::class.java)

        binding.restaurantRecycler.adapter = restaurantAdapter
        viewModel.movieList.observe(this, Observer {
            restaurantAdapter.setRestaurantsList(it)
        })

        viewModel.getRestaurants(resources)
    }

    override fun onItemClick(restaurantModelItem: RestaurantModelItem) {
        val intent = Intent(this@MainActivity, MenuActivity::class.java)
        intent.putExtra("restaurantItem", restaurantModelItem)
        startActivity(intent)
    }
}