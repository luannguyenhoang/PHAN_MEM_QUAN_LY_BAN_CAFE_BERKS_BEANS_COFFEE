/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import fomVe.CustomHeaderRenderer;
import fomVe.SVGIconHelper;
import fomVe.SVGIconHelperButton;
import java.awt.Color;
import java.awt.Component;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import domainmodel.NguyenLieu;
import domainmodel.PhieuKiemKe;
import domainmodel.TaiKhoanAdmin;
import domainmodel.TaiKhoanNguoiDung;
import javax.swing.JButton;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.IChiNhanh;
import service.IChiTietPhieuKiemKe;
import service.INguyenLieu;
import service.IPhieuKiemKe;
import service.implement.ChiNhanhSevice;
import service.implement.ChiTietPhieuKiemKeService;
import service.implement.NguyenLieuService;
import service.implement.PhieuKiemKeService;
import viewmodel.ChiNhanhVM_Long;
import viewmodel.ChiTietPhieuKiemKeViewModel_Long;
import viewmodel.NguyenLieuViewModel_Long;
import viewmodel.NhanVienViewModel_Hoang;
import viewmodel.PhieuKiemKeViewModel_Long;

/**
 *
 * @author hoang
 */
public class QLNguyenLieu extends javax.swing.JPanel {

    private DefaultTableModel dtm = new DefaultTableModel();
    private List<NguyenLieuViewModel_Long> lstnl = new ArrayList<>();
    private INguyenLieu nguyenlieuS = new NguyenLieuService();

    private DefaultTableModel dtm1 = new DefaultTableModel();
    private List<ChiTietPhieuKiemKeViewModel_Long> lstChitietPhieuKiemKe = new ArrayList<>();
    private IChiTietPhieuKiemKe chiTietPKKS = new ChiTietPhieuKiemKeService();

    private DefaultTableModel dtm2 = new DefaultTableModel();
    private List<PhieuKiemKeViewModel_Long> lstPKK = new ArrayList<>();
    private IPhieuKiemKe pKKeSevice = new PhieuKiemKeService();

    private DefaultComboBoxModel<PhieuKiemKeViewModel_Long> modelComBoNguyenLieu;
    private DefaultComboBoxModel<ChiNhanhVM_Long> modelComBoChiNhanh;
    private DefaultComboBoxModel<NguyenLieuViewModel_Long> modelComBoNguyenlieu1;
    private IChiNhanh cnS = new ChiNhanhSevice();
    private DefaultComboBoxModel<PhieuKiemKeViewModel_Long> modelComBoNguyenLieu2;
    private DefaultComboBoxModel<NhanVienViewModel_Hoang> modelComBoNhanVien;

    private List<ChiNhanhVM_Long> lstCN = new ArrayList<>();

    private DefaultTableModel dtmHT = new DefaultTableModel();
    private static FlatSVGIcon icon = null;

