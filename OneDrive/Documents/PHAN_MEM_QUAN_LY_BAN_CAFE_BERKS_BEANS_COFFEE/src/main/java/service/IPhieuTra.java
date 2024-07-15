/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import domainmodel.ChiTietPhieuTra;
import domainmodel.NguyenLieu;
import domainmodel.PhieuTraHang;
import java.util.Date;
import java.util.List;
import java.util.Set;
import viewmodel.ChiTietPhieuTraViewModel;
import viewmodel.NguyenLieuViewModel_Hoang;
import viewmodel.PhieuTraViewModel;

/**
 *
 * @author trant
 */
public interface IPhieuTra {

    Set<PhieuTraViewModel> getAllPhieuTraByChiNhanh(String idChiNhanh);

//    List<ChiTietPhieuTraViewModel> getAllChiTietPhieuTra();
    Set<ChiTietPhieuTraViewModel> getPhieuTraByChiTietPhieuTra(String idPhieuTra);

    PhieuTraViewModel getPhieuTraByMa(String maPT);

    PhieuTraViewModel getPhieuTraByID(String id);

    String insertPhieuTra(String maPT, String idNcc, String idNv, Date ngayTra, int trangThai);

    String updateSoluongNguyenLieuTra(String idNguyenLieu, float soLuongTra);

    void updatePhieuTra(String idPT, String maPT, String idNCC, String idNV, Date ngayTra);

    void deleteChiTietPnbyidPT(String idPT);

    void insertCTPhieuTra(String idPt, String idNL, float soLuongTra, String lyDo);

    String updateTrangThaiPhieuTra(String maPT, Integer trangThai);

    Set<PhieuTraViewModel> searchPhieuTra(String maPN);
}
