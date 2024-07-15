package domainmodel;

import domainmodel.Ban;
import domainmodel.ChiNhanh;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-12-09T12:30:15", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(KhuVuc.class)
public class KhuVuc_ { 

    public static volatile SingularAttribute<KhuVuc, String> ma;
    public static volatile SingularAttribute<KhuVuc, Integer> trangThai;
    public static volatile SingularAttribute<KhuVuc, ChiNhanh> chiNhanh;
    public static volatile SetAttribute<KhuVuc, Ban> listBan;
    public static volatile SingularAttribute<KhuVuc, String> id;

}