package com.aulia.kopidannyemil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(
    private var menuList: List<Menu>,  // var biar bisa di-filter
    private val onItemClick: (Menu) -> Unit,
    private val onAddClick: (Menu) -> Unit  // ← BARU: callback buat tombol +
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivMenuImage: ImageView = view.findViewById(R.id.ivMenuImage)
        val tvName: TextView = view.findViewById(R.id.tvMenuName)
        val tvDesc: TextView = view.findViewById(R.id.tvMenuDesc)
        val tvPrice: TextView = view.findViewById(R.id.tvMenuPrice)
        val btnAdd: ImageButton = view.findViewById(R.id.btnAdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menu = menuList[position]
        holder.ivMenuImage.setImageResource(menu.imageRes)
        holder.tvName.text = menu.name
        holder.tvDesc.text = menu.description
        holder.tvPrice.text = menu.price

        // Klik tombol + → panggil onAddClick
        holder.btnAdd.setOnClickListener {
            onAddClick(menu)
        }

        // Klik card (optional) → panggil onItemClick
        holder.itemView.setOnClickListener {
            onItemClick(menu)
        }
    }

    override fun getItemCount(): Int = menuList.size

    // FUNGSI FILTER — dipanggil dari HomeActivity
    fun filterList(filteredList: List<Menu>) {
        menuList = filteredList
        notifyDataSetChanged()
    }
}