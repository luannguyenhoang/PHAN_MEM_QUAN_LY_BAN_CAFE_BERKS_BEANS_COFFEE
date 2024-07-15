package viewmodel;

public class KhuyenMaiDangHoatDong {

    private String idKhuyenMai;
    private String tenKhuyenMai;
    private String moTa;
    private Float giaTriChietKhau;

    public KhuyenMaiDangHoatDong() {
    }

    public KhuyenMaiDangHoatDong(String idKhuyenMai, String tenKhuyenMai, String moTa, Float giaTriChietKhau) {
        this.idKhuyenMai = idKhuyenMai;
        this.tenKhuyenMai = tenKhuyenMai;
        this.moTa = moTa;
        this.giaTriChietKhau = giaTriChietKhau;
    }

    public String getIdKhuyenMai() {
        return idKhuyenMai;
    }

    public void setIdKhuyenMai(String idKhuyenMai) {
        this.idKhuyenMai = idKhuyenMai;
    }

    public String getTenKhuyenMai() {
        return tenKhuyenMai;
    }

    public void setTenKhuyenMai(String tenKhuyenMai) {
        this.tenKhuyenMai = tenKhuyenMai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Float getGiaTriChietKhau() {
        return giaTriChietKhau;
    }

    public void setGiaTriChietKhau(Float giaTriChietKhau) {
        this.giaTriChietKhau = giaTriChietKhau;
    }

    @Override
    public String toString() {
        return tenKhuyenMai;
    }

}
