package viewmodel;

/**
 *
 * @author duong
 */
public class KhachHangView {
    private String id;
    private String ma;
    private String hoTen;
    private String gioiTinh;
    private String sdt;
    private String thanhPho;
    private String quocGia;
    private Integer trangThai;
    private Integer diemTichLuy;

    public KhachHangView() {
    }

    public KhachHangView(String id, String ma, String hoTen, String gioiTinh, String sdt, String thanhPho, String quocGia, Integer trangThai, Integer diemTichLuy) {
        this.id = id;
        this.ma = ma;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.sdt = sdt;
        this.thanhPho = thanhPho;
        this.quocGia = quocGia;
        this.trangThai = trangThai;
        this.diemTichLuy = diemTichLuy;
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

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getThanhPho() {
        return thanhPho;
    }

    public void setThanhPho(String thanhPho) {
        this.thanhPho = thanhPho;
    }

    public String getQuocGia() {
        return quocGia;
    }

    public void setQuocGia(String quocGia) {
        this.quocGia = quocGia;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public Integer getDiemTichLuy() {
        return diemTichLuy;
    }

    public void setDiemTichLuy(Integer diemTichLuy) {
        this.diemTichLuy = diemTichLuy;
    }
    
    public Object[] toDataRow() {
        return new Object[] {id, ma, hoTen, gioiTinh, sdt, thanhPho, quocGia, trangThai == 1?"Còn hoạt động":"Ngừng hoạt động", diemTichLuy};
    }
}
