package com.islaharper.hungryhippo.ui

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.islaharper.hungryhippo.databinding.ActivitySuccessfulOrderBinding
import com.islaharper.hungryhippo.models.RestaurantModelItem

class SuccessfulOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuccessfulOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessfulOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurantModel: RestaurantModelItem? = intent.getParcelableExtra("RestaurantModel")
        val orderName: String? = intent.getStringExtra("OrderName")
        val orderTime: String? = intent.getStringExtra("OrderTime")
        val orderTotal: String? = intent.getStringExtra("OrderTotal")
        val collectTime: String? = intent.getStringExtra("CollectionTime")
        val actionbar: ActionBar? = supportActionBar

        actionbar?.title = restaurantModel?.name
        actionbar?.subtitle = restaurantModel?.address
        actionbar?.setDisplayHomeAsUpEnabled(false)

        binding.currentOrderName.text = "Order for $orderName"
        binding.orderTime.text = "Order Placed: $orderTime"
        binding.orderCollection.text = "Collection: $collectTime"
        binding.orderTotal.text = "Total: R$orderTotal"
        binding.buttonDone.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }
}