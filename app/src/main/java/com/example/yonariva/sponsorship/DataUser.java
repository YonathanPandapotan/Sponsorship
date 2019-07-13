package com.example.yonariva.sponsorship;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class DataUser {

    private String nama;
    private String id;
    private String namaInstansi;
    private String alamatInstansi;
    private String jabatanUser;
    private String email;
    private String noHP;
    private Boolean registComplete;

    public DataUser(){

    }

    public DataUser(String nama, String id, String namaInstansi, String alamatInstansi,
                    String jabatanUser, String email, String noHP, Boolean registComplete) {
        this.nama = nama;
        this.id = id;
        this.namaInstansi = namaInstansi;
        this.alamatInstansi = alamatInstansi;
        this.jabatanUser = jabatanUser;
        this.email = email;
        this.noHP = noHP;
        this.registComplete = registComplete;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nama", this.nama);
        result.put("id", this.id);
        result.put("namaInstansi", this.namaInstansi);
        result.put("alamatInstansi", this.alamatInstansi);
        result.put("jabatanUser", this.jabatanUser);
        result.put("email", this.email);
        result.put("noHP", this.noHP);
        result.put("registComplete", this.registComplete);

        return result;
    }

    public Boolean getRegistComplete() {
        return registComplete;
    }

    public void setRegistComplete(Boolean registComplete) {
        this.registComplete = registComplete;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaInstansi() {
        return namaInstansi;
    }

    public void setNamaInstansi(String namaInstansi) {
        this.namaInstansi = namaInstansi;
    }

    public String getAlamatInstansi() {
        return alamatInstansi;
    }

    public void setAlamatInstansi(String alamatInstansi) {
        this.alamatInstansi = alamatInstansi;
    }

    public String getJabatanUser() {
        return jabatanUser;
    }

    public void setJabatanUser(String jabatanUser) {
        this.jabatanUser = jabatanUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoHP() {
        return noHP;
    }

    public void setNoHP(String noHP) {
        this.noHP = noHP;
    }
}
