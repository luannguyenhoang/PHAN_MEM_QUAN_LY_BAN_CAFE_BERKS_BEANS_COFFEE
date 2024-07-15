/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import domainmodel.ChiNhanh;
import domainmodel.KhachHang;
import domainmodel.NhaCungCap;
import fomVe.CustomHeaderRenderer;
import fomVe.SVGIconHelperButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
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
import service.IKhachHang;
import service.IKhuyenMai;
import service.INhaCungCap;
import service.implement.KhachHangService;
import service.implement.KhuyenMaiService;
import service.implement.NhaCungCapService;
import viewmodel.ChiNhanhView;
import viewmodel.KhachHangView;
import viewmodel.NhaCungCapView;

/**
 *
 * @author hoang
 */
public class QLDoiTac extends javax.swing.JPanel {

    /**
     * Creates new form QLDoiTac
     */
    List<NhaCungCapView> nhaCungCapViews;
    List<KhachHangView> khachHangViews;
    IKhachHang iKhachHang;
    INhaCungCap iNhaCungCap;
    IKhuyenMai iKhuyenMai;
    private final String tenChucVu;
    private final ChiNhanh cn;
//    public QLDoiTac(TaiKhoanAdmin admin, TaiKhoanNguoiDung nguoiDung) {
//        
//    }

    public QLDoiTac(String tenChucVu) {
        this.tenChucVu = tenChucVu;
        this.cn = null;
        initComponents();
        init();
        khachHangViews = iKhachHang.getAllKhachHang();
        fillKHToTable();
    }

    public QLDoiTac(String tenChucVu, ChiNhanh cn) {
        this.tenChucVu = tenChucVu;
        this.cn = cn;
        initComponents();
        init();
        cbbFilterChiNhanh.setVisible(false);
        khachHangViews = iKhachHang.getAllKHByChiNhanh(cn);
        fillKHToTable();
    }

    private void init() {
        //
        rdoNam.setSelected(true);
        rdoAllNCC.setSelected(true);
        rdoAllKH.setSelected(true);
        txtSearchKH.setForeground(Color.GRAY);
        txtSearchNCC.setForeground(Color.GRAY);

        JTableHeader tableHeader2 = tblKhachHang.getTableHeader();
        tableHeader2.setBackground(new Color(143, 45, 52));
        tableHeader2.setForeground(Color.WHITE);
        tableHeader2.setBorder(null);
        TableCellRenderer defaultHeaderRenderer2 = tblKhachHang.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer2 = new CustomHeaderRenderer(defaultHeaderRenderer2);
        tblKhachHang.getTableHeader().setDefaultRenderer(customHeaderRenderer2);

        JTableHeader tableHeader = tblNhaCungCap.getTableHeader();
        tableHeader.setBackground(new Color(143, 45, 52));
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setBorder(null);
        TableCellRenderer defaultHeaderRenderer = tblNhaCungCap.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer = new CustomHeaderRenderer(defaultHeaderRenderer);
        tblNhaCungCap.getTableHeader().setDefaultRenderer(customHeaderRenderer);

        SVGIconHelperButton.setIcon(btnUpdateKH, "icon/update.svg", 25, 25);
        SVGIconHelperButton.setIcon(btnUpdateNCC, "icon/update.svg", 25, 25);

        SVGIconHelperButton.setIcon(btnAddNCC, "icon/plus.svg", 25, 25);
        SVGIconHelperButton.setIcon(btnXoaKH, "icon/delete.svg", 25, 25);

        SVGIconHelperButton.setIcon(btnXoaNCC, "icon/delete.svg", 25, 25);
        SVGIconHelperButton.setIcon(btnExcelKH, "icon/excel.svg", 25, 25);
        SVGIconHelperButton.setIcon(btnExcelNCC, "icon/excel.svg", 25, 25);

        //
        iKhuyenMai = new KhuyenMaiService();
        iNhaCungCap = new NhaCungCapService();
        iKhachHang = new KhachHangService();
        //
        nhaCungCapViews = iNhaCungCap.getAllNhaCungCap();
        cbbFilterChiNhanh.setModel(new DefaultComboBoxModel(concatenate(new Object[]{"- Tất cả chi nhánh -"}, iKhuyenMai.getAllChiNhanhON().toArray())));// viet lai repo ChiNhanh thi sua doan nay
        fillNCCToTable();
    }

    //// GENERAL
    // getData to validate
    private Object[] getDataFormControls(int type) { // type = 0 lay data NCC, type != 0 lay data KH
        if (type == 0) {
            return new Object[]{
                txtMaNCC.getText().trim(), txtTenNCC.getText().trim(), cbbTrangThaiNCC.getSelectedIndex()
            };
        } else {
            return new Object[]{txtMaKH.getText().trim(), txtHoTenKH.getText().trim(), txtSdtKH.getText().trim(),
                txtThanhPhoKH.getText().trim(), txtQuocGiaKH.getText().trim(), txtDiemTichLuy.getText().trim(),
                cbbTrangThaiKH.getSelectedIndex()
            };
        }
    }

