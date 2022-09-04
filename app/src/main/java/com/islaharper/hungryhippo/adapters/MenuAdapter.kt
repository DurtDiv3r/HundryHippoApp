package com.islaharper.hungryhippo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.islaharper.hungryhippo.databinding.MenuItemBinding
import com.islaharper.hungryhippo.models.Menu

class MenuAdapter(val menuList: List<Menu?>?, val clickListener: MenuItemClickListener) :
    RecyclerView.Adapter<MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = MenuItemBinding.inflate(inflater, parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = menuList?.get(position)
        holder.binding.menuName.text = item?.name
        holder.binding.menuPrice.text = "Price: R${item?.price}"

        holder.binding.addToCartButton.setOnClickListener {
            item?.totalInCart = 1
            if (item != null) {
                clickListener.addToCartClickListener(item)
            }
            holder.binding.addMoreLayout.visibility = View.VISIBLE
            holder.binding.addToCartButton.visibility = View.GONE
            holder.binding.tvCount.text = item?.totalInCart.toString()
        }

        holder.binding.imageMinus.setOnClickListener {
            var total: Int = item?.totalInCart!!
            total--
            if (total > 0) {
                item.totalInCart = total
                clickListener.updateCartClickListener(item)
                holder.binding.tvCount.text = item.totalInCart.toString()
            } else {
                item.totalInCart = total
                clickListener.removeFromCartClickListener(item)
                holder.binding.addMoreLayout.visibility = View.GONE
                holder.binding.addToCartButton.visibility = View.VISIBLE
            }
        }

        holder.binding.imageAddOne.setOnClickListener {
            var total: Int = item?.totalInCart!!
            total++
            if (total <= 10) {
                item.totalInCart = total
                clickListener.updateCartClickListener(item)
                holder.binding.tvCount.text = total.toString()
            }
        }

        Glide.with(holder.binding.thumbImage).load(item?.url).into(holder.binding.thumbImage)


    }

    override fun getItemCount(): Int {
        return menuList?.size ?: 0
    }

    interface MenuItemClickListener {
        fun addToCartClickListener(menu: Menu)
        fun updateCartClickListener(menu: Menu)
        fun removeFromCartClickListener(menu: Menu)
    }
}

class MenuViewHolder(var binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root)

