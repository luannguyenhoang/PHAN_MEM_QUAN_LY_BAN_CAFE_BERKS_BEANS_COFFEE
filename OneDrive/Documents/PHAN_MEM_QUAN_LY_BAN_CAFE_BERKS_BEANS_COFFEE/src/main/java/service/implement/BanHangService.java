/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.implement;

import domainmodel.Ban;
import domainmodel.ChiNhanh;
import domainmodel.HoaDonBanHang;
import domainmodel.KhachHang;
import domainmodel.KhuVuc;
import domainmodel.KhuyenMai;
import domainmodel.NhanVien;
import domainmodel.SanPham;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import repository.BanHangRepo;
import service.IBanHangService;
import viewmodel.Area;
import viewmodel.ChiNhanhViewModel_Hoang;
import viewmodel.KhuyenMaiDangHoatDong;
import viewmodel.ProductForSale;
import viewmodel.Table;
import viewmodel.ThemKhachViewModel;

/**
 *
 * @author trant
 */
public class BanHangService implements IBanHangService {

    BanHangRepo _BanHangRepo;

    public BanHangService() {
        _BanHangRepo = new BanHangRepo();
    }

    @Override
    public List<ProductForSale> getAllProductForSaleByChiNhanh(String idChiNhanh) {
        List<ProductForSale> listView = new ArrayList<>();
        var product = _BanHangRepo.getAllSanPhamDangBanByChiNhanh(idChiNhanh);
        if (product.size() > 0) {
            for (SanPham sanPham : product) {
                ProductForSale productView = new ProductForSale();
                productView.setIdSp(sanPham.getId());
                productView.setMaSp(sanPham.getMa());
                if (sanPham.getTen() != null) {
                    productView.setTenSp(sanPham.getTen());
                }
                if (sanPham.getGiaBan() != null) {
                    productView.setGiaBan(new BigDecimal(sanPham.getGiaBan()));
                }
                if (sanPham.getTrangThai() != null) {
                    productView.setTrangThai(sanPham.getTrangThai());
                }
                if (sanPham.getKhuyenMai() != null) {
                    productView.setTenKhuyenMai(sanPham.getKhuyenMai().getTen());
                }
                if (sanPham.getAvatar() != null) {
                    productView.setAvatar(sanPham.getAvatar());
                }
                listView.add(productView);
            }
        }
        return listView;
    }

    @Override
    public List<ProductForSale> searchProductForSaleByTenSpAndChiNhanh(String tenSp, String idChiNhanh) {
        List<ProductForSale> listSearch = new ArrayList<>();
        var product = _BanHangRepo.getAllSanPhamDangBanByChiNhanh(idChiNhanh);
        if (product.size() > 0) {
            for (SanPham sanPham : product) {
                if (sanPham.getTen().contains(tenSp)) {
                    ProductForSale productView = new ProductForSale();
                    productView.setIdSp(sanPham.getId());
                    productView.setMaSp(sanPham.getMa());
                    if (sanPham.getTen() != null) {
                        productView.setTenSp(sanPham.getTen());
                    }
                    if (sanPham.getGiaBan() != null) {
                        productView.setGiaBan(new BigDecimal(sanPham.getGiaBan()));
                    }
                    if (sanPham.getTrangThai() != null) {
                        productView.setTrangThai(sanPham.getTrangThai());
                    }
                    if (sanPham.getKhuyenMai() != null) {
                        productView.setTenKhuyenMai(sanPham.getKhuyenMai().getTen());
                    }
                    if (sanPham.getAvatar() != null) {
                        productView.setAvatar(sanPham.getAvatar());
                    }
                    listSearch.add(productView);
                }
            }
        }
        return listSearch;
    }

