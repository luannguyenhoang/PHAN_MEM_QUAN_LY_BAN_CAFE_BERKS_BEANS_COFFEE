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
public class ChiNhanhVM_Long {

    private String id;

    private String ma;

    private String quocGia;

    private String thanhPho;

    private Date ngayKhaiTruong;

    private Integer trangThai;

    private Float giaTriDoiDiem;

    private Float giaTriDiem;
    private String idKhuVuc;

    public ChiNhanhVM_Long() {
    }

    public ChiNhanhVM_Long(String id, String ma, String quocGia, String thanhPho, Date ngayKhaiTruong, Integer trangThai, Float giaTriDoiDiem, Float giaTriDiem, String idKhuVuc) {
        this.id = id;
        this.ma = ma;
        this.quocGia = quocGia;
        this.thanhPho = thanhPho;
        this.ngayKhaiTruong = ngayKhaiTruong;
        this.trangThai = trangThai;
        this.giaTriDoiDiem = giaTriDoiDiem;
        this.giaTriDiem = giaTriDiem;
        this.idKhuVuc = idKhuVuc;
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

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public Float getGiaTriDoiDiem() {
        return giaTriDoiDiem;
    }

    public void setGiaTriDoiDiem(Float giaTriDoiDiem) {
        this.giaTriDoiDiem = giaTriDoiDiem;
    }

    public Float getGiaTriDiem() {
        return giaTriDiem;
    }

    public void setGiaTriDiem(Float giaTriDiem) {
        this.giaTriDiem = giaTriDiem;
    }

    public String getIdKhuVuc() {
        return idKhuVuc;
    }

    public void setIdKhuVuc(String idKhuVuc) {
        this.idKhuVuc = idKhuVuc;
    }

    @Override
    public String toString() {
        return ma;
    }

    public Object[] getDataNguyenLieuView() {
        return new Object[]{id, ma};
    }

}
