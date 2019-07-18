package com.example.yonariva.sponsorship;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


@IgnoreExtraProperties
public class DataPostingan implements Serializable {

    private String namaPost, tanggal, instansi, alamat, op, jabatan, jenisBantuan, unit, hp, email, id, idUser, deskripsi;
    private int jumlahGambar;

    public DataPostingan(){

    }

    public DataPostingan(String namaPost, String tanggal, String alamat, String instansi, String op, String jabatan,
                         String jenisBantuan, String unit, String hp, String email, String id, String idUser, String deskripsi) {
        this.namaPost = namaPost;
        this.tanggal = tanggal;
        this.alamat = alamat;
        this.instansi = instansi;
        this.op = op;
        this.jabatan = jabatan;
        this.jenisBantuan = jenisBantuan;
        this.unit = unit;
        this.hp = hp;
        this.email = email;
        this.id = id;
        this.idUser = idUser;
        this.deskripsi = deskripsi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getJumlahGambar() {
        return jumlahGambar;
    }

    public void setJumlahGambar(int jumlahGambar) {
        this.jumlahGambar = jumlahGambar;
    }

    public String getNamaPost() {
        return namaPost;
    }

    public void setNamaPost(String namaPost) {
        this.namaPost = namaPost;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getInstansi() {
        return instansi;
    }

    public void setInstansi(String instansi) {
        this.instansi = instansi;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getJenisBantuan() {
        return jenisBantuan;
    }

    public void setJenisBantuan(String jenisBantuan) {
        this.jenisBantuan = jenisBantuan;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("namaPost", this.namaPost);
        result.put("tanggal", this.tanggal);
        result.put("alamat", this.alamat);
        result.put("instansi", this.instansi);
        result.put("op", this.op);
        result.put("jabatan", this.jabatan);
        result.put("jenisBantuan", this.jenisBantuan);
        result.put("unit", this.unit);
        result.put("hp", this.hp);
        result.put("email", this.email);
        result.put("id", this.id);
        result.put("idUser", this.idUser);
        result.put("jumlahGambar", this.jumlahGambar);
        result.put("deskripsi", this.deskripsi);

        return result;
    }
}
