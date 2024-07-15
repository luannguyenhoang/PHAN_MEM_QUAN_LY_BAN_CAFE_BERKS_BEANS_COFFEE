/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import domainmodel.HoaDonBanHang;
import java.util.Date;
import java.util.List;
import viewmodel.HoaDonViewModel;

/**
 *
 * @author trant
 */
public interface IHoaDon {

    List<HoaDonViewModel> getAllHoaDon();

    HoaDonViewModel getHoaDonByMa(String maHD);

    String updateTrangThai(String maHD,Integer trangThai);
    
    List<HoaDonViewModel> locHoaDon(Date startDate, Date endDate);
}
