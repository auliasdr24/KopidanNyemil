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

class LoginActivity : AppCompatActivity() {

    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLoginNow)
        val tvNoAccount = findViewById<TextView>(R.id.tvNoAccount)
        val btnTogglePassword = findViewById<ImageButton>(R.id.btnTogglePassword)

        // Toggle show/hide password
        btnTogglePassword?.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                etPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            etPassword.setSelection(etPassword.text.length)
        }

        btnBack.setOnClickListener {
            finish()
        }

        // Tombol Login
        btnLogin.setOnClickListener {
            val usernameOrEmail = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (usernameOrEmail.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Isi username/email dan password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Cek login ke User singleton
            val success = User.login(usernameOrEmail, password)
            if (success) {
                val user = User.getCurrentUser()!!
                Toast.makeText(this, "Login berhasil! Halo ${user.namaLengkap}", Toast.LENGTH_LONG).show()

                // Pindah ke HomeActivity, bawa data user lengkap
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("USERNAME", user.username)
                intent.putExtra("NAMA_LENGKAP", user.namaLengkap)
                intent.putExtra("EMAIL", user.email)
                intent.putExtra("NO_HP", user.noHp)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Username/email atau password salah!", Toast.LENGTH_SHORT).show()
            }
        }

        tvNoAccount.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}