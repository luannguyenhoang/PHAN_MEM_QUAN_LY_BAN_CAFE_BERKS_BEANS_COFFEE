package repository;

import domainmodel.NhaCungCap;
import domainmodel.PhieuNhapHang;
import domainmodel.PhieuTraHang;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utility.Hibernateutility;

/**
 *
 * @author duong
 */
public class NhaCungCapRepo {

    public NhaCungCapRepo() {

    }
    // lay doi tuong de fill len controls
    public NhaCungCap getNCCById(String id) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            NhaCungCap ncc = session.get(NhaCungCap.class, id);
            session.close();
            return ncc;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return null;
    }
    // check nhan vien trung ma, neu trung thi list co 1 phan tu
    public int countNCCByMa(String maNCC) {
        List<NhaCungCap> nhaCungCaps = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM NhaCungCap WHERE ma like :ma");
            query.setParameter("ma", maNCC);
            nhaCungCaps = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return nhaCungCaps.size();
    }
    
    /// READ
    public List<NhaCungCap> getAllNhaCungcap() {
        List<NhaCungCap> nhaCungCaps = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM NhaCungCap");
            nhaCungCaps = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return nhaCungCaps;
    }

    // Search By Ma
    public List<NhaCungCap> getAllNCCByMa(String maNCC) {
        List<NhaCungCap> nhaCungCaps = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM NhaCungCap WHERE ma like :ma");
            query.setParameter("ma", "%" + maNCC + "%");
            nhaCungCaps = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return nhaCungCaps;
    }

    // Search By Name
    public List<NhaCungCap> getAllNCCByName(String tenNCC) {
        List<NhaCungCap> nhaCungCaps = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM NhaCungCap WHERE ten like :ten");
            query.setParameter("ten", "%" + tenNCC + "%");
            nhaCungCaps = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return nhaCungCaps;
    }

    // Filter By TrangThai
    public List<NhaCungCap> getAllNCCByTrangThai(int trangThai) {
        List<NhaCungCap> nhaCungCaps = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM NhaCungCap WHERE trangThai = :trangThai");
            query.setParameter("trangThai", trangThai);
            nhaCungCaps = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return nhaCungCaps;
    }

    // Search and filter by name or ma
    public List<NhaCungCap> getAllNCCByTrangThaiAndName(int trangThai, String tenNCC) {
        List<NhaCungCap> nhaCungCaps = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM NhaCungCap WHERE trangThai = :trangThai AND ten like :ten");
            query.setParameter("trangThai", trangThai);
            query.setParameter("ten", "%" + tenNCC + "%");
            nhaCungCaps = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return nhaCungCaps;
    }

    public List<NhaCungCap> getAllNCCByTrangThaiAndMa(int trangThai, String maNCC) {
        List<NhaCungCap> nhaCungCaps = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("FROM NhaCungCap WHERE trangThai = :trangThai AND ma like :ma");
            query.setParameter("trangThai", trangThai);
            query.setParameter("ma", "%" + maNCC + "%");
            nhaCungCaps = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return nhaCungCaps;
    }
    // thu ket hop cac dieu kien chung 1 ham
//    public List<NhaCungCap> getAllNCCByTrangThaiAndMaAndTen(Integer trangThai, String maNCC, String tenNCC) {
//        List<NhaCungCap> nhaCungCaps = new ArrayList<>();
//        try ( Session session = Hibernateutility.getFactory().openSession()) {
//            Query query = session.createQuery("FROM NhaCungCap WHERE trangThai like :trangThai AND ma like :ma AND ten like :ten");
//            query.setParameter("trangThai", trangThai); // neu param nao co gia tri mac dinh thi dieu kien do khong duoc dung, danh mac cho truyen trangThai thanh null thi no van k lay dc all, dang String thi ok -> de nghi sau
//            query.setParameter("ma", "%" + maNCC + "%");
//            query.setParameter("ten", "%" + tenNCC + "%");
//            nhaCungCaps = query.getResultList();
//            session.close();
//        } catch (Exception e) {
//            e.printStackTrace(System.err);
//        }
//        return nhaCungCaps;
//    }

    /// CUD
    public boolean addNhaCungCap(NhaCungCap ncc) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction tran = session.beginTransaction();
            session.save(ncc);
            tran.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        }
    }

    public boolean updateNhaCungCap(String id, NhaCungCap ncc) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction tran = session.beginTransaction();
//            NhaCungCap nhaCungCap = session.get(NhaCungCap.class, ncc.getId());
            NhaCungCap nhaCungCap = session.get(NhaCungCap.class, id);
            nhaCungCap.setMa(ncc.getMa());
            nhaCungCap.setTen(ncc.getTen());
            nhaCungCap.setTrangThai(ncc.getTrangThai());
            session.update(nhaCungCap);
            tran.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        }
    }

    // chuyen trang thai thanh 0 chu k xoa
    public boolean deleteNhaCungCap(String id) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction tran = session.beginTransaction();
            NhaCungCap nhaCungCap = session.get(NhaCungCap.class, id);
            nhaCungCap.setTrangThai(0);
            session.update(nhaCungCap);
            tran.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        }
    }

    // neu xoa han nha cung cap di thi set khoa phu lien quan null
    public boolean deleteNCC(String id) {
        List<PhieuNhapHang> phieuNhapHangs = getAllPNHByNCC(id);
        List<PhieuTraHang> phieuTraHangs = getAllPTHByNCC(id);
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction tran = session.beginTransaction();
            NhaCungCap nhaCungCap = session.get(NhaCungCap.class, id);
            
            if (!phieuNhapHangs.isEmpty()) {
                for (PhieuNhapHang pnh : phieuNhapHangs) {
                    pnh.setNhaCungCap(null);
                    session.update(pnh);
                }
            }

            if (!phieuNhapHangs.isEmpty()) {
                for (PhieuTraHang pth : phieuTraHangs) {
                    pth.setNhaCungCap(null);
                    session.update(pth);
                }
            }
            session.delete(nhaCungCap);
            tran.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        }
    }

    //// Phieu Nhap Hang Repo
    public List<PhieuNhapHang> getAllPNHByNCC(String idNCC) {
        List<PhieuNhapHang> phieuNhapHangs = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT PNH FROM PhieuNhapHang PNH WHERE PNH.nhaCungCap.id = :id");
            query.setParameter("id", idNCC);
            phieuNhapHangs = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return phieuNhapHangs;
    }

    //// Phieu Tra Hang Repo
    public List<PhieuTraHang> getAllPTHByNCC(String idNCC) {
        List<PhieuTraHang> phieuTraHangs = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("SELECT PTH FROM PhieuTraHang PTH WHERE PTH.nhaCungCap.id = :id");
            query.setParameter("id", idNCC);
            phieuTraHangs = query.getResultList();
            session.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return phieuTraHangs;
    }
    
    //test
//    public static void main(String[] args) {
//        String st = new String();
//        System.out.println(new NhaCungCapRepo().getAllNCCByTrangThaiAndMaAndTen(null, "", "Name").size());
//        System.out.println(st.equalsIgnoreCase(""));
//    }
    
    // show sql hibernate
//    <property name="show_sqp"> true </property>
}
