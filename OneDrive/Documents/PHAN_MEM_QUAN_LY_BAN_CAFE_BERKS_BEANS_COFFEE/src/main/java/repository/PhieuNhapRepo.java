/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import domainmodel.ChiNhanh;
import domainmodel.ChiTietPhieuNhap;
import domainmodel.HoaDonBanHang;
import domainmodel.NguyenLieu;
import domainmodel.NhaCungCap;
import domainmodel.NhanVien;
import domainmodel.PhieuNhapHang;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utility.Hibernateutility;

/**
 *
 * @author ASUS
 */
public class PhieuNhapRepo {

    public Set<PhieuNhapHang> getAllPhieuNhapByChiNhanh(String idChiNhanh) {
        Set<PhieuNhapHang> SetPhieuNhap = new HashSet<>();
        Set<NguyenLieu> setNguyenLieu = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            ChiNhanh cn = session.get(ChiNhanh.class, idChiNhanh);
            setNguyenLieu = cn.getListNguyenLieu();
            for (NguyenLieu x : setNguyenLieu) {
                Set<ChiTietPhieuNhap> setChiTiet = x.getChiTietPhieuNhap();
                for (ChiTietPhieuNhap chiTietPhieuNhap : setChiTiet) {
                    SetPhieuNhap.add(chiTietPhieuNhap.getPhieuNhapKey());
                }
            }
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return SetPhieuNhap;
    }

