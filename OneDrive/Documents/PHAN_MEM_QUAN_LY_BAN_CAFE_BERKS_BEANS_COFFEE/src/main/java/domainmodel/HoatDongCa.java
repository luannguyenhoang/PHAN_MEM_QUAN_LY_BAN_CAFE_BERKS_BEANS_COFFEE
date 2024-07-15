/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domainmodel;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author trant
 */
@Entity
@Table(name = "HOATDONGCA")
public class HoatDongCa implements Serializable {

    @Id
    @GeneratedValue(generator = "GenIdSql")
    @GenericGenerator(name = "GenIdSql", strategy = "guid")
    @Column(name = "Id")
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdCa")
    private Ca ca;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdChiNhanh")
    private ChiNhanh chiNhanh;

    @Column(name = "GioMoCa")
    private LocalDateTime gioMoCa;

    @Column(name = "GioDongCa")
    private LocalDateTime gioDongCa;

    @Column(name = "TienDauCa")
    private Float tienDauCa;

    @Column(name = "TienCuoiCa")
    private Float tienCuoiCa;

    public HoatDongCa() {
    }

    public HoatDongCa(String id, Ca ca, ChiNhanh chiNhanh, LocalDateTime gioMoCa, LocalDateTime gioDongCa, Float tienDauCa, Float tienCuoiCa) {
        this.id = id;
        this.ca = ca;
        this.chiNhanh = chiNhanh;
        this.gioMoCa = gioMoCa;
        this.gioDongCa = gioDongCa;
        this.tienDauCa = tienDauCa;
        this.tienCuoiCa = tienCuoiCa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Ca getCa() {
        return ca;
    }

    public void setCa(Ca ca) {
        this.ca = ca;
    }

    public LocalDateTime getGioMoCa() {
        return gioMoCa;
    }

    public void setGioMoCa(LocalDateTime gioMoCa) {
        this.gioMoCa = gioMoCa;
    }

    public LocalDateTime getGioDongCa() {
        return gioDongCa;
    }

    public void setGioDongCa(LocalDateTime gioDongCa) {
        this.gioDongCa = gioDongCa;
    }

    public Float getTienDauCa() {
        return tienDauCa;
    }

    public void setTienDauCa(Float tienDauCa) {
        this.tienDauCa = tienDauCa;
    }

    public Float getTienCuoiCa() {
        return tienCuoiCa;
    }

    public void setTienCuoiCa(Float tienCuoiCa) {
        this.tienCuoiCa = tienCuoiCa;
    }

    public ChiNhanh getChiNhanh() {
        return chiNhanh;
    }

    public void setChiNhanh(ChiNhanh chiNhanh) {
        this.chiNhanh = chiNhanh;
    }

}
