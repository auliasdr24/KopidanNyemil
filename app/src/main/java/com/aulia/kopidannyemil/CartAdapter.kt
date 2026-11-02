package com.aulia.kopidannyemil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private val cartItems: MutableList<CartItem>,  // ← UBAH JADI MutableList
    private val onPlusClick: (CartItem) -> Unit,
    private val onMinusClick: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivCartImage: ImageView = view.findViewById(R.id.ivCartImage)
        val tvCartName: TextView = view.findViewById(R.id.tvCartName)
        val tvCartPrice: TextView = view.findViewById(R.id.tvCartPrice)
        val tvCartSubtotal: TextView = view.findViewById(R.id.tvCartSubtotal)
        val tvCartQty: TextView = view.findViewById(R.id.tvCartQty)
        val btnPlus: ImageButton = view.findViewById(R.id.btnPlus)
        val btnMinus: ImageButton = view.findViewById(R.id.btnMinus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.ivCartImage.setImageResource(item.menu.imageRes)
        holder.tvCartName.text = item.menu.name
        holder.tvCartPrice.text = item.menu.price
        holder.tvCartSubtotal.text = "Subtotal: ${item.subtotalFormatted}"
        holder.tvCartQty.text = item.qty.toString()

        holder.btnPlus.setOnClickListener { onPlusClick(item) }
        holder.btnMinus.setOnClickListener { onMinusClick(item) }
    }

    override fun getItemCount(): Int = cartItems.size
}