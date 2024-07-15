/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;

import java.util.List;
import java.util.Set;
import domainmodel.Ban;
import domainmodel.ChiNhanh;
import domainmodel.ChiTietHoaDon;
import domainmodel.ChiTietSP;
import domainmodel.HoaDonBanHang;
import domainmodel.KhachHang;
import domainmodel.KhuVuc;
import domainmodel.KhuyenMai;
import domainmodel.NguyenLieu;
import domainmodel.NhanVien;
import domainmodel.SanPham;
import domainmodel.TaiKhoanNguoiDung;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utility.Hibernateutility;

/**
 *
 * @author trant
 */
public class BanHangRepo {

    public static Set<SanPham> getAllSanPhamDangBanByChiNhanh(String idChiNhanh) {
        Set<SanPham> setSp = new HashSet<>();
        Set<NguyenLieu> setNguyenLieu;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            ChiNhanh chiNhanh = session.get(ChiNhanh.class, idChiNhanh);
            setNguyenLieu = chiNhanh.getListNguyenLieu();
            for (NguyenLieu nguyenLieu : setNguyenLieu) {
                Set<ChiTietSP> setChiTiet = nguyenLieu.getChiTietSp();
                for (ChiTietSP chiTietSP : setChiTiet) {
                    if (chiTietSP.getSanPhamKey().getTrangThai() == 1) {
                        setSp.add(chiTietSP.getSanPhamKey());
                    }

                }
            }

            session.close();
        }
        return setSp;
    }

    public static void updateNguyenLieuAfterSellSanPham(String idSanPham, int soLuongMua) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            SanPham sanPham = session.get(SanPham.class, idSanPham);
            Set<ChiTietSP> chiTietSp = sanPham.getChiTietSp();
            for (int i = 0; i < soLuongMua; i++) {
                for (ChiTietSP ctSp : chiTietSp) {
                    float soLuongTon = ctSp.getNguyenLieukey().getSoLuongTon();
                    float dinhLuong = ctSp.getDinhLuong();
                    ctSp.getNguyenLieukey().setSoLuongTon(soLuongTon - dinhLuong);
                    session.update(ctSp);
                }
            }
            trans.commit();
            session.close();
        }
    }

    public static boolean checkDinhLuongPhaChex3(String idSanPham) {
        boolean check = true;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            SanPham sanPham = session.get(SanPham.class, idSanPham);
            Set<ChiTietSP> setChiTiet = sanPham.getChiTietSp();
            for (ChiTietSP chiTietSP : setChiTiet) {
                float slTon = chiTietSP.getNguyenLieukey().getSoLuongTon();
                float dinhLuong = chiTietSP.getDinhLuong();
                if (slTon < (dinhLuong * 3)) {
                    check = false;
                    break;
                }
            }
            trans.commit();
            session.close();
        }
        return check;
    }

    public static Set<KhuVuc> getAllKhuVucByChiNhanh(String idChiNhanh) {
        Set<KhuVuc> setKhuVuc = null;
        Set<KhuVuc> khuVucDangHoatDong = new HashSet<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            ChiNhanh chiNhanh = session.get(ChiNhanh.class, idChiNhanh);
            setKhuVuc = chiNhanh.getSetKhuVuc();
            for (KhuVuc khuVuc : setKhuVuc) {
                if (khuVuc.getTrangThai() == 1) {
                    khuVucDangHoatDong.add(khuVuc);
                }
            }
            session.close();
        }
        return khuVucDangHoatDong;
    }

    public static List<ChiNhanh> getAllChiNhanh() {
        List<ChiNhanh> ListChiNhanh = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            ListChiNhanh = session.createQuery("FROM ChiNhanh WHERE trangThai=1").list();
            session.close();
        }
        return ListChiNhanh;
    }

    public static Set<KhuyenMai> getAllKhuyenMaiByChiNhanh(String idChiNhanh) {
        Set<KhuyenMai> setKhuyenMai = new HashSet<>();
        Set<SanPham> setSanPham = getAllSanPhamDangBanByChiNhanh(idChiNhanh);
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            for (SanPham sanPham : setSanPham) {
                KhuyenMai khuyenMai = sanPham.getKhuyenMai();
                if (khuyenMai != null) {
                    setKhuyenMai.add(khuyenMai);
                }
            }
            session.close();
        }
        return setKhuyenMai;
    }

    public static Set<Ban> getAllBanByKhuVuc(KhuVuc khuVuc) {
        Set<Ban> setBan = new HashSet<>();
        Set<Ban> setBanDangHoatDong = new HashSet<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            KhuVuc kv = session.get(KhuVuc.class, khuVuc.getId());
            setBan = kv.getListBan();
            for (Ban ban : setBan) {
                if (ban.getTrangThaiSuDung() == 1) {
                    setBanDangHoatDong.add(ban);
                }
            }
            session.close();
        }
        return setBanDangHoatDong;
    }

    public static KhuyenMai getKhuyenMaibySanPham(String id) {
        KhuyenMai khuyenMai = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            SanPham sp = session.get(SanPham.class, id);
            if (sp.getKhuyenMai() != null) {
                khuyenMai = sp.getKhuyenMai();
            }
            session.close();
        }
        return khuyenMai;
    }

    public String insertKhachHang(KhachHang khachHang) {
        String id = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            id = (String) session.save(khachHang);
            trans.commit();
            session.close();
        }
        return id;

    }

    public static KhachHang getKhachHangByMa(String maKhach) {
        KhachHang kh = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM KhachHang WHERE ma=:maKhach");
            query.setParameter("maKhach", maKhach);
            List<KhachHang> list = query.getResultList();
            if (list.size() > 0) {
                kh = list.get(0);
            }
            session.close();
        }
        return kh;
    }

    public static KhachHang getKhachHangBySdt(String sdt) {
        KhachHang kh = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM KhachHang WHERE sdt=:sdtKhach");
            query.setParameter("sdtKhach", sdt);
            List<KhachHang> listKh = query.getResultList();
            if (!listKh.isEmpty()) {
                kh = listKh.get(0);
            }
            session.close();
        }
        return kh;
    }

    public static void updateDiemKhachHang(String idKhach, Integer diemTichLuy) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            KhachHang khachHang = session.get(KhachHang.class, idKhach);
            khachHang.setDiemTichLuy(diemTichLuy);
            session.update(khachHang);
            trans.commit();
            session.close();
        }
    }

    public static ChiNhanh getOneChiNhanh() {
        List<ChiNhanh> listChiNhanh = null;
        ChiNhanh chiNhanh = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            listChiNhanh = session.createQuery("FROM ChiNhanh").list();
            if (!listChiNhanh.isEmpty()) {
                chiNhanh = listChiNhanh.get(0);
            }
            session.close();
        }
        return chiNhanh;
    }

    public static String inserHoaDon(String ma, LocalDateTime ngayTao, String idNhanVien,
            Integer soBan) {
        String id = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            NhanVien nhanVien = null;
            if (idNhanVien != null) {
                nhanVien = session.get(NhanVien.class, idNhanVien);
            }
            Query query = session.createQuery("FROM Ban WHERE soBan=:soBan");
            query.setParameter("soBan", soBan);
            Ban ban = (Ban) query.uniqueResult();
            HoaDonBanHang hoaDon = new HoaDonBanHang();
            hoaDon.setMa(ma);
            hoaDon.setNgayTao(ngayTao);
            hoaDon.setNhanVien(nhanVien);
            hoaDon.setTrangThai(1);
            hoaDon.setBan(ban);
            id = (String) session.save(hoaDon);
            trans.commit();
            session.close();
        }
        return id;
    }

    public static void insertChiTietHoaDon(String idSanPham, String idHoaDon, int soLuongMua,
            BigDecimal thanhTien, BigDecimal thanhTienSauKM) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            ChiTietHoaDon chiTietHd = new ChiTietHoaDon();
            HoaDonBanHang hoaDon = session.get(HoaDonBanHang.class, idHoaDon);
            SanPham sanPham = session.get(SanPham.class, idSanPham);
            chiTietHd.setSanPhamKey(sanPham);
            chiTietHd.setHoaDonKey(hoaDon);
            chiTietHd.setThanhTien(thanhTien);
            chiTietHd.setSoLuongMua(soLuongMua);
            chiTietHd.setThanhTienSauKm(thanhTienSauKM);
            session.save(chiTietHd);
            trans.commit();
            session.close();
        }

    }

    public static List<HoaDonBanHang> getAllHoaDon() {
        List<HoaDonBanHang> ListHoaDon = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            ListHoaDon = session.createQuery("FROM HoaDonBanHang").list();
            session.close();
        }
        return ListHoaDon;
    }

    public static NhanVien getNhanVienbyTaiKhoan(String idTaiKhoan) {
        NhanVien nhanVien = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            TaiKhoanNguoiDung taiKhoan = session.get(TaiKhoanNguoiDung.class, idTaiKhoan);
            nhanVien = taiKhoan.getNhanVien();
            session.close();
        }
        return nhanVien;
    }

    public static ChiNhanh getChiNhanhbyTaiKhoan(String idTaiKhoan) {
        NhanVien nhanVien = null;
        ChiNhanh chiNhanh = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            TaiKhoanNguoiDung taiKhoan = session.get(TaiKhoanNguoiDung.class, idTaiKhoan);
            nhanVien = taiKhoan.getNhanVien();
            chiNhanh = nhanVien.getChiNhanh();
            session.close();
        }
        return chiNhanh;
    }

    public static void updateTrangThaiBanBySoBan(Integer soBan) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            Query query = session.createQuery("FROM Ban WHERE soBan=:soBan");
            query.setParameter("soBan", soBan);
            Ban ban = (Ban) query.uniqueResult();
            if (ban.getTrangThai() == 0) {
                ban.setTrangThai(1);
            } else {
                ban.setTrangThai(0);
            }
            session.update(ban);
            trans.commit();
            session.close();
        }

    }

}
