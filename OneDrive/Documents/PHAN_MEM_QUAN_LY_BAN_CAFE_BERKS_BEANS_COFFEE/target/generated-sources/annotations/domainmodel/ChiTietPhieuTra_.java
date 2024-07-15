package domainmodel;

import domainmodel.NguyenLieu;
import domainmodel.PhieuTraHang;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-12-09T12:30:15", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(ChiTietPhieuTra.class)
public class ChiTietPhieuTra_ { 

    public static volatile SingularAttribute<ChiTietPhieuTra, NguyenLieu> nguyenLieuKey;
    public static volatile SingularAttribute<ChiTietPhieuTra, Float> soLuongTra;
    public static volatile SingularAttribute<ChiTietPhieuTra, String> liDo;
    public static volatile SingularAttribute<ChiTietPhieuTra, PhieuTraHang> phieuTraKey;

}