/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author ASUS
 */
public class HoaDonViewModel {

    private String id, maHoaDon;
    private LocalDateTime ngayTao;
    private String maNhanVien;
    private String tenNhanVien;
    private int trangThai;
    private int soBan;

    public HoaDonViewModel() {
    }

    public HoaDonViewModel(String id, String maHoaDon, LocalDateTime ngayTao, String maNhanVien, String tenNhanVien, int trangThai, int soBan) {
        this.id = id;
        this.maHoaDon = maHoaDon;
        this.ngayTao = ngayTao;
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.trangThai = trangThai;
        this.soBan = soBan;
    }

    public int getSoBan() {
        return soBan;
    }

    public void setSoBan(int soBan) {
        this.soBan = soBan;
    }

    public HoaDonViewModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public LocalDateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao = ngayTao;
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

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getStatus() {
        if (trangThai == 0) {
            return "Ðã hủy";
        } else if (trangThai == 1) {
            return "Đã thanh toán";
        }
        return null;
    }

    public Object[] getDataHoaDonView() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fomatString = ngayTao.format(dtf);
        return new Object[]{maHoaDon, fomatString, maNhanVien, tenNhanVien, soBan, getStatus()};
    }
}
