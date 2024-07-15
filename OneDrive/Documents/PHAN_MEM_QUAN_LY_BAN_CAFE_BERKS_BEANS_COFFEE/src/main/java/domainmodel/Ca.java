/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domainmodel;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author trant
 */
@Entity
@Table(name = "CA")
public class Ca implements Serializable {

    @Id
    @GeneratedValue(generator = "GenIdSql")
    @GenericGenerator(name = "GenIdSql", strategy = "guid")
    @Column(name = "Id")
    private String id;

    @Column(name = "Ma")
    private String ma;

    @Column(name = "GioBatDau")
    private LocalTime gioBatDau;

    @Column(name = "GioKetThuc")
    private LocalTime gioKetThuc;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CHITIETCA",
            joinColumns = {
                @JoinColumn(name = "IdCa")},
            inverseJoinColumns = {
                @JoinColumn(name = "IdNv")}
    )
    private Set<NhanVien> setNhanVien;

    public Ca() {
    }

    public Ca(String id, String ma, LocalTime gioBatDau, LocalTime gioKetThuc, Integer trangThai, Set<NhanVien> setNhanVien) {
        this.id = id;
        this.ma = ma;
        this.gioBatDau = gioBatDau;
        this.gioKetThuc = gioKetThuc;
        this.trangThai = trangThai;
        this.setNhanVien = setNhanVien;
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

    public LocalTime getGioBatDau() {
        return gioBatDau;
    }

    public void setGioBatDau(LocalTime gioBatDau) {
        this.gioBatDau = gioBatDau;
    }

    public LocalTime getGioKetThuc() {
        return gioKetThuc;
    }

    public void setGioKetThuc(LocalTime gioKetThuc) {
        this.gioKetThuc = gioKetThuc;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public Set<NhanVien> getSetNhanVien() {
        return setNhanVien;
    }

    public void setSetNhanVien(Set<NhanVien> setNhanVien) {
        this.setNhanVien = setNhanVien;
    }

}
