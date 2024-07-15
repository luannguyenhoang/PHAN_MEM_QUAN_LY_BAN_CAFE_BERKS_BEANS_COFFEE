/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import domainmodel.Ban;
import domainmodel.HoaDonBanHang;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utility.*;

/**
 *
 * @author trant
 */
public class HoaDonRepo {

    public static List<HoaDonBanHang> getAllHoaDon() {
        List<HoaDonBanHang> list;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            list = session.createQuery("FROM HoaDonBanHang").list();
            session.close();
        }
        return list;
    }

    public HoaDonBanHang getHoaDonByMa(String maHD) {
        HoaDonBanHang hdbh = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            Query query = session.createQuery("From HoaDonBanHang Where Ma=:Ma");
            query.setParameter("Ma", maHD);
            hdbh = (HoaDonBanHang) query.uniqueResult();
            trans.commit();
            session.close();
        }
        return hdbh;
    }

    public String updateTrangThai(String maHD, Integer trangThai) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            HoaDonBanHang hdbh = getHoaDonByMa(maHD);
            hdbh.setTrangThai(trangThai);
            session.update(hdbh);
            trans.commit();
            session.close();
            return "Thanh Cong";
        } catch (Exception e) {
            return "That bai";
        }
    }

    public List<HoaDonBanHang> locHoaDon(Date startDate, Date endDate) {
        Transaction trans = null;
        List<HoaDonBanHang> listHoaDon = new ArrayList<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            trans = session.beginTransaction();
            Query query = session.createQuery("FROM HoaDonBanHang WHERE NgayTao BETWEEN :frmDate AND :toDate");
            query.setTimestamp("frmDate", startDate);
            query.setTimestamp("toDate", endDate);
            List<HoaDonBanHang> listSearch = query.list();
            for (HoaDonBanHang x : listSearch) {
                listHoaDon.add(x);
            }
            trans.commit();
            session.close();
        } catch (Exception ex) {
            System.out.println("Lỗi ko thể lọc");
        }
        return listHoaDon;
    }
//    public List<HoaDonBanHang> locHoaDon(String startDate,String endDate) {
//        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
//        String frmDate = format.format(startDate);
//        String toDate = format.format(endDate);
//    }

    public static void main(String[] args) {
        List<HoaDonBanHang> list = getAllHoaDon();
        for (HoaDonBanHang ban : list) {
            System.out.println(ban.getId());
        }
    }
}
