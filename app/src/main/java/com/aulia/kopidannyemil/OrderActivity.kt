package com.aulia.kopidannyemil

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class OrderActivity : AppCompatActivity() {

    private lateinit var rvCart: RecyclerView
    private lateinit var tvEmptyCart: TextView
    private lateinit var tvSubtotal: TextView
    private lateinit var tvTax: TextView           // ← GANTI: pajak
    private lateinit var tvService: TextView        // ← BARU: biaya layanan (opsional)
    private lateinit var tvTotal: TextView
    private lateinit var sectionTotal: View
    private lateinit var cartAdapter: CartAdapter

    // Konstanta
    private val TAX_PERCENT = 0.05       // Pajak 5%
    private val SERVICE_FEE = 2000       // Biaya layanan Rp 2.000 (opsional)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val username = intent.getStringExtra("USERNAME") ?: "Aulia"

        // Inisialisasi view
        val etNama = findViewById<EditText>(R.id.etNamaLengkap)
        val etAlamat = findViewById<EditText>(R.id.etAlamat)
        val etKota = findViewById<EditText>(R.id.etKota)
        val etKodePos = findViewById<EditText>(R.id.etKodePos)
        val etNoHp = findViewById<EditText>(R.id.etNoHp)
        val etNotes = findViewById<EditText>(R.id.etNotes)
        val btnOrder = findViewById<Button>(R.id.btnOrder)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        rvCart = findViewById(R.id.rvCart)
        tvEmptyCart = findViewById(R.id.tvEmptyCart)
        tvSubtotal = findViewById(R.id.tvSubtotal)
        tvTax = findViewById(R.id.tvOngkir)        // ← RE-USE id lama, ganti jadi tvTax
        tvService = findViewById(R.id.tvService)   // ← BARU
        tvTotal = findViewById(R.id.tvTotal)
        sectionTotal = findViewById(R.id.sectionTotal)

        etNama.setText(username)

        // === Pre-fill nama dari User singleton (lebih reliable) ===
        val currentUser = User.getCurrentUser()
        if (currentUser != null) {
            etNama.setText(currentUser.namaLengkap)
        }

        // Setup cart adapter
        cartAdapter = CartAdapter(
            cartItems = Cart.getItems(),
            onPlusClick = { item ->
                Cart.addItem(item.menu)
                refreshCart()
            },
            onMinusClick = { item ->
                val namaMenu = item.menu.name
                val qtySebelum = item.qty
                val position = Cart.getItems().indexOf(item)

                Cart.removeItem(item)

                if (qtySebelum <= 1) {
                    Toast.makeText(this, "$namaMenu dihapus dari pesanan", Toast.LENGTH_SHORT).show()
                    cartAdapter.notifyItemRemoved(position)
                    cartAdapter.notifyItemRangeChanged(position, Cart.getItems().size)
                }
                refreshCart()
            }
        )
        rvCart.layoutManager = LinearLayoutManager(this)
        rvCart.adapter = cartAdapter
        rvCart.setHasFixedSize(true)

        // === SWIPE TO DELETE ===
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                if (position < 0 || position >= Cart.getItems().size) return

                val item = Cart.getItems()[position]
                val namaMenu = item.menu.name

                Cart.deleteItem(item)
                cartAdapter.notifyItemRemoved(position)
                cartAdapter.notifyItemRangeChanged(position, Cart.getItems().size)
                refreshCart()

                Toast.makeText(this@OrderActivity,
                    "$namaMenu dihapus dari pesanan", Toast.LENGTH_SHORT).show()
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val deleteBg = ColorDrawable(Color.parseColor("#F44336"))

                if (dX > 0) {
                    deleteBg.setBounds(itemView.left, itemView.top,
                        itemView.left + dX.toInt(), itemView.bottom)
                } else {
                    deleteBg.setBounds(itemView.right + dX.toInt(), itemView.top,
                        itemView.right, itemView.bottom)
                }
                deleteBg.draw(c)

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        })
        itemTouchHelper.attachToRecyclerView(rvCart)

        refreshCart()

        // Tombol Order
        btnOrder.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val alamat = etAlamat.text.toString().trim()
            val kota = etKota.text.toString().trim()
            val kodePos = etKodePos.text.toString().trim()
            val noHp = etNoHp.text.toString().trim()
            val notes = etNotes.text.toString().trim()

            if (nama.isEmpty() || alamat.isEmpty() || kota.isEmpty() ||
                kodePos.isEmpty() || noHp.isEmpty()) {
                Toast.makeText(this, "Lengkapi semua data alamat!", Toast.LENGTH_SHORT).show()
            } else if (Cart.isEmpty()) {
                Toast.makeText(this, "Pesanan masih kosong! Tambah menu dulu.", Toast.LENGTH_SHORT).show()
            } else {
                val orderDetail = Cart.getItems().joinToString("\n") { item ->
                    "${item.qty}x ${item.menu.name} - ${item.subtotalFormatted}"
                }

                // Hitung total final: subtotal + pajak + biaya layanan
                val subtotal = Cart.getTotalPrice()
                val tax = (subtotal * TAX_PERCENT).toInt()
                val total = subtotal + tax + SERVICE_FEE
                val totalFormatted = "Rp ${"%,d".format(total).replace(",", ".")}"

                val intent = Intent(this, ConfirmationActivity::class.java)
                intent.putExtra("USERNAME", username)
                intent.putExtra("TOTAL", totalFormatted)
                intent.putExtra("ORDER_DETAIL", orderDetail)
                intent.putExtra("NOTES", notes)
                startActivity(intent)
                finish()
            }
        }

        // Bottom navigation
        bottomNav.selectedItemId = R.id.nav_order
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("USERNAME", username)
                    startActivity(intent)
                    true
                }
                R.id.nav_order -> true
                R.id.nav_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("USERNAME", username)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun refreshCart() {
        cartAdapter.notifyDataSetChanged()
        if (Cart.isEmpty()) {
            rvCart.visibility = View.GONE
            sectionTotal.visibility = View.GONE
            tvEmptyCart.visibility = View.VISIBLE
        } else {
            rvCart.visibility = View.VISIBLE
            sectionTotal.visibility = View.VISIBLE
            tvEmptyCart.visibility = View.GONE

            // Hitung subtotal, pajak 5%, biaya layanan, total
            val subtotal = Cart.getTotalPrice()
            val tax = (subtotal * TAX_PERCENT).toInt()
            val total = subtotal + tax + SERVICE_FEE

            // Format ke "Rp X.XXX"
            tvSubtotal.text = "Rp ${"%,d".format(subtotal).replace(",", ".")}"
            tvTax.text = "Rp ${"%,d".format(tax).replace(",", ".")} (5%)"
            tvService.text = "Rp ${"%,d".format(SERVICE_FEE).replace(",", ".")}"
            tvTotal.text = "Rp ${"%,d".format(total).replace(",", ".")}"
        }
    }
}