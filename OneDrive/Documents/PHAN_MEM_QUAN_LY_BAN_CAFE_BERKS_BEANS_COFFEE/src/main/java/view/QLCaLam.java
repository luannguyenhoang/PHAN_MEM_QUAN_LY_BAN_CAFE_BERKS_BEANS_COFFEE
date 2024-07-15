/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import domainmodel.Ca;
import domainmodel.HoatDongCa;
import domainmodel.TaiKhoanAdmin;
import domainmodel.TaiKhoanNguoiDung;
import fomVe.CustomHeaderRenderer;
import fomVe.SVGIconHelper;
import fomVe.SVGIconHelperButton;
import java.awt.Color;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import repository.CaRepo;
import service.IBanHangService;
import service.ICa;
import service.implement.BanHangService;
import service.implement.CaService;
import viewmodel.CaViewModel_Quan;
import viewmodel.ChiNhanhViewModel_Hoang;
import viewmodel.NhanVienViewModel_Van;

/**
 *
 * @author hoang
 */
public class QLCaLam extends javax.swing.JPanel implements Runnable {

    private CaRepo caRepo;
    private ICa caService;
    private IBanHangService banHangService;
    private TaiKhoanAdmin _admin;
    private TaiKhoanNguoiDung _nguoiDung;
    private DefaultTableModel modelTableNhanVien;
    private DefaultTableModel modelTableCa;
    private DefaultComboBoxModel<ChiNhanhViewModel_Hoang> modelComboChiNhanh;
    ChiNhanhViewModel_Hoang _chiNhanhNguoiDung;
    private Ca caIsRunning = null;

    public QLCaLam(TaiKhoanAdmin admin, TaiKhoanNguoiDung nguoiDung) {
        initComponents();
        _admin = admin;
        _nguoiDung = nguoiDung;
        caRepo = new CaRepo();
        caService = new CaService();
        banHangService = new BanHangService();
        modelTableNhanVien = new DefaultTableModel();
        modelTableCa = new DefaultTableModel();
        modelTableNhanVien = (DefaultTableModel) tblNhanVien.getModel();
        modelTableCa = (DefaultTableModel) tblCa.getModel();
        btnKhoiPhucCa.setEnabled(false);
        SVGIconHelper.setIcon(lblCaDangHoatDong, "icon/bell.svg", 25, 25);
        SVGIconHelper.setIcon(lblGoiYCapNhatCaNv, "icon/!.svg", 15, 15);
        SVGIconHelper.setIcon(lblTrangThaiCa, "icon/off.svg", 60, 60);
        SVGIconHelperButton.setIcon(btnSuaCa, "icon/update.svg", 25, 25);
        SVGIconHelperButton.setIcon(btnCapNhatCaNhanVien, "icon/update.svg", 15, 15);
        SVGIconHelperButton.setIcon(btnThemCa, "icon/plus.svg", 25, 25);
        SVGIconHelperButton.setIcon(btnXoaCa, "icon/delete.svg", 25, 25);
        SVGIconHelperButton.setIcon(btnMoCa, "icon/key.svg", 25, 25);
        SVGIconHelperButton.setIcon(btnDongCa, "icon/khoa.svg", 25, 25);
        JTableHeader tableHeader2 = tblCa.getTableHeader();
        tableHeader2.setBackground(new Color(143, 45, 52));
        tableHeader2.setForeground(Color.WHITE);
        tableHeader2.setBorder(null);
        TableCellRenderer defaultHeaderRenderer2 = tblCa.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer2 = new CustomHeaderRenderer(defaultHeaderRenderer2);
        tblCa.getTableHeader().setDefaultRenderer(customHeaderRenderer2);

        JTableHeader tableHeader = tblNhanVien.getTableHeader();
        tableHeader.setBackground(new Color(143, 45, 52));
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setBorder(null);
        TableCellRenderer defaultHeaderRenderer = tblNhanVien.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer = new CustomHeaderRenderer(defaultHeaderRenderer);
        tblNhanVien.getTableHeader().setDefaultRenderer(customHeaderRenderer);

        Thread loadData = new Thread(this);
        loadData.start();
        try {
            loadData.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(QLCaLam.class.getName()).log(Level.SEVERE, null, ex);
        }
        checkTimeOfCa();

    }

    private void checkTimeOfCa() {
        Thread notificationCloseCa = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean stop = false;
                while (stop == false) {
                    if (caIsRunning != null) {
                        try {
                            Thread.sleep(1000);
                            LocalTime timeNow = LocalTime.now();
                            LocalTime timeEndCa = caIsRunning.getGioKetThuc();
                            int valueCompare = timeNow.compareTo(timeEndCa);
                            if (valueCompare >= 0) {
                                stop = true;
                                JOptionPane.showMessageDialog(null, "Đã hết ca");
                            }
                        } catch (InterruptedException ex) {
                            Logger.getLogger(QLCaLam.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {
                        stop = true;
                    }
                }
            }
        });
        notificationCloseCa.start();
    }

