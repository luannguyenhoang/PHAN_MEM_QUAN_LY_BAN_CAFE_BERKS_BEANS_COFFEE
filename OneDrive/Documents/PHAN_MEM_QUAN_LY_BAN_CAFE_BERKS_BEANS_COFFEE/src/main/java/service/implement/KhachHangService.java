package service.implement;

import domainmodel.ChiNhanh;
import domainmodel.KhachHang;
import java.util.ArrayList;
import java.util.List;
import repository.KhachHangRepo;
import service.IKhachHang;
import viewmodel.KhachHangView;

/**
 *
 * @author duong
 */
public class KhachHangService implements IKhachHang {

    KhachHangRepo khachHangRepo;

    public KhachHangService() {
        khachHangRepo = new KhachHangRepo();
    }

    @Override
    public KhachHangView getKHById(String id) {
        return toKhachHangView(khachHangRepo.getKHById(id));
    }

    @Override
    public int countKHByMa(String maKH) {
        return khachHangRepo.countKHByMa(maKH);
    }

    /// READ
    @Override
    public List<KhachHangView> getAllKhachHang() {
        return toDataView(khachHangRepo.getAllKhachHang());
    }

    @Override
    public List<KhachHangView> getAllKHByChiNhanh(ChiNhanh cn) {
        return toDataView(khachHangRepo.getAllKHByChiNhanh(cn));
    }

    // search and filter all
    @Override
    public List<KhachHangView> getAllKHByMa(String maKH) {
        return toDataView(khachHangRepo.getAllKHByMa(maKH));
    }

    @Override
    public List<KhachHangView> getAllKHByName(String tenKH) {
        return toDataView(khachHangRepo.getAllKHByName(tenKH));
    }

    @Override
    public List<KhachHangView> getAllKHBySDT(String sdt) {
        return toDataView(khachHangRepo.getAllKHBySDT(sdt));
    }

    @Override
    public List<KhachHangView> getAllKHByTrangThai(int trangThai) {
        return toDataView(khachHangRepo.getAllKHByTrangThai(trangThai));
    }

    @Override
    public List<KhachHangView> getAllKHByMaAndTrangThai(int trangThai, String maKH) {
        return toDataView(khachHangRepo.getAllKHByMaAndTrangThai(trangThai, maKH));
    }

    @Override
    public List<KhachHangView> getAllKHByNameAndTrangThai(int trangThai, String tenKH) {
        return toDataView(khachHangRepo.getAllKHByNameAndTrangThai(trangThai, tenKH));
    }

    @Override
    public List<KhachHangView> getAllKHBySDTAndTrangThai(int trangThai, String sdt) {
        return toDataView(khachHangRepo.getAllKHBySDTAndTrangThai(trangThai, sdt));
    }

    // search and filter by chi nhanh
    @Override
    public List<KhachHangView> getAllKHByChiNhanhAndMa(ChiNhanh cn, String maKH) {
        return toDataView(khachHangRepo.getAllKHByChiNhanhAndMa(cn, maKH));
    }

    @Override
    public List<KhachHangView> getAllKHByChiNhanhAndName(ChiNhanh cn, String tenKH) {
        return toDataView(khachHangRepo.getAllKHByChiNhanhAndName(cn, tenKH));
    }

    @Override
    public List<KhachHangView> getAllKHByChiNhanhAndSDT(ChiNhanh cn, String sdt) {
        return toDataView(khachHangRepo.getAllKHByChiNhanhAndSDT(cn, sdt));
    }

    @Override
    public List<KhachHangView> getAllKHByChiNhanhAndTrangThai(ChiNhanh cn, int trangThai) {
        return toDataView(khachHangRepo.getAllKHByChiNhanhAndTrangThai(cn, trangThai));
    }

    @Override
    public List<KhachHangView> getAllKHByChiNhanhAndTrangThaiAndMa(ChiNhanh cn, int trangThai, String maKH) {
        return toDataView(khachHangRepo.getAllKHByChiNhanhAndTrangThaiAndMa(cn, trangThai, maKH));
    }

    @Override
    public List<KhachHangView> getAllKHByChiNhanhAndTrangThaiAndName(ChiNhanh cn, int trangThai, String tenKH) {
        return toDataView(khachHangRepo.getAllKHByChiNhanhAndTrangThaiAndName(cn, trangThai, tenKH));
    }

    @Override
    public List<KhachHangView> getAllKHByChiNhanhAndTrangThaiAndSDT(ChiNhanh cn, int trangThai, String sdt) {
        return toDataView(khachHangRepo.getAllKHByChiNhanhAndTrangThaiAndSDT(cn, trangThai, sdt));
    }

    // CUD
    @Override
    public String addKhachHang(KhachHang kh) {
        return null; // ben nay khong co them
    }

