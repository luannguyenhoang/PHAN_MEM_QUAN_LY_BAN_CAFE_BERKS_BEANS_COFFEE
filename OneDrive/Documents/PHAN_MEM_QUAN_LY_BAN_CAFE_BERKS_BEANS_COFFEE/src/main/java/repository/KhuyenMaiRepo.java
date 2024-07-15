package repository;

import domainmodel.ChiNhanh;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import domainmodel.KhuyenMai;
import domainmodel.NhanVien;
import domainmodel.SanPham;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.sql.Date;
import javax.persistence.TemporalType;
import org.hibernate.Transaction;
import org.hibernate.type.DateType;
import utility.Hibernateutility;

/**
 *
 * @author duong
 */
public class KhuyenMaiRepo {

    public KhuyenMaiRepo() {

    }

    public KhuyenMai getKMById(String id) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
//            Object obj = session.get(KhuyenMai.class, id);
            KhuyenMai km = session.get(KhuyenMai.class, id);
            session.close();
//            return (KhuyenMai)obj;
            return km;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    // READ: Search, Filter
    public List<KhuyenMai> getAllKhuyenMai() {
        List<KhuyenMai> khuyenMais = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM KhuyenMai");
            khuyenMais = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return khuyenMais;
    }

    public List<KhuyenMai> getAllKMByChiNhanh(ChiNhanh cn) {
        List<KhuyenMai> khuyenMais = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT KM FROM ChiNhanh CN "
                    + "INNER JOIN CN.listNguyenLieu LNL "
                    + "INNER JOIN ChiTietSP CTSP ON CTSP.nguyenLieukey.id = LNL.id "
                    + "INNER JOIN CTSP.sanPhamKey.khuyenMai KM "
                    + "WHERE CN.id = :id");
//            Query query = session.createQuery("SELECT DISTINCT KM FROM ChiNhanh CN "
//                    + "INNER JOIN CN.listNguyenLieu LNL "
//                    + "INNER JOIN ChiTietSP CTSP ON CTSP.nguyenLieukey.id = LNL.id "
//                    + "INNER JOIN SanPham SP ON CTSP.sanPhamKey.id = SP.id "
//                    + "INNER JOIN SP.khuyenMai KM "
//                    + "WHERE CN.id = :id");
            query.setParameter("id", cn.getId());
            khuyenMais = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return khuyenMais;
    }

    public List<KhuyenMai> getAllKMByName(String tenKM) {
        List<KhuyenMai> khuyenMais = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM KhuyenMai WHERE ten like :ten");
            query.setParameter("ten", "%" + tenKM + "%");
            khuyenMais = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return khuyenMais;
    }

    public List<KhuyenMai> getAllKMByDateToDate(Date d1, Date d2) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String fromDate = format.format(d1);
//        String toDate = format.format(d2);
//        Date toDate = new Date(d2.getYear(), d2.getMonth(), d2.getDate() + 1, 0, 0, 0);
//        Date fromDate = new Date(d1.getYear(), d1.getMonth(), d1.getDate(), 0, 0, 0);
        List<KhuyenMai> khuyenMais = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT KM FROM KhuyenMai KM WHERE KM.ngayBatDau >= :d1 AND KM.ngayKetThuc <= :d2");
//            Query query = session.createQuery("SELECT KM FROM KhuyenMai KM WHERE KM.ngayKetThuc <= :d2");// cai nay thi chay dc
//            Query query = session.createQuery("SELECT KM FROM KhuyenMai KM WHERE KM.ngayBatDau IN(:d1, :d2) AND KM.ngayKetThuc <= :d2");
//            query.setParameter("d1", d1);
//            query.setParameter("d2", d2);
//            query.setParameter("d1", d1,TemporalType.DATE);
//            query.setParameter("d2", d2, TemporalType.DATE);
//            query.setParameter("d1", d1,DateType.INSTANCE);
//            query.setParameter("d2", d2, DateType.INSTANCE);
            query.setTimestamp("d1", d1);
            query.setTimestamp("d2", d2);
