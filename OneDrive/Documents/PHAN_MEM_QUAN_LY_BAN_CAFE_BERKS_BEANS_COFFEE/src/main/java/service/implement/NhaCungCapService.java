package service.implement;

import domainmodel.NhaCungCap;
import java.util.ArrayList;
import java.util.List;
import repository.NhaCungCapRepo;
import service.INhaCungCap;
import viewmodel.NhaCungCapView;

/**
 *
 * @author duong
 */
public class NhaCungCapService implements INhaCungCap {

    NhaCungCapRepo nhaCungCapRepo;

    public NhaCungCapService() {
        nhaCungCapRepo = new NhaCungCapRepo();
    }

    @Override
    public NhaCungCapView getNCCById(String id) {
        return toNCCView(nhaCungCapRepo.getNCCById(id));
    }

    @Override
    public int countNCCByMa(String maNCC) {
        return nhaCungCapRepo.countNCCByMa(maNCC);
    }

    /// READ
    @Override
    public List<NhaCungCapView> getAllNhaCungCap() {
        return toDataView(nhaCungCapRepo.getAllNhaCungcap());
    }

    // Search by ten + ma:
    @Override
    public List<NhaCungCapView> getAllNCCByName(String tenNCC) {
        return toDataView(nhaCungCapRepo.getAllNCCByName(tenNCC));
    }

    @Override
    public List<NhaCungCapView> getAllNCCByMa(String maNCC) {
        return toDataView(nhaCungCapRepo.getAllNCCByMa(maNCC));
    }

    // Filter by trangThai
    @Override
    public List<NhaCungCapView> getAllNCCByTrangThai(int trangThai) {
        return toDataView(nhaCungCapRepo.getAllNCCByTrangThai(trangThai));
    }

    // Search + filter by ten + ma
    @Override
    public List<NhaCungCapView> getAllNCCByTrangThaiAndName(int trangThai, String tenNCC) {
        return toDataView(nhaCungCapRepo.getAllNCCByTrangThaiAndName(trangThai, tenNCC));
    }

    @Override
    public List<NhaCungCapView> getAllNCCByTrangThaiAndMa(int trangThai, String maNCC) {
        return toDataView(nhaCungCapRepo.getAllNCCByTrangThaiAndMa(trangThai, maNCC));
    }

    /// CUD
    @Override
    public String addNhaCungCap(NhaCungCap ncc) {
        if (!isNCCExists(ncc.getMa())) {
            if (nhaCungCapRepo.addNhaCungCap(ncc)) {
                return "Them thanh cong!";
            } else {
                return "Them that bai!";
            }
        } else {
            return "Nha cung cap da ton tai!";
        }
    }

    @Override
    public String updateNhaCungCap(String id, NhaCungCap ncc, boolean check) {
        if (!check) {
            if (!isNCCExists(ncc.getMa())) {
                if (nhaCungCapRepo.updateNhaCungCap(id, ncc)) {
                    return "Cap nhat thanh cong!";
                } else {
                    return "Cap nhat that bai!";
                }
            } else {
                return "\"Ma NCC\" khong the cap nhat vi ton tai NCC co ma nay!";
            }
        } else {
            if (nhaCungCapRepo.updateNhaCungCap(id, ncc)) {
                return "Cap nhat thanh cong!";
            } else {
                return "Cap nhat that bai!";
            }
        }
    }

    @Override
    public String deleteNhaCungCap(String id) {
        if (nhaCungCapRepo.deleteNhaCungCap(id)) {
            return "Xoa thanh cong!";
        } else {
            return "Xoa that bai!";
        }
    }

    @Override
    public String deleteNCC(String id) {
        if (nhaCungCapRepo.deleteNCC(id)) {
            return "Xoa thanh cong!";
        } else {
            return "Xoa that bai!";
        }
    }

    /// LOGIC
    // Cast domain to view
    private List<NhaCungCapView> toDataView(List<NhaCungCap> nhaCungCaps) {
        List<NhaCungCapView> nhaCungCapViews = new ArrayList<>();
        if (!nhaCungCaps.isEmpty()) {
            for (NhaCungCap ncc : nhaCungCaps) {
                NhaCungCapView nccv = toNCCView(ncc);
                nhaCungCapViews.add(nccv);
            }
        }
        return nhaCungCapViews;
    }

    private NhaCungCapView toNCCView(NhaCungCap ncc) {
        if (ncc != null) {
            return new NhaCungCapView(ncc.getId(), ncc.getMa(), ncc.getTen(), ncc.getTrangThai());
        } else {
            return null;
        }
    }

    // Validate data input
    @Override
    public String validateDataInput(Object[] data) {
        String message = "";
        if (((String) data[0]).isBlank()) {
            message += "\"Ma NCC\" khong de trong!\n";
        }else if (((String) data[0]).length() > 15) {
            message += "\"Ma NCC\" khong duoc qua 15 ky tu! Ban dang nhap: " + ((String) data[0]).length() + "ky tu\n";
        }

        if (((String) data[1]).isBlank()) {
            message += "\"Ten NCC\" khong de trong!\n";
        }else if (((String) data[1]).length() > 30) {
            message += "\"Ten NCC\" khong duoc qua 30 ky tu! Ban dang nhap: " + ((String) data[0]).length() + "ky tu\n";
        }

        if (((Integer) data[2]) == 0) {
            message += "\"Trang thai\" phai duoc chon!\n";
        }
        return message;
    }

    // Check exists NCC
    private boolean isNCCExists(String maNCC) {
        return countNCCByMa(maNCC)==1;
    }
}
