

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;

/**
 *
 * @author PC
 */
public class ChiTietPhieuKiemKeViewModel_Long {
    private String idPhieuKiem;
    private String idNguyenLieu;
    private float soLuong;
    private float soLuongThucTe;
    private float soLuongChenhlech;
    private String liDo;
    private float soLuongTon;

    public ChiTietPhieuKiemKeViewModel_Long() {
    }

    public ChiTietPhieuKiemKeViewModel_Long(String idPhieuKiem, String idNguyenLieu, float soLuong, float soLuongThucTe, float soLuongChenhlech, String liDo, float soLuongTon) {
        this.idPhieuKiem = idPhieuKiem;
        this.idNguyenLieu = idNguyenLieu;
        this.soLuong = soLuong;
        this.soLuongThucTe = soLuongThucTe;
        this.soLuongChenhlech = soLuongChenhlech;
        this.liDo = liDo;
        this.soLuongTon = soLuongTon;
    }

    public String getIdPhieuKiem() {
        return idPhieuKiem;
    }

    public void setIdPhieuKiem(String idPhieuKiem) {
        this.idPhieuKiem = idPhieuKiem;
    }

    public String getIdNguyenLieu() {
        return idNguyenLieu;
    }

    public void setIdNguyenLieu(String idNguyenLieu) {
        this.idNguyenLieu = idNguyenLieu;
    }

    public float getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(float soLuong) {
        this.soLuong = soLuong;
    }

    public float getSoLuongThucTe() {
        return soLuongThucTe;
    }

    public void setSoLuongThucTe(float soLuongThucTe) {
        this.soLuongThucTe = soLuongThucTe;
    }

    public float getSoLuongChenhlech() {
        return soLuongChenhlech;
    }

    public void setSoLuongChenhlech(float soLuongChenhlech) {
        this.soLuongChenhlech = soLuongChenhlech;
    }

    public String getLiDo() {
        return liDo;
    }

    public void setLiDo(String liDo) {
        this.liDo = liDo;
    }

    public float getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(float soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

  
    @Override
    public String toString() {
        return "ChiTietPhieuKiemKeViewModel_Long{" + "idPhieuKiem=" + idPhieuKiem + ", idNguyenLieu=" + idNguyenLieu + ", soLuong=" + soLuong + ", soLuongThucTe=" + soLuongThucTe + ", soLuongChenhlech=" + soLuongChenhlech + ", liDo=" + liDo + '}';
    }
    
    
    
}
