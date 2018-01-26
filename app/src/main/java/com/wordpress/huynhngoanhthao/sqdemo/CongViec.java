package com.wordpress.huynhngoanhthao.sqdemo;

/**
 * Created by AnhThao on 21/01/2018.
 */

public class CongViec {
    private int mIdCv;
    private String TenCv;

    public CongViec(int idCv, String tenCv) {
        mIdCv = idCv;
        TenCv = tenCv;
    }

    public int getIdCv() {
        return mIdCv;
    }

    public void setIdCv(int idCv) {
        mIdCv = idCv;
    }

    public String getTenCv() {
        return TenCv;
    }

    public void setTenCv(String tenCv) {
        TenCv = tenCv;
    }
}
