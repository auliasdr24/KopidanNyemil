package com.aulia.kopidannyemil

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val username = intent.getStringExtra("USERNAME") ?: "Aulia"

        val tvGreeting = findViewById<TextView>(R.id.tvGreeting)
        tvGreeting.text = "Halo, $username!"

        val rvMenu = findViewById<RecyclerView>(R.id.rvMenu)
        rvMenu.layoutManager = LinearLayoutManager(this)
        rvMenu.adapter = MenuAdapter(getDummyMenu()) { menu ->
            val intent = Intent(this, OrderActivity::class.java)
            intent.putExtra("USERNAME", username)
            intent.putExtra("MENU_NAME", menu.name)
            intent.putExtra("MENU_PRICE", menu.price)
            intent.putExtra("MENU_IMAGE", menu.imageRes)  // ← KIRIM GAMBAR
            startActivity(intent)
        }
        rvMenu.setHasFixedSize(true)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.nav_home
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_order -> {
                    val intent = Intent(this, OrderActivity::class.java)
                    intent.putExtra("USERNAME", username)
                    startActivity(intent)
                    true
                }
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

    private fun getDummyMenu(): List<Menu> {
        return listOf(
            Menu("Kopi Susu Gula Aren", "Rp 22.000", "Kopi susu dengan gula aren asli", R.drawable.menu_kopi_susu),
            Menu("Donat Cokelat", "Rp 12.000", "Donat lembut dengan topping cokelat", R.drawable.menu_donat),
            Menu("Sourdough Loaf", "Rp 35.000", "Roti sourdough dipanggang segar", R.drawable.menu_sourdough),
            Menu("Cappuccino", "Rp 25.000", "Espresso dengan susu dan foam", R.drawable.menu_cappuccino),
            Menu("Matcha Latte", "Rp 27.000", "Matcha premium dengan susu segar", R.drawable.menu_matcha)
        )
    }
}