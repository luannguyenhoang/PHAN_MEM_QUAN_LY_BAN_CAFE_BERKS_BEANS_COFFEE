package viewmodel;

public class BanViewModel {

    private String idban;
    private int soban;
    private String makhuvuc;

    public BanViewModel() {
    }

    public BanViewModel(String idban, int soban, String makhuvuc) {
        this.idban = idban;
        this.soban = soban;

        this.makhuvuc = makhuvuc;
    }

    public String getIdban() {
        return idban;
    }

    public void setIdban(String idban) {
        this.idban = idban;
    }

    public int getSoban() {
        return soban;
    }

    public void setSoban(int soban) {
        this.soban = soban;
    }

    public String getMakhuvuc() {
        return makhuvuc;
    }

    public void setMakhuvuc(String makhuvuc) {
        this.makhuvuc = makhuvuc;
    }

    public Object[] getDataBan() {
        return new Object[]{idban, soban, makhuvuc};
    }

    @Override
    public String toString() {
        return makhuvuc;
    }
}
