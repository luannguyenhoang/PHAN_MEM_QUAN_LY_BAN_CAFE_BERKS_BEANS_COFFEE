/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import domainmodel.Ban;
import domainmodel.ChiNhanh;
import domainmodel.ChiTietHoaDon;
import domainmodel.HoaDonBanHang;
import domainmodel.KhuVuc;
import domainmodel.SanPham;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utility.Hibernateutility;

/**
 *
 *
 * @author trant
 */
public class ThongKeRepository {

    public static List<HoaDonBanHang> getAllHoaDonBanHangByDate(String date) {
        List<HoaDonBanHang> listHoaDon = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            String beginDay = date + " 00:00:00";
            String endDay = date + " 23:59:59";
            Query query = session.createQuery("FROM HoaDonBanHang WHERE ngayTao BETWEEN " + "'" + beginDay + "'" + " AND " + "'" + endDay + "'");
            listHoaDon = query.getResultList();
            session.close();
        }
        return listHoaDon;
    }

    public static Set<ChiTietHoaDon> getChiTietHoaDonByHoaDon(String idHoaDon) {
        Set<ChiTietHoaDon> setChiTiet = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            HoaDonBanHang hoaDon = session.get(HoaDonBanHang.class, idHoaDon);
            setChiTiet = hoaDon.getChiTietHoaDon();
            session.close();
        }
        return setChiTiet;
    }

    public static List<SanPham> getAllSanPham() {
        List<SanPham> list = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            list = session.createQuery("FROM SanPham").getResultList();
            session.close();
        }
        return list;
    }

    public static Set<ChiTietHoaDon> getChiTietHoaDonBySanPham(String idSp) {
        Set<ChiTietHoaDon> chiTietHD = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            SanPham sp = session.get(SanPham.class, idSp);
            chiTietHD = sp.getChiTietHoaDon();
            session.close();
        }
        return chiTietHD;
    }

    public static List<ChiNhanh> getAllChiNhannh() {
        List<ChiNhanh> list = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            list = session.createQuery("FROM ChiNhanh").getResultList();
            session.close();
        }
        return list;
    }

    public static Set<HoaDonBanHang> getAllHoaDonByChiNhanh(String idChiNhanh) {
        Set<HoaDonBanHang> setHoaDon = new HashSet<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            ChiNhanh chiNhanh = session.get(ChiNhanh.class, idChiNhanh);
            Set<KhuVuc> khuVuc = chiNhanh.getSetKhuVuc();
            for (KhuVuc kv : khuVuc) {
                Set<Ban> ban = kv.getListBan();
                for (Ban b : ban) {
                    Set<HoaDonBanHang> hd = b.getSetHoaDon();
                    if (hd != null) {
                        for (HoaDonBanHang hoaDonBanHang : hd) {
                            setHoaDon.add(hoaDonBanHang);
                        }

                    }
                }
            }
            session.close();
        }
        return setHoaDon;
    }

}
