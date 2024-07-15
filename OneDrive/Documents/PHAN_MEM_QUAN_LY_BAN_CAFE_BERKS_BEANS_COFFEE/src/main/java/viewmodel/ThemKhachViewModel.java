/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;

/**
 *
 * @author trant
 */
public class ThemKhachViewModel {

    private String id;
    private String ma;
    private String hoTen;
    private String quocGia;
    private String thanhPho;
    private String sdt;
    private String gioiTinh;
    private Integer trangThai;
    private Integer diemTichLuy;

    public ThemKhachViewModel() {
    }

    public ThemKhachViewModel(String id, String ma, String hoTen, String quocGia, String thanhPho, String sdt, String gioiTinh, Integer trangThai, Integer diemTichLuy) {
        this.id = id;
        this.ma = ma;
        this.hoTen = hoTen;
        this.quocGia = quocGia;
        this.thanhPho = thanhPho;
        this.sdt = sdt;
        this.gioiTinh = gioiTinh;
        this.trangThai = trangThai;
        this.diemTichLuy = diemTichLuy;
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

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
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

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public Integer getDiemTichLuy() {
        return diemTichLuy;
    }

    public void setDiemTichLuy(Integer diemTichLuy) {
        this.diemTichLuy = diemTichLuy;
    }

}
