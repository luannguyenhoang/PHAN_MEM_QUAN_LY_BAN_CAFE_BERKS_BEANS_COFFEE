package domainmodel;

import domainmodel.KhuVuc;
import domainmodel.NguyenLieu;
import domainmodel.NhanVien;
import domainmodel.ThuongHieu;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-12-09T12:30:15", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(ChiNhanh.class)
public class ChiNhanh_ { 

    public static volatile SingularAttribute<ChiNhanh, String> ma;
    public static volatile SingularAttribute<ChiNhanh, String> thanhPho;
    public static volatile SingularAttribute<ChiNhanh, Date> ngayKhaiTruong;
    public static volatile SingularAttribute<ChiNhanh, Integer> trangThai;
    public static volatile SingularAttribute<ChiNhanh, Float> giaTriDiem;
    public static volatile SingularAttribute<ChiNhanh, String> quocGia;
    public static volatile SingularAttribute<ChiNhanh, String> id;
    public static volatile SingularAttribute<ChiNhanh, Float> giaTriDoiDiem;
    public static volatile SingularAttribute<ChiNhanh, ThuongHieu> thuongHieu;
    public static volatile SetAttribute<ChiNhanh, NguyenLieu> listNguyenLieu;
    public static volatile SetAttribute<ChiNhanh, KhuVuc> setKhuVuc;
    public static volatile SetAttribute<ChiNhanh, NhanVien> setNhanVien;

}