    // reset con trols
    private void resetControls(int type) {
        if (type == 0) {// type = 0 reset NCC, type != 0 reset KH
            txtMaNCC.setText("");
            txtTenNCC.setText("");
            cbbTrangThaiNCC.setSelectedIndex(0);
        } else {
            txtMaKH.setText("");
            txtHoTenKH.setText("");
            txtSdtKH.setText("");
            txtThanhPhoKH.setText("");
            txtQuocGiaKH.setText("");
            txtDiemTichLuy.setText("");
            rdoNam.setSelected(true);
            cbbTrangThaiKH.setSelectedIndex(0);
        }
    }

    // Add to Cbb
    private Object[] concatenate(Object[] a, Object[] b) {
        Collection<Object> result = new ArrayList<Object>(a.length + b.length);
        result.addAll(Arrays.asList(a));
        result.addAll(Arrays.asList(b));
        return result.toArray();
    }

    //// NHA CUNG CAP
    /// VIEW
    // fill table
    private void fillNCCToTable() {
        DefaultTableModel modelNCC = (DefaultTableModel) tblNhaCungCap.getModel();
        modelNCC.setRowCount(0);
        if (!nhaCungCapViews.isEmpty()) {
            for (NhaCungCapView nccv : nhaCungCapViews) {
                modelNCC.addRow(nccv.toDataRow());
            }
        }
    }

    // fill controls
    private void fillNCCToControls() {
        int row = tblNhaCungCap.getSelectedRow();
        if (row != -1) {
            String id = tblNhaCungCap.getValueAt(row, 0) + "";
            NhaCungCapView nccv = iNhaCungCap.getNCCById(id);
            if (nccv != null) {
                txtMaNCC.setText(nccv.getMa());
                txtTenNCC.setText(nccv.getTen());
                if (nccv.getTrangThai() == 1) {
                    cbbTrangThaiNCC.setSelectedIndex(1);
                } else {
                    cbbTrangThaiNCC.setSelectedIndex(2);
                }
            }
        }
    }

    // search and filter NCC
    private void searchAndFilterNCC(String search, int trangThai) {
        if (search.isBlank()) {
            if (trangThai == -1) {
                nhaCungCapViews = iNhaCungCap.getAllNhaCungCap();
            } else {
                nhaCungCapViews = iNhaCungCap.getAllNCCByTrangThai(trangThai);
            }
        } else {
            if (trangThai == -1) {
                if (iNhaCungCap.getAllNCCByMa(search).isEmpty()) {
                    nhaCungCapViews = iNhaCungCap.getAllNCCByName(search);
                } else {
                    nhaCungCapViews = iNhaCungCap.getAllNCCByMa(search);
                }
            } else {
                if (iNhaCungCap.getAllNCCByTrangThaiAndMa(trangThai, search).isEmpty()) {
                    nhaCungCapViews = iNhaCungCap.getAllNCCByTrangThaiAndName(trangThai, search);
                } else {
                    nhaCungCapViews = iNhaCungCap.getAllNCCByTrangThaiAndMa(trangThai, search);
                }
            }
        }
    }

    // get data NCC
    private NhaCungCap getNCCFromControls() {
        return new NhaCungCap(null, txtMaNCC.getText().trim().toUpperCase(),
                txtTenNCC.getText().trim(),
                cbbTrangThaiNCC.getSelectedIndex() == 1 ? 1 : 0);
//                cbbTrangThaiNCC.getSelectedIndex() == 0 ? -1 : (cbbTrangThaiNCC.getSelectedIndex() == 1 ? 1 : 0));
    }

    /// CUD
    private void addNCC() {
        if (iNhaCungCap.validateDataInput(getDataFormControls(0)).isBlank()) {
            JOptionPane.showMessageDialog(null, iNhaCungCap.addNhaCungCap(getNCCFromControls()));
            int trangThai = rdoAllNCC.isSelected() ? -1 : (rdoOnNCC.isSelected() ? 1 : 0);
            searchAndFilterNCC("", trangThai);
            fillNCCToTable();
            resetControls(0);
        } else {
            JOptionPane.showMessageDialog(null, iNhaCungCap.validateDataInput(getDataFormControls(0)));
        }
    }

