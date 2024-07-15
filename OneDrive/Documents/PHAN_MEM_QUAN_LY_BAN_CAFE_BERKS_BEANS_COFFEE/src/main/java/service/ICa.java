/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import java.util.Set;
import viewmodel.CaViewModel_Quan;
import viewmodel.NhanVienViewModel_Van;

/**
 *
 * @author PC
 */
public interface ICa {

    List<CaViewModel_Quan> getAllCaDangSuDung();

    List<CaViewModel_Quan> getAllCaDaXoa();

    boolean checkExistedOfMaCa(String maCa);

    String insertCa(CaViewModel_Quan ca);

    void updateCa(CaViewModel_Quan ca);

    void changeStateOfCa(String idCa);

    boolean checkHourOfCa(String hour);

    boolean checkMinuteOfCa(String minute);

    Set<NhanVienViewModel_Van> getNhanVienByChiNhanh(String idChiNhanh);

    void addCaToNhanVien(String idNhanVien, Set<String> setIdCa);

    Set<CaViewModel_Quan> getCaOfNhanVien(String idNhanVien);
}
