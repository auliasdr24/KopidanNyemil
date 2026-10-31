package com.aulia.kopidannyemil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class OrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val username = intent.getStringExtra("USERNAME") ?: "Aulia"
        val menuName = intent.getStringExtra("MENU_NAME")
        val menuPrice = intent.getStringExtra("MENU_PRICE")
        val menuImage = intent.getIntExtra("MENU_IMAGE", 0)  // ← AMBIL GAMBAR

        val etNama = findViewById<EditText>(R.id.etNamaLengkap)
        val etAlamat = findViewById<EditText>(R.id.etAlamat)
        val etKota = findViewById<EditText>(R.id.etKota)
        val etKodePos = findViewById<EditText>(R.id.etKodePos)
        val etNoHp = findViewById<EditText>(R.id.etNoHp)
        val etNotes = findViewById<EditText>(R.id.etNotes)
        val btnOrder = findViewById<Button>(R.id.btnOrder)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        val ivMenuImage = findViewById<ImageView>(R.id.ivMenuImage)
        val tvMenuName = findViewById<TextView>(R.id.tvMenuName)
        val tvMenuPrice = findViewById<TextView>(R.id.tvMenuPrice)

        etNama.setText(username)

        // Tampilkan menu yang dipilih (kalau ada)
        if (menuName != null && menuPrice != null) {
            tvMenuName.text = menuName
            tvMenuPrice.text = menuPrice
            if (menuImage != 0) {
                ivMenuImage.setImageResource(menuImage)
                ivMenuImage.visibility = android.view.View.VISIBLE  // ← TAMPILKAN
            }
        } else {
            // Belum ada menu dipilih → sembunyikan gambar
            ivMenuImage.visibility = android.view.View.GONE  // ← SEMBUNYIKAN
        }

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
            } else if (menuName == null) {
                Toast.makeText(this, "Pilih menu dulu dari Home!", Toast.LENGTH_SHORT).show()
            } else {
                // Pindah ke ConfirmationActivity, bawa data menu + notes
                val intent = Intent(this, ConfirmationActivity::class.java)
                intent.putExtra("USERNAME", username)
                intent.putExtra("MENU_NAME", menuName)
                intent.putExtra("MENU_PRICE", menuPrice)
                intent.putExtra("MENU_IMAGE", menuImage)
                intent.putExtra("NOTES", notes)
                startActivity(intent)
                finish()
            }
        }

        bottomNav.selectedItemId = R.id.nav_order
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("USERNAME", username)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_order -> true
                R.id.nav_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("USERNAME", username)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}