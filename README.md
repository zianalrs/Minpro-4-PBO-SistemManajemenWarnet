# Sistem Manajemen Warnet

---

## Deskripsi Singkat Program

Program ini adalah aplikasi CRUD berbasis console yang dibuat menggunakan bahasa Java dengan menerapkan konsep Object Oriented Programming (OOP) dan terhubung ke database MySQL. Program ini dibuat untuk mensimulasikan sistem manajemen komputer pada sebuah warnet.

Program dapat digunakan untuk melihat, menambah, mengubah, dan menghapus data komputer, melakukan booking komputer, serta melihat riwayat transaksi. Komputer pada program dibedakan menjadi dua kategori dengan tarif tetap, yaitu Komputer Reguler (Rp7.000/jam) dan Komputer VIP (Rp14.000/jam dengan fasilitas tambahan).

Data program disimpan secara permanen di database `db_warnet`, sehingga tetap ada meskipun program ditutup dan dijalankan kembali. Koneksi ke database memakai JDBC dengan `PreparedStatement`, dan seluruh perintah SQL dikumpulkan di class DAO. Data awal (dummy data) tidak ditulis di dalam kode Java, tetapi diisi lewat file `db_warnet.sql` yang di-import ke database.

---

## Fitur Program

- Melihat seluruh data komputer
- Melakukan booking komputer beserta perhitungan total biaya
- Melihat riwayat transaksi
- Menambah data komputer dengan memilih kategori Reguler atau VIP
- Mengubah spesifikasi komputer
- Menghapus data komputer
- Mengosongkan status komputer
- Data tersimpan permanen di database MySQL
- Validasi input agar program tidak error saat salah tipe data
- Program berjalan terus sampai user memilih menu keluar

---

## Penjelasan Struktur Package

Program dibagi menjadi tujuh package berdasarkan fungsi masing-masing, dengan mengacu pada struktur MVC (Model, View, Controller) yang dilengkapi lapisan `Service` untuk aturan program dan class DAO untuk akses database. Alur pemanggilannya adalah `Controller` → `Service` → `DAO` → database, sedangkan hasilnya ditampilkan oleh `View`.

[ss struktur package]

### 1. Package Model

Package `Model` berisi class yang merepresentasikan data program, serta class DAO yang mengakses data tersebut di database.

- `Komputer` (abstract class, berfungsi sebagai superclass)
- `KomputerReguler` (subclass dari `Komputer`)
- `KomputerVIP` (subclass dari `Komputer`)
- `Transaksi`
- `KomputerDAO` (akses database untuk tabel `komputer`)
- `TransaksiDAO` (akses database untuk tabel `transaksi`)

Class DAO berada di package ini mengikuti contoh struktur proyek JDBC pada materi. Perintah SQL hanya ditulis di kedua class DAO.

### 2. Package View

Package `View` berisi class `View` yang khusus bertugas menampilkan teks ke layar, seperti menu utama, menu kelola komputer, daftar komputer, daftar transaksi, informasi booking, dan pesan-pesan kepada user. Class ini tidak membaca input dan tidak memproses data.

### 3. Package Controller

Package `Controller` berisi class `Controller` yang menjadi penghubung antara `View` dan bagian data. Class ini menjalankan perulangan menu, membaca pilihan user, menjalankan percabangan `switch-case`, memanggil `Service` untuk memproses data, lalu meminta `View` menampilkan hasilnya.

### 4. Package Service

Package `Service` berisi logic atau aturan program, misalnya pengecekan apakah komputer tersedia untuk dibooking atau apakah komputer boleh dihapus.

- `KomputerService` mengelola aturan yang berkaitan dengan data komputer.
- `TransaksiService` mengelola aturan yang berkaitan dengan transaksi booking.

Class di package ini tidak menulis perintah SQL. Untuk mengakses database, keduanya memanggil class DAO.

### 5. Package Config

Package `Config` berisi class `Koneksi` yang membuat koneksi ke database.

