package com.viethcn.shopbanhang.model;

public class Sach {
    private int masach;
    private String tenSach;
    private int giathue;
    private int maloai;
    private String tenloai;
    private int soLuongDaMuon;

    public Sach(int masach, String tenSach, int giathue, int maloai, String tenloai) {
        this.masach = masach;
        this.tenSach = tenSach;
        this.giathue = giathue;
        this.maloai = maloai;
        this.tenloai = tenloai;
    }

    public Sach(int masach, String tenSach, int soLuongDaMuon) {
        this.masach = masach;
        this.tenSach = tenSach;
        this.soLuongDaMuon = soLuongDaMuon;
    }

    public int getSoLuongDaMuon() {
        return soLuongDaMuon;
    }

    public void setSoLuongDaMuon(int soLuongDaMuon) {
        this.soLuongDaMuon = soLuongDaMuon;
    }

    public int getMasach() {
        return masach;
    }
    public int getMaloai() {
        return maloai;
    }
    public String getTenloai() {
        return tenloai;
    }

    public String getTenSach() {
        return tenSach;
    }
    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getGiathue() {
        return giathue;
    }
    public void setGiathue(int giathue) {
        this.giathue = giathue;
    }

}
