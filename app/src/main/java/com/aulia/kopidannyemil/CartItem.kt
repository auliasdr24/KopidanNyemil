package com.aulia.kopidannyemil

data class CartItem(
    val menu: Menu,
    var qty: Int = 1
) {
    // Hitung subtotal untuk item ini (qty × harga)
    // Asumsi harga format "Rp 22.000" → ambil angka 22000
    val subtotal: Int
        get() {
            // Parse "Rp 22.000" jadi 22000
            val numeric = menu.price.replace("Rp ", "").replace(".", "").replace(",", "")
            return try {
                numeric.toInt() * qty
            } catch (e: NumberFormatException) {
                0
            }
        }

    // Subtotal format "Rp 22.000"
    val subtotalFormatted: String
        get() = "Rp ${"%,d".format(subtotal).replace(",", ".")}"
}