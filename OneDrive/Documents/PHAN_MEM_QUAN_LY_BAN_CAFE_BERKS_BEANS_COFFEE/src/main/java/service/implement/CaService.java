/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.implement;

import domainmodel.Ca;
import domainmodel.NhanVien;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import repository.CaRepo;
import service.ICa;
import viewmodel.CaViewModel_Quan;
import viewmodel.NhanVienViewModel_Van;

/**
 *
 * @author PC
 */
public class CaService implements ICa {
    
    private CaRepo _caRepo;
    
    public CaService() {
        _caRepo = new CaRepo();
    }
    
    @Override
    public List<CaViewModel_Quan> getAllCaDangSuDung() {
        List<Ca> listCa = null;
        List<CaViewModel_Quan> listView = new ArrayList<>();
        listCa = _caRepo.getAllCaDangSuDung();
        if (listCa != null) {
            for (Ca ca : listCa) {
                CaViewModel_Quan caView = new CaViewModel_Quan();
                caView.setId(ca.getId());
                caView.setMa(ca.getMa());
                caView.setGioBD(ca.getGioBatDau());
                caView.setGioKT(ca.getGioKetThuc());
                listView.add(caView);
            }
            
        }
        return listView;
    }
    
    @Override
    public List<CaViewModel_Quan> getAllCaDaXoa() {
        List<Ca> listCa = null;
        List<CaViewModel_Quan> listView = new ArrayList<>();
        listCa = _caRepo.getAllCaDaXoa();
        if (listCa != null) {
            for (Ca ca : listCa) {
                CaViewModel_Quan caView = new CaViewModel_Quan();
                caView.setId(ca.getId());
                caView.setMa(ca.getMa());
                caView.setGioBD(ca.getGioBatDau());
                caView.setGioKT(ca.getGioKetThuc());
                listView.add(caView);
            }
            
        }
        return listView;
    }
    
    @Override
    public boolean checkExistedOfMaCa(String maCa) {
        Ca ca = _caRepo.getCabyMa(maCa);
        if (ca != null) {
            return false;
        }
        return true;
    }
    
    @Override
    public String insertCa(CaViewModel_Quan caView) {
        String id = null;
        Ca ca = new Ca();
        ca.setMa(caView.getMa());
        ca.setGioBatDau(caView.getGioBD());
        ca.setGioKetThuc(caView.getGioKT());
        ca.setTrangThai(1);
        id = _caRepo.insertCa(ca);
        return id;
    }
    
    @Override
    public void updateCa(CaViewModel_Quan caView) {
        Ca ca = new Ca();
        ca.setId(caView.getId());
        ca.setMa(caView.getMa());
        ca.setGioBatDau(caView.getGioBD());
        ca.setGioKetThuc(caView.getGioKT());
        ca.setTrangThai(1);
        _caRepo.updateCa(ca);
    }
    
    @Override
    public void changeStateOfCa(String idCa) {
        _caRepo.changeState(idCa);
    }
    
    @Override
    public Set<NhanVienViewModel_Van> getNhanVienByChiNhanh(String idChiNhanh) {
        Set<NhanVienViewModel_Van> listView = new HashSet<>();
        var allNhanVien = _caRepo.getNhanVienByChiNhanh(idChiNhanh);
        if (!allNhanVien.isEmpty()) {
            for (NhanVien nhanVien : allNhanVien) {
                NhanVienViewModel_Van nhanVienView = new NhanVienViewModel_Van();
                nhanVienView.setIdNhanVien(nhanVien.getId());
                nhanVienView.setMaNhanVien(nhanVien.getMa());
                nhanVienView.setHoTen(nhanVien.getHoTen());
                listView.add(nhanVienView);
            }
        }
        return listView;
    }
    
    @Override
    public void addCaToNhanVien(String idNhanVien, Set<String> setIdCa) {
        _caRepo.addCaToNhanVien(idNhanVien, setIdCa);
    }
    
    @Override
    public Set<CaViewModel_Quan> getCaOfNhanVien(String idNhanVien) {
        var allCa = _caRepo.getCaOfNhanVien(idNhanVien);
        Set<CaViewModel_Quan> caViewOfNhanVien = new HashSet<>();
        if (!allCa.isEmpty()) {
            for (Ca ca : allCa) {
                CaViewModel_Quan caView = new CaViewModel_Quan();
                caView.setId(ca.getId());
                caView.setMa(ca.getMa());
                caView.setGioBD(ca.getGioBatDau());
                caView.setGioKT(ca.getGioKetThuc());
                caViewOfNhanVien.add(caView);
            }
        }
        return caViewOfNhanVien;
    }
    
    @Override
    public boolean checkHourOfCa(String hour) {
        Pattern regexTime = Pattern.compile("^[0-9]{1,2}$");
        if (regexTime.matcher(hour).find()) {
            if (Integer.parseInt(hour) >= 0 && Integer.parseInt(hour) < 24) {
                return true;
            }
        }
        return false;
    }
    
    public boolean checkMinuteOfCa(String minute) {
        Pattern regexTime = Pattern.compile("^[0-9]{1,2}$");
        if (regexTime.matcher(minute).find()) {
            if (Integer.parseInt(minute) >= 0 && Integer.parseInt(minute) < 60) {
                return true;
            }
        }
        return false;
    }
}
