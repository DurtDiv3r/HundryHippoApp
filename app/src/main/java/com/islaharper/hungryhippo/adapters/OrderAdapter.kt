package com.islaharper.hungryhippo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.islaharper.hungryhippo.databinding.OrderItemBinding
import com.islaharper.hungryhippo.models.Menu

class OrderAdapter(val menuList: List<Menu?>?) : RecyclerView.Adapter<OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = OrderItemBinding.inflate(inflater, parent, false)

        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = menuList?.get(position)
        holder.binding.menuName.text = item?.name!!
        var price = String.format("%.2f", item.price!! * item.totalInCart)

        holder.binding.menuPrice.text = "Price R$price"
        holder.binding.menuQty.text = "Qty :" + item.totalInCart

        Glide.with(holder.binding.thumbImage)
            .load(item.url)
            .into(holder.binding.thumbImage)
    }

    override fun getItemCount(): Int {
        return menuList?.size ?: 0
    }
}

class OrderViewHolder(var binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root)