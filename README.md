# Asclepius

## English Version

Asclepius is a mobile application designed to detect skin cancer using on-device machine learning. This application provides an easy-to-use interface for users to scan their skin conditions and receive an instant classification result.

### Main Features

- **On-Device Machine Learning**: Uses a pre-trained model to classify skin conditions without requiring an internet connection.
- **Skin Scan**: Allows users to capture or upload an image of their skin for analysis.
- **Instant Diagnosis**: Provides immediate classification results along with a confidence score.
- **History Tracking**: Stores past scan results for user reference.
- **User-Friendly Interface**: Simple and intuitive design for all users.

### How to Run the Project

1. **Clone the Repository**:
   ```sh
   git clone git clone https://github.com/KILABID/SkinCancerDetector.git
   ```
2. **Open in Android Studio**
3. **Sync Gradle**
4. **Run the Application** on an emulator or a physical device.

### Technologies Used

- **Kotlin** for Android development
- **TensorFlow Lite** for on-device machine learning
- **Jetpack Compose** for UI
- **CameraX** for image capture

### Main Dependencies

Check `build.gradle.kts` for required dependencies, including:

```kotlin
implementation("org.tensorflow:tensorflow-lite-task-vision:<latest_version>")
implementation("androidx.camera:camera-core:<latest_version>")
```

### Contribution

If you want to contribute, please submit a pull request or report issues in the Issues section.


---

## Versi Indonesia

Asclepius adalah aplikasi mobile yang dirancang untuk mendeteksi kanker kulit menggunakan machine learning secara on-device. Aplikasi ini menyediakan antarmuka yang mudah digunakan untuk memindai kondisi kulit dan mendapatkan hasil klasifikasi secara instan.

### Fitur Utama

- **Machine Learning On-Device**: Menggunakan model yang telah dilatih sebelumnya untuk mengklasifikasikan kondisi kulit tanpa memerlukan koneksi internet.
- **Pemindaian Kulit**: Memungkinkan pengguna untuk mengambil atau mengunggah gambar kulit mereka untuk dianalisis.
- **Diagnosis Instan**: Memberikan hasil klasifikasi langsung dengan tingkat kepercayaan.
- **Riwayat Pemindaian**: Menyimpan hasil pemindaian sebelumnya untuk referensi pengguna.
- **Antarmuka Ramah Pengguna**: Desain sederhana dan intuitif untuk semua pengguna.

### Cara Menjalankan Proyek

1. **Clone Repository**:
   ```sh
   git clone https://github.com/KILABID/SkinCancerDetector.git
   ```
2. **Buka di Android Studio**
3. **Sinkronisasi Gradle**
4. **Jalankan Aplikasi** di emulator atau perangkat fisik.

### Teknologi yang Digunakan

- **Kotlin** untuk pengembangan Android
- **TensorFlow Lite** untuk machine learning on-device
- **Jetpack Compose** untuk UI
- **CameraX** untuk pengambilan gambar

### Dependencies Utama

Pastikan untuk memeriksa file `build.gradle.kts` untuk dependensi yang digunakan, termasuk:

```kotlin
implementation("org.tensorflow:tensorflow-lite-task-vision:<latest_version>")
implementation("androidx.camera:camera-core:<latest_version>")
```

### Kontribusi

Jika ingin berkontribusi, silakan buat pull request atau laporkan masalah melalui Issues.


---

Selamat menggunakan Asclepius! 🎉

