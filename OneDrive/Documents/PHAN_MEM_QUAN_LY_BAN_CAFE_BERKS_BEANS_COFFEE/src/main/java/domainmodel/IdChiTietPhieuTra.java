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
public class IdChiTietPhieuTra implements Serializable {

    private PhieuTraHang phieuTraKey;
    private NguyenLieu nguyenLieuKey;

    public IdChiTietPhieuTra() {
    }

    public IdChiTietPhieuTra(PhieuTraHang phieuTraKey, NguyenLieu nguyenLieuKey) {
        this.phieuTraKey = phieuTraKey;
        this.nguyenLieuKey = nguyenLieuKey;
    }

    public PhieuTraHang getPhieuTraKey() {
        return phieuTraKey;
    }

    public void setPhieuTraKey(PhieuTraHang phieuTraKey) {
        this.phieuTraKey = phieuTraKey;
    }

    public NguyenLieu getNguyenLieuKey() {
        return nguyenLieuKey;
    }

    public void setNguyenLieuKey(NguyenLieu nguyenLieuKey) {
        this.nguyenLieuKey = nguyenLieuKey;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.phieuTraKey);
        hash = 29 * hash + Objects.hashCode(this.nguyenLieuKey);
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
        final IdChiTietPhieuTra other = (IdChiTietPhieuTra) obj;
        if (!Objects.equals(this.phieuTraKey, other.phieuTraKey)) {
            return false;
        }
        return Objects.equals(this.nguyenLieuKey, other.nguyenLieuKey);
    }

}
