package edu.uph.m23si1.eunoia.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Akun extends RealmObject {

    @PrimaryKey
    private String email;
    private String password;
    private String username;
    private String namaLengkap;
    private String jurusan;
    private int umur;
    private String jenisKelamin;
    public Akun() {
        // constructor kosong wajib untuk Realm
    }

    public Akun(String email, String password, String username, String namaLengkap, String jurusan, int umur, String jenisKelamin) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.namaLengkap = namaLengkap;
        this.jurusan = jurusan;
        this.umur = umur;
        this.jenisKelamin = jenisKelamin;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }
    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getJurusan() {
        return jurusan;
    }
    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public int getUmur() {
        return umur;
    }
    public void setUmur(int umur) {
        this.umur = umur;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }
    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }
}
