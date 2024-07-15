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
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author trant
 */
@Entity
@Table(name = "KHACHHANG")
public class KhachHang implements Serializable {

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

    @Column(name = "TrangThai")
    private Integer trangThai;

    @Column(name = "diemTichLuy")
    private Integer diemTichLuy;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CHITIETKHACHHANG",
            joinColumns = {
                @JoinColumn(name = "IdKhachHang")},
            inverseJoinColumns = {
                @JoinColumn(name = "IdChiNhanh")})
    private Set<ChiNhanh> listChiNhanh;

    public KhachHang() {
    }

    public KhachHang(String id, String ma, String hoTen, String quocGia, String thanhPho, String sdt, String gioiTinh, Integer trangThai, Integer diemTichLuy, Set<ChiNhanh> listChiNhanh) {
        this.id = id;
        this.ma = ma;
        this.hoTen = hoTen;
        this.quocGia = quocGia;
        this.thanhPho = thanhPho;
        this.sdt = sdt;
        this.gioiTinh = gioiTinh;
        this.trangThai = trangThai;
        this.diemTichLuy = diemTichLuy;
        this.listChiNhanh = listChiNhanh;
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

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public Integer getDiemTichLuy() {
        return diemTichLuy;
    }

    public void setDiemTichLuy(Integer diemTichLuy) {
        this.diemTichLuy = diemTichLuy;
    }

    public Set<ChiNhanh> getListChiNhanh() {
        return listChiNhanh;
    }

    public void setListChiNhanh(Set<ChiNhanh> listChiNhanh) {
        this.listChiNhanh = listChiNhanh;
    }

}
