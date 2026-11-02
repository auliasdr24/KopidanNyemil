package com.aulia.kopidannyemil

// Singleton object buat simpan keranjang global
// Bisa diakses dari Home, Order, Confirmation
object Cart {
    private val items = mutableListOf<CartItem>()

    // Tambah menu ke cart. Kalau sudah ada, qty++
    fun addItem(menu: Menu) {
        val existing = items.find { it.menu.name == menu.name }
        if (existing != null) {
            existing.qty++
        } else {
            items.add(CartItem(menu, 1))
        }
    }

    // Kurang qty item. Kalau qty 0, hapus dari cart
    fun removeItem(cartItem: CartItem) {
        cartItem.qty--
        if (cartItem.qty <= 0) {
            items.remove(cartItem)
        }
    }

    // Hapus item tertentu dari cart
    fun deleteItem(cartItem: CartItem) {
        items.remove(cartItem)
    }

    // Ambil semua item di cart
    fun getItems(): MutableList<CartItem> = items

    // Hitung total item (total qty, bukan jumlah baris)
    fun getTotalQty(): Int = items.sumOf { it.qty }

    // Hitung total harga semua item
    fun getTotalPrice(): Int = items.sumOf { it.subtotal }

    // Total harga format "Rp 100.000"
    fun getTotalPriceFormatted(): String =
        "Rp ${"%,d".format(getTotalPrice()).replace(",", ".")}"

    // Cek apakah cart kosong
    fun isEmpty(): Boolean = items.isEmpty()

    // Kosongkan cart (setelah checkout)
    fun clear() {
        items.clear()
    }
}