//            query.setDate("d1", d1);
//            query.setDate("d2", d2);
            khuyenMais = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return khuyenMais;
    }

    public List<KhuyenMai> getAllKMByTrangThai(Integer trangThai) {
        List<KhuyenMai> khuyenMais = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM KhuyenMai WHERE trangThai = :trangThai");
            query.setParameter("trangThai", trangThai);
            khuyenMais = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return khuyenMais;
    }

    public List<KhuyenMai> getAllKMByNameAndTrangThai(int trangThai, String tenKM) {
        List<KhuyenMai> khuyenMais = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM KhuyenMai WHERE trangThai = :trangThai AND ten like :ten");
            query.setParameter("trangThai", trangThai);
            query.setParameter("ten", "%" + tenKM + "%");
            khuyenMais = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return khuyenMais;
    }

//    public List<KhuyenMai> getAllNVByDateAndTrangThai(int trangThai, Date d1, Date d2) {
//        return null;
//    }
    public List<KhuyenMai> getAllKMByChiNhanhAndName(ChiNhanh cn, String tenKM) {
        List<KhuyenMai> khuyenMais = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT KM FROM ChiNhanh CN "
                    + "INNER JOIN CN.listNguyenLieu LNL "
                    + "INNER JOIN ChiTietSP CTSP ON CTSP.nguyenLieukey.id = LNL.id "
                    + "INNER JOIN SanPham SP ON CTSP.sanPhamKey.id = SP.id "
                    + "INNER JOIN SP.khuyenMai KM "
                    + "WHERE CN.id = :id AND KM.ten like :ten");
            query.setParameter("id", cn.getId());
            query.setParameter("ten", "%" + tenKM + "%");
            khuyenMais = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return khuyenMais;
    }

//    public List<KhuyenMai> getAllKMByChiNhanhAndDate(ChiNhanh cn, Date d1, Date d2) {
//        return null;
//    }
    public List<KhuyenMai> getAllKMByChiNhanhAndTrangThai(ChiNhanh cn, int trangThai) {
        List<KhuyenMai> khuyenMais = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT KM FROM ChiNhanh CN "
                    + "INNER JOIN CN.listNguyenLieu LNL "
                    + "INNER JOIN ChiTietSP CTSP ON CTSP.nguyenLieukey.id = LNL.id "
                    + "INNER JOIN SanPham SP ON CTSP.sanPhamKey.id = SP.id "
                    + "INNER JOIN SP.khuyenMai KM "
                    + "WHERE CN.id = :id AND KM.trangThai = :trangThai");
            query.setParameter("id", cn.getId());
            query.setParameter("trangThai", trangThai);
            khuyenMais = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return khuyenMais;
    }

    public List<KhuyenMai> getAllKMByChiNhanhAndTrangThaiAndName(ChiNhanh cn, int trangThai, String tenKM) {
        List<KhuyenMai> khuyenMais = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT KM FROM ChiNhanh CN "
                    + "INNER JOIN CN.listNguyenLieu LNL "
                    + "INNER JOIN ChiTietSP CTSP ON CTSP.nguyenLieukey.id = LNL.id "
                    + "INNER JOIN SanPham SP ON CTSP.sanPhamKey.id = SP.id "
                    + "INNER JOIN SP.khuyenMai KM "
                    + "WHERE CN.id = :id AND KM.trangThai = :trangThai AND KM.ten like :ten");
            query.setParameter("id", cn.getId());
            query.setParameter("trangThai", trangThai);
            query.setParameter("ten", "%" + tenKM + "%");
            khuyenMais = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return khuyenMais;
    }
//    public List<KhuyenMai> getAllKMByChiNhanhAndTrangThaiAndDate(ChiNhanh cn, int trangThai, Date d1, Date d2) {
//        return null;
//    }
    // CUD

    public boolean addKhuyenMai(KhuyenMai km, List<SanPham> sanPhamAdds) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
//            session.beginTransaction().begin(); // dung kieu nay bi exception, de tim hieu sau
            Transaction tran = session.beginTransaction();
            session.save(km);
            if (!sanPhamAdds.isEmpty()) {
                for (SanPham sp : sanPhamAdds) {
                    sp.setKhuyenMai(km);
                    session.update(sp);
                }
            }
