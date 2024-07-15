/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.Date;
import java.util.List;
import viewmodel.ThongKeChiNhanh;
import viewmodel.ThongKeSanPhamBanChay;
import viewmodel.ThongKeTheoThoiGianViewModel;

/**
 *
 * @author trant
 */
public interface IThongKeService {

    public List<ThongKeTheoThoiGianViewModel> getAllThongKeByDate(Date dateStart, Date dateEnd);

    List<ThongKeSanPhamBanChay> getAllSanPhamBanChay();

    List<ThongKeChiNhanh> getAllThongKeChiNhanh();
}