    @Override
    public String updateKH(ChiNhanh cn, String id, KhachHang kh) {
        return null; // tim hieu them
    }

    @Override
    public String updateKhachHang(String id, KhachHang kh, boolean check) { // them check de co the update dc ca maKH, neu k thi k dc
        if (!check) {
            if (!isKHExists(kh.getMa())) {
                if (khachHangRepo.updateKhachHang(id, kh)) {
                    return "Cap nhat thanh cong!";
                } else {
                    return "Cap nhat that bai!";
                }
            } else {
                return "\"Ma khach hang\" khong the cap nhat vi ton tai khach hang co ma nay!";
            }
        } else {
            if (khachHangRepo.updateKhachHang(id, kh)) {
                return "Cap nhat thanh cong!";
            } else {
                return "Cap nhat that bai!";
            }
        }
    }

    @Override
    public String deleteKhachHang(String id) { // cap nhat trangThai
        if (khachHangRepo.deleteKhachHang(id)) {
            return "Xoa thanh cong!";
        } else {
            return "Xoa that bai!";
        }
    }

    @Override
    public String deleteKH(String id) { // xoa han
        if (khachHangRepo.deleteKH(id)) {
            return "Xoa thanh cong!";
        } else {
            return "Xoa that bai!";
        }
    }

    /// LOGIC
    //cast domain to view
    private List<KhachHangView> toDataView(List<KhachHang> khachHangs) {
        List<KhachHangView> khachHangViews = new ArrayList<>();
        if (!khachHangs.isEmpty()) {
            for (KhachHang kh : khachHangs) {
                KhachHangView khv = toKhachHangView(kh);
                khachHangViews.add(khv);
            }
        }
        return khachHangViews;
    }

    private KhachHangView toKhachHangView(KhachHang kh) {
        if (kh != null) {
            return new KhachHangView(kh.getId(), kh.getMa(), kh.getHoTen(), kh.getGioiTinh(),
                    kh.getSdt(), kh.getThanhPho(), kh.getQuocGia(), kh.getTrangThai(), kh.getDiemTichLuy());
        } else {
            return null;
        }
    }

    // validate data input
    @Override
    public String validateDataInput(Object[] data) {
        String message = "";

        if (((String) data[0]).isBlank()) {
            message += "\"Ma KH\" khong duoc de trong!\n";
        }else if (((String) data[0]).length() > 5) {
            message += "\"Ma KH\" khong duoc qua 5 ky tu! Ban dang nhap: " + ((String) data[0]).length() + "ky tu\n";
        }

        if (((String) data[1]).isBlank()) {
            message += "\"Ten KH\" khong duoc de trong!\n";
        }else if (((String) data[1]).length() > 30) {
            message += "\"Ten KH\" khong duoc qua 30 ky tu! Ban dang nhap: " + ((String) data[1]).length() + "ky tu\n";
        }

        if (((String) data[2]).isBlank()) {
            message += "\"SDT\" khong duoc de trong!\n";
        } else if (!((String) data[2]).matches("^[0-9]*$")) {
            message += "\"SDT\" khong duoc chua chu va ky tu dac biet!\n";
        } else if (((String) data[2]).length() < 10 || ((String) data[2]).length() > 12) {
            message += "\"SDT\" chi tu 10-12 ky tu! So ban nhap hien co:" + ((String) data[2]).length() + " ky tu\n";
        }

        if (((String) data[3]).isBlank()) {
            message += "\"Thanh pho\" khong duoc de trong!";
        } else if (((String) data[3]).length() > 20) {
            message += "\"Thanh pho\" khong duoc qua 20 ky tu! Ban dang nhap: " + ((String) data[3]).length() + "ky tu\n";
        }

        if (((String) data[4]).isBlank()) {
            message += "\"Quoc gia\" khong duoc de trong!";
        } else if (((String) data[4]).length() > 15) {
            message += "\"Quoc gia\" khong duoc qua 15 ky tu! Ban dang nhap: " + ((String) data[4]).length() + "ky tu\n";
        }

        if (((String) data[5]).isBlank()) {
            message += "\"Diem tich luy\" khong duoc de trong!";
        } else {
            try {
                int diem = Integer.parseInt((String) data[5]);
                if (diem < 0) {
                    message += "\"Diem tich luy\" phai lon hon hoac bang 0!";
                }
            } catch (NumberFormatException e) {
                e.printStackTrace(System.err);
                message += "\"Diem tich luy\" phai la so nguyen!";
            }
        }

        if (((Integer) data[6]) == 0) {
            message += "\"Trang thai\" phai duoc chon!";
        }

        return message;
    }

    // check KH exists
    private boolean isKHExists(String maKH) {// ham nay se loi neu ma KH05 va KH055 thi se bao
        return countKHByMa(maKH) == 1; // true la ton tai, false la khong ton tai
    }

}