### 6. Package Util

Package `Util` berisi class `Validasi`, yaitu class bantuan untuk membaca input dari user agar program tidak error ketika user memasukkan tipe data yang tidak sesuai, serta memastikan angka bernilai lebih dari 0 dan teks tidak kosong.

### 7. Package Main

Package `Main` berisi class `Warnet` yang merupakan entry point dari program. Class ini hanya membuat objek `Controller` lalu menjalankannya.

Selain package di atas, file `db_warnet.sql` disimpan di folder `database` pada root repository.

---

## Penjelasan Alur Program

Program dimulai dengan menjalankan class `Warnet`, yang membuat objek `Controller` lalu memanggil method `jalankan()`. Di awal, `Controller` mengecek koneksi ke database; jika koneksi gagal, program menampilkan pesan dan berhenti. Jika berhasil, sistem menampilkan menu utama yang berisi pilihan untuk melihat komputer, melakukan booking, melihat riwayat transaksi, mengelola komputer, dan keluar dari program.

[ss menu utama]

User memilih menu dengan memasukkan angka sesuai pilihan. Program menggunakan perulangan `while` sehingga menu utama akan terus ditampilkan sampai user memilih menu keluar, dan setiap pilihan diproses menggunakan `switch-case`. Seluruh input dibaca lewat class `Validasi`, sehingga jika user memasukkan huruf pada input angka, program tidak berhenti dan meminta user mengulang input.

---

### 1. Lihat Komputer

Menu **Lihat Komputer** menampilkan seluruh data komputer yang tersimpan di database. `Controller` meminta data ke `KomputerService`, yang mengambilnya lewat `KomputerDAO` dengan query `SELECT`. Hasilnya berupa `ArrayList<Komputer>` yang kemudian ditampilkan oleh `View` menggunakan perulangan `for`. Informasi yang ditampilkan meliputi nomor, kategori, spesifikasi, harga per jam, dan status; untuk komputer VIP ditampilkan juga fasilitas tambahannya. Karena data awal sudah ada di database, menu ini langsung menampilkan data tanpa perlu input terlebih dahulu.

[ss lihat komputer]

---

### 2. Booking Komputer

Menu **Booking Komputer** digunakan untuk melakukan penyewaan komputer oleh pelanggan.

- User memasukkan nomor komputer yang ingin digunakan.
- Program mengecek ketersediaan komputer di database. Jika nomor tidak ditemukan atau komputer sedang digunakan, program menampilkan pesan dan proses booking tidak dilanjutkan.
- Jika komputer tersedia, user diminta memasukkan nama pelanggan dan durasi penggunaan dalam jam. Kedua input ini diminta ulang jika kosong atau tidak lebih dari 0.
- Program menghitung total biaya berdasarkan durasi dan harga komputer per jam.

```text
Total Bayar = Durasi × Harga per Jam
```

- Setelah seluruh data valid, status komputer di database diubah menjadi sedang dipakai dan transaksi disimpan ke tabel `transaksi`. ID transaksi dibuat otomatis oleh database.

[ss booking komputer]

---

### 3. Riwayat Transaksi

Menu **Riwayat Transaksi** menampilkan seluruh transaksi booking yang tersimpan di tabel `transaksi`, meliputi ID transaksi, nama pelanggan, nomor komputer, durasi penggunaan, dan total pembayaran. Jika belum ada transaksi, program menampilkan pesan bahwa belum ada transaksi.

[ss riwayat transaksi]

---

### 4. Kelola Komputer

Menu **Kelola Komputer** membuka submenu dengan perulangannya sendiri, berisi lima pilihan: Tambah Komputer, Ubah Data Komputer, Hapus Komputer, Kosongkan Komputer, dan Kembali ke menu utama. Pilihan Ubah, Hapus, dan Kosongkan ditolak dengan pesan yang jelas jika data komputer masih kosong.

[ss menu kelola komputer]

#### 4.1 Tambah Komputer

