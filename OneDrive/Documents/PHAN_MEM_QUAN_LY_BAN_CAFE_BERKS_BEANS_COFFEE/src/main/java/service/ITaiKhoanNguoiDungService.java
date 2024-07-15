package service;

import domainmodel.NhanVien;
import java.util.List;
import viewmodel.NhanVienViewModel_Van;
import viewmodel.TaiKhoanNguoiDungViewModel;

public interface ITaiKhoanNguoiDungService {

    public List<TaiKhoanNguoiDungViewModel> getAllTkNguoiDung();

    public List<NhanVienViewModel_Van> getAllNV();

    String inserttkNguoiDung(String TenTk, String MatKhau, NhanVienViewModel_Van nv);

   void updateTKNguoiDung(String id, String TenTk, String MatKhau, NhanVienViewModel_Van nv1);

    public void deletetkNguoiDung(String idTk);

    public String getIdFromMa(String idTk);
}
