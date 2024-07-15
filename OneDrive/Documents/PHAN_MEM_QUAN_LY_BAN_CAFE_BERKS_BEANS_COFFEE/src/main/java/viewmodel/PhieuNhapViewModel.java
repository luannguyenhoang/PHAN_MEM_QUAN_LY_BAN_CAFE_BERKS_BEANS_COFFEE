/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;


import java.util.Date;

/**
 *
 * @author ASUS
 */
public class PhieuNhapViewModel {

    private String id;
    private String maPhieuNhap;
    private String maNhaCungCap;
    private String tenNhaCungCap;
    private String maNhanVien;
    private String tenNhanVien;
    private Date ngayNhap;
    private String donViTinh;
    private Integer trangThai;

    

    public PhieuNhapViewModel() {
    }

    public PhieuNhapViewModel(String id, String maPhieuNhap, String maNhaCungCap, String tenNhaCungCap, String maNhanVien, String tenNhanVien, Date ngayNhap, String donViTinh, Integer trangThai) {
        this.id = id;
        this.maPhieuNhap = maPhieuNhap;
        this.maNhaCungCap = maNhaCungCap;
        this.tenNhaCungCap = tenNhaCungCap;
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.ngayNhap = ngayNhap;
        this.donViTinh = donViTinh;
        this.trangThai = trangThai;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public String getTenNhaCungCap() {
        return tenNhaCungCap;
    }

    public void setTenNhaCungCap(String tenNhaCungCap) {
        this.tenNhaCungCap = tenNhaCungCap;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }
    public String getStatus() {
        if (trangThai == 0) {
            return "Đã hủy";
        } else if (trangThai == 1) {
            return "Phiếu tạm";
        } else if (trangThai == 3) {
            return "Đã hoàn thành";
        }
        return null;
    }

    public Object[] getDataPhieuNhapView() {
        return new Object[]{id,maPhieuNhap, maNhaCungCap,
            tenNhaCungCap, maNhanVien, tenNhanVien, ngayNhap, getStatus()};
    }
}
