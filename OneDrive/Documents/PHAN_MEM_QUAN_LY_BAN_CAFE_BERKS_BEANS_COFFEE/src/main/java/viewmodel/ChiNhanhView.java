package viewmodel;

/**
 *
 * @author duong
 */
public class ChiNhanhView {
    private String id;
    private String ma;

    public ChiNhanhView() {
    }
    
    public ChiNhanhView(String id, String ma) {
        this.id = id;
        this.ma = ma;
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

    @Override
    public String toString() {
        return ma;
    }
    
}
