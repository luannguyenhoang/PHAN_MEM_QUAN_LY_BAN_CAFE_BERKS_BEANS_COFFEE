/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domainmodel;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author trant
 */
@Entity
@Table(name = "CHITIETPHIEUKIEMKE")
@IdClass(IdChiTietPhieuKiemKe.class)
public class ChiTietPhieuKiemKe implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdPhieuKiem")
    private PhieuKiemKe kiemKeKey;
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdNguyenLieu")
    private NguyenLieu nguyenLieuKey;

    @Column(name = "SoLuong")
    private float soLuong;

    @Column(name = "SoLuongThucTe")
    private float soLuongThucTe;

    @Column(name = "SoLuongChenhlech")
    private float soLuongChenhlech;

    @Column(name = "LiDo")
    private String liDo;

    public ChiTietPhieuKiemKe() {
    }

    public ChiTietPhieuKiemKe(PhieuKiemKe kiemKeKey, NguyenLieu nguyenLieuKey, float soLuong, float soLuongThucTe, float soLuongChenhlech, String liDo) {
        this.kiemKeKey = kiemKeKey;
        this.nguyenLieuKey = nguyenLieuKey;
        this.soLuong = soLuong;
        this.soLuongThucTe = soLuongThucTe;
        this.soLuongChenhlech = soLuongChenhlech;
        this.liDo = liDo;
    }

    public PhieuKiemKe getKiemKeKey() {
        return kiemKeKey;
    }

    public void setKiemKeKey(PhieuKiemKe kiemKeKey) {
        this.kiemKeKey = kiemKeKey;
    }

    public NguyenLieu getNguyenLieuKey() {
        return nguyenLieuKey;
    }

    public void setNguyenLieuKey(NguyenLieu nguyenLieuKey) {
        this.nguyenLieuKey = nguyenLieuKey;
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

}
