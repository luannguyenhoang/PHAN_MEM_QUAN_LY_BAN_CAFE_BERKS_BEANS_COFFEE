/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import domainmodel.ChiTietHoaDon;
import domainmodel.HoaDonBanHang;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utility.Hibernateutility;

/**
 *
 * @author ASUS
 */
public class HoaDonChiTietRepo {

    public List<ChiTietHoaDon> getAllChiTietHoaDon() {
        List<ChiTietHoaDon> lstChiTietHD = null;
        try( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            lstChiTietHD = session.createQuery("From ChiTietHoaDon").list();
            trans.commit();
            session.close();
        }
        return lstChiTietHD;
    }

    public Set<ChiTietHoaDon> getHoaDonChiTietByHoaDon(HoaDonBanHang hd) {
        Set<ChiTietHoaDon> setHDCT;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            HoaDonBanHang hdbh = session.get(HoaDonBanHang.class, hd.getId());
            setHDCT = hdbh.getChiTietHoaDon();
            trans.commit();
            session.close();
        }
        return setHDCT;
    }
}
