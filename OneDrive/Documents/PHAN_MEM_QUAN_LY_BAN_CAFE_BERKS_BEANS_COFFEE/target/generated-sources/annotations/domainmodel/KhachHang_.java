package domainmodel;

import domainmodel.ChiNhanh;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-12-09T12:30:15", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(KhachHang.class)
public class KhachHang_ { 

    public static volatile SingularAttribute<KhachHang, String> sdt;
    public static volatile SingularAttribute<KhachHang, String> ma;
    public static volatile SingularAttribute<KhachHang, String> thanhPho;
    public static volatile SingularAttribute<KhachHang, Integer> trangThai;
    public static volatile SetAttribute<KhachHang, ChiNhanh> listChiNhanh;
    public static volatile SingularAttribute<KhachHang, String> quocGia;
    public static volatile SingularAttribute<KhachHang, Integer> diemTichLuy;
    public static volatile SingularAttribute<KhachHang, String> id;
    public static volatile SingularAttribute<KhachHang, String> hoTen;
    public static volatile SingularAttribute<KhachHang, String> gioiTinh;

}