//            session.beginTransaction().commit();
            tran.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return false;
        }
    }

    public boolean updateKhuyenMai(String id, KhuyenMai km, List<SanPham> sanPhamAdds, List<SanPham> sanPhamDels) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction tran = session.beginTransaction();
            KhuyenMai khuyenMai = session.get(KhuyenMai.class, id);
//            KhuyenMai khuyenMai = session.get(KhuyenMai.class, km.getId()); // neu km dc truyen id roi thi no dang o db
            khuyenMai.setTen(km.getTen());
            khuyenMai.setMota(km.getMota());
            khuyenMai.setNgayBatDau(km.getNgayBatDau());
            khuyenMai.setNgayKetThuc(km.getNgayKetThuc());
            khuyenMai.setGiaTriChietKhau(km.getGiaTriChietKhau());
            khuyenMai.setTrangThai(km.getTrangThai());
            session.update(khuyenMai);
            // delete
            if (!sanPhamDels.isEmpty()) { // nue spdel va spadd co chung phan tu thi bi loi :A different object with the same identifier value was already associated with the session
                for (SanPham spd : sanPhamDels) {
                    spd.setKhuyenMai(null); // giai phap1 : xoa phan tu chung trong spdel di
                    session.update(spd);// giai phap 2: dang tim
                }
            }
            // add
            if (!sanPhamAdds.isEmpty()) {
                for (SanPham spa : sanPhamAdds) {
                    spa.setKhuyenMai(khuyenMai);
                    session.update(spa);
                }
            }

            tran.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return false;
        }
    }

    public Boolean deleteKhuyenMai(String id) { // chuyen trang thai
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction tran = session.beginTransaction();
            KhuyenMai khuyenMai = session.get(KhuyenMai.class, id);
            khuyenMai.setTrangThai(0);
            session.update(khuyenMai);
            tran.commit();
            session.close();
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public boolean deleteKM(String id, List<SanPham> sanPhamDels) { // Xoa han luon
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction tran = session.beginTransaction();
            KhuyenMai khuyenMai = session.get(KhuyenMai.class, id);
            for (SanPham spd : sanPhamDels) {
                spd.setKhuyenMai(null);
                session.update(spd);
            }
            session.delete(khuyenMai);
            tran.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return false;
        }
    }

    public boolean deleteKM1(String id, List<SanPham> sanPhamDels) { // Chi doi trang thai va xoa sp co khuyen mai nay
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction tran = session.beginTransaction();
            KhuyenMai khuyenMai = session.get(KhuyenMai.class, id);
            khuyenMai.setTrangThai(0);
            for (SanPham spd : sanPhamDels) {
                spd.setKhuyenMai(null);
                session.update(spd);
            }
            session.update(khuyenMai);
            tran.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return false;
        }
    }

    //// ChiNhanh Repo
    public List<ChiNhanh> getAllChiNhanhON() {
        List<ChiNhanh> chiNhanhs = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM ChiNhanh WHERE trangThai = 1");
            chiNhanhs = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return chiNhanhs;
    }

    public List<ChiNhanh> getAllChiNhanh() {
        List<ChiNhanh> chiNhanhs = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM ChiNhanh");
            chiNhanhs = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return chiNhanhs;
    }

    public ChiNhanh getChiNhanhById(String id) {
        ChiNhanh cn = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            cn = session.get(ChiNhanh.class, id);
            session.close();
//            return cn;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return cn;
    }

