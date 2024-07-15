package service;

import java.util.List;
import viewmodel.ChiNhanhViewModel_Hoang;
import viewmodel.KhuVucViewModel;

public interface IKhuVucService {

    public List<ChiNhanhViewModel_Hoang> getAllChiNhanh();

    public List<KhuVucViewModel> getAllKhuVucByChiNhanh(String idChiNhanh);

    String insertKhuVucToChiNhanh(String maKv, String idChiNhanh);

    void updateKhuVuc(KhuVucViewModel kv, String maKv, Integer TrangThai);

    public void deleteKhuVuc(String idKhuVuc);
}
