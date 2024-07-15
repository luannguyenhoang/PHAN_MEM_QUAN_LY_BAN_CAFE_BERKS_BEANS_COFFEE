package repository;

import domainmodel.ChiNhanh;
import domainmodel.KhachHang;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utility.Hibernateutility;

/**
 *
 * @author duong
 */
public class KhachHangRepo {

    public KhachHangRepo() {

    }

    public KhachHang getKHById(String id) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            KhachHang kh = session.get(KhachHang.class, id);
            session.close();
            return kh;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    public int countKHByMa(String maKH) {
        List<KhachHang> khachHangs = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM KhachHang WHERE ma like :ma");
            query.setParameter("ma", maKH);
            khachHangs = query.getResultList();
            session.close();

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return khachHangs.size();
    }

    /// READ
    public List<KhachHang> getAllKhachHang() {
        List<KhachHang> khachHangs = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM KhachHang");
            khachHangs = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return khachHangs;
    }

    public List<KhachHang> getAllKHByChiNhanh(ChiNhanh cn) {
        List<KhachHang> khachHangs = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT KH FROM KhachHang KH INNER JOIN KH.listChiNhanh CNS WHERE CNS = :chiNhanh");
            query.setParameter("chiNhanh", cn);
            khachHangs = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return khachHangs;
    }

    // Filter and searh all
    public List<KhachHang> getAllKHByMa(String maKH) {
        List<KhachHang> khachHangs = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM KhachHang WHERE ma like :ma");
            query.setParameter("ma", "%" + maKH + "%");
            khachHangs = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return khachHangs;
    }

    public List<KhachHang> getAllKHByName(String tenKH) {
        List<KhachHang> khachHangs = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM KhachHang WHERE hoTen like :ten");
            query.setParameter("ten", "%" + tenKH + "%");
            khachHangs = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return khachHangs;
    }

    public List<KhachHang> getAllKHBySDT(String sdt) {
        List<KhachHang> khachHangs = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM KhachHang WHERE sdt like :sdt");
            query.setParameter("sdt", "%" + sdt + "%");
            khachHangs = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return khachHangs;
    }

    public List<KhachHang> getAllKHByTrangThai(int trangThai) {
        List<KhachHang> khachHangs = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM KhachHang WHERE trangThai = :trangThai");
            query.setParameter("trangThai", trangThai);
            khachHangs = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return khachHangs;
    }

    public List<KhachHang> getAllKHByMaAndTrangThai(int trangThai, String maKH) {
        List<KhachHang> khachHangs = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM KhachHang WHERE trangThai = :trangThai AND ma like :ma");
            query.setParameter("trangThai", trangThai);
            query.setParameter("ma", "%" + maKH + "%");
            khachHangs = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return khachHangs;
    }

    public List<KhachHang> getAllKHByNameAndTrangThai(int trangThai, String tenKH) {
        List<KhachHang> khachHangs = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM KhachHang WHERE trangThai = :trangThai AND hoTen like :ten");
            query.setParameter("trangThai", trangThai);
            query.setParameter("ten", "%" + tenKH + "%");
            khachHangs = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return khachHangs;
    }

    public List<KhachHang> getAllKHBySDTAndTrangThai(int trangThai, String sdt) {
        List<KhachHang> khachHangs = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM KhachHang WHERE trangThai = :trangThai AND sdt like :sdt");
            query.setParameter("trangThai", trangThai);
            query.setParameter("sdt", "%" + sdt + "%");
            khachHangs = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return khachHangs;
    }

    // Filter and search by chi nhanh
    public List<KhachHang> getAllKHByChiNhanhAndMa(ChiNhanh cn, String maKH) {
        List<KhachHang> khachHangs = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT KH FROM KhachHang KH INNER JOIN KH.listChiNhanh CNS WHERE CNS = :chiNhanh AND KH.ma like :ma");
            query.setParameter("chiNhanh", cn);
            query.setParameter("ma", "%" + maKH + "%");
            khachHangs = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return khachHangs;
    }

    public List<KhachHang> getAllKHByChiNhanhAndName(ChiNhanh cn, String tenKH) {
        List<KhachHang> khachHangs = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT KH FROM KhachHang KH INNER JOIN KH.listChiNhanh CNS WHERE CNS = :chiNhanh AND KH.hoTen like :ten");
            query.setParameter("chiNhanh", cn);
            query.setParameter("ten", "%" + tenKH + "%");
            khachHangs = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return khachHangs;
    }

    public List<KhachHang> getAllKHByChiNhanhAndSDT(ChiNhanh cn, String sdt) {
        List<KhachHang> khachHangs = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT KH FROM KhachHang KH INNER JOIN KH.listChiNhanh CNS WHERE CNS = :chiNhanh AND KH.sdt like :sdt");
            query.setParameter("chiNhanh", cn);
            query.setParameter("sdt", "%" + sdt + "%");
            khachHangs = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return khachHangs;
    }

