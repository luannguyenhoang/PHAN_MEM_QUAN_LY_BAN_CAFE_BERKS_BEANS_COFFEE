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
public class PhieuKiemKeViewModel_Long {
    private String id;
    private String ma;
    private Date ngayKiemKe;
    private int trangThai;
   private String maNhanVien;
    private String tenNhanVien;

    public PhieuKiemKeViewModel_Long() {
    }

    public PhieuKiemKeViewModel_Long(String id, String ma, Date ngayKiemKe, int trangThai, String maNhanVien, String tenNhanVien) {
        this.id = id;
        this.ma = ma;
        this.ngayKiemKe = ngayKiemKe;
        this.trangThai = trangThai;
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
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

    public Date getNgayKiemKe() {
        return ngayKiemKe;
    }

    public void setNgayKiemKe(Date ngayKiemKe) {
        this.ngayKiemKe = ngayKiemKe;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
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

    @Override
    public String toString() {
        return ma ;
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

    public Object[] getDataPhieuKiemKeView() {
        return new Object[]{id,ma, maNhanVien, tenNhanVien, ngayKiemKe, trangThai==0?"ok":"no"};
    }



   
 

    
}
