package service;

import java.util.List;
import viewmodel.BanViewModel;
import viewmodel.ChiNhanhViewModel_Hoang;
import viewmodel.KhuVucViewModel;

public interface IBanService {

    public List<BanViewModel> getAllBanByKhuVuc(String idKhuVuc);

    String insertBan(Integer SoBan, KhuVucViewModel kvView);

    public void deleteBan(String idBan);

    ChiNhanhViewModel_Hoang getChiNhanhByTaiKhoan(String idTaiKhoan);
}
