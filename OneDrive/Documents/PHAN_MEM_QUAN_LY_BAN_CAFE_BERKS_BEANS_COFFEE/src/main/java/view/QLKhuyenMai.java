/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import domainmodel.ChiNhanh;
import domainmodel.SanPham;
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

import fomVe.SVGIconHelperButton;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.IKhuyenMai;
import service.implement.KhuyenMaiService;
import viewmodel.ChiNhanhView;
import viewmodel.KhuyenMaiView;

/**
 *
 * @author hoang
 */
public class QLKhuyenMai extends javax.swing.JPanel {

    IKhuyenMai iKhuyenMai;
    List<KhuyenMaiView> khuyenMaiViews;
    List<SanPham> sanPhamAddKM;
    List<SanPham> sanPhamHaveKM;
    private final String tenChucVu;
    private final ChiNhanh cn;
    private int change = 1; // =1 là add, !=1 là update
    private List<SanPham> spDelete = new ArrayList<>();
    private List<SanPham> spAdd = new ArrayList<>();
    private boolean isClose = false;
    private boolean isFocus = false;
    private boolean isClick = false;

    public QLKhuyenMai(String tenChucVu) {
        initComponents();
        this.tenChucVu = tenChucVu;
        this.cn = null;
        initComponents();
        init();
        cbbFilterChiNhanh.setModel(new DefaultComboBoxModel(concatenate(new Object[]{"- Tất cả chi nhánh -"}, iKhuyenMai.getAllChiNhanhON().toArray())));
        khuyenMaiViews = iKhuyenMai.getAllKhuyenMai();
        sanPhamAddKM = iKhuyenMai.getAllSP();
        loadDataKhuyenMai();
        loadDataSPToAdd();

    }

    public QLKhuyenMai(String tenChucVu, ChiNhanh cn) {
        this.tenChucVu = tenChucVu;
        this.cn = cn;
        initComponents();

        init();
        khuyenMaiViews = iKhuyenMai.getAllKMByChiNhanh(cn);
        sanPhamAddKM = iKhuyenMai.getAllSPByChiNhanh(cn);
        cbbFilterChiNhanh.setModel(new DefaultComboBoxModel(new Object[]{toChiNhanhView(cn)}));// viet lai repo ChiNhanh thi sua doan nay
        loadDataKhuyenMai();
        loadDataSPToAdd();
        if (!khuyenMaiViews.isEmpty()) {
            fillDataToControls(0);
        }
    }

    private void init() {
        iKhuyenMai = new KhuyenMaiService();
        // Default Components

        btnUpdateKhuyenMai.setEnabled(false);
        rdoFillTatCa.setSelected(true);
        rdoConHan1.setSelected(true);
        rdoThemSp.setSelected(true);
        txtSearchKM.setForeground(Color.GRAY);
        txtSearchSP1.setForeground(Color.GRAY);
        txtSearchSP2.setForeground(Color.GRAY);
//        pnlSPCoKM.setVisible(false);S
        txtNgayBatDau1.setDateFormatString("yyyy-MM-dd");
        txtNgayKetThuc1.setDateFormatString("yyyy-MM-dd");

        dlgSanPham.setSize(660, 370);

        tblSanPhamAdd1.getTableHeader().setDefaultRenderer(new CustomHeader());
        tblSanPhamAdd1.getTableHeader().setPreferredSize(new Dimension(0, 25));

        tblSanPhamAdd2.getTableHeader().setDefaultRenderer(new CustomHeader());
        tblSanPhamAdd2.getTableHeader().setPreferredSize(new Dimension(0, 25));

        tblKhuyenMai.getTableHeader().setDefaultRenderer(new CustomHeader());
        tblKhuyenMai.getTableHeader().setPreferredSize(new Dimension(0, 25));

        tblSanPhamDelete1.getTableHeader().setDefaultRenderer(new CustomHeader());
        tblSanPhamDelete1.getTableHeader().setPreferredSize(new Dimension(0, 25));

        tblSanPhamDelete.getTableHeader().setDefaultRenderer(new CustomHeader());
        tblSanPhamDelete.getTableHeader().setPreferredSize(new Dimension(0, 25));

        jScrollPane7.getViewport().setBackground(Color.WHITE);
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        jScrollPane4.getViewport().setBackground(Color.WHITE);
        jScrollPane8.getViewport().setBackground(Color.WHITE);
        jScrollPane6.getViewport().setBackground(Color.WHITE);

        SVGIconHelperButton.setIcon(jButton1, "icon/plus.svg", 20, 20);
        SVGIconHelperButton.setIcon(btnAddKhuyenMai, "icon/plus.svg", 20, 20);
        SVGIconHelperButton.setIcon(btnUpdateKhuyenMai, "icon/update.svg", 20, 20);
        SVGIconHelperButton.setIcon(btnDeleteKhuyenMai, "icon/plus.svg", 20, 20);
        SVGIconHelperButton.setIcon(btnExportExcel, "icon/update.svg", 20, 20);

    }

    private Object[] concatenate(Object[] a, Object[] b) {
        Collection<Object> result = new ArrayList<Object>(a.length + b.length);
        result.addAll(Arrays.asList(a));
        result.addAll(Arrays.asList(b));
        return result.toArray();
    }

    private ChiNhanhView toChiNhanhView(ChiNhanh cn) {
        return new ChiNhanhView(cn.getId(), cn.getMa());
    }

    private class CustomHeader extends DefaultTableCellRenderer {

        public CustomHeader() {
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            com.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            com.setBackground(new Color(143, 45, 52));
            com.setForeground(Color.WHITE);
            return com;
        }
    }

    /// VIEW
    private void loadDataKhuyenMai() {
        DefaultTableModel model = (DefaultTableModel) tblKhuyenMai.getModel();
        model.setRowCount(0);

        if (khuyenMaiViews != null) {
            for (KhuyenMaiView kmv : khuyenMaiViews) {
                model.addRow(kmv.toDataRow());
            }
        }
    }

