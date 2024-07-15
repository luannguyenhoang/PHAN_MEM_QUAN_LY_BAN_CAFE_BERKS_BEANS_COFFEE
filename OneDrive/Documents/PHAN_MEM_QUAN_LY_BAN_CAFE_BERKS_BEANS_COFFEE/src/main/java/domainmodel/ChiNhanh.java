package domainmodel;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "CHINHANH")
public class ChiNhanh implements Serializable {

    @Id
    @GeneratedValue(generator = "GenIdSql")
    @GenericGenerator(name = "GenIdSql", strategy = "guid")
    @Column(name = "Id")
    private String id;

    @Column(name = "Ma")
    private String ma;

    @Column(name = "QuocGia")
    private String quocGia;

    @Column(name = "ThanhPho")
    private String thanhPho;

    @Column(name = "NgayKhaiTruong")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date ngayKhaiTruong;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @Column(name = "GiaTriDoiDiem")
    private Float giaTriDoiDiem;

    @Column(name = "GiaTriDiem")
    private Float giaTriDiem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdThuongHieu")
    private ThuongHieu thuongHieu;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "NGUYENLIEUCHINHANH",
            joinColumns = {
                @JoinColumn(name = "IdChiNhanh")},
            inverseJoinColumns = {
                @JoinColumn(name = "IdNguyenLieu")})
    private Set<NguyenLieu> listNguyenLieu;

    @OneToMany(mappedBy = "chiNhanh", fetch = FetchType.LAZY)
    private Set<KhuVuc> setKhuVuc;

    @OneToMany(mappedBy = "chiNhanh", fetch = FetchType.LAZY)
    private Set<NhanVien> setNhanVien;

    public ChiNhanh() {
    }

    public ChiNhanh(String id, String ma, String quocGia, String thanhPho,
            Date ngayKhaiTruong, Integer trangThai, Float giaTriDoiDiem, Float giaTriDiem,
            ThuongHieu thuongHieu, Set<NguyenLieu> listNguyenLieu, Set<KhuVuc> setKhuVuc, Set<NhanVien> setNhanVien) {
        this.id = id;
        this.ma = ma;
        this.quocGia = quocGia;
        this.thanhPho = thanhPho;
        this.ngayKhaiTruong = ngayKhaiTruong;
        this.trangThai = trangThai;
        this.giaTriDoiDiem = giaTriDoiDiem;
        this.giaTriDiem = giaTriDiem;
        this.thuongHieu = thuongHieu;
        this.listNguyenLieu = listNguyenLieu;
        this.setKhuVuc = setKhuVuc;
        this.setNhanVien = setNhanVien;
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

    public String getQuocGia() {
        return quocGia;
    }

    public void setQuocGia(String quocGia) {
        this.quocGia = quocGia;
    }

    public String getThanhPho() {
        return thanhPho;
    }

    public void setThanhPho(String thanhPho) {
        this.thanhPho = thanhPho;
    }

    public Date getNgayKhaiTruong() {
        return ngayKhaiTruong;
    }

    public void setNgayKhaiTruong(Date ngayKhaiTruong) {
        this.ngayKhaiTruong = ngayKhaiTruong;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public Float getGiaTriDoiDiem() {
        return giaTriDoiDiem;
    }

    public void setGiaTriDoiDiem(Float giaTriDoiDiem) {
        this.giaTriDoiDiem = giaTriDoiDiem;
    }

    public Float getGiaTriDiem() {
        return giaTriDiem;
    }

    public void setGiaTriDiem(Float giaTriDiem) {
        this.giaTriDiem = giaTriDiem;
    }

    public ThuongHieu getThuongHieu() {
        return thuongHieu;
    }

    public void setThuongHieu(ThuongHieu thuongHieu) {
        this.thuongHieu = thuongHieu;
    }

    public Set<NguyenLieu> getListNguyenLieu() {
        return listNguyenLieu;
    }

    public void setListNguyenLieu(Set<NguyenLieu> listNguyenLieu) {
        this.listNguyenLieu = listNguyenLieu;
    }

    public Set<KhuVuc> getSetKhuVuc() {
        return setKhuVuc;
    }

    public void setSetKhuVuc(Set<KhuVuc> setKhuVuc) {
        this.setKhuVuc = setKhuVuc;
    }

    public Set<NhanVien> getSetNhanVien() {
        return setNhanVien;
    }

    public void setSetNhanVien(Set<NhanVien> setNhanVien) {
        this.setNhanVien = setNhanVien;
    }

}
