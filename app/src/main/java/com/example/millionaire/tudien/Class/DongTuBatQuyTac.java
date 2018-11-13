package com.example.millionaire.tudien.Class;

/**
 * Created by BoBo on 5/3/2017.
 */

public class DongTuBatQuyTac {
    private String v1,v2,v3,nghia;

    public DongTuBatQuyTac(String v1, String v2, String v3, String nghia) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.nghia = nghia;
    }

    public String getNghia() {
        return nghia;
    }

    public void setNghia(String nghia) {
        this.nghia = nghia;
    }

    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1;
    }

    public String getV2() {
        return v2;
    }

    public void setV2(String v2) {
        this.v2 = v2;
    }

    public String getV3() {
        return v3;
    }

    public void setV3(String v3) {
        this.v3 = v3;
    }
}
