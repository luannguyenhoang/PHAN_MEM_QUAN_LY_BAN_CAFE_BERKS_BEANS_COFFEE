/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domainmodel;

import java.io.Serializable;
import java.util.Set;
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
@Table(name = "BAN")
public class Ban implements Serializable {

    @Id
    @GeneratedValue(generator = "GenIdSql")
    @GenericGenerator(name = "GenIdSql", strategy = "guid")
    @Column(name = "Id")
    private String id;
    @Column(name = "SoBan")
    private Integer soBan;
    @Column(name = "TrangThai")
    private Integer trangThai;
    @Column(name = "TrangThaiSuDung")
    private Integer trangThaiSuDung;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdKhuVuc")
    private KhuVuc khuVuc;

    @OneToMany(mappedBy = "ban", fetch = FetchType.LAZY)
    private Set<HoaDonBanHang> setHoaDon;

    public Ban() {
    }

    public Ban(String id, Integer soBan, Integer trangThai, Integer trangThaiSuDung, KhuVuc khuVuc, Set<HoaDonBanHang> setHoaDon) {
        this.id = id;
        this.soBan = soBan;
        this.trangThai = trangThai;
        this.trangThaiSuDung = trangThaiSuDung;
        this.khuVuc = khuVuc;
        this.setHoaDon = setHoaDon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSoBan() {
        return soBan;
    }

    public void setSoBan(Integer soBan) {
        this.soBan = soBan;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public Integer getTrangThaiSuDung() {
        return trangThaiSuDung;
    }

    public void setTrangThaiSuDung(Integer trangThaiSuDung) {
        this.trangThaiSuDung = trangThaiSuDung;
    }

    public KhuVuc getKhuVuc() {
        return khuVuc;
    }

    public void setKhuVuc(KhuVuc khuVuc) {
        this.khuVuc = khuVuc;
    }

    public Set<HoaDonBanHang> getSetHoaDon() {
        return setHoaDon;
    }

    public void setSetHoaDon(Set<HoaDonBanHang> setHoaDon) {
        this.setHoaDon = setHoaDon;
    }

}