    private void updateNCC() { // add và update chua hien ra thong bao trung ma
        int row = tblNhaCungCap.getSelectedRow();
        if (row != -1) {
            if (iNhaCungCap.validateDataInput(getDataFormControls(0)).isBlank()) {
                boolean check = false; // so sanh 2 gia tri maNCC tren table va control, tru thi update cac truong khac ngoai tru maNCC, false thi update ca maNCC nhung phai check trung
                String maNCCTable = tblNhaCungCap.getValueAt(row, 1) + "";
                String maNCCControls = txtMaNCC.getText().trim();
                if (maNCCTable.equalsIgnoreCase(maNCCControls)) {
                    check = true;
                }
                String id = tblNhaCungCap.getValueAt(row, 0) + "";
                NhaCungCap ncc = getNCCFromControls();
                JOptionPane.showMessageDialog(null, iNhaCungCap.updateNhaCungCap(id, ncc, check));
                int trangThai = rdoAllNCC.isSelected() ? -1 : (rdoOnNCC.isSelected() ? 1 : 0);
                Color color = txtSearchNCC.getForeground();
                if (!color.equals(Color.GRAY)) {
                    String search = txtSearchNCC.getText().trim();
                    searchAndFilterNCC(search, trangThai);
                } else {
                    searchAndFilterNCC("", trangThai);
                }
                fillNCCToTable();
            } else {
                JOptionPane.showMessageDialog(null, iNhaCungCap.validateDataInput(getDataFormControls(0)));
            }

        } else {
            JOptionPane.showMessageDialog(null, "Ban can chon 1 hang trong bang truoc!");
        }
    }

    private void deleteNCC() {
        int row = tblNhaCungCap.getSelectedRow();
        if (row != -1) {
            String id = tblNhaCungCap.getValueAt(row, 0) + "";
            JOptionPane.showMessageDialog(null, iNhaCungCap.deleteNhaCungCap(id));
            int trangThai = rdoAllNCC.isSelected() ? -1 : (rdoOnNCC.isSelected() ? 1 : 0);
            Color color = txtSearchNCC.getForeground();
            if (!color.equals(Color.GRAY)) {
                String search = txtSearchNCC.getText().trim();
                searchAndFilterNCC(search, trangThai);
            } else {
                searchAndFilterNCC("", trangThai);
            }
            fillNCCToTable();
            resetControls(0);
        } else {
            JOptionPane.showMessageDialog(null, "Ban can chon 1 hang trong bang truoc!");
        }
    }

