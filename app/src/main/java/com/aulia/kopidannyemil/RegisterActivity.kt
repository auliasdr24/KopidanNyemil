package com.aulia.kopidannyemil

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inisialisasi view
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val etNama = findViewById<EditText>(R.id.etNamaLengkap)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etNoHp = findViewById<EditText>(R.id.etNoHp)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegisterNow)
        val tvHaveAccount = findViewById<TextView>(R.id.tvHaveAccount)
        val btnTogglePassword = findViewById<ImageButton>(R.id.btnTogglePassword)
        val btnToggleConfirmPassword = findViewById<ImageButton>(R.id.btnToggleConfirmPassword)

        // Tombol Back
        btnBack.setOnClickListener {
            finish()
        }

        // Toggle show/hide password
        btnTogglePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                etPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                btnTogglePassword.setImageResource(android.R.drawable.ic_menu_view)
            } else {
                etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                btnTogglePassword.setImageResource(android.R.drawable.ic_menu_view)
            }
            // Pindah cursor ke akhir
            etPassword.setSelection(etPassword.text.length)
        }

        // Toggle show/hide confirm password
        btnToggleConfirmPassword.setOnClickListener {
            isConfirmPasswordVisible = !isConfirmPasswordVisible
            if (isConfirmPasswordVisible) {
                etConfirmPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                etConfirmPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            etConfirmPassword.setSelection(etConfirmPassword.text.length)
        }

        // Tombol Register
        btnRegister.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val username = etUsername.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val noHp = etNoHp.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            // === VALIDASI ===
            if (nama.isEmpty() || username.isEmpty() || email.isEmpty() ||
                noHp.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Lengkapi semua data!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi email
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Format email tidak valid!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi no HP (min 10 digit)
            if (noHp.length < 10) {
                Toast.makeText(this, "No. HP minimal 10 digit!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi password (min 6 karakter)
            if (password.length < 6) {
                Toast.makeText(this, "Password minimal 6 karakter!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi konfirmasi password
            if (password != confirmPassword) {
                Toast.makeText(this, "Konfirmasi password tidak cocok!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Simpan ke User singleton
            val userAccount = UserAccount(nama, username, email, noHp, password)
            val success = User.register(userAccount)

            if (success) {
                Toast.makeText(this, "Register berhasil! Selamat datang $nama", Toast.LENGTH_LONG).show()
                // Pindah ke LoginActivity (auto-login optional, di sini suruh login dulu)
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Username sudah dipakai! Pilih yang lain.", Toast.LENGTH_SHORT).show()
            }
        }

        // Link "Sudah punya akun" → pindah ke LoginActivity
        tvHaveAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}