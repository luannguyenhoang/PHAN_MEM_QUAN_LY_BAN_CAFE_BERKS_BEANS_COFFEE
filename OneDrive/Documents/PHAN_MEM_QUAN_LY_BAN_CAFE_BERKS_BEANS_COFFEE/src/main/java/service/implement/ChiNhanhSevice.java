/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.implement;

import domainmodel.ChiNhanh;
import domainmodel.KhuVuc;
import domainmodel.NhanVien;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import repository.ChiNhanhRepo;
import repository.KhuVucRepository;

import service.IChiNhanh;
import utility.Hibernateutility;
import viewmodel.ChiNhanhVM_Long;
import viewmodel.ChiNhanhViewModel_Long;
import viewmodel.KhuVucViewModel;
import viewmodel.NhanVienVM_Long;
import viewmodel.NhanVienViewModel_Hoang;

/**
 *
 * @author PC
 */
public class ChiNhanhSevice implements IChiNhanh {

    private ChiNhanhRepo chinhanhRepo = new ChiNhanhRepo();
    KhuVucRepository kr = new KhuVucRepository();
    
    @Override
    public String getChiNhanh(String ma) {
        String id = null;
        if (chinhanhRepo.getChiNhanh(ma) == null) {
            return id;
        } else {
            return id = chinhanhRepo.getChiNhanh(ma).getId();
        }
    }

    @Override
    public void update(ChiNhanh cn, String ma, String thanhpho, String quocgia, int trangThai, Date date) {
        ChiNhanh chinhanh = chinhanhRepo.getChiNhanh(cn.getMa());
        chinhanhRepo.update(chinhanh, ma, thanhpho, quocgia, trangThai, date);
    }


    @Override
    public List<ChiNhanhVM_Long> getAll() {

        var allCN = chinhanhRepo.getAll();
        List<ChiNhanhVM_Long> lstViewMD = new ArrayList<>();
        if(allCN !=null){
            for (ChiNhanh cn : allCN) {
                ChiNhanhVM_Long cnView = new ChiNhanhVM_Long();
                if(cn.getMa() != null){
                    
                }
            lstViewMD.add(new ChiNhanhVM_Long(cn.getId(), cn.getMa(), cn.getQuocGia(), cn.getThanhPho(), cn.getNgayKhaiTruong(), cn.getTrangThai(), cn.getGiaTriDoiDiem(), cn.getGiaTriDiem(),""));
        }
        }
        
        return lstViewMD;

    }

//     @Override
//    public List<BanViewModel> getAllBanByKhuVuc(String idKhuVuc) {
//        var allBan = banRepository.getAllBanByKhuVuc(idKhuVuc);
//        List<BanViewModel> listView = new ArrayList<>();
//        if (allBan != null) {
//            for (Ban ban : allBan) {
//                BanViewModel banView = new BanViewModel();
//                if (ban.getTrangThaiSuDung() == 1) {
//                    if (ban.getKhuVuc() != null) {
//                        banView.setMakhuvuc(ban.getKhuVuc().getMa());
//                    } else {
//                        banView.setMakhuvuc("chưa có thông tin");
//                    }
//                    if (ban.getId() != null) {
//                        banView.setIdban(ban.getId());
//                    } else {
//                        banView.setIdban("chưa có thông tin");
//                    }
//                    listView.add(new BanViewModel(ban.getId(), ban.getSoBan(), ban.getKhuVuc().getMa()));
//                }

    private List<ChiNhanhVM_Long> toDataView(List<ChiNhanh> chiNhanhs) {
        List<ChiNhanhVM_Long> chiNhanhVs = new ArrayList<>();
        for (ChiNhanh cn : chiNhanhs) {
            ChiNhanhVM_Long cnV = toChiNhanhView(cn);

            chiNhanhVs.add(cnV);
        }
        return chiNhanhVs;
    }

    private ChiNhanhVM_Long toChiNhanhView(ChiNhanh cn) {
        return new ChiNhanhVM_Long(cn.getId(), cn.getMa(), cn.getQuocGia(), cn.getThanhPho(), cn.getNgayKhaiTruong(), cn.getTrangThai(), cn.getGiaTriDoiDiem(), cn.getGiaTriDiem(),"");
    }


    @Override
    public void deleteMauSac(String maMau) {
        ChiNhanh chinhanh = chinhanhRepo.getChiNhanh(maMau);
        chinhanhRepo.deleteMauSac(maMau);
    }

    @Override
    public List<ChiNhanh> findByMa(String ma) {
        return chinhanhRepo.findByMa(ma);
    }

    public static void main(String[] args) {
        System.out.println(new ChiNhanhRepo().findByMa("CN1"));
    }


    @Override
    public String insertChiNhanh(String ma, String quocGia, String thanhPho, Date ngayKhaiTruong, float giaTriDiem, float giaTriDoiDiem, int trangThai) {
        return chinhanhRepo.insertChiNhanh(ma, quocGia, thanhPho, ngayKhaiTruong, giaTriDiem, giaTriDoiDiem, trangThai);
    }

    @Override
    public List<NhanVienVM_Long> getAllNV() {
           var allNV = chinhanhRepo.getAllNV();
        List<NhanVienVM_Long> lstViewMD = new ArrayList<>();
        if(allNV !=null){
            for (NhanVien cn : allNV) {
//                ChiNhanhVM_Long cnView = new ChiNhanhVM_Long();
NhanVienVM_Long nv = new NhanVienVM_Long();
                if(cn.getMa() != null){
                    
                }
//            lstViewMD.add(new ChiNhanhVM_Long(cn.getId(), cn.getMa(), cn.getQuocGia(), cn.getThanhPho(), cn.getNgayKhaiTruong(), cn.getTrangThai(), cn.getGiaTriDoiDiem(), cn.getGiaTriDiem(),""));
lstViewMD.add(new NhanVienVM_Long(cn.getId(), cn.getHoTen(), cn.getMa(), cn.getChiNhanh().getMa(),cn.getTrangThai()));
        }
        }
        
        return lstViewMD;
    }

    @Override
    public void deleteCN(String idCN) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
chinhanhRepo.deleteCN(idCN);
    }

    @Override
    public List<ChiNhanhVM_Long> getAllConHD() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
// var allCN = chinhanhRepo.getAll();
//        List<ChiNhanhVM_Long> lstViewMD = new ArrayList<>();
//        if(allCN !=null){
//            for (ChiNhanh cn : allCN) {
//                ChiNhanhVM_Long cnView = new ChiNhanhVM_Long();
//                if(cn.getMa() != null){
//                    
//                }
//            lstViewMD.add(new ChiNhanhVM_Long(cn.getId(), cn.getMa(), cn.getQuocGia(), cn.getThanhPho(), cn.getNgayKhaiTruong(), cn.getTrangThai(), cn.getGiaTriDoiDiem(), cn.getGiaTriDiem(),""));
//        }
//    }
//
//return lstViewMD;
  var allNV = chinhanhRepo.getAllConHD();
       List<ChiNhanhVM_Long> lstViewMD= new ArrayList<>();
       for(ChiNhanh cn : allNV){
//           lstViewMD.add(new NhanVienViewModel_Hoang(nv.getId(), nv.getMa(), nv.getHoTen()));
lstViewMD.add(new ChiNhanhVM_Long(cn.getId(), cn.getMa(), cn.getQuocGia(), cn.getThanhPho(), cn.getNgayKhaiTruong(), cn.getTrangThai(), cn.getGiaTriDoiDiem(), cn.getGiaTriDiem(),""));

       }
       return lstViewMD;

    }
}
