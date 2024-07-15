package service;

import domainmodel.NhaCungCap;
import java.util.List;
import viewmodel.NhaCungCapView;

/**
 *
 * @author duong
 */
public interface INhaCungCap {
    NhaCungCapView getNCCById(String id);
    int countNCCByMa(String maNCC);
    /// READ
    List<NhaCungCapView> getAllNhaCungCap();
    // Search: ten + ma
    List<NhaCungCapView> getAllNCCByName(String tenNCC);
    List<NhaCungCapView> getAllNCCByMa(String maNCC);
    // Filter: trangThai
    List<NhaCungCapView> getAllNCCByTrangThai(int trangThai);
    // Filter and Search
    List<NhaCungCapView> getAllNCCByTrangThaiAndName(int trangThai, String tenNCC);
    List<NhaCungCapView> getAllNCCByTrangThaiAndMa(int trangThai, String maNCC);
    
//    List<NhaCungCapView> getAllNCCFilterAndSearch(int trangThai, String tenNCC, String maNCC);// dung the nay no gon hon nhieu, tuy nhien doc code hoi kho hieu, dang tim cach gop lai ma chua xu ly dc cai trangThai
    /// CUD
    String addNhaCungCap(NhaCungCap ncc);
    String updateNhaCungCap(String id, NhaCungCap ncc, boolean check); 
    String deleteNhaCungCap(String id); // chuyen trang thai
    String deleteNCC(String id);    // Xoa han
    
    /// LOGIC
    String validateDataInput(Object[] data);
}

