/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import domainmodel.ChiNhanh;
import domainmodel.KhuVuc;
import domainmodel.NhanVien;
import java.util.Date;
import java.util.List;
import java.util.Set;
import viewmodel.ChiNhanhVM_Long;
import viewmodel.ChiNhanhViewModel_Long;
import viewmodel.KhuVucViewModel;
import viewmodel.NhanVienVM_Long;
import viewmodel.NhanVienViewModel_Hoang;


/**
 *
 * @author PC
 */
public interface IChiNhanh {
    List<ChiNhanhVM_Long> getAllConHD();
    void deleteCN(String idCN);
List<NhanVienVM_Long> getAllNV();
    List<ChiNhanhVM_Long> getAll();
   String insertChiNhanh(String ma, String quocGia, String thanhPho, Date ngayKhaiTruong, float giaTriDiem, float giaTriDoiDiem, int trangThai);
 void update(ChiNhanh cnV, String ma, String thanhpho, String quocgia, int trangThai, Date date);
 void deleteMauSac(String maMau);
  String getChiNhanh(String ma);
  List<ChiNhanh> findByMa(String ma);

}
