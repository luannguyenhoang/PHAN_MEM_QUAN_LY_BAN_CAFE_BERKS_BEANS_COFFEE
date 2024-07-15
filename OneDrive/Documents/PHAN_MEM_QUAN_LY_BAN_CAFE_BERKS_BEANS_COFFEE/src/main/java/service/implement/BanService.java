package service.implement;

import domainmodel.Ban;
import domainmodel.KhuVuc;
import java.util.ArrayList;
import java.util.List;
import repository.BanRepository;
import repository.KhuVucRepository;
import service.IBanService;
import viewmodel.BanViewModel;
import viewmodel.ChiNhanhViewModel_Hoang;
import viewmodel.KhuVucViewModel;

public class BanService implements IBanService {

    BanRepository banRepository;
    KhuVucRepository khuVucRepository;

    public BanService() {
        banRepository = new BanRepository();
        khuVucRepository = new KhuVucRepository();

    }

    @Override
    public List<BanViewModel> getAllBanByKhuVuc(String idKhuVuc) {
        var allBan = banRepository.getAllBanByKhuVuc(idKhuVuc);
        List<BanViewModel> listView = new ArrayList<>();
        if (allBan != null) {
            for (Ban ban : allBan) {
                BanViewModel banView = new BanViewModel();
                if (ban.getTrangThaiSuDung() == 1) {
                    if (ban.getKhuVuc() != null) {
                        banView.setMakhuvuc(ban.getKhuVuc().getMa());
                    } else {
                        banView.setMakhuvuc("chưa có thông tin");
                    }
                    if (ban.getId() != null) {
                        banView.setIdban(ban.getId());
                    } else {
                        banView.setIdban("chưa có thông tin");
                    }
                    listView.add(new BanViewModel(ban.getId(), ban.getSoBan(), ban.getKhuVuc().getMa()));
                }

            }
        }

        return listView;
    }

    @Override
    public String insertBan(Integer SoBan, KhuVucViewModel kvView) {
        KhuVuc kv = khuVucRepository.getKhuVucFromID(kvView.getIdKhuVuc());
        return banRepository.insertBan(SoBan, kv);
    }

    @Override
    public void deleteBan(String idBan) {
        banRepository.deleteBan(idBan);
    }

    @Override
    public ChiNhanhViewModel_Hoang getChiNhanhByTaiKhoan(String idTaiKhoan) {
        var chiNhanh = banRepository.getChiNhanhByTaiKhoan(idTaiKhoan);
        ChiNhanhViewModel_Hoang cnView = new ChiNhanhViewModel_Hoang();
        cnView.setId(chiNhanh.getId());
        cnView.setMa(chiNhanh.getMa());
        return cnView;
    }

}
