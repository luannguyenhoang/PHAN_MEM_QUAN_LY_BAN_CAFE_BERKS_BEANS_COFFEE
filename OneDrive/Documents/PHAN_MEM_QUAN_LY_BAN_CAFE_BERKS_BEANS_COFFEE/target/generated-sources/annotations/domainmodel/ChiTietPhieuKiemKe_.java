package domainmodel;

import domainmodel.NguyenLieu;
import domainmodel.PhieuKiemKe;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-12-09T12:30:15", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(ChiTietPhieuKiemKe.class)
public class ChiTietPhieuKiemKe_ { 

    public static volatile SingularAttribute<ChiTietPhieuKiemKe, Float> soLuongChenhlech;
    public static volatile SingularAttribute<ChiTietPhieuKiemKe, NguyenLieu> nguyenLieuKey;
    public static volatile SingularAttribute<ChiTietPhieuKiemKe, PhieuKiemKe> kiemKeKey;
    public static volatile SingularAttribute<ChiTietPhieuKiemKe, Float> soLuongThucTe;
    public static volatile SingularAttribute<ChiTietPhieuKiemKe, String> liDo;
    public static volatile SingularAttribute<ChiTietPhieuKiemKe, Float> soLuong;

}