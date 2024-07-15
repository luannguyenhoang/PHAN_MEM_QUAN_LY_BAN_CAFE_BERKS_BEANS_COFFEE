/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domainmodel;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "KHUVUC")
public class KhuVuc implements Serializable {

    @Id
    @GeneratedValue(generator = "GenIdSql")
    @GenericGenerator(name = "GenIdSql", strategy = "guid")
    @Column(name = "Id")
    private String id;
    @Column(name = "Ma")
    private String ma;
    @Column(name = "TrangThai")
    private Integer trangThai;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdChiNhanh")
    private ChiNhanh chiNhanh;
    @OneToMany(mappedBy = "khuVuc", fetch = FetchType.EAGER)
    private Set<Ban> listBan;

    public KhuVuc() {
    }

    public KhuVuc(String id, String ma, Integer trangThai, ChiNhanh chiNhanh, Set<Ban> listBan) {
        this.id = id;
        this.ma = ma;
        this.trangThai = trangThai;
        this.chiNhanh = chiNhanh;
        this.listBan = listBan;
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

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public ChiNhanh getChiNhanh() {
        return chiNhanh;
    }

    public void setChiNhanh(ChiNhanh chiNhanh) {
        this.chiNhanh = chiNhanh;
    }

    public Set<Ban> getListBan() {
        return listBan;
    }

    public void setListBan(Set<Ban> listBan) {
        this.listBan = listBan;
    }

}
