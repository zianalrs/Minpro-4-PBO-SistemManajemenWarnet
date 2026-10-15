/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
import java.util.ArrayList;
import Model.Komputer;
import Model.Transaksi;

/**
 *
 * @author LOQ
 */
public class View {
 
    public void tampilkanMenuUtama() {
        System.out.println("\n==========================");
        System.out.println(" SELAMAT DATANG DI WARNET ");
        System.out.println("==========================");
        System.out.println("1. Lihat Komputer");
        System.out.println("2. Booking Komputer");
        System.out.println("3. Riwayat Transaksi");
        System.out.println("4. Kelola Komputer");
        System.out.println("5. Keluar");
        System.out.println("==========================");
    }
 
    public void tampilkanMenuKelola() {
        System.out.println("\n==========================");
        System.out.println("      KELOLA KOMPUTER     ");
        System.out.println("==========================");
        System.out.println("1. Tambah Komputer");
        System.out.println("2. Ubah Data Komputer");
        System.out.println("3. Hapus Komputer");
        System.out.println("4. Kosongkan Komputer");
        System.out.println("5. Kembali");
        System.out.println("==========================");
    }
 
    public void tampilkanMenuKategori() {
        System.out.println("Kategori Komputer:");
        System.out.println("1. Reguler (Rp7000/jam)");
        System.out.println("2. VIP (Rp14000/jam)");
    }
 
    public void tampilkanDaftarKomputer(ArrayList<Komputer> daftar) {
        if (daftar.isEmpty()) {
            System.out.println("Belum ada data komputer.");
            return;
        }
        for (int i = 0; i < daftar.size(); i++) {
            System.out.println("--------------------");
            daftar.get(i).tampilkanInfo();
        }
    }
 
    public void tampilkanDaftarTransaksi(ArrayList<Transaksi> daftar) {
        if (daftar.isEmpty()) {
            System.out.println("Belum ada transaksi.");
            return;
        }
        for (int i = 0; i < daftar.size(); i++) {
            System.out.println("--------------------");
            daftar.get(i).tampilkanInfo();
        }
    }
 
    public void tampilkanInfoBooking(int idTransaksi, double totalBayar) {
        System.out.println("Booking berhasil.");
        System.out.println("ID Transaksi : " + idTransaksi);
        System.out.println("Total Bayar  : Rp" + totalBayar);
    }
 
    public void tampilkanPesan(String pesan) {
        System.out.println(pesan);
    }
}