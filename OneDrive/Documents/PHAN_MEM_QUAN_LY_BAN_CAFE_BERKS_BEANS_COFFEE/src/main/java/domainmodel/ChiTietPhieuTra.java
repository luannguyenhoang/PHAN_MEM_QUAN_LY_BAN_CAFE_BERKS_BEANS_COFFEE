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
@Table(name = "CHITIETPHIEUTRA")
@IdClass(IdChiTietPhieuTra.class)
public class ChiTietPhieuTra implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdPhieuTra")
    private PhieuTraHang phieuTraKey;
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdNguyenLieu")
    private NguyenLieu nguyenLieuKey;

    @Column(name = "SoLuongTra")
    private Float soLuongTra;

    @Column(name = "LiDo")
    private String liDo;

    public ChiTietPhieuTra() {
    }

    public ChiTietPhieuTra(PhieuTraHang phieuTraKey, NguyenLieu nguyenLieuKey, float soLuongTra, String liDo) {
        this.phieuTraKey = phieuTraKey;
        this.nguyenLieuKey = nguyenLieuKey;
        this.soLuongTra = soLuongTra;
        this.liDo = liDo;
    }

    public PhieuTraHang getPhieuTraKey() {
        return phieuTraKey;
    }

    public void setPhieuTraKey(PhieuTraHang phieuTraKey) {
        this.phieuTraKey = phieuTraKey;
    }

    public NguyenLieu getNguyenLieuKey() {
        return nguyenLieuKey;
    }

    public void setNguyenLieuKey(NguyenLieu nguyenLieuKey) {
        this.nguyenLieuKey = nguyenLieuKey;
    }

    public Float getSoLuongTra() {
        return soLuongTra;
    }

    public void setSoLuongTra(Float soLuongTra) {
        this.soLuongTra = soLuongTra;
    }

    public String getLiDo() {
        return liDo;
    }

    public void setLiDo(String liDo) {
        this.liDo = liDo;
    }

}
