package domainmodel;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author trant
 */
@Entity
@Table(name = "KHUYENMAI")
public class KhuyenMai implements Serializable {

    @Id
    @GeneratedValue(generator = "GenIdSql")
    @GenericGenerator(name = "GenIdSql", strategy = "guid")
    @Column(name = "Id")
    private String id;

    @Column(name = "Ten")
    private String ten;

    @Column(name = "NgayBatDau")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date ngayBatDau;

    @Column(name = "NgayKetThuc")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date ngayKetThuc;

    @Column(name = "Mota")
    private String mota;

    @Column(name = "GiaTriChietKhau")
    private Float giaTriChietKhau;

    @Column(name = "TrangThai")
    private Integer trangThai;

    public KhuyenMai() {
    }

    public KhuyenMai(String id, String ten, Date ngayBatDau, Date ngayKetThuc, String mota, float giaTriChietKhau, Integer trangThai) {
        this.id = id;
        this.ten = ten;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.mota = mota;
        this.giaTriChietKhau = giaTriChietKhau;
        this.trangThai = trangThai;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
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

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public Float getGiaTriChietKhau() {
        return giaTriChietKhau;
    }

    public void setGiaTriChietKhau(Float giaTriChietKhau) {
        this.giaTriChietKhau = giaTriChietKhau;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

}
