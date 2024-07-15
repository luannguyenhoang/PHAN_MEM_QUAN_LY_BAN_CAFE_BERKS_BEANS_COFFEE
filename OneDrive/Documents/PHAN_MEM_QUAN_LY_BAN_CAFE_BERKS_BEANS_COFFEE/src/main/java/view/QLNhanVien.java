/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import domainmodel.ChiNhanh;
import domainmodel.ChucVu;
import domainmodel.NhanVien;
import fomVe.CustomHeaderRenderer;
import fomVe.SVGIconHelperButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.IKhuyenMai;
import service.INhanVien;
import service.implement.KhuyenMaiService;
import service.implement.NhanVienService;
import static view.QLGiaoDich.setIcon2;
import static view.QLSanPham.setIcon2;
import viewmodel.ChiNhanhView;
import viewmodel.ChucVuView;
import viewmodel.NhanVienView;

/**
 *
 * @author hoang
 */
public class QLNhanVien extends javax.swing.JPanel {

    List<NhanVienView> nhanVienViews;
    INhanVien iNhanVien;
    IKhuyenMai iKhuyenMai;
    byte[] avatar = null;
    ImageIcon defaultAvatar;
    private final String tenChucVu;
    private final ChiNhanh cn;
    private static FlatSVGIcon icon = null;

