/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;

import java.time.LocalTime;

/**
 *
 * @author PC
 */
public class CaViewModel_Quan {

    private String id;
    private String ma;
    private LocalTime gioBatDau;
    private LocalTime gioKetThuc;

    public CaViewModel_Quan() {
    }

    public CaViewModel_Quan(String id, String ma, LocalTime gioBD, LocalTime gioKT) {
        this.id = id;
        this.ma = ma;
        this.gioBatDau = gioBD;
        this.gioKetThuc = gioKT;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public LocalTime getGioBD() {
        return gioBatDau;
    }

    public void setGioBD(LocalTime gioBD) {
        this.gioBatDau = gioBD;
    }

    public LocalTime getGioKT() {
        return gioKetThuc;
    }

    public void setGioKT(LocalTime gioKT) {
        this.gioKetThuc = gioKT;
    }

    public Object[] toDataRow() {
        return new Object[]{id, ma, gioBatDau, gioKetThuc};
    }
}
