/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.implement;

import domainmodel.TaiKhoanNguoiDung;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import service.QuenMatKhauService;
import utility.Hibernateutility;

/**
 *
 * @author hoang
 */
public class QuenMatKhauSevice implements QuenMatKhauService<TaiKhoanNguoiDung, String> {

    private SessionFactory ssf;

    public QuenMatKhauSevice() {
        ssf = Hibernateutility.getFactory();
    }

    @Override
    public void update(TaiKhoanNguoiDung e) {
        Session ss = ssf.openSession();
        Transaction ts = ss.getTransaction();
        ts.begin();
        ss.merge(e);
        ts.commit();
        ss.close();
    }

    @Override
    public TaiKhoanNguoiDung selectById(String maNV) {
        Query q;
        try (Session ss = ssf.openSession()) {
            q = ss.createQuery("Select tk From TaiKhoanNguoiDung tk where tk.tenTK = :tenTK", TaiKhoanNguoiDung.class);
            q.setParameter("tenTK", maNV);

            List<TaiKhoanNguoiDung> results = q.getResultList();
            if (results.isEmpty()) {
                return null; // Or throw a custom exception, or handle it in a different way
            } else {
                return results.get(0);
            }
        }
    }
}
