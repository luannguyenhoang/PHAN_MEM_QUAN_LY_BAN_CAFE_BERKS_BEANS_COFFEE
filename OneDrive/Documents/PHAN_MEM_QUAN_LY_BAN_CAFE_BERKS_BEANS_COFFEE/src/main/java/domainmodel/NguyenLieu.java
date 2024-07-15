/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domainmodel;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author trant
 */
@Entity
@Table(name = "NGUYENLIEU")
public class NguyenLieu implements Serializable {

    @Id
    @GeneratedValue(generator = "GenIdSql")
    @GenericGenerator(name = "GenIdSql", strategy = "guid")
    @Column(name = "Id")
    private String id;

    @Column(name = "Ma")
    private String ma;

    @Column(name = "Ten")
    private String ten;

    @Column(name = "HanSuDung")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date hanSuDung;

    @Column(name = "DonViTinh")
    private String donViTinh;

    @Column(name = "SoLuongTon")
    private Float soLuongTon;

    @OneToMany(mappedBy = "nguyenLieukey", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ChiTietSP> chiTietSp;

    @OneToMany(mappedBy = "nguyenLieuKey", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ChiTietPhieuNhap> chiTietPhieuNhap;

    @OneToMany(mappedBy = "nguyenLieuKey", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ChiTietPhieuTra> chiTietPhieuTra;

    public NguyenLieu() {
    }

    public NguyenLieu(String id, String ma, String ten, Date hanSuDung,
            String donViTinh, Float soLuongTon, Set<ChiTietSP> chiTietSp,
            Set<ChiTietPhieuNhap> chiTietPhieuNhap, Set<ChiTietPhieuTra> chiTietPhieuTra) {
        this.id = id;
        this.ma = ma;
        this.ten = ten;
        this.hanSuDung = hanSuDung;
        this.donViTinh = donViTinh;
        this.soLuongTon = soLuongTon;
        this.chiTietSp = chiTietSp;
        this.chiTietPhieuNhap = chiTietPhieuNhap;
        this.chiTietPhieuTra = chiTietPhieuTra;
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

    public Date getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(Date hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public Float getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(Float soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public Set<ChiTietSP> getChiTietSp() {
        return chiTietSp;
    }

    public void setChiTietSp(Set<ChiTietSP> chiTietSp) {
        this.chiTietSp = chiTietSp;
    }

    @Override
    public String toString() {
        return "NguyenLieu{" + "id=" + id + ", ma=" + ma + ", ten=" + ten + ", hanSuDung=" + hanSuDung + ", donViTinh=" + donViTinh + ", soLuongTon=" + soLuongTon + '}';
    }

    public Object[] toDataRow() {
        return new Object[]{id, ma, ten, soLuongTon, hanSuDung, donViTinh};
    }

    /**
     * @return the chiTietPhieuNhap
     */
    public Set<ChiTietPhieuNhap> getChiTietPhieuNhap() {
        return chiTietPhieuNhap;
    }

    /**
     * @param chiTietPhieuNhap the chiTietPhieuNhap to set
     */
    public void setChiTietPhieuNhap(Set<ChiTietPhieuNhap> chiTietPhieuNhap) {
        this.chiTietPhieuNhap = chiTietPhieuNhap;
    }

    /**
     * @return the chiTietPhieuTra
     */
    public Set<ChiTietPhieuTra> getChiTietPhieuTra() {
        return chiTietPhieuTra;
    }

    /**
     * @param chiTietPhieuTra the chiTietPhieuTra to set
     */
    public void setChiTietPhieuTra(Set<ChiTietPhieuTra> chiTietPhieuTra) {
        this.chiTietPhieuTra = chiTietPhieuTra;
    }

}
