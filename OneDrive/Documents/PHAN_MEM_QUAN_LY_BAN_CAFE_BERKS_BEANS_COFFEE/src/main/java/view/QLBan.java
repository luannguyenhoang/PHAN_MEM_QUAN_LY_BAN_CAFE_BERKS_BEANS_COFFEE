/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import domainmodel.TaiKhoanAdmin;
import domainmodel.TaiKhoanNguoiDung;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import fomVe.CustomHeaderRenderer;
import fomVe.SVGIconHelper;
import fomVe.SVGIconHelperButton;
import repository.BanRepository;
import repository.KhuVucRepository;
import service.IBanService;
import service.IKhuVucService;
import service.implement.BanService;
import service.implement.KhuVucService;
import viewmodel.BanViewModel;
import viewmodel.ChiNhanhViewModel_Hoang;
import viewmodel.KhuVucViewModel;

/**
 *
 * @author hoang
 */
public class QLBan extends javax.swing.JPanel {

    private DefaultTableModel defaultTableModel;
    private DefaultComboBoxModel<KhuVucViewModel> comboKhuVuc;
    private DefaultComboBoxModel<ChiNhanhViewModel_Hoang> comboChiNhanh;
    private IBanService ibanService;
    private IKhuVucService iKhuVucService;
    private KhuVucRepository khuVucRepo;
    private BanRepository banRepo;
    private TaiKhoanAdmin taiKhoanAdmin;
    private TaiKhoanNguoiDung taiKhoanNguoiDung;

    KhuVucViewModel kvView = new KhuVucViewModel();
    BanViewModel banView = new BanViewModel();
    List<KhuVucViewModel> listkvView = new ArrayList<>();
    List<BanViewModel> listBanView = new ArrayList<>();
    List<ChiNhanhViewModel_Hoang> listcnView = new ArrayList<>();

