package com.example.millionaire.tudien.Class;

/**
 * Created by BoBo on 3/14/2017.
 */

public class TuVungAnhViet {
    private String tuKhoa, phatAm, nghia;
    private  int yeuThich;

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

    public TuVungAnhViet(String tuKhoa, String phatAm, String nghia, int yeuThich) {
        this.tuKhoa = tuKhoa;
        this.phatAm = phatAm;
        this.nghia = nghia;
        this.yeuThich = yeuThich;
    }

    public TuVungAnhViet(String tuKhoa, String phatAm, String nghia) {

        this.tuKhoa = tuKhoa;
        this.phatAm = phatAm;
        this.nghia = nghia;
    }

    public String toString() {
        // TODO Auto-generated method stub
        return tuKhoa;
    }

    public String getTuKhoa() {
        return tuKhoa;
    }

    public int getYeuThich() {
        return yeuThich;
    }

    public void setYeuThich(int yeuThich) {
        this.yeuThich = yeuThich;
    }
}
