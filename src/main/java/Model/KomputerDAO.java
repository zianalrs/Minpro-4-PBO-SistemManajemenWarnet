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
public class KomputerDAO {
 
    public int hitung() {
        String sql = "SELECT COUNT(*) FROM komputer";
        Connection conn = Koneksi.getConnection();
 
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Gagal menghitung data komputer: " + e.getMessage());
        }
        return 0;
    }
 
    public ArrayList<Komputer> ambilSemua() {
        ArrayList<Komputer> daftar = new ArrayList<>();
        String sql = "SELECT nomor, spesifikasi, kategori, dipakai FROM komputer ORDER BY nomor";
        Connection conn = Koneksi.getConnection();
 
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                daftar.add(buatKomputer(rs));
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data komputer: " + e.getMessage());
        }
        return daftar;
    }
 
    public Komputer ambilByNomor(int nomor) {
        String sql = "SELECT nomor, spesifikasi, kategori, dipakai FROM komputer WHERE nomor = ?";
        Connection conn = Koneksi.getConnection();
 
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, nomor);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return buatKomputer(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal mencari komputer: " + e.getMessage());
        }
        return null;
    }
 
    public boolean tambah(Komputer komputer) {
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
    }
 
    public boolean ubahSpesifikasi(int nomor, String spesifikasiBaru) {
        String sql = "UPDATE komputer SET spesifikasi = ? WHERE nomor = ?";
        Connection conn = Koneksi.getConnection();
 
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, spesifikasiBaru);
            pstmt.setInt(2, nomor);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal mengubah komputer: " + e.getMessage());
        }
        return false;
    }
 
    public boolean ubahStatus(int nomor, boolean dipakai) {
        String sql = "UPDATE komputer SET dipakai = ? WHERE nomor = ?";
        Connection conn = Koneksi.getConnection();
 
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, dipakai);
            pstmt.setInt(2, nomor);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal mengubah status komputer: " + e.getMessage());
        }
        return false;
    }
 
    public boolean hapus(int nomor) {
        String sql = "DELETE FROM komputer WHERE nomor = ?";
        Connection conn = Koneksi.getConnection();
 
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, nomor);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal menghapus komputer: " + e.getMessage());
        }
        return false;
    }
 
    private Komputer buatKomputer(ResultSet rs) throws SQLException {
        int nomor = rs.getInt("nomor");
        String spesifikasi = rs.getString("spesifikasi");
        String kategori = rs.getString("kategori");
        boolean dipakai = rs.getBoolean("dipakai");
 
        Komputer komputer;
        if (kategori.equalsIgnoreCase("VIP")) {
            komputer = new KomputerVIP(nomor, spesifikasi);
        } else {
            komputer = new KomputerReguler(nomor, spesifikasi);
        }
        komputer.setDipakai(dipakai);
        return komputer;
    }
}
