package domainmodel;

import domainmodel.NguyenLieu;
import domainmodel.PhieuNhapHang;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-12-09T12:30:15", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(ChiTietPhieuNhap.class)
public class ChiTietPhieuNhap_ { 

    public static volatile SingularAttribute<ChiTietPhieuNhap, NguyenLieu> nguyenLieuKey;
    public static volatile SingularAttribute<ChiTietPhieuNhap, Float> soLuongNhap;
    public static volatile SingularAttribute<ChiTietPhieuNhap, PhieuNhapHang> phieuNhapKey;
    public static volatile SingularAttribute<ChiTietPhieuNhap, Float> donGia;

}