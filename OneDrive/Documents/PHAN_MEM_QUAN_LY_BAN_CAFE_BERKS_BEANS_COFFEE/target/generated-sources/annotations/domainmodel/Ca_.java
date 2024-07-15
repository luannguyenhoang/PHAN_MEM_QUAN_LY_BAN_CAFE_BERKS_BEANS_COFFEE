package domainmodel;

import domainmodel.NhanVien;
import java.time.LocalTime;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-12-09T12:30:15", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Ca.class)
public class Ca_ { 

    public static volatile SingularAttribute<Ca, String> ma;
    public static volatile SingularAttribute<Ca, LocalTime> gioBatDau;
    public static volatile SingularAttribute<Ca, Integer> trangThai;
    public static volatile SingularAttribute<Ca, LocalTime> gioKetThuc;
    public static volatile SingularAttribute<Ca, String> id;
    public static volatile SetAttribute<Ca, NhanVien> setNhanVien;

}