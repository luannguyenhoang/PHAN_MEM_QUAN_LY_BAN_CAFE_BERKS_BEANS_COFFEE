package domainmodel;

import domainmodel.NhanVien;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-12-09T12:30:15", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(TaiKhoanNguoiDung.class)
public class TaiKhoanNguoiDung_ { 

    public static volatile SingularAttribute<TaiKhoanNguoiDung, String> matKhau;
    public static volatile SingularAttribute<TaiKhoanNguoiDung, Integer> trangThai;
    public static volatile SingularAttribute<TaiKhoanNguoiDung, NhanVien> nhanVien;
    public static volatile SingularAttribute<TaiKhoanNguoiDung, String> tenTK;
    public static volatile SingularAttribute<TaiKhoanNguoiDung, String> id;

}