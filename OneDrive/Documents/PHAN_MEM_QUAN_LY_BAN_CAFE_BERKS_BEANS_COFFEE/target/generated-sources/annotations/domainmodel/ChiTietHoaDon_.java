package domainmodel;

import domainmodel.HoaDonBanHang;
import domainmodel.SanPham;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-12-09T12:30:15", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(ChiTietHoaDon.class)
public class ChiTietHoaDon_ { 

    public static volatile SingularAttribute<ChiTietHoaDon, HoaDonBanHang> hoaDonKey;
    public static volatile SingularAttribute<ChiTietHoaDon, Integer> soLuongMua;
    public static volatile SingularAttribute<ChiTietHoaDon, SanPham> sanPhamKey;
    public static volatile SingularAttribute<ChiTietHoaDon, BigDecimal> thanhTien;
    public static volatile SingularAttribute<ChiTietHoaDon, BigDecimal> thanhTienSauKm;

}