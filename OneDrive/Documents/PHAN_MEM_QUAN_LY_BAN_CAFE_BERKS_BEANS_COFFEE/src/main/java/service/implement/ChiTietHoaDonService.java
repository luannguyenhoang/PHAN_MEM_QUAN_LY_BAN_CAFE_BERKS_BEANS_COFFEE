/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.implement;

import domainmodel.ChiTietHoaDon;
import domainmodel.HoaDonBanHang;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import repository.HoaDonChiTietRepo;
import repository.HoaDonRepo;
import service.IHoaDonChiTiet;
import viewmodel.ChitietHoaDonViewModel;

/**
 *
 * @author ASUS
 */
public class ChiTietHoaDonService implements IHoaDonChiTiet {

    HoaDonChiTietRepo hdctRepo;
    HoaDonRepo hdRepo;

    public ChiTietHoaDonService() {
        hdctRepo = new HoaDonChiTietRepo();
        hdRepo = new HoaDonRepo();
    }

    @Override
    public List<ChitietHoaDonViewModel> getHoaDonChiTietByMaHoaDon(String maHD) {
        var chiTietHoaDon = hdctRepo.getHoaDonChiTietByHoaDon(hdRepo.getHoaDonByMa(maHD));
        List<ChitietHoaDonViewModel> lstView = new ArrayList<>();
        for (ChiTietHoaDon x : chiTietHoaDon) {
            ChitietHoaDonViewModel chiTietView = new ChitietHoaDonViewModel();
            chiTietView.setMaSanPham(x.getSanPhamKey().getMa());
            if (x.getSanPhamKey().getTen() != null) {
                chiTietView.setTenSanPham(x.getSanPhamKey().getTen());
            }
            if (x.getSoLuongMua() != null) {
                chiTietView.setSoLuongMua( x.getSoLuongMua());
            }
            if (x.getSanPhamKey().getGiaBan() != null) {
                 chiTietView.setGiaBan(new BigDecimal(x.getSanPhamKey().getGiaBan()));
            }
            if (x.getThanhTien() != null) {
                chiTietView.setThanhTien((x.getThanhTien()));
            }
            chiTietView.setThanhTienSauKM(x.getThanhTienSauKm());
            lstView.add(chiTietView);
        }
        return lstView;
    }

}