User memasukkan nomor komputer, spesifikasi, dan memilih kategori Reguler atau VIP. Harga per jam tidak diinput manual karena sudah ditentukan oleh kategori. Jika nomor komputer sudah digunakan, komputer baru tidak dapat ditambahkan dengan nomor yang sama. Data yang valid disimpan ke tabel `komputer`.

[ss tambah komputer]

#### 4.2 Ubah Data Komputer

User memasukkan nomor komputer yang ingin diubah, lalu memasukkan spesifikasi baru. Jika nomor ditemukan, spesifikasi diperbarui di database; jika tidak, program menampilkan pesan bahwa komputer tidak ditemukan.

[ss ubah komputer]

#### 4.3 Hapus Komputer

User memasukkan nomor komputer yang ingin dihapus. Jika komputer ditemukan dan belum memiliki riwayat transaksi, datanya dihapus dari database. Komputer yang sudah memiliki riwayat transaksi tidak dapat dihapus, dan program menampilkan pesan yang menjelaskan alasannya, supaya data transaksi tidak kehilangan komputer yang terkait.

[ss hapus komputer]

#### 4.4 Kosongkan Komputer

User memasukkan nomor komputer yang ingin dikosongkan. Jika komputer sedang dipakai, statusnya di database diubah kembali menjadi kosong sehingga bisa dibooking lagi. Jika komputer memang sudah kosong, program memberi tahu hal tersebut.

[ss kosongkan komputer]

---

### 5. Keluar Program

Menu **Keluar** menghentikan perulangan `while` pada menu utama sehingga program selesai dijalankan.

[ss keluar program]

---

## Penjelasan Penerapan Encapsulation dan Inheritance

### Encapsulation

Encapsulation diterapkan dengan menggunakan access modifier `private` pada atribut di class model, sehingga atribut tidak dapat diakses langsung dari luar class.

```java
private final int nomor;
private String spesifikasi;
private boolean dipakai;
```

Atribut yang nilainya tidak pernah berubah dideklarasikan `final`, yaitu `nomor` pada `Komputer`, seluruh atribut pada `Transaksi`, serta konstanta harga dan fasilitas pada subclass. Atribut `final` diisi satu kali lewat constructor dan tidak memiliki setter.

Untuk atribut yang memang dapat berubah, program menggunakan getter dan setter. Validasi nilai diletakkan langsung di dalam setter:

```java
public String getSpesifikasi() {
    return spesifikasi;
}

public void setSpesifikasi(String spesifikasi) {
    if (spesifikasi != null && !spesifikasi.trim().isEmpty()) {
        this.spesifikasi = spesifikasi;
    } else {
        System.out.println("Spesifikasi tidak boleh kosong.");
    }
}
```

Dengan demikian, data pada objek hanya dapat diubah melalui method yang telah disediakan dan dikontrol oleh class itu sendiri.

### Inheritance

Inheritance diterapkan pada class komputer. Class `Komputer` digunakan sebagai superclass, sedangkan `KomputerReguler` dan `KomputerVIP` merupakan subclass yang mewarisi atribut dan method dari `Komputer` menggunakan `extends`.

```java
public class KomputerReguler extends Komputer {

    public KomputerReguler(int nomor, String spesifikasi) {
        super(nomor, spesifikasi);
    }
}
```

Pada constructor subclass digunakan `super(...)` untuk memanggil constructor dari superclass. Dengan pewarisan ini, atribut dan method yang sama untuk semua komputer cukup ditulis satu kali di `Komputer`, dan subclass hanya menambahkan hal yang membedakannya, seperti harga sewa dan fasilitas tambahan pada `KomputerVIP`.

---

## Penjelasan Penerapan Polymorphism dan Abstraction

### Polymorphism

**Method overriding.** `KomputerReguler` dan `KomputerVIP` meng-override method `getKategori()` dan `getHargaPerJam()` dari superclass dengan isi yang berbeda sesuai jenis komputer.

