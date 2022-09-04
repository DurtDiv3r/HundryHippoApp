package com.islaharper.hungryhippo.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.islaharper.hungryhippo.adapters.MenuAdapter
import com.islaharper.hungryhippo.adapters.MenuAdapter.MenuItemClickListener
import com.islaharper.hungryhippo.databinding.ActivityMenuBinding
import com.islaharper.hungryhippo.models.Menu
import com.islaharper.hungryhippo.models.RestaurantModelItem

class MenuActivity : AppCompatActivity(), MenuItemClickListener {
    private lateinit var binding: ActivityMenuBinding
    private var menuList: List<Menu?>? = null
    private var menuAdapter: MenuAdapter? = null

    private var itemsInTheCartList: MutableList<Menu?>? = null
    private var totalItemInCartCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurantItem = intent?.getParcelableExtra<RestaurantModelItem>("restaurantItem")

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = restaurantItem?.name
        actionBar?.subtitle = restaurantItem?.address
        actionBar?.setDisplayHomeAsUpEnabled(true)

        menuList = restaurantItem?.menus!!
        menuAdapter = MenuAdapter(menuList, this)
        binding.menuRecycler.layoutManager = GridLayoutManager(this, 2)
        binding.menuRecycler.adapter = menuAdapter
        binding.checkoutButton.setOnClickListener {
            if (itemsInTheCartList != null && itemsInTheCartList!!.size <= 0) {
                Toast.makeText(
                    this@MenuActivity,
                    "Please add some items in cart",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                restaurantItem.menus = itemsInTheCartList
                val intent = Intent(this@MenuActivity, OrderActivity::class.java)
                intent.putExtra("RestaurantModel", restaurantItem)
                startActivityForResult(intent, 1000)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            finish()
        }
    }

    override fun addToCartClickListener(menu: Menu) {
        if (itemsInTheCartList == null) {
            itemsInTheCartList = ArrayList()
        }
        itemsInTheCartList?.add(menu)
        totalItemInCartCount = 0
        for (menu in itemsInTheCartList!!) {
            totalItemInCartCount = totalItemInCartCount + menu?.totalInCart!!
        }
        binding.checkoutButton.text = "Checkout ($totalItemInCartCount Items"
    }

    override fun updateCartClickListener(menu: Menu) {
        val index = itemsInTheCartList!!.indexOf(menu)
        itemsInTheCartList?.removeAt(index)
        itemsInTheCartList?.add(menu)
        totalItemInCartCount = 0
        for (menu in itemsInTheCartList!!) {
            totalItemInCartCount = totalItemInCartCount + menu?.totalInCart!!
        }
        binding.checkoutButton.text = "Checkout ($totalItemInCartCount) Items"
    }

    override fun removeFromCartClickListener(menu: Menu) {
        if (itemsInTheCartList!!.contains(menu)) {
            itemsInTheCartList?.remove(menu)
            totalItemInCartCount = 0
            for (menu in itemsInTheCartList!!) {
                totalItemInCartCount = totalItemInCartCount + menu?.totalInCart!!
            }
            binding.checkoutButton.text = "Checkout ($totalItemInCartCount  Items"
        }
    }
}