//    public ChiNhanh getChiNhanhByNV(NhanVien nv) {
//        try(Session session = Hibernateutility.getFactory().openSession()) {
//            Query query = session.createQuery("FROM SanPham");
//            sanPhams = query.getResultList();
//            session.close();
//        } catch (Exception e) {
//            e.printStackTrace(System.out);
//        }
//    }
    ////  Repo SanPham
    public SanPham getSPById(String id) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            SanPham sp = session.get(SanPham.class, id);
            session.close();
            return sp;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public List<SanPham> getAllSP() {
        List<SanPham> sanPhams = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM SanPham");
            sanPhams = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return sanPhams;
    }

    public List<SanPham> getAllSPByMa(String maSP) {
        List<SanPham> sanPhams = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM SanPham WHERE ma like :ma");
            query.setParameter("ma", "%" + maSP + "%");
            sanPhams = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return sanPhams;
    }

    public List<SanPham> getAllSPByName(String tenSP) {
        List<SanPham> sanPhams = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM SanPham WHERE ten like :ten");
            query.setParameter("ten", "%" + tenSP + "%");
            sanPhams = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return sanPhams;
    }

    public List<SanPham> getAllSPByChiNhanh(ChiNhanh cn) {
        List<SanPham> sanPhams = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT SP FROM ChiNhanh CN "
                    + "INNER JOIN CN.listNguyenLieu LNL "
                    + "INNER JOIN ChiTietSP CTSP ON CTSP.nguyenLieukey.id = LNL.id "
                    + "INNER JOIN SanPham SP ON CTSP.sanPhamKey.id = SP.id "
                    + "WHERE CN.id = :id");
            query.setParameter("id", cn.getId());
            sanPhams = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return sanPhams;
    }

    public List<SanPham> getAllSPByChiNhanhAndMa(ChiNhanh cn, String maSP) {
        List<SanPham> sanPhams = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT SP FROM ChiNhanh CN "
                    + "INNER JOIN CN.listNguyenLieu LNL "
                    + "INNER JOIN ChiTietSP CTSP ON CTSP.nguyenLieukey.id = LNL.id "
                    + "INNER JOIN SanPham SP ON CTSP.sanPhamKey.id = SP.id "
                    + "WHERE CN.id = :id AND SP.ma like :ma");
            query.setParameter("id", cn.getId());
            query.setParameter("ma", "%" + maSP + "%");
            sanPhams = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return sanPhams;
    }

    public List<SanPham> getAllSPByChiNhanhAndName(ChiNhanh cn, String tenSP) {
        List<SanPham> sanPhams = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT SP FROM ChiNhanh CN "
                    + "INNER JOIN CN.listNguyenLieu LNL "
                    + "INNER JOIN ChiTietSP CTSP ON CTSP.nguyenLieukey.id = LNL.id "
                    + "INNER JOIN SanPham SP ON CTSP.sanPhamKey.id = SP.id "
                    + "WHERE CN.id = :id AND SP.ten like :ten");
            query.setParameter("id", cn.getId());
            query.setParameter("ten", "%" + tenSP + "%");
            sanPhams = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return sanPhams;
    }

    public List<SanPham> getAllSPByKhuyenMai(KhuyenMai km) { // fill tat sp co khuyen mai, va o tat ca chi nhanh
        List<SanPham> sanPhams = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT SP FROM SanPham SP "
                    + "INNER JOIN SP.khuyenMai KM WHERE KM.id = :id");
            query.setParameter("id", km.getId());
            sanPhams = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return sanPhams;
    }

    public List<SanPham> getAllSPByKhuyenMaiAndChiNhanh(ChiNhanh cn, KhuyenMai km) { // dùng fill len bang sp co khuyen mai khi chon chi nhanh
        List<SanPham> sanPhams = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT SP FROM ChiNhanh CN "
                    + "INNER JOIN CN.listNguyenLieu LNL "
                    + "INNER JOIN ChiTietSP CTSP ON CTSP.nguyenLieukey.id = LNL.id "
                    + "INNER JOIN SanPham SP ON CTSP.sanPhamKey.id = SP.id "
                    + "INNER JOIN SP.khuyenMai KM "
                    + "WHERE CN.id = :id AND KM = :km");
            query.setParameter("id", cn.getId());
            query.setParameter("km", km);
            sanPhams = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return sanPhams;
    }

    public SanPham getSanPhamByMa(String ma) {
        SanPham sp = new SanPham();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM SanPham WHERE ma = :ma");
            query.setParameter("ma", ma);
            sp = (SanPham) query.getResultList().get(0);
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return sp;
    }

    // test
    public static void main(String[] args) {
        Date d1 = new Date(2022, 13, 10);
        Date d2 = new Date(2022, 13, 30);
        System.out.println(new KhuyenMaiRepo().getAllKMByDateToDate(d1, d2).size());
        Integer[] index = new Integer[2];
        for (Integer i : index) {
            System.out.println(i);
        }
        System.out.println(new KhuyenMaiRepo().getChiNhanhById("8FE134E6-5E39-48AA-BAE2-DC3A489EA158").getMa());
    }
}
