/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;

import java.math.BigDecimal;

/**
 *
 * @author ASUS
 */
public class ChiTietPhieuNhapViewModel {

    private String idPhieuNhap, idNguyenLieu;
    private String maNguyenLieu, tenNguyenLieu;
    private BigDecimal soLuongNhap;
    private String donViTinh;
    private BigDecimal donGia;

    public BigDecimal thanhTien() {
        return soLuongNhap.multiply(donGia);
    }

    public ChiTietPhieuNhapViewModel() {
    }

    public ChiTietPhieuNhapViewModel(String idPhieuNhap, String idNguyenLieu, String maNguyenLieu, String tenNguyenLieu, BigDecimal soLuongNhap, String donViTinh, BigDecimal donGia) {
        this.idPhieuNhap = idPhieuNhap;
        this.idNguyenLieu = idNguyenLieu;
        this.maNguyenLieu = maNguyenLieu;
        this.tenNguyenLieu = tenNguyenLieu;
        this.soLuongNhap = soLuongNhap;
        this.donViTinh = donViTinh;
        this.donGia = donGia;
    }

    public String getIdPhieuNhap() {
        return idPhieuNhap;
    }

    public void setIdPhieuNhap(String idPhieuNhap) {
        this.idPhieuNhap = idPhieuNhap;
    }

    public String getIdNguyenLieu() {
        return idNguyenLieu;
    }

    public void setIdNguyenLieu(String idNguyenLieu) {
        this.idNguyenLieu = idNguyenLieu;
    }

    public String getMaNguyenLieu() {
        return maNguyenLieu;
    }

    public void setMaNguyenLieu(String maNguyenLieu) {
        this.maNguyenLieu = maNguyenLieu;
    }

    public String getTenNguyenLieu() {
        return tenNguyenLieu;
    }

    public void setTenNguyenLieu(String tenNguyenLieu) {
        this.tenNguyenLieu = tenNguyenLieu;
    }

    public BigDecimal getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(BigDecimal soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

   

    public Object[] getDataChiTietPhieuNhapView() {
        return new Object[]{idPhieuNhap,idNguyenLieu,maNguyenLieu, tenNguyenLieu,
            soLuongNhap, donViTinh,donGia,thanhTien()};
    }
    public Object[] getDataNguyenLieuView() {
        return new Object[]{idNguyenLieu,maNguyenLieu, tenNguyenLieu,soLuongNhap,
            donViTinh,donGia};
    }
}
