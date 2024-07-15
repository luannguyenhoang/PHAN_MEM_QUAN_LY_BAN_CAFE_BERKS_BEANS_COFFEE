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
public class ChiTietPhieuTraViewModel {

    private String idPhieuTra, idNguyenLieu;
    private String maNguyenLieu, tenNguyenLieu;
    private BigDecimal soLuongTra;
    private String donViTinh;
    private BigDecimal donGia;
    private String lyDo;

    public BigDecimal thanhTien() {
        return soLuongTra.multiply(donGia);
    }

    public ChiTietPhieuTraViewModel(String idPhieuTra, String idNguyenLieu, String maNguyenLieu, String tenNguyenLieu, BigDecimal soLuongTra, String donViTinh, BigDecimal donGia, String lyDo) {
        this.idPhieuTra = idPhieuTra;
        this.idNguyenLieu = idNguyenLieu;
        this.maNguyenLieu = maNguyenLieu;
        this.tenNguyenLieu = tenNguyenLieu;
        this.soLuongTra = soLuongTra;
        this.donViTinh = donViTinh;
        this.donGia = donGia;
        this.lyDo = lyDo;
    }

    public String getLyDo() {
        return lyDo;
    }

    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
    }

   

    

    public ChiTietPhieuTraViewModel() {
    }

    public String getIdPhieuTra() {
        return idPhieuTra;
    }

    public void setIdPhieuTra(String idPhieuTra) {
        this.idPhieuTra = idPhieuTra;
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

    public BigDecimal getSoLuongTra() {
        return soLuongTra;
    }

    public void setSoLuongTra(BigDecimal soLuongTra) {
        this.soLuongTra = soLuongTra;
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

  

    public Object[] getChiTietPhieuTrahangView() {
        return new Object[]{idPhieuTra, idNguyenLieu, maNguyenLieu, tenNguyenLieu, soLuongTra, donViTinh, donGia, thanhTien(),lyDo};
    }
    public Object[] getNguyenLieuByPhieuTrahangView() {
        return new Object[]{idNguyenLieu, maNguyenLieu, tenNguyenLieu, soLuongTra, donViTinh,lyDo};
    }
}
