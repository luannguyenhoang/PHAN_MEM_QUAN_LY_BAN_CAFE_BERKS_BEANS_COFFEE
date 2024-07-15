/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.implement;

import domainmodel.NhanVien;
import domainmodel.PhieuKiemKe;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import repository.PhieuKiemKeRepo;
import service.IPhieuKiemKe;
import viewmodel.ChiTietPhieuKiemKeViewModel_Long;
import viewmodel.NguyenLieuViewModel_Hoang;
import viewmodel.NguyenLieuViewModel_Long;
import viewmodel.NhanVienViewModel_Hoang;
import viewmodel.PhieuKiemKeViewModel_Long;

/**
 *
 * @author PC
 */
public class PhieuKiemKeService implements IPhieuKiemKe{
   PhieuKiemKeRepo pkkRepo;

    public PhieuKiemKeService() {
        pkkRepo = new PhieuKiemKeRepo();
    }
    
    
    

    @Override
    public List<PhieuKiemKeViewModel_Long> getAllPKK() {
       var allPKK = pkkRepo.getAllPKK();
       List<PhieuKiemKeViewModel_Long> lstViewMD= new ArrayList<>();
       for(PhieuKiemKe pk : allPKK){
           lstViewMD.add(new PhieuKiemKeViewModel_Long(pk.getId(),pk.getMa(),pk.getNgayKiemKe(),pk.getTrangThai(),pk.getNhanVien().getMa(),null));

       }
       return lstViewMD;
    }
    
    
//    @Override
//    public List<DongSpViewModel> getAllDongSp() {
//        var listDong = DongRepo.getAllDongSp();
//        List<DongSpViewModel> listView = new ArrayList<>();
//        for (DongSP dongSP : listDong) {
//            listView.add(new DongSpViewModel(dongSP.getMa(), dongSP.getTen()));
//        }
//        return listView;
//    }


//    @Override
//    public String insertBan(String ma, Date ngayKiemKe, int trangThai) {
//       return pkkRepo.insertBan(ma, ngayKiemKe, trangThai);
//    }
//      @Override
//    public List<BanViewModel> getAllBan() {
//        var allBan = banRepository.getAllBan();
//        List<BanViewModel> listView = new ArrayList<>();
//        for (Ban ban : allBan) {
//            if (ban.getTrangThaiSuDung()== 1) {
//                listView.add(new BanViewModel(ban.getId(), ban.getSoBan(), ban.getKhuVuc().getMa()));
//            }
//
//        }
//        return listView;
//    }

//    @Override
//    public String insertBan(String ma, Date ngayKiemKe, int trangThai) {
//        return pkkRepo.insertBan(ma, ngayKiemKe, trangThai);
//    }

    @Override
    public String insertPKK(String ma, Date ngayKiemKe, int trangThai, NhanVienViewModel_Hoang nhanVien) {
//         NhanVien nv = khuVucRepository.getKhuVucFromID(kvView.getIdKhuVuc());
NhanVien nv = pkkRepo.getNhanVienFromID(nhanVien.getId());
       return pkkRepo.insertPKK(ma, ngayKiemKe, trangThai, nv);
    }

    @Override
    public void deletePKK(String idPKK) {
        pkkRepo.deletePKK(idPKK);
    }

    @Override
    public List<NhanVienViewModel_Hoang> getAllNV() {
            var allNV = pkkRepo.getAllNV();
       List<NhanVienViewModel_Hoang> lstViewMD= new ArrayList<>();
       for(NhanVien nv : allNV){
           lstViewMD.add(new NhanVienViewModel_Hoang(nv.getId(), nv.getMa(), nv.getHoTen()));

       }
       return lstViewMD;
    }

    @Override
    public void update(PhieuKiemKe cn, String ma, int trangThai, Date date) {
        PhieuKiemKe chinhanh = pkkRepo.getChiNhanh(cn.getMa());
        pkkRepo.update(chinhanh, ma, trangThai, date);
    }

    @Override
    public String getChiNhanh(String ma) {
         String id = null;
        if (pkkRepo.getChiNhanh(ma) == null) {
            return id;
        } else {
            return id = pkkRepo.getChiNhanh(ma).getId();
        }
    }

    @Override
    public List<PhieuKiemKeViewModel_Long> getAllPKKcbb() {
         var allPKK = pkkRepo.getAllPKKcbb();
       List<PhieuKiemKeViewModel_Long> lstViewMD= new ArrayList<>();
       for(PhieuKiemKe pk : allPKK){
           lstViewMD.add(new PhieuKiemKeViewModel_Long(pk.getId(),pk.getMa(),pk.getNgayKiemKe(),pk.getTrangThai(),pk.getNhanVien().getMa(),null));

       }
       return lstViewMD;
    }

   
}
