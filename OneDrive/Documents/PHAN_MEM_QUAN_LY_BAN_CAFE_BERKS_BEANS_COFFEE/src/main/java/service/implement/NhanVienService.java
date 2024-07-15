package service.implement;

import domainmodel.ChiNhanh;
import domainmodel.ChucVu;
import domainmodel.NhanVien;
import java.util.ArrayList;
import java.util.List;
import repository.NhanVienRepo;
import service.INhanVien;
import viewmodel.ChucVuView;
import viewmodel.NhanVienView;

/**
 *
 * @author duong
 */
public class NhanVienService implements INhanVien {

    NhanVienRepo nhanVienRepo;

    public NhanVienService() {
        nhanVienRepo = new NhanVienRepo();
    }

    @Override
    public NhanVienView getNhanVienById(String id) {
        return toNhanVienView(nhanVienRepo.getNhanVienById(id));
    }

    @Override
    public int countNVByMa(String maNV) {
        return nhanVienRepo.countNVByMa(maNV);
    }

    @Override
    public List<NhanVienView> getAllNhanVien() {
        return toDataView(nhanVienRepo.getAllNhanVien());
    }

    @Override
    public List<NhanVienView> getAllNVByChiNhanh(ChiNhanh cn) {
        return toDataView(nhanVienRepo.getAllNVByChiNhanh(cn));
    }

    // Filter and searh all
    @Override
    public List<NhanVienView> getAllNVByMa(String maNV) {
        return toDataView(nhanVienRepo.getAllNVByMa(maNV));
    }

    @Override
    public List<NhanVienView> getAllNVByName(String tenNV) {
        return toDataView(nhanVienRepo.getAllNVByName(tenNV));
    }

    @Override
    public List<NhanVienView> getAllNVBySDT(String sdt) {
        return toDataView(nhanVienRepo.getAllNVBySDT(sdt));
    }

    @Override
    public List<NhanVienView> getAllNVByTrangThai(int trangThai) {
        return toDataView(nhanVienRepo.getAllNVByTrangThai(trangThai));
    }

    @Override
    public List<NhanVienView> getAllNVByMaAndTrangThai(int trangThai, String maNV) {
        return toDataView(nhanVienRepo.getAllNVByMaAndTrangThai(trangThai, maNV));
    }

    @Override
    public List<NhanVienView> getAllNVByNameAndTrangThai(int trangThai, String tenNV) {
        return toDataView(nhanVienRepo.getAllNVByNameAndTrangThai(trangThai, tenNV));
    }

    @Override
    public List<NhanVienView> getAllNVBySDTAndTrangThai(int trangThai, String sdt) {
        return toDataView(nhanVienRepo.getAllNVBySDTAndTrangThai(trangThai, sdt));
    }

    // Filter and search by chi nhanh
    @Override
    public List<NhanVienView> getAllNVByChiNhanhAndMa(ChiNhanh cn, String maNV) {
        return toDataView(nhanVienRepo.getAllNVByChiNhanhAndMa(cn, maNV));
    }

    @Override
    public List<NhanVienView> getAllNVByChiNhanhAndName(ChiNhanh cn, String tenNV) {
        return toDataView(nhanVienRepo.getAllNVByChiNhanhAndName(cn, tenNV));
    }

    @Override
    public List<NhanVienView> getAllNVByChiNhanhAndSDT(ChiNhanh cn, String sdt) {
        return toDataView(nhanVienRepo.getAllNVByChiNhanhAndSDT(cn, sdt));
    }

    @Override
    public List<NhanVienView> getAllNVByChiNhanhAndTrangThai(ChiNhanh cn, int trangThai) {
        return toDataView(nhanVienRepo.getAllNVByChiNhanhAndTrangThai(cn, trangThai));
    }

    @Override
    public List<NhanVienView> getAllNVByChiNhanhAndTrangThaiAndMa(ChiNhanh cn, int trangThai, String maNV) {
        return toDataView(nhanVienRepo.getAllNVByChiNhanhAndTrangThaiAndMa(cn, trangThai, maNV));
    }

    @Override
    public List<NhanVienView> getAllNVByChiNhanhAndTrangThaiAndName(ChiNhanh cn, int trangThai, String tenNV) {
        return toDataView(nhanVienRepo.getAllNVByChiNhanhAndTrangThaiAndName(cn, trangThai, tenNV));
    }

    @Override
    public List<NhanVienView> getAllNVByChiNhanhAndTrangThaiAndSDT(ChiNhanh cn, int trangThai, String sdt) {
        return toDataView(nhanVienRepo.getAllNVByChiNhanhAndTrangThaiAndSDT(cn, trangThai, sdt));
    }

    //CUD
    @Override
    public String addNhanVien(NhanVien nv) { // phan nay ben ban hang
        if (!isNVExists(nv.getMa())) {
            if (nhanVienRepo.addNhanVien(nv)) {
                return "Them thanh cong!";
            } else {
                return "Them that bai!";
            }
        } else {
            return "Nhan vien da ton tai!";
        }
    }

    @Override
    public String updateNV(ChiNhanh cn, String id, NhanVien nv) { // sua dc chi nhanh
        return null;
    }

    @Override
    public String updateNhanVien(String id, NhanVien nv, boolean check) { // chi sua thong tin co ban, khong sua chi nhanh
        if (!check) {
            if (!isNVExists(nv.getMa())) {
                if (nhanVienRepo.updateNhanVien(id, nv)) {
                    return "Cap nhat thanh cong!";
                } else {
                    return "Cap nhat that bai!";
                }
            } else {
                return "\"Ma nhan vien\" khong the cap nhat vi ton tai nhan vien co ma nay!";
            }
        } else {
            if (nhanVienRepo.updateNhanVien(id, nv)) {
                return "Cap nhat thanh cong!";
            } else {
                return "Cap nhat that bai!";
            }
        }
    }

