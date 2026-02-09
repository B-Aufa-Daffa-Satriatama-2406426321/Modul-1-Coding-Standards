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