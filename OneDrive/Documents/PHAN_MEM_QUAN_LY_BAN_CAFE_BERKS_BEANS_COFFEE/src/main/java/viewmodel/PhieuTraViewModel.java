/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ASUS
 */
public class PhieuTraViewModel {

    private String idPhieuTra;
    private String maPhieuTra;
    private String maNhaCungCap;
    private String tenNhaCungCap;
    private String maNhanVien;
    private String tenNhanVien;
    private Date ngayTra;
    private int trangThai;

    public PhieuTraViewModel() {
    }

    public PhieuTraViewModel(String idPhieuTra, String maPhieuTra, String maNhaCungCap, String tenNhaCungCap, String maNhanVien, String tenNhanVien, Date ngayTra, int trangThai) {
        this.idPhieuTra = idPhieuTra;
        this.maPhieuTra = maPhieuTra;
        this.maNhaCungCap = maNhaCungCap;
        this.tenNhaCungCap = tenNhaCungCap;
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.ngayTra = ngayTra;
        this.trangThai = trangThai;
    }

    public String getIdPhieuTra() {
        return idPhieuTra;
    }

    public void setIdPhieuTra(String idPhieuTra) {
        this.idPhieuTra = idPhieuTra;
    }

    public String getMaPhieuTra() {
        return maPhieuTra;
    }

    public void setMaPhieuTra(String maPhieuTra) {
        this.maPhieuTra = maPhieuTra;
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

    public Date getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
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

    public Object[] getPhieuTrahangView() {
        return new Object[]{idPhieuTra, maPhieuTra, maNhaCungCap, tenNhaCungCap, maNhanVien, tenNhanVien, ngayTra, getStatus()};
    }
}
