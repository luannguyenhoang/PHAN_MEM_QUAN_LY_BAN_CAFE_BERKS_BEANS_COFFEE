package viewmodel;

import java.util.Date;
//import java.util.UUID;
//import javax.swing.JCheckBox;

/**
 *
 * @author duong
 */
public class KhuyenMaiView {

    private String id;
    private String tenKM;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private Integer hinhThuc;
    private Float chietKhau;
    private Integer trangThai;
    private String moTa;
//    private Set<SanPhamView> sanPhamViews;
    public KhuyenMaiView() {
    }

    public KhuyenMaiView(String id,String tenKM, Date ngayBatDau, Date ngayKetThuc, Float chietKhau, Integer trangThai, String moTa) {
        this.id = id;
        this.tenKM = tenKM;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.chietKhau = chietKhau;
        this.trangThai = trangThai; //1: con han, 0: het han
        this.moTa = moTa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getTenKM() {
        return tenKM;
    }

    public void setTenKM(String tenKM) {
        this.tenKM = tenKM;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public Integer getHinhThuc() {
        return hinhThuc;
    }

    public void setHinhThuc(Integer hinhThuc) {
        this.hinhThuc = hinhThuc;
    }

    public Float getChietKhau() {
        return chietKhau;
    }

    public void setChietKhau(Float chietKhau) {
        this.chietKhau = chietKhau;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    
    public Object[] toDataRow() {
        return new Object[] {id,tenKM, moTa, ngayBatDau, ngayKetThuc, chietKhau, trangThai==1?"Kích hoạt":"Dừng áp dụng"}; //new JCheckBox("", true):new JCheckBox("", false)
    }
}
