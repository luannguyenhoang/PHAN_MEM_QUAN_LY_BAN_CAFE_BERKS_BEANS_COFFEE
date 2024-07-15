/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;

import java.math.BigDecimal;

/**
 *
 * @author trant
 */
public class ProductForSale {

    private String idSp;
    private String maSp;
    private String tenSp;
    private BigDecimal giaBan;
    private Integer trangThai;
    private String TenKhuyenMai;
    private byte[] avatar;

    public ProductForSale() {
    }

    public ProductForSale(String idSp, String maSp, String tenSp, BigDecimal giaBan, Integer trangThai, String TenKhuyenMai, byte[] avatar) {
        this.idSp = idSp;
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.giaBan = giaBan;
        this.trangThai = trangThai;
        this.TenKhuyenMai = TenKhuyenMai;
        this.avatar = avatar;
    }

    public String getIdSp() {
        return idSp;
    }

    public void setIdSp(String idSp) {
        this.idSp = idSp;
    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public String getTenKhuyenMai() {
        return TenKhuyenMai;
    }

    public void setTenKhuyenMai(String TenKhuyenMai) {
        this.TenKhuyenMai = TenKhuyenMai;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

}
