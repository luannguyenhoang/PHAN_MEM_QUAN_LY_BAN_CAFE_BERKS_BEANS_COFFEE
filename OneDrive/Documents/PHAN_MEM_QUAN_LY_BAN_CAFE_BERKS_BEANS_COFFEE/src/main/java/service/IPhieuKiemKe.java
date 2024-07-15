/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import domainmodel.ChiNhanh;
import domainmodel.ChiTietPhieuKiemKe;
import domainmodel.NguyenLieu;
import domainmodel.NhanVien;
import domainmodel.PhieuKiemKe;
import java.util.Date;
import java.util.List;
import java.util.Set;
import viewmodel.ChiTietPhieuKiemKeViewModel_Long;
import viewmodel.NguyenLieuViewModel_Hoang;
import viewmodel.NguyenLieuViewModel_Long;
import viewmodel.NhanVienViewModel_Hoang;
import viewmodel.PhieuKiemKeViewModel_Long;

/**
 *
 * @author PC
 */
public interface IPhieuKiemKe {
    List<PhieuKiemKeViewModel_Long> getAllPKKcbb();
  void update(PhieuKiemKe cn, String ma, int trangThai, Date date);
    String getChiNhanh(String ma);
    List<NhanVienViewModel_Hoang> getAllNV();
 String insertPKK(String ma, Date ngayKiemKe, int trangThai, NhanVienViewModel_Hoang nhanVien);
  void deletePKK(String idPKK);
List<PhieuKiemKeViewModel_Long> getAllPKK();
}
