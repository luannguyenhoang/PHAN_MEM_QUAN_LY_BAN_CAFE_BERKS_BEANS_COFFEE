/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;

import java.util.Date;

/**
 *
 * @author PC
 */
public class NguyenLieuViewModel_Long {
       private String id, ma, ten;
    private Date hanSuDung;
    private String donVitinh;
    private float soLuongTon;

    public NguyenLieuViewModel_Long() {
    }

    public NguyenLieuViewModel_Long(String id, String ma, String ten, Date hanSuDung, String donVitinh, float soLuongTon) {
        this.id = id;
        this.ma = ma;
        this.ten = ten;
        this.hanSuDung = hanSuDung;
        this.donVitinh = donVitinh;
        this.soLuongTon = soLuongTon;
    }

   

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Date getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(Date hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    public String getDonVitinh() {
        return donVitinh;
    }

    public void setDonVitinh(String donVitinh) {
        this.donVitinh = donVitinh;
    }

    public float getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(float soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    @Override
    public String toString() {
        return ten;
    }
    
     public Object[] toDataRow() {
        return new Object[]{id,ma, ten, soLuongTon, hanSuDung, donVitinh};
    }
}
