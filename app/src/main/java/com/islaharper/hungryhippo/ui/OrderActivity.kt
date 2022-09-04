package com.islaharper.hungryhippo.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.islaharper.hungryhippo.adapters.OrderAdapter
import com.islaharper.hungryhippo.databinding.ActivityOrderBinding
import com.islaharper.hungryhippo.models.RestaurantModelItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    var isDeliveryOn: Boolean = false
    var orderTotal: String = ""
    private var orderAdapter: OrderAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurantModelItem: RestaurantModelItem? = intent.getParcelableExtra("RestaurantModel")
        orderAdapter = OrderAdapter(restaurantModelItem?.menus)
        binding.cartItemsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cartItemsRecyclerView.adapter = orderAdapter

        val actionbar: ActionBar? = supportActionBar
        actionbar?.title = restaurantModelItem?.name
        actionbar?.subtitle = restaurantModelItem?.address
        actionbar?.setDisplayHomeAsUpEnabled(true)

        binding.buttonPlaceYourOrder.setOnClickListener {
            onPlaceOrderButtonCLick(restaurantModelItem)
        }
        calculateTotalAmount(restaurantModelItem)
    }

    private fun calculateTotalAmount(restaurantItem: RestaurantModelItem?) {
        var subTotalAmount = 0.00
        for (menu in restaurantItem?.menus!!) {
            subTotalAmount += menu?.price!! * menu.totalInCart!!

        }
        binding.tvSubtotalAmount.text = "R${String.format("%.2f", subTotalAmount)}"
        if (isDeliveryOn) {
            subTotalAmount += restaurantItem.delivery_charge?.toFloat()!!
        }

        orderTotal = String.format("%.2f", subTotalAmount)
        binding.tvTotalAmount.text = "R$orderTotal"
    }

    private fun onPlaceOrderButtonCLick(restaurantItem: RestaurantModelItem?) {
        var name = ""
        if (TextUtils.isEmpty(binding.inputName.text.toString())) {
            binding.inputName.error = "Enter your name"
            return
        } else {
            name = binding.inputName.text.toString()
        }
        val intent = Intent(this@OrderActivity, SuccessfulOrderActivity::class.java)
        var orderTime = getTime()
        var collectionTime = setCollectionTime()
        intent.putExtra("RestaurantModel", restaurantItem)
        intent.putExtra("OrderName", name)
        intent.putExtra("OrderTime", orderTime)
        intent.putExtra("CollectionTime", collectionTime)
        intent.putExtra("OrderTotal", orderTotal)
        startActivityForResult(intent, 1000)
    }

    private fun getTime(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
        return current.format(formatter)
    }

    private fun setCollectionTime(): String {
        val current = LocalDateTime.now().plusMinutes(40)
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
        return current.format(formatter)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1000) {
            setResult(RESULT_OK)
            finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
        finish()
    }
}