    public static void setIcon2(JButton label, String path, int width, int height) {
        try {
            icon = new FlatSVGIcon(path, width, height);
            label.setIcon(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public QLNguyenLieu(TaiKhoanAdmin admin, TaiKhoanNguoiDung nguoiDung) {
        initComponents();
        tblBang.setModel(dtm);
        String[] header = {"ID", "Mã", "Tên", "Số Lượng Tồn", "Đơn Vị Tính", "Hạn Sử Dụng"};
        dtm.setColumnIdentifiers(header);
        tblBang.getColumnModel().getColumn(0).setMinWidth(0);
        tblBang.getColumnModel().getColumn(0).setMaxWidth(0);
        lstnl = nguyenlieuS.getAll();

        tblBang1.setModel(dtmHT);
        String[] headerHT = {"ID", "Mã", "Tên", "Số Lượng Tồn", "Hạn Sử Dụng", "Đơn Vị Tính"};
        dtmHT.setColumnIdentifiers(header);
        tblBang1.getColumnModel().getColumn(0).setMinWidth(0);
        tblBang1.getColumnModel().getColumn(0).setMaxWidth(0);
        lstnl = nguyenlieuS.getAll();

        tblBangCTKK.setModel(dtm1);
        String[] hearder = {"Số Lượng Tồn", "Số Lượng Thực Tế", "Chênh Lệch", "Lí Do", "Phiếu Kiểm kê", "Mã Nguyên Liệu"};
        dtm1.setColumnIdentifiers(hearder);
//           tblBangCTKK.getColumnModel().getColumn(0).setMinWidth(0);
//        tblBangCTKK.getColumnModel().getColumn(0).setMaxWidth(0);
        lstChitietPhieuKiemKe = chiTietPKKS.getAllChiTietHoaDon();

        tblBangPkk.setModel(dtm2);
        String[] hearderKK = {"id", "Mã", "Ngaỳ Kiểm Kê", "Trạng Thái", "Nhân Viên"};
        dtm2.setColumnIdentifiers(hearderKK);
        tblBangPkk.getColumnModel().getColumn(0).setMinWidth(0);
        tblBangPkk.getColumnModel().getColumn(0).setMaxWidth(0);
        lstPKK = pKKeSevice.getAllPKK();

//        lstChiNhanhVM = cnS.getAll();
        modelComBoNguyenLieu = (DefaultComboBoxModel) new DefaultComboBoxModel<>(pKKeSevice.getAllPKKcbb().toArray());
        cbopkk.setModel((DefaultComboBoxModel) modelComBoNguyenLieu);

        modelComBoChiNhanh = (DefaultComboBoxModel) new DefaultComboBoxModel<>(cnS.getAllConHD().toArray());
        cboCN.setModel((DefaultComboBoxModel) modelComBoChiNhanh);

        modelComBoNguyenlieu1 = (DefaultComboBoxModel) new DefaultComboBoxModel<>(nguyenlieuS.getAll().toArray());
        cboNL.setModel((DefaultComboBoxModel) modelComBoNguyenlieu1);

        modelComBoNhanVien = (DefaultComboBoxModel) new DefaultComboBoxModel<>(pKKeSevice.getAllNV().toArray());
        cboNV.setModel((DefaultComboBoxModel) modelComBoNhanVien);

        modelComBoChiNhanh = (DefaultComboBoxModel) new DefaultComboBoxModel<>(cnS.getAllConHD().toArray());
        cboCNPKK.setModel((DefaultComboBoxModel) modelComBoChiNhanh);
//       SVGIconHelperButton.setIcon(btnThem, "icon/add-circle-svgrepo-com.svg", 25, 25);
        btnThem.setFocusPainted(false);
        btnThemNL.setFocusPainted(false);
        btnCapNhat.setFocusPainted(false);
        btnThemPkk.setFocusPainted(false);
        btnXuatEx.setFocusPainted(false);
        btnXoaPkk.setFocusPainted(false);
        btnCapNhatPkk.setFocusPainted(false);
        cboNL.setRenderer(new BasicComboBoxRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setFocusable(false);
                return this;
            }
        });
        cboCN.setRenderer(new BasicComboBoxRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setFocusable(false);
                return this;
            }
        });
        cboNV.setRenderer(new BasicComboBoxRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setFocusable(false);
                return this;
            }
        });
        cbopkk.setRenderer(new BasicComboBoxRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setFocusable(false);
                return this;
            }
        });

        cboCNPKK.setRenderer(new BasicComboBoxRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setFocusable(false);
                return this;
            }
        });

        JTableHeader tableHeader = tblBang.getTableHeader();
        tableHeader.setBackground(new Color(143, 45, 52));
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setBorder(null);
        TableCellRenderer defaultHeaderRenderer = tblBang.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer = new CustomHeaderRenderer(defaultHeaderRenderer);
        tblBang.getTableHeader().setDefaultRenderer(customHeaderRenderer);

        JTableHeader tableHeader2 = tblBang1.getTableHeader();
        tableHeader2.setBackground(new Color(143, 45, 52));
        tableHeader2.setForeground(Color.WHITE);
        tableHeader2.setBorder(null);
        TableCellRenderer defaultHeaderRenderer2 = tblBang1.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer2 = new CustomHeaderRenderer(defaultHeaderRenderer2);
        tblBang1.getTableHeader().setDefaultRenderer(customHeaderRenderer2);

        JTableHeader tableHeader3 = tblBangCTKK.getTableHeader();
        tableHeader3.setBackground(new Color(143, 45, 52));
        tableHeader3.setForeground(Color.WHITE);
        tableHeader3.setBorder(null);
        TableCellRenderer defaultHeaderRenderer3 = tblBangCTKK.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer3 = new CustomHeaderRenderer(defaultHeaderRenderer3);
        tblBangCTKK.getTableHeader().setDefaultRenderer(customHeaderRenderer3);

        JTableHeader tableHeader4 = tblBangPkk.getTableHeader();
        tableHeader4.setBackground(new Color(143, 45, 52));
        tableHeader4.setForeground(Color.WHITE);
        tableHeader4.setBorder(null);
        TableCellRenderer defaultHeaderRenderer4 = tblBangPkk.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer4 = new CustomHeaderRenderer(defaultHeaderRenderer4);
        tblBangPkk.getTableHeader().setDefaultRenderer(customHeaderRenderer4);
        setIcon2(btnXuatEx, "icon/excel.svg", 25, 25);
        SVGIconHelperButton.setIcon(btnThem, "icon/plus.svg", 25, 25);
        SVGIconHelperButton.setIcon(btnThemNL, "icon/plus.svg", 25, 25);
        SVGIconHelperButton.setIcon(btnThemPkk, "icon/plus.svg", 25, 25);
        SVGIconHelperButton.setIcon(btnCapNhat, "icon/update.svg", 25, 25);
        SVGIconHelperButton.setIcon(btnCapNhatPkk, "icon/update.svg", 25, 25);
        SVGIconHelperButton.setIcon(btnXoaPkk, "icon/delete.svg", 25, 25);
         SVGIconHelperButton.setIcon(btnXuatEx, "icon/excel.svg", 25, 25);

        show(lstnl);
        ShowKK(lstPKK);
        ShowCTKK(lstChitietPhieuKiemKe);
        show1(lstnl);
    }

    private void ShowKK(List<PhieuKiemKeViewModel_Long> lst) {
        dtm2.setRowCount(0);
        for (PhieuKiemKeViewModel_Long pk : lst) {
            dtm2.addRow(new Object[]{pk.getId(), pk.getMa(), pk.getNgayKiemKe(), pk.getTrangThai() == 1 ? "Đã xác nhận" : "Tạm thời", pk.getMaNhanVien()});
        }
    }

    private void ShowCTKK(List<ChiTietPhieuKiemKeViewModel_Long> lstct) {
        dtm1.setRowCount(0);
        for (ChiTietPhieuKiemKeViewModel_Long ct : lstct) {
            dtm1.addRow(new Object[]{ct.getSoLuongTon(), ct.getSoLuongChenhlech(), ct.getSoLuongTon() - ct.getSoLuongChenhlech(), ct.getLiDo(), ct.getIdPhieuKiem(), ct.getIdNguyenLieu()});
        }
    }

    private void show(List<NguyenLieuViewModel_Long> lst) {
        dtm.setRowCount(0);
        for (NguyenLieuViewModel_Long nl : lst) {
            dtm.addRow(new Object[]{nl.getId(), nl.getMa(), nl.getTen(), nl.getSoLuongTon(), nl.getDonVitinh(), nl.getHanSuDung()});
        }
    }

    private boolean checkMaSp(String maSp) {
        if (nguyenlieuS.getChiNhanh(maSp) != null) {
            JOptionPane.showMessageDialog(this, "Mã đã tồn tại");
            return false;
        } else {
            return true;
        }
    }

    private void show1(List<NguyenLieuViewModel_Long> lst) {
        dtmHT.setRowCount(0);
        for (NguyenLieuViewModel_Long nl : lst) {
            dtmHT.addRow(new Object[]{nl.getId(), nl.getMa(), nl.getTen(), nl.getSoLuongTon(), nl.getDonVitinh(), nl.getHanSuDung()});
        }
    }

    private boolean checkFormEmpty(JTextField ma, JTextField ten) {
        if (ma.getText().isBlank() || ten.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Không được trống");
            return false;
        } else {
            return true;
        }
    }

    public void fill(int index, List<NguyenLieuViewModel_Long> lst) {
        NguyenLieuViewModel_Long nl = lst.get(index);
        txtMa.setText(nl.getMa());
        txtTen.setText(nl.getTen());
        txtDVT.setText(nl.getDonVitinh());
        int soLuongTon = (int) Math.round(nl.getSoLuongTon());
        txtSLT.setValue(soLuongTon);
        txtHSD.setDate(nl.getHanSuDung());
    }

    public boolean checkChu(String mark) {
        Pattern regexInt = Pattern.compile("[a-zA-Z]+");
        Pattern regexDouble = Pattern.compile("[a-zA-Z]+");
        if (!regexInt.matcher(mark).find() && !regexDouble.matcher(mark).find()) {
            JOptionPane.showMessageDialog(this, "Nhập chữ");
            return false;
        } else {
            return true;
        }
    }

    public void fillHT(int index, List<NguyenLieuViewModel_Long> lst) {
        NguyenLieuViewModel_Long nl = lst.get(index);
        txtMa.setText(nl.getMa());
        txtTen.setText(nl.getTen());
        txtDVT.setText(nl.getDonVitinh());
        txtSLT.setValue(String.valueOf(nl.getSoLuongTon()));

    }

    private boolean checkFormEmptyCTPhieu(JTextField ma, JTextArea ten) {
        if (ma.getText().isBlank() || ten.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Không được trống");
            return false;
        } else {
            return true;
        }
    }

    public void fillPKK(int indexPkk, List<PhieuKiemKeViewModel_Long> lst) {
        PhieuKiemKeViewModel_Long pkk = lst.get(indexPkk);
        txtMaPKK.setText(pkk.getMa());
        txtNgayPkk.setDate(pkk.getNgayKiemKe());

//        txtNgayPKK.setText(String.valueOf(pkk.getNgayKiemKe()));
    }

    private void loadCBBPKK(List<ChiTietPhieuKiemKeViewModel_Long> lst) {
        dtm1 = (DefaultTableModel) tblBangCTKK.getModel();
        dtm1.setRowCount(0);
        for (ChiTietPhieuKiemKeViewModel_Long x : lst) {
            dtm1.addRow(new Object[]{x.getIdNguyenLieu(), x.getIdPhieuKiem(), x.getSoLuong(), x.getSoLuongChenhlech(), x.getSoLuongThucTe(), x.getLiDo(), x.getSoLuongTon()});
        }
    }

    private boolean checkVuotquakituKhuVuc(String makv) {
        Pattern regex = Pattern.compile("^\\w{1,5}+$");
        if (!regex.matcher(makv).find()) {
            JOptionPane.showMessageDialog(this, "Tối đa 5 kí tự", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        panel1 = new fomVe.Panel();
        txtMa = new javax.swing.JTextField();
        panel2 = new fomVe.Panel();
        txtTen = new javax.swing.JTextField();
        panel3 = new fomVe.Panel();
        txtDVT = new javax.swing.JTextField();
        txtSLT = new fomVe.JSpinnerEdit();
        txtHSD = new com.toedter.calendar.JDateChooser();
        jPanel5 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        panel9 = new fomVe.Panel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBang1 = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        panel8 = new fomVe.Panel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBang = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        cboCN = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        panel10 = new fomVe.Panel();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        cboCNPKK = new javax.swing.JComboBox<>();
        panel12 = new fomVe.Panel();
        txtSLTT = new javax.swing.JTextField();
        cbopkk = new javax.swing.JComboBox<>();
        cboNL = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtLiDo = new javax.swing.JTextArea();
        jPanel10 = new javax.swing.JPanel();
        btnThemNL = new javax.swing.JButton();
        btnXuatEx = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblBangCTKK = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        panel11 = new fomVe.Panel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblBangPkk = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        panel15 = new fomVe.Panel();
        txtMaPKK = new javax.swing.JTextField();
        txtNgayPkk = new com.toedter.calendar.JDateChooser();
        jPanel13 = new javax.swing.JPanel();
        rdoNo = new javax.swing.JRadioButton();
        rdoOK = new javax.swing.JRadioButton();
        cboNV = new javax.swing.JComboBox<>();
        jPanel14 = new javax.swing.JPanel();
        btnThemPkk = new javax.swing.JButton();
        btnCapNhatPkk = new javax.swing.JButton();
        btnXoaPkk = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(51, 51, 51));

        jTabbedPane1.setBackground(new java.awt.Color(51, 51, 51));
        jTabbedPane1.setForeground(new java.awt.Color(0, 255, 255));

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));
        jPanel3.setLayout(new java.awt.GridLayout(5, 0, 0, 10));

        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Mã :");
        jPanel3.add(jLabel1);

        jLabel2.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tên :");
        jPanel3.add(jLabel2);

        jLabel3.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Đơn vị tính :");
        jPanel3.add(jLabel3);

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Số lượng tồn :");
        jPanel3.add(jLabel4);

        jLabel5.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Hạn sử dụng :");
        jPanel3.add(jLabel5);

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));
        jPanel4.setLayout(new java.awt.GridLayout(5, 0, 0, 10));

        panel1.setBackground(new java.awt.Color(255, 255, 255));

        txtMa.setBackground(new java.awt.Color(255, 255, 255));
        txtMa.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        txtMa.setForeground(new java.awt.Color(255, 0, 0));
        txtMa.setBorder(null);

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMa, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMa)
                .addContainerGap())
        );

        jPanel4.add(panel1);

        panel2.setBackground(new java.awt.Color(255, 255, 255));

        txtTen.setBackground(new java.awt.Color(255, 255, 255));
        txtTen.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        txtTen.setForeground(new java.awt.Color(0, 0, 0));
        txtTen.setBorder(null);

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTen, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTen)
                .addContainerGap())
        );

        jPanel4.add(panel2);

        panel3.setBackground(new java.awt.Color(255, 255, 255));

        txtDVT.setBackground(new java.awt.Color(255, 255, 255));
        txtDVT.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        txtDVT.setForeground(new java.awt.Color(0, 0, 0));
        txtDVT.setBorder(null);

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtDVT, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtDVT)
                .addContainerGap())
        );

        jPanel4.add(panel3);
        jPanel4.add(txtSLT);

        txtHSD.setBackground(new java.awt.Color(51, 51, 51));
        txtHSD.setDateFormatString("yyyy-MM-dd");
        txtHSD.setMinimumSize(new java.awt.Dimension(90, 22));
        jPanel4.add(txtHSD);

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));
        jPanel5.setLayout(new java.awt.GridLayout(1, 2, 10, 0));

        btnThem.setBackground(new java.awt.Color(155, 49, 56));
        btnThem.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel5.add(btnThem);

        btnCapNhat.setBackground(new java.awt.Color(155, 49, 56));
        btnCapNhat.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        btnCapNhat.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });
        jPanel5.add(btnCapNhat);

        jPanel6.setBackground(new java.awt.Color(51, 51, 51));
        jPanel6.setLayout(new java.awt.GridLayout(2, 0, 0, 20));

        panel9.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(null);

        tblBang1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblBang1);

        jLabel18.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("- Nguyên liệu theo chi nhánh -");

        javax.swing.GroupLayout panel9Layout = new javax.swing.GroupLayout(panel9);
        panel9.setLayout(panel9Layout);
        panel9Layout.setHorizontalGroup(
            panel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 663, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane2)
        );
        panel9Layout.setVerticalGroup(
            panel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.add(panel9);

        panel8.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);

        tblBang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblBang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBang);

        jLabel19.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("- Danh sách nguyên liệu -");

        javax.swing.GroupLayout panel8Layout = new javax.swing.GroupLayout(panel8);
        panel8.setLayout(panel8Layout);
        panel8Layout.setHorizontalGroup(
            panel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
            .addGroup(panel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel8Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        panel8Layout.setVerticalGroup(
            panel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel8Layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(panel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel8Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel19)
                    .addContainerGap(271, Short.MAX_VALUE)))
        );

        jPanel6.add(panel8);

        cboCN.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboCN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCNActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Chi nhánh :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboCN, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 625, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboCN)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(395, 395, 395))))
        );

        jTabbedPane1.addTab("Nguyên Liệu", jPanel1);

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        jPanel7.setBackground(new java.awt.Color(51, 51, 51));
        jPanel7.setLayout(new java.awt.GridLayout(0, 2, 10, 0));

        panel10.setBackground(new java.awt.Color(55, 55, 55));
        panel10.setForeground(new java.awt.Color(102, 102, 102));

        jPanel8.setBackground(new java.awt.Color(55, 55, 55));
        jPanel8.setForeground(new java.awt.Color(153, 153, 153));
        jPanel8.setLayout(new java.awt.GridLayout(4, 1, 0, 5));

        jLabel7.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Chi nhánh :");
        jPanel8.add(jLabel7);

        jLabel10.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Số lượng thực tế :");
        jPanel8.add(jLabel10);

        jLabel11.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Phiếu kiểm kê :");
        jPanel8.add(jLabel11);

        jLabel8.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Nguyên liệu :");
        jPanel8.add(jLabel8);

        jPanel9.setBackground(new java.awt.Color(55, 55, 55));
        jPanel9.setLayout(new java.awt.GridLayout(4, 1, 0, 5));

        cboCNPKK.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        cboCNPKK.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel9.add(cboCNPKK);

        panel12.setBackground(new java.awt.Color(255, 255, 255));

        txtSLTT.setBackground(new java.awt.Color(255, 255, 255));
        txtSLTT.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        txtSLTT.setBorder(null);
        txtSLTT.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                txtSLTTVetoableChange(evt);
            }
        });

        javax.swing.GroupLayout panel12Layout = new javax.swing.GroupLayout(panel12);
        panel12.setLayout(panel12Layout);
        panel12Layout.setHorizontalGroup(
            panel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSLTT, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel12Layout.setVerticalGroup(
            panel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSLTT)
                .addContainerGap())
        );

        jPanel9.add(panel12);

        cbopkk.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        cbopkk.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel9.add(cbopkk);

        cboNL.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        cboNL.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel9.add(cboNL);

        txtLiDo.setColumns(20);
        txtLiDo.setRows(5);
        jScrollPane3.setViewportView(txtLiDo);

        jPanel10.setBackground(new java.awt.Color(55, 55, 55));
        jPanel10.setLayout(new java.awt.GridLayout(1, 2, 10, 0));

        btnThemNL.setBackground(new java.awt.Color(155, 49, 56));
        btnThemNL.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        btnThemNL.setForeground(new java.awt.Color(255, 255, 255));
        btnThemNL.setText("Thêm");
        btnThemNL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNLActionPerformed(evt);
            }
        });
        jPanel10.add(btnThemNL);

        btnXuatEx.setBackground(new java.awt.Color(10, 118, 64));
        btnXuatEx.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        btnXuatEx.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatEx.setText("Xuất Excel");
        btnXuatEx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExActionPerformed(evt);
            }
        });
        jPanel10.add(btnXuatEx);

        tblBangCTKK.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tblBangCTKK);

        jLabel9.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("lý do :");

        jLabel12.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("  - Kiểm Kê Nguyên Liệu -");

        javax.swing.GroupLayout panel10Layout = new javax.swing.GroupLayout(panel10);
        panel10.setLayout(panel10Layout);
        panel10Layout.setHorizontalGroup(
            panel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel10Layout.createSequentialGroup()
                        .addGroup(panel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel10Layout.createSequentialGroup()
                                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18))
                            .addGroup(panel10Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)))
                        .addGroup(panel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3)
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panel10Layout.setVerticalGroup(
            panel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGroup(panel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel10Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel10Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(panel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.add(panel10);

        panel11.setBackground(new java.awt.Color(55, 55, 55));

        tblBangPkk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblBangPkk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBangPkkMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblBangPkk);

        jPanel11.setBackground(new java.awt.Color(55, 55, 55));
        jPanel11.setLayout(new java.awt.GridLayout(4, 1, 0, 10));

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Mã :");
        jPanel11.add(jLabel13);

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Ngày kiểm kê :");
        jPanel11.add(jLabel16);

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Trạng thái :");
        jPanel11.add(jLabel15);

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Nhân viên :");
        jPanel11.add(jLabel14);

        jPanel12.setBackground(new java.awt.Color(55, 55, 55));
        jPanel12.setLayout(new java.awt.GridLayout(4, 1, 0, 10));

        panel15.setBackground(new java.awt.Color(255, 255, 255));

        txtMaPKK.setBackground(new java.awt.Color(255, 255, 255));
        txtMaPKK.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        txtMaPKK.setForeground(new java.awt.Color(255, 0, 0));
        txtMaPKK.setBorder(null);

        javax.swing.GroupLayout panel15Layout = new javax.swing.GroupLayout(panel15);
        panel15.setLayout(panel15Layout);
        panel15Layout.setHorizontalGroup(
            panel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMaPKK, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel15Layout.setVerticalGroup(
            panel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMaPKK)
                .addContainerGap())
        );

        jPanel12.add(panel15);

        txtNgayPkk.setBackground(new java.awt.Color(255, 255, 255));
        txtNgayPkk.setDateFormatString("yyyy-MM-dd");
        txtNgayPkk.setMinimumSize(new java.awt.Dimension(90, 22));
        jPanel12.add(txtNgayPkk);

        jPanel13.setBackground(new java.awt.Color(55, 55, 55));

        rdoNo.setBackground(new java.awt.Color(55, 55, 55));
        buttonGroup1.add(rdoNo);
        rdoNo.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        rdoNo.setForeground(new java.awt.Color(255, 255, 255));
        rdoNo.setText("Tạm thời");
        rdoNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNoActionPerformed(evt);
            }
        });

        rdoOK.setBackground(new java.awt.Color(55, 55, 55));
        buttonGroup1.add(rdoOK);
        rdoOK.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        rdoOK.setForeground(new java.awt.Color(255, 255, 255));
        rdoOK.setText("Đã xác nhận");
        rdoOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(rdoNo)
                .addGap(30, 30, 30)
                .addComponent(rdoOK)
                .addGap(0, 296, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rdoNo, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
            .addComponent(rdoOK, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel12.add(jPanel13);

        cboNV.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        cboNV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel12.add(cboNV);

        jPanel14.setBackground(new java.awt.Color(55, 55, 55));
        jPanel14.setLayout(new java.awt.GridLayout(1, 3, 10, 0));

        btnThemPkk.setBackground(new java.awt.Color(155, 49, 56));
        btnThemPkk.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        btnThemPkk.setForeground(new java.awt.Color(255, 255, 255));
        btnThemPkk.setText("Thêm");
        btnThemPkk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemPkkActionPerformed(evt);
            }
        });
        jPanel14.add(btnThemPkk);

        btnCapNhatPkk.setBackground(new java.awt.Color(155, 49, 56));
        btnCapNhatPkk.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        btnCapNhatPkk.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhatPkk.setText("Cập nhật");
        btnCapNhatPkk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatPkkActionPerformed(evt);
            }
        });
        jPanel14.add(btnCapNhatPkk);

        btnXoaPkk.setBackground(new java.awt.Color(155, 49, 56));
        btnXoaPkk.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        btnXoaPkk.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaPkk.setText("Xóa");
        btnXoaPkk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaPkkActionPerformed(evt);
            }
        });
        jPanel14.add(btnXoaPkk);

        jLabel17.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText(" - Phiếu Kiểm Kê-");

        javax.swing.GroupLayout panel11Layout = new javax.swing.GroupLayout(panel11);
        panel11.setLayout(panel11Layout);
        panel11Layout.setHorizontalGroup(
            panel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane6)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel11Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panel11Layout.setVerticalGroup(
            panel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel11Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(panel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.add(panel11);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 711, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Phiếu Kiểm Kê", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboCNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCNActionPerformed

        dtmHT.setRowCount(0);

        modelComBoNguyenLieu = (DefaultComboBoxModel) new DefaultComboBoxModel<>(
                nguyenlieuS.getAllNguyenLieuByChiNhanh(((ChiNhanhVM_Long) modelComBoChiNhanh.getSelectedItem()).getId()).toArray());
        ChiNhanhVM_Long cn = (ChiNhanhVM_Long) cboCN.getSelectedItem();
        show1(nguyenlieuS.getAllNguyenLieuByChiNhanh(cn.getId()));

    }//GEN-LAST:event_cboCNActionPerformed

    private void tblBangMouseClicked(java.awt.event.MouseEvent evt) {
        int row = tblBang.getSelectedRow();
        fill(row, lstnl);
    }

    private void txtSLTTVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_txtSLTTVetoableChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSLTTVetoableChange

    private void btnXuatExActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExActionPerformed
        FileOutputStream excelFOU = null;
        BufferedOutputStream excelBOU = null;
        XSSFWorkbook excelJTableExporter = null;
        JFileChooser excelFileChooser = new JFileChooser("C:\\Users\\PC\\Desktop\\Test");
        excelFileChooser.setDialogTitle("Save As");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
        excelFileChooser.setFileFilter(fnef);
        int excelChosser = excelFileChooser.showSaveDialog(null);
        if (excelChosser == JFileChooser.APPROVE_OPTION) {

            try {
                excelJTableExporter = new XSSFWorkbook();
                XSSFSheet excelSheet = excelJTableExporter.createSheet("JTable Sheet");

                // Thêm tiêu đề cột
                XSSFRow headerRow = excelSheet.createRow(0);
                headerRow.createCell(0).setCellValue("Số lượng tồn");
                headerRow.createCell(1).setCellValue("Số lượng thực tế");
                headerRow.createCell(2).setCellValue("Chênh lệch");
                headerRow.createCell(3).setCellValue("Lí do");
                headerRow.createCell(4).setCellValue("Phiếu kiểm kê");
                headerRow.createCell(5).setCellValue("Mã nguyên liệu");

                for (int i = 0; i < dtm1.getRowCount(); i++) {
                    XSSFRow excelRow = excelSheet.createRow(i + 1);
                    for (int j = 0; j < dtm1.getColumnCount(); j++) {
                        XSSFCell excelCell = excelRow.createCell(j);
                        excelCell.setCellValue(dtm1.getValueAt(i, j).toString());
                    }
                }

                // Căn kích thước cho các cột
                for (int colIndex = 0; colIndex < dtm1.getColumnCount(); colIndex++) {
                    excelSheet.autoSizeColumn(colIndex);
                }

                excelFOU = new FileOutputStream(excelFileChooser.getSelectedFile() + ".xlsx");
                excelBOU = new BufferedOutputStream(excelFOU);
                excelJTableExporter.write(excelBOU);
                JOptionPane.showMessageDialog(this, "Exported");
            } catch (FileNotFoundException ex) {
            } catch (IOException ex) {
            } finally {
                try {
                    if (excelBOU != null) {
                        excelBOU.close();
                    }
                    if (excelFOU != null) {
                        excelFOU.close();
                    }
                    if (excelJTableExporter != null) {
                        excelJTableExporter.close();
                    }
                } catch (IOException ex) {
                }
            }

        }
    }//GEN-LAST:event_btnXuatExActionPerformed

    private void btnThemNLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNLActionPerformed
        if (checkFormEmptyCTPhieu(txtSLTT, txtLiDo)) {
            PhieuKiemKeViewModel_Long pk = (PhieuKiemKeViewModel_Long) cbopkk.getSelectedItem();
            NguyenLieuViewModel_Long nl = (NguyenLieuViewModel_Long) cboNL.getSelectedItem();
            chiTietPKKS.insertNguyenLieu(Float.valueOf(txtSLTT.getText()), txtLiDo.getText(), String.valueOf(nl.getId()), String.valueOf(pk.getId()));
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            lstChitietPhieuKiemKe = chiTietPKKS.getAllChiTietHoaDon();
            ShowCTKK(lstChitietPhieuKiemKe);
            // TODO add your handling code here:
        } else {

        }
    }//GEN-LAST:event_btnThemNLActionPerformed

    private void btnThemPkkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemPkkActionPerformed
        NhanVienViewModel_Hoang nv = (NhanVienViewModel_Hoang) cboNV.getSelectedItem();

        int trangThai;
        if (rdoOK.isSelected()) {
            trangThai = 1;
        } else {
            trangThai = 0;
        }
        pKKeSevice.insertPKK(txtMaPKK.getText(), txtNgayPkk.getDate(), trangThai, nv);
        modelComBoNguyenLieu = (DefaultComboBoxModel) new DefaultComboBoxModel<>(pKKeSevice.getAllPKKcbb().toArray());
        cbopkk.setModel((DefaultComboBoxModel) modelComBoNguyenLieu);
        modelComBoNguyenlieu1 = (DefaultComboBoxModel) new DefaultComboBoxModel<>(nguyenlieuS.getAll().toArray());
        cboNL.setModel((DefaultComboBoxModel) modelComBoNguyenlieu1);
        JOptionPane.showMessageDialog(this, "Thêm thành công");

