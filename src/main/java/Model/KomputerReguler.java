/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author LOQ
 */
public class KomputerReguler extends Komputer {
    private final double HARGA_PER_JAM = 7000;
 
    public KomputerReguler(int nomor, String spesifikasi) {
        super(nomor, spesifikasi);
    }
 
    @Override
    public String getKategori() {
        return "Reguler";
    }
 
    @Override
    public double getHargaPerJam() {
        return HARGA_PER_JAM;
    }
}
