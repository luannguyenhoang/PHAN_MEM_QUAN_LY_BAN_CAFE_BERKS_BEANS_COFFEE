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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author trant
 */
@Entity
@Table(name = "PHIEUTRAHANG")
public class PhieuTraHang implements Serializable {

    @Id
    @GeneratedValue(generator = "GenIdSql")
    @GenericGenerator(name = "GenIdSql", strategy = "guid")
    @Column(name = "Id")
    private String id;

    @Column(name = "Ma")
    private String ma;

    @Column(name = "NgayTra")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date ngayTra;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdNhanVien")
    private NhanVien nhanVien;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdNhaCungCap")
    private NhaCungCap nhaCungCap;

    @OneToMany(mappedBy = "phieuTraKey", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ChiTietPhieuTra> chiTietPhieuTra;

    public PhieuTraHang() {
    }

    public PhieuTraHang(String id, String ma, Date ngayTra, Integer trangThai, NhanVien nhanVien,
            NhaCungCap nhaCungCap, Set<ChiTietPhieuTra> chiTietPhieuTra) {
        this.id = id;
        this.ma = ma;
        this.ngayTra = ngayTra;
        this.trangThai = trangThai;
        this.nhanVien = nhanVien;
        this.nhaCungCap = nhaCungCap;
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

    public Date getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public Set<ChiTietPhieuTra> getChiTietPhieuTra() {
        return chiTietPhieuTra;
    }

    public void setChiTietPhieuTra(Set<ChiTietPhieuTra> chiTietPhieuTra) {
        this.chiTietPhieuTra = chiTietPhieuTra;
    }

}
