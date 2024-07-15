/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.implement;


import domainmodel.NguyenLieu;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import repository.NguyenLieuRepo;

import service.INguyenLieu;
import viewmodel.ChiNhanhVM_Long;
import viewmodel.NguyenLieuDangSuDung;
import viewmodel.NguyenLieuViewModel_Long;

/**
 *
 * @author PC
 */
public class NguyenLieuService implements INguyenLieu {

    private NguyenLieuRepo nr = new NguyenLieuRepo();

    @Override
    public List<NguyenLieuViewModel_Long> getAll() {
        var allPKK = nr.getAll();
       
       List<NguyenLieuViewModel_Long> lstViewMD= new ArrayList<>();
       for(NguyenLieu ctpk : allPKK){
//           lstViewMD.add(new ChiTietPhieuKiemKeViewModel_Long("", "", ctpk.getSoLuong(), ctpk.getSoLuongChenhlech(), ctpk.getSoLuongThucTe(), ctpk.getLiDo()));
lstViewMD.add(new NguyenLieuViewModel_Long(ctpk.getId(), ctpk.getMa(), ctpk.getTen(), ctpk.getHanSuDung(), ctpk.getDonViTinh(), ctpk.getSoLuongTon()));
       }
       return lstViewMD;
    }

//    @Override
//    public String insertNguyenLieu(String ma, String ten, Date hsd, String dvt, Float slt) {
//        return nr.insertNguyenLieu(ma, ten, hsd, dvt, slt);
//    }

    @Override
    public String getNguyeLieu(String ma) {
        String id = null;
        if (nr.getNguyeLieu(ma) == null) {
            return id;
        } else {
            return id = nr.getNguyeLieu(ma).getId();
        }
    }

//    @Override
//    public void deleteNguyenLieu(String maNL) {
//        NguyenLieu nguyenlieu = nr.getNguyeLieu(maNL);
//        nr.deleteMauSac(maNL);
//    }

    @Override
    public void update(String id, String ma, String ten, String donViTinh, float soLuongTon, Date ngay) {
        nr.update(id, ma, ten, donViTinh, soLuongTon, ngay);
    }

//    public static void main(String[] args) {
//        NguyenLieuService ns = new NguyenLieuService();
//        ns.update("BC99620B-A27E-436A-8B12-628CCCDF31F9", "nl1", "sss", "111");
//    }

//    @Override
//    public void insertNguyenLieuToChiNhanh(String idChiNhanh, NguyenLieu nguyenLieu) {
//        nr.insertNguyenLieuToChiNhanh(idChiNhanh, nguyenLieu);
//    }

//    @Override
//    public String insertNguyenLieu(String ma, String ten, Date hsd, String dvt, Float slt, String idChiNhanh) {
//       return nr.insertNguyenLieu(ma, ten, hsd, dvt, slt, idChiNhanh);
//    }

//    @Override
//    public void insertNguyenLieuToChiNhanh(String idChiNhanh, NguyenLieu nguyenLieu, String ma, String ten, Date hsd, String dvt, Float slt) {
//        nr.insertNguyenLieuToChiNhanh(idChiNhanh, nguyenLieu, ma, ten, hsd, dvt, slt);
//    }

    @Override
    public String insertNguyenLieu(String ma, String ten, Date hsd, String dvt, Float slt, String idChiNhanh) {
//         KhuVuc kv = khuVucRepository.getKhuVucFromID(kvView.getIdKhuVuc());
//        return banRepository.insertBan(SoBan, kv);
        
//        ChiNhanh cn = nr.getKhuVucFromID(idChiNhanh.getId());
      return nr.insertNguyenLieu(ma, ten, hsd, dvt, slt, idChiNhanh);
    }

    @Override
    public void deleteMauSac(String idnguyenLieu, String idChiNhanh) {
            NguyenLieu nguyenlieu = nr.getNguyeLieu(idnguyenLieu);
//        nr.deleteMauSac(idnguyenLieu, idChiNhanh);
    }
//    @Override
//    public void deleteNguyenLieu(String maNL) {
//        NguyenLieu nguyenlieu = nr.getNguyeLieu(maNL);
//        nr.deleteMauSac(maNL);
//    }

//    @Override
//    public void deleteNguyenLieu(String idNguyenLieu) {
//      NguyenLieu nl1 = nr.getNguyeLieu(idNguyenLieu);
//      nr.deleteNguyenLieu(idNguyenLieu);
//    }

    @Override
    public void deleteNguyenLieu(String idNguyenLieu) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

//    @Override
//    public String getChiTietSpByIdSanPham(String id) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }

    @Override
    public List<NguyenLieuViewModel_Long> getAllNguyenLieuByChiNhanh(String idChiNhanh) {
      List<NguyenLieuViewModel_Long> listNguyenLieu = new ArrayList<>();
        var ListNl = nr.getAllNguyenLieuByChiNhanh(idChiNhanh);
        for (NguyenLieu nguyenLieu : ListNl) {
            NguyenLieuViewModel_Long nl = new NguyenLieuViewModel_Long();
            nl.setId(nguyenLieu.getId());
            nl.setMa(nguyenLieu.getMa());
            nl.setTen(nguyenLieu.getTen());
            nl.setSoLuongTon(nguyenLieu.getSoLuongTon());
            nl.setHanSuDung(nguyenLieu.getHanSuDung());
            nl.setDonVitinh(nguyenLieu.getDonViTinh());
            listNguyenLieu.add(nl);
        }
        return listNguyenLieu;
    }

    @Override
    public List<NguyenLieuViewModel_Long> getAllNL(String ma) {
        var allNL = nr.getAllNL(ma);
       
       List<NguyenLieuViewModel_Long> lstViewMD= new ArrayList<>();
       for(NguyenLieu ctpk : allNL){
//           lstViewMD.add(new ChiTietPhieuKiemKeViewModel_Long("", "", ctpk.getSoLuong(), ctpk.getSoLuongChenhlech(), ctpk.getSoLuongThucTe(), ctpk.getLiDo()));
lstViewMD.add(new NguyenLieuViewModel_Long(ctpk.getId(), ctpk.getMa(), ctpk.getTen(), ctpk.getHanSuDung(), ctpk.getDonViTinh(), ctpk.getSoLuongTon()));
       }
       return lstViewMD;
    }

    @Override
    public List<NguyenLieuViewModel_Long> getAllNguyenLieuByChiNhanh1(String MA) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    List<NguyenLieuViewModel_Long> listNguyenLieu = new ArrayList<>();
        var ListNl = nr.getAllNguyenLieuByChiNhanh1(MA);
        for (NguyenLieu nguyenLieu : ListNl) {
            NguyenLieuViewModel_Long nl = new NguyenLieuViewModel_Long();
            nl.setId(nguyenLieu.getId());
            nl.setMa(nguyenLieu.getMa());
            nl.setTen(nguyenLieu.getTen());
            nl.setSoLuongTon(nguyenLieu.getSoLuongTon());
            nl.setHanSuDung(nguyenLieu.getHanSuDung());
            nl.setDonVitinh(nguyenLieu.getDonViTinh());
            listNguyenLieu.add(nl);
        }
        return listNguyenLieu;
    }

    @Override
    public String getChiNhanh(String ma) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    String id = null;
        if (nr.getChiNhanh(ma)== null) {
            return id;
        } else {
            return id = nr.getChiNhanh(ma).getId();
        }
    }
}
