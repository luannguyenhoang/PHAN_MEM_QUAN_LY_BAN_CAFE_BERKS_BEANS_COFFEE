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
public class ChiNhanhViewModel_Long {
    private String id;
     private String ma;
    private String quocGia;
    private String thanhPho;
    private Date ngayKhaiTruong;
    private float giaTriDoiDiem;
    private float giaTriDiem;
    private String idThuongHieu;

    public ChiNhanhViewModel_Long() {
    }

    public ChiNhanhViewModel_Long(String id, String ma, String quocGia, String thanhPho, Date ngayKhaiTruong, float giaTriDoiDiem, float giaTriDiem, String idThuongHieu) {
        this.id = id;
        this.ma = ma;
        this.quocGia = quocGia;
        this.thanhPho = thanhPho;
        this.ngayKhaiTruong = ngayKhaiTruong;
        this.giaTriDoiDiem = giaTriDoiDiem;
        this.giaTriDiem = giaTriDiem;
        this.idThuongHieu = idThuongHieu;
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

    public String getQuocGia() {
        return quocGia;
    }

    public void setQuocGia(String quocGia) {
        this.quocGia = quocGia;
    }

    public String getThanhPho() {
        return thanhPho;
    }

    public void setThanhPho(String thanhPho) {
        this.thanhPho = thanhPho;
    }

    public Date getNgayKhaiTruong() {
        return ngayKhaiTruong;
    }

    public void setNgayKhaiTruong(Date ngayKhaiTruong) {
        this.ngayKhaiTruong = ngayKhaiTruong;
    }

    public float getGiaTriDoiDiem() {
        return giaTriDoiDiem;
    }

    public void setGiaTriDoiDiem(float giaTriDoiDiem) {
        this.giaTriDoiDiem = giaTriDoiDiem;
    }

    public float getGiaTriDiem() {
        return giaTriDiem;
    }

    public void setGiaTriDiem(float giaTriDiem) {
        this.giaTriDiem = giaTriDiem;
    }

    public String getIdThuongHieu() {
        return idThuongHieu;
    }

    public void setIdThuongHieu(String idThuongHieu) {
        this.idThuongHieu = idThuongHieu;
    }

    @Override
    public String toString() {
        return id;

   
    
   
    }
}
