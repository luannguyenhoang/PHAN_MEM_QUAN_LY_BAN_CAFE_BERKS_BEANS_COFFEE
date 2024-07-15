package repository;

import domainmodel.ChiTietSP;
import domainmodel.NguyenLieu;
import domainmodel.SanPham;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utility.Hibernateutility;

public class ChiTietSanPhamRepository {

    public void insertChiTietSanPham(float dinhLuong, SanPham sp, NguyenLieu nguyenlieu) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            ChiTietSP chiTietSp = new ChiTietSP();
            chiTietSp.setDinhLuong(dinhLuong);
            chiTietSp.setSanPhamKey(sp);
            chiTietSp.setNguyenLieukey(nguyenlieu);
            session.save(chiTietSp);
            trans.commit();
            session.close();
        }
    }

    public Set<ChiTietSP> getChiTietSpByIdSanPham(String id) {
        Set<ChiTietSP> setChiTiet = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            SanPham sp = session.get(SanPham.class, id);
            setChiTiet = sp.getChiTietSp();
            session.close();
        }
        return setChiTiet;
    }

    public static void deleteChiTietSpByIdSp(String id) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            SanPham sp = session.get(SanPham.class, id);
            Set<ChiTietSP> setChiTiet = sp.getChiTietSp();
            for (ChiTietSP chiTietSP : setChiTiet) {
                Set<ChiTietSP> setCt = chiTietSP.getNguyenLieukey().getChiTietSp();
                for (ChiTietSP ct : setCt) {
                    if (chiTietSP.getSanPhamKey().getId().equals(ct.getSanPhamKey().getId())) {
                        setCt.remove(ct);
                        break;
                    }
                }
            }

            trans.commit();
            session.close();
        }

    }

    public NguyenLieu getNguyenLieuByID(String id) {
        NguyenLieu nguyenLieu;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            nguyenLieu = session.get(NguyenLieu.class, id);
            session.close();
        }
        return nguyenLieu;
    }
}
