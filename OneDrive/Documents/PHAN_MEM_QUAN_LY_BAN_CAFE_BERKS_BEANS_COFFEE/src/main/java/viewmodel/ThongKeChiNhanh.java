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
public class ThongKeChiNhanh {

    private String maChiNhanh;
    private Integer soLuongDonHang;
    private BigDecimal tongChietKhau;
    private BigDecimal tongDoanhThu;

    public ThongKeChiNhanh() {
    }

    public ThongKeChiNhanh(String maChiNhanh, Integer soLuongDonHang, BigDecimal tongChietKhau, BigDecimal tongDoanhThu) {
        this.maChiNhanh = maChiNhanh;
        this.soLuongDonHang = soLuongDonHang;
        this.tongChietKhau = tongChietKhau;
        this.tongDoanhThu = tongDoanhThu;
    }

    public String getMaChiNhanh() {
        return maChiNhanh;
    }

    public void setMaChiNhanh(String maChiNhanh) {
        this.maChiNhanh = maChiNhanh;
    }

    public Integer getSoLuongDonHang() {
        return soLuongDonHang;
    }

    public void setSoLuongDonHang(Integer soLuongDonHang) {
        this.soLuongDonHang = soLuongDonHang;
    }

    public BigDecimal getTongChietKhau() {
        return tongChietKhau;
    }

    public void setTongChietKhau(BigDecimal tongChietKhau) {
        this.tongChietKhau = tongChietKhau;
    }

    public BigDecimal getTongDoanhThu() {
        return tongDoanhThu;
    }

    public void setTongDoanhThu(BigDecimal tongDoanhThu) {
        this.tongDoanhThu = tongDoanhThu;
    }

    public Object[] getThongKeChiNhanh() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        String formattedTongTienHang = currencyFormat.format(tongChietKhau);
        String formattedTongDoanhThu = currencyFormat.format(tongDoanhThu);
        return new Object[]{maChiNhanh, soLuongDonHang, formattedTongTienHang, formattedTongDoanhThu};
    }

}