    public QLBan(TaiKhoanAdmin admin, TaiKhoanNguoiDung nguoiDung) {
        initComponents();
        banRepo = new BanRepository();
        ibanService = new BanService();
        iKhuVucService = new KhuVucService();
        khuVucRepo = new KhuVucRepository();
        taiKhoanAdmin = admin;
        taiKhoanNguoiDung = nguoiDung;
        SVGIconHelperButton.setIcon(btnThemBan, "icon/plus.svg", 20, 20);
        SVGIconHelperButton.setIcon(btnThemKhuVuc, "icon/plus.svg", 20, 20);
        SVGIconHelperButton.setIcon(btnXoaBan, "icon/delete.svg", 20, 20);
        SVGIconHelperButton.setIcon(btnCapNhatKhuVuc, "icon/update.svg", 20, 20);

        JTableHeader tableHeader = tblBan.getTableHeader();
        tableHeader.setBackground(new Color(143, 45, 52));
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setBorder(null);
        TableCellRenderer defaultHeaderRenderer = tblBan.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer = new CustomHeaderRenderer(defaultHeaderRenderer);
        tblBan.getTableHeader().setDefaultRenderer(customHeaderRenderer);

        JTableHeader tableHeader2 = tblKhuVuc.getTableHeader();
        tableHeader2.setBackground(new Color(143, 45, 52));
        tableHeader2.setForeground(Color.WHITE);
        tableHeader2.setBorder(null);
        TableCellRenderer defaultHeaderRenderer2 = tblKhuVuc.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer2 = new CustomHeaderRenderer(defaultHeaderRenderer2);
        tblKhuVuc.getTableHeader().setDefaultRenderer(customHeaderRenderer2);
        if (taiKhoanAdmin != null) {
            cboChiNhanh.setVisible(true);
            comboChiNhanh = (DefaultComboBoxModel) new DefaultComboBoxModel<>(iKhuVucService.getAllChiNhanh().toArray());
            cboChiNhanh.setModel((DefaultComboBoxModel) comboChiNhanh);
            comboKhuVuc = (DefaultComboBoxModel) new DefaultComboBoxModel<>(
                    iKhuVucService.getAllKhuVucByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId()).toArray());
            loadData(((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId());
        } else {
            cboChiNhanh.setVisible(false);
            comboKhuVuc = (DefaultComboBoxModel) new DefaultComboBoxModel<>(
                    iKhuVucService.getAllKhuVucByChiNhanh(ibanService.getChiNhanhByTaiKhoan(nguoiDung.getId()).getId()).toArray());
            loadData(ibanService.getChiNhanhByTaiKhoan(nguoiDung.getId()).getId());
        }
    }

    private void addComboKhuVucByChiNhah(String idChiNhanh) {
        listkvView = iKhuVucService.getAllKhuVucByChiNhanh(idChiNhanh);
        comboKhuVuc = (DefaultComboBoxModel) (new DefaultComboBoxModel<>(listkvView.toArray()));
        cbKhuVuc.setModel((DefaultComboBoxModel) comboKhuVuc);
    }

    private void load_KhuVuc_By_ChiNhanh(List<KhuVucViewModel> list) {
        defaultTableModel = (DefaultTableModel) tblKhuVuc.getModel();
        defaultTableModel.setRowCount(0);
        for (KhuVucViewModel x : list) {
            defaultTableModel.addRow(x.getDataKhuVuc());
        }
    }

    private void load_Ban_By_KhuVuc(List<BanViewModel> list) {
        defaultTableModel = (DefaultTableModel) tblBan.getModel();
        defaultTableModel.setRowCount(0);
        for (BanViewModel x : list) {
            defaultTableModel.addRow(x.getDataBan());
        }

    }

    private void loadData(String idChiNhanh) {
        load_KhuVuc_By_ChiNhanh(iKhuVucService.getAllKhuVucByChiNhanh(idChiNhanh));
        addComboKhuVucByChiNhah(idChiNhanh);
        if (cbKhuVuc.getItemCount() > 0) {
            load_Ban_By_KhuVuc(ibanService.getAllBanByKhuVuc(((KhuVucViewModel) comboKhuVuc.getSelectedItem()).getIdKhuVuc()));
        } else {
            defaultTableModel = (DefaultTableModel) tblBan.getModel();
            defaultTableModel.setRowCount(0);
        }

    }

    private void fillDataToFormBan(int row) {
        txtSoBan.setText(tblBan.getValueAt(row, 1).toString());
        comboKhuVuc.setSelectedItem((findbyName(tblBan.getModel().getValueAt(row, 2).toString())));
    }

    private KhuVucViewModel findbyName(String MaKV) {
        for (int i = 0; i < comboKhuVuc.getSize(); i++) {
            KhuVucViewModel kv = comboKhuVuc.getElementAt(i);
            if (MaKV.equalsIgnoreCase(kv.getMakhuvuc())) {
                return kv;
            }
        }
        return null;
    }

    private void fillDataToFormKhuVuc(int row) {
        txtMaKhuVuc.setText(tblKhuVuc.getValueAt(row, 1).toString());
        if (tblKhuVuc.getValueAt(row, 2).toString().equalsIgnoreCase("Dừng hoạt động")) {
            cbTrangThaiKhuVuc.setSelectedIndex(1);
        } else {
            cbTrangThaiKhuVuc.setSelectedIndex(0);
        }

    }

    private boolean checkFormEmptyBan(JTextField soban) {
        if (soban.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Không được trống");
            return false;
        } else {
            return true;
        }
    }

    private boolean checkNumber(String num) {
        Pattern regexInt = Pattern.compile("^[0-9]+$");
        if (!regexInt.matcher(num).find()) {
            JOptionPane.showMessageDialog(this, "Số không hợp lệ(0-9)", "Number Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkVuotquakituBan(String soban) {
        Pattern regex = Pattern.compile("^\\w{1,5}+$");
        if (!regex.matcher(soban).find()) {
            JOptionPane.showMessageDialog(this, "Tối đa 5 kí tự", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            return true;
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

    private boolean checkFormEmpty1(JTextField ma) {
        if (ma.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Không được trống");
            return false;
        } else {
            return true;
        }
    }

    private boolean checkSoBan(Integer soBan) {
        domainmodel.Ban ban = banRepo.getBanFormSoBan(soBan);
        if (ban != null) {
            JOptionPane.showMessageDialog(this, "Số bàn này đã tồn tại");
            return false;
        }
        return true;
    }

    private boolean checkMaKhuVuc(String maKhuVuc) {
        domainmodel.KhuVuc khuVuc = khuVucRepo.getKhuVucFromMa(maKhuVuc);
        if (khuVuc != null) {
            JOptionPane.showMessageDialog(this, "Mã khu vực này đã tồn tại");
            return false;
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

        cboChiNhanh = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        panel1 = new fomVe.Panel();
        cbTrangThaiKhuVuc = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKhuVuc = new javax.swing.JTable();
        btnThemKhuVuc = new javax.swing.JButton();
        btnCapNhatKhuVuc = new javax.swing.JButton();
        txtMaKhuVuc = new fomVe.JxText();
        jLabel2 = new javax.swing.JLabel();
        panel3 = new fomVe.Panel();
        cbKhuVuc = new javax.swing.JComboBox<>();
        btnThemBan = new javax.swing.JButton();
        btnXoaBan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBan = new javax.swing.JTable();
        txtSoBan = new fomVe.JxText();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(51, 51, 51));

        cboChiNhanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChiNhanhActionPerformed(evt);
            }
        });

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridLayout(1, 2, 15, 0));

        panel1.setBackground(new java.awt.Color(255, 255, 255));

        cbTrangThaiKhuVuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang hoạt động", "Dừng hoạt động" }));
        cbTrangThaiKhuVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTrangThaiKhuVucActionPerformed(evt);
            }
        });

