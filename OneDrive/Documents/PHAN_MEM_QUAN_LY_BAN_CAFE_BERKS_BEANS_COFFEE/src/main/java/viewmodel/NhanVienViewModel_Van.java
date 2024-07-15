package viewmodel;

public class NhanVienViewModel_Van {

    private String idNhanVien;
    private String HoTen;
    private String MaNhanVien;

    public NhanVienViewModel_Van() {
    }

    public NhanVienViewModel_Van(String idNhanVien, String HoTen, String MaNhanVien) {
        this.idNhanVien = idNhanVien;
        this.HoTen = HoTen;
        this.MaNhanVien = MaNhanVien;
    }

    public String getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(String idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public String getMaNhanVien() {
        return MaNhanVien;
    }

    public void setMaNhanVien(String MaNhanVien) {
        this.MaNhanVien = MaNhanVien;
    }

    @Override
    public String toString() {
        return MaNhanVien;
    }
}