    @Override
    public boolean checkSo(String soLuong) {
        Pattern checkInt = Pattern.compile("^[0-9]+$");
        if (!checkInt.matcher(soLuong).find()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean checkFormatSdt(String sdt) {
        Pattern checksdt = Pattern.compile("^[0][0-9]{9}$");
        if (!checksdt.matcher(sdt).find()) {
            return false;
        }
        return true;
    }

    @Override
    public List<Area> getAllKhuVucByChiNhanh(String idChiNhanh) {
        List<Area> listView = new ArrayList<>();
        var khuVuc = _BanHangRepo.getAllKhuVucByChiNhanh(idChiNhanh);
        if (khuVuc.size() > 0) {
            for (KhuVuc kv : khuVuc) {
                Area area = new Area();
                area.setIdArea(kv.getId());
                area.setCodeArea(kv.getMa());
                listView.add(area);
            }
        }
        return listView;
    }

    @Override
    public List<Table> getAllBanByKhuVuc(Area area) {
        KhuVuc kv = new KhuVuc();
        kv.setId(area.getIdArea());
        List<Table> listView = new ArrayList<>();
        var listBan = BanHangRepo.getAllBanByKhuVuc(kv);
        if (listBan.size() > 0) {
            for (Ban ban : listBan) {
                Table table = new Table();
                table.setSoBan(ban.getSoBan());
                table.setTrangThaiBan(ban.getTrangThai());
                listView.add(table);
            }
        }
        return listView;
    }

    @Override
    public List<KhuyenMaiDangHoatDong> getAllKhuyenMaiByChiNhanh(String idChiNhanh) {
        List<KhuyenMaiDangHoatDong> listView = new ArrayList<>();
        var khuyenMai = _BanHangRepo.getAllKhuyenMaiByChiNhanh(idChiNhanh);
        if (khuyenMai.size() > 0) {
            for (KhuyenMai km : khuyenMai) {
                KhuyenMaiDangHoatDong kmView = new KhuyenMaiDangHoatDong();
                kmView.setIdKhuyenMai(km.getId());
                if (km.getTen() != null) {
                    kmView.setTenKhuyenMai(km.getTen());
                }
                if (km.getMota() != null) {
                    kmView.setMoTa(km.getMota());
                }
                listView.add(kmView);
            }
        }
        return listView;
    }

    @Override
    public KhuyenMaiDangHoatDong getKhuyenMaibySanPham(String id) {
        KhuyenMaiDangHoatDong kmView = null;
        var khuyenMai = BanHangRepo.getKhuyenMaibySanPham(id);
        if (khuyenMai != null) {
            kmView = new KhuyenMaiDangHoatDong();
            kmView.setIdKhuyenMai(khuyenMai.getId());
            kmView.setTenKhuyenMai(khuyenMai.getTen());
            kmView.setMoTa(khuyenMai.getMota());
            kmView.setGiaTriChietKhau(khuyenMai.getGiaTriChietKhau());
        }
        return kmView;
    }

    @Override
    public String insertKhachHang(ThemKhachViewModel khachView) {
        KhachHang khach = new KhachHang();
        khach.setMa(khachView.getMa());
        if (khachView.getHoTen() != null) {
            khach.setHoTen(khachView.getHoTen());
        }
        if (khachView.getThanhPho() != null) {
            khach.setThanhPho(khachView.getThanhPho());
        }
        if (khachView.getSdt() != null) {
            khach.setSdt(khachView.getSdt());
        }
        khach.setQuocGia(khachView.getQuocGia());
        khach.setGioiTinh(khachView.getGioiTinh());
        khach.setTrangThai(1);
        khach.setDiemTichLuy(0);
        return _BanHangRepo.insertKhachHang(khach);
    }

    @Override
    public boolean checkMaKhach(String maKhach) {
        var khachHang = _BanHangRepo.getKhachHangByMa(maKhach);
        if (khachHang != null) {
            return true;
        }
        return false;
    }

    @Override
    public ThemKhachViewModel getKhachHangBySdt(String sdt) {
        var khachHang = _BanHangRepo.getKhachHangBySdt(sdt);
        ThemKhachViewModel khachView = null;
        if (khachHang != null) {
            khachView = new ThemKhachViewModel();
            khachView.setId(khachHang.getId());
            khachView.setMa(khachHang.getMa());
            khachView.setHoTen(khachHang.getHoTen());
            khachView.setSdt(khachHang.getSdt());
            khachView.setDiemTichLuy(khachHang.getDiemTichLuy());
        }
        return khachView;
    }

    @Override
    public Float getGiaTriDoiDiem() {
        Float giaTriDoiDiem = null;
        if (_BanHangRepo.getOneChiNhanh() != null) {
            giaTriDoiDiem = _BanHangRepo.getOneChiNhanh().getGiaTriDoiDiem();
        }
        return giaTriDoiDiem;
    }

    @Override
    public Float getGiaTriDiem() {
        Float giaTriDiem = null;
        if (_BanHangRepo.getOneChiNhanh() != null) {
            giaTriDiem = _BanHangRepo.getOneChiNhanh().getGiaTriDiem();
        }
        return giaTriDiem;
    }

    @Override
    public String inserHoaDon(String ma, LocalDateTime ngayTao, String idNhanVien, Integer soBan) {
        return _BanHangRepo.inserHoaDon(ma, ngayTao, idNhanVien, soBan);
    }

    @Override
    public void insertChiTietHoaDon(String idSanPham, String idHoaDon, int soLuongMua, BigDecimal thanhTien, BigDecimal thanhTienSauKM) {
        _BanHangRepo.insertChiTietHoaDon(idSanPham, idHoaDon, soLuongMua, thanhTien, thanhTienSauKM);
    }

    @Override
    public String autoGenMaHoaDon() {
        String firstWord = "HD";
        int lastNumber = 0;
        String maHD = null;
        List<Integer> list = new ArrayList<>();
        var listHoaDon = _BanHangRepo.getAllHoaDon();
        if (listHoaDon.size() > 0) {
            for (HoaDonBanHang hoaDonBanHang : listHoaDon) {
                maHD = hoaDonBanHang.getMa();
                list.add(Integer.parseInt(maHD.substring(2)));
            }
            list.sort((o1, o2) -> {
                return o1.compareTo(o2);
            });
            lastNumber = (int) list.get(list.size() - 1);
            lastNumber++;
        }

        return maHD = firstWord + String.valueOf(lastNumber);
    }

    @Override
    public NhanVien getNhanVienbyTaiKhoan(String idTaiKhoan) {
        var nhanVien = _BanHangRepo.getNhanVienbyTaiKhoan(idTaiKhoan);
        if (nhanVien != null) {
            return nhanVien;
        }
        return null;
    }

    @Override
    public void updateDiemKhachHang(String idKhach, Integer diemTichLuy) {
        _BanHangRepo.updateDiemKhachHang(idKhach, diemTichLuy);
    }

    @Override
    public ChiNhanhViewModel_Hoang getChiNhanhbyTaiKhoan(String idTaiKhoan) {
        var chiNhanh = _BanHangRepo.getChiNhanhbyTaiKhoan(idTaiKhoan);
        ChiNhanhViewModel_Hoang cnView = new ChiNhanhViewModel_Hoang();
        cnView.setId(chiNhanh.getId());
        return cnView;
    }

    @Override
    public List<ChiNhanhViewModel_Hoang> getAllChiNhanh() {
        var chiNhanh = _BanHangRepo.getAllChiNhanh();
        List<ChiNhanhViewModel_Hoang> listView = new ArrayList<>();
        for (ChiNhanh cn : chiNhanh) {
            ChiNhanhViewModel_Hoang cnView = new ChiNhanhViewModel_Hoang();
            cnView.setId(cn.getId());
            cnView.setMa(cn.getMa());
            listView.add(cnView);
        }

        return listView;
    }

    @Override
    public void updateTrangThaiBanBySoBan(Integer soBan) {
        _BanHangRepo.updateTrangThaiBanBySoBan(soBan);
    }

    @Override
    public void updateNguyenLieuAfterSellSanPham(String idSanPham, int soLuongMua) {
        _BanHangRepo.updateNguyenLieuAfterSellSanPham(idSanPham, soLuongMua);
    }

    @Override
    public boolean checkDinhLuongPhaChex3(String idSanPham) {
        return _BanHangRepo.checkDinhLuongPhaChex3(idSanPham);
    }

    @Override
    public KhuyenMaiDangHoatDong getKhuyenMaibySanPham2(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