```java
@Override
public String getKategori() {
    return "Reguler";
}

@Override
public double getHargaPerJam() {
    return HARGA_PER_JAM;
}
```

Class `KomputerVIP` juga meng-override `tampilkanInfo()` untuk menambahkan informasi fasilitas:

```java
@Override
public void tampilkanInfo() {
    super.tampilkanInfo();
    System.out.println("Fasilitas     : " + FASILITAS_TAMBAHAN);
}
```

Polymorphism juga terlihat pada `ArrayList<Komputer>` yang dikembalikan oleh `KomputerDAO`. Saat membaca data dari database, DAO membuat objek `KomputerReguler` atau `KomputerVIP` sesuai kolom `kategori`, lalu menyimpan keduanya dalam satu daftar bertipe `Komputer`. Ketika `tampilkanInfo()` dipanggil pada setiap elemen, Java otomatis menjalankan versi method yang sesuai dengan jenis objeknya.

```java
Komputer komputer;
if (kategori.equalsIgnoreCase("VIP")) {
    komputer = new KomputerVIP(nomor, spesifikasi);
} else {
    komputer = new KomputerReguler(nomor, spesifikasi);
}
```

**Method overloading.** Method `tambah()` dibuat dengan dua versi parameter yang berbeda, baik pada `KomputerService` maupun `TransaksiService`.

```java
public boolean tambah(Komputer komputer) {
    return komputerDao.tambah(komputer);
}

public boolean tambah(int nomor, String spesifikasi, String kategori) {
    if (kategori.equalsIgnoreCase("VIP")) {
        return tambah(new KomputerVIP(nomor, spesifikasi));
    } else {
        return tambah(new KomputerReguler(nomor, spesifikasi));
    }
}
```

Pada `TransaksiService`, versi kedua dari `tambah()` menerima data transaksi mentah lalu membuat objek `Transaksi`. ID diberi nilai 0 karena ID sebenarnya dibuat otomatis oleh database (`AUTO_INCREMENT`).

```java
public boolean tambah(Transaksi transaksi) {
    return dao.tambah(transaksi);
}

public boolean tambah(String namaPelanggan, int nomorKomputer, int durasiJam, double totalBayar) {
    return tambah(new Transaksi(0, namaPelanggan, nomorKomputer, durasiJam, totalBayar));
}
```

### Abstraction

Abstraction diterapkan dengan menjadikan `Komputer` sebagai `abstract class`, karena dalam sistem ini tidak pernah ada komputer tanpa kategori; yang ada selalu `KomputerReguler` atau `KomputerVIP`. Dengan begitu, objek `Komputer` tidak dapat dibuat secara langsung.

Di dalamnya terdapat dua abstract method, yaitu `getKategori()` dan `getHargaPerJam()`, yang hanya berisi deklarasi tanpa isi.

```java
public abstract class Komputer {

    public abstract String getKategori();

    public abstract double getHargaPerJam();
}
```

Setiap subclass wajib mengimplementasikan kedua method tersebut, dan kompilasi akan gagal jika ada subclass yang tidak melakukannya. Method lain seperti `tampilkanInfo()` tetap memiliki isi di superclass karena logikanya sama untuk semua kategori.

---

## Penjelasan Penerapan JDBC

JDBC (Java Database Connectivity) digunakan untuk menghubungkan program Java dengan database MySQL. Dengan JDBC, data tidak lagi disimpan di `ArrayList` yang hilang saat program ditutup, melainkan tersimpan permanen di database `db_warnet`.

### 1. Struktur Database

Database `db_warnet` memiliki dua tabel, yaitu `komputer` dan `transaksi`. Kolom `nomor_komputer` pada tabel `transaksi` merupakan foreign key yang merujuk ke kolom `nomor` pada tabel `komputer`, sedangkan `id_transaksi` dibuat otomatis (`AUTO_INCREMENT`).

