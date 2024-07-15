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
public class IdChiTietPhieuNhap implements Serializable {

    private PhieuNhapHang phieuNhapKey;
    private NguyenLieu nguyenLieuKey;

    public IdChiTietPhieuNhap() {
    }

    public IdChiTietPhieuNhap(PhieuNhapHang phieuNhapKey, NguyenLieu nguyenLieuKey) {
        this.phieuNhapKey = phieuNhapKey;
        this.nguyenLieuKey = nguyenLieuKey;
    }

    public PhieuNhapHang getPhieuNhapKey() {
        return phieuNhapKey;
    }

    public void setPhieuNhapKey(PhieuNhapHang phieuNhapKey) {
        this.phieuNhapKey = phieuNhapKey;
    }

    public NguyenLieu getNguyenLieuKey() {
        return nguyenLieuKey;
    }

    public void setNguyenLieuKey(NguyenLieu nguyenLieuKey) {
        this.nguyenLieuKey = nguyenLieuKey;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.phieuNhapKey);
        hash = 47 * hash + Objects.hashCode(this.nguyenLieuKey);
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
        final IdChiTietPhieuNhap other = (IdChiTietPhieuNhap) obj;
        if (!Objects.equals(this.phieuNhapKey, other.phieuNhapKey)) {
            return false;
        }
        return Objects.equals(this.nguyenLieuKey, other.nguyenLieuKey);
    }

}
