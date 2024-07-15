/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import domainmodel.ChiTietHoaDon;
import domainmodel.HoaDonBanHang;
import java.util.List;
import java.util.Set;
import viewmodel.ChitietHoaDonViewModel;

/**
 *
 * @author ASUS
 */
public interface IHoaDonChiTiet {

//    List<ChitietHoaDonViewModel> getAllChiTietHoaDon();

    List<ChitietHoaDonViewModel> getHoaDonChiTietByMaHoaDon(String maHD);
}
