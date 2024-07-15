package domainmodel;

import domainmodel.Ca;
import domainmodel.ChiNhanh;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-12-09T12:30:15", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(HoatDongCa.class)
public class HoatDongCa_ { 

    public static volatile SingularAttribute<HoatDongCa, LocalDateTime> gioMoCa;
    public static volatile SingularAttribute<HoatDongCa, ChiNhanh> chiNhanh;
    public static volatile SingularAttribute<HoatDongCa, String> id;
    public static volatile SingularAttribute<HoatDongCa, Float> tienDauCa;
    public static volatile SingularAttribute<HoatDongCa, Ca> ca;
    public static volatile SingularAttribute<HoatDongCa, LocalDateTime> gioDongCa;
    public static volatile SingularAttribute<HoatDongCa, Float> tienCuoiCa;

}