package domainmodel;

import domainmodel.ChiTietPhieuNhap;
import domainmodel.NhaCungCap;
import domainmodel.NhanVien;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-12-09T12:30:15", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(PhieuNhapHang.class)
public class PhieuNhapHang_ { 

    public static volatile SingularAttribute<PhieuNhapHang, String> ma;
    public static volatile SingularAttribute<PhieuNhapHang, NhaCungCap> nhaCungCap;
    public static volatile SingularAttribute<PhieuNhapHang, Integer> trangThai;
    public static volatile SingularAttribute<PhieuNhapHang, NhanVien> nhanVien;
    public static volatile SetAttribute<PhieuNhapHang, ChiTietPhieuNhap> chiTietPhieuNhap;
    public static volatile SingularAttribute<PhieuNhapHang, String> id;
    public static volatile SingularAttribute<PhieuNhapHang, Date> ngayNhap;

}