/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import domainmodel.ChiTietPhieuKiemKe;
import domainmodel.NguyenLieu;
import domainmodel.NhanVien;
import domainmodel.PhieuKiemKe;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utility.Hibernateutility;

/**
 *
 * @author PC
 */
public class PhieuKiemKeRepo {

    
    

    public List<PhieuKiemKe> getAllPKK() {
        List<PhieuKiemKe> list = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            list = session.createQuery("FROM PhieuKiemKe").list();
            trans.commit();
            session.close();
        }
        return list;
    }
    
    
     public List<PhieuKiemKe> getAllPKKcbb() {
        List<PhieuKiemKe> list = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            list = session.createQuery("FROM PhieuKiemKe where TrangThai =1").list();
            trans.commit();
            session.close();
        }
        return list;
    }
    
    
     public List<NhanVien> getAllNV() {
        List<NhanVien> list = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            list = session.createQuery("FROM NhanVien where TrangThai =1").list();
            trans.commit();
            session.close();
        }
        return list;
    }
public NhanVien getNhanVienFromID(String idNhanVien) {
        NhanVien Nv = null;
        try (Session session = Hibernateutility.getFactory().openSession()) {
            Nv = session.get(NhanVien.class, idNhanVien);
            session.close();
        }
        return Nv;
    }

//    
//    
//    public static void main(String[] args) {
//        PhieuKiemKe pk = new PhieuKiemKe();
//        pk.setId("458B9550-3DA9-4606-89D9-01C0140204CE");
//        
//        NguyenLieu nl = new NguyenLieu();
//        nl.setId("96B97E69-EFC1-483F-B911-75FDFBAD7EB9");
//        
//        insertCTPhieukk("458B9550-3DA9-4606-89D9-01C0140204CE", "96B97E69-EFC1-483F-B911-75FDFBAD7EB9", 12, 12, 12, "sá");
//    }
//    

    public String insertPKK(String ma, Date ngayKiemKe, int trangThai, NhanVien nhanVien) {
        String id = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            PhieuKiemKe pkk = new PhieuKiemKe();
            
            pkk.setMa(ma);
            pkk.setNgayKiemKe(ngayKiemKe);
            pkk.setTrangThai(trangThai);
pkk.setNhanVien(nhanVien);
            id = (String) session.save(pkk);
            trans.commit();
            session.close();
        }
        return id;

    }
    
    
    
    
    
    public static void deletePKK(String idPKK){
        try (Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
//            Ban ban = session.get(Ban.class, idBan);
PhieuKiemKe pkk = session.get(PhieuKiemKe.class, idPKK);
pkk.setId(idPKK);
        
            session.delete(pkk);
            trans.commit();
            session.close();
        }
    }
//    public void deleteBan(String idBan) {
//        try (Session session = Hibernateutility.getFactory().openSession()) {
//            Transaction trans = session.beginTransaction();
//            Ban ban = session.get(Ban.class, idBan);
//            ban.setTrangThaiSuDung(0);
//            session.update(ban);
//            trans.commit();
//            session.close();
//        }
//    }
    public PhieuKiemKe getPKKFromId(String idPKK) {
        PhieuKiemKe pk = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            pk = session.get(PhieuKiemKe.class, idPKK);
            session.close();
        }
        return pk;
    }
    
    
     public void update(PhieuKiemKe cn, String ma, int trangThai, Date date) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            cn.setMa(ma);
            cn.setTrangThai(trangThai);
            cn.setNgayKiemKe(date);
           
            session.update(cn);
            trans.commit();
            session.close();
        }
    }
    
    
    
    
    
    
    
       public PhieuKiemKe getChiNhanh(String ma) {
        PhieuKiemKe chinhanh = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            Query query = session.createQuery("FROM PhieuKiemKe Where ma=:ma");
            query.setParameter("ma", ma);
            List<PhieuKiemKe> list = query.getResultList();
            if (list.size() > 0) {
                chinhanh = list.get(0);
            }
            trans.commit();
            session.close();
        }
        return chinhanh;
    }
    
    
    
    
    public static void main(String[] args) {
        deletePKK("ss");
    }
}
