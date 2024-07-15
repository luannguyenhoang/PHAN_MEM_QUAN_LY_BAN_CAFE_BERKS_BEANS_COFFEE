/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import domainmodel.ChiTietPhieuKiemKe;
import domainmodel.NguyenLieu;
import domainmodel.PhieuKiemKe;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utility.Hibernateutility;

/**
 *
 * @author PC
 */
public class ChiTietPhieuKiemKeRepo {

    public Set<ChiTietPhieuKiemKe> getCYPKKbyPKK(String idPhieu) {
        Set<ChiTietPhieuKiemKe> setCT = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            PhieuKiemKe pkk = session.get(PhieuKiemKe.class, idPhieu);
//            setBan = kv.getListBan();
            setCT = pkk.getChiTietphieuKiem();
            trans.commit();
            session.close();
        }
        return setCT;
    }

    public Set<ChiTietPhieuKiemKe> getPhieuNhapByChiTietPhieuNhap(String id) {
        Set<ChiTietPhieuKiemKe> setCTPN = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            PhieuKiemKe pn = session.get(PhieuKiemKe.class, id);
            setCTPN = pn.getChiTietphieuKiem();
            session.close();
        }
        return setCTPN;
    }

    public List<ChiTietPhieuKiemKe> getAllChiTietHoaDon() {
        List<ChiTietPhieuKiemKe> lstChiTietPKK = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            lstChiTietPKK = session.createQuery("From ChiTietPhieuKiemKe").list();
            trans.commit();
            session.close();
        }
        return lstChiTietPKK;
    }
    
    
      public void update(String id, Float soLuongThucTe, String liDo) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            ChiTietPhieuKiemKe nguyenlieu = session.get(ChiTietPhieuKiemKe.class, id);
//            nguyenlieu.setMa(ma);
//            nguyenlieu.setTen(ten);
//            nguyenlieu.setDonViTinh(donViTinh);
//            nguyenlieu.setSoLuongTon(soLuongTon);
//            nguyenlieu.setHanSuDung(ngay);
nguyenlieu.setSoLuongThucTe(soLuongThucTe);
nguyenlieu.setLiDo(liDo);
            session.update(nguyenlieu);
            trans.commit();
            session.close();
        }
    }
      
      
      public void update(ChiTietPhieuKiemKe cn,Float soLuongThucTe, String liDo) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
          cn.setSoLuongThucTe(soLuongThucTe);
          cn.setLiDo(liDo);
            session.update(cn);
            trans.commit();
            session.close();
        }
    }

// public String insertNguyenLieu(float SoLuong, float SoLuongThucTe, float SoLuongChenhLech, String liDo, String idnl, String idpk) {
//        String id = null;
//        try ( Session session = Hibernateutility.getFactory().openSession()) {
//            Transaction trans = session.beginTransaction();
//            NguyenLieu nguyenLieu = new NguyenLieu();
////            ChiNhanh cn = session.get(ChiNhanh.class, idChiNhanh);
//
//            cn.getListNguyenLieu().add(nguyenLieu);
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
    public static void insertNguyenLieu( float SoLuongThucTe, String liDo, String idnl, String idpk) {

     try( Session session = Hibernateutility.getFactory().openSession()){
        Transaction trans = session.beginTransaction();
        ChiTietPhieuKiemKe CTpk = new ChiTietPhieuKiemKe();
        PhieuKiemKe pk = session.get(PhieuKiemKe.class, idpk);
        NguyenLieu nl = session.get(NguyenLieu.class, idnl);
        pk.getChiTietphieuKiem().add(CTpk);
        
        CTpk.setKiemKeKey(pk);
        CTpk.setNguyenLieuKey(nl);
       
        CTpk.setSoLuongThucTe(SoLuongThucTe);
        CTpk.setLiDo(liDo);
       
//            CTpk.setKiemKeKey(pkk);
        session.save(CTpk);
        trans.commit();
        session.close();

//        return id;
    }
     
    
//     public void insertCTPhieuNhap(Float , String idNl, float soLuongNhap, float donGia) {
//        try ( Session session = Hibernateutility.getFactory().openSession()) {
//            Transaction trans = session.beginTransaction();
//            PhieuNhapHang pn = session.get(PhieuNhapHang.class, idPn);
//            NguyenLieu nl = session.get(NguyenLieu.class, idNl);
//            ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
//            ctpn.setPhieuNhapKey(pn);
//            ctpn.setNguyenLieuKey(nl);
//            ctpn.setSoLuongNhap(soLuongNhap);
//            ctpn.setDonGia(donGia);
//            session.save(ctpn);
//            trans.commit();
//            session.close();
//        }
//    }
}
    
    
    
    
//    public static void main(String[] args) {
//        PhieuKiemKe pk = new PhieuKiemKe();
////                pk.setId("458B9550-3DA9-4606-89D9-01C0140204CE");
//        ChiTietPhieuKiemKe ct = new ChiTietPhieuKiemKe();
////       ct.getKiemKeKey().setId(id);
//
//        insertNguyenLieu(0, 0, 0, "sss","A88F658C-AF47-4932-9AC9-0BC370115123","0B90A851-EFF2-4CB9-B3EC-48F147D219D1");
//    }

}
