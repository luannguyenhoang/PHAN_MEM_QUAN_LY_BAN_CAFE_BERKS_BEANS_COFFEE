package domainmodel;

import domainmodel.ChiTietPhieuKiemKe;
import domainmodel.NhanVien;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-12-09T12:30:15", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(PhieuKiemKe.class)
public class PhieuKiemKe_ { 

    public static volatile SingularAttribute<PhieuKiemKe, String> ma;
    public static volatile SingularAttribute<PhieuKiemKe, Integer> trangThai;
    public static volatile SingularAttribute<PhieuKiemKe, NhanVien> nhanVien;
    public static volatile SetAttribute<PhieuKiemKe, ChiTietPhieuKiemKe> chiTietphieuKiem;
    public static volatile SingularAttribute<PhieuKiemKe, String> id;
    public static volatile SingularAttribute<PhieuKiemKe, Date> ngayKiemKe;

}