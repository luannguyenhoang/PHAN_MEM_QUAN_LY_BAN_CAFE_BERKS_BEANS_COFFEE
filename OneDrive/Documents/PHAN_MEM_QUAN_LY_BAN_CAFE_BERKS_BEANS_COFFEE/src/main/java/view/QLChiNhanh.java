/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import domainmodel.ChiNhanh;
import domainmodel.TaiKhoanAdmin;
import domainmodel.TaiKhoanNguoiDung;
import fomVe.CustomHeaderRenderer;
import fomVe.SVGIconHelper;
import fomVe.SVGIconHelperButton;
import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.IChiNhanh;
import service.IKhuVucService;
import service.implement.ChiNhanhSevice;
import service.implement.KhuVucService;
import viewmodel.ChiNhanhVM_Long;
import viewmodel.KhuVucViewModel;
import viewmodel.NhanVienVM_Long;

/**
 *
 * @author hoang
 */
public class QLChiNhanh extends javax.swing.JPanel {

    private DefaultTableModel dtm = new DefaultTableModel();
    private List<ChiNhanhVM_Long> lstcn = new ArrayList<>();
    private List<NhanVienVM_Long> lstnv = new ArrayList<>();
    private IChiNhanh chiNhanhS = new ChiNhanhSevice();
    private DefaultComboBoxModel<KhuVucViewModel> modelComBokv;
    private IKhuVucService cnS = new KhuVucService();
    private DefaultTableModel dtmNV = new DefaultTableModel();

    public QLChiNhanh(TaiKhoanAdmin admin, TaiKhoanNguoiDung nguoiDung) {
        initComponents();
        tblBang.setModel(dtm);
        String[] header = {"ID", "Mã", "Quốc Gia", "Thành Phố", "Ngày Khai Trương", "Điểm", "Đổi Điểm", "Trạng Thái"};
        dtm.setColumnIdentifiers(header);
        tblBang.getColumnModel().getColumn(0).setMinWidth(0);
        tblBang.getColumnModel().getColumn(0).setMaxWidth(0);
        lstcn = chiNhanhS.getAll();
        SVGIconHelper.setIcon(lblGoiY, "icon/light.svg", 20, 20);
        SVGIconHelperButton.setIcon(btnSUa, "icon/update.svg", 25, 25);

        SVGIconHelperButton.setIcon(btnThem, "icon/plus.svg", 25, 25);
        SVGIconHelperButton.setIcon(btnXoa, "icon/delete.svg", 25, 25);

        SVGIconHelperButton.setIcon(btnXuatEx, "icon/excel.svg", 25, 25);

        JTableHeader tableHeader2 = tblBang.getTableHeader();
        tableHeader2.setBackground(new Color(143, 45, 52));
        tableHeader2.setForeground(Color.WHITE);
        tableHeader2.setBorder(null);
        TableCellRenderer defaultHeaderRenderer2 = tblBang.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer2 = new CustomHeaderRenderer(defaultHeaderRenderer2);
        tblBang.getTableHeader().setDefaultRenderer(customHeaderRenderer2);

        JTableHeader tableHeader3 = tblNV.getTableHeader();
        tableHeader3.setBackground(new Color(143, 45, 52));
        tableHeader3.setForeground(Color.WHITE);
        tableHeader3.setBorder(null);
        TableCellRenderer defaultHeaderRenderer3 = tblNV.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer3 = new CustomHeaderRenderer(defaultHeaderRenderer3);
        tblNV.getTableHeader().setDefaultRenderer(customHeaderRenderer3);

        tblNV.setModel(dtmNV);
        String[] header1 = {"Mã", "Tên", "Chi Nhánh", "Trạng Thái"};
        dtmNV.setColumnIdentifiers(header1);
        lstnv = chiNhanhS.getAllNV();

        modelComBokv = (DefaultComboBoxModel) new DefaultComboBoxModel<>(cnS.getAllChiNhanh().toArray());
        show(lstcn);
        showNV(lstnv);
    }