    @Override
    public void run() {
        if (_admin != null) {
            modelComboChiNhanh = (DefaultComboBoxModel) new DefaultComboBoxModel<>(banHangService.getAllChiNhanh().toArray());
            cboChiNhanh.setModel((DefaultComboBoxModel) modelComboChiNhanh);
            showNhanVienToTable(caService.getNhanVienByChiNhanh(
                    ((ChiNhanhViewModel_Hoang) modelComboChiNhanh.getElementAt(0)).getId())
            );
            caIsRunning = caRepo.getCaRunningOfChiNhanh(((ChiNhanhViewModel_Hoang) modelComboChiNhanh.getSelectedItem()).getId());
            if (caIsRunning != null) {
                lblCaDangHoatDong.setText(caIsRunning.getMa() + " đang hoạt động");
            } else {
                lblCaDangHoatDong.setText("Chưa mở ca");
            }

        } else {
            cboChiNhanh.setVisible(false);
            _chiNhanhNguoiDung = banHangService.getChiNhanhbyTaiKhoan(_nguoiDung.getId());
            showNhanVienToTable(caService.getNhanVienByChiNhanh(_chiNhanhNguoiDung.getId()));
            caIsRunning = caRepo.getCaRunningOfChiNhanh(_chiNhanhNguoiDung.getId());
            if (caIsRunning != null) {
                lblCaDangHoatDong.setText(caIsRunning.getMa() + " đang hoạt động");
            } else {
                lblCaDangHoatDong.setText("Chưa mở ca");
            }
        }
        showCaToTable(caService.getAllCaDangSuDung());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dLongDongCa = new javax.swing.JDialog();
        pnlDongCa = new javax.swing.JPanel();
        txtTienThucTeTrongKet = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnXacNhanDongCa = new javax.swing.JButton();
        btnDongCaHuyBo = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        lblChenhLech = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lblTienKetDauCa = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lblTienBanGiao = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        lblCanhBaoTienThucTe = new javax.swing.JLabel();
        dLogMoCa = new javax.swing.JDialog();
        pnlMoCa = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtTienKetDauCa = new javax.swing.JTextField();
        btnMoCaHuyBo = new javax.swing.JButton();
        btnXacNhanMoCa = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        lblCanhBaoTienDauCa = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        cboChiNhanh = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCa = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnKhoiPhucCa = new javax.swing.JButton();
        rdoDangSuDung = new javax.swing.JRadioButton();
        rdoDaXoa = new javax.swing.JRadioButton();
        btnCapNhatCaNhanVien = new javax.swing.JButton();
        lblGoiYCapNhatCaNv = new javax.swing.JLabel();
        lblCaDangHoatDong = new javax.swing.JLabel();
        txtMaCa = new fomVe.JxText();
        lblCanhBaoMa = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtGioBatDau = new fomVe.JxText();
        txtPhutBatDau = new fomVe.JxText();
        txtPhutKetThuc = new fomVe.JxText();
        txtGioKetThuc = new fomVe.JxText();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnMoCa = new javax.swing.JButton();
        btnDongCa = new javax.swing.JButton();
        lblCanhBaoGioBatDau = new javax.swing.JLabel();
        lblCanhBaoGioKetThuc = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnThemCa = new javax.swing.JButton();
        btnSuaCa = new javax.swing.JButton();
        btnXoaCa = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        lblTrangThaiCa = new javax.swing.JLabel();

        dLongDongCa.setUndecorated(true);
        dLongDongCa.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                dLongDongCaWindowLostFocus(evt);
            }
        });

        pnlDongCa.setBackground(new java.awt.Color(255, 255, 255));
        pnlDongCa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 0), 2));

        txtTienThucTeTrongKet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienThucTeTrongKetKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Tiền thực tế trong két ( tiền mặt ):");

        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("VND");

        btnXacNhanDongCa.setBackground(new java.awt.Color(0, 153, 0));
        btnXacNhanDongCa.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnXacNhanDongCa.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhanDongCa.setText("Xác nhận đóng ca");
        btnXacNhanDongCa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXacNhanDongCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanDongCaActionPerformed(evt);
            }
        });

        btnDongCaHuyBo.setBackground(new java.awt.Color(255, 0, 0));
        btnDongCaHuyBo.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnDongCaHuyBo.setForeground(new java.awt.Color(255, 255, 255));
        btnDongCaHuyBo.setText("Hủy bỏ");
        btnDongCaHuyBo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDongCaHuyBo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDongCaHuyBoActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Chênh lệch :");

        lblChenhLech.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblChenhLech.setText("0");
        lblChenhLech.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("VND");

        jLabel18.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Tiền két đầu ca :");

        lblTienKetDauCa.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblTienKetDauCa.setText("0");
        lblTienKetDauCa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("VND");

        jLabel21.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("Tiền bàn giao ( tiền mặt ) :");

        lblTienBanGiao.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblTienBanGiao.setText("0");
        lblTienBanGiao.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText("VND");

        lblCanhBaoTienThucTe.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout pnlDongCaLayout = new javax.swing.GroupLayout(pnlDongCa);
        pnlDongCa.setLayout(pnlDongCaLayout);
        pnlDongCaLayout.setHorizontalGroup(
            pnlDongCaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDongCaLayout.createSequentialGroup()
                .addComponent(btnXacNhanDongCa, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDongCaHuyBo, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE))
            .addGroup(pnlDongCaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDongCaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDongCaLayout.createSequentialGroup()
                        .addGroup(pnlDongCaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlDongCaLayout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(140, 140, 140)
                                .addComponent(lblTienKetDauCa, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel20))
                            .addGroup(pnlDongCaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnlDongCaLayout.createSequentialGroup()
                                    .addComponent(jLabel15)
                                    .addGap(164, 164, 164)
                                    .addComponent(lblChenhLech, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel17))
                                .addGroup(pnlDongCaLayout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtTienThucTeTrongKet, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel11))))
                        .addGap(18, 18, 18)
                        .addComponent(lblCanhBaoTienThucTe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlDongCaLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(72, 72, 72)
                        .addComponent(lblTienBanGiao, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel23)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlDongCaLayout.setVerticalGroup(
            pnlDongCaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDongCaLayout.createSequentialGroup()
                .addGroup(pnlDongCaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDongCaLayout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(lblCanhBaoTienThucTe, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDongCaLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(pnlDongCaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(lblTienKetDauCa)
                            .addComponent(jLabel18))
                        .addGap(26, 26, 26)
                        .addGroup(pnlDongCaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtTienThucTeTrongKet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))))
                .addGap(18, 18, 18)
                .addGroup(pnlDongCaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addGroup(pnlDongCaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblChenhLech)
                        .addComponent(jLabel15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(pnlDongCaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTienBanGiao)
                    .addComponent(jLabel23)
                    .addComponent(jLabel21))
                .addGap(18, 18, 18)
                .addGroup(pnlDongCaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXacNhanDongCa, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDongCaHuyBo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        dLongDongCa.getContentPane().add(pnlDongCa, java.awt.BorderLayout.CENTER);

        dLogMoCa.setUndecorated(true);
        dLogMoCa.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                dLogMoCaWindowLostFocus(evt);
            }
        });

        pnlMoCa.setBackground(new java.awt.Color(255, 255, 255));
        pnlMoCa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 0), 2));

        jLabel7.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel7.setText("Tiền két đầu ca ( Tiền mặt )");

        txtTienKetDauCa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKetDauCaKeyReleased(evt);
            }
        });

        btnMoCaHuyBo.setBackground(new java.awt.Color(255, 0, 0));
        btnMoCaHuyBo.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnMoCaHuyBo.setForeground(new java.awt.Color(255, 255, 255));
        btnMoCaHuyBo.setText("Hủy bỏ");
        btnMoCaHuyBo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMoCaHuyBo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoCaHuyBoActionPerformed(evt);
            }
        });

        btnXacNhanMoCa.setBackground(new java.awt.Color(0, 153, 0));
        btnXacNhanMoCa.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnXacNhanMoCa.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhanMoCa.setText("Xác nhận mở ca");
        btnXacNhanMoCa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXacNhanMoCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanMoCaActionPerformed(evt);
            }
        });

        jLabel8.setText("VND");

        lblCanhBaoTienDauCa.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout pnlMoCaLayout = new javax.swing.GroupLayout(pnlMoCa);
        pnlMoCa.setLayout(pnlMoCaLayout);
        pnlMoCaLayout.setHorizontalGroup(
            pnlMoCaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMoCaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMoCaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMoCaLayout.createSequentialGroup()
                        .addComponent(btnXacNhanMoCa, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMoCaHuyBo, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE))
                    .addGroup(pnlMoCaLayout.createSequentialGroup()
                        .addGroup(pnlMoCaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(pnlMoCaLayout.createSequentialGroup()
                                .addComponent(txtTienKetDauCa, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(jLabel8)))
                        .addGap(47, 47, 47)
                        .addComponent(lblCanhBaoTienDauCa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlMoCaLayout.setVerticalGroup(
            pnlMoCaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMoCaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMoCaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTienKetDauCa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(lblCanhBaoTienDauCa, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                .addGroup(pnlMoCaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXacNhanMoCa, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMoCaHuyBo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        dLogMoCa.getContentPane().add(pnlMoCa, java.awt.BorderLayout.CENTER);

        cboChiNhanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChiNhanhActionPerformed(evt);
            }
        });

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Mã nhân viên", "Tên nhân viên"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.getTableHeader().setReorderingAllowed(false);
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblNhanVien);
        if (tblNhanVien.getColumnModel().getColumnCount() > 0) {
            tblNhanVien.getColumnModel().getColumn(0).setMinWidth(0);
            tblNhanVien.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblNhanVien.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        tblCa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Chọn", "Id", "Mã ca", "Giờ bắt đầu", "Giờ kết thúc"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCa.getTableHeader().setReorderingAllowed(false);
        tblCa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCa);
        if (tblCa.getColumnModel().getColumnCount() > 0) {
            tblCa.getColumnModel().getColumn(0).setPreferredWidth(2);
            tblCa.getColumnModel().getColumn(1).setMinWidth(0);
            tblCa.getColumnModel().getColumn(1).setPreferredWidth(0);
            tblCa.getColumnModel().getColumn(1).setMaxWidth(0);
        }

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("- Danh sách nhân viên ở chi nhánh -");

        btnKhoiPhucCa.setBackground(new java.awt.Color(102, 102, 102));
        btnKhoiPhucCa.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnKhoiPhucCa.setForeground(new java.awt.Color(51, 255, 255));
        btnKhoiPhucCa.setText("khôi phục ca");
        btnKhoiPhucCa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnKhoiPhucCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhoiPhucCaActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoDangSuDung);
        rdoDangSuDung.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        rdoDangSuDung.setSelected(true);
        rdoDangSuDung.setText("Đang sử dụng");
        rdoDangSuDung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDangSuDungActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoDaXoa);
        rdoDaXoa.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        rdoDaXoa.setText("Đã xóa");
        rdoDaXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDaXoaActionPerformed(evt);
            }
        });

        btnCapNhatCaNhanVien.setBackground(new java.awt.Color(0, 204, 204));
        btnCapNhatCaNhanVien.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnCapNhatCaNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhatCaNhanVien.setText("CẬP NHẬT CA NHÂN VIÊN");
        btnCapNhatCaNhanVien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCapNhatCaNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatCaNhanVienActionPerformed(evt);
            }
        });

        lblGoiYCapNhatCaNv.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblGoiYCapNhatCaNv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblGoiYCapNhatCaNvMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblGoiYCapNhatCaNvMouseExited(evt);
            }
        });

        lblCaDangHoatDong.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        lblCaDangHoatDong.setForeground(new java.awt.Color(255, 0, 0));
        lblCaDangHoatDong.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCaDangHoatDong.setText("Chưa mở ca");

        txtMaCa.setPrompt("Mã ca làm");
        txtMaCa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMaCaKeyReleased(evt);
            }
        });

        lblCanhBaoMa.setForeground(new java.awt.Color(255, 51, 51));

        jLabel2.setText("Giờ bắt đầu :");

        txtGioBatDau.setPrompt("Giờ");
        txtGioBatDau.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGioBatDauKeyReleased(evt);
            }
        });

        txtPhutBatDau.setPrompt("Phút");
        txtPhutBatDau.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPhutBatDauKeyReleased(evt);
            }
        });

        txtPhutKetThuc.setPrompt("Phút");
        txtPhutKetThuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPhutKetThucKeyReleased(evt);
            }
        });

        txtGioKetThuc.setPrompt("Giờ");
        txtGioKetThuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGioKetThucKeyReleased(evt);
            }
        });

        jLabel3.setText(":");

        jLabel4.setText("Giờ kết thúc :");

        jLabel5.setText(":");

        jPanel2.setLayout(new java.awt.GridLayout(2, 1, 0, 15));

        btnMoCa.setBackground(new java.awt.Color(10, 188, 62));
        btnMoCa.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        btnMoCa.setForeground(new java.awt.Color(255, 255, 255));
        btnMoCa.setText("Mở ca");
        btnMoCa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMoCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoCaActionPerformed(evt);
            }
        });
        jPanel2.add(btnMoCa);

        btnDongCa.setBackground(new java.awt.Color(255, 0, 0));
        btnDongCa.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        btnDongCa.setForeground(new java.awt.Color(255, 255, 255));
        btnDongCa.setText("Đóng ca");
        btnDongCa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDongCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDongCaActionPerformed(evt);
            }
        });
        jPanel2.add(btnDongCa);

        lblCanhBaoGioBatDau.setForeground(new java.awt.Color(255, 51, 51));

        lblCanhBaoGioKetThuc.setForeground(new java.awt.Color(255, 51, 51));

        jPanel3.setLayout(new java.awt.GridLayout(3, 1, 0, 10));

        btnThemCa.setBackground(new java.awt.Color(143, 45, 52));
        btnThemCa.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnThemCa.setForeground(new java.awt.Color(255, 255, 255));
        btnThemCa.setText("THÊM CA TRONG NGÀY");
        btnThemCa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThemCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemCaActionPerformed(evt);
            }
        });
        jPanel3.add(btnThemCa);

        btnSuaCa.setBackground(new java.awt.Color(143, 45, 52));
        btnSuaCa.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnSuaCa.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaCa.setText("CẬP NHẬT CA");
        btnSuaCa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSuaCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaCaActionPerformed(evt);
            }
        });
        jPanel3.add(btnSuaCa);

        btnXoaCa.setBackground(new java.awt.Color(143, 45, 52));
        btnXoaCa.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnXoaCa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaCa.setText("XÓA CA");
        btnXoaCa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXoaCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaCaActionPerformed(evt);
            }
        });
        jPanel3.add(btnXoaCa);

        jLabel9.setText("Chọn chi nhánh :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboChiNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                            .addComponent(lblTrangThaiCa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rdoDangSuDung)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdoDaXoa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblCaDangHoatDong, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnCapNhatCaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblGoiYCapNhatCaNv, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                                .addComponent(btnKhoiPhucCa, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtGioBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtPhutBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblCanhBaoGioBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtGioKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtPhutKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel4)))
                            .addComponent(txtMaCa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblCanhBaoMa, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lblCanhBaoGioKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(106, 106, 106)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboChiNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoDangSuDung)
                            .addComponent(rdoDaXoa)))
                    .addComponent(lblCaDangHoatDong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTrangThaiCa, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCapNhatCaNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblGoiYCapNhatCaNv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnKhoiPhucCa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCanhBaoMa, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtMaCa, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtPhutBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtGioBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblCanhBaoGioBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtGioKetThuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtPhutKetThuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lblCanhBaoGioKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(170, 170, 170))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 693, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
    }// </editor-fold>//GEN-END:initComponents
  private void clearFormCRUD() {
        txtMaCa.setText("");
        txtGioBatDau.setText("");
        txtGioKetThuc.setText("");
        txtPhutBatDau.setText("");
        txtGioKetThuc.setText("");
    }

    private void showNhanVienToTable(Set<NhanVienViewModel_Van> setNhanVienView) {
        modelTableNhanVien.setRowCount(0);
        for (NhanVienViewModel_Van nv : setNhanVienView) {
            modelTableNhanVien.addRow(new Object[]{nv.getIdNhanVien(),
                nv.getMaNhanVien(), nv.getHoTen()});
        }
    }

    private void showCaToTable(List<CaViewModel_Quan> setCaView) {
        modelTableCa.setRowCount(0);
        for (CaViewModel_Quan caView : setCaView) {
            modelTableCa.addRow(new Object[]{false, caView.getId(),
                caView.getMa(), caView.getGioBD(), caView.getGioKT()});
        }
    }

    private void cboChiNhanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChiNhanhActionPerformed

        showNhanVienToTable(caService.getNhanVienByChiNhanh(
                ((ChiNhanhViewModel_Hoang) modelComboChiNhanh.getSelectedItem()).getId())
        );

        for (int i = 0; i < tblCa.getRowCount(); i++) {
            tblCa.setValueAt(false, i, 0);
        }
        caIsRunning = caRepo.getCaRunningOfChiNhanh(((ChiNhanhViewModel_Hoang) modelComboChiNhanh.getSelectedItem()).getId());
        if (caIsRunning != null) {
            lblCaDangHoatDong.setText(caIsRunning.getMa() + " đang hoạt động");

        } else {

            lblCaDangHoatDong.setText("Chưa mở ca");
        }
        checkTimeOfCa();
        tblCa.clearSelection();
        txtMaCa.setText("");
        txtGioBatDau.setText("");
        txtGioKetThuc.setText("");
        txtPhutBatDau.setText("");
        txtPhutKetThuc.setText("");

    }//GEN-LAST:event_cboChiNhanhActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        for (int i = 0; i < tblCa.getRowCount(); i++) {
            tblCa.setValueAt(false, i, 0);
        }
        int row = tblNhanVien.getSelectedRow();
        if (row != -1) {
            Set<CaViewModel_Quan> caOfNhanVien = caService.getCaOfNhanVien(tblNhanVien.getValueAt(row, 0).toString());
            if (caOfNhanVien.isEmpty()) {
                for (int i = 0; i < tblCa.getRowCount(); i++) {
                    tblCa.setValueAt(false, i, 0);
                }
            } else {
                for (CaViewModel_Quan caNhanVien : caOfNhanVien) {
                    for (int i = 0; i < tblCa.getRowCount(); i++) {
                        if (caNhanVien.getId().equals(tblCa.getValueAt(i, 1).toString())) {
                            tblCa.setValueAt(true, i, 0);
                            break;
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void tblCaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCaMouseClicked
        int row = tblCa.getSelectedRow();
        if (row != -1) {
            txtMaCa.setText(tblCa.getValueAt(row, 2).toString());
            txtGioBatDau.setText(tblCa.getValueAt(row, 3).toString().substring(0, 2));
            txtPhutBatDau.setText(tblCa.getValueAt(row, 3).toString().substring(3, 5));
            txtGioKetThuc.setText(tblCa.getValueAt(row, 4).toString().substring(0, 2));
            txtPhutKetThuc.setText(tblCa.getValueAt(row, 4).toString().substring(3, 5));
            lblCanhBaoMa.setText("");
            // lblCanhBaoGioBatDau.setText("");
            //lblCanhBaoGioKetThuc.setText("");
        }
    }//GEN-LAST:event_tblCaMouseClicked

    private void btnKhoiPhucCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhoiPhucCaActionPerformed
        int row = tblCa.getSelectedRow();
        if (row != -1) {
            caService.changeStateOfCa(tblCa.getValueAt(row, 1).toString());
            JOptionPane.showMessageDialog(this, "Khôi phục ca thành công");
            showCaToTable(caService.getAllCaDaXoa());
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ca cần khôi phục");
        }
    }//GEN-LAST:event_btnKhoiPhucCaActionPerformed

    private void rdoDangSuDungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDangSuDungActionPerformed
        showCaToTable(caService.getAllCaDangSuDung());
        btnMoCa.setEnabled(true);
        btnDongCa.setEnabled(true);
        btnThemCa.setEnabled(true);
        btnXoaCa.setEnabled(true);
        btnSuaCa.setEnabled(true);
        btnCapNhatCaNhanVien.setEnabled(true);
        btnKhoiPhucCa.setEnabled(false);
    }//GEN-LAST:event_rdoDangSuDungActionPerformed

    private void rdoDaXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDaXoaActionPerformed
        showCaToTable(caService.getAllCaDaXoa());
        btnMoCa.setEnabled(false);
        btnDongCa.setEnabled(false);
        btnThemCa.setEnabled(false);
        btnXoaCa.setEnabled(false);
        btnSuaCa.setEnabled(false);
        btnCapNhatCaNhanVien.setEnabled(false);
        btnKhoiPhucCa.setEnabled(true);
    }//GEN-LAST:event_rdoDaXoaActionPerformed

    private void btnCapNhatCaNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatCaNhanVienActionPerformed
        int rowNhanVien = tblNhanVien.getSelectedRow();
        Set<String> idCaSelected = new HashSet<>();
        if (rowNhanVien != -1) {
            for (int i = 0; i < tblCa.getRowCount(); i++) {
                if (tblCa.getValueAt(i, 0).equals(true)) {
                    idCaSelected.add(tblCa.getValueAt(i, 1).toString());
                }
            }
            caService.addCaToNhanVien(tblNhanVien.getValueAt(rowNhanVien, 0).toString(), idCaSelected);
            JOptionPane.showMessageDialog(this, "Cập nhật ca cho nhân viên thành công");
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 nhân viên");
        }
    }//GEN-LAST:event_btnCapNhatCaNhanVienActionPerformed

    private void lblGoiYCapNhatCaNvMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGoiYCapNhatCaNvMouseEntered
        lblGoiYCapNhatCaNv.setText("Chọn nhân viên và ca muốn áp dụng");
    }//GEN-LAST:event_lblGoiYCapNhatCaNvMouseEntered

    private void lblGoiYCapNhatCaNvMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGoiYCapNhatCaNvMouseExited
        lblGoiYCapNhatCaNv.setText("");
    }//GEN-LAST:event_lblGoiYCapNhatCaNvMouseExited

    private void txtMaCaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaCaKeyReleased
        if (txtMaCa.getText().length() > 5) {
            lblCanhBaoMa.setText("Tối đa 5 kí tự");
            txtMaCa.setText("");
        } else {
            if (caService.checkExistedOfMaCa(txtMaCa.getText())) {
                lblCanhBaoMa.setText("");
            } else {
                lblCanhBaoMa.setText("Mã này đã được sử dụng");
                txtMaCa.setText("");

            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaCaKeyReleased

    private void btnMoCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoCaActionPerformed
        int row = tblCa.getSelectedRow();

        if (!lblCaDangHoatDong.getText().equalsIgnoreCase("Chưa mở ca")) {
            JOptionPane.showMessageDialog(this, "Đang có ca hoạt động, không thể mở thêm");
        } else {
            if (row != -1) {

                LocalTime timeNow = LocalTime.now();
                Integer gioMoCa = Integer.parseInt(tblCa.getValueAt(row, 3).toString().substring(0, 2));
                Integer phutMoCa = Integer.parseInt(tblCa.getValueAt(row, 3).toString().substring(3, 5));
                Integer gioDongCa = Integer.parseInt(tblCa.getValueAt(row, 4).toString().substring(0, 2));
                Integer phutDongCa = Integer.parseInt(tblCa.getValueAt(row, 4).toString().substring(3, 5));
                LocalTime timeMoCa = LocalTime.of(gioMoCa, phutMoCa);
                LocalTime timeDongCa = LocalTime.of(gioDongCa, phutDongCa);
                int valueCompareMoCa = timeNow.compareTo(timeMoCa);
                int valueCompareDongCa = timeNow.compareTo(timeDongCa);
                System.out.println(timeMoCa);
                System.out.println(timeNow);
                if (valueCompareMoCa >= 0 && valueCompareDongCa <= 0) {
                    dLogMoCa.setVisible(true);
                    dLogMoCa.setSize(720, 350);
                    dLogMoCa.setLocationRelativeTo(null);
                    SVGIconHelper.setIcon(lblTrangThaiCa, "icon/on.svg", 60, 60);
                } else {
                    JOptionPane.showMessageDialog(this, "Chưa đến giờ, không thể mở ca");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ca muốn mở");
            }
        }
    }//GEN-LAST:event_btnMoCaActionPerformed

    private void btnDongCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDongCaActionPerformed

        if (_admin != null) {
            caIsRunning = caRepo.getCaRunningOfChiNhanh(((ChiNhanhViewModel_Hoang) modelComboChiNhanh.getSelectedItem()).getId());
            HoatDongCa activity = caRepo.getHoatDongCaRunningOfChiNhanh(((ChiNhanhViewModel_Hoang) modelComboChiNhanh.getSelectedItem()).getId());
            SVGIconHelper.setIcon(lblTrangThaiCa, "icon/off.svg", 60, 60);

            if (caIsRunning != null) {
                dLongDongCa.setVisible(true);
                dLongDongCa.setSize(740, 340);
                dLongDongCa.setLocationRelativeTo(null);
                lblTienKetDauCa.setText(String.valueOf(activity.getTienDauCa()));
            } else {
                JOptionPane.showMessageDialog(this, "Không có ca nào đang hoạt động");
            }
        } else {
            caIsRunning = caRepo.getCaRunningOfChiNhanh(_chiNhanhNguoiDung.getId());
            HoatDongCa activity = caRepo.getHoatDongCaRunningOfChiNhanh(_chiNhanhNguoiDung.getId());
            if (caIsRunning != null) {
                dLongDongCa.setVisible(true);
                dLongDongCa.setSize(740, 340);
                dLongDongCa.setLocationRelativeTo(null);
                lblTienKetDauCa.setText(String.valueOf(activity.getTienDauCa()));
            } else {
                JOptionPane.showMessageDialog(this, "Không có ca nào đang hoạt động");
            }
        }
    }//GEN-LAST:event_btnDongCaActionPerformed

    private void btnThemCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemCaActionPerformed
        if (!txtMaCa.getText().isBlank() && !txtGioBatDau.getText().isBlank() && !txtGioKetThuc.getText().isBlank()) {
            if (caService.checkExistedOfMaCa(txtMaCa.getText())) {
                LocalTime gioBatDau = LocalTime.of(Integer.parseInt(txtGioBatDau.getText()),
                        Integer.parseInt(txtPhutBatDau.getText()));
                LocalTime gioKetThuc = LocalTime.of(Integer.parseInt(txtGioKetThuc.getText()),
                        Integer.parseInt(txtPhutKetThuc.getText()));
                CaViewModel_Quan caView = new CaViewModel_Quan();
                caView.setMa(txtMaCa.getText());
                caView.setGioBD(gioBatDau);
                caView.setGioKT(gioKetThuc);
                String id = caService.insertCa(caView);
                if (id != null) {
                    JOptionPane.showMessageDialog(this, "Thêm ca làm việc thành công");
                    showCaToTable(caService.getAllCaDangSuDung());
                    clearFormCRUD();
                } else {
                    JOptionPane.showMessageDialog(this, "Có lỗi xảy ra");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Mã ca này đã được sử dụng");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Mã ca và giờ");
        }
    }//GEN-LAST:event_btnThemCaActionPerformed

    private void btnXoaCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaCaActionPerformed
        int row = tblCa.getSelectedRow();
        if (row != -1) {
            if (caIsRunning != null) {
                JOptionPane.showMessageDialog(this, "Vui lòng đóng ca hiện tại");
            } else {
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa ca này", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    caService.changeStateOfCa(tblCa.getValueAt(row, 1).toString());
                    JOptionPane.showMessageDialog(this, "Xóa ca thành công");
                    showCaToTable(caService.getAllCaDangSuDung());
                    clearFormCRUD();
                }
            }

        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ca muốn xóa");
        }
    }//GEN-LAST:event_btnXoaCaActionPerformed

    private void btnSuaCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaCaActionPerformed
        int row = tblCa.getSelectedRow();
        if (row != -1) {
            if (caIsRunning != null) {
                JOptionPane.showMessageDialog(this, "Vui lòng đóng ca hiện tại");
            } else {
                if (!txtMaCa.getText().isBlank() && !txtGioBatDau.getText().isBlank() && !txtGioKetThuc.getText().isBlank()) {
                    LocalTime gioBatDau = LocalTime.of(Integer.parseInt(txtGioBatDau.getText()),
                            Integer.parseInt(txtPhutBatDau.getText()));
                    LocalTime gioKetThuc = LocalTime.of(Integer.parseInt(txtGioKetThuc.getText()),
                            Integer.parseInt(txtPhutKetThuc.getText()));
                    CaViewModel_Quan caView = new CaViewModel_Quan();
                    caView.setId(tblCa.getValueAt(row, 1).toString());
                    caView.setMa(txtMaCa.getText());
                    caView.setGioBD(gioBatDau);
                    caView.setGioKT(gioKetThuc);
                    caService.updateCa(caView);
                    JOptionPane.showMessageDialog(this, "Cập nhật ca thành công");
                    showCaToTable(caService.getAllCaDangSuDung());
                    clearFormCRUD();
                } else {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Mã ca và giờ");
                }
            }

        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ca muốn cập nhật");
        }
    }//GEN-LAST:event_btnSuaCaActionPerformed

    private void txtTienThucTeTrongKetKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienThucTeTrongKetKeyReleased
        if (!banHangService.checkSo(txtTienThucTeTrongKet.getText())) {
            lblCanhBaoTienThucTe.setText("Số tiền không hợp lệ");
            txtTienThucTeTrongKet.setText("");
        } else {
            float tienChenhLech = Float.parseFloat(txtTienThucTeTrongKet.getText())
                    - Float.parseFloat(lblTienKetDauCa.getText());
            lblChenhLech.setText(String.valueOf(tienChenhLech));
            lblTienBanGiao.setText(txtTienThucTeTrongKet.getText());
            lblCanhBaoTienThucTe.setText("");
        }
    }//GEN-LAST:event_txtTienThucTeTrongKetKeyReleased

    private void btnXacNhanDongCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanDongCaActionPerformed
        HoatDongCa activity = null;
        if (_admin != null) {
            activity = caRepo.getHoatDongCaRunningOfChiNhanh(((ChiNhanhViewModel_Hoang) modelComboChiNhanh.getSelectedItem()).getId());
        } else {
            activity = caRepo.getHoatDongCaRunningOfChiNhanh(_chiNhanhNguoiDung.getId());
        }

        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        String strDateTimeNow = dateTimeFormat.format(dateTimeNow);
        LocalDateTime timeCloseCa = LocalDateTime.parse(strDateTimeNow, dateTimeFormat);
        caRepo.updateHoatDongRunning(activity.getId(), Float.parseFloat(lblTienBanGiao.getText()),
                timeCloseCa);
        lblCaDangHoatDong.setText("Chưa mở ca");
        JOptionPane.showMessageDialog(this, "Đóng ca thành công");
        caIsRunning = null;
    }//GEN-LAST:event_btnXacNhanDongCaActionPerformed

    private void btnDongCaHuyBoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDongCaHuyBoActionPerformed
        lblTienKetDauCa.setText("0");
        txtTienThucTeTrongKet.setText("");
        lblChenhLech.setText("0");
        lblTienBanGiao.setText("0");
        dLongDongCa.setVisible(false);
    }//GEN-LAST:event_btnDongCaHuyBoActionPerformed

    private void dLongDongCaWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_dLongDongCaWindowLostFocus
        dLongDongCa.setVisible(false);
    }//GEN-LAST:event_dLongDongCaWindowLostFocus

    private void btnMoCaHuyBoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoCaHuyBoActionPerformed
        txtTienKetDauCa.setText("");
        dLogMoCa.setVisible(false);
    }//GEN-LAST:event_btnMoCaHuyBoActionPerformed

    private void btnXacNhanMoCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanMoCaActionPerformed
        int row = tblCa.getSelectedRow();
        if (row != -1 && !txtTienKetDauCa.getText().isBlank()) {
            lblCanhBaoTienDauCa.setText("");
            DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTimeNow = LocalDateTime.now();
            String strDateTimeNow = dateTimeFormat.format(dateTimeNow);
            LocalDateTime timeOpenCa = LocalDateTime.parse(strDateTimeNow, dateTimeFormat);
            if (_admin != null) {
                caRepo.insertHoatDongCaOfChiNhanh(tblCa.getValueAt(row, 1).toString(),
                        ((ChiNhanhViewModel_Hoang) modelComboChiNhanh.getSelectedItem()).getId(),
                        timeOpenCa, Float.parseFloat(txtTienKetDauCa.getText()));
            } else {
                caRepo.insertHoatDongCaOfChiNhanh(tblCa.getValueAt(row, 1).toString(),
                        _chiNhanhNguoiDung.getId(), timeOpenCa, Float.parseFloat(txtTienKetDauCa.getText()));
            }
            JOptionPane.showMessageDialog(this, "Mở ca thành công");
            if (_admin != null) {
                caIsRunning = caRepo.getCaRunningOfChiNhanh(((ChiNhanhViewModel_Hoang) modelComboChiNhanh.getSelectedItem()).getId());
            } else {
                caIsRunning = caRepo.getCaRunningOfChiNhanh(_chiNhanhNguoiDung.getId());
            }
            lblCaDangHoatDong.setText(caIsRunning.getMa() + " Đang hoạt động");
            checkTimeOfCa();
        } else {
            lblCanhBaoTienDauCa.setText("Nhập số tiền");
        }
    }//GEN-LAST:event_btnXacNhanMoCaActionPerformed

    private void dLogMoCaWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_dLogMoCaWindowLostFocus
        dLogMoCa.setVisible(false);
    }//GEN-LAST:event_dLogMoCaWindowLostFocus

    private void txtGioBatDauKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGioBatDauKeyReleased
        if (!caService.checkHourOfCa(txtGioBatDau.getText())) {
            txtGioBatDau.setText("");
            lblCanhBaoGioBatDau.setText("Thời gian không hợp lệ");
        } else {
            lblCanhBaoGioBatDau.setText("");
        }
    }//GEN-LAST:event_txtGioBatDauKeyReleased

    private void txtPhutBatDauKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhutBatDauKeyReleased
        if (!caService.checkMinuteOfCa(txtPhutBatDau.getText())) {
            txtPhutBatDau.setText("");
            lblCanhBaoGioBatDau.setText("Thời gian không hợp lệ");
        } else {
            lblCanhBaoGioBatDau.setText("");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhutBatDauKeyReleased

    private void txtGioKetThucKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGioKetThucKeyReleased
        if (!caService.checkHourOfCa(txtGioKetThuc.getText()) || Integer.parseInt(txtGioKetThuc.getText()) == 0) {
            txtGioKetThuc.setText("");
            lblCanhBaoGioKetThuc.setText("Thời gian không hợp lệ");
        } else {
            lblCanhBaoGioKetThuc.setText("");
        }
    }//GEN-LAST:event_txtGioKetThucKeyReleased

    private void txtPhutKetThucKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhutKetThucKeyReleased
        if (!caService.checkMinuteOfCa(txtPhutKetThuc.getText())) {
            txtPhutKetThuc.setText("");
            lblCanhBaoGioKetThuc.setText("Thời gian không hợp lệ");
        } else {
            lblCanhBaoGioKetThuc.setText("");
        }
    }//GEN-LAST:event_txtPhutKetThucKeyReleased

    private void txtTienKetDauCaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKetDauCaKeyReleased
        if (!banHangService.checkSo(txtTienKetDauCa.getText())) {
            lblCanhBaoTienDauCa.setText("Số tiền không hợp lệ");
            txtTienKetDauCa.setText("");
        } else {
            lblCanhBaoTienDauCa.setText("");
        }
    }//GEN-LAST:event_txtTienKetDauCaKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhatCaNhanVien;
    private javax.swing.JButton btnDongCa;
    private javax.swing.JButton btnDongCaHuyBo;
    private javax.swing.JButton btnKhoiPhucCa;
    private javax.swing.JButton btnMoCa;
    private javax.swing.JButton btnMoCaHuyBo;
    private javax.swing.JButton btnSuaCa;
    private javax.swing.JButton btnThemCa;
    private javax.swing.JButton btnXacNhanDongCa;
    private javax.swing.JButton btnXacNhanMoCa;
    private javax.swing.JButton btnXoaCa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboChiNhanh;
    private javax.swing.JDialog dLogMoCa;
    private javax.swing.JDialog dLongDongCa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCaDangHoatDong;
    private javax.swing.JLabel lblCanhBaoGioBatDau;
    private javax.swing.JLabel lblCanhBaoGioKetThuc;
    private javax.swing.JLabel lblCanhBaoMa;
    private javax.swing.JLabel lblCanhBaoTienDauCa;
    private javax.swing.JLabel lblCanhBaoTienThucTe;
    private javax.swing.JLabel lblChenhLech;
    private javax.swing.JLabel lblGoiYCapNhatCaNv;
    private javax.swing.JLabel lblTienBanGiao;
    private javax.swing.JLabel lblTienKetDauCa;
    private javax.swing.JLabel lblTrangThaiCa;
    private javax.swing.JPanel pnlDongCa;
    private javax.swing.JPanel pnlMoCa;
    private javax.swing.JRadioButton rdoDaXoa;
    private javax.swing.JRadioButton rdoDangSuDung;
    private javax.swing.JTable tblCa;
    private javax.swing.JTable tblNhanVien;
    private fomVe.JxText txtGioBatDau;
    private fomVe.JxText txtGioKetThuc;
    private fomVe.JxText txtMaCa;
    private fomVe.JxText txtPhutBatDau;
    private fomVe.JxText txtPhutKetThuc;
    private javax.swing.JTextField txtTienKetDauCa;
    private javax.swing.JTextField txtTienThucTeTrongKet;
    // End of variables declaration//GEN-END:variables
}
