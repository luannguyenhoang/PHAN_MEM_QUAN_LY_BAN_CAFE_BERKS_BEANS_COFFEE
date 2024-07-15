package service;

import domainmodel.ChiNhanh;
import domainmodel.KhachHang;
import java.util.List;
import viewmodel.KhachHangView;

/**
 *
 * @author duong
 */
public interface IKhachHang {
    KhachHangView getKHById(String id);
    int countKHByMa(String maKH);
    // READ
    List<KhachHangView> getAllKhachHang();
    List<KhachHangView> getAllKHByChiNhanh(ChiNhanh cn);
    // Filter and searh all
    List<KhachHangView> getAllKHByMa(String maKH);
    List<KhachHangView> getAllKHByName(String tenKH);
    List<KhachHangView> getAllKHBySDT(String sdt);
    List<KhachHangView> getAllKHByTrangThai(int trangThai);
    List<KhachHangView> getAllKHByMaAndTrangThai(int trangThai, String maKH);
    List<KhachHangView> getAllKHByNameAndTrangThai(int trangThai, String tenKH);
    List<KhachHangView> getAllKHBySDTAndTrangThai(int trangThai, String sdt);
    // Filter and search by chi nhanh
    List<KhachHangView> getAllKHByChiNhanhAndMa(ChiNhanh cn, String maKH);
    List<KhachHangView> getAllKHByChiNhanhAndName(ChiNhanh cn, String tenKH);
    List<KhachHangView> getAllKHByChiNhanhAndSDT(ChiNhanh cn, String sdt);
    List<KhachHangView> getAllKHByChiNhanhAndTrangThai(ChiNhanh cn, int trangThai);
    List<KhachHangView> getAllKHByChiNhanhAndTrangThaiAndMa(ChiNhanh cn, int trangThai, String maKH);
    List<KhachHangView> getAllKHByChiNhanhAndTrangThaiAndName(ChiNhanh cn, int trangThai, String tenKH);
    List<KhachHangView> getAllKHByChiNhanhAndTrangThaiAndSDT(ChiNhanh cn, int trangThai, String sdt);
    //CUD
    String addKhachHang(KhachHang kh); // phan nay ben ban hang
    String updateKH(ChiNhanh cn,String id, KhachHang kh); // sua dc chi nhanh
    String updateKhachHang(String id, KhachHang kh, boolean check); // chi sua thong tin co ban, khong sua chi nhanh
    String deleteKhachHang(String id); // xoa trang thai
    String deleteKH(String id); // xoa han
    // LOGIC
    String validateDataInput(Object[] data);
}