    // search and filter khuyen mai
    private void searchAndFilterKM(String search, int trangThai) {
        if (tenChucVu.equalsIgnoreCase("Ông chủ")) {
            int type = cbbFilterChiNhanh.getSelectedIndex(); // type = 0 all, !=0 theo chi nhanh
            if (type == 0) {
                // tim truoc loc sau
                if (search.isBlank()) {
                    if (trangThai == -1) { // -1 all, !-1 la theo trang thai
                        khuyenMaiViews = iKhuyenMai.getAllKhuyenMai();
                    } else {
                        khuyenMaiViews = iKhuyenMai.getAllKMByTrangThai(trangThai);
                    }
                } else {
                    if (trangThai == -1) {
                        khuyenMaiViews = iKhuyenMai.getAllKMByName(search);
                    } else {
                        khuyenMaiViews = iKhuyenMai.getAllKMByNameAndTrangThai(trangThai, search);
                    }
                }
            } else {
                ChiNhanhView cnv = (ChiNhanhView) cbbFilterChiNhanh.getSelectedItem();
                ChiNhanh cn = iKhuyenMai.getChiNhanhById(cnv.getId());
                if (search.isBlank()) {
                    if (trangThai == -1) {
                        khuyenMaiViews = iKhuyenMai.getAllKMByChiNhanh(cn);
                    } else {
                        khuyenMaiViews = iKhuyenMai.getAllKMByChiNhanhAndTrangThai(cn, trangThai);
                    }
                } else {
                    if (trangThai == -1) {
                        khuyenMaiViews = iKhuyenMai.getAllKMByChiNhanhAndName(cn, search);
                    } else {
                        khuyenMaiViews = iKhuyenMai.getAllKMByChiNhanhAndTrangThaiAndName(cn, trangThai, search);
                    }
                }
            }
        } else {
            if (search.isBlank()) {
                if (trangThai == -1) {
                    khuyenMaiViews = iKhuyenMai.getAllKMByChiNhanh(cn);
                } else {
                    khuyenMaiViews = iKhuyenMai.getAllKMByChiNhanhAndTrangThai(cn, trangThai);
                }
            } else {
                if (trangThai == -1) {
                    khuyenMaiViews = iKhuyenMai.getAllKMByChiNhanhAndName(cn, search);
                } else {
                    khuyenMaiViews = iKhuyenMai.getAllKMByChiNhanhAndTrangThaiAndName(cn, trangThai, search);
                }
            }
        }
    }

    private void searchAndFilterSPAdd(String search) {
        if (tenChucVu.equalsIgnoreCase("Ông chủ")) {
            int type = cbbFilterChiNhanh.getSelectedIndex();
            if (type == 0) {
                if (search.isBlank()) {
                    sanPhamAddKM = iKhuyenMai.getAllSP();
                } else {
                    if (iKhuyenMai.getAllSPByMa(search).isEmpty()) {
                        sanPhamAddKM = iKhuyenMai.getAllSPByName(search);
                    } else {
                        sanPhamAddKM = iKhuyenMai.getAllSPByMa(search);
                    }
                }
            } else {
                ChiNhanhView cnv = (ChiNhanhView) cbbFilterChiNhanh.getSelectedItem();
                ChiNhanh chiNhanh = iKhuyenMai.getChiNhanhById(cnv.getId());
                if (search.isBlank()) {
                    sanPhamAddKM = iKhuyenMai.getAllSPByChiNhanh(chiNhanh);
                } else {
                    if (iKhuyenMai.getAllSPByChiNhanhAndMa(chiNhanh, search).isEmpty()) {
                        sanPhamAddKM = iKhuyenMai.getAllSPByChiNhanhAndName(chiNhanh, search);
                    } else {
                        sanPhamAddKM = iKhuyenMai.getAllSPByChiNhanhAndMa(chiNhanh, search);
                    }
                }
            }
        } else {
            if (search.isBlank()) {
                sanPhamAddKM = iKhuyenMai.getAllSPByChiNhanh(cn);
            } else {
                if (iKhuyenMai.getAllSPByChiNhanhAndMa(cn, search).isEmpty()) {
                    sanPhamAddKM = iKhuyenMai.getAllSPByChiNhanhAndName(cn, search);
                } else {
                    sanPhamAddKM = iKhuyenMai.getAllSPByChiNhanhAndMa(cn, search);
                }
            }
        }
    }

    /// SAN PHAM
    private void loadDataSPToAdd() {
        DefaultTableModel model = (DefaultTableModel) tblSanPhamAdd1.getModel();
        DefaultTableModel model1 = (DefaultTableModel) tblSanPhamAdd2.getModel();
        model.setRowCount(0);
        model1.setRowCount(0);
        if (sanPhamAddKM != null) {
            for (SanPham sp : sanPhamAddKM) {
                model.addRow(new Object[]{sp.getId(), sp.getMa(), sp.getTen(), sp.getGiaBan(), Boolean.FALSE});
                model1.addRow(new Object[]{sp.getId(), sp.getMa(), sp.getTen(), sp.getGiaBan(), Boolean.FALSE});
            }
        }
    }

    private void loadDataSPHaveKM(domainmodel.KhuyenMai km) {
        DefaultTableModel model = (DefaultTableModel) tblSanPhamDelete.getModel();
        DefaultTableModel model1 = (DefaultTableModel) tblSanPhamDelete1.getModel();
        model.setRowCount(0);
        model1.setRowCount(0);
        if (tenChucVu.equalsIgnoreCase("Ông chủ")) {
            int type = cbbFilterChiNhanh.getSelectedIndex();
            if (type == 0) {
                sanPhamHaveKM = iKhuyenMai.getAllSPByKhuyenMai(km);
            } else {
                ChiNhanhView cnv = (ChiNhanhView) cbbFilterChiNhanh.getSelectedItem();
                ChiNhanh chiNhanh = iKhuyenMai.getChiNhanhById(cnv.getId());
                sanPhamHaveKM = iKhuyenMai.getAllSPByKhuyenMaiAndChiNhanh(chiNhanh, km);
            }
        } else {
            sanPhamHaveKM = iKhuyenMai.getAllSPByKhuyenMaiAndChiNhanh(cn, km);
        }

        if (sanPhamHaveKM != null) {
            for (SanPham sp : sanPhamHaveKM) {
                model.addRow(new Object[]{sp.getId(), sp.getMa(), sp.getTen(), sp.getGiaBan(), Boolean.TRUE});
                model1.addRow(new Object[]{sp.getId(), sp.getMa(), sp.getTen(), sp.getGiaBan(), Boolean.TRUE});
            }
        }
    }

    private void fillDataToControls(int row) {
        String id = (String) tblKhuyenMai.getValueAt(row, 0);
        KhuyenMaiView kmv = iKhuyenMai.getKhuyenMaiViewById(id);
        domainmodel.KhuyenMai km = iKhuyenMai.getKMById(id);
        if (km != null) {
            txtTenKM1.setText(kmv.getTenKM());
            txtNgayBatDau1.setDate(kmv.getNgayBatDau());
            txtNgayKetThuc1.setDate(kmv.getNgayKetThuc());
            txtChietKhau1.setText(kmv.getChietKhau() + "");
            txtMoTa1.setText(kmv.getMoTa());
            if (kmv.getTrangThai() == 1) {
                rdoConHan1.setSelected(true);
            } else {
                rdoHetHan1.setSelected(true);
            }
            loadDataSPHaveKM(km);
        }
    }

    private Object[] getDataFromControls() {
        return new Object[]{
            txtTenKM1.getText().trim(),
            txtMoTa1.getText().trim(),
            txtNgayBatDau1.getDate(),
            txtNgayKetThuc1.getDate(),
            txtChietKhau1.getText()
        };
    }

