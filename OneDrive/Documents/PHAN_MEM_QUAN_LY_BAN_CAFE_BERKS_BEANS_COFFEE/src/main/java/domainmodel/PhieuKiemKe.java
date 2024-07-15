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
import javax.persistence.JoinTable;
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
@Table(name = "PHIEUKIEMKE")
public class PhieuKiemKe implements Serializable {

    @Id
    @GeneratedValue(generator = "GenIdSql")
    @GenericGenerator(name = "GenIdSql", strategy = "guid")
    @Column(name = "Id")
    private String id;

    @Column(name = "Ma")
    private String ma;

    @Column(name = "NgayKiemKe")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date ngayKiemKe;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdNhanVien")
    private NhanVien nhanVien;

    @OneToMany(mappedBy = "kiemKeKey", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ChiTietPhieuKiemKe> chiTietphieuKiem;

    public PhieuKiemKe() {
    }

    public PhieuKiemKe(String id, String ma, Date ngayKiemKe, Integer trangThai, NhanVien nhanVien, Set<ChiTietPhieuKiemKe> chiTietphieuKiem) {
        this.id = id;
        this.ma = ma;
        this.ngayKiemKe = ngayKiemKe;
        this.trangThai = trangThai;
        this.nhanVien = nhanVien;
        this.chiTietphieuKiem = chiTietphieuKiem;
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

    public Date getNgayKiemKe() {
        return ngayKiemKe;
    }

    public void setNgayKiemKe(Date ngayKiemKe) {
        this.ngayKiemKe = ngayKiemKe;
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

    public Set<ChiTietPhieuKiemKe> getChiTietphieuKiem() {
        return chiTietphieuKiem;
    }

    public void setChiTietphieuKiem(Set<ChiTietPhieuKiemKe> chiTietphieuKiem) {
        this.chiTietphieuKiem = chiTietphieuKiem;
    }

}
