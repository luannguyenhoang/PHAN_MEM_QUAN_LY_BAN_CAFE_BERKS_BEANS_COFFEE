package viewmodel;

import java.math.BigDecimal;

public class SanPhamViewModel {

    private String idSp;
    private String maSp;
    private String tenSp;
    private BigDecimal giaBan;
    private byte[] avatar;

    public SanPhamViewModel() {
    }

    public SanPhamViewModel(String idSp, String maSp, String tenSp, BigDecimal giaBan, byte[] avatar) {
        this.idSp = idSp;
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.giaBan = giaBan;
        this.avatar = avatar;
    }

    public String getIdSp() {
        return idSp;
    }

    public void setIdSp(String idSp) {
        this.idSp = idSp;
    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

}