    public NhaCungCap getAllNhaCungCapByPhieuNhap(String idPhieuNhap) {
        NhaCungCap ncc = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            PhieuNhapHang pn = session.get(PhieuNhapHang.class, idPhieuNhap);
            ncc = pn.getNhaCungCap();
        }
        return ncc;
    }

    public List<ChiTietPhieuNhap> getAllChiTietPhieuNhap() {
        List<ChiTietPhieuNhap> lstPhieuNhapChiTiet = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            lstPhieuNhapChiTiet = session.createQuery("from ChiTietPhieuNhap").list();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lstPhieuNhapChiTiet;
    }

    public Set<ChiTietPhieuNhap> getPhieuNhapByChiTietPhieuNhap(String id) {
        Set<ChiTietPhieuNhap> setCTPN = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            PhieuNhapHang pn = session.get(PhieuNhapHang.class, id);
            setCTPN = pn.getChiTietPhieuNhap();
            session.close();
        }
        return setCTPN;
    }

    public List<NhaCungCap> getAllNhaCungCap() {
        List<NhaCungCap> lstNhaCungCap = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            lstNhaCungCap = session.createQuery("from NhaCungCap").list();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lstNhaCungCap;
    }

    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> lstNhanVien = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            lstNhanVien = session.createQuery("from NhanVien").list();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lstNhanVien;
    }

    public PhieuNhapHang getPhieuNhapByMa(String maPN) {
        PhieuNhapHang phieuNhapHang = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            Query query = session.createQuery("From PhieuNhapHang Where Ma=:Ma");
            query.setParameter("Ma", maPN);
            phieuNhapHang = (PhieuNhapHang) query.uniqueResult();
            trans.commit();
            session.close();
        }
        return phieuNhapHang;
    }

    public NhanVien getNhanVienByMa(String maNhanVien) {
        NhanVien nhanVien = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            Query query = session.createQuery("From NhanVien Where Ma=:Ma");
            query.setParameter("Ma", maNhanVien);
            nhanVien = (NhanVien) query.uniqueResult();
            trans.commit();
            session.close();
        }
        return nhanVien;
    }

    public NhaCungCap getNhaCungCapByMa(String maNhaCungCap) {
        NhaCungCap nhaCungCap = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            Query query = session.createQuery("From NhaCungCap Where Ma=:Ma");
            query.setParameter("Ma", maNhaCungCap);
            nhaCungCap = (NhaCungCap) query.uniqueResult();
            trans.commit();
            session.close();
        }
        return nhaCungCap;
    }

    public NguyenLieu getNguyenLieuByMa(String maNguyenLieu) {
        NguyenLieu nguyenLieu = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            Query query = session.createQuery("From NguyenLieu Where Ma=:Ma");
            query.setParameter("Ma", maNguyenLieu);
            nguyenLieu = (NguyenLieu) query.uniqueResult();
            trans.commit();
            session.close();
        }
        return nguyenLieu;
    }

    public PhieuNhapHang getPhieuNhapById(String idPhieuNhap) {
        PhieuNhapHang pnh = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            pnh = session.get(PhieuNhapHang.class, idPhieuNhap);
            trans.commit();
            session.close();
        }
        return pnh;
    }

    public NhanVien getNhanVienById(String idNhanVien) {
        NhanVien nv = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            nv = session.get(NhanVien.class, idNhanVien);
            trans.commit();
            session.close();
        }
        return nv;
    }

    public NhaCungCap getNhaCungCapById(String idNhaCungCap) {
        NhaCungCap ncc = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            ncc = session.get(NhaCungCap.class, idNhaCungCap);
            trans.commit();
            session.close();
        }
        return ncc;
    }

    public NguyenLieu getNguyenLieuById(String idNguyenLieu) {
        NguyenLieu nl = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            nl = session.get(NguyenLieu.class, idNguyenLieu);
            trans.commit();
            session.close();
        }
        return nl;
    }

    public String updateTrangThaiPhieuNhap(String maPN, Integer trangThai) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            PhieuNhapHang phieuNhapHang = getPhieuNhapByMa(maPN);
            phieuNhapHang.setTrangThai(trangThai);
            session.update(phieuNhapHang);
            trans.commit();
            session.close();
            return "Thanh Cong";
        } catch (Exception e) {
            return "That bai";
        }
    }

    public String insertPhieuNhap(String maPN, String idNcc, String idNv, Date ngayNhap, int trangThai) {
        String id = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            NhaCungCap ncc = session.get(NhaCungCap.class, idNcc);
            NhanVien nv = session.get(NhanVien.class, idNv);
            PhieuNhapHang pnh = new PhieuNhapHang();
            pnh.setMa(maPN);
            pnh.setNhaCungCap(ncc);
            pnh.setNhanVien(nv);
            pnh.setNgayNhap(ngayNhap);
            pnh.setTrangThai(trangThai);
            id = (String) session.save(pnh);
            trans.commit();
            session.close();
        }
        return id;
    }

    public void updatePhieuNhap(String idPN, String maPN, String idNCC, String idNV, Date ngayNhap) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            NhaCungCap ncc = session.get(NhaCungCap.class, idNCC);
            NhanVien nv = session.get(NhanVien.class, idNV);
            PhieuNhapHang phieuNhap = session.get(PhieuNhapHang.class, idPN);
            phieuNhap.setMa(maPN);
            phieuNhap.setNhaCungCap(ncc);
            phieuNhap.setNhanVien(nv);
            phieuNhap.setNgayNhap(ngayNhap);
            session.update(phieuNhap);
            trans.commit();
            session.close();
        }
    }

    public void updateCTPhieuNhap(String idPn, String idNl, float soLuongNhap, float donGia) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            PhieuNhapHang pn = session.get(PhieuNhapHang.class, idPn);
            NguyenLieu nl = session.get(NguyenLieu.class, idNl);
            ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
            ctpn.setPhieuNhapKey(pn);
            ctpn.setNguyenLieuKey(nl);
            ctpn.setSoLuongNhap(soLuongNhap);
            ctpn.setDonGia(donGia);
            session.update(ctpn);
            trans.commit();
            session.close();
        }
    }

    public void deleteChiTietPnbyidPn(String idPn) {
        Set<ChiTietPhieuNhap> setChiTiet = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            PhieuNhapHang pnh = session.get(PhieuNhapHang.class, idPn);
            setChiTiet = pnh.getChiTietPhieuNhap();
            setChiTiet.clear();
            trans.commit();
            session.close();
        }
    }

    public void insertCTPhieuNhap(String idPn, String idNl, float soLuongNhap, float donGia) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            PhieuNhapHang pn = session.get(PhieuNhapHang.class, idPn);
            NguyenLieu nl = session.get(NguyenLieu.class, idNl);
            ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
            ctpn.setPhieuNhapKey(pn);
            ctpn.setNguyenLieuKey(nl);
            ctpn.setSoLuongNhap(soLuongNhap);
            ctpn.setDonGia(donGia);
            session.save(ctpn);
            trans.commit();
            session.close();
        }
    }

    public void updateSoluongNguyenLieu(String idNguyenLieu, float soLuongNhap) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            NguyenLieu nl = session.get(NguyenLieu.class, idNguyenLieu);
            float soLuongNL = nl.getSoLuongTon() + soLuongNhap;
            nl.setSoLuongTon(soLuongNL);
            session.update(nl);
            trans.commit();
            session.close();
        }
    }

    public Set<NhanVien> getAllNhanVienByChiNhanh(String IdchiNhanh) {
        Set<NhanVien> setNhanVien = new HashSet<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            ChiNhanh cn = session.get(ChiNhanh.class, IdchiNhanh);
            Set<NhanVien> setAllNhanVien = cn.getSetNhanVien();
            for (NhanVien nhanVien : setAllNhanVien) {
                setNhanVien.add(nhanVien);
            }
            trans.commit();
            session.close();
        }
        return setNhanVien;
    }

    public Set<NguyenLieu> getAllNguyenLieuByChiNhanh(String IdchiNhanh) {
        Set<NguyenLieu> setNguyenLieu = new HashSet<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            ChiNhanh cn = session.get(ChiNhanh.class, IdchiNhanh);
            Set<NguyenLieu> setAllNguyenLieu = cn.getListNguyenLieu();
            for (NguyenLieu nguyenLieu : setAllNguyenLieu) {
                setNguyenLieu.add(nguyenLieu);
            }
            trans.commit();
            session.close();
        }
        return setNguyenLieu;
    }

    public Set<ChiTietPhieuNhap> searchPhieuNhap(String maPN) {
        Transaction trans = null;
        Set<ChiTietPhieuNhap> listChiTiet = new HashSet<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            trans = session.beginTransaction();
            Query query = session.createQuery("FROM PhieuNhapHang WHERE Ma like :Ma");
            query.setParameter("Ma", "%" + maPN + "%");
            List<PhieuNhapHang> listSearch = query.list();
            for (PhieuNhapHang x : listSearch) {
                for (ChiTietPhieuNhap ctpn : x.getChiTietPhieuNhap()) {
                    listChiTiet.add(ctpn);
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
