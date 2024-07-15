/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import domainmodel.ChucVu;
import domainmodel.NhanVien;
import domainmodel.TaiKhoanAdmin;
import domainmodel.TaiKhoanNguoiDung;
import java.util.List;
import viewmodel.ChiNhanhViewModel_Hoang;
import viewmodel.TaiKhoanNguoiDungViewModel_Hoang;

/**
 *
 * @author ASUS
 */
public interface ILogin {

    TaiKhoanNguoiDung getNguoiDung(String tenTaiKhoan, String matKhau);

    TaiKhoanAdmin getAdmin(String tenTaiKhoan, String matKhau);

    ChucVu getChucVubyIdNhanVien(String idNV);

    NhanVien getNhanVienbyTaiKhoan(String idTaiKhoan);

    ChiNhanhViewModel_Hoang getChiNhanhByNhanVien(String idNV);

    List<ChiNhanhViewModel_Hoang> getAllChiNhanh();
}
