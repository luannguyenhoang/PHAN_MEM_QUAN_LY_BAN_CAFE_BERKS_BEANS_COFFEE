package domainmodel;

import domainmodel.HoaDonBanHang;
import domainmodel.KhuVuc;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-12-09T12:30:15", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Ban.class)
public class Ban_ { 

    public static volatile SingularAttribute<Ban, Integer> trangThai;
    public static volatile SetAttribute<Ban, HoaDonBanHang> setHoaDon;
    public static volatile SingularAttribute<Ban, KhuVuc> khuVuc;
    public static volatile SingularAttribute<Ban, String> id;
    public static volatile SingularAttribute<Ban, Integer> soBan;
    public static volatile SingularAttribute<Ban, Integer> trangThaiSuDung;

}