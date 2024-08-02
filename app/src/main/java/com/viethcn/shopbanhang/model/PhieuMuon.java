package com.viethcn.shopbanhang.model;

public class PhieuMuon {
    private int mapm;
    private String tendangnhap;
    private int masach;
    private String ngay;
    private int trasach;
    private int tienthue;
    private String HoTenDangNhap;
    private String tensach;

    //pm.mapm,  pm.matv, tv.hoten , pm.tendangnhap,  nd.hoten ,  pm.masach,  sc.tensach, pm.ngay,  pm.trasach,  pm.tienthue

    public PhieuMuon(int mapm, String tendangnhap, String hoTenDangNhap,  int masach,  String tensach,  String ngay, int trasach, int tienthue) {
        this.mapm = mapm;
        this.tendangnhap = tendangnhap;
        this.masach = masach;
        this.ngay = ngay;
        this.trasach = trasach;
        this.tienthue = tienthue;
        HoTenDangNhap = hoTenDangNhap;
        this.tensach = tensach;
    }

    public PhieuMuon(String tendangnhap, int masach, String ngay, int trasach, int tienthue) {
        this.tendangnhap = tendangnhap;
        this.masach = masach;
        this.ngay = ngay;
        this.trasach = trasach;
        this.tienthue = tienthue;
    }

    public int getMapm() {
        return mapm;
    }

    public void setMapm(int mapm) {
        this.mapm = mapm;
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
