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
@Table(name = "CHITIETSANPHAM")
@IdClass(IdChiTietSP.class)
public class ChiTietSP implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdSanPham")
    private SanPham sanPhamKey;
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdNguyenLieu")
    private NguyenLieu nguyenLieukey;

    @Column(name = "DinhLuong")
    private float dinhLuong;

    public ChiTietSP() {
    }

    public ChiTietSP(SanPham sanPhamKey, NguyenLieu nguyenLieukey, float dinhLuong) {
        this.sanPhamKey = sanPhamKey;
        this.nguyenLieukey = nguyenLieukey;
        this.dinhLuong = dinhLuong;
    }

    public SanPham getSanPhamKey() {
        return sanPhamKey;
    }

    public void setSanPhamKey(SanPham sanPhamKey) {
        this.sanPhamKey = sanPhamKey;
    }

    public NguyenLieu getNguyenLieukey() {
        return nguyenLieukey;
    }

    public void setNguyenLieukey(NguyenLieu nguyenLieukey) {
        this.nguyenLieukey = nguyenLieukey;
    }

    public float getDinhLuong() {
        return dinhLuong;
    }

    public void setDinhLuong(float dinhLuong) {
        this.dinhLuong = dinhLuong;
    }

}
