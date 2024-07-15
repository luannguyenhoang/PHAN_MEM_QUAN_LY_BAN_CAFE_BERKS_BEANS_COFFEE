/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.implement;

import domainmodel.ChiTietPhieuNhap;
import domainmodel.ChiTietPhieuTra;
import domainmodel.PhieuTraHang;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import repository.*;
import service.IPhieuNhap;
import service.IPhieuTra;
import viewmodel.ChiTietPhieuTraViewModel;
import viewmodel.NguyenLieuViewModel_Hoang;
import viewmodel.NhaCungCapViewModel_Hoang;
import viewmodel.NhanVienViewModel_Hoang;
import viewmodel.PhieuNhapViewModel;
import viewmodel.PhieuTraViewModel;

/**
 *
 * @author ASUS
 */
public class PhieuTraService implements IPhieuTra {

    PhieuTraRepo phieuTraRepo;
    PhieuNhapRepo phieuNhapRepo;

    public PhieuTraService() {
        phieuTraRepo = new PhieuTraRepo();
        phieuNhapRepo = new PhieuNhapRepo();
    }

    @Override
    public Set<PhieuTraViewModel> getAllPhieuTraByChiNhanh(String idChiNhanh) {
        Set<PhieuTraViewModel> lstView = new HashSet<>();
        var lstPhieuTra = phieuTraRepo.getAllPhieuTraByChiNhanh(idChiNhanh);

        for (PhieuTraHang x : lstPhieuTra) {
            PhieuTraViewModel ptView = new PhieuTraViewModel();
            ptView.setIdPhieuTra(x.getId());
            ptView.setMaPhieuTra(x.getMa());
            ptView.setMaNhaCungCap(x.getNhaCungCap().getMa());
            if (x.getNhaCungCap().getTen() != null) {
                ptView.setTenNhaCungCap(x.getNhaCungCap().getTen());
            }
            ptView.setMaNhanVien(x.getNhanVien().getMa());
            if (x.getNhanVien().getHoTen() != null) {
                ptView.setTenNhanVien(x.getNhanVien().getHoTen());
            }
            if (x.getNgayTra() != null) {
                ptView.setNgayTra(x.getNgayTra());
            }
            if (x.getTrangThai() != null) {
                ptView.setTrangThai(x.getTrangThai());
            }
            lstView.add(ptView);
        }
        return lstView;
    }

    @Override
    public PhieuTraViewModel getPhieuTraByMa(String maPT) {
        PhieuTraHang pt = phieuTraRepo.getPhieuTraByMa(maPT);
        PhieuTraViewModel ptView = null;
        if (pt != null) {
            ptView = new PhieuTraViewModel();
            ptView.setMaPhieuTra(pt.getMa());
        }
        return ptView;
    }

    @Override
    public String updateTrangThaiPhieuTra(String maPT, Integer trangThai) {
        return phieuTraRepo.updateTrangThaiPhieuTra(maPT, trangThai);
    }

    @Override
    public Set<PhieuTraViewModel> searchPhieuTra(String maPN) {
        Set<PhieuTraViewModel> lstView = new HashSet<>();
        var lstCtPhieuTra = phieuTraRepo.searchPhieuTra(maPN);
        for (ChiTietPhieuTra x : lstCtPhieuTra) {
            PhieuTraViewModel ptView = new PhieuTraViewModel();
            ptView.setIdPhieuTra(x.getPhieuTraKey().getId());
            ptView.setMaPhieuTra(x.getPhieuTraKey().getMa());
            ptView.setMaNhaCungCap(x.getPhieuTraKey().getNhaCungCap().getMa());
            if (x.getPhieuTraKey().getNhaCungCap().getTen() != null) {
                ptView.setTenNhaCungCap(x.getPhieuTraKey().getNhaCungCap().getTen());
            }
            ptView.setMaNhanVien(x.getPhieuTraKey().getNhanVien().getMa());
            if (x.getPhieuTraKey().getNhanVien().getHoTen() != null) {
                ptView.setTenNhanVien(x.getPhieuTraKey().getNhanVien().getHoTen());
            }
            if (x.getPhieuTraKey().getNgayTra() != null) {
                ptView.setNgayTra(x.getPhieuTraKey().getNgayTra());
            }
            if (x.getPhieuTraKey().getTrangThai() != null) {
                ptView.setTrangThai(x.getPhieuTraKey().getTrangThai());
            }
            lstView.add(ptView);
        }
        return lstView;
    }

    @Override
    public Set<ChiTietPhieuTraViewModel> getPhieuTraByChiTietPhieuTra(String idPhieuTra) {
        var chiTietPhieuTra = phieuTraRepo.getPhieuTraByChiTietPhieuTra(idPhieuTra);
        var ncc = phieuNhapRepo.getAllChiTietPhieuNhap();
        Set<ChiTietPhieuTraViewModel> chitietView = new HashSet<>();
        for (ChiTietPhieuTra x : chiTietPhieuTra) {
            if (chiTietPhieuTra != null) {
                ChiTietPhieuTraViewModel ctView = new ChiTietPhieuTraViewModel();
                ctView.setIdPhieuTra(x.getPhieuTraKey().getId());
                ctView.setIdNguyenLieu(x.getNguyenLieuKey().getId());
                ctView.setMaNguyenLieu(x.getNguyenLieuKey().getMa());
                ctView.setTenNguyenLieu(x.getNguyenLieuKey().getTen());
                ctView.setSoLuongTra(BigDecimal.valueOf(x.getSoLuongTra()));
                ctView.setDonViTinh(x.getNguyenLieuKey().getDonViTinh());
                for (ChiTietPhieuNhap y : ncc) {
                    ctView.setDonGia(BigDecimal.valueOf(y.getDonGia()));
                }
                ctView.setLyDo(x.getLiDo());
                chitietView.add(ctView);
            }
        }
        return chitietView;
    }



    @Override
    public PhieuTraViewModel getPhieuTraByID(String id) {
        PhieuTraHang pt = phieuTraRepo.getPhieuTraByID(id);
        PhieuTraViewModel ptView = null;
        if (pt != null) {
            ptView = new PhieuTraViewModel();
            ptView.setIdPhieuTra(pt.getId());
        }
        return ptView;
    }

    @Override
    public String insertPhieuTra(String maPT, String idNcc, String idNv, Date ngayTra, int trangThai) {
        return phieuTraRepo.insertPhieuTra(maPT, idNcc, idNv, ngayTra, trangThai);
    }

    @Override
    public String updateSoluongNguyenLieuTra(String idNguyenLieu, float soLuongTra) {
        return phieuTraRepo.updateSoluongNguyenLieuTra(idNguyenLieu, soLuongTra);
    }

    @Override
    public void insertCTPhieuTra(String idPt, String idNL, float soLuongTra, String lyDo) {
        phieuTraRepo.insertCTPhieuTra(idPt, idNL, soLuongTra, lyDo);
    }

    @Override
    public void updatePhieuTra(String idPT, String maPT, String idNCC, String idNV, Date ngayTra) {
        phieuTraRepo.updatePhieuTra(idPT, maPT, idNCC, idNV, ngayTra);
    }

    @Override
    public void deleteChiTietPnbyidPT(String idPT) {
        phieuTraRepo.deleteChiTietPnbyidPT(idPT);
    }

}
