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
public class IdChiTietPhieuKiemKe implements Serializable {

    private NguyenLieu nguyenLieuKey;
    private PhieuKiemKe kiemKeKey;

    public IdChiTietPhieuKiemKe() {
    }

    public IdChiTietPhieuKiemKe(NguyenLieu nguyenLieuKey, PhieuKiemKe kiemKeKey) {
        this.nguyenLieuKey = nguyenLieuKey;
        this.kiemKeKey = kiemKeKey;
    }

    public NguyenLieu getNguyenLieuKey() {
        return nguyenLieuKey;
    }

    public void setNguyenLieuKey(NguyenLieu nguyenLieuKey) {
        this.nguyenLieuKey = nguyenLieuKey;
    }

    public PhieuKiemKe getKiemKeKey() {
        return kiemKeKey;
    }

    public void setKiemKeKey(PhieuKiemKe kiemKeKey) {
        this.kiemKeKey = kiemKeKey;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.nguyenLieuKey);
        hash = 97 * hash + Objects.hashCode(this.kiemKeKey);
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
        final IdChiTietPhieuKiemKe other = (IdChiTietPhieuKiemKe) obj;
        if (!Objects.equals(this.nguyenLieuKey, other.nguyenLieuKey)) {
            return false;
        }
        return Objects.equals(this.kiemKeKey, other.kiemKeKey);
    }

}
