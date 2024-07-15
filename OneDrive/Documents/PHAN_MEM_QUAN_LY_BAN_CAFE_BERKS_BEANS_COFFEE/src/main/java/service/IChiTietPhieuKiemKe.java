/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import domainmodel.ChiTietPhieuKiemKe;
import domainmodel.PhieuKiemKe;
import java.util.List;
import java.util.Set;
import viewmodel.ChiTietPhieuKiemKeViewModel_Long;
import viewmodel.PhieuKiemKeViewModel_Long;

/**
 *
 * @author PC
 */
public interface IChiTietPhieuKiemKe {
   void update(String id, Float soLuongThucTe, String liDo);
  public List<ChiTietPhieuKiemKeViewModel_Long> getCYPKKbyPKK(String idPhieu);
    List<ChiTietPhieuKiemKeViewModel_Long> getAllChiTietHoaDon();
 void insertNguyenLieu( float SoLuongThucTe,String liDo, String idnl, String idpk);
   List<ChiTietPhieuKiemKeViewModel_Long> getPhieuNhapByChiTietPhieuNhap(String id);
}