//             addCbKhuVucByChiNhah(((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId());
//            load_KhuVuc(iKhuVucService.getAllKhuVucByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId()));
//addCbKhuVucByChiNhah(modelComBoNguyenLieu.getSelectedItem().get)
//   loadCBBPKK(chiTietPKKS.getCYPKKbyPKK(((PhieuKiemKeViewModel_Long) cbbpkk.getSelectedItem()).getId()));
        lstPKK = pKKeSevice.getAllPKK();
        ShowKK(lstPKK);

    }//GEN-LAST:event_btnThemPkkActionPerformed

    private void btnCapNhatPkkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatPkkActionPerformed
        int row = tblBangPkk.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng click vào Bảng");
        } else {

            if (txtMaPKK.getText().equals(tblBangPkk.getValueAt(row, 1).toString())) {
                PhieuKiemKe mauView = new PhieuKiemKe();
                mauView.setMa(tblBangPkk.getValueAt(row, 1).toString());
//                    String ngS = txtNgayKC.getText();
//            Date ng = Date.valueOf(ngS);
                int trangThai;
                if (rdoOK.isSelected()) {
                    trangThai = 1;
                } else {
                    trangThai = 0;
                }
//                    chiNhanhS.update(mauView, txtMa.getText(), txtThanhPho.getText(), txtQuocGia.getText(), trangThai, txtNgayKC.getDate());
                pKKeSevice.update(mauView, txtMaPKK.getText(), trangThai, txtNgayPkk.getDate());
                modelComBoNguyenLieu = (DefaultComboBoxModel) new DefaultComboBoxModel<>(pKKeSevice.getAllPKKcbb().toArray());
                cbopkk.setModel((DefaultComboBoxModel) modelComBoNguyenLieu);
                JOptionPane.showMessageDialog(this, "Sửa thành công");
                lstPKK = pKKeSevice.getAllPKK();

//                    show(chiNhanhS.getAll());
                ShowKK(lstPKK);
            }

        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnCapNhatPkkActionPerformed

    private void btnXoaPkkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaPkkActionPerformed
        int row1 = tblBangPkk.getSelectedRow();
        pKKeSevice.deletePKK(tblBangPkk.getValueAt(row1, 0).toString());
        modelComBoNguyenLieu = (DefaultComboBoxModel) new DefaultComboBoxModel<>(pKKeSevice.getAllPKK().toArray());
        cbopkk.setModel((DefaultComboBoxModel) modelComBoNguyenLieu);
        JOptionPane.showMessageDialog(this, "Xoá tc");

        lstPKK = pKKeSevice.getAllPKK();
        lstChitietPhieuKiemKe = chiTietPKKS.getAllChiTietHoaDon();
        ShowKK(lstPKK);
        ShowCTKK(lstChitietPhieuKiemKe);
    }//GEN-LAST:event_btnXoaPkkActionPerformed

    private void tblBangPkkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangPkkMouseClicked
        int row1 = tblBangPkk.getSelectedRow();
        fillPKK(row1, lstPKK);// TODO add your handling code here:
    }//GEN-LAST:event_tblBangPkkMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (checkFormEmpty(txtMa, txtDVT) && checkVuotquakituKhuVuc(txtMa.getText()) && checkChu(txtTen.getText()) && checkMaSp(txtMa.getText())) {
