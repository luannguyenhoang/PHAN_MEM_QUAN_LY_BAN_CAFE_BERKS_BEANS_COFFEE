package service;

import domainmodel.ChiNhanh;
import domainmodel.ChucVu;
import domainmodel.NhanVien;
import java.util.List;
import viewmodel.ChucVuView;
import viewmodel.NhanVienView;

/**
 *
 * @author duong
 */
public interface INhanVien {

    NhanVienView getNhanVienById(String id);
    int countNVByMa(String maNV);
    // READ
    List<NhanVienView> getAllNhanVien();

    List<NhanVienView> getAllNVByChiNhanh(ChiNhanh cn);

    // Filter and searh all
    List<NhanVienView> getAllNVByMa(String maNV);

    List<NhanVienView> getAllNVByName(String tenNV);

    List<NhanVienView> getAllNVBySDT(String sdt);

    List<NhanVienView> getAllNVByTrangThai(int trangThai);

    List<NhanVienView> getAllNVByMaAndTrangThai(int trangThai, String maNV);

    List<NhanVienView> getAllNVByNameAndTrangThai(int trangThai, String tenNV);

    List<NhanVienView> getAllNVBySDTAndTrangThai(int trangThai, String sdt);

    // Filter and search by chi nhanh
    List<NhanVienView> getAllNVByChiNhanhAndMa(ChiNhanh cn, String maNV);

    List<NhanVienView> getAllNVByChiNhanhAndName(ChiNhanh cn, String tenNV);

    List<NhanVienView> getAllNVByChiNhanhAndSDT(ChiNhanh cn, String sdt);

    List<NhanVienView> getAllNVByChiNhanhAndTrangThai(ChiNhanh cn, int trangThai);

    List<NhanVienView> getAllNVByChiNhanhAndTrangThaiAndMa(ChiNhanh cn, int trangThai, String maNV);

    List<NhanVienView> getAllNVByChiNhanhAndTrangThaiAndName(ChiNhanh cn, int trangThai, String tenNV);

    List<NhanVienView> getAllNVByChiNhanhAndTrangThaiAndSDT(ChiNhanh cn, int trangThai, String sdt);

    //CUD
    String addNhanVien(NhanVien nv); // phan nay ben ban hang

    String updateNV(ChiNhanh cn, String id, NhanVien nv); // sua dc chi nhanh

    String updateNhanVien(String id, NhanVien nv, boolean check); // chi sua thong tin co ban, khong sua chi nhanh
    String updateNhanVienByAdmin(String id, NhanVien nv, boolean check);
    
//    String updateChiNhanhNhanVien(String id, ChiNhanh cn, boolean check); // chi sua chi nhanh

    String deleteNhanVien(String id); // xoa trang thai

    String deleteNV(String id); // xoa han
    // LOGIC

    String validateDataInput(Object[] data);
    
    /// Repo Chuc Vu
    List<ChucVuView> getAllChucVu();
    ChucVu getChucVuById(String id);
}