```sql
CREATE TABLE komputer (
    nomor INT PRIMARY KEY,
    spesifikasi VARCHAR(255) NOT NULL,
    kategori ENUM('Reguler', 'VIP') NOT NULL,
    dipakai BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE transaksi (
    id_transaksi INT AUTO_INCREMENT PRIMARY KEY,
    nama_pelanggan VARCHAR(100) NOT NULL,
    nomor_komputer INT NOT NULL,
    durasi_jam INT NOT NULL,
    total_bayar DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (nomor_komputer) REFERENCES komputer(nomor)
);
```

[ss struktur tabel di database]

### 2. Koneksi ke Database

Driver JDBC dipasang lewat `pom.xml` dengan dependency `mysql-connector-j`.

```xml
    <dependencies>
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>8.3.0</version>
    </dependency>
</dependencies>
```

Koneksi dibuat oleh class `Koneksi` di package `Config`. Class ini menyimpan URL, username, dan password database, lalu method `getConnection()` membuat koneksi baru hanya jika koneksi belum ada atau sudah terputus. Jika koneksi gagal, `SQLException` ditangkap dan pesan kesalahannya ditampilkan.

```java
private static final String URL = "jdbc:mysql://localhost:3306/db_warnet";
private static final String USER = "root";
private static final String PASS = "";

private static Connection conn;

public static Connection getConnection() {
    try {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(URL, USER, PASS);
        }
    } catch (SQLException e) {
        System.out.println("Koneksi GAGAL: " + e.getMessage());
    }
    return conn;
}
```

### 3. PreparedStatement

Seluruh perintah SQL dijalankan menggunakan `PreparedStatement`. Nilai yang berasal dari user tidak digabung langsung ke teks query, tetapi diisi lewat tanda `?` kemudian diset satu per satu berdasarkan urutannya. Cara ini mencegah SQL Injection karena input user selalu diperlakukan sebagai data, bukan bagian dari perintah SQL.

```java
String sql = "INSERT INTO komputer (nomor, spesifikasi, kategori, dipakai) VALUES (?, ?, ?, ?)";
Connection conn = Koneksi.getConnection();

try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
    pstmt.setInt(1, komputer.getNomor());
    pstmt.setString(2, komputer.getSpesifikasi());
    pstmt.setString(3, komputer.getKategori());
    pstmt.setBoolean(4, komputer.isDipakai());
    return pstmt.executeUpdate() > 0;
} catch (SQLException e) {
    System.out.println("Gagal menambah komputer: " + e.getMessage());
}
return false;
```

Untuk perintah `SELECT`, hasil query dibaca lewat `ResultSet`, kemudian setiap baris diubah menjadi objek `Komputer` atau `Transaksi` dan dimasukkan ke `ArrayList`.

### 4. DAO (Data Access Object)

DAO digunakan untuk memisahkan logic program dari logic akses database. Semua perintah SQL hanya boleh ditulis di class DAO, sedangkan `Service` dan `Controller` hanya memanggil method-nya.

| Class | Method | Perintah SQL |
|---|---|---|
| `KomputerDAO` | `hitung()` | `SELECT COUNT(*)` |
| `KomputerDAO` | `ambilSemua()` | `SELECT` seluruh komputer |
| `KomputerDAO` | `ambilByNomor()` | `SELECT ... WHERE nomor = ?` |
| `KomputerDAO` | `tambah()` | `INSERT` |
| `KomputerDAO` | `ubahSpesifikasi()` | `UPDATE spesifikasi` |
| `KomputerDAO` | `ubahStatus()` | `UPDATE dipakai` |
| `KomputerDAO` | `hapus()` | `DELETE` |
| `TransaksiDAO` | `tambah()` | `INSERT` |
| `TransaksiDAO` | `ambilSemua()` | `SELECT` seluruh transaksi |
| `TransaksiDAO` | `ambilIdTerakhir()` | `SELECT MAX(id_transaksi)` |
| `TransaksiDAO` | `adaTransaksiUntuk()` | `SELECT COUNT(*) ... WHERE nomor_komputer = ?` |
