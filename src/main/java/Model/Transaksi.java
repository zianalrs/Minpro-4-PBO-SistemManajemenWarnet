/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author LOQ
 */
public class Transaksi {
    private final int idTransaksi;
    private final String namaPelanggan;
    private final int nomorKomputer;
    private final int durasiJam;
    private final double totalBayar;
 
    public Transaksi(int idTransaksi, String namaPelanggan, int nomorKomputer, int durasiJam, double totalBayar) {
        if (namaPelanggan == null || namaPelanggan.trim().isEmpty()) {
            System.out.println("Nama pelanggan tidak boleh kosong.");
        }
        if (durasiJam <= 0) {
            System.out.println("Durasi harus lebih dari 0 jam.");
        }
 
        this.idTransaksi = idTransaksi;
        this.namaPelanggan = namaPelanggan;
        this.nomorKomputer = nomorKomputer;
        this.durasiJam = durasiJam;
        this.totalBayar = totalBayar;
    }
 
    public int getIdTransaksi() {
        return idTransaksi;
    }
 
    public String getNamaPelanggan() {
        return namaPelanggan;
    }
 
    public int getNomorKomputer() {
        return nomorKomputer;
    }
 
    public int getDurasiJam() {
        return durasiJam;
    }
 
    public double getTotalBayar() {
        return totalBayar;
    }
 
    public void tampilkanInfo() {
        System.out.println("ID Transaksi   : " + idTransaksi);
        System.out.println("Pelanggan      : " + namaPelanggan);
        System.out.println("Nomor Komputer : " + nomorKomputer);
        System.out.println("Durasi         : " + durasiJam + " jam");
        System.out.println("Total Bayar    : Rp" + totalBayar);
    }
}