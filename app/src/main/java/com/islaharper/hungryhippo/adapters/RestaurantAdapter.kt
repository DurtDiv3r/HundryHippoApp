package com.islaharper.hungryhippo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.islaharper.hungryhippo.databinding.RestaurantItemBinding
import com.islaharper.hungryhippo.models.Hours
import com.islaharper.hungryhippo.models.RestaurantModel
import com.islaharper.hungryhippo.models.RestaurantModelItem
import java.text.SimpleDateFormat
import java.util.*

class RestaurantAdapter(val clickListener: RestaurantClickListener) :
    RecyclerView.Adapter<MyViewHolder>() {
    var restaurants = mutableListOf<RestaurantModelItem?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = RestaurantItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.binding.tvRestaurantName.text = restaurant?.name
        holder.binding.tvRestaurantAddress.text = restaurant?.address
        holder.binding.tvRestaurantHours.text =
            "Todays Hours: " + getTodaysHours(restaurant?.hours!!)
        Glide.with(holder.binding.thumbImage).load(restaurant.image).into(holder.binding.thumbImage)
        holder.itemView.setOnClickListener {
            clickListener.onItemClick(restaurants[position]!!)
        }
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    fun setRestaurantsList(restaurants: RestaurantModel?) {
        this.restaurants = restaurants?.toMutableList()!!
        notifyDataSetChanged()
    }

    private fun getTodaysHours(hours: Hours): String? {
        val calendar: Calendar = Calendar.getInstance()
        val date = calendar.time
        val day: String = SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.time)

        return when (day) {
            "Monday" -> hours.Monday
            "Tuesday" -> hours.Tuesday
            "Wednesday" -> hours.Wednesday
            "Thursday" -> hours.Thursday
            "Friday" -> hours.Friday
            "Saturday" -> hours.Saturday
            else -> hours.Sunday
        }
    }

    interface RestaurantClickListener {
        fun onItemClick(restaurantModelItem: RestaurantModelItem)
    }
}

class MyViewHolder(val binding: RestaurantItemBinding) : RecyclerView.ViewHolder(binding.root)