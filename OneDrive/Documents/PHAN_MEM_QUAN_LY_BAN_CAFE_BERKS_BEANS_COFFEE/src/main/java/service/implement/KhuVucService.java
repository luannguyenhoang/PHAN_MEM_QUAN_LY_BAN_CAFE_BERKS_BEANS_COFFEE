package service.implement;

import domainmodel.ChiNhanh;
import domainmodel.KhuVuc;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import repository.KhuVucRepository;
import service.IKhuVucService;
import viewmodel.ChiNhanhViewModel_Hoang;
import viewmodel.KhuVucViewModel;

public class KhuVucService implements IKhuVucService {

    KhuVucRepository khuVucRepository;

    public KhuVucService() {
        khuVucRepository = new KhuVucRepository();

    }

    @Override
    public String insertKhuVucToChiNhanh(String maKv, String idChiNhanh) {
        return khuVucRepository.insertKhuVucToChiNhanh(maKv, idChiNhanh);
    }

    @Override
    public void updateKhuVuc(KhuVucViewModel kvView, String maKv, Integer TrangThai) {
        KhuVuc kv = khuVucRepository.getKhuVucFromID(kvView.getIdKhuVuc());
        khuVucRepository.updateKhuVuc(kv, maKv, TrangThai);
    }

    @Override
    public void deleteKhuVuc(String idKhuVuc) {
        khuVucRepository.deleteKhuVuc(idKhuVuc);
    }

    @Override
    public List<KhuVucViewModel> getAllKhuVucByChiNhanh(String idChiNhanh) {
        var allKhuVuc = khuVucRepository.getAllKhuVucByChiNhanh(idChiNhanh);
        List<KhuVucViewModel> listView = new ArrayList<>();
        for (KhuVuc kv : allKhuVuc) {
            listView.add(new KhuVucViewModel(kv.getId(), kv.getMa(), kv.getTrangThai()));
        }
        return listView;
    }

    @Override
    public List<ChiNhanhViewModel_Hoang> getAllChiNhanh() {
        var allChiNhanh = khuVucRepository.getAllChiNhanh();
        List<ChiNhanhViewModel_Hoang> listView = new ArrayList<>();
        for (ChiNhanh cn : allChiNhanh) {
            listView.add(new ChiNhanhViewModel_Hoang(cn.getId(), cn.getMa()));
        }
        return listView;
    }

}
