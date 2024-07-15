/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.implement;

import domainmodel.ChiNhanh;
import domainmodel.ChucVu;
import domainmodel.NhanVien;
import domainmodel.TaiKhoanAdmin;
import domainmodel.TaiKhoanNguoiDung;
import java.util.ArrayList;
import java.util.List;
import repository.Login_NguoiDungRepo;
import service.ILogin;
import viewmodel.ChiNhanhViewModel_Hoang;
import viewmodel.TaiKhoanNguoiDungViewModel_Hoang;

/**
 *
 * @author ASUS
 */
public class LoginSerVice implements ILogin {

    Login_NguoiDungRepo logRepo;

    public LoginSerVice() {
        logRepo = new Login_NguoiDungRepo();
    }

    @Override
    public TaiKhoanNguoiDung getNguoiDung(String tenTaiKhoan, String matKhau) {
        TaiKhoanNguoiDung taiKhoanNguoiDung = null;
        taiKhoanNguoiDung = logRepo.getNguoiDung(tenTaiKhoan, matKhau);
        if (taiKhoanNguoiDung != null) {
            return taiKhoanNguoiDung;
        }
        return taiKhoanNguoiDung;
    }

    @Override
    public TaiKhoanAdmin getAdmin(String tenTaiKhoan, String matKhau) {
        TaiKhoanAdmin taiKhoanAdmin = null;
        taiKhoanAdmin = logRepo.getAdmin(tenTaiKhoan, matKhau);
        if (taiKhoanAdmin != null) {
            return taiKhoanAdmin;
        }
        return taiKhoanAdmin;
    }

    @Override
    public ChucVu getChucVubyIdNhanVien(String idNV) {
        ChucVu cv = logRepo.getChucVubyIdNhanVien(idNV);
        if (cv != null) {
            return cv;
        }
        return null;
    }

    @Override
    public NhanVien getNhanVienbyTaiKhoan(String idTaiKhoan) {
        NhanVien nv = logRepo.getNhanVienbyTaiKhoan(idTaiKhoan);
        if (nv != null) {
            return nv;
        }
        return null;
    }

    @Override
    public ChiNhanhViewModel_Hoang getChiNhanhByNhanVien(String idNV) {
        ChiNhanh cn = logRepo.getChiNhanhByNhanVien(idNV);
        ChiNhanhViewModel_Hoang cnView = null;
        if (cn != null){
            cnView = new ChiNhanhViewModel_Hoang();
            cnView.setId(cn.getId());
            cnView.setMa(cn.getMa());
        }
        return cnView;
    }

    @Override
    public List<ChiNhanhViewModel_Hoang> getAllChiNhanh() {
        List<ChiNhanhViewModel_Hoang> lstView = new ArrayList<>();
        var chiNhanh = logRepo.getAllChiNhanh();
        if (chiNhanh != null) {
            for (ChiNhanh x : chiNhanh) {
                ChiNhanhViewModel_Hoang cnView = new ChiNhanhViewModel_Hoang();
                cnView.setId(x.getId());
                cnView.setMa(x.getMa());
                lstView.add(cnView);
            }
        }
        return lstView;
    }

}
