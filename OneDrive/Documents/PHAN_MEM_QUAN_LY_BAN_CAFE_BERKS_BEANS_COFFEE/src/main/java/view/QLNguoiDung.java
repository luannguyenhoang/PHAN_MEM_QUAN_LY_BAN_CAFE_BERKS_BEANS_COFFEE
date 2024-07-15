/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import domainmodel.TaiKhoanAdmin;
import domainmodel.TaiKhoanNguoiDung;
import fomVe.CustomHeaderRenderer;
import fomVe.SVGIconHelperButton;
import java.awt.Color;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import service.ITaiKhoanNguoiDungService;
import service.implement.TaiKhoanNguoiDungService;
import viewmodel.NhanVienViewModel_Van;
import viewmodel.TaiKhoanNguoiDungViewModel;

/**
 *
 * @author hoang
 */
public class QLNguoiDung extends javax.swing.JPanel {

    /**
     * Creates new form QLNguoiDung
     */
    private DefaultTableModel defaultTableModel;
    private DefaultComboBoxModel<NhanVienViewModel_Van> comboBoxNV;
    private ITaiKhoanNguoiDungService iTaiKhoanNguoiDungService;
    TaiKhoanNguoiDungViewModel tkView = new TaiKhoanNguoiDungViewModel();
    List<TaiKhoanNguoiDungViewModel> listTkView = new ArrayList<>();
    List<NhanVienViewModel_Van> listNvView = new ArrayList<>();

    public QLNguoiDung(TaiKhoanAdmin admin, TaiKhoanNguoiDung nguoiDung) {
        initComponents();
        iTaiKhoanNguoiDungService = new TaiKhoanNguoiDungService();
        comboBoxNV = new DefaultComboBoxModel<>();
        // tblNguoiDung.setRowSelectionInterval(0, 0);
        load_TK(iTaiKhoanNguoiDungService.getAllTkNguoiDung());
        SVGIconHelperButton.setIcon(btnLamMoi, "icon/reset.svg", 25, 25);
        SVGIconHelperButton.setIcon(btnThemTK, "icon/plus.svg", 25, 25);
        SVGIconHelperButton.setIcon(btnCapNhatTK, "icon/update.svg", 25, 25);
        SVGIconHelperButton.setIcon(btnXoa, "icon/delete.svg", 25, 25);

        JTableHeader tableHeader = tblNguoiDung.getTableHeader();
        tableHeader.setBackground(new Color(143, 45, 52));
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setBorder(null);
        TableCellRenderer defaultHeaderRenderer = tblNguoiDung.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer = new CustomHeaderRenderer(defaultHeaderRenderer);
        tblNguoiDung.getTableHeader().setDefaultRenderer(customHeaderRenderer);
        addCbNhanVien();
    }

    private void load_TK(List<TaiKhoanNguoiDungViewModel> list) {
        defaultTableModel = (DefaultTableModel) tblNguoiDung.getModel();
        defaultTableModel.setRowCount(0);
        for (TaiKhoanNguoiDungViewModel x : list) {
            defaultTableModel.addRow(x.getDataTK());
        }

    }

    private void addCbNhanVien() {
        listNvView = iTaiKhoanNguoiDungService.getAllNV();
        comboBoxNV = (DefaultComboBoxModel) (new DefaultComboBoxModel<>(listNvView.toArray()));
        cboNhanVien.setModel((DefaultComboBoxModel) comboBoxNV);
    }

    private void fillDataToFormND(int row) {
        txtTenTaiKhoan.setText(tblNguoiDung.getValueAt(row, 1).toString());
        pw.setText(tblNguoiDung.getValueAt(row, 2).toString());
        if (tblNguoiDung.getValueAt(row, 3).toString().equalsIgnoreCase("(chưa có thông tin)")) {
            cboNhanVien.setEnabled(false);
        } else {
            cboNhanVien.setEnabled(true);
            for (int i = 0; i < cboNhanVien.getItemCount(); i++) {
                if (tblNguoiDung.getValueAt(row, 3).toString().equals(comboBoxNV.getElementAt(i).getMaNhanVien())) {
                    cboNhanVien.setSelectedIndex(i);
                    break;
                }
            }
        }

    }

