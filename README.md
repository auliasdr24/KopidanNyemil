# ☕ Kopi dan Nyemil

Mobile application untuk pemesanan kopi dan nyemil (camilan) — Tugas Pemrograman Aplikasi Mobile (PAM).

![Platform](https://img.shields.io/badge/Platform-Android-green) ![Language](https://img.shields.io/badge/Language-Kotlin-orange) ![Min SDK](https://img.shields.io/badge/Min%20SDK-24-blue)

## 📖 Deskripsi

**Kopi dan Nyemil** adalah aplikasi mobile pemesanan kopi dan camilan dengan tema warna kopi yang hangat (coklat & krem). Aplikasi ini dibuat untuk memenuhi tugas UTS mata kuliah Pemrograman Aplikasi Mobile (PAM).

Aplikasi ini menggunakan **dummy data** (data statis) sehingga tidak memerlukan database. Pengguna dapat memilih menu dari daftar, mengisi alamat pengiriman, dan melihat konfirmasi pesanan.

## ✨ Fitur

- 🔐 Autentikasi (Register & Login sederhana dengan validasi)
- 🏠 Home Page dengan daftar 5 menu (kopi & nyemil)
- 🛍️ Order Page dengan form alamat pengiriman lengkap
- 👤 Profile Page untuk edit nama & logout
- ✅ Confirmation Page setelah order berhasil
- 📱 Bottom Navigation (Home / Order / Profile) dengan animasi
- 🎬 Animasi slide smooth antar activity
- 🎨 Tema warna kopi (coklat, krem, karamel)
- 🔤 Font custom (Poppins dari Google Fonts)
- 🖼️ Gambar menu & avatar profile

## 🛠️ Teknologi

- **Kotlin** — Bahasa pemrograman utama
- **Android Studio** — IDE
- **XML Layout** — UI dengan Views tradisional
- **Material Components** — UI components (BottomNavigationView, dll)
- **RecyclerView** — Untuk menampilkan list menu
- **Intent** — Navigasi antar activity
- **Vector Drawable** — Icon custom
- **Poppins Font** — Custom font dari Google Fonts

## 📋 Halaman (8 Total)

| # | Halaman | File |
|---|---------|------|
| 1 | Welcome / Splash | WelcomeActivity.kt |
| 2 | Sign Up (pilih Register / Login) | SignUpActivity.kt |
| 3 | Register | RegisterActivity.kt |
| 4 | Login | LoginActivity.kt |
| 5 | Home (list menu) | HomeActivity.kt |
| 6 | Profile | ProfileActivity.kt |
| 7 | Order (alamat pengiriman) | OrderActivity.kt |
| 8 | Confirmation | ConfirmationActivity.kt |

## 📦 Struktur Project

    app/
    └── src/main/
        ├── AndroidManifest.xml
        ├── java/com/aulia/kopidannyemil/
        │   ├── WelcomeActivity.kt
        │   ├── SignUpActivity.kt
        │   ├── RegisterActivity.kt
        │   ├── LoginActivity.kt
        │   ├── HomeActivity.kt
        │   ├── ProfileActivity.kt
        │   ├── OrderActivity.kt
        │   ├── ConfirmationActivity.kt
        │   ├── Menu.kt
        │   └── MenuAdapter.kt
        └── res/
            ├── anim/
            ├── drawable/
            ├── font/
            ├── layout/
            ├── menu/
            └── values/

## 🚀 Cara Menjalankan

### Prasyarat
- Android Studio (versi terbaru)
- JDK 11+
- Android SDK Minimum API 24 (Android 7.0 Nougat)

### Langkah-langkah

1. **Clone repository**
   git clone https://github.com/auliasdr24/kopi-dan-nyemil.git


2. **Buka di Android Studio**
- File → Open → pilih folder project
- Tunggu Gradle sync selesai

3. **Jalankan di emulator atau device fisik**
- Pilih device di dropdown atas
- Klik tombol Run (▶) atau tekan Shift + F10

### Login Demo

Aplikasi menggunakan dummy data (tanpa database), jadi login bisa pakai username & password apa saja selama tidak kosong.

## 🎨 Tema Warna

| Warna | Hex | Penggunaan |
|-------|-----|------------|
| Coklat Tua | #3E2723 | Heading, header |
| Coklat Kopi | #6F4E37 | Primary (tombol) |
| Coklat Soft | #A0826D | Teks sekunder |
| Krem | #F5EFE6 | Background |
| Karamel | #D4A574 | Aksen |

## 📸 Screenshot

### Welcome Page
![Welcome](screenshots/welcome.jpeg)

### Sign Up Page
![Sign Up](screenshots/signup.jpeg)

### Register Page
![Register](screenshots/register.jpeg)

### Login Page
![Login](screenshots/login.jpeg)

### Home Page
![Home](screenshots/home.jpeg)

### Profile Page
![Profile](screenshots/profile.jpeg)

### Order Page
![Order](screenshots/order.jpeg)

### Confirmation Page
![Confirmation](screenshots/confirmation.jpeg)

## 👤 Author

- **Nama**: Aulia
- **GitHub**: [@auliasdr24](https://github.com/auliasdr24)

## 📜 License

Project ini dibuat untuk tujuan edukasi (tugas kuliah). Bebas dipakai untuk belajar.

Font Poppins menggunakan SIL Open Font License 1.1.
