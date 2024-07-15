/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domainmodel;

import java.io.Serializable;
import java.util.Objects;

public class IdChiTietHoaDon implements Serializable {

    private SanPham sanPhamKey;
    private HoaDonBanHang hoaDonKey;

    public IdChiTietHoaDon() {
    }

    public IdChiTietHoaDon(SanPham sanPhamKey, HoaDonBanHang hoaDonKey) {
        this.sanPhamKey = sanPhamKey;
        this.hoaDonKey = hoaDonKey;
    }

    public SanPham getSanPhamKey() {
        return sanPhamKey;
    }

    public void setSanPhamKey(SanPham sanPhamKey) {
        this.sanPhamKey = sanPhamKey;
    }

    public HoaDonBanHang getHoaDonKey() {
        return hoaDonKey;
    }

    public void setHoaDonKey(HoaDonBanHang hoaDonKey) {
        this.hoaDonKey = hoaDonKey;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.sanPhamKey);
        hash = 71 * hash + Objects.hashCode(this.hoaDonKey);
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
        final IdChiTietHoaDon other = (IdChiTietHoaDon) obj;
        if (!Objects.equals(this.sanPhamKey, other.sanPhamKey)) {
            return false;
        }
        return Objects.equals(this.hoaDonKey, other.hoaDonKey);
    }

}