    private boolean checkTenTK(String Tentk) {
        List<TaiKhoanNguoiDungViewModel> list = new ArrayList<>();
        list = iTaiKhoanNguoiDungService.getAllTkNguoiDung();
        for (TaiKhoanNguoiDungViewModel tk : list) {
            if (Tentk.equals(tk.getTenTk())) {
                JOptionPane.showMessageDialog(this, "Tên tài khoản đã tồn tại");
                return false;
            }
        }
        return true;
    }

    private boolean checkFormEmpty(JTextField tentkField, JPasswordField pw) {
        if (tentkField.getText().isBlank() || pw.getPassword().toString().isBlank()) {
            JOptionPane.showMessageDialog(this, "Không được trống");
            return false;
        } else {
            return true;
        }
    }

    private boolean checkTaiKhoanCuaNhanVien(String maNhanVien) {
        List<TaiKhoanNguoiDungViewModel> list = new ArrayList<>();
        list = iTaiKhoanNguoiDungService.getAllTkNguoiDung();
        for (TaiKhoanNguoiDungViewModel tk : list) {
            if (maNhanVien.equals(tk.getMaNhanVien())) {
                JOptionPane.showMessageDialog(this, "Nhân viên này đã có tài khoản");
                return false;
            }
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtTenTaiKhoan = new javax.swing.JTextField();
        pw = new javax.swing.JPasswordField();
        cboNhanVien = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        btnThemTK = new javax.swing.JButton();
        btnCapNhatTK = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        panel31 = new fomVe.Panel3();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNguoiDung = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(51, 51, 51));

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridLayout(3, 1, 0, 20));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tên tài khoản :");
        jPanel1.add(jLabel1);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Mật khẩu :");
        jPanel1.add(jLabel2);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nhân viên :");
        jPanel1.add(jLabel3);

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridLayout(3, 1, 0, 20));
        jPanel2.add(txtTenTaiKhoan);
        jPanel2.add(pw);

        cboNhanVien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cboNhanVien);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridLayout(1, 4, 10, 0));

        btnThemTK.setBackground(new java.awt.Color(155, 49, 56));
        btnThemTK.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnThemTK.setForeground(new java.awt.Color(255, 255, 255));
        btnThemTK.setText("Thêm");
        btnThemTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemTKActionPerformed(evt);
            }
        });
        jPanel3.add(btnThemTK);

        btnCapNhatTK.setBackground(new java.awt.Color(155, 49, 56));
        btnCapNhatTK.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnCapNhatTK.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhatTK.setText("Cập nhật");
        btnCapNhatTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatTKActionPerformed(evt);
            }
        });
        jPanel3.add(btnCapNhatTK);

        btnXoa.setBackground(new java.awt.Color(155, 49, 56));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel3.add(btnXoa);

        btnLamMoi.setBackground(new java.awt.Color(46, 144, 232));
        btnLamMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLamMoiMouseEntered(evt);
            }
        });
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        panel31.setBackground(new java.awt.Color(255, 255, 255));

        tblNguoiDung.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Tài khoản", "Mật khẩu", "Mã nhân viên"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNguoiDung.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNguoiDungMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNguoiDung);
        if (tblNguoiDung.getColumnModel().getColumnCount() > 0) {
            tblNguoiDung.getColumnModel().getColumn(0).setMinWidth(0);
            tblNguoiDung.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblNguoiDung.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        javax.swing.GroupLayout panel31Layout = new javax.swing.GroupLayout(panel31);
        panel31.setLayout(panel31Layout);
        panel31Layout.setHorizontalGroup(
            panel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
        );
        panel31Layout.setVerticalGroup(
            panel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("- Danh sách nhân viên -");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                            .addComponent(btnLamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(465, 465, 465))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemTKActionPerformed
        NhanVienViewModel_Van nvView1 = new NhanVienViewModel_Van();
        if (checkFormEmpty(txtTenTaiKhoan, pw) && checkTenTK(txtTenTaiKhoan.getText())
                && checkTaiKhoanCuaNhanVien(((NhanVienViewModel_Van) cboNhanVien.getSelectedItem()).getMaNhanVien())) {
            NhanVienViewModel_Van nvView = (NhanVienViewModel_Van) comboBoxNV.getSelectedItem();
            iTaiKhoanNguoiDungService.inserttkNguoiDung(txtTenTaiKhoan.getText(), pw.getText(), nvView);
            JOptionPane.showMessageDialog(this, "Thêm Tai Khoan thành công");
            load_TK(iTaiKhoanNguoiDungService.getAllTkNguoiDung());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemTKActionPerformed

    private void tblNguoiDungMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNguoiDungMouseClicked
        int row = tblNguoiDung.getSelectedRow();
        fillDataToFormND(row);
    }//GEN-LAST:event_tblNguoiDungMouseClicked

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int row = tblNguoiDung.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Click on table,please");
        } else {
            iTaiKhoanNguoiDungService.deletetkNguoiDung(tblNguoiDung.getValueAt(row, 0).toString());
            JOptionPane.showMessageDialog(this, "Delete Success");
            load_TK(iTaiKhoanNguoiDungService.getAllTkNguoiDung());
            btnLamMoiActionPerformed(evt);
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        cboNhanVien.setSelectedIndex(0);
        txtTenTaiKhoan.setText("");
        pw.setText("");        // TODO add your handling code here:
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnCapNhatTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatTKActionPerformed
        int row = tblNguoiDung.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Click on table,please");
        } else {
            if (tblNguoiDung.getValueAt(row, 3).toString().
                    equalsIgnoreCase(((NhanVienViewModel_Van) cboNhanVien.getSelectedItem()).getMaNhanVien())) {
                NhanVienViewModel_Van nvView = (NhanVienViewModel_Van) comboBoxNV.getSelectedItem();
                iTaiKhoanNguoiDungService.updateTKNguoiDung(tblNguoiDung.getValueAt(row, 0).toString(), txtTenTaiKhoan.getText(), pw.getText(), nvView);
                JOptionPane.showMessageDialog(this, "Update Success");
                load_TK(iTaiKhoanNguoiDungService.getAllTkNguoiDung());
                btnLamMoiActionPerformed(evt);
            } else {
                if (checkTaiKhoanCuaNhanVien(((NhanVienViewModel_Van) cboNhanVien.getSelectedItem()).getMaNhanVien())) {
                    NhanVienViewModel_Van nvView = (NhanVienViewModel_Van) comboBoxNV.getSelectedItem();
                    iTaiKhoanNguoiDungService.updateTKNguoiDung(tblNguoiDung.getValueAt(row, 0).toString(), txtTenTaiKhoan.getText(), pw.getText(), nvView);
                    JOptionPane.showMessageDialog(this, "Update Success");
                    load_TK(iTaiKhoanNguoiDungService.getAllTkNguoiDung());
                    btnLamMoiActionPerformed(evt);
                }
            }

        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnCapNhatTKActionPerformed

    private void btnLamMoiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLamMoiMouseEntered
       // SVGIconHelperButton.setIcon(btnLamMoi, "icon/reset2.svg", 25, 25);
    }//GEN-LAST:event_btnLamMoiMouseEntered


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhatTK;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnThemTK;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboNhanVien;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private fomVe.Panel3 panel31;
    private javax.swing.JPasswordField pw;
    private javax.swing.JTable tblNguoiDung;
    private javax.swing.JTextField txtTenTaiKhoan;
    // End of variables declaration//GEN-END:variables
}
