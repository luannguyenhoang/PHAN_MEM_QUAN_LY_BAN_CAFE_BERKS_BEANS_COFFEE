package service;

import java.util.Set;
import viewmodel.ChiTietSPViewModel;

public interface IChiTietSpService {

    void insertChiTietSanPham(float dinhLuong, String Idsp, String Idnguyenlieu);

    Set<ChiTietSPViewModel> getChiTietSpByIdSanPham(String idSanPham);

    void deleteChiTietSpByIdSp(String id);

}
