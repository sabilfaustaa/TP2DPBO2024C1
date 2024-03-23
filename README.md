# LP6DPBO2024C1

Saya Muhamad Sabil Fausta NIM 2210142 mengerjakan Latihan 6 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek
untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

# Desain Program

nim: Atribut ini merupakan pengidentifikasi unik untuk setiap mahasiswa. NIM (Nomor Induk Mahasiswa) digunakan untuk membedakan antara satu mahasiswa dengan mahasiswa lainnya. Ini adalah string yang menyimpan nomor induk khusus yang diberikan kepada mahasiswa oleh institusi pendidikannya.

nama: Atribut ini menyimpan nama dari mahasiswa tersebut. Ini adalah string yang merepresentasikan nama lengkap mahasiswa, memungkinkan identifikasi pribadi di luar penggunaan NIM.

umur: Atribut ini merepresentasikan umur mahasiswa dalam tahun. Ini adalah integer yang menyimpan usia mahasiswa, memberikan konteks umur relatif mahasiswa dalam populasi akademik.

jurusan: Atribut ini menyimpan informasi tentang jurusan akademik yang diikuti oleh mahasiswa tersebut dalam lembaga pendidikan. Jurusan bisa beragam tergantung pada penawaran akademik institusi tersebut, seperti "Teknik Informatika", "Psikologi", "Kedokteran", dll.

tahunMasuk: Atribut ini menunjukkan tahun masuk atau tahun penerimaan mahasiswa ke dalam program pendidikannya. Ini adalah integer yang merepresentasikan tahun kalender ketika mahasiswa dimulai di institusinya, yang bisa digunakan untuk menghitung lama studi atau untuk keperluan administratif lainnya.

semester: Atribut ini menyimpan nilai semester saat ini dari mahasiswa. Ini adalah integer yang menunjukkan di semester berapa mahasiswa tersebut berada dalam perjalanan akademiknya, memberikan indikasi progres studinya.

mataKuliah: Atribut ini merupakan daftar mata kuliah yang diambil oleh mahasiswa tersebut. Ini adalah List<String> yang berisi nama-nama mata kuliah yang sedang atau telah diikuti oleh mahasiswa, merefleksikan beban studi dan area keilmuan yang dipelajari.

# Alur Program dan Screenshoot

![image](https://github.com/sabilfaustaa/LP5DPBO2024C1/assets/61264687/25bf1866-9b87-4069-90a0-5916a0556745)

## Menambahkan Data Mahasiswa Baru
1. Isi field NIM dengan Nomor Induk Mahasiswa yang unik.
2. Isi field Nama dengan nama lengkap mahasiswa.
3. Pilih Jenis Kelamin dari dropdown menu.
4. Pilih Tahun Masuk dari dropdown menu yang menunjukkan tahun mahasiswa tersebut mulai masuk universitas.
5. Pilih Semester saat ini dari dropdown menu.
6. Pilih Mata Kuliah yang diambil dengan menekan dan memilih mata kuliah di daftar. Untuk memilih lebih dari satu mata kuliah, gunakan klik sambil menahan tombol Ctrl (Cmd di Mac) untuk pilihan individual atau Shift untuk rentang pilihan.
7. Klik tombol Add untuk menambahkan data mahasiswa ke dalam daftar.

## Mengedit Data Mahasiswa

1. Klik pada baris mahasiswa yang datanya ingin diubah di tabel.
2. Data mahasiswa yang dipilih akan muncul di field-form di atas.
3. Edit informasi di field-form tersebut sesuai kebutuhan.
4. Klik tombol Add yang akan berubah menjadi Update setelah Anda memilih mahasiswa dari tabel.

## Menghapus Data Mahasiswa

1. Klik pada baris mahasiswa yang datanya ingin dihapus di tabel. Baris tersebut akan di-highlight.
2. Klik tombol Delete.
3. Akan muncul konfirmasi pop-up. Pilih Yes untuk mengkonfirmasi penghapusan data.
