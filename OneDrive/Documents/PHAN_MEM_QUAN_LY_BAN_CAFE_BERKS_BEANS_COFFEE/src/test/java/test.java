
import org.junit.Test;
import service.implement.QuenMatKhauSevice;

public class test {
    @Test
    public void naem() {
        System.out.println( new QuenMatKhauSevice().selectById("NguyenHoangLuan").getTenTK());
    }
}