    @Override
    public String updateNhanVienByAdmin(String id, NhanVien nv, boolean check) {
        if (!check) {
            if (!isNVExists(nv.getMa())) {
                if (nhanVienRepo.updateNhanVienByAdmin(id, nv)) {
                    return "Cap nhat thanh cong!";
                } else {
                    return "Cap nhat that bai!";
                }
            } else {
                return "\"Ma nhan vien\" khong the cap nhat vi ton tai nhan vien co ma nay!";
            }
        } else {
            if (nhanVienRepo.updateNhanVienByAdmin(id, nv)) { // that ra la sai dong nay =)), quen chua doi lai ham khi copy
                return "Cap nhat thanh cong!";
            } else {
                return "Cap nhat that bai!";
            }
        }
    }

//    String updateChiNhanhNhanVien(String id, ChiNhanh cn, boolean check); // chi sua chi nhanh
    @Override
    public String deleteNhanVien(String id) { // xoa trang thai
        if (nhanVienRepo.deleteNhanVien(id)) {
            return "Xoa thanh cong!";
        } else {
            return "Xoa that bai!";
        }
    }

    @Override
    public String deleteNV(String id) { // xoa han
        return null;
    }

    // LOGIC
    // cast domain to view
    private List<NhanVienView> toDataView(List<NhanVien> nhanViens) {
        List<NhanVienView> nhanVienViews = new ArrayList<>();
        if (!nhanViens.isEmpty()) {
            for (NhanVien nv : nhanViens) {
                NhanVienView nvv = toNhanVienView(nv);
                nhanVienViews.add(nvv);
            }
        }
        return nhanVienViews;
    }

    private NhanVienView toNhanVienView(NhanVien nv) {
        if (nv != null) {
            return new NhanVienView(nv.getId(), nv.getMa(), nv.getHoTen(), nv.getGioiTinh(), nv.getSdt(),
                    nv.getThanhPho(), nv.getQuocGia(), nv.getLuong(), nv.getChiNhanh(), nv.getChucVu(), nv.getTrangThai(), nv.getAvatar());
        } else {
            return null;
        }
    }

    // validate data input
    @Override
    public String validateDataInput(Object[] data) {
        String message = "";

        if (((String) data[0]).isBlank()) {
            message += "\"Ma NV\" khong duoc de trong!\n";
        } else if (((String) data[0]).length() > 5) {
            message += "\"Ma NV\" khong duoc qua 5 ky tu! Ban dang nhap: " + ((String) data[0]).length() + "ky tu\n";
        }

        if (((String) data[1]).isBlank()) {
            message += "\"Ten NV\" khong duoc de trong!\n";
        }

        if (((String) data[2]).isBlank()) {
            message += "\"SDT\" khong duoc de trong!\n";
        } else if (!((String) data[2]).matches("^[0-9]*$")) {
            message += "\"SDT\" khong duoc chua chu va ky tu dac biet!\n";
        } else if (((String) data[2]).length() < 10 || ((String) data[2]).length() > 12) {
            message += "\"SDT\" chi tu 10-12 ky tu! So ban nhap hien co:" + ((String) data[2]).length() + " ky tu\n";
        }

        if (((String) data[3]).isBlank()) {
            message += "\"Thanh pho\" khong duoc de trong!\n";
        } else if (((String) data[3]).length() > 20) {
            message += "\"Thanh pho\" khong duoc qua 20 ky tu! Ban dang nhap: " + ((String) data[3]).length() + "ky tu\n";
        }

        if (((String) data[4]).isBlank()) {
            message += "\"Quoc gia\" khong duoc de trong!\n";
        } else if (((String) data[4]).length() > 15) {
            message += "\"Quoc gia\" khong duoc qua 15 ky tu! Ban dang nhap: " + ((String) data[4]).length() + "ky tu\n";
        }

        if (((String) data[5]).isBlank()) {
            message += "\"Tien luong\" khong duoc de trong!\n";
        } else {
            try {
                float diem = Float.parseFloat((String) data[5]);
                if (diem < 0) {
                    message += "\"Tien luong\" phai lon hon hoac bang 0!\n";
                }
            } catch (NumberFormatException e) {
                e.printStackTrace(System.err);
                message += "\"Tien luong\" phai la so, khong chua chu hoac ky tu dac biet!\n";
            }
        }

        if (((Integer) data[6]) == 0) {
            message += "\"Trang thai\" phai duoc chon!\n";
        }

        return message;
    }

    // check exists nhan vien
    private boolean isNVExists(String maNV) {
        return countNVByMa(maNV) == 1;
    }

    /// Repo Chuc Vu
    @Override
    public List<ChucVuView> getAllChucVu() {
        return toDataChucVuView(nhanVienRepo.getAllChucVu());
    }

    @Override
    public ChucVu getChucVuById(String id) {
        return nhanVienRepo.getChucVuById(id);
    }

    // cast domain to view
    private List<ChucVuView> toDataChucVuView(List<ChucVu> chucVus) {
        List<ChucVuView> chucVuViews = new ArrayList<>();
        if (!chucVus.isEmpty()) {
            for (ChucVu cv : chucVus) {
                ChucVuView cvv = toChucVuView(cv);
                chucVuViews.add(cvv);
            }
        }
        return chucVuViews;
    }

    private ChucVuView toChucVuView(ChucVu cv) {
        return new ChucVuView(cv.getId(), cv.getTen());
    }

}
