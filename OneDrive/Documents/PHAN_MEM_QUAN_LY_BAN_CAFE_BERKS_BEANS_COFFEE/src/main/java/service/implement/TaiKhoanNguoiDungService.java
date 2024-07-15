package service.implement;

import domainmodel.NhanVien;
import domainmodel.TaiKhoanNguoiDung;

import java.util.ArrayList;
import java.util.List;
import repository.TaiKhoanNguoiDungRepository;
import service.ITaiKhoanNguoiDungService;
import viewmodel.NhanVienViewModel_Van;
import viewmodel.TaiKhoanNguoiDungViewModel;

public class TaiKhoanNguoiDungService implements ITaiKhoanNguoiDungService {

    TaiKhoanNguoiDungRepository taiKhoanNguoiDungRepository;

    public TaiKhoanNguoiDungService() {
        taiKhoanNguoiDungRepository = new TaiKhoanNguoiDungRepository();
    }

    @Override
    public List<TaiKhoanNguoiDungViewModel> getAllTkNguoiDung() {
        var allTk = taiKhoanNguoiDungRepository.getAllTaiKhoanNguoiDung();
        List<TaiKhoanNguoiDungViewModel> listView = new ArrayList<>();
        for (TaiKhoanNguoiDung tk : allTk) {
            TaiKhoanNguoiDungViewModel tkView = new TaiKhoanNguoiDungViewModel();
            if (tk.getTrangThai() != 0) {
                if (tk.getNhanVien() != null) {
                    tkView.setMaNhanVien(tk.getNhanVien().getMa());
                } else {
                    tkView.setMaNhanVien("chưa có thông tin");
                }
                listView.add(new TaiKhoanNguoiDungViewModel(tk.getId(), tk.getTenTK(), tk.getMatKhau(), tk.getNhanVien().getMa(),tk.getNhanVien().getHoTen(),tk.getTrangThai()));
            }

        }
        return listView;
    }

    @Override
    public List<NhanVienViewModel_Van> getAllNV() {

        var nv = taiKhoanNguoiDungRepository.getAllNhanVien();
        List<NhanVienViewModel_Van> listView = new ArrayList<>();
        for (NhanVien NV : nv) {
            NhanVienViewModel_Van nvView = new NhanVienViewModel_Van();
            nvView.setIdNhanVien(NV.getId());
            nvView.setMaNhanVien(NV.getMa());
            nvView.setHoTen(NV.getHoTen());
            listView.add(nvView);
        }
        return listView;

    }

    @Override
    public String inserttkNguoiDung(String TenTk, String MatKhau, NhanVienViewModel_Van nvView) {
           NhanVien nv = taiKhoanNguoiDungRepository.getNhanVienFromMa(nvView.getMaNhanVien());
        return taiKhoanNguoiDungRepository.insertTaiKhoanNguoiDung(TenTk, MatKhau,nv );
    }

    @Override
    public void updateTKNguoiDung(String id, String TenTk, String MatKhau, NhanVienViewModel_Van nv1) {
        NhanVien nv = taiKhoanNguoiDungRepository.getNhanVienFromMa(nv1.getMaNhanVien());
        taiKhoanNguoiDungRepository.updateTaiKhoanNguoiDung(id, TenTk, MatKhau, nv);
    }

    @Override
    public void deletetkNguoiDung(String idTk) {
        taiKhoanNguoiDungRepository.deleteTaiKhoanNguoiDung(idTk);
    }

    @Override
    public String getIdFromMa(String idTk) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
