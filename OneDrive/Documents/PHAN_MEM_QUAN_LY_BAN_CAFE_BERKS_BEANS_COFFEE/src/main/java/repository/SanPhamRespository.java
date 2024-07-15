package repository;

import domainmodel.ChiNhanh;
import domainmodel.ChiTietSP;
import domainmodel.NguyenLieu;
import domainmodel.SanPham;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utility.Hibernateutility;

public class SanPhamRespository {

    public Set<SanPham> getAllSanPhamByChiNhanh(String idChiNhanh) {
        Set<SanPham> setSp = new HashSet<>();
        Set<NguyenLieu> setNguyenLieu;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            ChiNhanh chiNhanh = session.get(ChiNhanh.class, idChiNhanh);
            setNguyenLieu = chiNhanh.getListNguyenLieu();
            for (NguyenLieu nguyenLieu : setNguyenLieu) {
                Set<ChiTietSP> setChiTiet = nguyenLieu.getChiTietSp();
                for (ChiTietSP chiTietSP : setChiTiet) {
                    setSp.add(chiTietSP.getSanPhamKey());
                }
            }

            session.close();
        }
        return setSp;
    }

    public String insertSanPham(String ma, String ten, float giaBan, byte[] avatar) {
        String id = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            SanPham sanpham = new SanPham();
            sanpham.setId(id);
            sanpham.setMa(ma);
            sanpham.setTen(ten);
            sanpham.setGiaBan(giaBan);
            sanpham.setTrangThai(1);
            sanpham.setKhuyenMai(null);
            sanpham.setAvatar(avatar);
            id = (String) session.save(sanpham);
            trans.commit();
            session.close();
        }
        return id;

    }

    public void UpdateSanPham(String id, String ma, String ten, float giaBan, byte[] avatar) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            SanPham sanpham = session.get(SanPham.class, id);
            sanpham.setMa(ma);
            sanpham.setTen(ten);
            sanpham.setGiaBan(giaBan);
            sanpham.setAvatar(avatar);
            session.update(sanpham);
            trans.commit();
            session.close();
        }
    }

    public void deleteSanPham(String id) {
        Session session = Hibernateutility.getFactory().openSession();
        Transaction trans = session.beginTransaction();
        SanPham sanPham = session.get(SanPham.class, id);
        sanPham.setTrangThai(0);
        session.update(sanPham);
        trans.commit();
        session.close();
    }

    public List<SanPham> searchSanPham(String tenSp) {
        Transaction trans = null;
        List<SanPham> listSP = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            trans = session.beginTransaction();
            Query query = session.createQuery("FROM SanPham WHERE ten like :Ten");
            query.setParameter("Ten", "%" + tenSp + "%");
            List<SanPham> listSearch = query.list();
            for (SanPham sanPham : listSearch) {
                listSP.add(sanPham);
            }
            trans.commit();
            session.close();
        } catch (Exception ex) {
            System.out.println("Lỗi ko thể tìm kiếm");
        }
        return listSP;
    }

    public static SanPham getSanPhamById(String id) {
        SanPham sp;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            sp = session.get(SanPham.class, id);
            session.close();
        }
        return sp;
    }

    public static SanPham getSanPhamByMa(String ma) {
        SanPham sp = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM SanPham WHERE ma=:maSp");
            query.setParameter("maSp", ma);
            List<SanPham> list = query.getResultList();
            if (list.size() > 0) {
                sp = list.get(0);
            }
            session.close();
        }
        return sp;
    }

    public Set<NguyenLieu> getAllNguyenLieuByChiNhanh(String idChiNhanh) {
        Set<NguyenLieu> setNguyenLieu = new HashSet<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            ChiNhanh chiNhanh = session.get(ChiNhanh.class, idChiNhanh);
            Set<NguyenLieu> allNguyenLieu = chiNhanh.getListNguyenLieu();
            for (NguyenLieu nguyenLieu : allNguyenLieu) {
                setNguyenLieu.add(nguyenLieu);
            }
            session.close();
        }
        return setNguyenLieu;
    }
}
