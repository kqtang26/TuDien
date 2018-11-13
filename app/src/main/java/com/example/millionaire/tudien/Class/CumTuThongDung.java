package com.example.millionaire.tudien.Class;

/**
 * Created by BoBo on 5/3/2017.
 */

public class CumTuThongDung {
    private String tiengAnh, tiengViet;

    public String getTiengAnh() {
        return tiengAnh;
    }

    public void setTiengAnh(String tiengAnh) {
        this.tiengAnh = tiengAnh;
    }

    public String getTiengViet() {
        return tiengViet;
    }

    public void setTiengViet(String tiengViet) {
        this.tiengViet = tiengViet;
    }

    public CumTuThongDung(String tiengAnh, String tiengViet) {

        this.tiengAnh = tiengAnh;
        this.tiengViet = tiengViet;
    }
}