    private void resetControl() {
        txtTenKM1.setText("");
        txtNgayBatDau1.setDate(null);
        txtNgayKetThuc1.setDate(null);
        txtChietKhau1.setText("");
        txtMoTa1.setText("");
        rdoConHan1.setSelected(true);
    }

    private domainmodel.KhuyenMai getKMFromControls() {
        return new domainmodel.KhuyenMai(null, txtTenKM1.getText().trim(),
                txtNgayBatDau1.getDate(), txtNgayKetThuc1.getDate(), txtMoTa1.getText(),
                Float.parseFloat(txtChietKhau1.getText().trim()),
                rdoConHan1.isSelected() == true ? 1 : 0
        );
    }

    private List<SanPham> getSPAdd() {// dung cho nut them
        List<SanPham> sanPhams = new ArrayList<>();
        if (!sanPhamAddKM.isEmpty()) {
            for (int i = 0; i < tblSanPhamAdd1.getRowCount(); i++) {
                if (tblSanPhamAdd1.getValueAt(i, 4).equals(Boolean.TRUE)) {
                    String id = tblSanPhamAdd1.getValueAt(i, 0) + "";
                    sanPhams.add(iKhuyenMai.getSPById(id));
                }
            }
        }
        return sanPhams;
    }

    // lay san pham xoa khuyen mai
    private List<SanPham> getSPDelete() { // dung cho nut cap nhat san pham
        List<SanPham> sanPhams = new ArrayList<>();
        if (!sanPhamHaveKM.isEmpty()) {
            for (int i = 0; i < tblSanPhamDelete.getRowCount(); i++) {
                if (tblSanPhamDelete.getValueAt(i, 4).equals(Boolean.FALSE)) {
                    String id = tblSanPhamDelete.getValueAt(i, 0) + "";
                    sanPhams.add(iKhuyenMai.getSPById(id));
                }
            }
        }
        return sanPhams;
    }

    private List<SanPham> getSPDelete1() { // dung cho nut xoa 
        List<SanPham> sanPhams = new ArrayList<>();
        if (!sanPhamHaveKM.isEmpty()) {
            for (int i = 0; i < tblSanPhamDelete.getRowCount(); i++) {
                if (tblSanPhamDelete.getValueAt(i, 4).equals(Boolean.TRUE)) {
                    String id = tblSanPhamDelete.getValueAt(i, 0) + "";
                    sanPhams.add(iKhuyenMai.getSPById(id));
                }
            }
        }
        return sanPhams;
    }

    private List<SanPham> getSPAdd1() {// dung cho nut cap nhat
        spAdd = new ArrayList<>();
        if (tblSanPhamDelete1.getRowCount() > 0) {
            for (int i = 0; i < tblSanPhamDelete1.getRowCount(); i++) {
                if (tblSanPhamDelete1.getValueAt(i, 4).equals(Boolean.TRUE)) {
                    String id = tblSanPhamDelete1.getValueAt(i, 0) + "";
                    spAdd.add(iKhuyenMai.getSPById(id));
                }
            }
        }
        return spAdd;
    }

    private void deleteCommonTwoList() { // xoa phan tu trung nhau trong spAdd va spDelete trong spDelete
        if (!spDelete.isEmpty()) {
            if (!spAdd.isEmpty()) {
//                Integer[] index = new Integer[spDelete.size()];
                int cnt = 0;
                for (int i = 0; i < spDelete.size(); i++) {
                    for (int j = 0; j < spAdd.size(); j++) {
                        if (spAdd.get(j).getId().equalsIgnoreCase(spDelete.get(i).getId())) {
                            cnt++;
                        }
                    }
                }

                if (cnt != 0) {
                    int[] removeIndex = new int[cnt];
                    int cntTemp = 0;
                    for (int i = 0; i < spDelete.size(); i++) {
                        for (int j = 0; j < spAdd.size(); j++) {
                            if (spAdd.get(j).getId().equalsIgnoreCase(spDelete.get(i).getId())) {
                                cntTemp++;
                                if (cntTemp == 1) {
                                    removeIndex[0] = i;
                                } else {
                                    removeIndex[cntTemp - 1] = i;
                                }
                            }
                        }
                    }

                    for (int i = 0; i < removeIndex.length; i++) {
                        spDelete.remove(removeIndex[i] - i);
                    }
                }
            }
        }
    }

    private void addRowToTableDelete1() {// them san pham vao bang delete1
        if (tblSanPhamDelete1.getRowCount() > 0) {
            if (tblSanPhamAdd2.getRowCount() > 0) {
                for (int i = 0; i < tblSanPhamAdd2.getRowCount(); i++) {
                    if (tblSanPhamAdd2.getValueAt(i, 4).equals(Boolean.TRUE)) {
                        int count = 0;
                        for (int j = 0; j < tblSanPhamDelete1.getRowCount(); j++) {
                            if (((String) tblSanPhamDelete1.getValueAt(j, 0)).equalsIgnoreCase((String) tblSanPhamAdd2.getValueAt(i, 0))) {
                                count++;
                            }
                        }
                        if (count == 0) {
                            ((DefaultTableModel) tblSanPhamDelete1.getModel()).addRow(new Object[]{
                                tblSanPhamAdd2.getValueAt(i, 0), tblSanPhamAdd2.getValueAt(i, 1), tblSanPhamAdd2.getValueAt(i, 2), tblSanPhamAdd2.getValueAt(i, 3), tblSanPhamAdd2.getValueAt(i, 4)
                            });
                        }
                    }
                }
            }
        } else {
            if (tblSanPhamAdd2.getRowCount() > 0) {
                for (int i = 0; i < tblSanPhamAdd2.getRowCount(); i++) {
                    if (tblSanPhamAdd2.getValueAt(i, 4).equals(Boolean.TRUE)) {
                        ((DefaultTableModel) tblSanPhamDelete1.getModel()).addRow(new Object[]{
                            tblSanPhamAdd2.getValueAt(i, 0), tblSanPhamAdd2.getValueAt(i, 1), tblSanPhamAdd2.getValueAt(i, 2), tblSanPhamAdd2.getValueAt(i, 3), tblSanPhamAdd2.getValueAt(i, 4)
                        });
                    }
                }
            }
        }
    }

    private void isSelectAllSPAdd(boolean check) {
        if (check == true) {
            if (tblSanPhamAdd1.getRowCount() > 0) {
                for (int i = 0; i < tblSanPhamAdd1.getRowCount(); i++) {
                    tblSanPhamAdd1.setValueAt(true, i, 4);
                }
            }
        } else {
            if (tblSanPhamAdd1.getRowCount() > 0) {
                for (int i = 0; i < tblSanPhamAdd1.getRowCount(); i++) {
                    tblSanPhamAdd1.setValueAt(false, i, 4);
                }
            }
        }
    }

