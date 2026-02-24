# Refleksi 1

- Menambahkan findByProductId di productRepository.java
- Menambahkan delete() di productRepository.java

Clean code principles that have been applied:

- Penamaan yang jelas untuk method
- Menggunakan Optional untuk menghindari NullPointerException

Secure coding practices:
- Menggunakan th:text dibandingkan th:utext untuk mencegah XSS
- Menggunakan UUID untuk id agar lebih sulit ditebak
- Memvalidasi nama dan harga produk sebelum disimpan

Improvement dari source code sebelumnya:
- Mengubah tipe data untuk id menjadi UUID agar lebih aman dan unik

# Refleksi 2

## 1

Setelah mengerjakan unit test, saya merasa lebih yakin bahwa kode yang saya tulis tidak memiliki bug.

Idealnya, dalam satu class, test class kita dapat meng-cover semua method dan variasi input yang mungkin terjadi pada method tersebut. 

Dengan membuat banyak variasi test case, kita dapat memastikan bahwa setiap kemungkinan skenario telah diuji dan berfungsi sesuai harapan.

Ketika code coverage bisa mencapai 100%, itu berarti semua baris kode telah diuji. Namun, ini tidak selalu menjamin bahwa kode bebas dari bug. Bug yang mungkin terjadi bisa saja berasal dari logika bisnis yang salah. 

Kode baru akan mengurangi clean code karena terjadi redundansi (duplikat) dengan kode di CreateProductFunctionalTest.java. Improvement-nya, kita dapat membuat sebuah method di CreateProductFunctionalTest.java yang digunakan untuk test flow menghitung banyaknya item di list produk.





# Modul 2

## Refleksi 1

Tautan Koyeb: https://mutual-elk-aufasatriatama-c1bddbb6.koyeb.app/

## 1. Code quality issue yang diperbaiki

- Token-Permissions

    Sebelumnya, workflow memiliki akses token dengan izin penuh, yang dapat menimbulkan risiko keamanan jika token tersebut disalahgunakan. Dengan membatasi izin token hanya untuk membaca konten repository, kita dapat mengurangi risiko tersebut.

    Menambahkan `permissions:
  contents: read` pada workflow untuk membatasi akses token hanya untuk membaca konten repository.

- Dependency-Update-Tool

    Ini terjadi karena sebelumnya tidak ada dependabot.yml yang mengatur pembaruan otomatis untuk dependensi. Dengan menambahkan dependabot.yml, kita dapat memastikan bahwa dependensi selalu diperbarui ke versi terbaru yang aman.

    Solusi:
        Menambahkan file dependabot.yml dengan konfigurasi untuk memantau pembaruan dependensi secara otomatis.

- Branch-Protection

    Terjadi karena sebelumnya tidak ada aturan perlindungan branch yang diterapkan pada branch utama. 

    Solusi:
        Menambahkan aturan perlindungan branch pada branch utama untuk mencegah perubahan langsung tanpa review. (pakai pull request)


## 2

Syarat CI CD yang sudah dipenuhi:

- Trigger workflow secara otomatis pada setiap push:
    Ya. Ini karena setiap workflow di project ini akan jalan otomatis setiap kali ada push ke repository.

- Build dan test dijalankan otomatis:
    Ya. workflow di project ini sudah mengandung langkah untuk build dan test project secara otomatis. (Menggunakan sonarcloud)

- Deployment otomatis:
    Ya. workflow di project ini sudah mengandung langkah untuk melakukan deployment otomatis ke Koyeb setelah build dan test berhasil.

Dengan demikian, syarat-syarat CI/CD sudah dipenuhi dalam project ini.


