/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;
import java.util.ArrayList;
import Model.TransaksiDAO;
import Model.Transaksi;
/**
 *
 * @author LOQ
 */
public class TransaksiService {
    private TransaksiDAO dao;
 
    public TransaksiService() {
        dao = new TransaksiDAO();
    }
 
    public boolean tambah(Transaksi transaksi) {
        return dao.tambah(transaksi);
    }
 
    public boolean tambah(String namaPelanggan, int nomorKomputer, int durasiJam, double totalBayar) {
        return tambah(new Transaksi(0, namaPelanggan, nomorKomputer, durasiJam, totalBayar));
    }
 
    public ArrayList<Transaksi> ambilSemua() {
        return dao.ambilSemua();
    }
 
    public int idTerakhir() {
        return dao.ambilIdTerakhir();
    }
}
