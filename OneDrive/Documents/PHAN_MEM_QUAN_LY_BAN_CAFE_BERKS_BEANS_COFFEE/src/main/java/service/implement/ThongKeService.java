/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.implement;

import domainmodel.ChiNhanh;
import domainmodel.ChiTietHoaDon;
import domainmodel.HoaDonBanHang;
import domainmodel.SanPham;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import repository.ThongKeRepository;
import service.IThongKeService;
import viewmodel.ThongKeChiNhanh;
import viewmodel.ThongKeSanPhamBanChay;
import viewmodel.ThongKeTheoThoiGianViewModel;

/**
 *
 * @author trant
 */
public class ThongKeService implements IThongKeService {

    ThongKeRepository ThongKeRepository;

    public ThongKeService() {
        ThongKeRepository = new ThongKeRepository();
    }

    private static Calendar getCalendarWithoutTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static List<Date> getDatesBetween(Date startDate, Date endDate) {
        List<Date> datesInRange = new ArrayList<>();
        Calendar calendar = getCalendarWithoutTime(startDate);
        Calendar endCalendar = getCalendarWithoutTime(endDate);
        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        Date result = calendar.getTime();
        datesInRange.add(result);
        calendar.add(Calendar.DATE, 1);
        return datesInRange;
    }

    @Override
    public List<ThongKeTheoThoiGianViewModel> getAllThongKeByDate(Date dateStart, Date dateEnd) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Date> allDate = getDatesBetween(dateStart, dateEnd);
        List<ThongKeTheoThoiGianViewModel> listThongKe = new ArrayList<>();
        for (Date time : allDate) {
            String date = dateFormat.format(time);
            var listHoaDon = ThongKeRepository.getAllHoaDonBanHangByDate(date);
            ThongKeTheoThoiGianViewModel thongKeView = new ThongKeTheoThoiGianViewModel();
            double tongTienHang = 0;
            double tienChietKhau = 0;
            if (listHoaDon.size() > 0) {
                try {
                    Date ngay = dateFormat.parse(date);
                    thongKeView.setNgay(ngay);
                } catch (ParseException ex) {
                    Logger.getLogger(ThongKeService.class.getName()).log(Level.SEVERE, null, ex);
                }

                thongKeView.setSoLuongDonHang(listHoaDon.size());
                for (HoaDonBanHang hd : listHoaDon) {
                    Set<ChiTietHoaDon> setChiTiet = ThongKeRepository.getChiTietHoaDonByHoaDon(hd.getId());
                    for (ChiTietHoaDon chiTiet : setChiTiet) {
                        tongTienHang += Double.parseDouble(String.valueOf(chiTiet.getThanhTien()));
                        tienChietKhau += Double.parseDouble(String.valueOf(chiTiet.getThanhTien()))
                                - Double.parseDouble(String.valueOf(chiTiet.getThanhTienSauKm()));
                    }
                }
                thongKeView.setTongTienHang(new BigDecimal(tongTienHang));
                thongKeView.setTongTienChietKhau(new BigDecimal(tienChietKhau));
                listThongKe.add(thongKeView);
            }
        }

        return listThongKe;
    }

    @Override
    public List<ThongKeSanPhamBanChay> getAllSanPhamBanChay() {
        var allSanPham = ThongKeRepository.getAllSanPham();
        List<ThongKeSanPhamBanChay> listSpBanChay = new ArrayList<>();
        int soLuongBanRa = 0;
        double tongTienHang = 0;
        double tongDoanhThu = 0;
        for (SanPham sanPham : allSanPham) {
            Set<ChiTietHoaDon> chiTiet = ThongKeRepository.getChiTietHoaDonBySanPham(sanPham.getId());
            if (chiTiet != null) {
                ThongKeSanPhamBanChay spBanChay = new ThongKeSanPhamBanChay();
                spBanChay.setMaSp(sanPham.getMa());
                spBanChay.setTenSP(sanPham.getTen());
                for (ChiTietHoaDon chiTietHoaDon : chiTiet) {
                    soLuongBanRa += chiTietHoaDon.getSoLuongMua();
                    tongTienHang += Double.parseDouble(String.valueOf(chiTietHoaDon.getThanhTien()));
                    tongDoanhThu += Double.parseDouble(String.valueOf(chiTietHoaDon.getThanhTienSauKm()));
                }
                spBanChay.setSoLuongbanRa(soLuongBanRa);
                spBanChay.setTongTienHang(new BigDecimal(tongTienHang));
                spBanChay.setTongDoanhThu(new BigDecimal(tongDoanhThu));
                spBanChay.setSoLuongDonHang(chiTiet.size());
                listSpBanChay.add(spBanChay);
            }
        }
        return listSpBanChay;
    }

    @Override
    public List<ThongKeChiNhanh> getAllThongKeChiNhanh() {
        var allChiNhanh = ThongKeRepository.getAllChiNhannh();
        List<ThongKeChiNhanh> listThongKeChiNhanh = new ArrayList<>();
        if (allChiNhanh != null) {
            for (ChiNhanh chiNhanh : allChiNhanh) {
                ThongKeChiNhanh thongKeCn = new ThongKeChiNhanh();
                thongKeCn.setMaChiNhanh(chiNhanh.getMa());
                var allHoaDon = ThongKeRepository.getAllHoaDonByChiNhanh(chiNhanh.getId());
                if (!allHoaDon.isEmpty()) {
                    double tongChietKhau = 0;
                    double tongDoanhThu = 0;
                    thongKeCn.setSoLuongDonHang(allHoaDon.size());
                    for (HoaDonBanHang hoaDonBanHang : allHoaDon) {
                        var chiTietHd = ThongKeRepository.getChiTietHoaDonByHoaDon(hoaDonBanHang.getId());
                        for (ChiTietHoaDon chiTietHoaDon : chiTietHd) {
                            tongChietKhau += Double.parseDouble(String.valueOf(chiTietHoaDon.getThanhTien()))
                                    - Double.parseDouble(String.valueOf(chiTietHoaDon.getThanhTienSauKm()));
                            tongDoanhThu += Double.parseDouble(String.valueOf(chiTietHoaDon.getThanhTienSauKm()));

                        }
                    }
                    thongKeCn.setTongChietKhau(new BigDecimal(tongChietKhau));
                    thongKeCn.setTongDoanhThu(new BigDecimal(tongDoanhThu));
                } else {
                    thongKeCn.setSoLuongDonHang(0);
                    thongKeCn.setTongChietKhau(new BigDecimal(0));
                    thongKeCn.setTongDoanhThu(new BigDecimal(0));
                }
                listThongKeChiNhanh.add(thongKeCn);
            }
        }
        return listThongKeChiNhanh;
    }

}