    // Export Excel
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
                for (int i = 0; i < tblNhaCungCap.getRowCount(); i++) {
                    XSSFRow excelRow = excelSheet.createRow(i);
                    for (int j = 0; j < tblNhaCungCap.getColumnCount(); j++) {
                        XSSFCell excelCell = excelRow.createCell(j);
                        excelCell.setCellValue(tblNhaCungCap.getValueAt(i, j).toString());
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

    //// KHACH HANG
    /// View
    // fill table
    private void fillKHToTable() {
        DefaultTableModel modelKH = (DefaultTableModel) tblKhachHang.getModel();
        modelKH.setRowCount(0);
        if (khachHangViews != null) {
            for (KhachHangView khv : khachHangViews) {
                modelKH.addRow(khv.toDataRow());
            }
        }
    }

    // fill controls
    private void fillKHToControls() {
        int row = tblKhachHang.getSelectedRow();
        String id = tblKhachHang.getValueAt(row, 0) + "";
        KhachHangView khv = iKhachHang.getKHById(id);
        if (khv != null) {
            txtMaKH.setText(khv.getMa());
            if (khv.getDiemTichLuy() != null) {
                txtDiemTichLuy.setText(khv.getDiemTichLuy() + "");
            } else {
                txtDiemTichLuy.setText("0");
            }
            txtHoTenKH.setText(khv.getHoTen());
            txtSdtKH.setText(khv.getSdt());
            txtThanhPhoKH.setText(khv.getThanhPho());
            txtQuocGiaKH.setText(khv.getQuocGia());
            if (khv.getGioiTinh().equalsIgnoreCase("Nam")) {
                rdoNam.setSelected(true);
            } else {
                rdoNu.setSelected(true);
            }

            if (khv.getTrangThai() != null) {
                if (khv.getTrangThai() == 1) {
                    cbbTrangThaiKH.setSelectedIndex(1);
                } else {
                    cbbTrangThaiKH.setSelectedIndex(2);
                }
            }
        }
    }

    // search and filter
    private void searchAndFilterKH(String search, int trangThai) {
        if (tenChucVu.equalsIgnoreCase("Ông chủ")) {
            int type = cbbFilterChiNhanh.getSelectedIndex();
            if (type == 0) { // 0 = all, !=0 theo chi nhanh
                if (search.isBlank()) {
                    if (trangThai == -1) {
                        khachHangViews = iKhachHang.getAllKhachHang();
                    } else {
                        khachHangViews = iKhachHang.getAllKHByTrangThai(trangThai);
                    }
                } else {
                    if (trangThai == -1) {
                        if (iKhachHang.getAllKHByMa(search).isEmpty()) {
                            if (iKhachHang.getAllKHByName(search).isEmpty()) {
                                khachHangViews = iKhachHang.getAllKHBySDT(search);
                            } else {
                                khachHangViews = iKhachHang.getAllKHByName(search);
                            }
                        } else {
                            khachHangViews = iKhachHang.getAllKHByMa(search);
                        }
                    } else {
                        if (iKhachHang.getAllKHByMaAndTrangThai(trangThai, search).isEmpty()) {
                            if (iKhachHang.getAllKHByNameAndTrangThai(trangThai, search).isEmpty()) {
                                khachHangViews = iKhachHang.getAllKHBySDTAndTrangThai(trangThai, search);
                            } else {
                                khachHangViews = iKhachHang.getAllKHByNameAndTrangThai(trangThai, search);
                            }
                        } else {
                            khachHangViews = iKhachHang.getAllKHByMaAndTrangThai(trangThai, search);
                        }
                    }
                }
            } else {
                ChiNhanhView cnv = (ChiNhanhView) cbbFilterChiNhanh.getSelectedItem();
                String id = cnv.getId();
                ChiNhanh cn = iKhuyenMai.getChiNhanhById(id);
                if (search.isBlank()) {
                    if (trangThai == -1) {
                        khachHangViews = iKhachHang.getAllKHByChiNhanh(cn);
                    } else {
                        khachHangViews = iKhachHang.getAllKHByChiNhanhAndTrangThai(cn, trangThai);
                    }
                } else {
                    if (trangThai == -1) {
                        if (iKhachHang.getAllKHByChiNhanhAndMa(cn, search).isEmpty()) {
                            if (iKhachHang.getAllKHByChiNhanhAndName(cn, search).isEmpty()) {
                                khachHangViews = iKhachHang.getAllKHByChiNhanhAndSDT(cn, search);
                            } else {
                                khachHangViews = iKhachHang.getAllKHByChiNhanhAndName(cn, search);
                            }
                        } else {
                            khachHangViews = iKhachHang.getAllKHByChiNhanhAndMa(cn, search);
                        }
                    } else {
                        if (iKhachHang.getAllKHByChiNhanhAndTrangThaiAndMa(cn, trangThai, search).isEmpty()) {
                            if (iKhachHang.getAllKHByChiNhanhAndTrangThaiAndName(cn, trangThai, search).isEmpty()) {
                                khachHangViews = iKhachHang.getAllKHByChiNhanhAndTrangThaiAndSDT(cn, trangThai, search);
                            } else {
                                khachHangViews = iKhachHang.getAllKHByChiNhanhAndTrangThaiAndName(cn, trangThai, search);
                            }
                        } else {
                            khachHangViews = iKhachHang.getAllKHByChiNhanhAndTrangThaiAndMa(cn, trangThai, search);
                        }
                    }
                }
            }
        } else {
            if (search.isBlank()) {
                if (trangThai == -1) {
                    khachHangViews = iKhachHang.getAllKHByChiNhanh(cn);
                } else {
                    khachHangViews = iKhachHang.getAllKHByChiNhanhAndTrangThai(cn, trangThai);
                }
            } else {
                if (trangThai == -1) {
                    if (iKhachHang.getAllKHByChiNhanhAndMa(cn, search).isEmpty()) {
                        if (iKhachHang.getAllKHByChiNhanhAndName(cn, search).isEmpty()) {
                            khachHangViews = iKhachHang.getAllKHByChiNhanhAndSDT(cn, search);
                        } else {
                            khachHangViews = iKhachHang.getAllKHByChiNhanhAndName(cn, search);
                        }
                    } else {
                        khachHangViews = iKhachHang.getAllKHByChiNhanhAndMa(cn, search);
                    }
                } else {
                    if (iKhachHang.getAllKHByChiNhanhAndTrangThaiAndMa(cn, trangThai, search).isEmpty()) {
                        if (iKhachHang.getAllKHByChiNhanhAndTrangThaiAndName(cn, trangThai, search).isEmpty()) {
                            khachHangViews = iKhachHang.getAllKHByChiNhanhAndTrangThaiAndSDT(cn, trangThai, search);
                        } else {
                            khachHangViews = iKhachHang.getAllKHByChiNhanhAndTrangThaiAndName(cn, trangThai, search);
                        }
                    } else {
                        khachHangViews = iKhachHang.getAllKHByChiNhanhAndTrangThaiAndMa(cn, trangThai, search);
                    }
                }
            }
        }
    }

    // get data KH
    private KhachHang getKHFromControls() {
        return new KhachHang(null, txtMaKH.getText().trim().toUpperCase(), txtHoTenKH.getText().trim(),
                txtQuocGiaKH.getText().trim(), txtThanhPhoKH.getText().trim(), txtSdtKH.getText().trim(),
                rdoNam.isSelected() ? "Nam" : "Nữ",
                cbbTrangThaiKH.getSelectedIndex() == 1 ? 1 : 0,
                //                cbbTrangThaiKH.getSelectedIndex() == 0 ? -1 : (cbbTrangThaiNCC.getSelectedIndex() == 1 ? 1 : 0), // thay -1 bang null thi lai loi, chua hieu lam
                Integer.parseInt(txtDiemTichLuy.getText().trim()), null
        );
    }

    /// CUD
    private void addKH() {

    }

    private void updateKH() {
        int row = tblKhachHang.getSelectedRow();
        if (row != -1) {
            if (iKhachHang.validateDataInput(getDataFormControls(1)).isBlank()) {
                boolean check = false; // so sanh 2 gia tri maNCC tren table va control, tru thi update cac truong khac ngoai tru maNCC, false thi update ca maNCC nhung phai check trung
                String maKHTable = tblKhachHang.getValueAt(row, 1) + "";
                String maKHControls = txtMaKH.getText().trim();
                if (maKHTable.equalsIgnoreCase(maKHControls)) {
                    check = true;
                }
                String id = tblKhachHang.getValueAt(row, 0) + "";
                KhachHang kh = getKHFromControls();
                JOptionPane.showMessageDialog(null, iKhachHang.updateKhachHang(id, kh, check));
                // fill dung voi che do hien tai
                int trangThai = rdoAllKH.isSelected() ? -1 : (rdoOnKH.isSelected() ? 1 : 0);
                Color color = txtSearchKH.getForeground();
                if (!color.equals(Color.GRAY)) {
                    String search = txtSearchKH.getText().trim();
                    searchAndFilterKH(search, trangThai);
                } else {
                    searchAndFilterKH("", trangThai);
                }
                fillKHToTable();
            } else {
                JOptionPane.showMessageDialog(null, iKhachHang.validateDataInput(getDataFormControls(1)));
            }

        } else {
            JOptionPane.showMessageDialog(null, "Ban can chon 1 hang trong bang truoc!");
        }
    }

    private void deleteKH() {
        int row = tblKhachHang.getSelectedRow();
        if (row != -1) {
            String id = tblKhachHang.getValueAt(row, 0) + "";
            JOptionPane.showMessageDialog(null, iKhachHang.deleteKhachHang(id));
            int trangThai = rdoAllKH.isSelected() ? -1 : (rdoOnKH.isSelected() ? 1 : 0);
            Color color = txtSearchKH.getForeground();
            if (!color.equals(Color.GRAY)) {
                String search = txtSearchKH.getText().trim();
                searchAndFilterKH(search, trangThai);
            } else {
                searchAndFilterKH("", trangThai);
            }
            fillKHToTable();
            resetControls(1);
        } else {
            JOptionPane.showMessageDialog(null, "Ban can chon 1 hang trong bang truoc!");
        }
    }

    private void exportExcelKH() {
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
                for (int i = 0; i < tblKhachHang.getRowCount(); i++) {
                    XSSFRow excelRow = excelSheet.createRow(i);
                    for (int j = 0; j < tblKhachHang.getColumnCount(); j++) {
                        XSSFCell excelCell = excelRow.createCell(j);
                        excelCell.setCellValue(tblKhachHang.getValueAt(i, j).toString());
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
        buttonGroup3 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        panel31 = new fomVe.Panel3();
        cbbFilterChiNhanh = new javax.swing.JComboBox<>();
        rdoAllKH = new javax.swing.JRadioButton();
        rdoOnKH = new javax.swing.JRadioButton();
        rdoOffKH = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtSearchKH = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnUpdateKH = new javax.swing.JButton();
        btnXoaKH = new javax.swing.JButton();
        btnExcelKH = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        txtMaKH = new fomVe.JxText();
        txtHoTenKH = new fomVe.JxText();
        jPanel7 = new javax.swing.JPanel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        txtThanhPhoKH = new fomVe.JxText();
        jPanel5 = new javax.swing.JPanel();
        txtDiemTichLuy = new fomVe.JxText();
        txtSdtKH = new fomVe.JxText();
        cbbTrangThaiKH = new javax.swing.JComboBox<>();
        txtQuocGiaKH = new fomVe.JxText();
        panel32 = new fomVe.Panel3();
        txtSearchNCC = new fomVe.JxText();
        rdoAllNCC = new javax.swing.JRadioButton();
        rdoOnNCC = new javax.swing.JRadioButton();
        rdoOffNCC = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNhaCungCap = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        txtMaNCC = new fomVe.JxText();
        txtTenNCC = new fomVe.JxText();
        cbbTrangThaiNCC = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        btnAddNCC = new javax.swing.JButton();
        btnXoaNCC = new javax.swing.JButton();
        btnUpdateNCC = new javax.swing.JButton();
        btnExcelNCC = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        panel31.setBackground(new java.awt.Color(255, 255, 255));

        cbbFilterChiNhanh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbFilterChiNhanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbFilterChiNhanhActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdoAllKH);
        rdoAllKH.setText("Tất cả");
        rdoAllKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoAllKHActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdoOnKH);
        rdoOnKH.setText("Còn hoạt động");
        rdoOnKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoOnKHActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdoOffKH);
        rdoOffKH.setText("Ngừng hoạt động");
        rdoOffKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoOffKHActionPerformed(evt);
            }
        });

        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã KH", "Họ tên", "Giới tính", "Số điện thoại", "Thành phố", "Quốc gia", "Trạng thái", "Điểm tích lũy"
            }
        ));
        tblKhachHang.setRowHeight(25);
        tblKhachHang.setSelectionBackground(new java.awt.Color(220, 204, 186));
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKhachHang);
        if (tblKhachHang.getColumnModel().getColumnCount() > 0) {
            tblKhachHang.getColumnModel().getColumn(0).setMinWidth(0);
            tblKhachHang.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblKhachHang.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("- Danh sách và thông tin khách hàng -");

        txtSearchKH.setText("Tìm theo mã, tên, sdt...");
        txtSearchKH.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtSearchKHCaretUpdate(evt);
            }
        });
        txtSearchKH.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchKHFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchKHFocusLost(evt);
            }
        });

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridLayout(3, 1, 0, 15));

        btnUpdateKH.setBackground(new java.awt.Color(155, 49, 56));
        btnUpdateKH.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnUpdateKH.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateKH.setText("Cập nhật khách hàng");
        btnUpdateKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateKHActionPerformed(evt);
            }
        });
        jPanel4.add(btnUpdateKH);

        btnXoaKH.setBackground(new java.awt.Color(155, 49, 56));
        btnXoaKH.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoaKH.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaKH.setText("Xóa khách hàng");
        btnXoaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKHActionPerformed(evt);
            }
        });
        jPanel4.add(btnXoaKH);

        btnExcelKH.setBackground(new java.awt.Color(10, 118, 64));
        btnExcelKH.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnExcelKH.setForeground(new java.awt.Color(255, 255, 255));
        btnExcelKH.setText("Xuất  Excel");
        btnExcelKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelKHActionPerformed(evt);
            }
        });
        jPanel4.add(btnExcelKH);

        jPanel8.setOpaque(false);
        jPanel8.setLayout(new java.awt.GridLayout(1, 2, 5, 0));

        jPanel6.setOpaque(false);
        jPanel6.setLayout(new java.awt.GridLayout(4, 1, 0, 5));

        txtMaKH.setPrompt("Mã khách hàng");
        jPanel6.add(txtMaKH);

        txtHoTenKH.setPrompt("Họ và tên");
        jPanel6.add(txtHoTenKH);

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(rdoNam)
                .addGap(18, 18, 18)
                .addComponent(rdoNu)
                .addGap(0, 188, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rdoNam, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
            .addComponent(rdoNu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel6.add(jPanel7);

        txtThanhPhoKH.setPrompt("Thành phố");
        jPanel6.add(txtThanhPhoKH);

        jPanel8.add(jPanel6);

        jPanel5.setOpaque(false);
        jPanel5.setLayout(new java.awt.GridLayout(4, 1, 0, 5));

        txtDiemTichLuy.setPrompt("Điểm tích lũy");
        jPanel5.add(txtDiemTichLuy);

        txtSdtKH.setPrompt("Số điện thoại");
        jPanel5.add(txtSdtKH);

        cbbTrangThaiKH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Chọn trạng thái -", "Còn hoạt động", "Ngừng hoạt động" }));
        jPanel5.add(cbbTrangThaiKH);

        txtQuocGiaKH.setPrompt("Quốc gia");
        jPanel5.add(txtQuocGiaKH);

        jPanel8.add(jPanel5);

        javax.swing.GroupLayout panel31Layout = new javax.swing.GroupLayout(panel31);
        panel31.setLayout(panel31Layout);
        panel31Layout.setHorizontalGroup(
            panel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel31Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel31Layout.createSequentialGroup()
                        .addGroup(panel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSearchKH)
                            .addGroup(panel31Layout.createSequentialGroup()
                                .addComponent(cbbFilterChiNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoAllKH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoOnKH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoOffKH))
                    .addGroup(panel31Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(panel31Layout.createSequentialGroup()
                .addGap(238, 238, 238)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panel31Layout.setVerticalGroup(
            panel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(cbbFilterChiNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(panel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchKH, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoAllKH)
                    .addComponent(rdoOnKH)
                    .addComponent(rdoOffKH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panel32.setBackground(new java.awt.Color(255, 255, 255));

        txtSearchNCC.setPrompt("Tìm theo mã tên");
        txtSearchNCC.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtSearchNCCCaretUpdate(evt);
            }
        });
        txtSearchNCC.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchNCCFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchNCCFocusLost(evt);
            }
        });

        buttonGroup3.add(rdoAllNCC);
        rdoAllNCC.setText("Tất cả");
        rdoAllNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoAllNCCActionPerformed(evt);
            }
        });

        buttonGroup3.add(rdoOnNCC);
        rdoOnNCC.setText("Còn hoạt động");
        rdoOnNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoOnNCCActionPerformed(evt);
            }
        });

        buttonGroup3.add(rdoOffNCC);
        rdoOffNCC.setText("Ngừng hoạt động");
        rdoOffNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoOffNCCActionPerformed(evt);
            }
        });

        tblNhaCungCap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Mã  NCC", "Tên NCC", "Trạng thái"
            }
        ));
        tblNhaCungCap.setRowHeight(25);
        tblNhaCungCap.setSelectionBackground(new java.awt.Color(220, 204, 186));
        tblNhaCungCap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhaCungCapMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblNhaCungCap);
        if (tblNhaCungCap.getColumnModel().getColumnCount() > 0) {
            tblNhaCungCap.getColumnModel().getColumn(0).setMinWidth(0);
            tblNhaCungCap.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblNhaCungCap.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridLayout(3, 1, 0, 10));

        txtMaNCC.setPrompt("Mã nhà cung cấp");
        jPanel2.add(txtMaNCC);

        txtTenNCC.setPrompt("Tên nhà cung cấp");
        jPanel2.add(txtTenNCC);

        cbbTrangThaiNCC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Chọn trạng thái -", "Còn hoạt động", "Ngừng hoạt động" }));
        jPanel2.add(cbbTrangThaiNCC);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridLayout(4, 1, 0, 5));

        btnAddNCC.setBackground(new java.awt.Color(155, 49, 56));
        btnAddNCC.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAddNCC.setForeground(new java.awt.Color(255, 255, 255));
        btnAddNCC.setText("Thêm  nhà cung cấp");
        btnAddNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNCCActionPerformed(evt);
            }
        });
        jPanel3.add(btnAddNCC);

        btnXoaNCC.setBackground(new java.awt.Color(155, 49, 56));
        btnXoaNCC.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoaNCC.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaNCC.setText("Xóa nhà cung cấp");
        btnXoaNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaNCCActionPerformed(evt);
            }
        });
        jPanel3.add(btnXoaNCC);

        btnUpdateNCC.setBackground(new java.awt.Color(155, 49, 56));
        btnUpdateNCC.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnUpdateNCC.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateNCC.setText("Cập nhật nhà cung cấp");
        btnUpdateNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateNCCActionPerformed(evt);
            }
        });
        jPanel3.add(btnUpdateNCC);

        btnExcelNCC.setBackground(new java.awt.Color(10, 118, 64));
        btnExcelNCC.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnExcelNCC.setForeground(new java.awt.Color(255, 255, 255));
        btnExcelNCC.setText("Xuất  Excel");
        btnExcelNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelNCCActionPerformed(evt);
            }
        });
        jPanel3.add(btnExcelNCC);

        jLabel2.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("- Dánh sách và thông tin nhà cung cấp - ");

        javax.swing.GroupLayout panel32Layout = new javax.swing.GroupLayout(panel32);
        panel32.setLayout(panel32Layout);
        panel32Layout.setHorizontalGroup(
            panel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel32Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(panel32Layout.createSequentialGroup()
                        .addComponent(txtSearchNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoAllNCC)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rdoOnNCC)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoOffNCC))
                    .addGroup(panel32Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel32Layout.setVerticalGroup(
            panel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoAllNCC)
                    .addComponent(rdoOnNCC)
                    .addComponent(rdoOffNCC))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbbFilterChiNhanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbFilterChiNhanhActionPerformed
        if (txtSearchKH.getForeground().equals(Color.GRAY)) {
            rdoAllKH.setSelected(true);
            searchAndFilterKH("", -1);
        } else {
            int trangThai = rdoAllKH.isSelected() ? -1 : (rdoOnKH.isSelected() ? 1 : 0);
            String search = txtSearchKH.getText().trim();
            searchAndFilterKH(search, trangThai);
        }
        fillKHToTable();
    }//GEN-LAST:event_cbbFilterChiNhanhActionPerformed

    private void btnXoaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKHActionPerformed
        deleteKH();
    }//GEN-LAST:event_btnXoaKHActionPerformed

    private void btnUpdateKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateKHActionPerformed
        updateKH();
    }//GEN-LAST:event_btnUpdateKHActionPerformed

    private void btnExcelKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelKHActionPerformed
        exportExcelKH();
    }//GEN-LAST:event_btnExcelKHActionPerformed

    private void rdoAllKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoAllKHActionPerformed
        int trangThai = rdoAllKH.isSelected() ? -1 : (rdoOnKH.isSelected() ? 1 : 0);
        if (txtSearchKH.getForeground().equals(Color.BLACK)) {
            String search = txtSearchKH.getText().trim();
            searchAndFilterKH(search, trangThai);
        } else {
            searchAndFilterKH("", trangThai);
        }
        fillKHToTable();
    }//GEN-LAST:event_rdoAllKHActionPerformed

    private void rdoOnKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoOnKHActionPerformed
        rdoAllKHActionPerformed(evt);
    }//GEN-LAST:event_rdoOnKHActionPerformed

    private void rdoOffKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoOffKHActionPerformed
        rdoAllKHActionPerformed(evt);
    }//GEN-LAST:event_rdoOffKHActionPerformed

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        fillKHToControls();
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void btnAddNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNCCActionPerformed
        addNCC();
    }//GEN-LAST:event_btnAddNCCActionPerformed

    private void btnXoaNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaNCCActionPerformed
        deleteNCC();
    }//GEN-LAST:event_btnXoaNCCActionPerformed

    private void btnUpdateNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateNCCActionPerformed
        updateNCC();
    }//GEN-LAST:event_btnUpdateNCCActionPerformed

    private void btnExcelNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelNCCActionPerformed
        exportExcelNCC();
    }//GEN-LAST:event_btnExcelNCCActionPerformed

    private void txtSearchNCCCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtSearchNCCCaretUpdate
        int trangThai = rdoAllNCC.isSelected() ? -1 : (rdoOnNCC.isSelected() ? 1 : 0);
        Color color = txtSearchNCC.getForeground();
        if (!color.equals(Color.GRAY)) {
            String search = txtSearchNCC.getText().trim();
            searchAndFilterNCC(search, trangThai);
            fillNCCToTable();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchNCCCaretUpdate

    private void txtSearchNCCFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchNCCFocusGained
        if (txtSearchNCC.getForeground().equals(Color.GRAY)) {
            txtSearchNCC.setText("");
            txtSearchNCC.setForeground(Color.BLACK);
        } // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchNCCFocusGained

    private void txtSearchNCCFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchNCCFocusLost
        if (txtSearchNCC.getText().isBlank()) {
            txtSearchNCC.setText("Tìm theo mã, tên...");
            txtSearchNCC.setForeground(Color.GRAY);
            int trangThai = rdoAllNCC.isSelected() ? -1 : (rdoOnNCC.isSelected() ? 1 : 0);
            searchAndFilterNCC("", trangThai);
            fillNCCToTable();
        }  // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchNCCFocusLost

    private void rdoAllNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoAllNCCActionPerformed
        int trangThai = rdoAllNCC.isSelected() ? -1 : (rdoOnNCC.isSelected() ? 1 : 0);
        if (txtSearchNCC.getForeground().equals(Color.BLACK)) {
            String search = txtSearchNCC.getText().trim();
            searchAndFilterNCC(search, trangThai);
        } else {
            searchAndFilterNCC("", trangThai);
        }
        fillNCCToTable();
    }//GEN-LAST:event_rdoAllNCCActionPerformed

    private void rdoOnNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoOnNCCActionPerformed
        rdoAllNCCActionPerformed(evt);
    }//GEN-LAST:event_rdoOnNCCActionPerformed

    private void rdoOffNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoOffNCCActionPerformed
        rdoAllNCCActionPerformed(evt);
    }//GEN-LAST:event_rdoOffNCCActionPerformed

    private void tblNhaCungCapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhaCungCapMouseClicked
        fillNCCToControls();
    }//GEN-LAST:event_tblNhaCungCapMouseClicked

    private void txtSearchKHCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtSearchKHCaretUpdate
        int trangThai = rdoAllKH.isSelected() ? -1 : (rdoOnKH.isSelected() ? 1 : 0);
        Color color = txtSearchKH.getForeground();
        if (!color.equals(Color.GRAY)) {
            String search = txtSearchKH.getText().trim();
            searchAndFilterKH(search, trangThai);
            fillKHToTable();
        }
    }//GEN-LAST:event_txtSearchKHCaretUpdate

    private void txtSearchKHFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchKHFocusGained
        if (txtSearchKH.getForeground().equals(Color.GRAY)) {
            txtSearchKH.setText("");
            txtSearchKH.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_txtSearchKHFocusGained

    private void txtSearchKHFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchKHFocusLost
        if (txtSearchKH.getText().isBlank()) {
            txtSearchKH.setText("Tìm theo mã, tên, sdt...");
            txtSearchKH.setForeground(Color.GRAY);
            int trangThai = rdoAllKH.isSelected() ? -1 : (rdoOnKH.isSelected() ? 1 : 0);
            searchAndFilterKH("", trangThai);
            fillKHToTable();
        }
    }//GEN-LAST:event_txtSearchKHFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddNCC;
    private javax.swing.JButton btnExcelKH;
    private javax.swing.JButton btnExcelNCC;
    private javax.swing.JButton btnUpdateKH;
    private javax.swing.JButton btnUpdateNCC;
    private javax.swing.JButton btnXoaKH;
    private javax.swing.JButton btnXoaNCC;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JComboBox<String> cbbFilterChiNhanh;
    private javax.swing.JComboBox<String> cbbTrangThaiKH;
    private javax.swing.JComboBox<String> cbbTrangThaiNCC;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private fomVe.Panel3 panel31;
    private fomVe.Panel3 panel32;
    private javax.swing.JRadioButton rdoAllKH;
    private javax.swing.JRadioButton rdoAllNCC;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JRadioButton rdoOffKH;
    private javax.swing.JRadioButton rdoOffNCC;
    private javax.swing.JRadioButton rdoOnKH;
    private javax.swing.JRadioButton rdoOnNCC;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTable tblNhaCungCap;
    private fomVe.JxText txtDiemTichLuy;
    private fomVe.JxText txtHoTenKH;
    private fomVe.JxText txtMaKH;
    private fomVe.JxText txtMaNCC;
    private fomVe.JxText txtQuocGiaKH;
    private fomVe.JxText txtSdtKH;
    private javax.swing.JTextField txtSearchKH;
    private fomVe.JxText txtSearchNCC;
    private fomVe.JxText txtTenNCC;
    private fomVe.JxText txtThanhPhoKH;
    // End of variables declaration//GEN-END:variables
}
