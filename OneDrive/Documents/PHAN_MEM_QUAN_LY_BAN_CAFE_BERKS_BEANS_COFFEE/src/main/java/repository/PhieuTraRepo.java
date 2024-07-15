/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import domainmodel.ChiNhanh;
import domainmodel.ChiTietPhieuTra;
import domainmodel.NguyenLieu;
import domainmodel.NhaCungCap;
import domainmodel.NhanVien;
import domainmodel.PhieuNhapHang;
import domainmodel.PhieuTraHang;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.*;
import utility.Hibernateutility;

/**
 *
 * @author ASUS
 */
public class PhieuTraRepo {

    public Set<PhieuTraHang> getAllPhieuTraByChiNhanh(String idChiNhanh) {
        Set<PhieuTraHang> setPhieuTra = new HashSet<>();
        Set<NguyenLieu> setNguyenLieu = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            ChiNhanh cn = session.get(ChiNhanh.class, idChiNhanh);
            setNguyenLieu = cn.getListNguyenLieu();
            for (NguyenLieu x : setNguyenLieu) {
                Set<ChiTietPhieuTra> setChiTiet = x.getChiTietPhieuTra();
                for (ChiTietPhieuTra y : setChiTiet) {
                    setPhieuTra.add(y.getPhieuTraKey());
                }
            }
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return setPhieuTra;
    }

    public List<ChiTietPhieuTra> getAllChiTietPhieuTra() {
        List<ChiTietPhieuTra> lstChiTietPhieuTra = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            lstChiTietPhieuTra = session.createQuery("from ChiTietPhieuTra").list();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lstChiTietPhieuTra;
    }

    public NhaCungCap getNhaCungCapByPhieuTra(String idPhieuTra) {
        NhaCungCap ncc = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            PhieuTraHang pn = session.get(PhieuTraHang.class, idPhieuTra);
            ncc = pn.getNhaCungCap();
            session.close();
        }
        return ncc;
    }

    public Set<ChiTietPhieuTra> getPhieuTraByChiTietPhieuTra(String idPhieuTra) {
        Set<ChiTietPhieuTra> setCtpt = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            PhieuTraHang pt = session.get(PhieuTraHang.class, idPhieuTra);
            setCtpt = pt.getChiTietPhieuTra();
            session.close();
        }
        return setCtpt;
    }

    public PhieuTraHang getPhieuTraByMa(String maPT) {
        PhieuTraHang phieuTraHang = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            Query query = session.createQuery("From PhieuTraHang Where Ma=:Ma");
            query.setParameter("Ma", maPT);
            phieuTraHang = (PhieuTraHang) query.uniqueResult();
            trans.commit();
            session.close();
        }
        return phieuTraHang;
    }

    public PhieuTraHang getPhieuTraByID(String id) {
        PhieuTraHang phieuTraHang = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            Query query = session.createQuery("From PhieuTraHang Where Id=:Id");
            query.setParameter("Id", id);
            phieuTraHang = (PhieuTraHang) query.uniqueResult();
            trans.commit();
            session.close();
        }
        return phieuTraHang;
    }

    public String updateTrangThaiPhieuTra(String maPT, Integer trangThai) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            PhieuTraHang phieuTraHang = getPhieuTraByMa(maPT);
            phieuTraHang.setTrangThai(trangThai);
            session.update(phieuTraHang);
            trans.commit();
            session.close();
            return "Thành công";
        } catch (Exception e) {
            return "Thất bại";
        }
    }

    public String insertPhieuTra(String maPT, String idNcc, String idNv, Date ngayTra, int trangThai) {
        String id = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            NhaCungCap ncc = session.get(NhaCungCap.class, idNcc);
            NhanVien nv = session.get(NhanVien.class, idNv);
            PhieuTraHang pth = new PhieuTraHang();
            pth.setMa(maPT);
            pth.setNhaCungCap(ncc);
            pth.setNhanVien(nv);
            pth.setNgayTra(ngayTra);
            pth.setTrangThai(trangThai);
            id = (String) session.save(pth);
            trans.commit();
            session.close();
        }
        return id;
    }

    public String updateSoluongNguyenLieuTra(String idNguyenLieu, float soLuongTra) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            NguyenLieu nl = session.get(NguyenLieu.class, idNguyenLieu);
            float soLuongNL = nl.getSoLuongTon() - soLuongTra;
            nl.setSoLuongTon(soLuongNL);
            session.update(nl);
            trans.commit();
            session.close();
        }
        return "Thành công";
    }

    public void insertCTPhieuTra(String idPt, String idNL, float soLuongTra, String lyDo) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            PhieuTraHang pth = session.get(PhieuTraHang.class, idPt);
            NguyenLieu nl = session.get(NguyenLieu.class, idNL);
            ChiTietPhieuTra ctpt = new ChiTietPhieuTra();
            ctpt.setPhieuTraKey(pth);
            ctpt.setNguyenLieuKey(nl);
            ctpt.setSoLuongTra(soLuongTra);
            ctpt.setLiDo(lyDo);
            session.save(ctpt);
            trans.commit();
            session.close();
        }
    }

    public void updatePhieuTra(String idPT, String maPT, String idNCC, String idNV, Date ngayTra) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            NhaCungCap ncc = session.get(NhaCungCap.class, idNCC);
            NhanVien nv = session.get(NhanVien.class, idNV);
            PhieuTraHang phieuTra = session.get(PhieuTraHang.class, idPT);
            phieuTra.setMa(maPT);
            phieuTra.setNhaCungCap(ncc);
            phieuTra.setNhanVien(nv);
            phieuTra.setNgayTra(ngayTra);
            session.update(phieuTra);
            trans.commit();
            session.close();
        }
    }

    public void updateCTPhieuTra(String idPt, String idNl, float soLuongTra, String lyDo) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            PhieuTraHang pt = session.get(PhieuTraHang.class, idPt);
            NguyenLieu nl = session.get(NguyenLieu.class, idNl);
            ChiTietPhieuTra ctpt = new ChiTietPhieuTra();
            ctpt.setPhieuTraKey(pt);
            ctpt.setNguyenLieuKey(nl);
            ctpt.setSoLuongTra(soLuongTra);
            ctpt.setLiDo(lyDo);
            session.update(ctpt);
            trans.commit();
            session.close();
        }
    }

    public void deleteChiTietPnbyidPT(String idPT) {
        Set<ChiTietPhieuTra> setChiTiet = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            PhieuTraHang pnh = session.get(PhieuTraHang.class, idPT);
            setChiTiet = pnh.getChiTietPhieuTra();
            setChiTiet.clear();
            trans.commit();
            session.close();
        }
    }

    public Set<ChiTietPhieuTra> searchPhieuTra(String maPN) {
        Transaction trans = null;
        Set<ChiTietPhieuTra> listChiTiet = new HashSet<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            trans = session.beginTransaction();
            Query query = session.createQuery("FROM PhieuTraHang WHERE Ma like :Ma");
            query.setParameter("Ma", "%" + maPN + "%");
            List<PhieuTraHang> listSearch = query.list();
            for (PhieuTraHang x : listSearch) {
                for (ChiTietPhieuTra ctpt : x.getChiTietPhieuTra()) {
                    listChiTiet.add(ctpt);
                }
            }
            trans.commit();
            session.close();
        } catch (Exception ex) {
            System.out.println("Lỗi ko thể tìm kiếm");
        }
        return listChiTiet;
    }
}
