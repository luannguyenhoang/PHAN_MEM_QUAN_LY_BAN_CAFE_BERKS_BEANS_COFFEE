package viewmodel;

/**
 *
 * @author duong
 */
public class ChucVuView {
    private String id;
    private String ten;

    public ChucVuView() {
    }

    public ChucVuView(String id, String ten) {
        this.id = id;
        this.ten = ten;
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

    @Override
    public String toString() {
        return ten;
    }
    
    
}
