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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author trant
 */
@Entity
@Table(name = "NHANVIEN")
public class NhanVien implements Serializable {

    @Id
    @GeneratedValue(generator = "GenIdSql")
    @GenericGenerator(name = "GenIdSql", strategy = "guid")
    @Column(name = "Id")
    private String id;

    @Column(name = "Ma")
    private String ma;

    @Column(name = "HoTen")
    private String hoTen;

    @Column(name = "QuocGia")
    private String quocGia;

    @Column(name = "ThanhPho")
    private String thanhPho;

    @Column(name = "Sdt")
    private String sdt;

    @Column(name = "GioiTinh")
    private String gioiTinh;

    @Column(name = "Luong")
    private Float luong;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @Column(name = "Avatar")
    private byte[] avatar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdChiNhanh")
    private ChiNhanh chiNhanh;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdChucVu")
    private ChucVu chucVu;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CHITIETCA",
            joinColumns = {
                @JoinColumn(name = "IdNv")},
            inverseJoinColumns = {
                @JoinColumn(name = "IdCa")}
    )
    private Set<Ca> setCa;

    public NhanVien() {
    }

    public NhanVien(String id, String ma, String hoTen, String quocGia, String thanhPho,
            String sdt, String gioiTinh, Float luong, Integer trangThai, byte[] avatar, ChiNhanh chiNhanh, ChucVu chucVu) {
        this.id = id;
        this.ma = ma;
        this.hoTen = hoTen;
        this.quocGia = quocGia;
        this.thanhPho = thanhPho;
        this.sdt = sdt;
        this.gioiTinh = gioiTinh;
        this.luong = luong;
        this.trangThai = trangThai;
        this.avatar = avatar;
        this.chiNhanh = chiNhanh;
        this.chucVu = chucVu;
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

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getQuocGia() {
        return quocGia;
    }

    public void setQuocGia(String quocGia) {
        this.quocGia = quocGia;
    }

    public String getThanhPho() {
        return thanhPho;
    }

    public void setThanhPho(String thanhPho) {
        this.thanhPho = thanhPho;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Float getLuong() {
        return luong;
    }

    public void setLuong(Float luong) {
        this.luong = luong;
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

    public ChiNhanh getChiNhanh() {
        return chiNhanh;
    }

    public void setChiNhanh(ChiNhanh chiNhanh) {
        this.chiNhanh = chiNhanh;
    }

    public ChucVu getChucVu() {
        return chucVu;
    }

    public void setChucVu(ChucVu chucVu) {
        this.chucVu = chucVu;
    }

    public Set<Ca> getSetCa() {
        return setCa;
    }

    public void setSetCa(Set<Ca> setCa) {
        this.setCa = setCa;
    }

}
