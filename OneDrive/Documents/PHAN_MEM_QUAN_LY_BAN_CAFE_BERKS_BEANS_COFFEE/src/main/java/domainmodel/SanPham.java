/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domainmodel;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author trant
 */
@Entity
@Table(name = "SANPHAM")
public class SanPham implements Serializable {

    @Id
    @GeneratedValue(generator = "GenIdSql")
    @GenericGenerator(name = "GenIdSql", strategy = "guid")
    @Column(name = "Id")
    private String id;

    @Column(name = "Ma")
    private String ma;

    @Column(name = "Ten")
    private String ten;

    @Column(name = "GiaBan")
    private Float giaBan;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @Column(name = "Avatar")
    private byte[] avatar;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdKhuyenMai")
    private KhuyenMai khuyenMai;

    @OneToMany(mappedBy = "sanPhamKey", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<ChiTietSP> chiTietSp;

    @OneToMany(mappedBy = "sanPhamKey", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<ChiTietHoaDon> chiTietHoaDon;

    public SanPham() {
    }

    public SanPham(String id, String ma, String ten, Float giaBan, Integer trangThai, byte[] avatar, KhuyenMai khuyenMai, Set<ChiTietSP> chiTietSp, Set<ChiTietHoaDon> chiTietHoaDon) {
        this.id = id;
        this.ma = ma;
        this.ten = ten;
        this.giaBan = giaBan;
        this.trangThai = trangThai;
        this.avatar = avatar;
        this.khuyenMai = khuyenMai;
        this.chiTietSp = chiTietSp;
        this.chiTietHoaDon = chiTietHoaDon;
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

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Float getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(Float giaBan) {
        this.giaBan = giaBan;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public KhuyenMai getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(KhuyenMai khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    public Set<ChiTietSP> getChiTietSp() {
        return chiTietSp;
    }

    public void setChiTietSp(Set<ChiTietSP> chiTietSp) {
        this.chiTietSp = chiTietSp;
    }

    public Set<ChiTietHoaDon> getChiTietHoaDon() {
        return chiTietHoaDon;
    }

    public void setChiTietHoaDon(Set<ChiTietHoaDon> chiTietHoaDon) {
        this.chiTietHoaDon = chiTietHoaDon;
    }

}
