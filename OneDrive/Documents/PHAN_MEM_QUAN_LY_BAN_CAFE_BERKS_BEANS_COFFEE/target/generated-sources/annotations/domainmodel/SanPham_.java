package domainmodel;

import domainmodel.ChiTietHoaDon;
import domainmodel.ChiTietSP;
import domainmodel.KhuyenMai;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-12-09T12:30:15", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(SanPham.class)
public class SanPham_ { 

    public static volatile SingularAttribute<SanPham, KhuyenMai> khuyenMai;
    public static volatile SingularAttribute<SanPham, String> ma;
    public static volatile SingularAttribute<SanPham, Integer> trangThai;
    public static volatile SingularAttribute<SanPham, Float> giaBan;
    public static volatile SetAttribute<SanPham, ChiTietSP> chiTietSp;
    public static volatile SingularAttribute<SanPham, String> id;
    public static volatile SingularAttribute<SanPham, byte[]> avatar;
    public static volatile SingularAttribute<SanPham, String> ten;
    public static volatile SetAttribute<SanPham, ChiTietHoaDon> chiTietHoaDon;

}