    private void isSelectAllSPAdd1(boolean check) {
        if (check == true) {
            if (tblSanPhamAdd2.getRowCount() > 0) {
                for (int i = 0; i < tblSanPhamAdd2.getRowCount(); i++) {
                    tblSanPhamAdd2.setValueAt(true, i, 4);
                }
            }
        } else {
            if (tblSanPhamAdd2.getRowCount() > 0) {
                for (int i = 0; i < tblSanPhamAdd2.getRowCount(); i++) {
                    tblSanPhamAdd2.setValueAt(false, i, 4);
                }
            }
        }
    }

    private void isSelectAllSPDelete(boolean check) {
        if (check == true) {
            for (int i = 0; i < sanPhamHaveKM.size(); i++) {
                tblSanPhamDelete.setValueAt(false, i, 4);
            }
        } else {
            for (int i = 0; i < sanPhamHaveKM.size(); i++) {
                tblSanPhamDelete.setValueAt(true, i, 4);
            }
        }
    }

    private void isSelectAllSPDelete1(boolean check) {
        if (check == true) {
            for (int i = 0; i < sanPhamHaveKM.size(); i++) {
                tblSanPhamDelete1.setValueAt(false, i, 4);
            }
        } else {
            for (int i = 0; i < sanPhamHaveKM.size(); i++) {
                tblSanPhamDelete1.setValueAt(true, i, 4);
            }
        }
    }

    /// CUD
    private void addKhuyenMai() {
        if (iKhuyenMai.validateDataInput(getDataFromControls()).isBlank()) {
            if (!getSPAdd().isEmpty()) {
                String mes = iKhuyenMai.addKhuyenMai(getKMFromControls(), getSPAdd());
                JOptionPane.showMessageDialog(null, mes);
                if (mes.equalsIgnoreCase("Them thanh cong!")) {
                    resetControl();
                }
                int trangThai = rdoFillTatCa.isSelected() ? -1 : (rdoFillConHan.isSelected() ? 1 : 0);
                searchAndFilterKM("", trangThai);
                loadDataKhuyenMai();
            } else {
                JOptionPane.showMessageDialog(null, "Can chon it nhat mot san pham ap dung!");
            }
        } else {
            JOptionPane.showMessageDialog(null, iKhuyenMai.validateDataInput(getDataFromControls()));
        }
    }

