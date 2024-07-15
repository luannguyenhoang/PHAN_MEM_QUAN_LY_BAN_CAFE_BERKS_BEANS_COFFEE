package viewmodel;

import domainmodel.ChiNhanh;
import domainmodel.ChucVu;

/**
 *
 * @author duong
 */
public class NhanVienView {
    private String id;
    private String ma;
    private String hoTen;
    private String gioTinh;
    private String sdt;
    private String thanhPho;
    private String quocGia;
    private Float luong;
    private ChiNhanh chiNhanh;
    private ChucVu chucVu;
    private Integer trangThai;
    private byte[] avatar;
    
    public NhanVienView() {
        
    }

    public NhanVienView(String id, String ma, String hoTen, String gioTinh, String sdt, String thanhPho, String quocGia, Float luong, ChiNhanh chiNhanh, ChucVu chucVu,Integer trangThai, byte[] avatar) {
        this.id = id;
        this.ma = ma;
        this.hoTen = hoTen;
        this.gioTinh = gioTinh;
        this.sdt = sdt;
        this.thanhPho = thanhPho;
        this.quocGia = quocGia;
        this.luong = luong;
        this.chiNhanh = chiNhanh;
        this.chucVu = chucVu;
        this.trangThai = trangThai;
        this.avatar = avatar;
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

    public String getGioTinh() {
        return gioTinh;
    }

    public void setGioTinh(String gioTinh) {
        this.gioTinh = gioTinh;
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

    public Float getLuong() {
        return luong;
    }

    public void setLuong(Float luong) {
        this.luong = luong;
    }

    public ChiNhanh getChiNhanh() {
        return chiNhanh;
    }

    public void setChiNhanh(ChiNhanh chiNhanh) {
        this.chiNhanh = chiNhanh;
    }

    public ChucVu getChucVu() {
        return chucVu;
    }

    public void setChucVu(ChucVu chucVu) {
        this.chucVu = chucVu;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
    
    public Object[] toDataRow() {
        return new Object[] {id, ma, hoTen, gioTinh, sdt, thanhPho, quocGia, luong, chiNhanh.getMa(), chucVu.getTen(), trangThai==1?"Đang làm":"Đã nghỉ"};
    }
}
