/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domainmodel;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "CHITIETHOADON")
@IdClass(IdChiTietHoaDon.class)
public class ChiTietHoaDon implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdSanPham")
    private SanPham sanPhamKey;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdHoaDon")
    private HoaDonBanHang hoaDonKey;

    @Column(name = "SoLuongMua")
    private Integer soLuongMua;

    @Column(name = "ThanhTien")
    private BigDecimal thanhTien;

    @Column(name = "ThanhTienSauKm")
    private BigDecimal thanhTienSauKm;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(SanPham sanPhamKey, HoaDonBanHang hoaDonKey, Integer soLuongMua, BigDecimal thanhTien, BigDecimal thanhTienSauKm) {
        this.sanPhamKey = sanPhamKey;
        this.hoaDonKey = hoaDonKey;
        this.soLuongMua = soLuongMua;
        this.thanhTien = thanhTien;
        this.thanhTienSauKm = thanhTienSauKm;
    }

    public SanPham getSanPhamKey() {
        return sanPhamKey;
    }

    public void setSanPhamKey(SanPham sanPhamKey) {
        this.sanPhamKey = sanPhamKey;
    }

    public HoaDonBanHang getHoaDonKey() {
        return hoaDonKey;
    }

    public void setHoaDonKey(HoaDonBanHang hoaDonKey) {
        this.hoaDonKey = hoaDonKey;
    }

    public Integer getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(Integer soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    public BigDecimal getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(BigDecimal thanhTien) {
        this.thanhTien = thanhTien;
    }

    public BigDecimal getThanhTienSauKm() {
        return thanhTienSauKm;
    }

    public void setThanhTienSauKm(BigDecimal thanhTienSauKm) {
        this.thanhTienSauKm = thanhTienSauKm;
    }

}
