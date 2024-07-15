/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import domainmodel.ChiNhanh;
import domainmodel.ChucVu;
import domainmodel.NhanVien;
import domainmodel.TaiKhoanAdmin;
import domainmodel.TaiKhoanNguoiDung;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utility.Hibernateutility;

/**
 *
 * @author ASUS
 */
public class Login_NguoiDungRepo {
     public TaiKhoanNguoiDung getNguoiDung(String tenTaiKhoan,String matKhau) {
      TaiKhoanNguoiDung taiKhoan  = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            Query query = session.createQuery("FROM TaiKhoanNguoiDung Where TenTK=:tenTaiKhoan and MatKhau=:matKhau");
            query.setParameter("tenTaiKhoan", tenTaiKhoan);
            query.setParameter("matKhau", matKhau);
            taiKhoan = (TaiKhoanNguoiDung) query.uniqueResult();
            trans.commit();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return taiKhoan;
    }
      public static TaiKhoanAdmin getAdmin(String tenTaiKhoan, String matKhau) {
        TaiKhoanAdmin taiKhoan = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            Query query = session.createQuery("FROM TaiKhoanAdmin Where tenTK=:tenTaiKhoan and matKhau=:matKhau");
            query.setParameter("tenTaiKhoan", tenTaiKhoan);
            query.setParameter("matKhau", matKhau);
            taiKhoan = (TaiKhoanAdmin) query.uniqueResult();
            trans.commit();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return taiKhoan;
    }

    public NhanVien getNhanVienbyTaiKhoan(String idTaiKhoan) {
        Session session = Hibernateutility.getFactory().openSession();
        NhanVien nhanVien = null;
        TaiKhoanNguoiDung taiKhoan = session.get(TaiKhoanNguoiDung.class, idTaiKhoan);
        nhanVien = taiKhoan.getNhanVien();
        session.close();
        return nhanVien;
    }

    public ChucVu getChucVubyIdNhanVien(String idNV) {
        Session session = Hibernateutility.getFactory().openSession();
        ChucVu chucVu = null;
        NhanVien nhanVien = session.get(NhanVien.class, idNV);
        chucVu = nhanVien.getChucVu();
        session.close();
        return chucVu;
    }

    public List<ChiNhanh> getAllChiNhanh() {
        List<ChiNhanh> list = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            list = session.createQuery("FROM ChiNhanh").list();
            session.close();
        }
        return list;
    }

    public ChiNhanh getChiNhanhByNhanVien(String idNV) {
        Session session = Hibernateutility.getFactory().openSession();
        ChiNhanh chiNhanh = null;
        NhanVien nhanVien = session.get(NhanVien.class, idNV);
        chiNhanh = nhanVien.getChiNhanh();
        session.close();
        return chiNhanh;
    }

    public static void main(String[] args) {
        TaiKhoanAdmin admin = getAdmin("admin", "123");
        if (admin != null) {
            System.out.println(admin.getTenTK() + admin.getMatKhau());
        }
    }
}
