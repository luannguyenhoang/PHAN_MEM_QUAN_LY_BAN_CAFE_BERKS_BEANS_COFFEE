package repository;

import domainmodel.ChiNhanh;
import domainmodel.ChucVu;
import domainmodel.NhanVien;
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
public class NhanVienRepo {

    public NhanVienRepo() {
    }

    // get Nhan vien
    public NhanVien getNhanVienById(String id) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            NhanVien nv = session.get(NhanVien.class, id);
            session.close();
            return nv;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    public int countNVByMa(String maNV) {
        List<NhanVien> nhanViens = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM NhanVien WHERE ma like :ma");
            query.setParameter("ma", maNV);
            nhanViens = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return nhanViens.size();
    }

    // READ
    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> nhanViens = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM NhanVien");
            nhanViens = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return nhanViens;
    }

    public List<NhanVien> getAllNVByChiNhanh(ChiNhanh cn) {
        List<NhanVien> nhanViens = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT NV FROM NhanVien NV WHERE NV.chiNhanh = :chiNhanh");
            query.setParameter("chiNhanh", cn);
            nhanViens = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return nhanViens;
    }

    // Filter and searh all
    public List<NhanVien> getAllNVByMa(String maNV) {
        List<NhanVien> nhanViens = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM NhanVien WHERE ma like :ma");
            query.setParameter("ma", "%" + maNV + "%");
            nhanViens = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return nhanViens;
    }

    public List<NhanVien> getAllNVByName(String tenNV) {
        List<NhanVien> nhanViens = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM NhanVien WHERE hoTen like :ten");
            query.setParameter("ten", "%" + tenNV + "%");
            nhanViens = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return nhanViens;
    }

    public List<NhanVien> getAllNVBySDT(String sdt) {
        List<NhanVien> nhanViens = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM NhanVien WHERE sdt like :sdt");
            query.setParameter("sdt", "%" + sdt + "%");
            nhanViens = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return nhanViens;
    }

    public List<NhanVien> getAllNVByTrangThai(int trangThai) {
        List<NhanVien> nhanViens = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM NhanVien WHERE trangThai = :trangThai");
            query.setParameter("trangThai", trangThai);
            nhanViens = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return nhanViens;
    }

    public List<NhanVien> getAllNVByMaAndTrangThai(int trangThai, String maNV) {
        List<NhanVien> nhanViens = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM NhanVien WHERE trangThai = :trangThai AND ma like :ma");
            query.setParameter("trangThai", trangThai);
            query.setParameter("ma", "%" + maNV + "%");
            nhanViens = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return nhanViens;
    }

    public List<NhanVien> getAllNVByNameAndTrangThai(int trangThai, String tenNV) {
        List<NhanVien> nhanViens = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM NhanVien WHERE trangThai = :trangThai AND hoTen like :ten");
            query.setParameter("trangThai", trangThai);
            query.setParameter("ten", "%" + tenNV + "%");
            nhanViens = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return nhanViens;
    }

    public List<NhanVien> getAllNVBySDTAndTrangThai(int trangThai, String sdt) {
        List<NhanVien> nhanViens = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM NhanVien WHERE trangThai = :trangThai AND sdt like :sdt");
            query.setParameter("trangThai", trangThai);
            query.setParameter("sdt", "%" + sdt + "%");
            nhanViens = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return nhanViens;
    }

    // Filter and search by chi nhanh
    public List<NhanVien> getAllNVByChiNhanhAndMa(ChiNhanh cn, String maNV) {
        List<NhanVien> nhanViens = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT NV FROM NhanVien NV WHERE NV.chiNhanh = :chiNhanh AND NV.ma like :ma");
            query.setParameter("chiNhanh", cn);
            query.setParameter("ma", "%" + maNV + "%");
            nhanViens = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return nhanViens;
    }

    public List<NhanVien> getAllNVByChiNhanhAndName(ChiNhanh cn, String tenNV) {
        List<NhanVien> nhanViens = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT NV FROM NhanVien NV WHERE NV.chiNhanh = :chiNhanh AND NV.hoTen like :ten");
            query.setParameter("chiNhanh", cn);
            query.setParameter("ten", "%" + tenNV + "%");
            nhanViens = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return nhanViens;
    }

    public List<NhanVien> getAllNVByChiNhanhAndSDT(ChiNhanh cn, String sdt) {
        List<NhanVien> nhanViens = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT NV FROM NhanVien NV WHERE NV.chiNhanh = :chiNhanh AND NV.sdt like :sdt");
            query.setParameter("chiNhanh", cn);
            query.setParameter("sdt", "%" + sdt + "%");
            nhanViens = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return nhanViens;
    }

    public List<NhanVien> getAllNVByChiNhanhAndTrangThai(ChiNhanh cn, int trangThai) {
        List<NhanVien> nhanViens = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT NV FROM NhanVien NV WHERE NV.chiNhanh = :chiNhanh AND NV.trangThai = :trangThai");
            query.setParameter("chiNhanh", cn);
            query.setParameter("trangThai", trangThai);
            nhanViens = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return nhanViens;
    }

    public List<NhanVien> getAllNVByChiNhanhAndTrangThaiAndMa(ChiNhanh cn, int trangThai, String maNV) {
        List<NhanVien> nhanViens = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT NV FROM NhanVien NV WHERE NV.chiNhanh = :chiNhanh AND NV.trangThai = :trangThai AND NV.ma like :ma");
            query.setParameter("chiNhanh", cn);
            query.setParameter("trangThai", trangThai);
            query.setParameter("ma", "%" + maNV + "%");
            nhanViens = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return nhanViens;
    }

    public List<NhanVien> getAllNVByChiNhanhAndTrangThaiAndName(ChiNhanh cn, int trangThai, String tenNV) {
        List<NhanVien> nhanViens = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT NV FROM NhanVien NV WHERE NV.chiNhanh = :chiNhanh AND NV.trangThai = :trangThai AND NV.hoTen like :ten");
            query.setParameter("chiNhanh", cn);
            query.setParameter("trangThai", trangThai);
            query.setParameter("ten", "%" + tenNV + "%");
            nhanViens = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return nhanViens;
    }

    public List<NhanVien> getAllNVByChiNhanhAndTrangThaiAndSDT(ChiNhanh cn, int trangThai, String sdt) {
        List<NhanVien> nhanViens = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT DISTINCT NV FROM NhanVien NV WHERE NV.chiNhanh = :chiNhanh AND NV.trangThai = :trangThai AND NV.sdt like :sdt");
            query.setParameter("chiNhanh", cn);
            query.setParameter("trangThai", trangThai);
            query.setParameter("sdt", "%" + sdt + "%");
            nhanViens = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return nhanViens;
    }

    // CRUD
    public boolean addNhanVien(NhanVien nv) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction tran = session.beginTransaction();
            session.save(nv);
            tran.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        }
    }

    public boolean updateNhanVien(String id, NhanVien nv) { // khong update chi nhanh, khong update ca
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction tran = session.beginTransaction();
            NhanVien nhanVien = session.get(NhanVien.class, id);
            nhanVien.setMa(nv.getMa());
            nhanVien.setAvatar(nv.getAvatar());
            nhanVien.setHoTen(nv.getHoTen());
            nhanVien.setGioiTinh(nv.getGioiTinh());
            nhanVien.setSdt(nv.getSdt());
            nhanVien.setLuong(nv.getLuong());
            nhanVien.setThanhPho(nv.getThanhPho());
            nhanVien.setQuocGia(nv.getQuocGia());
//            nhanVien.setChiNhanh(nv.getChiNhanh());
            nhanVien.setChucVu(nv.getChucVu());
//            nhanVien.setChiTietCa(nv.getChiTietCa());
            nhanVien.setTrangThai(nv.getTrangThai());
            session.update(nhanVien);
            tran.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        }
    }

