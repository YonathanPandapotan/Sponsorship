package com.example.yonariva.sponsorship;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class DataSponsoran implements Serializable {

    private String id, judulSponsor, deskripsi, namaPembuat, instansi, idUser;
    private int jumlahGambar;

    public String mode = "sponsoran";

    public DataSponsoran(){

    }

    public DataSponsoran(String id, String judulSponsor, String deskripsi, String namaPembuat, String instansi, String idUser){
        this.id = id;
        this.judulSponsor = judulSponsor;
        this.deskripsi = deskripsi;
        this.namaPembuat = namaPembuat;
        this.instansi = instansi;
        this.idUser = idUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudulSponsor() {
        return judulSponsor;
    }

    public void setJudulSponsor(String judulSponsor) {
        this.judulSponsor = judulSponsor;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getNamaPembuat() {
        return namaPembuat;
    }

    public void setNamaPembuat(String namaPembuat) {
        this.namaPembuat = namaPembuat;
    }

    public String getInstansi() {
        return instansi;
    }

    public void setInstansi(String instansi) {
        this.instansi = instansi;
    }

    public int getJumlahGambar() {
        return jumlahGambar;
    }

    public void setJumlahGambar(int jumlahGambar) {
        this.jumlahGambar = jumlahGambar;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("id", this.id);
        result.put("deskripsi", this.deskripsi);
        result.put("judulSponsor", this.judulSponsor);
        result.put("namaPembuat", this.namaPembuat);
        result.put("instansi", this.instansi);
        result.put("idUser", this.idUser);
        result.put("jumlahGambar", this.jumlahGambar);

        return result;
    }
}
