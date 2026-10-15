/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import java.util.Scanner;
import Config.Koneksi;
import Model.Komputer;
import Service.KomputerService;
import Service.TransaksiService;
import Util.Validasi;
import View.View;
/**
 *
 * @author LOQ
 */
public class Controller {
    private KomputerService komputerService;
    private TransaksiService transaksiService;
    private Validasi validasi;
    private View view;
    private Scanner input;
 
    public Controller() {
        komputerService = new KomputerService();
        transaksiService = new TransaksiService();
        validasi = new Validasi();
        view = new View();
        input = new Scanner(System.in);
    }
 
    public void jalankan() {
        if (Koneksi.getConnection() == null) {
            view.tampilkanPesan("Program dihentikan karena tidak dapat terhubung ke database.");
            return;
        }
 
        boolean lanjut = true;
 
        while (lanjut) {
            view.tampilkanMenuUtama();
            int pilihan = validasi.bacaInt(input, "Pilih menu >> ");
 
            switch (pilihan) {
                case 1:
                    view.tampilkanDaftarKomputer(komputerService.ambilSemua());
                    break;
 
                case 2:
                    prosesBooking();
                    break;
 
                case 3:
                    view.tampilkanDaftarTransaksi(transaksiService.ambilSemua());
                    break;
 
                case 4:
                    kelolaKomputer();
                    break;
 
                case 5:
                    lanjut = false;
                    view.tampilkanPesan("Terima kasih sudah berkunjung di Warnet kami!.");
                    break;
 
                default:
                    view.tampilkanPesan("Pilihan tidak tersedia, coba lagi.");
            }
        }
    }
 
    private void prosesBooking() {
        if (!komputerService.adaData()) {
            view.tampilkanPesan("Belum ada data komputer, tidak bisa dibooking.");
            return;
        }
 
        int nomorBooking = validasi.bacaIntPositif(input, "Masukkan nomor komputer yang ingin dibooking: ");
        String status = komputerService.cekKetersediaan(nomorBooking);
 
        if (status.equals("tersedia")) {
            Komputer k = komputerService.cariByNomor(nomorBooking);
            String namaPelanggan = validasi.bacaTeks(input, "Nama Pelanggan : ");
            int durasiJam = validasi.bacaIntPositif(input, "Durasi (jam)   : ");
            double totalBayar = durasiJam * k.getHargaPerJam();
 
            if (!komputerService.booking(nomorBooking)) {
                view.tampilkanPesan("Booking gagal diproses.");
                return;
            }
 
            transaksiService.tambah(namaPelanggan, k.getNomor(), durasiJam, totalBayar);
            view.tampilkanInfoBooking(transaksiService.idTerakhir(), totalBayar);
        } else if (status.equals("sudah_dipakai")) {
            view.tampilkanPesan("Komputer sedang dipakai orang lain.");
        } else {
            view.tampilkanPesan("Komputer dengan nomor tersebut tidak ditemukan.");
        }
    }
 
    private void kelolaKomputer() {
        boolean kembali = false;
 
        while (!kembali) {
            view.tampilkanMenuKelola();
            int pilihan = validasi.bacaInt(input, "Pilih menu >> ");
 
            switch (pilihan) {
                case 1:
                    tambahKomputer();
                    break;
                case 2:
                    ubahKomputer();
                    break;
                case 3:
                    hapusKomputer();
                    break;
                case 4:
                    kosongkanKomputer();
                    break;
                case 5:
                    kembali = true;
                    break;
                default:
                    view.tampilkanPesan("Pilihan tidak tersedia, coba lagi.");
            }
        }
    }
 
    private void tambahKomputer() {
        int nomor = validasi.bacaIntPositif(input, "Nomor Komputer : ");
 
        if (komputerService.cariByNomor(nomor) != null) {
            view.tampilkanPesan("Nomor komputer sudah dipakai, gunakan nomor lain.");
            return;
        }
 
        String spesifikasi = validasi.bacaTeks(input, "Spesifikasi    : ");
 
        view.tampilkanMenuKategori();
        int kategori = validasi.bacaInt(input, "Pilih kategori : ");
        String namaKategori = (kategori == 2) ? "VIP" : "Reguler";
 
        boolean berhasil = komputerService.tambah(nomor, spesifikasi, namaKategori);
        view.tampilkanPesan(berhasil ? "Komputer berhasil ditambahkan." : "Komputer gagal ditambahkan.");
    }
 
    private void ubahKomputer() {
        if (!komputerService.adaData()) {
            view.tampilkanPesan("Belum ada data komputer, tidak bisa diubah.");
            return;
        }
 
        int nomorUbah = validasi.bacaIntPositif(input, "Masukkan nomor komputer yang ingin diubah: ");
        String spesifikasiBaru = validasi.bacaTeks(input, "Spesifikasi baru   : ");
 
        boolean berhasil = komputerService.ubah(nomorUbah, spesifikasiBaru);
        view.tampilkanPesan(berhasil ? "Data berhasil diubah." : "Komputer dengan nomor tersebut tidak ditemukan.");
    }
 
    private void hapusKomputer() {
        if (!komputerService.adaData()) {
            view.tampilkanPesan("Belum ada data komputer, tidak bisa dihapus.");
            return;
        }
 
        int nomorHapus = validasi.bacaIntPositif(input, "Masukkan nomor komputer yang ingin dihapus: ");
 
        String hasil = komputerService.hapus(nomorHapus);
        if (hasil.equals("berhasil")) {
            view.tampilkanPesan("Data berhasil dihapus.");
        } else if (hasil.equals("punya_transaksi")) {
            view.tampilkanPesan("Komputer tidak bisa dihapus karena sudah memiliki riwayat transaksi.");
        } else if (hasil.equals("tidak_ditemukan")) {
            view.tampilkanPesan("Komputer dengan nomor tersebut tidak ditemukan.");
        } else {
            view.tampilkanPesan("Komputer gagal dihapus.");
        }
    }
 
    private void kosongkanKomputer() {
        if (!komputerService.adaData()) {
            view.tampilkanPesan("Belum ada data komputer, tidak bisa dikosongkan.");
            return;
        }
 
        int nomorKosong = validasi.bacaIntPositif(input, "Masukkan nomor komputer yang ingin dikosongkan: ");
 
        String hasil = komputerService.kosongkan(nomorKosong);
        if (hasil.equals("berhasil")) {
            view.tampilkanPesan("Komputer berhasil dikosongkan.");
        } else if (hasil.equals("belum_dipakai")) {
            view.tampilkanPesan("Komputer ini memang sedang kosong.");
        } else {
            view.tampilkanPesan("Komputer dengan nomor tersebut tidak ditemukan.");
        }
    }
}