    public static void setIcon2(JButton label, String path, int width, int height) {
        try {
            icon = new FlatSVGIcon(path, width, height);
            label.setIcon(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public QLNhanVien(String tenChucVu) {
        this.tenChucVu = tenChucVu;
        this.cn = null;
        initComponents();
        SVGIconHelperButton.setIcon(btnReset, "icon/reset.svg", 25, 25);
        setIcon2(btnDeleteNV, "icon/delete.svg", 20, 20);
        setIcon2(btnExecl, "icon/excel.svg", 20, 20);
        setIcon2(btnAddNhanVien, "icon/plus.svg", 20, 20);
        setIcon2(btnChonAnhNV, "icon/upload-svgrepo-com.svg", 25, 25);
        SVGIconHelperButton.setIcon(btnUpdateNV, "icon/update.svg", 20, 20);
        init();
        cbbChiNhanh.setVisible(true);
        nhanVienViews = iNhanVien.getAllNhanVien();
        cbbFilterChiNhanh.setModel(new DefaultComboBoxModel(concatenate(new Object[]{"- Tất cả chi nhánh -"}, iKhuyenMai.getAllChiNhanh().toArray())));
        fillNVToTable();
    }

    public QLNhanVien(String tenChucVu, ChiNhanh cn) {
        this.tenChucVu = tenChucVu;
        this.cn = cn;
        initComponents();
        init();
        //  lbChonChiNhanh.setVisible(false);
        cbbChiNhanh.setVisible(false);
        cbbFilterChiNhanh.setModel(new DefaultComboBoxModel(new Object[]{toChiNhanhView(cn)}));
        nhanVienViews = iNhanVien.getAllNVByChiNhanh(cn);
        fillNVToTable();
    }

    private void init() {
        iNhanVien = new NhanVienService();
        iKhuyenMai = new KhuyenMaiService();

        rdoNam.setSelected(true);
        rdoAllNV.setSelected(true);
        txtSearchNV.setForeground(Color.GRAY);
        cbbChucVu.setModel(new DefaultComboBoxModel(iNhanVien.getAllChucVu().toArray()));
//        cbbFilterChiNhanh.setSelectedIndex(0);
        cbbChiNhanh.setModel(new DefaultComboBoxModel(iKhuyenMai.getAllChiNhanhON().toArray()));

//        cbbFilterChiNhanh.setModel(new DefaultComboBoxModel(concatenate(new Object[]{"- Tat ca chi nhanh -"}, iKhuyenMai.getAllChiNhanhON().toArray())));
        Image image = new ImageIcon(getClass().getClassLoader().getResource("icon/coffee.gif")).getImage();
        defaultAvatar = new ImageIcon(image.getScaledInstance(150, 200, Image.SCALE_SMOOTH));
        //imageAvartar1.setIcon(defaultAvatar);
        JTableHeader tableHeader = tblNhanVien.getTableHeader();
        tableHeader.setBackground(new Color(143, 45, 52));
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setBorder(null);
        TableCellRenderer defaultHeaderRenderer = tblNhanVien.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer = new CustomHeaderRenderer(defaultHeaderRenderer);
        tblNhanVien.getTableHeader().setDefaultRenderer(customHeaderRenderer);
    }

    private Object[] concatenate(Object[] a, Object[] b) {
        Collection<Object> result = new ArrayList<Object>(a.length + b.length);
        result.addAll(Arrays.asList(a));
        result.addAll(Arrays.asList(b));
        return result.toArray();
    }

    private class CustomHeader extends DefaultTableCellRenderer {

        public CustomHeader() {
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            com.setFont(new Font("Segoe UI", Font.BOLD, 12));
            com.setBackground(new Color(108, 83, 54));
            com.setForeground(Color.WHITE);
            return com;
        }
    }

    private ChiNhanhView toChiNhanhView(ChiNhanh cn) {
        return new ChiNhanhView(cn.getId(), cn.getMa());
    }

    // VIEW
    private void fillNVToTable() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        if (!nhanVienViews.isEmpty()) {
            for (NhanVienView nvv : nhanVienViews) {
                model.addRow(nvv.toDataRow());
            }
        }
    }

    private void fillNVToControls(int row) {
        String id = tblNhanVien.getValueAt(row, 0) + "";
        NhanVienView nvv = iNhanVien.getNhanVienById(id);
        if (nvv != null) {
            txtMaNV.setText(nvv.getMa());
            txtHoTenNV.setText(nvv.getHoTen());
            txtSDT.setText(nvv.getSdt());
            txtThanhPho.setText(nvv.getThanhPho());
            txtQuocGia.setText(nvv.getQuocGia());

            if (nvv.getLuong() != null) {
                txtLuong.setText(nvv.getLuong() + "");
            } else {
                txtLuong.setText("0.0");
            }

            if (nvv.getGioTinh() != null) {
                if (nvv.getGioTinh().equalsIgnoreCase("Nam")) {
                    rdoNam.setSelected(true);
                } else {
                    rdoNu.setSelected(true);
                }
            }

            if (nvv.getTrangThai() != null) {
                if (nvv.getTrangThai() == 1) {
                    cbbTrangThai.setSelectedIndex(1);
                } else {
                    cbbTrangThai.setSelectedIndex(2);
                }
            }

            if (tenChucVu.equalsIgnoreCase("Ông chủ")) {
                if (nvv.getChiNhanh() != null) {
                    cbbChiNhanh.setSelectedIndex(indexChiNhanh(tblNhanVien.getValueAt(row, 8) + ""));
                }
            }
            if (nvv.getChucVu() != null) {
                cbbChucVu.setSelectedIndex(indexChucVu(tblNhanVien.getValueAt(row, 9) + ""));
            }
            // load anh nv
            if (nvv.getAvatar() != null) {
                Image image = new ImageIcon(nvv.getAvatar()).getImage().getScaledInstance(190, 200, Image.SCALE_SMOOTH);
                lblAnhNV.setIcon(new ImageIcon(image));
            } else {
                lblAnhNV.setIcon(defaultAvatar);
            }
//            cbbChucVu.setSelectedItem(nvv.getChucVu()); // bi loi k chay dc, du co cast ve doi tuong roi, chua hieu sai o dau
//            cbbChiNhanh.setSelectedItem(nvv.getChiNhanh()));
        }
    }

    private int indexChiNhanh(String maCN) {
        List<ChiNhanhView> cnvs = iKhuyenMai.getAllChiNhanhON();
        if (!cnvs.isEmpty()) {
            for (int i = 0; i < cnvs.size(); i++) {
                if (cnvs.get(i).getMa().equalsIgnoreCase(maCN)) {
                    return i;
                }
            }
        }
        return 0;
    }

    private int indexChucVu(String tenCV) {
        List<ChucVuView> cvvs = iNhanVien.getAllChucVu();
        if (!cvvs.isEmpty()) {
            for (int i = 0; i < cvvs.size(); i++) {
                if (cvvs.get(i).getTen().equalsIgnoreCase(tenCV)) {
                    return i;
                }
            }
        }
        return 0;
    }

    private void resetControls() {
        lblAnhNV.setIcon(null);
        txtMaNV.setText("");
        txtHoTenNV.setText("");
        txtSDT.setText("");
        txtThanhPho.setText("");
        txtQuocGia.setText("");
        txtLuong.setText("");
        rdoNam.setSelected(true);
        cbbTrangThai.setSelectedIndex(0);
        cbbChucVu.setSelectedIndex(0);
        cbbChiNhanh.setSelectedIndex(0);
    }

    private Object[] getDataFormControls() {
        return new Object[]{txtMaNV.getText().trim(), txtHoTenNV.getText().trim(),
            txtSDT.getText().trim(), txtThanhPho.getText().trim(), txtQuocGia.getText().trim(),
            txtLuong.getText().trim(), cbbTrangThai.getSelectedIndex()
        };
    }

    private NhanVien getNVFromControls() {
        if (tenChucVu.equalsIgnoreCase("Ông chủ")) {
            int type = cbbFilterChiNhanh.getSelectedIndex();
            if (type == 0) {
                ChiNhanh chiNhanh = iKhuyenMai.getChiNhanhById(((ChiNhanhView) cbbChiNhanh.getSelectedItem()).getId());
                ChucVu chucVu = iNhanVien.getChucVuById(((ChucVuView) cbbChucVu.getSelectedItem()).getId());
                return new NhanVien(null, txtMaNV.getText().trim(), txtHoTenNV.getText().trim(), txtQuocGia.getText().trim(),
                        txtThanhPho.getText().trim(), txtSDT.getText().trim(), rdoNam.isSelected() ? "Nam" : "Nữ",
                        Float.parseFloat(txtLuong.getText().trim()), cbbTrangThai.getSelectedIndex() == 1 ? 1 : 0,
                        avatar, chiNhanh, chucVu);
            } else {
                return new NhanVien(null, txtMaNV.getText().trim(), txtHoTenNV.getText().trim(), txtQuocGia.getText().trim(),
                        txtThanhPho.getText().trim(), txtSDT.getText().trim(), rdoNam.isSelected() ? "Nam" : "Nữ",
                        Float.parseFloat(txtLuong.getText().trim()), cbbTrangThai.getSelectedIndex() == 1 ? 1 : 0,
                        avatar, iKhuyenMai.getChiNhanhById(((ChiNhanhView) cbbFilterChiNhanh.getSelectedItem()).getId()),
                        iNhanVien.getChucVuById(((ChucVuView) cbbChucVu.getSelectedItem()).getId())
                );
            }
        } else { // theo chi nhanh cu the voi chuc vu la quan ly
            return new NhanVien(null, txtMaNV.getText().trim(), txtHoTenNV.getText().trim(), txtQuocGia.getText().trim(),
                    txtThanhPho.getText().trim(), txtSDT.getText().trim(), rdoNam.isSelected() ? "Nam" : "Nữ",
                    Float.parseFloat(txtLuong.getText().trim()), cbbTrangThai.getSelectedIndex() == 1 ? 1 : 0,
                    avatar, cn,
                    iNhanVien.getChucVuById(((ChucVuView) cbbChucVu.getSelectedItem()).getId())
            );
        }

    }

    private void searchAndFilterNV(String search, int trangThai) {
        if (tenChucVu.equalsIgnoreCase("Ông chủ")) {
            int type = cbbFilterChiNhanh.getSelectedIndex();
            if (type == 0) { // 0 = all, !=0 theo chi nhanh
                if (search.isBlank()) {
                    if (trangThai == -1) {
                        nhanVienViews = iNhanVien.getAllNhanVien();
                    } else {
                        nhanVienViews = iNhanVien.getAllNVByTrangThai(trangThai);
                    }
                } else {
                    if (trangThai == -1) {
                        if (iNhanVien.getAllNVByMa(search).isEmpty()) {
                            if (iNhanVien.getAllNVByName(search).isEmpty()) {
                                nhanVienViews = iNhanVien.getAllNVBySDT(search);
                            } else {
                                nhanVienViews = iNhanVien.getAllNVByName(search);
                            }
                        } else {
                            nhanVienViews = iNhanVien.getAllNVByMa(search);
                        }
                    } else {
                        if (iNhanVien.getAllNVByMaAndTrangThai(trangThai, search).isEmpty()) {
                            if (iNhanVien.getAllNVByNameAndTrangThai(trangThai, search).isEmpty()) {
                                nhanVienViews = iNhanVien.getAllNVBySDTAndTrangThai(trangThai, search);
                            } else {
                                nhanVienViews = iNhanVien.getAllNVByNameAndTrangThai(trangThai, search);
                            }
                        } else {
                            nhanVienViews = iNhanVien.getAllNVByMaAndTrangThai(trangThai, search);
                        }
                    }
                }
            } else {
                ChiNhanhView cnv = (ChiNhanhView) cbbFilterChiNhanh.getSelectedItem();
                String id = cnv.getId();
                ChiNhanh cn = iKhuyenMai.getChiNhanhById(id);
                if (search.isBlank()) {
                    if (trangThai == -1) {
                        nhanVienViews = iNhanVien.getAllNVByChiNhanh(cn);
                    } else {
                        nhanVienViews = iNhanVien.getAllNVByChiNhanhAndTrangThai(cn, trangThai);
                    }
                } else {
                    if (trangThai == -1) {
                        if (iNhanVien.getAllNVByChiNhanhAndMa(cn, search).isEmpty()) {
                            if (iNhanVien.getAllNVByChiNhanhAndName(cn, search).isEmpty()) {
                                nhanVienViews = iNhanVien.getAllNVByChiNhanhAndSDT(cn, search);
                            } else {
                                nhanVienViews = iNhanVien.getAllNVByChiNhanhAndName(cn, search);
                            }
                        } else {
                            nhanVienViews = iNhanVien.getAllNVByChiNhanhAndMa(cn, search);
                        }
                    } else {
                        if (iNhanVien.getAllNVByChiNhanhAndTrangThaiAndMa(cn, trangThai, search).isEmpty()) {
                            if (iNhanVien.getAllNVByChiNhanhAndTrangThaiAndName(cn, trangThai, search).isEmpty()) {
                                nhanVienViews = iNhanVien.getAllNVByChiNhanhAndTrangThaiAndSDT(cn, trangThai, search);
                            } else {
                                nhanVienViews = iNhanVien.getAllNVByChiNhanhAndTrangThaiAndName(cn, trangThai, search);
                            }
                        } else {
                            nhanVienViews = iNhanVien.getAllNVByChiNhanhAndTrangThaiAndMa(cn, trangThai, search);
                        }
                    }
                }
            }
        } else {
            if (search.isBlank()) {
                if (trangThai == -1) {
                    nhanVienViews = iNhanVien.getAllNVByChiNhanh(cn);
                } else {
                    nhanVienViews = iNhanVien.getAllNVByChiNhanhAndTrangThai(cn, trangThai);
                }
            } else {
                if (trangThai == -1) {
                    if (iNhanVien.getAllNVByChiNhanhAndMa(cn, search).isEmpty()) {
                        if (iNhanVien.getAllNVByChiNhanhAndName(cn, search).isEmpty()) {
                            nhanVienViews = iNhanVien.getAllNVByChiNhanhAndSDT(cn, search);
                        } else {
                            nhanVienViews = iNhanVien.getAllNVByChiNhanhAndName(cn, search);
                        }
                    } else {
                        nhanVienViews = iNhanVien.getAllNVByChiNhanhAndMa(cn, search);
                    }
                } else {
                    if (iNhanVien.getAllNVByChiNhanhAndTrangThaiAndMa(cn, trangThai, search).isEmpty()) {
                        if (iNhanVien.getAllNVByChiNhanhAndTrangThaiAndName(cn, trangThai, search).isEmpty()) {
                            nhanVienViews = iNhanVien.getAllNVByChiNhanhAndTrangThaiAndSDT(cn, trangThai, search);
                        } else {
                            nhanVienViews = iNhanVien.getAllNVByChiNhanhAndTrangThaiAndName(cn, trangThai, search);
                        }
                    } else {
                        nhanVienViews = iNhanVien.getAllNVByChiNhanhAndTrangThaiAndMa(cn, trangThai, search);
                    }
                }
            }
        }
    }

    // CUD
    private void addNhanVien() { // chi them nhan vien o cac chi nhanh dang hoat dong
        if (iNhanVien.validateDataInput(getDataFormControls()).isBlank()) {
            JOptionPane.showMessageDialog(null, iNhanVien.addNhanVien(getNVFromControls()));
            int trangThai = rdoAllNV.isSelected() ? -1 : (rdoNVOn.isSelected() ? 1 : 0);
            searchAndFilterNV("", trangThai);
            fillNVToTable();
//            if(iNhanVien.addNhanVien(getNVFromControls()). equalsIgnoreCase("Them thanh cong!")){
            resetControls();
//            }   
        } else {
            JOptionPane.showMessageDialog(null, iNhanVien.validateDataInput(getDataFormControls()));
        }
    }

    private void updateNhanVien() {
        int row = tblNhanVien.getSelectedRow();
        if (row != -1) {
            if (iNhanVien.validateDataInput(getDataFormControls()).isBlank()) {
                boolean check = false; // so sanh 2 gia tri maNV tren table va control, tru thi update cac truong khac ngoai tru maNV, false thi update ca maNCC nhung phai check trung
                String maNVTable = tblNhanVien.getValueAt(row, 1) + "";
                String maNVControls = txtMaNV.getText().trim();
                if (maNVTable.equalsIgnoreCase(maNVControls)) {
                    check = true;
                }
                String id = tblNhanVien.getValueAt(row, 0) + "";
                if (tenChucVu.equalsIgnoreCase("Ông chủ")) {
                    JOptionPane.showMessageDialog(null, iNhanVien.updateNhanVienByAdmin(id, getNVFromControls(), check));
//                    JOptionPane.showConfirmDialog(null, getNVFromControls().getChiNhanh().getMa());
//                    JOptionPane.showConfirmDialog(null, iKhuyenMai.getChiNhanhById(getNVFromControls().getChiNhanh().getId()).getMa());
                } else {
                    JOptionPane.showMessageDialog(null, iNhanVien.updateNhanVien(id, getNVFromControls(), check));
                }
                int trangThai = rdoAllNV.isSelected() ? -1 : (rdoNVOn.isSelected() ? 1 : 0);
                if (txtSearchNV.getForeground().equals(Color.BLACK)) {
                    String search = txtSearchNV.getText().trim();
                    searchAndFilterNV(search, trangThai);
                } else {
                    searchAndFilterNV("", trangThai);
                }
                System.out.println(nhanVienViews.size());
                fillNVToTable();
            } else {
                JOptionPane.showMessageDialog(null, iNhanVien.validateDataInput(getDataFormControls()));
            }

        } else {
            JOptionPane.showMessageDialog(null, "Ban can chon 1 hang trong bang truoc!");
        }
    }

    private void deleteNhaVien() {
        int row = tblNhanVien.getSelectedRow();
        if (row != -1) {
            String id = tblNhanVien.getValueAt(row, 0) + "";
            JOptionPane.showMessageDialog(null, iNhanVien.deleteNhanVien(id));
            int trangThai = rdoAllNV.isSelected() ? -1 : (rdoNVOn.isSelected() ? 1 : 0);
            if (txtSearchNV.getForeground().equals(Color.BLACK)) {
                String search = txtSearchNV.getText().trim();
                searchAndFilterNV(search, trangThai);
            } else {
                searchAndFilterNV("", trangThai);
            }
            fillNVToTable();
            resetControls();
        } else {
            JOptionPane.showMessageDialog(null, "Ban can chon 1 hang trong bang truoc!");
        }
    }

    private void exportExcelNCC() {
        FileOutputStream excelFOU = null;
        BufferedOutputStream excelBOU = null;
        XSSFWorkbook excelJTableExporter = null;
        JFileChooser excelFileChooser = new JFileChooser(".\\src");
        excelFileChooser.setDialogTitle("Save As");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
        excelFileChooser.setFileFilter(fnef);
        int excelChosser = excelFileChooser.showSaveDialog(null);
        if (excelChosser == JFileChooser.APPROVE_OPTION) {

            try {
                excelJTableExporter = new XSSFWorkbook();
                XSSFSheet excelSheet = excelJTableExporter.createSheet("JTable Sheet");
                for (int i = 0; i < tblNhanVien.getRowCount(); i++) {
                    XSSFRow excelRow = excelSheet.createRow(i);
                    for (int j = 0; j < tblNhanVien.getColumnCount(); j++) {
                        XSSFCell excelCell = excelRow.createCell(j);
                        excelCell.setCellValue(tblNhanVien.getValueAt(i, j).toString());
                    }
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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        panel1 = new fomVe.Panel();
        cbbFilterChiNhanh = new javax.swing.JComboBox<>();
        rdoAllNV = new javax.swing.JRadioButton();
        rdoNVOn = new javax.swing.JRadioButton();
        rdoNVOff = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        panel2 = new fomVe.Panel();
        jPanel2 = new javax.swing.JPanel();
        lblAnhNV = new javax.swing.JLabel();
        btnChonAnhNV = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        txtMaNV = new fomVe.JxText();
        jPanel6 = new javax.swing.JPanel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        txtThanhPho = new fomVe.JxText();
        txtLuong = new fomVe.JxText();
        cbbTrangThai = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        txtHoTenNV = new fomVe.JxText();
        txtSDT = new fomVe.JxText();
        txtQuocGia = new fomVe.JxText();
        cbbChucVu = new javax.swing.JComboBox<>();
        cbbChiNhanh = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        btnAddNhanVien = new javax.swing.JButton();
        btnUpdateNV = new javax.swing.JButton();
        btnDeleteNV = new javax.swing.JButton();
        btnExecl = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        panel3 = new fomVe.Panel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        txtSearchNV = new fomVe.JxText();

        setBackground(new java.awt.Color(51, 51, 51));

        panel1.setBackground(new java.awt.Color(255, 255, 255));

        cbbFilterChiNhanh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbFilterChiNhanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbFilterChiNhanhActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoAllNV);
        rdoAllNV.setForeground(new java.awt.Color(0, 0, 0));
        rdoAllNV.setText("Tất cả");
        rdoAllNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoAllNVActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoNVOn);
        rdoNVOn.setForeground(new java.awt.Color(0, 0, 0));
        rdoNVOn.setText("Đang làm");
        rdoNVOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNVOnActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoNVOff);
        rdoNVOff.setForeground(new java.awt.Color(0, 0, 0));
        rdoNVOff.setText("Đã nghỉ");
        rdoNVOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNVOffActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("- Trạng thái -");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbFilterChiNhanh, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdoAllNV, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdoNVOn, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdoNVOff)
                            .addComponent(jLabel1))
                        .addGap(0, 163, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbbFilterChiNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdoAllNV)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdoNVOff)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdoNVOn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel2.setBackground(new java.awt.Color(255, 255, 255));
        panel2.setForeground(new java.awt.Color(0, 0, 0));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 83, 54)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnhNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnhNV, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );

        btnChonAnhNV.setBackground(new java.awt.Color(155, 49, 56));
        btnChonAnhNV.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        btnChonAnhNV.setForeground(new java.awt.Color(255, 255, 255));
        btnChonAnhNV.setText("Upload");
        btnChonAnhNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonAnhNVActionPerformed(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.GridLayout(1, 2, 5, 0));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.GridLayout(5, 1, 0, 5));

        txtMaNV.setPrompt("Mã nhân viên");
        jPanel5.add(txtMaNV);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        buttonGroup2.add(rdoNam);
        rdoNam.setText("Nam");

        buttonGroup2.add(rdoNu);
        rdoNu.setText("Nữ");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(rdoNam)
                .addGap(18, 18, 18)
                .addComponent(rdoNu)
                .addGap(0, 283, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rdoNam, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
            .addComponent(rdoNu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel6);

        txtThanhPho.setPrompt("Thành phố");
        jPanel5.add(txtThanhPho);

        txtLuong.setPrompt("Lương");
        jPanel5.add(txtLuong);

        cbbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Chọn trạng thái -", "Đang làm", "Đã nghỉ" }));
        jPanel5.add(cbbTrangThai);

        jPanel7.add(jPanel5);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridLayout(5, 1, 0, 5));

        txtHoTenNV.setPrompt("Họ tên nhân viên");
        jPanel3.add(txtHoTenNV);

        txtSDT.setPrompt("Số điện thoại");
        jPanel3.add(txtSDT);

        txtQuocGia.setPrompt("Quốc gia");
        jPanel3.add(txtQuocGia);

        cbbChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel3.add(cbbChucVu);

        cbbChiNhanh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel3.add(cbbChiNhanh);

        jPanel7.add(jPanel3);

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridLayout(1, 4, 5, 0));

        btnAddNhanVien.setBackground(new java.awt.Color(155, 49, 56));
        btnAddNhanVien.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        btnAddNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        btnAddNhanVien.setText("Thêm");
        btnAddNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNhanVienActionPerformed(evt);
            }
        });
        jPanel4.add(btnAddNhanVien);

        btnUpdateNV.setBackground(new java.awt.Color(155, 49, 56));
        btnUpdateNV.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        btnUpdateNV.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateNV.setText("Cập Nhật");
        btnUpdateNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateNVActionPerformed(evt);
            }
        });
        jPanel4.add(btnUpdateNV);

        btnDeleteNV.setBackground(new java.awt.Color(155, 49, 56));
        btnDeleteNV.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        btnDeleteNV.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteNV.setText("Xóa ");
        btnDeleteNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteNVActionPerformed(evt);
            }
        });
        jPanel4.add(btnDeleteNV);

        btnExecl.setBackground(new java.awt.Color(10, 118, 64));
        btnExecl.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        btnExecl.setForeground(new java.awt.Color(255, 255, 255));
        btnExecl.setText("Xuất Excel");
        btnExecl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExeclActionPerformed(evt);
            }
        });
        jPanel4.add(btnExecl);

        btnReset.setBackground(new java.awt.Color(32, 137, 230));
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnChonAnhNV, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnChonAnhNV, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        panel3.setBackground(new java.awt.Color(255, 255, 255));

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã nhân viên", "Họ tên", "Giới tính", "Số điện thoại", "Thành phố", "Quốc gia", "Lương", "Chi nhánh", "Chức vụ", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.setRowHeight(25);
        tblNhanVien.setSelectionBackground(new java.awt.Color(220, 204, 186));
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);
        if (tblNhanVien.getColumnModel().getColumnCount() > 0) {
            tblNhanVien.getColumnModel().getColumn(0).setMinWidth(0);
            tblNhanVien.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblNhanVien.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        txtSearchNV.setForeground(new java.awt.Color(0, 0, 0));
        txtSearchNV.setPrompt("Tìm kiếm theo mã , tên , sđt ...");
        txtSearchNV.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtSearchNVCaretUpdate(evt);
            }
        });
        txtSearchNV.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchNVFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchNVFocusLost(evt);
            }
        });

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1231, Short.MAX_VALUE)
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addComponent(txtSearchNV, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearchNV, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbbFilterChiNhanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbFilterChiNhanhActionPerformed
        //        if()
        if (cbbFilterChiNhanh.getSelectedIndex() == 0) {
            //  lbChonChiNhanh.setVisible(true);
            cbbChiNhanh.setVisible(true);
        } else {
            //  lbChonChiNhanh.setVisible(false);
            cbbChiNhanh.setVisible(false);
        }
        //        txtSearchNV.setText("Tìm theo mã, tên, sdt...");
        if (txtSearchNV.getForeground().equals(Color.GRAY)) {
            rdoAllNV.setSelected(true);
            searchAndFilterNV("", -1);
        } else {
            int trangThai = rdoAllNV.isSelected() ? -1 : (rdoNVOn.isSelected() ? 1 : 0);
            String search = txtSearchNV.getText().trim();
            searchAndFilterNV(search, trangThai);
        }
        fillNVToTable();
    }//GEN-LAST:event_cbbFilterChiNhanhActionPerformed

    private void rdoAllNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoAllNVActionPerformed
        int trangThai = rdoAllNV.isSelected() ? -1 : (rdoNVOn.isSelected() ? 1 : 0);
        Color color = txtSearchNV.getForeground();
        if (color.equals(Color.BLACK)) {
            String search = txtSearchNV.getText().trim();
            searchAndFilterNV(search, trangThai);
        } else {
            searchAndFilterNV("", trangThai);
        }
        fillNVToTable();
    }//GEN-LAST:event_rdoAllNVActionPerformed

    private void rdoNVOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNVOnActionPerformed
        rdoAllNVActionPerformed(evt);
    }//GEN-LAST:event_rdoNVOnActionPerformed

    private void rdoNVOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNVOffActionPerformed
        rdoAllNVActionPerformed(evt);
    }//GEN-LAST:event_rdoNVOffActionPerformed

    private void btnChonAnhNVActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        int choice = fileChooser.showOpenDialog(this);
        if (choice == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();//return file selected-gán path cho File
            Image image = new ImageIcon(file.getAbsolutePath()).getImage().getScaledInstance(lblAnhNV.getWidth(), lblAnhNV.getHeight(), Image.SCALE_SMOOTH);
            lblAnhNV.setIcon(new ImageIcon(image));//tao đối tượng ImageIcon(lấy path file truyền vào)rồi seticon cho label
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", baos);
                avatar = new byte[baos.toByteArray().length];
                avatar = baos.toByteArray();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void btnAddNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNhanVienActionPerformed
        addNhanVien();
    }//GEN-LAST:event_btnAddNhanVienActionPerformed

    private void btnUpdateNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateNVActionPerformed
        updateNhanVien();
    }//GEN-LAST:event_btnUpdateNVActionPerformed

    private void btnDeleteNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteNVActionPerformed
        deleteNhaVien();
    }//GEN-LAST:event_btnDeleteNVActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        int row = tblNhanVien.getSelectedRow();
        if (row != -1) {
            fillNVToControls(row);
            if ((tblNhanVien.getValueAt(row, 10) + "").equalsIgnoreCase("Đang làm")) {
                btnDeleteNV.setEnabled(true);
            } else {
                btnDeleteNV.setEnabled(false);
            }
        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void btnExeclActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExeclActionPerformed
        exportExcelNCC();
    }//GEN-LAST:event_btnExeclActionPerformed

    private void txtSearchNVCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtSearchNVCaretUpdate
        int trangThai = rdoAllNV.isSelected() ? -1 : (rdoNVOn.isSelected() ? 1 : 0);
        Color color = txtSearchNV.getForeground();
        if (!color.equals(Color.GRAY)) {
            String search = txtSearchNV.getText().trim();
            searchAndFilterNV(search, trangThai);
            fillNVToTable();
        }
    }//GEN-LAST:event_txtSearchNVCaretUpdate

    private void txtSearchNVFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchNVFocusLost
        if (txtSearchNV.getText().isBlank()) {

            int trangThai = rdoAllNV.isSelected() ? -1 : (rdoNVOn.isSelected() ? 1 : 0);
            searchAndFilterNV("", trangThai);
            fillNVToTable();
        }
    }//GEN-LAST:event_txtSearchNVFocusLost

    private void txtSearchNVFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchNVFocusGained
        Color color = txtSearchNV.getForeground();
        if (color.equals(Color.GRAY)) {
            txtSearchNV.setText("");
            txtSearchNV.setForeground(Color.BLACK);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchNVFocusGained

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        resetControls();
    }//GEN-LAST:event_btnResetActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddNhanVien;
    private javax.swing.JButton btnChonAnhNV;
    private javax.swing.JButton btnDeleteNV;
    private javax.swing.JButton btnExecl;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnUpdateNV;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cbbChiNhanh;
    private javax.swing.JComboBox<String> cbbChucVu;
    private javax.swing.JComboBox<String> cbbFilterChiNhanh;
    private javax.swing.JComboBox<String> cbbTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAnhNV;
    private fomVe.Panel panel1;
    private fomVe.Panel panel2;
    private fomVe.Panel panel3;
    private javax.swing.JRadioButton rdoAllNV;
    private javax.swing.JRadioButton rdoNVOff;
    private javax.swing.JRadioButton rdoNVOn;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblNhanVien;
    private fomVe.JxText txtHoTenNV;
    private fomVe.JxText txtLuong;
    private fomVe.JxText txtMaNV;
    private fomVe.JxText txtQuocGia;
    private fomVe.JxText txtSDT;
    private fomVe.JxText txtSearchNV;
    private fomVe.JxText txtThanhPho;
    // End of variables declaration//GEN-END:variables
}
