/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;
import java.util.ArrayList;
import Model.KomputerDAO;
import Model.TransaksiDAO;
import Model.Komputer;
import Model.KomputerReguler;
import Model.KomputerVIP;
/**
 *
 * @author LOQ
 */

public class KomputerService {
    private KomputerDAO komputerDao;
    private TransaksiDAO transaksiDao;
 
    public KomputerService() {
        komputerDao = new KomputerDAO();
        transaksiDao = new TransaksiDAO();
    }
 
    public boolean adaData() {
        return komputerDao.hitung() > 0;
    }
 
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
 
    public ArrayList<Komputer> ambilSemua() {
        return komputerDao.ambilSemua();
    }
 
    public Komputer cariByNomor(int nomor) {
        return komputerDao.ambilByNomor(nomor);
    }
 
    public boolean ubah(int nomor, String spesifikasiBaru) {
        return komputerDao.ubahSpesifikasi(nomor, spesifikasiBaru);
    }
 
    public String hapus(int nomor) {
        if (komputerDao.ambilByNomor(nomor) == null) {
            return "tidak_ditemukan";
        }
        if (transaksiDao.adaTransaksiUntuk(nomor)) {
            return "punya_transaksi";
        }
        return komputerDao.hapus(nomor) ? "berhasil" : "gagal";
    }
 
    public String cekKetersediaan(int nomor) {
        Komputer k = komputerDao.ambilByNomor(nomor);
        if (k == null) {
            return "tidak_ditemukan";
        }
        if (k.isDipakai()) {
            return "sudah_dipakai";
        }
        return "tersedia";
    }
 
    public boolean booking(int nomor) {
        return komputerDao.ubahStatus(nomor, true);
    }
 
    public String kosongkan(int nomor) {
        Komputer k = komputerDao.ambilByNomor(nomor);
        if (k == null) {
            return "tidak_ditemukan";
        }
        if (!k.isDipakai()) {
            return "belum_dipakai";
        }
        komputerDao.ubahStatus(nomor, false);
        return "berhasil";
    }
}