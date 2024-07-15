package repository;

import domainmodel.NhanVien;
import domainmodel.TaiKhoanNguoiDung;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utility.Hibernateutility;
import viewmodel.ChiNhanhViewModel_Hoang;

public class TaiKhoanNguoiDungRepository {

    public List<TaiKhoanNguoiDung> getAllTaiKhoanNguoiDung() {
        List<TaiKhoanNguoiDung> list = null;
        try (Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            list = session.createQuery("FROM TaiKhoanNguoiDung").list();
            trans.commit();
            session.close();
        }
        return list;
    }

    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> list = null;
        try (Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            list = session.createQuery("FROM NhanVien").list();
            trans.commit();
            session.close();
        }
        return list;
    }

    public NhanVien getNhanVienFromMa(String Ma) {
        NhanVien nv = null;
        try (Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            Query query = session.createQuery("FROM NhanVien Where Ma=:Ma");
            query.setParameter("Ma", Ma);
            List<NhanVien> list = query.getResultList();
            if (list.size() > 0) {
                nv = list.get(0);
            }

            trans.commit();
            session.close();
        }
        return nv;
    }

    public String insertTaiKhoanNguoiDung(String TenTk, String MatKhau, NhanVien nv) {
        String id = null;
        try (Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            TaiKhoanNguoiDung taiKhoanNguoiDung = new TaiKhoanNguoiDung();
            taiKhoanNguoiDung.setTenTK(TenTk);
            taiKhoanNguoiDung.setMatKhau(MatKhau);
            taiKhoanNguoiDung.setTrangThai(1);
            taiKhoanNguoiDung.setNhanVien(nv);
            id = (String) session.save(taiKhoanNguoiDung);
            trans.commit();
            session.close();
        }
        return id;

    }

    public void updateTaiKhoanNguoiDung(String id, String TenTk, String MatKhau, NhanVien nv) {
        try (Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            TaiKhoanNguoiDung taiKhoanNguoiDung = session.get(TaiKhoanNguoiDung.class, id);
            taiKhoanNguoiDung.setTenTK(TenTk);
            taiKhoanNguoiDung.setMatKhau(MatKhau);
            taiKhoanNguoiDung.setNhanVien(nv);
            session.update(taiKhoanNguoiDung);
            trans.commit();
            session.close();
        }
    }

    public void deleteTaiKhoanNguoiDung(String idTk) {
        try (Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            TaiKhoanNguoiDung taiKhoanNguoiDung = session.get(TaiKhoanNguoiDung.class, idTk);
            taiKhoanNguoiDung.setTrangThai(0);
            session.update(taiKhoanNguoiDung);
            trans.commit();
            session.close();
        }
    }

}
