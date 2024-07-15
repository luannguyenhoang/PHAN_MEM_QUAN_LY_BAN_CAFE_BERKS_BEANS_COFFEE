package service.implement;

import domainmodel.NguyenLieu;
import domainmodel.SanPham;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import repository.SanPhamRespository;
import service.ISanPhamService;
import viewmodel.NguyenLieuDangSuDung;
import viewmodel.SanPhamViewModel;

public class SanPhamService implements ISanPhamService {

    private SanPhamRespository sanPhamRepo;

    public SanPhamService() {
        sanPhamRepo = new SanPhamRespository();
    }

    @Override
    public List<SanPhamViewModel> getAllSanPhamDangBanByChiNhanh(String idChiNhanh) {
        var listSanPham = sanPhamRepo.getAllSanPhamByChiNhanh(idChiNhanh);
        List<SanPhamViewModel> listView = new ArrayList<>();
        if (listSanPham != null) {
            for (SanPham sanPham : listSanPham) {
                if (sanPham.getTrangThai() == 1) {
                    SanPhamViewModel spView = new SanPhamViewModel();
                    spView.setIdSp(sanPham.getId());
                    spView.setMaSp(sanPham.getMa());
                    if (sanPham.getTen() != null) {
                        spView.setTenSp(sanPham.getTen());
                    }
                    if (sanPham.getGiaBan() != null) {
                        spView.setGiaBan(new BigDecimal(sanPham.getGiaBan()));
                    }
                    if (sanPham.getAvatar() != null) {
                        spView.setAvatar(sanPham.getAvatar());
                    }
                    listView.add(spView);
                }

            }
        }
        return listView;
    }

    @Override
    public List<SanPhamViewModel> getAllSanPhamDaXoaByChiNhanh(String idChiNhanh) {
        var listSanPham = sanPhamRepo.getAllSanPhamByChiNhanh(idChiNhanh);
        List<SanPhamViewModel> listView = new ArrayList<>();
        if (listSanPham != null) {
            for (SanPham sanPham : listSanPham) {
                if (sanPham.getTrangThai() == 0) {
                    SanPhamViewModel spView = new SanPhamViewModel();
                    spView.setIdSp(sanPham.getId());
                    spView.setMaSp(sanPham.getMa());
                    if (sanPham.getTen() != null) {
                        spView.setTenSp(sanPham.getTen());
                    }
                    if (sanPham.getGiaBan() != null) {
                        spView.setGiaBan(new BigDecimal(sanPham.getGiaBan()));
                    }
                    if (sanPham.getAvatar() != null) {
                        spView.setAvatar(sanPham.getAvatar());
                    }
                    listView.add(spView);
                }

            }
        }
        return listView;
    }

    @Override
    public String insertSanPham(String ma, String ten, float giaBan, byte[] avatar) {
        String id = sanPhamRepo.insertSanPham(ma, ten, giaBan, avatar);
        return id;
    }

    @Override
    public void UpdateSanPham(String id, String ma, String ten, float giaBan, byte[] avatar) {
        sanPhamRepo.UpdateSanPham(id, ma, ten, giaBan, avatar);
    }

    @Override
    public void deleteSanPham(String id) {
        sanPhamRepo.deleteSanPham(id);
    }

    @Override
    public List<SanPhamViewModel> searchSanPham(String tenSp) {
        List<SanPhamViewModel> listView = new ArrayList<>();
        var listSp = sanPhamRepo.searchSanPham(tenSp);
        for (SanPham sanPham : listSp) {
            SanPhamViewModel spView = new SanPhamViewModel();
            spView.setMaSp(sanPham.getMa());
            if (sanPham.getTen() != null) {
                spView.setTenSp(sanPham.getTen());
            }
            if (sanPham.getGiaBan() != null) {
                spView.setGiaBan(new BigDecimal(sanPham.getGiaBan()));
            }
            listView.add(spView);

        }
        return listView;
    }

    @Override
    public SanPhamViewModel getSanPhamById(String id) {
        SanPham sanPham = sanPhamRepo.getSanPhamById(id);
        SanPhamViewModel spView = new SanPhamViewModel();
        spView.setIdSp(sanPham.getId());
        spView.setMaSp(sanPham.getMa());
        if (sanPham.getTen() != null) {
            spView.setTenSp(sanPham.getTen());
        }
        if (sanPham.getGiaBan() != null) {
            spView.setGiaBan(new BigDecimal(sanPham.getGiaBan()));
        }
        if (sanPham.getAvatar() != null) {
            spView.setAvatar(sanPham.getAvatar());
        }
        return spView;
    }

    @Override
    public List<NguyenLieuDangSuDung> getAllNguyenLieuByChiNhanh(String idChiNhanh) {
        List<NguyenLieuDangSuDung> listNguyenLieu = new ArrayList<>();
        var ListNl = sanPhamRepo.getAllNguyenLieuByChiNhanh(idChiNhanh);
        for (NguyenLieu nguyenLieu : ListNl) {
            NguyenLieuDangSuDung nl = new NguyenLieuDangSuDung();
            nl.setId(nguyenLieu.getId());
            nl.setMa(nguyenLieu.getMa());
            nl.setTen(nguyenLieu.getTen());
            listNguyenLieu.add(nl);
        }
        return listNguyenLieu;
    }

    @Override
    public SanPhamViewModel getSanPhamByMa(String ma) {
        var sanPham = sanPhamRepo.getSanPhamByMa(ma);
        SanPhamViewModel spView = null;
        if (sanPham != null) {
            spView = new SanPhamViewModel();
            spView.setIdSp(sanPham.getId());
            spView.setMaSp(sanPham.getMa());
            if (sanPham.getTen() != null) {
                spView.setTenSp(sanPham.getTen());
            }
            if (sanPham.getGiaBan() != null) {
                spView.setGiaBan(new BigDecimal(sanPham.getGiaBan()));
            }
            if (sanPham.getAvatar() != null) {
                spView.setAvatar(sanPham.getAvatar());
            }

        }
        return spView;
    }

}
