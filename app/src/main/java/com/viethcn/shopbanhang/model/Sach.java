package com.viethcn.shopbanhang.model;

public class Sach {
    private int masach;
    private String tenSach;
    private int giathue;
    private int maloai;

    public Sach(int masach, String tenSach, int giathue, int maloai) {
        this.masach = masach;
        this.tenSach = tenSach;
        this.giathue = giathue;
        this.maloai = maloai;
    }

    public int getMasach() {
        return masach;
    }

    public void setMasach(int masach) {
        this.masach = masach;
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

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }
}