        tblKhuVuc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "Mã", "Trạng Thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhuVuc.getTableHeader().setReorderingAllowed(false);
        tblKhuVuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhuVucMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblKhuVuc);
        if (tblKhuVuc.getColumnModel().getColumnCount() > 0) {
            tblKhuVuc.getColumnModel().getColumn(0).setMinWidth(0);
            tblKhuVuc.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblKhuVuc.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        btnThemKhuVuc.setBackground(new java.awt.Color(155, 49, 56));
        btnThemKhuVuc.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnThemKhuVuc.setForeground(new java.awt.Color(255, 255, 255));
        btnThemKhuVuc.setText("Thêm");
        btnThemKhuVuc.setBorderPainted(false);
        btnThemKhuVuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThemKhuVuc.setFocusPainted(false);
        btnThemKhuVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKhuVucActionPerformed(evt);
            }
        });

        btnCapNhatKhuVuc.setBackground(new java.awt.Color(155, 49, 56));
        btnCapNhatKhuVuc.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnCapNhatKhuVuc.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhatKhuVuc.setText("Cập Nhật");
        btnCapNhatKhuVuc.setBorderPainted(false);
        btnCapNhatKhuVuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCapNhatKhuVuc.setFocusPainted(false);
        btnCapNhatKhuVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatKhuVucActionPerformed(evt);
            }
        });

        txtMaKhuVuc.setPrompt("Mã khu vực");

        jLabel2.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("- Khu vực -");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaKhuVuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbTrangThaiKhuVuc, 0, 449, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThemKhuVuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCapNhatKhuVuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemKhuVuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMaKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCapNhatKhuVuc)
                    .addComponent(cbTrangThaiKhuVuc, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(panel1);

        panel3.setBackground(new java.awt.Color(255, 255, 255));

        cbKhuVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbKhuVucActionPerformed(evt);
            }
        });

        btnThemBan.setBackground(new java.awt.Color(155, 49, 56));
        btnThemBan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnThemBan.setForeground(new java.awt.Color(255, 255, 255));
        btnThemBan.setText("Thêm");
        btnThemBan.setBorderPainted(false);
        btnThemBan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThemBan.setFocusPainted(false);
        btnThemBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemBanActionPerformed(evt);
            }
        });

        btnXoaBan.setBackground(new java.awt.Color(155, 49, 56));
        btnXoaBan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnXoaBan.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaBan.setText("Xóa");
        btnXoaBan.setBorderPainted(false);
        btnXoaBan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXoaBan.setFocusPainted(false);
        btnXoaBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaBanActionPerformed(evt);
            }
        });

        tblBan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "Số Bàn", "Khu Vực"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBan.getTableHeader().setReorderingAllowed(false);
        tblBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBan);
        if (tblBan.getColumnModel().getColumnCount() > 0) {
            tblBan.getColumnModel().getColumn(0).setMinWidth(0);
            tblBan.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblBan.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        txtSoBan.setPrompt("Số bàn");

        jLabel3.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("- Bàn -");

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbKhuVuc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSoBan, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel3Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(btnXoaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnThemBan, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSoBan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXoaBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbKhuVuc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(panel3);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Chi nhánh :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboChiNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboChiNhanh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 703, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbTrangThaiKhuVucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTrangThaiKhuVucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTrangThaiKhuVucActionPerformed

    private void cboChiNhanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChiNhanhActionPerformed
        comboKhuVuc = (DefaultComboBoxModel) new DefaultComboBoxModel<>(
                iKhuVucService.getAllKhuVucByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId()).toArray());
        loadData(((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId());
    }//GEN-LAST:event_cboChiNhanhActionPerformed

    private void tblKhuVucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhuVucMouseClicked
        int row = tblKhuVuc.getSelectedRow();
        fillDataToFormKhuVuc(row);
    }//GEN-LAST:event_tblKhuVucMouseClicked

    private void btnThemKhuVucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKhuVucActionPerformed
        if (checkFormEmptyBan(txtMaKhuVuc) && checkVuotquakituKhuVuc(txtMaKhuVuc.getText())
                && checkMaKhuVuc(txtMaKhuVuc.getText())) {
            if (taiKhoanAdmin != null) {
                iKhuVucService.insertKhuVucToChiNhanh(txtMaKhuVuc.getText(),
                        ((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId());
                JOptionPane.showMessageDialog(this, "Thêm Khu Vực thành công");
                addComboKhuVucByChiNhah(((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId());
                load_KhuVuc_By_ChiNhanh(iKhuVucService.getAllKhuVucByChiNhanh(
                        ((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId()));
            } else {
                iKhuVucService.insertKhuVucToChiNhanh(txtMaKhuVuc.getText(),
                        ibanService.getChiNhanhByTaiKhoan(taiKhoanNguoiDung.getId()).getId());
                JOptionPane.showMessageDialog(this, "Thêm Khu Vực thành công");
                addComboKhuVucByChiNhah(
                        ((ChiNhanhViewModel_Hoang) ibanService.getChiNhanhByTaiKhoan(taiKhoanNguoiDung.getId())).getId());
                load_KhuVuc_By_ChiNhanh(iKhuVucService.getAllKhuVucByChiNhanh(
                        ((ChiNhanhViewModel_Hoang) ibanService.getChiNhanhByTaiKhoan(taiKhoanNguoiDung.getId())).getId()));

            }

        } else {
            JOptionPane.showMessageDialog(this, "Thêm khu vực thất bại");
        }
    }//GEN-LAST:event_btnThemKhuVucActionPerformed

    private void btnCapNhatKhuVucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatKhuVucActionPerformed
        int row = tblKhuVuc.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khu vực trên bảng");
        } else {
            if (checkFormEmpty1(txtMaKhuVuc)) {
                KhuVucViewModel khuVucViewModel = new KhuVucViewModel();
                khuVucViewModel.setIdKhuVuc(tblKhuVuc.getValueAt(row, 0).toString());
                khuVucViewModel.setMakhuvuc(tblKhuVuc.getValueAt(row, 1).toString());
                //                if (cbTrangThaiKhuVuc.getSelectedIndex() == 1) {
                //                    khuVucViewModel.setTrangthai(0);
                //                } else {
                //                    khuVucViewModel.setTrangthai(1);
                //                }
                iKhuVucService.updateKhuVuc(khuVucViewModel, txtMaKhuVuc.getText(), cbTrangThaiKhuVuc.getSelectedIndex() == 1 ? 0 : 1);
                JOptionPane.showMessageDialog(this, "Cập nhật thành công  ");
                if (taiKhoanAdmin != null) {
                    load_KhuVuc_By_ChiNhanh(iKhuVucService.getAllKhuVucByChiNhanh(
                            ((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId()));
                } else {
                    load_KhuVuc_By_ChiNhanh(iKhuVucService.getAllKhuVucByChiNhanh(
                            ((ChiNhanhViewModel_Hoang) ibanService.getChiNhanhByTaiKhoan(taiKhoanNguoiDung.getId())).getId()));
                }

            }
        }
    }//GEN-LAST:event_btnCapNhatKhuVucActionPerformed

    private void cbKhuVucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbKhuVucActionPerformed
        load_Ban_By_KhuVuc(ibanService.getAllBanByKhuVuc(((KhuVucViewModel) comboKhuVuc.getSelectedItem()).getIdKhuVuc()));
    }//GEN-LAST:event_cbKhuVucActionPerformed

    private void btnThemBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemBanActionPerformed
        if (checkFormEmptyBan(txtSoBan) && checkNumber(txtSoBan.getText())
                && checkVuotquakituBan(txtSoBan.getText()) && checkSoBan(Integer.parseInt(txtSoBan.getText()))) {
            KhuVucViewModel kvView = (KhuVucViewModel) comboKhuVuc.getSelectedItem();
            ibanService.insertBan(Integer.parseInt(txtSoBan.getText()), kvView);
            JOptionPane.showMessageDialog(this, "Thêm Bàn thành công");
            load_Ban_By_KhuVuc(ibanService.getAllBanByKhuVuc(((KhuVucViewModel) comboKhuVuc.getSelectedItem()).getIdKhuVuc()));
        }
    }//GEN-LAST:event_btnThemBanActionPerformed

    private void btnXoaBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaBanActionPerformed
        int row = tblBan.getSelectedRow();
        ibanService.deleteBan(tblBan.getValueAt(row, 0).toString());
        JOptionPane.showMessageDialog(this, "Xoa Thành Công");
        load_Ban_By_KhuVuc(ibanService.getAllBanByKhuVuc(((KhuVucViewModel) comboKhuVuc.getSelectedItem()).getIdKhuVuc()));
    }//GEN-LAST:event_btnXoaBanActionPerformed

    private void tblBanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBanMouseClicked
        int row = tblBan.getSelectedRow();
        fillDataToFormBan(row);
    }//GEN-LAST:event_tblBanMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhatKhuVuc;
    private javax.swing.JButton btnThemBan;
    private javax.swing.JButton btnThemKhuVuc;
    private javax.swing.JButton btnXoaBan;
    private javax.swing.JComboBox<KhuVucViewModel> cbKhuVuc;
    private javax.swing.JComboBox<String> cbTrangThaiKhuVuc;
    private javax.swing.JComboBox<String> cboChiNhanh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private fomVe.Panel panel1;
    private fomVe.Panel panel3;
    private javax.swing.JTable tblBan;
    private javax.swing.JTable tblKhuVuc;
    private fomVe.JxText txtMaKhuVuc;
    private fomVe.JxText txtSoBan;
    // End of variables declaration//GEN-END:variables
}
