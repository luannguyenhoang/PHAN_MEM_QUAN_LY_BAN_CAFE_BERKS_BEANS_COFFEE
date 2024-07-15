package domainmodel;

import domainmodel.ChiTietPhieuNhap;
import domainmodel.ChiTietPhieuTra;
import domainmodel.ChiTietSP;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-12-09T12:30:15", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(NguyenLieu.class)
public class NguyenLieu_ { 

    public static volatile SingularAttribute<NguyenLieu, Date> hanSuDung;
    public static volatile SingularAttribute<NguyenLieu, String> ma;
    public static volatile SingularAttribute<NguyenLieu, Float> soLuongTon;
    public static volatile SetAttribute<NguyenLieu, ChiTietPhieuTra> chiTietPhieuTra;
    public static volatile SetAttribute<NguyenLieu, ChiTietSP> chiTietSp;
    public static volatile SetAttribute<NguyenLieu, ChiTietPhieuNhap> chiTietPhieuNhap;
    public static volatile SingularAttribute<NguyenLieu, String> id;
    public static volatile SingularAttribute<NguyenLieu, String> ten;
    public static volatile SingularAttribute<NguyenLieu, String> donViTinh;

}