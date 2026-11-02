package com.aulia.kopidannyemil

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class HomeActivity : AppCompatActivity() {

    private lateinit var menuAdapter: MenuAdapter
    private lateinit var rvMenu: RecyclerView
    private lateinit var etSearch: EditText
    private lateinit var chipGroup: ChipGroup
    private lateinit var bottomNav: BottomNavigationView

    private val allMenu = mutableListOf<Menu>()
    private var currentFilter = "Semua"
    private var currentSearch = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val username = intent.getStringExtra("USERNAME") ?: "Aulia"

        // Ambil data user dari User singleton
        val currentUser = User.getCurrentUser()
        val namaLengkap = currentUser?.namaLengkap ?: username

        val tvGreeting = findViewById<TextView>(R.id.tvGreeting)
        tvGreeting.text = "Halo, $namaLengkap!"

        // Inisialisasi view
        rvMenu = findViewById(R.id.rvMenu)
        etSearch = findViewById(R.id.etSearch)
        chipGroup = findViewById(R.id.chipGroup)
        bottomNav = findViewById(R.id.bottomNav)

        // Isi allMenu dengan dummy data
        allMenu.clear()
        allMenu.addAll(getDummyMenu())

        // Setup adapter
        menuAdapter = MenuAdapter(allMenu, onItemClick = { menu ->
            val intent = Intent(this, OrderActivity::class.java)
            intent.putExtra("USERNAME", username)
            startActivity(intent)
        }, onAddClick = { menu ->
            Cart.addItem(menu)
            Toast.makeText(this, "${menu.name} ditambahkan ke pesanan", Toast.LENGTH_SHORT).show()
            updateCartBadge()
        })
        rvMenu.layoutManager = LinearLayoutManager(this)
        rvMenu.adapter = menuAdapter
        rvMenu.setHasFixedSize(true)

        // Setup search
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentSearch = s.toString().lowercase()
                applyFilter()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Setup chip filter
        chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val chip = findViewById<Chip>(checkedIds[0])
                currentFilter = chip.text.toString()
                applyFilter()
            }
        }

        // Setup bottom nav
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

        updateCartBadge()
    }

    private fun applyFilter() {
        val filtered = allMenu.filter { menu ->
            val categoryMatch = currentFilter == "Semua" || menu.category == currentFilter
            val searchMatch = currentSearch.isEmpty() ||
                    menu.name.lowercase().contains(currentSearch) ||
                    menu.description.lowercase().contains(currentSearch)
            categoryMatch && searchMatch
        }
        menuAdapter.filterList(filtered)
    }

    private fun updateCartBadge() {
        val totalQty = Cart.getTotalQty()
        if (totalQty > 0) {
            bottomNav.getOrCreateBadge(R.id.nav_order).apply {
                isVisible = true
                number = totalQty
                backgroundColor = resources.getColor(R.color.red, theme)
            }
        } else {
            bottomNav.removeBadge(R.id.nav_order)
        }
    }

    override fun onResume() {
        super.onResume()
        updateCartBadge()
    }

    private fun getDummyMenu(): List<Menu> {
        return listOf(
            Menu("Kopi Susu Gula Aren", "Rp 22.000", "Kopi susu dengan gula aren asli", R.drawable.menu_kopi_susu, "Kopi"),
            Menu("Cappuccino", "Rp 25.000", "Espresso dengan susu dan foam", R.drawable.menu_cappuccino, "Kopi"),
            Menu("Kopi Tubruk", "Rp 15.000", "Kopi tubruk tradisional khas Indonesia", R.drawable.menu_kopi_susu, "Kopi"),
            Menu("Es Kopi Krim", "Rp 28.000", "Es kopi dengan krim lembut", R.drawable.menu_cappuccino, "Kopi"),
            Menu("Matcha Latte", "Rp 27.000", "Matcha premium dengan susu segar", R.drawable.menu_matcha, "Non-Kopi"),
            Menu("Chocolate Latte", "Rp 23.000", "Coklat hangat dengan susu", R.drawable.menu_kopi_susu, "Non-Kopi"),
            Menu("Sourdough Loaf", "Rp 35.000", "Roti sourdough dipanggang segar", R.drawable.menu_sourdough, "Roti"),
            Menu("Butter Croissant", "Rp 18.000", "Croissant renyah dengan mentega", R.drawable.menu_donat, "Roti"),
            Menu("Donat Cokelat", "Rp 12.000", "Donat lembut dengan topping cokelat", R.drawable.menu_donat, "Nyemil"),
            Menu("Roti Bakar Pisang", "Rp 15.000", "Roti bakar dengan pisang dan keju", R.drawable.menu_sourdough, "Nyemil"),
            Menu("Cheesecake Strawberry", "Rp 30.000", "Cheesecake creamy dengan strawberry", R.drawable.menu_donat, "Dessert"),
            Menu("Pudding Caramel", "Rp 17.000", "Pudding lembut dengan saus caramel", R.drawable.menu_matcha, "Dessert")
        )
    }
}