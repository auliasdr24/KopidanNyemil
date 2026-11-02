package com.aulia.kopidannyemil

// Singleton object buat simpen data user yang login
// Bisa diakses dari Register, Login, Profile, dll
object User {
    private var currentUser: UserAccount? = null

    // Daftar user yang sudah register (dummy database)
    private val registeredUsers = mutableListOf<UserAccount>()

    // Register user baru
    fun register(user: UserAccount): Boolean {
        // Cek apakah username sudah dipakai
        if (registeredUsers.any { it.username == user.username }) {
            return false  // username sudah ada
        }
        registeredUsers.add(user)
        currentUser = user  // auto-login setelah register
        return true
    }

    // Login user
    fun login(usernameOrEmail: String, password: String): Boolean {
        val user = registeredUsers.find {
            (it.username == usernameOrEmail || it.email == usernameOrEmail) &&
                    it.password == password
        }
        if (user != null) {
            currentUser = user
            return true
        }
        return false
    }

    // Ambil user yang sedang login
    fun getCurrentUser(): UserAccount? = currentUser

    // Update profile user yang sedang login
    fun updateProfile(nama: String, email: String, noHp: String) {
        currentUser?.let { user ->
            val updated = user.copy(namaLengkap = nama, email = email, noHp = noHp)
            // Update di list registeredUsers juga
            val index = registeredUsers.indexOfFirst { it.username == user.username }
            if (index >= 0) registeredUsers[index] = updated
            currentUser = updated
        }
    }

    // Logout
    fun logout() {
        currentUser = null
    }

    // Cek apakah ada user yang login
    fun isLoggedIn(): Boolean = currentUser != null
}