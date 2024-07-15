/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domainmodel;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author trant
 */
public class IdChiTietSP implements Serializable {

    private SanPham sanPhamKey;
    private NguyenLieu nguyenLieukey;

    public IdChiTietSP() {
    }

    public IdChiTietSP(SanPham sanPhamKey, NguyenLieu nguyenLieukey) {
        this.sanPhamKey = sanPhamKey;
        this.nguyenLieukey = nguyenLieukey;
    }

    public SanPham getSanPhamKey() {
        return sanPhamKey;
    }

    public void setSanPhamKey(SanPham sanPhamKey) {
        this.sanPhamKey = sanPhamKey;
    }

    public NguyenLieu getNguyenLieukey() {
        return nguyenLieukey;
    }

    public void setNguyenLieukey(NguyenLieu nguyenLieukey) {
        this.nguyenLieukey = nguyenLieukey;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.sanPhamKey);
        hash = 97 * hash + Objects.hashCode(this.nguyenLieukey);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IdChiTietSP other = (IdChiTietSP) obj;
        if (!Objects.equals(this.sanPhamKey, other.sanPhamKey)) {
            return false;
        }
        return Objects.equals(this.nguyenLieukey, other.nguyenLieukey);
    }

}