//            String ngS = txtHSD.getText();
//            java.sql.Date ng = java.sql.Date.valueOf(ngS);

            NguyenLieu nl = new NguyenLieu();
            nl.setMa(txtMa.getText());
            nl.setSoLuongTon(Float.valueOf(txtSLT.getValue().toString()));
            nl.setDonViTinh(txtDVT.getText());

//            nl.setHanSuDung(ng);
            ChiNhanhVM_Long cn1 = new ChiNhanhVM_Long();
            ChiNhanhVM_Long cn = (ChiNhanhVM_Long) cboCN.getSelectedItem();

            nguyenlieuS.insertNguyenLieu(txtMa.getText(), txtTen.getText(), txtHSD.getDate(), txtDVT.getText(), Float.valueOf(txtSLT.getValue().toString()), String.valueOf(cn.getId()));
            modelComBoNguyenlieu1 = (DefaultComboBoxModel) new DefaultComboBoxModel<>(nguyenlieuS.getAll().toArray());
            cboNL.setModel((DefaultComboBoxModel) modelComBoNguyenlieu1);

            modelComBoChiNhanh = (DefaultComboBoxModel) new DefaultComboBoxModel<>(cnS.getAll().toArray());
            cboCN.setModel((DefaultComboBoxModel) modelComBoChiNhanh);

            JOptionPane.showMessageDialog(this, "Thêm thành công");
            loadCBBPKK(chiTietPKKS.getCYPKKbyPKK(((PhieuKiemKeViewModel_Long) cbopkk.getSelectedItem()).getId()));
            lstnl = nguyenlieuS.getAll();

            show(lstnl);
            show1(lstnl);

        } else {

        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        int row = tblBang.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng click vào Bảng");
        } else {
            if (checkFormEmpty(txtMa, txtTen)) {
                if (txtMa.getText().equals(tblBang.getValueAt(row, 1).toString())) {
//                    String ngS = txtHSD.getText();
//                    java.sql.Date ng = java.sql.Date.valueOf(ngS);
//                    nguyenlieuS.update(tblBang.getValueAt(row, 0).toString(), txtMa.getText(), txtTen.getText(), txtDVT.getText()instanceof , txtSLT.getText(), ng);
                    nguyenlieuS.update(tblBang.getValueAt(row, 0).toString(), txtMa.getText(), txtTen.getText(), txtDVT.getText(), Float.valueOf(txtSLT.getValue().toString()), txtHSD.getDate());
                    JOptionPane.showMessageDialog(this, "Sửa thành công");
                    show(nguyenlieuS.getAll());
                }
            }
        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void rdoNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNoActionPerformed

    }//GEN-LAST:event_rdoNoActionPerformed

    private void rdoOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoOKActionPerformed

    }//GEN-LAST:event_rdoOKActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnCapNhatPkk;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemNL;
    private javax.swing.JButton btnThemPkk;
    private javax.swing.JButton btnXoaPkk;
    private javax.swing.JButton btnXuatEx;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboCN;
    private javax.swing.JComboBox<String> cboCNPKK;
    private javax.swing.JComboBox<String> cboNL;
    private javax.swing.JComboBox<String> cboNV;
    private javax.swing.JComboBox<String> cbopkk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private fomVe.Panel panel1;
    private fomVe.Panel panel10;
    private fomVe.Panel panel11;
    private fomVe.Panel panel12;
    private fomVe.Panel panel15;
    private fomVe.Panel panel2;
    private fomVe.Panel panel3;
    private fomVe.Panel panel8;
    private fomVe.Panel panel9;
    private javax.swing.JRadioButton rdoNo;
    private javax.swing.JRadioButton rdoOK;
    private javax.swing.JTable tblBang;
    private javax.swing.JTable tblBang1;
    private javax.swing.JTable tblBangCTKK;
    private javax.swing.JTable tblBangPkk;
    private javax.swing.JTextField txtDVT;
    private com.toedter.calendar.JDateChooser txtHSD;
    private javax.swing.JTextArea txtLiDo;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtMaPKK;
    private com.toedter.calendar.JDateChooser txtNgayPkk;
    private fomVe.JSpinnerEdit txtSLT;
    private javax.swing.JTextField txtSLTT;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables
}
