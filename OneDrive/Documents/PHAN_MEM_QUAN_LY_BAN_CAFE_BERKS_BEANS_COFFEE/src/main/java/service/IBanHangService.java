/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import domainmodel.NhanVien;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import viewmodel.Area;
import viewmodel.ChiNhanhViewModel_Hoang;
import viewmodel.KhuyenMaiDangHoatDong;
import viewmodel.ProductForSale;
import viewmodel.Table;
import viewmodel.ThemKhachViewModel;

/**
 *
 * @author trant
 */
public interface IBanHangService {

    List<ProductForSale> getAllProductForSaleByChiNhanh(String idChiNhanh);

    List<ProductForSale> searchProductForSaleByTenSpAndChiNhanh(String tenSp, String idChiNhanh);

    boolean checkSo(String soLuong);

    boolean checkFormatSdt(String sdt);

    List<Area> getAllKhuVucByChiNhanh(String idChiNhanh);

    List<KhuyenMaiDangHoatDong> getAllKhuyenMaiByChiNhanh(String idChiNhanh);

    KhuyenMaiDangHoatDong getKhuyenMaibySanPham(String id);
    
    KhuyenMaiDangHoatDong getKhuyenMaibySanPham2(String id);

    List<Table> getAllBanByKhuVuc(Area are);

    String insertKhachHang(ThemKhachViewModel khachHang);

    ThemKhachViewModel getKhachHangBySdt(String sdt);

    boolean checkMaKhach(String maKhach);

    Float getGiaTriDoiDiem();

    Float getGiaTriDiem();

    String inserHoaDon(String ma, LocalDateTime ngayTao, String idNhanVien,
            Integer soBan);

    void insertChiTietHoaDon(String idSanPham, String idHoaDon, int soLuongMua,
            BigDecimal thanhTien, BigDecimal thanhTienSauKM);

    String autoGenMaHoaDon();

    NhanVien getNhanVienbyTaiKhoan(String idTaiKhoan);

    void updateDiemKhachHang(String idKhach, Integer diemTichLuy);

    ChiNhanhViewModel_Hoang getChiNhanhbyTaiKhoan(String idTaiKhoan);

    List<ChiNhanhViewModel_Hoang> getAllChiNhanh();

    void updateTrangThaiBanBySoBan(Integer soBan);

    void updateNguyenLieuAfterSellSanPham(String idSanPham, int soLuongMua);

    boolean checkDinhLuongPhaChex3(String idSanPham);
}
