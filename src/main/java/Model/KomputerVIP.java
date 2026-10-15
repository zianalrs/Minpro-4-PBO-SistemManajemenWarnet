/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author LOQ
 */
public class KomputerVIP extends Komputer {
    private final double HARGA_PER_JAM = 14000;
    private final String FASILITAS_TAMBAHAN = "AC + Snack Gratis + Kursi Gaming";
 
    public KomputerVIP(int nomor, String spesifikasi) {
        super(nomor, spesifikasi);
    }
 
    public String getFasilitasTambahan() {
        return FASILITAS_TAMBAHAN;
    }
 
    @Override
    public String getKategori() {
        return "VIP";
    }
 
    @Override
    public double getHargaPerJam() {
        return HARGA_PER_JAM;
    }
 
    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println("Fasilitas     : " + FASILITAS_TAMBAHAN);
    }
}
