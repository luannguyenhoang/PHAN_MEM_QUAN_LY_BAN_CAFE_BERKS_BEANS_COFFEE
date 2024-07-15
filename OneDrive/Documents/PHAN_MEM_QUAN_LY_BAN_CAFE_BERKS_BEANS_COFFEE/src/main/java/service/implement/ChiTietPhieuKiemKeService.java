/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.implement;

import domainmodel.ChiTietPhieuKiemKe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.text.html.ListView;
import repository.ChiTietPhieuKiemKeRepo;
import repository.PhieuKiemKeRepo;
import service.IChiTietPhieuKiemKe;
import viewmodel.ChiTietPhieuKiemKeViewModel_Long;
import viewmodel.PhieuKiemKeViewModel_Long;

/**
 *
 * @author PC
 */
public class ChiTietPhieuKiemKeService implements IChiTietPhieuKiemKe{
//     PhieuKiemKeRepo pkkRepo;
//
//    public PhieuKiemKeService() {
//        pkkRepo = new PhieuKiemKeRepo();
//    }
    
    
    
    ChiTietPhieuKiemKeRepo chitietRepo;
    PhieuKiemKeRepo pkRepo;
    public ChiTietPhieuKiemKeService() {
        chitietRepo = new ChiTietPhieuKiemKeRepo();
        pkRepo = new PhieuKiemKeRepo();
    }

    @Override
    public List<ChiTietPhieuKiemKeViewModel_Long> getAllChiTietHoaDon() {
          var allPKK = chitietRepo.getAllChiTietHoaDon();
       List<ChiTietPhieuKiemKeViewModel_Long> lstViewMD= new ArrayList<>();
       for(ChiTietPhieuKiemKe ctpk : allPKK){
           lstViewMD.add(new ChiTietPhieuKiemKeViewModel_Long(ctpk.getKiemKeKey().getMa(), ctpk.getNguyenLieuKey().getMa(), ctpk.getSoLuong(), ctpk.getSoLuongChenhlech(), ctpk.getSoLuongThucTe(), ctpk.getLiDo(), ctpk.getNguyenLieuKey().getSoLuongTon()));
       }
       return lstViewMD;
    }

    
    
//    @Override
//    public String insertNguyenLieu(float SoLuong, float SoLuongThucTe, float SoLuongChenhLech, String liDo, String idpkk) {
//       return chitietRepo.insertNguyenLieu(SoLuong, SoLuongThucTe, SoLuongChenhLech, liDo, idpkk);
//    }

//    @Override
//    public String insertNguyenLieu(float SoLuong, float SoLuongThucTe, float SoLuongChenhLech, String liDo, String idpkk) {
////        PhieuKiemKe pk = pkRepo.getPKKFromId(idpkk.getId());
//       return chitietRepo.insertNguyenLieu(SoLuong, SoLuongThucTe, SoLuongChenhLech, liDo, idpkk);
//    }

    @Override
    public void insertNguyenLieu( float SoLuongThucTe,  String liDo, String idnl, String idpk) {
       chitietRepo.insertNguyenLieu(SoLuongThucTe, liDo, idnl, idpk);
    }

    @Override
    public List<ChiTietPhieuKiemKeViewModel_Long> getPhieuNhapByChiTietPhieuNhap(String id) {
        var chiTietPhieuNhap = chitietRepo.getPhieuNhapByChiTietPhieuNhap(id);
        List<ChiTietPhieuKiemKeViewModel_Long> lstView = new ArrayList<>();
         for(ChiTietPhieuKiemKe ctpk : chiTietPhieuNhap){
//           lstView.add(new ChiTietPhieuKiemKeViewModel_Long("", "", ctpk.getSoLuong(), ctpk.getSoLuongChenhlech(), ctpk.getSoLuongThucTe(), ctpk.getLiDo(), ctpk.getNguyenLieuKey().getSoLuongTon()));
 ChiTietPhieuKiemKeViewModel_Long ctView = new ChiTietPhieuKiemKeViewModel_Long();
//            ctView.setIdPhieuNhap(x.getPhieuNhapKey().getId());
ctView.setIdPhieuKiem(ctpk.getKiemKeKey().getId());
//            ctView.setIdNguyenLieu(x.getNguyenLieuKey().getId());
lstView.add(ctView);
       }
//       return ;
return lstView;
    }

    @Override
    public List<ChiTietPhieuKiemKeViewModel_Long> getCYPKKbyPKK(String idPhieu) {
         var allCT = chitietRepo.getCYPKKbyPKK(idPhieu);
        List<ChiTietPhieuKiemKeViewModel_Long> listView = new ArrayList<>();
        for (ChiTietPhieuKiemKe ct : allCT) {
            ChiTietPhieuKiemKeViewModel_Long ctView = new ChiTietPhieuKiemKeViewModel_Long();
            
                listView.add(new ChiTietPhieuKiemKeViewModel_Long(ct.getKiemKeKey().getMa(), ct.getNguyenLieuKey().getMa(), ct.getSoLuong(), ct.getSoLuongChenhlech(), ct.getSoLuongThucTe(), ct.getLiDo(), ct.getNguyenLieuKey().getSoLuongTon()));
              
            }
        return listView;
  
        }

    @Override
    public void update(String id, Float soLuongThucTe, String liDo) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
chitietRepo.update(id, soLuongThucTe, liDo);
    }
  
    
}
  

    
    

    

