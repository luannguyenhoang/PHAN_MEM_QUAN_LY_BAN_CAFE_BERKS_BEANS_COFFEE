package service.implement;

import domainmodel.ChiTietSP;
import domainmodel.NguyenLieu;
import domainmodel.SanPham;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import repository.ChiTietSanPhamRepository;
import repository.SanPhamRespository;
import service.IChiTietSpService;
import viewmodel.ChiTietSPViewModel;

public class ChiTietSpService implements IChiTietSpService {
    
    ChiTietSanPhamRepository chiTietSpRepo;
    SanPhamRespository sanPhamrepo;
    
    public ChiTietSpService() {
        chiTietSpRepo = new ChiTietSanPhamRepository();
    }
    
    @Override
    public void insertChiTietSanPham(float dinhLuong, String idSanPham, String idNguyenLieu) {
        SanPham sp = sanPhamrepo.getSanPhamById(idSanPham);
        NguyenLieu nguyenLieu = chiTietSpRepo.getNguyenLieuByID(idNguyenLieu);
        chiTietSpRepo.insertChiTietSanPham(dinhLuong, sp, nguyenLieu);
    }
    
    @Override
    public Set<ChiTietSPViewModel> getChiTietSpByIdSanPham(String idsanPham) {
        Set<ChiTietSP> setChiTiet = chiTietSpRepo.getChiTietSpByIdSanPham(idsanPham);
        Set<ChiTietSPViewModel> setChiTietView = new HashSet<>();
        for (ChiTietSP chiTietSP : setChiTiet) {
            ChiTietSPViewModel ctView = new ChiTietSPViewModel();
            ctView.setIdNguyenLieu(chiTietSP.getNguyenLieukey().getId());
            ctView.setMaNguyenLieu(chiTietSP.getNguyenLieukey().getMa());
            ctView.setTenNguyenLieu(chiTietSP.getNguyenLieukey().getTen());
            ctView.setDinhLuong(new BigDecimal(chiTietSP.getDinhLuong()));
            setChiTietView.add(ctView);
        }
        return setChiTietView;
    }

    @Override
    public void deleteChiTietSpByIdSp(String id) {
        chiTietSpRepo.deleteChiTietSpByIdSp(id);
    }
    
}