//    public boolean updateChiNhanhNhanVien(String id, ChiNhanh cn, boolean check){ // chi sua chi nhanh, chi co admin
//        try ( Session session = Hibernateutility.getFactory().openSession()) {
//            Transaction tran = session.beginTransaction();
//            NhanVien nhanVien = session.get(NhanVien.class, id);
//            nhanVien.setChiNhanh(cn);
//            session.update(nhanVien);
//            tran.commit();
//            session.close();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace(System.err);
//            return false;
//        }
//    }
    public boolean updateNhanVienByAdmin(String id, NhanVien nv) { // co update chi nhanh, khong update ca
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction tran = session.beginTransaction();
            NhanVien nhanVien = session.get(NhanVien.class, id); // test trong main thi update dc, khi goi thi lai khong, khong suat ra loi gi ca
            nhanVien.setMa(nv.getMa());
            nhanVien.setAvatar(nv.getAvatar());
            nhanVien.setHoTen(nv.getHoTen());
            nhanVien.setGioiTinh(nv.getGioiTinh());
            nhanVien.setSdt(nv.getSdt());
            nhanVien.setLuong(nv.getLuong());
            nhanVien.setThanhPho(nv.getThanhPho());
            nhanVien.setQuocGia(nv.getQuocGia());
            nhanVien.setChucVu(nv.getChucVu());
            nhanVien.setChiNhanh(nv.getChiNhanh());
//            nhanVien.setChiTietCa(nv.getChiTietCa());
            nhanVien.setTrangThai(nv.getTrangThai());
            session.update(nhanVien);
            tran.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        }
    }

    // viet ham update moi
    public boolean updateNVByAdmin(String id, NhanVien nv, ChiNhanh cn) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction tran = session.beginTransaction();
            NhanVien nhanVien = session.get(NhanVien.class, id);
            nhanVien.setMa(nv.getMa());
            nhanVien.setAvatar(nv.getAvatar());
            nhanVien.setHoTen(nv.getHoTen());
            nhanVien.setGioiTinh(nv.getGioiTinh());
            nhanVien.setSdt(nv.getSdt());
            nhanVien.setLuong(nv.getLuong());
            nhanVien.setThanhPho(nv.getThanhPho());
            nhanVien.setQuocGia(nv.getQuocGia());
            nhanVien.setChucVu(nv.getChucVu());
            nhanVien.setChiNhanh(cn);
//            nhanVien.setChiTietCa(nv.getChiTietCa());
            nhanVien.setTrangThai(nv.getTrangThai());
            session.update(nhanVien);
            tran.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        }
    }

    public boolean deleteNhanVien(String id) { // chuyen trang thai
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction tran = session.beginTransaction();
            NhanVien nhanVien = session.get(NhanVien.class, id);
            nhanVien.setTrangThai(0);
            session.update(nhanVien);
            tran.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        }
    }

    public boolean deleteNV(String id) { // Xoa han luon, xoa het cac khoa phu tai cac bang co lien ket voi nhan vien
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction tran = session.beginTransaction();
            NhanVien nhanVien = session.get(NhanVien.class, id);
            nhanVien.setChiNhanh(null);
//            khachHang.setListChiNhanh(null);
            session.delete(nhanVien);
            tran.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        }
    }

    /// Repo Chuc Vu
    public List<ChucVu> getAllChucVu() {
        List<ChucVu> chucVus = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM ChucVu");
            chucVus = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return chucVus;
    }

    public ChucVu getChucVuById(String id) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            ChucVu cv = session.get(ChucVu.class, id);
            session.close();
            return cv;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

}
