package repository;

import domainmodel.Ban;
import domainmodel.ChiNhanh;
import domainmodel.KhuVuc;
import domainmodel.NhanVien;
import domainmodel.TaiKhoanNguoiDung;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utility.Hibernateutility;

public class BanRepository_1 {

    public Set<Ban> getAllBanByKhuVuc(String idKhuVuc) {
        Set<Ban> setBan = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            KhuVuc kv = session.get(KhuVuc.class, idKhuVuc);
            setBan = kv.getListBan();
            trans.commit();
            session.close();
        }
        return setBan;
    }

    public String insertBan(Integer SoBan, KhuVuc kv) {
        String id = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            Ban ban = new Ban();
            ban.setSoBan(SoBan);
            ban.setTrangThaiSuDung(1);
            ban.setTrangThai(0);
            ban.setKhuVuc(kv);
            id = (String) session.save(ban);
            trans.commit();
            session.close();
        }
        return id;

    }

    public void updateBan(String idBan, KhuVuc kv) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            Ban ban = session.get(Ban.class, idBan);
            ban.setKhuVuc(kv);
            session.update(ban);
            trans.commit();
            session.close();
        }
    }

    public void deleteBan(String idBan) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            Ban ban = session.get(Ban.class, idBan);
            ban.setTrangThaiSuDung(0);
            session.update(ban);
            trans.commit();
            session.close();
        }
    }

    public Ban getBanFormSoBan(Integer soBan) {
        Ban ban = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            Query query = session.createQuery("FROM Ban Where soBan=:soBan");
            query.setParameter("soBan", soBan);
            List<Ban> list = query.getResultList();
            if (list.size() > 0) {
                ban = list.get(0);
            }

            trans.commit();
            session.close();
        }
        return ban;
    }

    public static ChiNhanh getChiNhanhByTaiKhoan(String idTaiKhoan) {
        ChiNhanh chiNhanh = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            TaiKhoanNguoiDung tk = session.get(TaiKhoanNguoiDung.class, idTaiKhoan);
            NhanVien nv = tk.getNhanVien();
            chiNhanh = nv.getChiNhanh();
            session.close();
        }
        return chiNhanh;
    }
}
