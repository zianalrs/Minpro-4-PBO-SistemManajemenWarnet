/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Config.Koneksi;
/**
 *
 * @author LOQ
 */
public class TransaksiDAO {
 
    public boolean tambah(Transaksi transaksi) {
        String sql = "INSERT INTO transaksi (nama_pelanggan, nomor_komputer, durasi_jam, total_bayar) VALUES (?, ?, ?, ?)";
        Connection conn = Koneksi.getConnection();
 
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, transaksi.getNamaPelanggan());
            pstmt.setInt(2, transaksi.getNomorKomputer());
            pstmt.setInt(3, transaksi.getDurasiJam());
            pstmt.setDouble(4, transaksi.getTotalBayar());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal menyimpan transaksi: " + e.getMessage());
        }
        return false;
    }
 
    public ArrayList<Transaksi> ambilSemua() {
        ArrayList<Transaksi> daftar = new ArrayList<>();
        String sql = "SELECT id_transaksi, nama_pelanggan, nomor_komputer, durasi_jam, total_bayar FROM transaksi ORDER BY id_transaksi";
        Connection conn = Koneksi.getConnection();
 
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                daftar.add(new Transaksi(
                        rs.getInt("id_transaksi"),
                        rs.getString("nama_pelanggan"),
                        rs.getInt("nomor_komputer"),
                        rs.getInt("durasi_jam"),
                        rs.getDouble("total_bayar")));
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data transaksi: " + e.getMessage());
        }
        return daftar;
    }
 
    public int ambilIdTerakhir() {
        String sql = "SELECT MAX(id_transaksi) FROM transaksi";
        Connection conn = Koneksi.getConnection();
 
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil ID transaksi: " + e.getMessage());
        }
        return 0;
    }
 
    public boolean adaTransaksiUntuk(int nomorKomputer) {
        String sql = "SELECT COUNT(*) FROM transaksi WHERE nomor_komputer = ?";
        Connection conn = Koneksi.getConnection();
 
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, nomorKomputer);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengecek transaksi: " + e.getMessage());
        }
        return false;
    }
}