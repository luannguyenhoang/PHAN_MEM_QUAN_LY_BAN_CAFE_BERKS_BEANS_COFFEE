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
 * @author trant
 */
public class ThongKeSanPhamBanChay {

    private String maSp;
    private String tenSP;
    private Integer soLuongbanRa;
    private BigDecimal tongTienHang;
    private BigDecimal tongDoanhThu;
    private Integer soLuongDonHang;

    public ThongKeSanPhamBanChay() {
    }

    public ThongKeSanPhamBanChay(String maSp, String tenSP, Integer soLuongbanRa, BigDecimal tongTienHang, BigDecimal tongDoanhThu, Integer soLuongDonHang) {
        this.maSp = maSp;
        this.tenSP = tenSP;
        this.soLuongbanRa = soLuongbanRa;
        this.tongTienHang = tongTienHang;
        this.tongDoanhThu = tongDoanhThu;
        this.soLuongDonHang = soLuongDonHang;
    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public BigDecimal getTongTienHang() {
        return tongTienHang;
    }

    public void setTongTienHang(BigDecimal tongTienHang) {
        this.tongTienHang = tongTienHang;
    }

    public BigDecimal getTongDoanhThu() {
        return tongDoanhThu;
    }

    public void setTongDoanhThu(BigDecimal tongDoanhThu) {
        this.tongDoanhThu = tongDoanhThu;
    }

    public Integer getSoLuongDonHang() {
        return soLuongDonHang;
    }

    public void setSoLuongDonHang(Integer soLuongDonHang) {
        this.soLuongDonHang = soLuongDonHang;
    }

    public Integer getSoLuongbanRa() {
        return soLuongbanRa;
    }

    public void setSoLuongbanRa(Integer soLuongbanRa) {
        this.soLuongbanRa = soLuongbanRa;
    }

    public Object[] getThongKeSanPhamBanChay() {
        // Định dạng tiền tệ theo tiếng Việt
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        String formattedTongTienHang = currencyFormat.format(tongTienHang);
        String formattedTongDoanhThu = currencyFormat.format(tongDoanhThu);

        return new Object[]{maSp, tenSP, getSoLuongbanRa(), formattedTongTienHang, formattedTongDoanhThu, soLuongDonHang};
    }

}