    private void updateKhuyenMai() { // dung cho nut cap nhat san pham
        int row = tblKhuyenMai.getSelectedRow();
        if (row != -1) {
            String id = tblKhuyenMai.getValueAt(row, 0) + "";
            if (iKhuyenMai.validateDataInput1(getDataFromControls(), iKhuyenMai.getKMById(id)).isBlank()) {
                domainmodel.KhuyenMai km = getKMFromControls();
                JOptionPane.showMessageDialog(null, iKhuyenMai.updateKhuyenMai(id, km, getSPAdd(), getSPDelete()));
                int trangThai = rdoFillTatCa.isSelected() ? -1 : (rdoFillConHan.isSelected() ? 1 : 0);
                Color color = txtSearchKM.getForeground();
                if (!color.equals(Color.GRAY)) {
                    String search = txtSearchKM.getText().trim();
                    searchAndFilterKM(search, trangThai);
                } else {
                    searchAndFilterKM("", trangThai);
                }
                loadDataKhuyenMai();
//                if(trangThai == -1){
//                    loadDataSPHaveKM(iKhuyenMai.getKMById(id));
//                } else {
//                    loadDataSPHaveKM(iKhuyenMai.getK);
//                }
                loadDataSPHaveKM(iKhuyenMai.getKMById(id));
            } else {
                JOptionPane.showMessageDialog(null, iKhuyenMai.validateDataInput1(getDataFromControls(), iKhuyenMai.getKMById(id)));
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ban can chon 1 hang trong bang truoc!");
        }
    }

    private void updateKhuyenMai1() { // dung cho nut cap nhat san pham
        int row = tblKhuyenMai.getSelectedRow();
        if (row != -1) {
            String id = tblKhuyenMai.getValueAt(row, 0) + "";
            if (iKhuyenMai.validateDataInput1(getDataFromControls(), iKhuyenMai.getKMById(id)).isBlank()) {
                domainmodel.KhuyenMai km = getKMFromControls();
                JOptionPane.showMessageDialog(null, iKhuyenMai.updateKhuyenMai(id, km, getSPAdd1(), spDelete));
                int trangThai = rdoFillTatCa.isSelected() ? -1 : (rdoFillConHan.isSelected() ? 1 : 0);
                Color color = txtSearchKM.getForeground();
                if (!color.equals(Color.GRAY)) {
                    String search = txtSearchKM.getText().trim();
                    searchAndFilterKM(search, trangThai);
                } else {
                    searchAndFilterKM("", trangThai);
                }
                loadDataKhuyenMai();
//                if(trangThai == -1){
//                    loadDataSPHaveKM(iKhuyenMai.getKMById(id));
//                } else {
//                    loadDataSPHaveKM(iKhuyenMai.getK);
//                }
                loadDataSPHaveKM(iKhuyenMai.getKMById(id));
            } else {
                JOptionPane.showMessageDialog(null, iKhuyenMai.validateDataInput1(getDataFromControls(), iKhuyenMai.getKMById(id)));
            }
        }
    }

//    private void deleteKhuyenMai() { // xoa han
//        int row = tblKhuyenMai.getSelectedRow();
//        if (row != -1) {
//            String id = (String) tblKhuyenMai.getValueAt(row, 0);
//            domainmodel.KhuyenMai km = iKhuyenMai.getKMById(id);
//            JOptionPane.showMessageDialog(null, iKhuyenMai.deleteKM(id, iKhuyenMai.getAllSPByKhuyenMai(km)));
////            khuyenMaiViews.clear();
////            khuyenMaiViews = iKhuyenMai.getAllKhuyenMai();
//            loadDataKhuyenMai();
//        } else {
//            JOptionPane.showMessageDialog(null, "Ban can chon 1 hang trong bang truoc!");
//        }
//    }
    private void deleteKM() { // chuyen trang thai va xoa sp dang co khuyen mai nay
        int row = tblKhuyenMai.getSelectedRow();
        if (row != -1) {
            String id = (String) tblKhuyenMai.getValueAt(row, 0);
            JOptionPane.showMessageDialog(null, iKhuyenMai.deleteKM1(id, getSPDelete1()));
            int trangThai = rdoFillTatCa.isSelected() ? -1 : (rdoFillConHan.isSelected() ? 1 : 0);
            Color color = txtSearchKM.getForeground();
            if (!color.equals(Color.GRAY)) {
                String search = txtSearchKM.getText().trim();
                searchAndFilterKM(search, trangThai);
            } else {
                searchAndFilterKM("", trangThai);
            }
            loadDataKhuyenMai();
            loadDataSPHaveKM(iKhuyenMai.getKMById(id));
        } else {
            JOptionPane.showMessageDialog(null, "Bạn cần chọn một hàng trong bảng !!!");
        }
    }

    private void exportExcelKM() {
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
                for (int i = 0; i < tblKhuyenMai.getRowCount(); i++) {
                    XSSFRow excelRow = excelSheet.createRow(i);
                    for (int j = 0; j < tblKhuyenMai.getColumnCount(); j++) {
                        XSSFCell excelCell = excelRow.createCell(j);
                        excelCell.setCellValue(tblKhuyenMai.getValueAt(i, j).toString());
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
        dlgSanPham = new javax.swing.JDialog();
        pnlAdd1 = new javax.swing.JPanel();
        chkbSelectAllSPAdd2 = new javax.swing.JCheckBox();
        txtSearchSP2 = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblSanPhamAdd2 = new javax.swing.JTable();
        btnGetSPAdd = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        buttonGroup3 = new javax.swing.ButtonGroup();
        cbbFilterChiNhanh = new javax.swing.JComboBox<>();
        rdoFillTatCa = new javax.swing.JRadioButton();
        rdoFillConHan = new javax.swing.JRadioButton();
        rdoFillHetHan = new javax.swing.JRadioButton();
        txtSearchKM = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKhuyenMai = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSanPhamDelete = new javax.swing.JTable();
        btnExportExcel = new javax.swing.JButton();
        panel1 = new fomVe.Panel();
        rdoConHan1 = new javax.swing.JRadioButton();
        rdoHetHan1 = new javax.swing.JRadioButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtMoTa1 = new javax.swing.JTextArea();
        txtNgayBatDau1 = new com.toedter.calendar.JDateChooser();
        txtNgayKetThuc1 = new com.toedter.calendar.JDateChooser();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        rdoThemSp = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        txtTenKM1 = new fomVe.JxText();
        txtChietKhau1 = new fomVe.JxText();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnAddKhuyenMai = new javax.swing.JButton();
        btnUpdateKhuyenMai = new javax.swing.JButton();
        btnDeleteKhuyenMai = new javax.swing.JButton();
        panel2 = new fomVe.Panel();
        pnlAdd = new javax.swing.JPanel();
        chkbSelectAllSPAdd1 = new javax.swing.JCheckBox();
        txtSearchSP1 = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblSanPhamAdd1 = new javax.swing.JTable();
        pnlUpdate = new javax.swing.JPanel();
        chkbBoChonTatCa1 = new javax.swing.JCheckBox();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblSanPhamDelete1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        dlgSanPham.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dlgSanPham.setBackground(new java.awt.Color(220, 204, 186));
        dlgSanPham.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                dlgSanPhamWindowLostFocus(evt);
            }
        });

        pnlAdd1.setBackground(new java.awt.Color(255, 255, 255));

        chkbSelectAllSPAdd2.setForeground(new java.awt.Color(0, 0, 0));
        chkbSelectAllSPAdd2.setText("Chọn tất cả");
        chkbSelectAllSPAdd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkbSelectAllSPAdd2ActionPerformed(evt);
            }
        });

        txtSearchSP2.setText("Tìm theo mã, tên…");
        txtSearchSP2.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtSearchSP2CaretUpdate(evt);
            }
        });
        txtSearchSP2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchSP2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchSP2FocusLost(evt);
            }
        });

        jScrollPane8.setBackground(new java.awt.Color(255, 255, 255));

        tblSanPhamAdd2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Mã sản phẩm", "Tên sản phẩm", "Giá tiền", "Chọn"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblSanPhamAdd2.setRowHeight(25);
        tblSanPhamAdd2.setSelectionBackground(new java.awt.Color(220, 204, 186));
        jScrollPane8.setViewportView(tblSanPhamAdd2);
        if (tblSanPhamAdd2.getColumnModel().getColumnCount() > 0) {
            tblSanPhamAdd2.getColumnModel().getColumn(0).setMinWidth(0);
            tblSanPhamAdd2.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblSanPhamAdd2.getColumnModel().getColumn(0).setMaxWidth(0);
            tblSanPhamAdd2.getColumnModel().getColumn(4).setPreferredWidth(5);
        }

        btnGetSPAdd.setBackground(new java.awt.Color(143, 45, 52));
        btnGetSPAdd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGetSPAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnGetSPAdd.setText("Chọn");
        btnGetSPAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetSPAddActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(143, 45, 52));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Hủy");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAdd1Layout = new javax.swing.GroupLayout(pnlAdd1);
        pnlAdd1.setLayout(pnlAdd1Layout);
        pnlAdd1Layout.setHorizontalGroup(
            pnlAdd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAdd1Layout.createSequentialGroup()
                .addGroup(pnlAdd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAdd1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlAdd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                            .addGroup(pnlAdd1Layout.createSequentialGroup()
                                .addComponent(txtSearchSP2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(chkbSelectAllSPAdd2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlAdd1Layout.createSequentialGroup()
                        .addGap(327, 327, 327)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(pnlAdd1Layout.createSequentialGroup()
                .addGap(209, 209, 209)
                .addComponent(btnGetSPAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlAdd1Layout.setVerticalGroup(
            pnlAdd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAdd1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAdd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkbSelectAllSPAdd2)
                    .addComponent(txtSearchSP2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAdd1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(btnGetSPAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout dlgSanPhamLayout = new javax.swing.GroupLayout(dlgSanPham.getContentPane());
        dlgSanPham.getContentPane().setLayout(dlgSanPhamLayout);
        dlgSanPhamLayout.setHorizontalGroup(
            dlgSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlAdd1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dlgSanPhamLayout.setVerticalGroup(
            dlgSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlAdd1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBackground(new java.awt.Color(51, 51, 51));

        cbbFilterChiNhanh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbFilterChiNhanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbFilterChiNhanhActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoFillTatCa);
        rdoFillTatCa.setForeground(new java.awt.Color(255, 255, 255));
        rdoFillTatCa.setText("Tất cả");
        rdoFillTatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoFillTatCaActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoFillConHan);
        rdoFillConHan.setForeground(new java.awt.Color(255, 255, 255));
        rdoFillConHan.setText("Kích hoạt");
        rdoFillConHan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoFillConHanActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoFillHetHan);
        rdoFillHetHan.setForeground(new java.awt.Color(255, 255, 255));
        rdoFillHetHan.setText("Dừng áp dụng");
        rdoFillHetHan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoFillHetHanActionPerformed(evt);
            }
        });

        txtSearchKM.setText("Tìm theo tên");
        txtSearchKM.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtSearchKMCaretUpdate(evt);
            }
        });
        txtSearchKM.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchKMFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchKMFocusLost(evt);
            }
        });

        tblKhuyenMai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Tên chương trình", "Ghi chú", "Ngày bắt đầu", "Ngày kết thúc", "Giá trị chiết khấu", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhuyenMai.setRowHeight(25);
        tblKhuyenMai.setSelectionBackground(new java.awt.Color(220, 204, 186));
        tblKhuyenMai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhuyenMaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKhuyenMai);
        if (tblKhuyenMai.getColumnModel().getColumnCount() > 0) {
            tblKhuyenMai.getColumnModel().getColumn(0).setMinWidth(0);
            tblKhuyenMai.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblKhuyenMai.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        tblSanPhamDelete.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Mã sản phẩm", "Tên sản phẩm", "Giá tiền", "Áp dụng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPhamDelete.setRowHeight(25);
        tblSanPhamDelete.setSelectionBackground(new java.awt.Color(220, 204, 186));
        jScrollPane4.setViewportView(tblSanPhamDelete);
        if (tblSanPhamDelete.getColumnModel().getColumnCount() > 0) {
            tblSanPhamDelete.getColumnModel().getColumn(0).setMinWidth(0);
            tblSanPhamDelete.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblSanPhamDelete.getColumnModel().getColumn(0).setMaxWidth(0);
            tblSanPhamDelete.getColumnModel().getColumn(4).setPreferredWidth(5);
        }

        btnExportExcel.setBackground(new java.awt.Color(143, 45, 52));
        btnExportExcel.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        btnExportExcel.setForeground(new java.awt.Color(255, 255, 255));
        btnExportExcel.setText("Cập Nhật Sản Phẩm");
        btnExportExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportExcelActionPerformed(evt);
            }
        });

        panel1.setBackground(new java.awt.Color(255, 255, 255));

        buttonGroup2.add(rdoConHan1);
        rdoConHan1.setText("Kích hoạt");

        buttonGroup2.add(rdoHetHan1);
        rdoHetHan1.setText("Dừng áp dụng");

        txtMoTa1.setColumns(20);
        txtMoTa1.setRows(5);
        jScrollPane5.setViewportView(txtMoTa1);

        txtNgayBatDau1.setBackground(new java.awt.Color(255, 255, 255));

        txtNgayKetThuc1.setBackground(new java.awt.Color(255, 255, 255));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Giảm giá sản phẩm" }));

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Bạn muốn :");

        buttonGroup3.add(rdoThemSp);
        rdoThemSp.setForeground(new java.awt.Color(0, 0, 0));
        rdoThemSp.setText("Thêm ");
        rdoThemSp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoThemSpActionPerformed(evt);
            }
        });

        buttonGroup3.add(jRadioButton2);
        jRadioButton2.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton2.setText("Cập nhật ");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        txtTenKM1.setPrompt("Tên khuyến mãi");

        txtChietKhau1.setPrompt("");

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Giảm giá");

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("% sản phẩm");

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Ngày bắt dầu :");

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Ngày kết thúc :");

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Mô tả khuyến mãi :");

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Trạng thái :");

        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Phương thức giảm giá :");

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridLayout(1, 3, 10, 0));

        btnAddKhuyenMai.setBackground(new java.awt.Color(143, 45, 52));
        btnAddKhuyenMai.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        btnAddKhuyenMai.setForeground(new java.awt.Color(255, 255, 255));
        btnAddKhuyenMai.setText("Thêm");
        btnAddKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddKhuyenMaiActionPerformed(evt);
            }
        });
        jPanel2.add(btnAddKhuyenMai);

        btnUpdateKhuyenMai.setBackground(new java.awt.Color(143, 45, 52));
        btnUpdateKhuyenMai.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        btnUpdateKhuyenMai.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateKhuyenMai.setText("Cập Nhật");
        btnUpdateKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateKhuyenMaiActionPerformed(evt);
            }
        });
        jPanel2.add(btnUpdateKhuyenMai);

        btnDeleteKhuyenMai.setBackground(new java.awt.Color(143, 45, 52));
        btnDeleteKhuyenMai.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        btnDeleteKhuyenMai.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteKhuyenMai.setText("Xóa ");
        btnDeleteKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteKhuyenMaiActionPerformed(evt);
            }
        });
        jPanel2.add(btnDeleteKhuyenMai);

        panel2.setBackground(new java.awt.Color(51, 51, 51));
        panel2.setLayout(new java.awt.CardLayout());

        pnlAdd.setBackground(new java.awt.Color(51, 51, 51));
        pnlAdd.setOpaque(false);

        chkbSelectAllSPAdd1.setForeground(new java.awt.Color(255, 255, 255));
        chkbSelectAllSPAdd1.setText("Chọn tất cả");
        chkbSelectAllSPAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkbSelectAllSPAdd1ActionPerformed(evt);
            }
        });

        txtSearchSP1.setText("Tìm theo mã, tên…");
        txtSearchSP1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtSearchSP1CaretUpdate(evt);
            }
        });
        txtSearchSP1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchSP1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchSP1FocusLost(evt);
            }
        });
        txtSearchSP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchSP1ActionPerformed(evt);
            }
        });

        jScrollPane7.setBackground(new java.awt.Color(255, 255, 255));

        tblSanPhamAdd1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Mã sản phẩm", "Tên sản phẩm", "Giá tiền", "Chọn"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPhamAdd1.setRowHeight(25);
        tblSanPhamAdd1.setSelectionBackground(new java.awt.Color(220, 204, 186));
        jScrollPane7.setViewportView(tblSanPhamAdd1);
        if (tblSanPhamAdd1.getColumnModel().getColumnCount() > 0) {
            tblSanPhamAdd1.getColumnModel().getColumn(0).setMinWidth(0);
            tblSanPhamAdd1.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblSanPhamAdd1.getColumnModel().getColumn(0).setMaxWidth(0);
            tblSanPhamAdd1.getColumnModel().getColumn(1).setResizable(false);
            tblSanPhamAdd1.getColumnModel().getColumn(4).setPreferredWidth(1);
        }

        javax.swing.GroupLayout pnlAddLayout = new javax.swing.GroupLayout(pnlAdd);
        pnlAdd.setLayout(pnlAddLayout);
        pnlAddLayout.setHorizontalGroup(
            pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnlAddLayout.createSequentialGroup()
                        .addComponent(txtSearchSP1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chkbSelectAllSPAdd1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlAddLayout.setVerticalGroup(
            pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearchSP1, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(chkbSelectAllSPAdd1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panel2.add(pnlAdd, "card4");

        pnlUpdate.setBackground(new java.awt.Color(51, 51, 51));
        pnlUpdate.setOpaque(false);

        chkbBoChonTatCa1.setText("Bỏ chọn tất cả");
        chkbBoChonTatCa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkbBoChonTatCa1ActionPerformed(evt);
            }
        });

        tblSanPhamDelete1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Mã sản phẩm", "Tên sản phẩm", "Giá tiền", "Áp dụng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblSanPhamDelete1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamDelete1MouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblSanPhamDelete1);

        jButton1.setBackground(new java.awt.Color(108, 83, 54));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Thêm sản phẩm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlUpdateLayout = new javax.swing.GroupLayout(pnlUpdate);
        pnlUpdate.setLayout(pnlUpdateLayout);
        pnlUpdateLayout.setHorizontalGroup(
            pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUpdateLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnlUpdateLayout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chkbBoChonTatCa1)))
                .addContainerGap())
        );
        pnlUpdateLayout.setVerticalGroup(
            pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUpdateLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(chkbBoChonTatCa1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panel2.add(pnlUpdate, "card3");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addComponent(txtTenKM1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(rdoConHan1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdoHetHan1))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdoThemSp)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton2))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jComboBox1, 0, 165, Short.MAX_VALUE)
                                        .addComponent(txtNgayBatDau1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel4))
                                .addGap(66, 66, 66)
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(txtNgayKetThuc1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtChietKhau1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel3))))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 118, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(rdoThemSp)
                    .addComponent(jRadioButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenKM1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoConHan1)
                    .addComponent(rdoHetHan1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(txtNgayBatDau1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtChietKhau1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addComponent(txtNgayKetThuc1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Chọn chi nhánh :");

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Trạng thái :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSearchKM, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 709, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 709, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnExportExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rdoFillTatCa, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdoFillConHan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdoFillHetHan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cbbFilterChiNhanh, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbbFilterChiNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoFillTatCa)
                            .addComponent(rdoFillConHan)
                            .addComponent(rdoFillHetHan)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSearchKM, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExportExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbbFilterChiNhanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbFilterChiNhanhActionPerformed
        int trangThai = rdoFillTatCa.isSelected() ? -1 : (rdoFillConHan.isSelected() ? 1 : 0);
        if (txtSearchKM.getForeground().equals(Color.BLACK)) {
            String search = txtSearchKM.getText().trim();
            searchAndFilterKM(search, trangThai);
        } else {
            searchAndFilterKM("", trangThai);
        }
        searchAndFilterSPAdd("");
        loadDataKhuyenMai();
        loadDataSPToAdd();
    }//GEN-LAST:event_cbbFilterChiNhanhActionPerformed

    private void rdoFillConHanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoFillConHanActionPerformed
        rdoFillTatCaActionPerformed(evt);
    }//GEN-LAST:event_rdoFillConHanActionPerformed

    private void rdoFillHetHanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoFillHetHanActionPerformed
        rdoFillTatCaActionPerformed(evt);
    }//GEN-LAST:event_rdoFillHetHanActionPerformed

    private void rdoFillTatCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoFillTatCaActionPerformed
        int trangThai = rdoFillTatCa.isSelected() ? -1 : (rdoFillConHan.isSelected() ? 1 : 0);
        Color color = txtSearchKM.getForeground();
        if (!color.equals(Color.GRAY)) {
            String search = txtSearchKM.getText().trim();
            searchAndFilterKM(search, trangThai);
            loadDataKhuyenMai();
        } else {
            searchAndFilterKM("", trangThai);
            loadDataKhuyenMai();
        }
    }//GEN-LAST:event_rdoFillTatCaActionPerformed

    private void txtSearchKMCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtSearchKMCaretUpdate
        int trangThai = rdoFillTatCa.isSelected() ? -1 : (rdoFillConHan.isSelected() ? 1 : 0);
        Color color = txtSearchKM.getForeground();
        if (!color.equals(Color.GRAY)) {
            String search = txtSearchKM.getText().trim();
            searchAndFilterKM(search, trangThai);
            loadDataKhuyenMai();
        }
    }//GEN-LAST:event_txtSearchKMCaretUpdate

    private void txtSearchKMFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchKMFocusGained
        if (txtSearchKM.getForeground().equals(Color.GRAY)) {
            txtSearchKM.setText("");
            txtSearchKM.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_txtSearchKMFocusGained

    private void txtSearchKMFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchKMFocusLost
        if (txtSearchKM.getText().isBlank()) {
            txtSearchKM.setText("Tìm theo tên...");
            txtSearchKM.setForeground(Color.GRAY);
            int trangThai = rdoFillTatCa.isSelected() ? -1 : (rdoFillConHan.isSelected() ? 1 : 0);
            searchAndFilterKM("", trangThai);
            loadDataKhuyenMai();
        }
    }//GEN-LAST:event_txtSearchKMFocusLost

    private void tblKhuyenMaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhuyenMaiMouseClicked
        int row = tblKhuyenMai.getSelectedRow();
        if (row != -1) {

            fillDataToControls(row);
            if ((tblKhuyenMai.getValueAt(row, 6) + "").equalsIgnoreCase("Kích hoạt")) {
                btnDeleteKhuyenMai.setEnabled(true);
            } else {
                btnDeleteKhuyenMai.setEnabled(false);
            }
        } else {
            //  btnUpdateKhuyenMai.setEnabled(false);
        }
    }//GEN-LAST:event_tblKhuyenMaiMouseClicked

    private void btnExportExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportExcelActionPerformed
        //        exportExcelKM();
        updateKhuyenMai();
        btnUpdateKhuyenMai.setEnabled(false);
    }//GEN-LAST:event_btnExportExcelActionPerformed

    private void chkbSelectAllSPAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkbSelectAllSPAdd1ActionPerformed
        isSelectAllSPAdd(chkbSelectAllSPAdd1.isSelected());
    }//GEN-LAST:event_chkbSelectAllSPAdd1ActionPerformed

    private void txtSearchSP1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtSearchSP1CaretUpdate
        Color color = txtSearchSP1.getForeground();
        if (!color.equals(Color.GRAY)) {
            searchAndFilterSPAdd(txtSearchSP1.getText().trim());
            loadDataSPToAdd();
        }
    }//GEN-LAST:event_txtSearchSP1CaretUpdate

    private void txtSearchSP1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchSP1FocusGained
        Color color = txtSearchSP1.getForeground();
        if (!color.equals(Color.BLACK)) {
            txtSearchSP1.setText("");
            txtSearchSP1.setForeground(Color.BLACK);
            searchAndFilterSPAdd("");
            loadDataSPToAdd();
        }
    }//GEN-LAST:event_txtSearchSP1FocusGained

    private void txtSearchSP1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchSP1FocusLost
        if (txtSearchSP1.getText().isBlank()) {
            txtSearchSP1.setText("Tìm theo mã, tên…");
            txtSearchSP1.setForeground(Color.GRAY);
            searchAndFilterSPAdd("");
            loadDataSPToAdd();
        }
    }//GEN-LAST:event_txtSearchSP1FocusLost

    private void txtSearchSP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchSP1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchSP1ActionPerformed

    private void chkbBoChonTatCa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkbBoChonTatCa1ActionPerformed
        isSelectAllSPDelete1(chkbBoChonTatCa1.isSelected());
    }//GEN-LAST:event_chkbBoChonTatCa1ActionPerformed

    private void tblSanPhamDelete1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamDelete1MouseClicked
        int row = tblSanPhamDelete1.getSelectedRow();
        int col = tblSanPhamDelete1.getSelectedColumn();
        if (row != -1 && col != -1) {
            if (col == 4) {
                if (tblSanPhamDelete1.getValueAt(row, col).equals(Boolean.FALSE)) {
                    String id = tblSanPhamDelete1.getValueAt(row, 0) + "";
                    if (spDelete.isEmpty()) {
                        spDelete.add(iKhuyenMai.getSPById(id));
                    } else {
                        int cnt = 0;
                        for (int i = 0; i < spDelete.size(); i++) {
                            if (spDelete.get(i).getId().equalsIgnoreCase(id)) {
                                cnt++;
                            }
                        }
                        if (cnt == 0) {
                            spDelete.add(iKhuyenMai.getSPById(id));
                        }
                    }
                    ((DefaultTableModel) tblSanPhamDelete1.getModel()).removeRow(row);
                }
            }
        }
    }//GEN-LAST:event_tblSanPhamDelete1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //        dlgAddAndUpdateKM.setVisible(true);
        isClick = true;
        //        dlgAddAndUpdateKM.setVisible(true);// no vao su kien window focus luons
        dlgSanPham.setVisible(true);
        dlgSanPham.setLocationRelativeTo(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void chkbSelectAllSPAdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkbSelectAllSPAdd2ActionPerformed
        isSelectAllSPAdd1(chkbSelectAllSPAdd2.isSelected());
    }//GEN-LAST:event_chkbSelectAllSPAdd2ActionPerformed

    private void txtSearchSP2CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtSearchSP2CaretUpdate
        Color color = txtSearchSP2.getForeground();
        if (!color.equals(Color.GRAY)) {
            searchAndFilterSPAdd(txtSearchSP2.getText().trim());
            loadDataSPToAdd();
        }
    }//GEN-LAST:event_txtSearchSP2CaretUpdate

    private void txtSearchSP2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchSP2FocusGained
        Color color = txtSearchSP2.getForeground();
        if (!color.equals(Color.BLACK)) {
            txtSearchSP2.setText("");
            txtSearchSP2.setForeground(Color.BLACK);
            searchAndFilterSPAdd("");
            loadDataSPToAdd();
        }
    }//GEN-LAST:event_txtSearchSP2FocusGained

    private void txtSearchSP2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchSP2FocusLost
        if (txtSearchSP2.getText().isBlank()) {
            txtSearchSP2.setText("Tìm theo mã, tên…");
            txtSearchSP2.setForeground(Color.GRAY);
            searchAndFilterSPAdd("");
            loadDataSPToAdd();
        }
    }//GEN-LAST:event_txtSearchSP2FocusLost

    private void btnGetSPAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetSPAddActionPerformed
        addRowToTableDelete1();
        dlgSanPham.setVisible(false);
        isSelectAllSPAdd1(false);
    }//GEN-LAST:event_btnGetSPAddActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        dlgSanPham.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void dlgSanPhamWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_dlgSanPhamWindowLostFocus
