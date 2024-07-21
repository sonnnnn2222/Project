package com.viethcn.shopbanhang.model;

public class PhieuMuon {
    private int mapm;
    private int matv;
    private String tendangnhap;
    private int masach;
    private String ngay;
    private int trasach;
    private int tienthue;
    private String tentv;

    private String HoTenDangNhap;
    private String tensach;

    public PhieuMuon(int mapm, int matv, String tendangnhap, int masach, String ngay, int trasach, int tienthue, String tentv, String hoTenDangNhap, String tensach) {
        this.mapm = mapm;
        this.matv = matv;
        this.tendangnhap = tendangnhap;
        this.masach = masach;
        this.ngay = ngay;
        this.trasach = trasach;
        this.tienthue = tienthue;
        this.tentv = tentv;
        HoTenDangNhap = hoTenDangNhap;
        this.tensach = tensach;
    }

    public int getMapm() {
        return mapm;
    }

    public void setMapm(int mapm) {
        this.mapm = mapm;
    }

    public int getMatv() {
        return matv;
    }

    public void setMatv(int matv) {
        this.matv = matv;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public int getMasach() {
        return masach;
    }

    public void setMasach(int masach) {
        this.masach = masach;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getTrasach() {
        return trasach;
    }

    public void setTrasach(int trasach) {
        this.trasach = trasach;
    }

    public int getTienthue() {
        return tienthue;
    }

    public void setTienthue(int tienthue) {
        this.tienthue = tienthue;
    }

    public String getTentv() {
        return tentv;
    }

    public void setTentv(String tentv) {
        this.tentv = tentv;
    }

    public String getHoTenDangNhap() {
        return HoTenDangNhap;
    }

    public void setHoTenDangNhap(String hoTenDangNhap) {
        HoTenDangNhap = hoTenDangNhap;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }
}
