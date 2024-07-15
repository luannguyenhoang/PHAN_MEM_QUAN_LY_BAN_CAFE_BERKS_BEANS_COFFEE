package service;

import domainmodel.ChiNhanh;
import domainmodel.KhuyenMai;
import domainmodel.SanPham;
import java.util.Date;
import java.util.List;
import viewmodel.ChiNhanhView;
import viewmodel.KhuyenMaiView;

/**
 *
 * @author duong
 */
public interface IKhuyenMai {
    
    // get data
    KhuyenMai getKMById(String id);
    KhuyenMaiView getKhuyenMaiViewById(String id);
    // READ
    List<KhuyenMaiView> getAllKhuyenMai();

    List<KhuyenMaiView> getAllKMByChiNhanh(ChiNhanh cn);

    // Filter and searh all

    List<KhuyenMaiView> getAllKMByName(String tenKM);

//    List<KhuyenMaiView> getAllKMByDate(Date d1, Date d2);

    List<KhuyenMaiView> getAllKMByTrangThai(int trangThai);

    List<KhuyenMaiView> getAllKMByNameAndTrangThai(int trangThai, String tenKM);

//    List<KhuyenMaiView> getAllNVByDateAndTrangThai(int trangThai, Date d1, Date d2);

    // Filter and search by chi nhanh

    List<KhuyenMaiView> getAllKMByChiNhanhAndName(ChiNhanh cn, String tenKM);

//    List<KhuyenMaiView> getAllKMByChiNhanhAndDate(ChiNhanh cn, Date d1, Date d2);

    List<KhuyenMaiView> getAllKMByChiNhanhAndTrangThai(ChiNhanh cn, int trangThai);

    List<KhuyenMaiView> getAllKMByChiNhanhAndTrangThaiAndName(ChiNhanh cn, int trangThai, String tenKM);

//    List<KhuyenMaiView> getAllKMByChiNhanhAndTrangThaiAndDate(ChiNhanh cn, int trangThai, Date d1, Date d2);
    // CUD
    String addKhuyenMai(KhuyenMai khuyenMai, List<SanPham> sanPhamAdds);
    String updateKhuyenMai(String id, KhuyenMai khuyenMai, List<SanPham> sanPhamAdds, List<SanPham> sanPhamDels);
    String deleteKhuyenMai(String id); // chuyen trang thai
    String deleteKM(String id, List<SanPham> sanPhamDels); // xoa han luon
    String deleteKM1(String id, List<SanPham> sanPhamDels); // xoa han luon
    
    // LOGIC
    String validateDataInput(Object[] data);
    String validateDataInput1(Object[] data, KhuyenMai km);
    
    /// Repo ChiNhanh
    List<ChiNhanhView> getAllChiNhanhON();
    List<ChiNhanhView> getAllChiNhanh();
    ChiNhanh getChiNhanhById(String id);
    
    /// Repo SanPham
    SanPham getSPById(String id);
    List<SanPham> getAllSP();
    List<SanPham> getAllSPByMa(String maSP);
    List<SanPham> getAllSPByName(String tenSP);
    
    List<SanPham> getAllSPByChiNhanh(ChiNhanh cn);
    public List<SanPham> getAllSPByChiNhanhAndMa(ChiNhanh cn, String maSP);
    public List<SanPham> getAllSPByChiNhanhAndName(ChiNhanh cn, String tenSP);
    
    List<SanPham> getAllSPByKhuyenMai(KhuyenMai km);
    List<SanPham> getAllSPByKhuyenMaiAndChiNhanh(ChiNhanh cn, KhuyenMai km);
    SanPham getSanPhamByMa(String ma);
    // ChiNhanh
//    ChiNhanh getChiNhanhByNV();
}