//        isClick = false;
//        dlgSanPham.dispose();
//        if () {
//            dlgAddAndUpdateKM.dispose();
//        }
//                if(evt.getOppositeWindow().equals(dlgAddAndUpdateKM)) {
//                    System.out.println("true");
//                } else {
//                    System.out.println("false");
//                }
    }//GEN-LAST:event_dlgSanPhamWindowLostFocus

    private void btnAddKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddKhuyenMaiActionPerformed
        addKhuyenMai();
    }//GEN-LAST:event_btnAddKhuyenMaiActionPerformed

    private void btnUpdateKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateKhuyenMaiActionPerformed
        updateKhuyenMai();
    }//GEN-LAST:event_btnUpdateKhuyenMaiActionPerformed

    private void btnDeleteKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteKhuyenMaiActionPerformed
        deleteKM();
    }//GEN-LAST:event_btnDeleteKhuyenMaiActionPerformed

    private void rdoThemSpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoThemSpActionPerformed
        resetControl();
        searchAndFilterSPAdd("");
        loadDataSPToAdd();
        pnlAdd.setVisible(true);
        pnlUpdate.setVisible(false);
        btnUpdateKhuyenMai.setEnabled(false);
        btnAddKhuyenMai.setEnabled(true);
        change = 1;
    }//GEN-LAST:event_rdoThemSpActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        int row = tblKhuyenMai.getSelectedRow();
