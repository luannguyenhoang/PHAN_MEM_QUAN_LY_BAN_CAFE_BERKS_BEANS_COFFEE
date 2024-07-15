package domainmodel;

import domainmodel.Ban;
import domainmodel.ChiTietHoaDon;
import domainmodel.NhanVien;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-12-09T12:30:15", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(HoaDonBanHang.class)
public class HoaDonBanHang_ { 

    public static volatile SingularAttribute<HoaDonBanHang, String> ma;
    public static volatile SingularAttribute<HoaDonBanHang, Integer> trangThai;
    public static volatile SingularAttribute<HoaDonBanHang, NhanVien> nhanVien;
    public static volatile SingularAttribute<HoaDonBanHang, String> id;
    public static volatile SingularAttribute<HoaDonBanHang, LocalDateTime> ngayTao;
    public static volatile SingularAttribute<HoaDonBanHang, Ban> ban;
    public static volatile SetAttribute<HoaDonBanHang, ChiTietHoaDon> chiTietHoaDon;

}