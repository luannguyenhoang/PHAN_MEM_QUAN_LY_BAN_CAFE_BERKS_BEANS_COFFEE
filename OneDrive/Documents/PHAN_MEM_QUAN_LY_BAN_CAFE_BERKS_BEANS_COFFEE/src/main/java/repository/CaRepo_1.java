/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import domainmodel.Ca;
import domainmodel.ChiNhanh;
import domainmodel.HoatDongCa;
import domainmodel.NhanVien;
import java.time.LocalDateTime;
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
public class CaRepo_1 {

    public List<Ca> getAllCaDangSuDung() {
        List<Ca> lst = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query q = session.createQuery("From Ca WHERE trangThai=1");
            lst = q.getResultList();
            session.close();
        }
        return lst;
    }

    public List<Ca> getAllCaDaXoa() {
        List<Ca> lst = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query q = session.createQuery("From Ca WHERE trangThai=0");
            lst = q.getResultList();
            session.close();
        }
        return lst;
    }

    public Ca getCabyMa(String maCa) {
        Ca ca = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query query = session.createQuery("From Ca WHERE ma=:maCa");
            query.setParameter("maCa", maCa);
            List<Ca> ListCa = query.getResultList();
            if (ListCa.size() > 0) {
                ca = ListCa.get(0);
            }
            session.close();
        }
        return ca;
    }

    public static Set<NhanVien> getNhanVienByChiNhanh(String idChiNhanh) {
        Set<NhanVien> setNhanVien = new HashSet<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            ChiNhanh chiNhanh = session.get(ChiNhanh.class, idChiNhanh);
            Set<NhanVien> listNhanVien = chiNhanh.getSetNhanVien();
            if (listNhanVien != null) {
                for (NhanVien nhanVien : listNhanVien) {
                    setNhanVien.add(nhanVien);
                }
            }
            session.close();
        }
        return setNhanVien;
    }

    public static Set<Ca> getCaOfNhanVien(String idNhanVien) {
        Set<Ca> caOfNhanVien = new HashSet<>();
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            NhanVien nhanVien = session.get(NhanVien.class, idNhanVien);
            Set<Ca> allCa = nhanVien.getSetCa();
            if (allCa != null) {
                for (Ca ca : allCa) {
                    caOfNhanVien.add(ca);
                }
            }
            session.close();
        }
        return caOfNhanVien;
    }

    public static void addCaToNhanVien(String idNhanVien, Set<String> setIdCa) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            NhanVien nhanVien = session.get(NhanVien.class, idNhanVien);
            nhanVien.getSetCa().clear();
            for (String idCa : setIdCa) {
                Ca ca = session.get(Ca.class, idCa);
                nhanVien.getSetCa().add(ca);
            }
            trans.commit();
            session.close();
        }
    }

    public String insertCa(Ca ca) {
        String id = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            id = (String) session.save(ca);
            trans.commit();
            session.close();
        }
        return id;
    }

    public static void updateCa(Ca ca) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            session.update(ca);
            trans.commit();
            session.close();
        }
    }

    public void changeState(String idCa) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            Ca ca = session.get(Ca.class, idCa);
            if (ca.getTrangThai() == 1) {
                ca.setTrangThai(0);
            } else {
                ca.setTrangThai(1);
            }
            session.update(ca);
            trans.commit();
            session.close();
        }
    }

    public String insertHoatDongCaOfChiNhanh(String idCa, String idChiNhanh, LocalDateTime gioMoCa, Float tienDauCa) {
        String id = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            HoatDongCa hoatDong = new HoatDongCa();
            Ca ca = session.get(Ca.class, idCa);
            ChiNhanh chiNhanh = session.get(ChiNhanh.class, idChiNhanh);
            hoatDong.setCa(ca);
            hoatDong.setChiNhanh(chiNhanh);
            hoatDong.setGioMoCa(gioMoCa);
            hoatDong.setTienDauCa(tienDauCa);
            id = (String) session.save(hoatDong);
            trans.commit();
            session.close();
        }
        return id;
    }

    public Ca getCaRunningOfChiNhanh(String idChiNhanh) {
        Ca ca = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query q = session.createQuery("From HoatDongCa WHERE idChiNhanh=" + "'" + idChiNhanh + "'");
            List<HoatDongCa> list = q.getResultList();
            for (HoatDongCa hoatDongCa : list) {
                if (hoatDongCa.getGioDongCa() == null) {
                    ca = hoatDongCa.getCa();
                    break;
                }
            }
            session.close();
        }
        return ca;
    }

    public HoatDongCa getHoatDongCaRunningOfChiNhanh(String idChiNhanh) {
        HoatDongCa hoatDongCa = null;
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Query q = session.createQuery("From HoatDongCa WHERE idChiNhanh=" + "'" + idChiNhanh + "'");
            List<HoatDongCa> list = q.getResultList();
            for (HoatDongCa hdCa : list) {
                if (hdCa.getGioDongCa() == null) {
                    hoatDongCa = hdCa;
                    break;
                }
            }
            session.close();
        }
        return hoatDongCa;
    }

    public void updateHoatDongRunning(String idHoatDong, Float tienCuoiCa, LocalDateTime gioDongCa) {
        try ( Session session = Hibernateutility.getFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            HoatDongCa activity = session.get(HoatDongCa.class, idHoatDong);
            activity.setTienCuoiCa(tienCuoiCa);
            activity.setGioDongCa(gioDongCa);
            session.update(activity);
            trans.commit();
            session.close();
        }

    }
}
