package com.aulia.kopidannyemil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val username = intent.getStringExtra("USERNAME") ?: "Aulia"

        // Inisialisasi view
        val tvProfileName = findViewById<TextView>(R.id.tvProfileName)
        val tvProfileUsername = findViewById<TextView>(R.id.tvProfileUsername)
        val etNama = findViewById<EditText>(R.id.etNama)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etNoHp = findViewById<EditText>(R.id.etNoHp)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        // === Ambil data dari User singleton (RELIABLE) ===
        val currentUser = User.getCurrentUser()
        val namaLengkap = currentUser?.namaLengkap ?: username
        val email = currentUser?.email ?: ""
        val noHp = currentUser?.noHp ?: ""

        // Set tampilan awal
        tvProfileName.text = namaLengkap
        tvProfileUsername.text = "@${username.lowercase()}"
        etNama.setText(namaLengkap)
        etEmail.setText(email)
        etNoHp.setText(noHp)

        // Tombol Save → update User singleton
        btnSave.setOnClickListener {
            val newNama = etNama.text.toString().trim()
            val newEmail = etEmail.text.toString().trim()
            val newNoHp = etNoHp.text.toString().trim()

            if (newNama.isEmpty()) {
                Toast.makeText(this, "Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (newEmail.isNotEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
                Toast.makeText(this, "Format email tidak valid!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (newNoHp.isNotEmpty() && newNoHp.length < 10) {
                Toast.makeText(this, "No. HP minimal 10 digit!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Update User singleton
            User.updateProfile(newNama, newEmail, newNoHp)

            // Update tampilan
            tvProfileName.text = newNama
            Toast.makeText(this, "Profil berhasil disimpan!", Toast.LENGTH_SHORT).show()
        }

        // Tombol Logout → clear User + balik Welcome
        btnLogout.setOnClickListener {
            User.logout()
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // Bottom navigation
        bottomNav.selectedItemId = R.id.nav_profile
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("USERNAME", username)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_order -> {
                    val intent = Intent(this, OrderActivity::class.java)
                    intent.putExtra("USERNAME", username)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_profile -> true
                else -> false
            }
        }
    }
}