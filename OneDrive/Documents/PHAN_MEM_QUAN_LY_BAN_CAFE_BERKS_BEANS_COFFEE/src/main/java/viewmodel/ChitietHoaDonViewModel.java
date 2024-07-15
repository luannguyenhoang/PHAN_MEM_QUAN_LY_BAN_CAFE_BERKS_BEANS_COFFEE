/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author ASUS
 */
public class ChitietHoaDonViewModel {

    private String id, maSanPham, tenSanPham;
    private int soLuongMua;
    private BigDecimal giaBan, thanhTien, thanhTienSauKM;

    public ChitietHoaDonViewModel() {
    }

    public ChitietHoaDonViewModel(String id, String maSanPham, String tenSanPham, int soLuongMua, BigDecimal giaBan, BigDecimal thanhTien, BigDecimal thanhTienSauKM) {
        this.id = id;
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.soLuongMua = soLuongMua;
        this.giaBan = giaBan;
        this.thanhTien = thanhTien;
        this.thanhTienSauKM = thanhTienSauKM;
    }

    public BigDecimal totol() {
        BigDecimal thanhtien;
        thanhtien = BigDecimal.valueOf(this.soLuongMua).multiply(this.giaBan);

        return thanhtien;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public BigDecimal getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(BigDecimal thanhTien) {
        this.thanhTien = thanhTien;
    }

    public BigDecimal getThanhTienSauKM() {
        return thanhTienSauKM;
    }

    public void setThanhTienSauKM(BigDecimal thanhTienSauKM) {
        this.thanhTienSauKM = thanhTienSauKM;
    }

    public Object[] getDataHoaDonChiTietView() {

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        String formattedGiaBan = currencyFormat.format(giaBan);
        String formattedThanhTienSauKM = currencyFormat.format(thanhTienSauKM);

        return new Object[]{maSanPham, tenSanPham, soLuongMua, formattedGiaBan, totol(), formattedThanhTienSauKM};
    }
}
