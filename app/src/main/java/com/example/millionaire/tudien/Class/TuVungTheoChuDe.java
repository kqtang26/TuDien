package com.example.millionaire.tudien.Class;

import android.graphics.Bitmap;
import android.media.Image;

/**
 * Created by BoBo on 4/1/2017.
 */

public class TuVungTheoChuDe {
    private String tuKhoa, phatAm, nghia;
    private Bitmap hinhAnh;

    public TuVungTheoChuDe(String tuKhoa, String phatAm, String nghia, Bitmap hinhAnh) {
        this.tuKhoa = tuKhoa;
        this.phatAm = phatAm;
        this.nghia = nghia;
        this.hinhAnh = hinhAnh;
    }

    public String getTuKhoa() {
        return tuKhoa;
    }

    public void setTuKhoa(String tuKhoa) {
        this.tuKhoa = tuKhoa;
    }

    public String getPhatAm() {
        return phatAm;
    }

    public void setPhatAm(String phatAm) {
        this.phatAm = phatAm;
    }

    public String getNghia() {
        return nghia;
    }

    public void setNghia(String nghia) {
        this.nghia = nghia;
    }

    public Bitmap getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(Bitmap hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
