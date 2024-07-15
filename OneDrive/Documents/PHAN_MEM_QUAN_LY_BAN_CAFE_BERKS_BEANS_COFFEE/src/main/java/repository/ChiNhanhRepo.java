/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import domainmodel.Ban;
import domainmodel.ChiNhanh;
import domainmodel.KhuVuc;
import domainmodel.NhanVien;
import java.util.ArrayList;
import java.util.Date;
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
public class ChiNhanhRepo {

    
    
    public void deleteCN(String idCN) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            ChiNhanh chinhanh = session.get(ChiNhanh.class, idCN);
//            ban.setTrangThaiSuDung(0);
chinhanh.setTrangThai(0);
            session.update(chinhanh);
            trans.commit();
            session.close();
        }
    }
    
     public List<NhanVien> getAllNV() {
        Session session = Hibernateutility.getFactory().openSession();
        Query q = session.createQuery("From NhanVien");
        List<NhanVien> lst = q.getResultList();
        return lst;

    }
    
   
       
       
       
    public List<ChiNhanh> getAll() {
       List<ChiNhanh> list = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            list = session.createQuery("FROM ChiNhanh").list();
            trans.commit();
            session.close();
        }
        return list;

    }
    
    
      public List<ChiNhanh> getAllConHD() {
        Session session = Hibernateutility.getFactory().openSession();
        Query q = session.createQuery("From ChiNhanh where TrangThai =1");
        List<ChiNhanh> lst = q.getResultList();
        return lst;

    }
    
    
    
//     public String insertNguyenLieu(String ma, String ten, Date hsd, String dvt, Float slt, String idChiNhanh) {
//        String id = null;
//        try ( Session session = Hibernateutility.getFactory().openSession()) {
//            Transaction trans = session.beginTransaction();
//            NguyenLieu nguyenLieu = new NguyenLieu();
//          ChiNhanh  cn = session.get(ChiNhanh.class, idChiNhanh);
//        cn.getListNguyenLieu().add(nguyenLieu);
//            nguyenLieu.setMa(ma);
//            nguyenLieu.setTen(ten);
//            nguyenLieu.setHanSuDung(hsd);
//            nguyenLieu.setDonViTinh(dvt);
//            nguyenLieu.setSoLuongTon(slt);
//            id = (String) session.save(nguyenLieu);
//
//            cn.getListNguyenLieu().add(nguyenLieu);
//
//            trans.commit();
//            session.close();
//        }
//        
//        return id;
//      
//    }

    
//      public String insertBan(Integer SoBan, KhuVuc kv) {
//        String id = null;
//        try (Session session = Hibernateutility.getFactory().openSession()) {
//            Transaction trans = session.beginTransaction();
//            Ban ban = new Ban();
//            ban.setSoBan(SoBan);
//            ban.setTrangThaiSuDung(1);
//            ban.setTrangThai(0);
//            ban.setKhuVuc(kv);
//            id = (String) session.save(ban);
//            trans.commit();
//            session.close();
//        }
//        return id;
//
//    }
    
    public String insertChiNhanh(String ma, String quocGia, String thanhPho, Date ngayKhaiTruong, float giaTriDiem, float giaTriDoiDiem, int trangThai) {
        String id = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            ChiNhanh chinhanh = new ChiNhanh();
            
          
            chinhanh.setMa(ma);
            chinhanh.setQuocGia(quocGia);
            chinhanh.setThanhPho(thanhPho);
            chinhanh.setNgayKhaiTruong(ngayKhaiTruong);
            chinhanh.setTrangThai(trangThai);
            chinhanh.setGiaTriDiem(giaTriDiem);
            chinhanh.setGiaTriDoiDiem(giaTriDoiDiem);
            id = (String) session.save(chinhanh);
            trans.commit();
            session.close();
        }
        return id;
    }
//     

    public void update(ChiNhanh cn, String ma, String thanhpho, String quocgia, int trangThai, Date date) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            cn.setMa(ma);
            cn.setThanhPho(thanhpho);
            cn.setQuocGia(quocgia);
            cn.setTrangThai(trangThai);
            cn.setNgayKhaiTruong(date);
            session.update(cn);
            trans.commit();
            session.close();
        }
    }

    public Boolean deleteMauSac(String maMau) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            ChiNhanh mauSac = session.get(ChiNhanh.class, getChiNhanh(maMau).getId());
            session.delete(mauSac);
            trans.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public ChiNhanh getChiNhanh(String ma) {
        ChiNhanh chinhanh = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            Query query = session.createQuery("FROM ChiNhanh Where ma=:ma");
            query.setParameter("ma", ma);
            List<ChiNhanh> list = query.getResultList();
            if (list.size() > 0) {
                chinhanh = list.get(0);
            }
            trans.commit();
            session.close();
        }
        return chinhanh;
    }

    public List<ChiNhanh> findByMa(String id) {
      
        List<ChiNhanh> listChiNhanh = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();;
            trans = session.beginTransaction();
            Query query = session.createQuery("SELECT ChiNhanh FROM ChiNhanh WHERE id=:id");
            query.setParameter("id", "%" + id + "%");

            trans.commit();
            session.close();
        } catch (Exception ex) {
            System.out.println("Lỗi ko thể tìm kiếm");
        }
        return listChiNhanh;
    }
}
