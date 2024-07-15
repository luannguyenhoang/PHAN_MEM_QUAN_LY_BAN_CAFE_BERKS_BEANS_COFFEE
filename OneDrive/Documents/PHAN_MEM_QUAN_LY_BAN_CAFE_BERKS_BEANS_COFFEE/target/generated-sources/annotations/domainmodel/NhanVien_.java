package domainmodel;

import domainmodel.Ca;
import domainmodel.ChiNhanh;
import domainmodel.ChucVu;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-12-09T12:30:15", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(NhanVien.class)
public class NhanVien_ { 

    public static volatile SingularAttribute<NhanVien, String> sdt;
    public static volatile SetAttribute<NhanVien, Ca> setCa;
    public static volatile SingularAttribute<NhanVien, String> thanhPho;
    public static volatile SingularAttribute<NhanVien, String> gioiTinh;
    public static volatile SingularAttribute<NhanVien, byte[]> avatar;
    public static volatile SingularAttribute<NhanVien, ChucVu> chucVu;
    public static volatile SingularAttribute<NhanVien, String> ma;
    public static volatile SingularAttribute<NhanVien, Integer> trangThai;
    public static volatile SingularAttribute<NhanVien, String> quocGia;
    public static volatile SingularAttribute<NhanVien, Float> luong;
    public static volatile SingularAttribute<NhanVien, ChiNhanh> chiNhanh;
    public static volatile SingularAttribute<NhanVien, String> id;
    public static volatile SingularAttribute<NhanVien, String> hoTen;

}