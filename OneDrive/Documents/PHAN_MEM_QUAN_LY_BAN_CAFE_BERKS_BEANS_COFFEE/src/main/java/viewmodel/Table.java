/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;

/**
 *
 * @author trant
 */
public class Table {

    private Integer soBan;
    private Integer trangThaiBan;

    public Table() {
    }

    public Table(Integer soBan, Integer trangThaiBan) {
        this.soBan = soBan;
        this.trangThaiBan = trangThaiBan;
    }

    public Integer getSoBan() {
        return soBan;
    }

    public void setSoBan(Integer soBan) {
        this.soBan = soBan;
    }

    public Integer getTrangThaiBan() {
        return trangThaiBan;
    }

    public void setTrangThaiBan(Integer trangThaiBan) {
        this.trangThaiBan = trangThaiBan;
    }

}