//        if (row != -1) {
        btnUpdateKhuyenMai.setEnabled(true);
        btnAddKhuyenMai.setEnabled(false);
        pnlUpdate.setVisible(true);
        pnlAdd.setVisible(false);
        fillDataToControls(row);

        // }
        change = 2;
    }//GEN-LAST:event_jRadioButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddKhuyenMai;
    private javax.swing.JButton btnDeleteKhuyenMai;
    private javax.swing.JButton btnExportExcel;
    private javax.swing.JButton btnGetSPAdd;
    private javax.swing.JButton btnUpdateKhuyenMai;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JComboBox<String> cbbFilterChiNhanh;
    private javax.swing.JCheckBox chkbBoChonTatCa1;
    private javax.swing.JCheckBox chkbSelectAllSPAdd1;
    private javax.swing.JCheckBox chkbSelectAllSPAdd2;
    private javax.swing.JDialog dlgSanPham;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private fomVe.Panel panel1;
    private fomVe.Panel panel2;
    private javax.swing.JPanel pnlAdd;
    private javax.swing.JPanel pnlAdd1;
    private javax.swing.JPanel pnlUpdate;
    private javax.swing.JRadioButton rdoConHan1;
    private javax.swing.JRadioButton rdoFillConHan;
    private javax.swing.JRadioButton rdoFillHetHan;
    private javax.swing.JRadioButton rdoFillTatCa;
    private javax.swing.JRadioButton rdoHetHan1;
    private javax.swing.JRadioButton rdoThemSp;
    private javax.swing.JTable tblKhuyenMai;
    private javax.swing.JTable tblSanPhamAdd1;
    private javax.swing.JTable tblSanPhamAdd2;
    private javax.swing.JTable tblSanPhamDelete;
    private javax.swing.JTable tblSanPhamDelete1;
    private fomVe.JxText txtChietKhau1;
    private javax.swing.JTextArea txtMoTa1;
    private com.toedter.calendar.JDateChooser txtNgayBatDau1;
    private com.toedter.calendar.JDateChooser txtNgayKetThuc1;
    private javax.swing.JTextField txtSearchKM;
    private javax.swing.JTextField txtSearchSP1;
    private javax.swing.JTextField txtSearchSP2;
    private fomVe.JxText txtTenKM1;
    // End of variables declaration//GEN-END:variables
}