    public List<KhachHang> getAllKHByChiNhanhAndTrangThai(ChiNhanh cn, int trangThai) {
        List<KhachHang> khachHangs = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT KH FROM KhachHang KH INNER JOIN KH.listChiNhanh CNS WHERE CNS = :chiNhanh AND KH.trangThai = :trangThai");
            query.setParameter("chiNhanh", cn);
            query.setParameter("trangThai", trangThai);
            khachHangs = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return khachHangs;
    }

    public List<KhachHang> getAllKHByChiNhanhAndTrangThaiAndMa(ChiNhanh cn, int trangThai, String maKH) {
        List<KhachHang> khachHangs = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT KH FROM KhachHang KH INNER JOIN KH.listChiNhanh CNS WHERE CNS = :chiNhanh AND KH.trangThai = :trangThai AND KH.ma like :ma");
            query.setParameter("chiNhanh", cn);
            query.setParameter("trangThai", trangThai);
            query.setParameter("ma", "%" + maKH + "%");
            khachHangs = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return khachHangs;
    }

    public List<KhachHang> getAllKHByChiNhanhAndTrangThaiAndName(ChiNhanh cn, int trangThai, String tenKH) {
        List<KhachHang> khachHangs = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT KH FROM KhachHang KH INNER JOIN KH.listChiNhanh CNS WHERE CNS = :chiNhanh AND KH.trangThai = :trangThai AND KH.hoTen like :ten");
            query.setParameter("chiNhanh", cn);
            query.setParameter("trangThai", trangThai);
            query.setParameter("ten", "%" + tenKH + "%");
            khachHangs = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return khachHangs;
    }

    public List<KhachHang> getAllKHByChiNhanhAndTrangThaiAndSDT(ChiNhanh cn, int trangThai, String sdt) {
        List<KhachHang> khachHangs = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT KH FROM KhachHang KH INNER JOIN KH.listChiNhanh CNS WHERE CNS = :chiNhanh AND KH.trangThai = :trangThai AND KH.sdt like :sdt");
            query.setParameter("chiNhanh", cn);
            query.setParameter("trangThai", trangThai);
            query.setParameter("sdt", "%" + sdt + "%");
            khachHangs = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return khachHangs;
    }

    // CRUD
    public boolean addKhachHang(KhachHang kh) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction tran = session.beginTransaction();
            session.save(kh);
            tran.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        }
    }

    public boolean updateKhachHang(String id, KhachHang kh) { // khong update chi nhanh
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction tran = session.beginTransaction();
//            KhachHang khachHang = session.get(KhachHang.class, kh.getId());
            KhachHang khachHang = session.get(KhachHang.class, id);
            khachHang.setMa(kh.getMa());
            khachHang.setHoTen(kh.getHoTen());
            khachHang.setGioiTinh(kh.getGioiTinh());
            khachHang.setSdt(kh.getSdt());
            khachHang.setThanhPho(kh.getThanhPho());
            khachHang.setQuocGia(kh.getQuocGia());
            khachHang.setTrangThai(kh.getTrangThai());
            khachHang.setDiemTichLuy(kh.getDiemTichLuy());
//            khachHang.setListChiNhanh(kh.getListChiNhanh()); // neu update chi nhanh thi phai lay dc chi nhanh khach muon update trong listChiNhanh, de nghi them
            session.update(khachHang);
            tran.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        }
    }
    
//    public boolean updateKH(String id, KhachHang kh) { // co update chi nhanh
//        try ( Session session = Hibernateutility.getFactory().openSession()) {
//            Transaction tran = session.beginTransaction();
////            KhachHang khachHang = session.get(KhachHang.class, kh.getId());
//            KhachHang khachHang = session.get(KhachHang.class, id);
//            khachHang.setMa(kh.getMa());
//            khachHang.setHoTen(kh.getHoTen());
//            khachHang.setGioiTinh(kh.getGioiTinh());
//            khachHang.setSdt(kh.getSdt());
//            khachHang.setThanhPho(kh.getThanhPho());
//            khachHang.setQuocGia(kh.getQuocGia());
//            khachHang.setTrangThai(kh.getTrangThai());
//            khachHang.setDiemTichLuy(kh.getDiemTichLuy());
////            khachHang.setListChiNhanh(kh.getListChiNhanh()); // neu update chi nhanh thi phai lay dc chi nhanh khach muon update trong listChiNhanh, de nghi them
//            session.update(khachHang);
//            tran.commit();
//            session.close();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace(System.err);
//            return false;
//        }
//    }

    public boolean deleteKhachHang(String id) { // chuyen trang thai
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction tran = session.beginTransaction();
            KhachHang khachHang = session.get(KhachHang.class, id);
            khachHang.setTrangThai(0);
            session.update(khachHang);
            tran.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        }
    }

    public boolean deleteKH(String id) { // Xoa han luon
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction tran = session.beginTransaction();
            KhachHang khachHang = session.get(KhachHang.class, id);
            Set<ChiNhanh> chiNhanhs = khachHang.getListChiNhanh();
            chiNhanhs.clear();
//            khachHang.setListChiNhanh(null);
            session.delete(khachHang);
            session.save(khachHang);
            tran.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        }
    }

}