    private void showNV(List<NhanVienVM_Long> lst) {
        dtmNV.setRowCount(0);
        for (NhanVienVM_Long cn : lst) {
//            dtm.addRow(new Object[]{cn.getMa(), cn.getQuocGia(), cn.getThanhPho(), cn.getNgayKhaiTruong(), cn.getGiaTriDiem(), cn.getGiaTriDoiDiem(),cn.getTrangThai()==1?"Ðang hoat dong":"DungHoatDong"
            dtmNV.addRow(new Object[]{cn.getMa(), cn.getTen(), cn.getIdcn(), cn.getTrangThai() == 0 ? "Đã Nghỉ" : "Còn Làm"});

        }
    }

    private void show(List<ChiNhanhVM_Long> lst) {
        dtm.setRowCount(0);
        for (ChiNhanhVM_Long cn : lst) {
            dtm.addRow(new Object[]{cn.getId(), cn.getMa(), cn.getQuocGia(), cn.getThanhPho(), cn.getNgayKhaiTruong(), cn.getGiaTriDiem(), cn.getGiaTriDoiDiem(), cn.getTrangThai() == 1 ? "Ðang Hoạt Động" : "Dừng Hoạt Động"
            });
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

    private boolean checkFormEmptyDiem(JTextField ma, JTextField ten) {
        if (ma.getText().isBlank() || ten.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Không được trống");
            return false;
        } else {
            return true;
        }
    }

    public void fill(int index, List<ChiNhanhVM_Long> lst) {
        ChiNhanhVM_Long cn = lst.get(index);
        txtMa.setText(cn.getMa());
        txtThanhPho.setText(cn.getThanhPho());
        txtQuocGia.setText(cn.getQuocGia());
        if (cn.getTrangThai() != null) {
            if (cn.getTrangThai() == 1) {
                rdoDHD.setSelected(true);
            } else {
                rdoDungHD.setSelected(true);
            }
        }
        txtDoiDiem.setText(String.valueOf(cn.getGiaTriDiem()));
        txtDiem.setText(String.valueOf(cn.getGiaTriDoiDiem()));
        txtNgayKC.setDate(cn.getNgayKhaiTruong());
//        txtNgayKC.setText(String.valueOf(cn.getNgayKhaiTruong()));
    }

    private boolean checkMaSp(String maSp) {
        if (chiNhanhS.getChiNhanh(maSp) != null) {
            JOptionPane.showMessageDialog(this, "Mã đã tồn tại");
            return true;
        } else {
            return false;
        }
    }

    public boolean checkChu(String mark) {
        Pattern regexInt = Pattern.compile("[a-zA-Z]+");
        Pattern regexDouble = Pattern.compile("[a-zA-Z]+");
        if (!regexInt.matcher(mark).find() && !regexDouble.matcher(mark).find()) {
            JOptionPane.showMessageDialog(this, "chu ban oi");
            return false;
        } else {
            return true;
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
        jPanel1 = new javax.swing.JPanel();
        txtMa = new fomVe.JxText();
        jLabel1 = new javax.swing.JLabel();
        txtNgayKC = new com.toedter.calendar.JDateChooser();
        txtThanhPho = new fomVe.JxText();
        txtQuocGia = new fomVe.JxText();
        jPanel2 = new javax.swing.JPanel();
        rdoDHD = new javax.swing.JRadioButton();
        rdoDungHD = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNV = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSUa = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnXuatEx = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBang = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        txtDiem = new fomVe.JxText();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        txtDoiDiem = new fomVe.JxText();
        jLabel12 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblGoiY = new javax.swing.JLabel();
        lblThongBao = new javax.swing.JLabel();

        jPanel1.setLayout(new java.awt.GridLayout(6, 1, 0, 5));

        txtMa.setPrompt("Mã chi nhánh");
        jPanel1.add(txtMa);

        jLabel1.setText("Ngày khai trương:");
        jPanel1.add(jLabel1);

        txtNgayKC.setBackground(new java.awt.Color(255, 255, 255));
        txtNgayKC.setOpaque(false);
        jPanel1.add(txtNgayKC);

        txtThanhPho.setPrompt("Thành phố");
        jPanel1.add(txtThanhPho);

        txtQuocGia.setPrompt("Quốc gia");
        txtQuocGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuocGiaActionPerformed(evt);
            }
        });
        jPanel1.add(txtQuocGia);

        buttonGroup1.add(rdoDHD);
        rdoDHD.setText("Đang Hoạt Động");

        buttonGroup1.add(rdoDungHD);
        rdoDungHD.setText("Dừng Hoạt Động");
        rdoDungHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDungHDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(rdoDHD)
                .addGap(28, 28, 28)
                .addComponent(rdoDungHD)
                .addGap(0, 288, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rdoDungHD, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
            .addComponent(rdoDHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2);

        tblNV.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblNV);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setText("Chọn nhân viên :");

        jPanel3.setLayout(new java.awt.GridLayout(1, 4, 5, 0));

        btnThem.setBackground(new java.awt.Color(143, 45, 52));
        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel3.add(btnThem);

        btnSUa.setBackground(new java.awt.Color(143, 45, 52));
        btnSUa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSUa.setForeground(new java.awt.Color(255, 255, 255));
        btnSUa.setText("Sửa");
        btnSUa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSUaActionPerformed(evt);
            }
        });
        jPanel3.add(btnSUa);

        btnXoa.setBackground(new java.awt.Color(143, 45, 52));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel3.add(btnXoa);

        btnXuatEx.setBackground(new java.awt.Color(10, 118, 64));
        btnXuatEx.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXuatEx.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatEx.setText("Excel");
        btnXuatEx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExActionPerformed(evt);
            }
        });
        jPanel3.add(btnXuatEx);

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

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Bảng Chi Nhánh :");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridLayout(2, 0, 0, 6));

        jPanel5.setBackground(new java.awt.Color(51, 255, 51));
        jPanel5.setOpaque(false);

        txtDiem.setPrompt("VND");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 153, 255));
        jLabel3.setText("= 1 Điểm");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setText("Tỷ Lệ Quy Đổi Điểm Thưởng :");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtDiem, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 51));
        jPanel6.setOpaque(false);

        txtDoiDiem.setPrompt("VND");

        jLabel12.setText("VND");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setText("Thanh Toán Bằng Điểm :");

        jLabel9.setBackground(new java.awt.Color(255, 255, 102));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(153, 153, 255));
        jLabel9.setText("1 Điểm =");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDoiDiem, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addGap(12, 12, 12))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtDoiDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.add(jPanel6);

        lblGoiY.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblGoiYMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblGoiYMouseExited(evt);
            }
        });

        lblThongBao.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblThongBao.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblGoiY, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblThongBao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel16)
                    .addComponent(lblGoiY, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblThongBao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtQuocGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuocGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuocGiaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        //        JOptionPane.showConfirmDialog(jButton1, "Them");
        if (checkFormEmpty(txtMa, txtQuocGia) && !checkMaSp(txtMa.getText()) && checkChu(txtQuocGia.getText()) && checkChu(txtThanhPho.getText()) && checkFormEmptyDiem(txtDoiDiem, txtDoiDiem)) {
            //            String ngS = txtNgayKC.getText();
            //            Date ng = Date.valueOf(ngS);
            int trangThai;
            if (rdoDHD.isSelected()) {
                trangThai = 1;
            } else {
                trangThai = 0;
            }
            chiNhanhS.insertChiNhanh(txtMa.getText(), txtQuocGia.getText(), txtThanhPho.getText(), txtNgayKC.getDate(), Float.valueOf(txtDoiDiem.getText()), Float.valueOf(txtDoiDiem.getText()), trangThai);
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            lstcn = chiNhanhS.getAll();
            show(chiNhanhS.getAll());
        } else {

        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSUaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSUaActionPerformed

        int row = tblBang.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng click vào Bảng");
        } else {
            if (checkFormEmpty(txtMa, txtQuocGia)) {
                if (txtMa.getText().equals(tblBang.getValueAt(row, 1).toString())) {
                    ChiNhanh mauView = new ChiNhanh();
                    mauView.setMa(tblBang.getValueAt(row, 1).toString());
                    //                    String ngS = txtNgayKC.getText();
                    //            Date ng = Date.valueOf(ngS);
                    int trangThai;
                    if (rdoDHD.isSelected()) {
                        trangThai = 1;
                    } else {
                        trangThai = 0;
                    }
                    chiNhanhS.update(mauView, txtMa.getText(), txtThanhPho.getText(), txtQuocGia.getText(), trangThai, txtNgayKC.getDate());
                    JOptionPane.showMessageDialog(this, "Sửa thành công");
                    lstcn = chiNhanhS.getAll();
                    show(chiNhanhS.getAll());
                }
            }
        }
    }//GEN-LAST:event_btnSUaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed

        //        String ma = lstcn.get(tblBang.getSelectedRow()).getMa();
        //        chiNhanhS.deleteMauSac(txtMa.getText());
        //        lstcn.remove(tblBang.getSelectedRow());
        //        JOptionPane.showMessageDialog(this, "Xóa Thành Công");
        //        lstcn = chiNhanhS.getAll();
        //        show(lstcn);
        int row = tblBang.getSelectedRow();
        //        ibanService.deleteBan(tblBan.getValueAt(row, 0).toString());
        chiNhanhS.deleteCN(tblBang.getValueAt(row, 0).toString());
        JOptionPane.showMessageDialog(this, "Xoa Thành Công");
        //        load_Ban_By_KhuVuc(ibanService.getAllBanByKhuVuc(((KhuVucViewModel) comboKhuVuc.getSelectedItem()).getIdKhuVuc()));
        show(chiNhanhS.getAll());
    }//GEN-LAST:event_btnXoaActionPerformed

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
                for (int i = 0; i < dtm.getRowCount(); i++) {
                    XSSFRow excelRow = excelSheet.createRow(i);
                    for (int j = 0; j < dtm.getColumnCount(); j++) {
                        XSSFCell excelCell = excelRow.createCell(j);
                        excelCell.setCellValue(dtm.getValueAt(i, j).toString());
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

        // TODO add your handling code here:
    }//GEN-LAST:event_btnXuatExActionPerformed

    private void tblBangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangMouseClicked
        int row = tblBang.getSelectedRow();
        fill(row, lstcn);// TODO add your handling code here:
    }//GEN-LAST:event_tblBangMouseClicked

    private void rdoDungHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDungHDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoDungHDActionPerformed

    private void lblGoiYMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGoiYMouseEntered
        lblThongBao.setText("Ví dụ : 100.000 VND tương đương với 1 điểm, và 1 điểm có thể quy đổi thành 1.000 VND.");
    }//GEN-LAST:event_lblGoiYMouseEntered

    private void lblGoiYMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGoiYMouseExited
        lblThongBao.setText("");
    }//GEN-LAST:event_lblGoiYMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSUa;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatEx;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblGoiY;
    private javax.swing.JLabel lblThongBao;
    private javax.swing.JRadioButton rdoDHD;
    private javax.swing.JRadioButton rdoDungHD;
    private javax.swing.JTable tblBang;
    private javax.swing.JTable tblNV;
    private fomVe.JxText txtDiem;
    private fomVe.JxText txtDoiDiem;
    private fomVe.JxText txtMa;
    private com.toedter.calendar.JDateChooser txtNgayKC;
    private fomVe.JxText txtQuocGia;
    private fomVe.JxText txtThanhPho;
    // End of variables declaration//GEN-END:variables
}
