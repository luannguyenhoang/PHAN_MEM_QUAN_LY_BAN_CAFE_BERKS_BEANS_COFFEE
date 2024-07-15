/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import domainmodel.ChiNhanh;
import domainmodel.NguyenLieu;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import utility.Hibernateutility;

/**
 *
 * @author PC
 */
public class NguyenLieuRepo {
    
    
//      public Set<ChiTietPhieuNhap> searchPhieuNhap(String maPN) {
//        Transaction trans = null;
//        Set<ChiTietPhieuNhap> listChiTiet = new HashSet<>();
//        try ( Session session = Hibernateutility.getFactory().openSession()) {
//            trans = session.beginTransaction();
//            Query query = session.createQuery("FROM PhieuNhapHang WHERE Ma like :Ma");
//            query.setParameter("Ma", "%" + maPN + "%");
//            List<PhieuNhapHang> listSearch = query.list();
//            for (PhieuNhapHang x : listSearch) {
//                for (ChiTietPhieuNhap ctpn : x.getChiTietPhieuNhap()) {
//                    listChiTiet.add(ctpn);
//                }
//            }
//            trans.commit();
//            session.close();
//        } catch (Exception ex) {
//            System.out.println("Lỗi ko thể tìm kiếm");
//        }
//        return listChiTiet;
//    }

    
    

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
     
     
      public Set<NguyenLieu> getAllNguyenLieuByChiNhanh1(String MA) {
        Set<NguyenLieu> setNguyenLieu = new HashSet<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
//            ChiNhanh chiNhanh = session.get(ChiNhanh.class, );
NguyenLieu nl = session.get(NguyenLieu.class, MA);
//            Set<NguyenLieu> allNguyenLieu = chiNhanh.getListNguyenLieu();
//            for (NguyenLieu nguyenLieu : allNguyenLieu) {
//                setNguyenLieu.add(nguyenLieu);
//            }
            session.close();
        }
        return setNguyenLieu;
    }
    
         public NguyenLieu getChiNhanh(String ma) {
        NguyenLieu chinhanh = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            Query query = session.createQuery("FROM NguyenLieu Where ma=:ma");
            query.setParameter("ma", ma);
            List<NguyenLieu> list = query.getResultList();
            if (list.size() > 0) {
                chinhanh = list.get(0);
            }
            trans.commit();
            session.close();
        }
        return chinhanh;
    }
    
       public String getChiTietSpByIdSanPham(String id) {
        String setChiTiet = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            ChiNhanh sp = session.get(ChiNhanh.class, id);
            setChiTiet = sp.getId();
            session.close();
        }
        return setChiTiet;
    }
    
    
    public List<NguyenLieu> getAll() {
        Session session = Hibernateutility.getFactory().openSession();
        Query q = session.createQuery("From NguyenLieu");
        List<NguyenLieu> lst = q.getResultList();
        return lst;

    }
    
    
      public List<NguyenLieu> getAllNL(String ma) {
        Session session = Hibernateutility.getFactory().openSession();
        Query q = session.createQuery("From NguyenLieu Where ma = ma");
        List<NguyenLieu> lst = q.getResultList();
        return lst;

    }
    
    
    

 
    public static void deleteMauSac(String idChiNhanh, NguyenLieu nguyenLieu) {
        Session session = Hibernateutility.getFactory().openSession();
        Transaction trans = session.beginTransaction();
        ChiNhanh cn = session.get(ChiNhanh.class, idChiNhanh);
        cn.getListNguyenLieu().add(nguyenLieu);
        session.delete(cn);
        trans.commit();
        session.close();

    }

    public static void deleteMauSac1(String maMau) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
//            MauSac mauSac = session.get(MauSac.class, getMauSacFromMa(maMau).getId());
            ChiNhanh cn = session.get(ChiNhanh.class,maMau);
//NguyenLieu nl = session.get(NguyenLieu.class, getMauSacFromMa(maMau).getId());
            for (NguyenLieu chiTiet : cn.getListNguyenLieu()) {
//                chiTiet.setMauSac(null);
                session.update(chiTiet);
            }
            session.remove(cn);
            trans.commit();
            session.close();
        }
    }

    public NguyenLieu getMauSacFromMa(String maMau) {
        NguyenLieu mauSac = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            org.hibernate.query.Query query = session.createQuery("FROM NguyenLieu Where ma=:ma");
            query.setParameter("ma", maMau);
            List<NguyenLieu> list = query.getResultList();
            if (list.size() > 0) {
                mauSac = list.get(0);
            }
            trans.commit();
            session.close();
        }
        return mauSac;

    }

