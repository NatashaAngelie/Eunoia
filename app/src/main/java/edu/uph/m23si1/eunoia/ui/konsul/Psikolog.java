package edu.uph.m23si1.eunoia.ui.konsul;

public class Psikolog {
    private String nama;
    private int fotoResId;

    public Psikolog(String nama, int fotoResId) {
        this.nama = nama;
        this.fotoResId = fotoResId;
    }

    public String getNama() {
        return nama;
    }

    public int getFotoResId() {
        return fotoResId;
    }
}