//    public static void deleteMauSac(String maMau) {
//        try ( Session session = Hibernateutility.getFactory().openSession()) {
//            Transaction trans = session.beginTransaction();
//            NguyenLieu mauSac = session.get(NguyenLieu.class, getNguyeLieu(maMau).getId());
//            session.delete(mauSac);
//            trans.commit();
//           
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//      
//    }
    public static void deleteNguyenLieu(String idChiNhanh, NguyenLieu NguyenLieu) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
           Transaction trans = session.beginTransaction();
//            MauSac mauSac = session.get(MauSac.class, getMauSacFromMa(maMau).getId());
            ChiNhanh cn = session.get(ChiNhanh.class, idChiNhanh);
//NguyenLieu nl = session.get(NguyenLieu.class, getMauSacFromMa(maMau).getId());
            for (NguyenLieu chiTiet : cn.getListNguyenLieu()) {
//                chiTiet.setMauSac(null);
                session.update(chiTiet);
            }
            session.remove(cn);
            trans.commit();
            session.close();
        }
    }

    public static void insertNguyenLieuToChiNhanh(ChiNhanh idChiNhanh, NguyenLieu nguyenLieu) {
        Session session = Hibernateutility.getFactory().openSession();
        Transaction trans = session.beginTransaction();
        ChiNhanh cn = session.get(ChiNhanh.class, idChiNhanh);
        cn.getListNguyenLieu().add(nguyenLieu);
        nguyenLieu.getMa();
        nguyenLieu.getTen();
        nguyenLieu.getSoLuongTon();
        nguyenLieu.getHanSuDung();
        nguyenLieu.getDonViTinh();
//        cn.get
        session.save(cn);
        trans.commit();
        session.close();

    }
//    entityManager.remove(group)
//for (User user : group.users) {
//     user.groups.remove(group);
//}
    
     public ChiNhanh getKhuVucFromID(String idKhuVuc) {
        ChiNhanh kv = null;
        try (Session session = Hibernateutility.getFactory().openSession()) {
            kv = session.get(ChiNhanh.class, idKhuVuc);
            session.close();
        }
        return kv;
    }

    public String insertNguyenLieu(String ma, String ten, Date hsd, String dvt, Float slt, String idChiNhanh) {
        String id = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            NguyenLieu nguyenLieu = new NguyenLieu();
            ChiNhanh cn = session.get(ChiNhanh.class, idChiNhanh);
            cn.getListNguyenLieu().add(nguyenLieu);
            nguyenLieu.setMa(ma);
            nguyenLieu.setTen(ten);
            nguyenLieu.setHanSuDung(hsd);
            nguyenLieu.setDonViTinh(dvt);
            nguyenLieu.setSoLuongTon(slt);
            id = (String) session.save(nguyenLieu);

            cn.getListNguyenLieu().add(nguyenLieu);

            trans.commit();
            session.close();
        }

        return id;

    }

    public NguyenLieu getNguyeLieu(String ma) {
        NguyenLieu mauSac = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            Query query = session.createQuery("FROM NguyenLieu Where ma=:ma");
            query.setParameter("ma", ma);
            List<NguyenLieu> list = query.getResultList();
            if (list.size() > 0) {
                mauSac = list.get(0);
            }
            trans.commit();
            session.close();
        }
        return mauSac;
    }

    public void update(String id, String ma, String ten, String donViTinh, float soLuongTon, Date ngay) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            NguyenLieu nguyenlieu = session.get(NguyenLieu.class, id);
            nguyenlieu.setMa(ma);
            nguyenlieu.setTen(ten);
            nguyenlieu.setDonViTinh(donViTinh);
            nguyenlieu.setSoLuongTon(soLuongTon);
            nguyenlieu.setHanSuDung(ngay);
            session.update(nguyenlieu);
            trans.commit();
            session.close();
        }
    }

    public static void main(String[] args) {
//        ChiNhanh cn = new ChiNhanh();
//        cn.setId("653425A5-3EEA-424E-B029-3B1710F61609");
//        
        NguyenLieu nl = new NguyenLieu();
        nl.setId("A88F658C-AF47-4932-9AC9-0BC370115123");
//        nl.setMa("NL1");

////
////        insertNguyenLieuToChiNhanh("C2056753-E567-4856-BA2D-42993CEE88D4", nl,"sad","ádasd",null,"ádasd",null);
//        insertNguyenLieu("sá", "âsas", null, "kj", null, "C2056753-E567-4856-BA2D-42993CEE88D4");
//deleteMauSac("", nl);
//deleteNguyenLieu("A88F658C-AF47-4932-9AC9-0BC370115123",nl);
//        deleteMauSac("A88F658C-AF47-4932-9AC9-0BC370115123", nl);
//deleteNguyenLieu11("01EADEA8-BC38-4B79-8A37-7D032C64C644");
//insertNguyenLieuToChiNhanh("01EADEA8-BC38-4B79-8A37-7D032C64C644", nl);
deleteMauSac1("01EADEA8-BC38-4B79-8A37-7D032C64C644");
    }
}
