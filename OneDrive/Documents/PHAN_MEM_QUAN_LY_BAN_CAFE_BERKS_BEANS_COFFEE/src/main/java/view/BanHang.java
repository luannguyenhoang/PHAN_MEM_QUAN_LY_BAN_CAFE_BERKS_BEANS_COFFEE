/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.*;

import fomVe.CustomHeaderRenderer;
import fomVe.CustomScrollBarUI;
import fomVe.SVGIconHelper;
import static fomVe.SVGIconHelper.setIcon;
import domainmodel.Ca;
import domainmodel.TaiKhoanAdmin;
import domainmodel.TaiKhoanNguoiDung;
import repository.CaRepo;
import service.IBanHangService;
import service.implement.BanHangService;
import viewmodel.Area;
import viewmodel.ChiNhanhViewModel_Hoang;
import viewmodel.KhuyenMaiDangHoatDong;
import viewmodel.ProductForSale;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import domainmodel.NhanVien;
import domainmodel.SanPham;

import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import repository.CaRepo;
import service.IBanHangService;
import service.implement.BanHangService;
import utility.Image2;
import static view.OverView.setIcon2;
import viewmodel.Area;
import viewmodel.ChiNhanhViewModel_Hoang;
import viewmodel.KhuyenMaiDangHoatDong;
import viewmodel.NguyenLieuViewModel_Hoang;
import viewmodel.NguyenLieuViewModel_Long;
import viewmodel.ProductForSale;
import viewmodel.SanPhamViewModel;
import viewmodel.Table;
import viewmodel.ThemKhachViewModel;
import viewmodel.ThemKhachViewModel;

/**
 *
 * @author hoang
 */
public class BanHang extends javax.swing.JPanel implements Runnable {

    int count = 0;
    Ca caIsRunning = null;
    private CaRepo caRepo;
    private IBanHangService banHangService;
    private List<ItemSanPham> listItemSanPham;
    private DefaultTableModel modelTableChonSp;
    private DefaultComboBoxModel<Area> modelComboArea;
    private DefaultComboBoxModel<ChiNhanhViewModel_Hoang> modelComboChiNhanh;
    private List<ItemBan> listItemBan;
    ThemKhachViewModel _khach = null;
    private boolean _ConfirmCloseThanhToan = false;
    private boolean _ConfirmCloseThemKhach = false;
    TaiKhoanAdmin _admin;
    TaiKhoanNguoiDung _nguoiDung;
    ChiNhanhViewModel_Hoang _chiNhanhNguoiDung;
    private static FlatSVGIcon icon = null;

    public static void setIcon2(JButton label, String path, int width, int height) {
        try {
            icon = new FlatSVGIcon(path, width, height);
            label.setIcon(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BanHang(TaiKhoanAdmin admin, TaiKhoanNguoiDung nguoiDung) {

        initComponents();
        _admin = admin;
        _nguoiDung = nguoiDung;
        caRepo = new CaRepo();
        SVGIconHelper.setIcon(lblTimKiem, "icon/search-alt-svgrepo-com.svg", 25, 25);
        SVGIconHelper.setIcon(btnOpenFormAddGuest, "icon/add.svg", 25, 25);
        SVGIconHelper.setIcon(lblSearchKH, "icon/search-alt-svgrepo-com.svg", 20, 20);
        setIcon2(btnTT, "icon/pay-by-credit-card-svgrepo-com.svg", 50, 50);
        SVGIconHelper.setIcon(lblCloseThemKhach, "icon/close-md-svgrepo-com.svg", 20, 20);
        txtTimKhach.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    lblSearchKHMouseClicked(null);
                }
            }
        });
        // SVGIconHelper.setIcon(btnOpenFormAddGuest, "", 20, 20);
        //btnOpenFormAddGuest 
        listItemSanPham = new ArrayList<>();
        listItemBan = new ArrayList<>();
        hideInfoKhach();
        modelTableChonSp = new DefaultTableModel();
        modelTableChonSp = (DefaultTableModel) tblSpChon.getModel();
        banHangService = new BanHangService();
        btnOpenFormAddGuest.setCursor(new Cursor(Cursor.HAND_CURSOR));

        cboChiNhanh.setRenderer(new BasicComboBoxRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setFocusable(false);
                return this;
            }
        });
        cboKhuVuc.setRenderer(new BasicComboBoxRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setFocusable(false);
                return this;
            }
        });

        cpListSp.getVerticalScrollBar().setUnitIncrement(20);
        cpListSp.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        cpBan.getVerticalScrollBar().setUnitIncrement(20);

        checkTimeOfCa();
        Thread loadData = new Thread(this);

        loadData.start();
        try {
            loadData.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
        }
        run();

        ActionListener actionListener = cboKhuVuc.getActionListeners()[0];
        actionListener.actionPerformed(new ActionEvent(cboKhuVuc, ActionEvent.ACTION_PERFORMED, null));

        JTableHeader tableHeader = tblSpChon.getTableHeader();
        tableHeader.setBackground(new Color(143, 45, 52));
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setBorder(null);
        TableCellRenderer defaultHeaderRenderer = tblSpChon.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer = new CustomHeaderRenderer(defaultHeaderRenderer);
        tblSpChon.getTableHeader().setDefaultRenderer(customHeaderRenderer);
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
                                //btnTT.setEnabled(false);
                                //   btnOpenFormAddGuest.setEnabled(false);
                                lblStateMoCa.setText("Chưa mở ca");

                            }
                        } catch (InterruptedException ex) {
                            Logger.getLogger(QLca.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {
                        stop = true;
                    }
                }
            }
        });
        notificationCloseCa.start();
    }

    /**
     *
     */
    @Override
    public void run() {
        if (_nguoiDung != null) {
            try {
                _chiNhanhNguoiDung = banHangService.getChiNhanhbyTaiKhoan(_nguoiDung.getId());
                modelComboArea = (DefaultComboBoxModel) new DefaultComboBoxModel<>(banHangService.
                        getAllKhuVucByChiNhanh(_chiNhanhNguoiDung.getId()).toArray());
                cboChiNhanh.setVisible(false);
                lblChiNhanh.setText("");
                showProductForSale(banHangService.getAllProductForSaleByChiNhanh(_chiNhanhNguoiDung.getId()));
                showKhuyenMai(_chiNhanhNguoiDung.getId());
                caIsRunning = caRepo.getCaRunningOfChiNhanh(_chiNhanhNguoiDung.getId());
                if (caIsRunning != null) {
                    btnTT.setEnabled(true);
                    btnOpenFormAddGuest.setEnabled(true);
                    lblStateMoCa.setText("");
                }
            } catch (IOException ex) {
                Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                cboChiNhanh.setVisible(true);
                lblChiNhanh.setText("Chi nhánh");
                modelComboChiNhanh = (DefaultComboBoxModel) new DefaultComboBoxModel<>(banHangService.getAllChiNhanh().toArray());
                cboChiNhanh.setModel((DefaultComboBoxModel) modelComboChiNhanh);
                modelComboArea = (DefaultComboBoxModel) new DefaultComboBoxModel<>(banHangService.
                        getAllKhuVucByChiNhanh(((ChiNhanhViewModel_Hoang) modelComboChiNhanh.getSelectedItem()).getId()).toArray());
                showProductForSale(banHangService.getAllProductForSaleByChiNhanh(((ChiNhanhViewModel_Hoang) modelComboChiNhanh.getElementAt(0)).getId())
                );
                showKhuyenMai(((ChiNhanhViewModel_Hoang) modelComboChiNhanh.getSelectedItem()).getId());
                caIsRunning = caRepo.getCaRunningOfChiNhanh(((ChiNhanhViewModel_Hoang) modelComboChiNhanh.getElementAt(0)).getId());
                if (caIsRunning != null) {
                    btnTT.setEnabled(true);
                    btnOpenFormAddGuest.setEnabled(true);
                    lblStateMoCa.setText("");
                } else {
                    btnTT.setEnabled(false);
                    btnOpenFormAddGuest.setEnabled(false);
                    lblStateMoCa.setText("Chưa mở ca");

                }
            } catch (IOException ex) {
                Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        cboKhuVuc.setModel((DefaultComboBoxModel) modelComboArea);
        setEventClickForItemSanPham();

    }

    void clearTamTinh() {
        lblSoBan.setText("-");
        lblTongTien.setText("0 đ");
        lblTongTien2.setText("");
        lblTienDuocGiamGia.setText("0 đ");
        lblTienCanThanhToan.setText("0 đ");
        lblTienCanThanhToan2.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DlogThemKhach = new javax.swing.JDialog();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        txtMaKhach = new fomVe.JxText();
        lblCanhBaoMaKhach = new javax.swing.JLabel();
        txtSdt = new fomVe.JxText();
        lblCanhBaoSdt = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        txtTenKhach = new fomVe.JxText();
        lblCanhBaoTenKhach = new javax.swing.JLabel();
        cboQuocGia = new javax.swing.JComboBox<>();
        txtThanhPho = new fomVe.JxText();
        btnAddGuest = new javax.swing.JButton();
        rdoNu = new javax.swing.JRadioButton();
        rdoNam = new javax.swing.JRadioButton();
        lblCloseThemKhach = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        DlogTraTien = new javax.swing.JDialog();
        curvesPanel1 = new fomVe.CurvesPanel();
        jPanel4 = new javax.swing.JPanel();
        lblSoTienCanTra = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        lblDiemTichLuy = new javax.swing.JLabel();
        lblTienTichLuy = new javax.swing.JLabel();
        cboDungDiem = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        lblSoTienCanTra2 = new javax.swing.JLabel();
        txtTienKhachDua = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        lblTienThua = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        lblCanhBaoTienKhachDua = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblTienTichLuy2 = new javax.swing.JLabel();
        textFieldLogin1 = new fomVe.TextFieldLogin();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jLabel11 = new javax.swing.JLabel();
        cboChiNhanh = new javax.swing.JComboBox<>();
        lblChiNhanh = new javax.swing.JLabel();
        cboKhuVuc = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cpBan = new javax.swing.JScrollPane();
        pnlBan = new javax.swing.JPanel();
        cpListSp = new javax.swing.JScrollPane();
        pnlListSp = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblSpChon = new javax.swing.JTable();
        panel1 = new fomVe.Panel();
        lblTimKiem = new javax.swing.JLabel();
        txtTimSp = new fomVe.JtextSearch();
        jPanel1 = new javax.swing.JPanel();
        lblSlide = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtKhuyenMai = new javax.swing.JTextPane();
        panel2 = new fomVe.Panel();
        txtTimKhach = new fomVe.JtextSearch1();
        btnOpenFormAddGuest = new javax.swing.JLabel();
        lblSearchKH = new javax.swing.JLabel();
        lblStateMoCa = new javax.swing.JLabel();
        panel3 = new fomVe.Panel();
        jPanel2 = new javax.swing.JPanel();
        lblTongTien = new javax.swing.JLabel();
        lblTienCanThanhToan = new javax.swing.JLabel();
        lblSoBan = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblTienDuocGiamGia = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblTienCanThanhToan2 = new javax.swing.JLabel();
        lblTongTien2 = new javax.swing.JLabel();
        btnTT = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtHienThiKhach = new javax.swing.JTextArea();

        DlogThemKhach.setBackground(new java.awt.Color(255, 255, 255));
        DlogThemKhach.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                DlogThemKhachWindowLostFocus(evt);
            }
        });

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jPanel10.setOpaque(false);
        jPanel10.setLayout(new java.awt.GridLayout(4, 1, 0, 5));

        txtMaKhach.setPrompt("Mã khách hàng");
        jPanel10.add(txtMaKhach);

        lblCanhBaoMaKhach.setBackground(new java.awt.Color(255, 255, 255));
        lblCanhBaoMaKhach.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lblCanhBaoMaKhach.setForeground(new java.awt.Color(255, 0, 0));
        jPanel10.add(lblCanhBaoMaKhach);

        txtSdt.setPrompt("Số điện thoại");
        jPanel10.add(txtSdt);

        lblCanhBaoSdt.setBackground(new java.awt.Color(255, 255, 255));
        lblCanhBaoSdt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lblCanhBaoSdt.setForeground(new java.awt.Color(255, 51, 51));
        jPanel10.add(lblCanhBaoSdt);

        jPanel11.setOpaque(false);
        jPanel11.setLayout(new java.awt.GridLayout(4, 1, 0, 5));

        txtTenKhach.setPrompt("Tên khách hàng");
        jPanel11.add(txtTenKhach);

        lblCanhBaoTenKhach.setBackground(new java.awt.Color(228, 212, 189));
        lblCanhBaoTenKhach.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        lblCanhBaoTenKhach.setForeground(new java.awt.Color(255, 51, 51));
        jPanel11.add(lblCanhBaoTenKhach);

        cboQuocGia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua", "Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia", "Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo", "Costa Rica", "Côte d’Ivoire", "Croatia", "Cuba", "Cyprus", "Czechia", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "DR Congo", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Holy See", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "North Macedonia", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "Saint Kitts & Nevis", "Saint Lucia", "Samoa", "San Marino", "Sao Tome & Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "St. Vincent & Grenadines", "State of Palestine", "Sudan", "Suriname", "Sweden", "Switzerland", "Syria", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe" }));
        cboQuocGia.setSelectedIndex(193);
        jPanel11.add(cboQuocGia);

        txtThanhPho.setPrompt("Thành phố");
        jPanel11.add(txtThanhPho);

        btnAddGuest.setBackground(new java.awt.Color(108, 83, 54));
        btnAddGuest.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnAddGuest.setForeground(new java.awt.Color(255, 255, 255));
        btnAddGuest.setText("Thêm khách");
        btnAddGuest.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddGuest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddGuestActionPerformed(evt);
            }
        });

        buttonGroup3.add(rdoNu);
        rdoNu.setForeground(new java.awt.Color(0, 0, 0));
        rdoNu.setText("Nữ");

        buttonGroup3.add(rdoNam);
        rdoNam.setForeground(new java.awt.Color(0, 0, 0));
        rdoNam.setSelected(true);
        rdoNam.setText("Nam");

        lblCloseThemKhach.setBackground(new java.awt.Color(255, 255, 255));
        lblCloseThemKhach.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblCloseThemKhach.setForeground(new java.awt.Color(0, 0, 0));
        lblCloseThemKhach.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCloseThemKhach.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCloseThemKhach.setOpaque(true);
        lblCloseThemKhach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCloseThemKhachMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCloseThemKhachMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCloseThemKhachMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(rdoNu)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(166, 166, 166)
                        .addComponent(btnAddGuest))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoNam)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(98, 98, 98))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(lblCloseThemKhach, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(lblCloseThemKhach, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(btnAddGuest))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoNu)
                            .addComponent(rdoNam))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout DlogThemKhachLayout = new javax.swing.GroupLayout(DlogThemKhach.getContentPane());
        DlogThemKhach.getContentPane().setLayout(DlogThemKhachLayout);
        DlogThemKhachLayout.setHorizontalGroup(
            DlogThemKhachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
        );
        DlogThemKhachLayout.setVerticalGroup(
            DlogThemKhachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DlogThemKhachLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 387, Short.MAX_VALUE))
        );

        DlogTraTien.setBackground(new java.awt.Color(102, 102, 102));

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));
        jPanel4.setOpaque(false);

        lblSoTienCanTra.setForeground(new java.awt.Color(67, 67, 67));

        jPanel7.setBackground(new java.awt.Color(51, 51, 51));
        jPanel7.setOpaque(false);
        jPanel7.setLayout(new java.awt.GridLayout(7, 1, 0, 5));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 0, 0));
        jLabel18.setText("=");

        lblDiemTichLuy.setBackground(new java.awt.Color(255, 255, 255));
        lblDiemTichLuy.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblDiemTichLuy.setForeground(new java.awt.Color(0, 0, 0));
        lblDiemTichLuy.setText("0");

        lblTienTichLuy.setBackground(new java.awt.Color(255, 255, 255));
        lblTienTichLuy.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTienTichLuy.setForeground(new java.awt.Color(0, 0, 0));
        lblTienTichLuy.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDiemTichLuy, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTienTichLuy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(lblTienTichLuy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblDiemTichLuy, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel7.add(jPanel3);

        cboDungDiem.setBackground(new java.awt.Color(67, 67, 67));
        cboDungDiem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cboDungDiem.setForeground(new java.awt.Color(255, 255, 255));
        cboDungDiem.setText("Xác nhận dùng điểm");
        cboDungDiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDungDiemActionPerformed(evt);
            }
        });
        jPanel7.add(cboDungDiem);

        jPanel5.setBackground(new java.awt.Color(240, 240, 240));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));

        lblSoTienCanTra2.setBackground(new java.awt.Color(255, 0, 0));
        lblSoTienCanTra2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSoTienCanTra2.setForeground(new java.awt.Color(255, 0, 0));
        lblSoTienCanTra2.setText("0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(lblSoTienCanTra2, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSoTienCanTra2, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
        );

        jPanel7.add(jPanel5);

        txtTienKhachDua.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTienKhachDua.setForeground(new java.awt.Color(0, 0, 0));
        txtTienKhachDua.setText("0");
        txtTienKhachDua.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));
        txtTienKhachDua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienKhachDuaActionPerformed(evt);
            }
        });
        txtTienKhachDua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKhachDuaKeyReleased(evt);
            }
        });
        jPanel7.add(txtTienKhachDua);

        jPanel6.setBackground(new java.awt.Color(240, 240, 240));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));

        lblTienThua.setBackground(new java.awt.Color(0, 255, 51));
        lblTienThua.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTienThua.setForeground(new java.awt.Color(0, 204, 51));
        lblTienThua.setText("0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(lblTienThua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTienThua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
        );

        jPanel7.add(jPanel6);

        jButton1.setBackground(new java.awt.Color(0, 204, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI Light", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Trả tiền");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton1);

        lblCanhBaoTienKhachDua.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblCanhBaoTienKhachDua.setForeground(new java.awt.Color(255, 0, 0));
        lblCanhBaoTienKhachDua.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel7.add(lblCanhBaoTienKhachDua);

        jPanel8.setBackground(new java.awt.Color(51, 51, 51));
        jPanel8.setOpaque(false);
        jPanel8.setLayout(new java.awt.GridLayout(7, 1, 0, 5));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Tỉ lệ quy đổi :");
        jPanel8.add(jLabel13);

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Điểm tích lũy :");
        jPanel8.add(jLabel20);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Số tiền cần trả :");
        jPanel8.add(jLabel14);

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Số tiền khách đưa :");
        jPanel8.add(jLabel15);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Tiền thừa :");
        jPanel8.add(jLabel16);
        jPanel8.add(jLabel2);
        jPanel8.add(jLabel4);

        lblTienTichLuy2.setForeground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTienTichLuy2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSoTienCanTra)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(lblSoTienCanTra, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(112, 112, 112)
                        .addComponent(lblTienTichLuy2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout curvesPanel1Layout = new javax.swing.GroupLayout(curvesPanel1);
        curvesPanel1.setLayout(curvesPanel1Layout);
        curvesPanel1Layout.setHorizontalGroup(
            curvesPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        curvesPanel1Layout.setVerticalGroup(
            curvesPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout DlogTraTienLayout = new javax.swing.GroupLayout(DlogTraTien.getContentPane());
        DlogTraTien.getContentPane().setLayout(DlogTraTienLayout);
        DlogTraTienLayout.setHorizontalGroup(
            DlogTraTienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(curvesPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        DlogTraTienLayout.setVerticalGroup(
            DlogTraTienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DlogTraTienLayout.createSequentialGroup()
                .addComponent(curvesPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 42, Short.MAX_VALUE))
        );

        textFieldLogin1.setText("textFieldLogin1");

        jLabel11.setText("jLabel11");

        setBackground(new java.awt.Color(51, 51, 51));

        cboChiNhanh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Khu vực" }));
        cboChiNhanh.setToolTipText("");
        cboChiNhanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChiNhanhActionPerformed(evt);
            }
        });

        lblChiNhanh.setForeground(new java.awt.Color(255, 255, 255));
        lblChiNhanh.setText("Chi nhánh :");

        cboKhuVuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKhuVucActionPerformed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Khu vực :");

        cpBan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        pnlBan.setLayout(new java.awt.GridLayout(0, 4, 5, 5));
        cpBan.setViewportView(pnlBan);

        cpListSp.setBackground(new java.awt.Color(255, 255, 255));
        cpListSp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        pnlListSp.setBackground(new java.awt.Color(232, 232, 232));
        pnlListSp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        pnlListSp.setForeground(new java.awt.Color(153, 153, 153));
        pnlListSp.setLayout(new java.awt.GridLayout(0, 4, 6, 6));
        cpListSp.setViewportView(pnlListSp);

        jScrollPane7.setBorder(null);

        tblSpChon.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        tblSpChon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Mã", "Tên sản phẩm", "Đơn giá", "Số lượng", "Thành tiền", "Gỡ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSpChon.setGridColor(new java.awt.Color(255, 255, 255));
        tblSpChon.getTableHeader().setReorderingAllowed(false);
        tblSpChon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSpChonMouseClicked(evt);
            }
        });
        tblSpChon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblSpChonKeyReleased(evt);
            }
        });
        jScrollPane7.setViewportView(tblSpChon);
        if (tblSpChon.getColumnModel().getColumnCount() > 0) {
            tblSpChon.getColumnModel().getColumn(0).setMinWidth(0);
            tblSpChon.getColumnModel().getColumn(0).setMaxWidth(0);
            tblSpChon.getColumnModel().getColumn(1).setPreferredWidth(10);
            tblSpChon.getColumnModel().getColumn(2).setResizable(false);
            tblSpChon.getColumnModel().getColumn(3).setResizable(false);
            tblSpChon.getColumnModel().getColumn(4).setResizable(false);
            tblSpChon.getColumnModel().getColumn(4).setPreferredWidth(25);
            tblSpChon.getColumnModel().getColumn(5).setResizable(false);
            tblSpChon.getColumnModel().getColumn(6).setResizable(false);
            tblSpChon.getColumnModel().getColumn(6).setPreferredWidth(10);
        }

        panel1.setBackground(new java.awt.Color(255, 255, 255));

        lblTimKiem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search.png"))); // NOI18N

        txtTimSp.setBackground(new java.awt.Color(255, 255, 255));
        txtTimSp.setBorder(null);
        txtTimSp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimSpKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addComponent(lblTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimSp, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addComponent(lblTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimSp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblSlide.setBackground(new java.awt.Color(255, 255, 255));
        lblSlide.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/backround.gif"))); // NOI18N
        lblSlide.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel5.setFont(new java.awt.Font("SimSun", 0, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Discover the Richness of Berk's Beans, Your Perfect Brew Haven!");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Thông tin khách hàng :");

        txtKhuyenMai.setEditable(false);
        txtKhuyenMai.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtKhuyenMai.setForeground(new java.awt.Color(255, 0, 0));
        jScrollPane2.setViewportView(txtKhuyenMai);

        panel2.setBackground(new java.awt.Color(255, 255, 255));

        txtTimKhach.setBorder(null);

        btnOpenFormAddGuest.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnOpenFormAddGuest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/plusNho.png"))); // NOI18N
        btnOpenFormAddGuest.setText(" ");
        btnOpenFormAddGuest.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnOpenFormAddGuestMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnOpenFormAddGuestMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnOpenFormAddGuestMouseExited(evt);
            }
        });

        lblSearchKH.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSearchKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSearchKHMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addComponent(lblSearchKH, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKhach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOpenFormAddGuest))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSearchKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnOpenFormAddGuest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKhach, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblStateMoCa.setForeground(new java.awt.Color(255, 0, 0));
        lblStateMoCa.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblStateMoCa.setText("Chưa mở ca");
        lblStateMoCa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        panel3.setBackground(new java.awt.Color(240, 240, 240));

        jPanel2.setBackground(new java.awt.Color(240, 240, 240));

        lblTongTien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTongTien.setForeground(new java.awt.Color(161, 44, 52));
        lblTongTien.setText("0");

        lblTienCanThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        lblTienCanThanhToan.setForeground(new java.awt.Color(141, 38, 45));
        lblTienCanThanhToan.setText("0");

        lblSoBan.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        lblSoBan.setForeground(new java.awt.Color(255, 51, 51));
        lblSoBan.setText("-");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Số Bàn :");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Tổng tiền :");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Tiền cần thanh toán :");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Tiền giảm giá :");

        lblTienDuocGiamGia.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTienDuocGiamGia.setForeground(new java.awt.Color(0, 204, 0));
        lblTienDuocGiamGia.setText("0");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Tạm tính");

        lblTienCanThanhToan2.setForeground(new java.awt.Color(240, 240, 240));
        lblTienCanThanhToan2.setText("0");

        lblTongTien2.setForeground(new java.awt.Color(240, 240, 240));
        lblTongTien2.setText("jLabel21");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTienCanThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblTongTien2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSoBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(26, 26, 26)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTienCanThanhToan2)
                .addGap(6, 6, 6))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTienDuocGiamGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTienCanThanhToan2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTongTien2)
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblSoBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTienDuocGiamGia)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTienCanThanhToan)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(1, 1, 1)))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnTT.setBackground(new java.awt.Color(155, 49, 56));
        btnTT.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        btnTT.setForeground(new java.awt.Color(255, 255, 255));
        btnTT.setText("Thanh toán");
        btnTT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTTMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTTMouseExited(evt);
            }
        });
        btnTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTTActionPerformed(evt);
            }
        });

        txtHienThiKhach.setEditable(false);
        txtHienThiKhach.setColumns(20);
        txtHienThiKhach.setRows(5);
        jScrollPane1.setViewportView(txtHienThiKhach);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cpBan, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboChiNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblChiNhanh))
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cboKhuVuc, 0, 162, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblSlide, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cpListSp, javax.swing.GroupLayout.PREFERRED_SIZE, 837, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnTT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(122, 122, 122)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblStateMoCa, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblChiNhanh)
                                    .addComponent(jLabel3))
                                .addGap(7, 7, 7))
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cboChiNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboKhuVuc))
                                .addGap(3, 3, 3)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(cpBan, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lblSlide, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(257, 257, 257)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(lblStateMoCa))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTT, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cpListSp, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimSpKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimSpKeyReleased
        if (_nguoiDung != null) {
            if (!txtTimSp.getText().isBlank()) {
                try {
                    showProductForSale(banHangService.searchProductForSaleByTenSpAndChiNhanh(
                            txtTimSp.getText(), _chiNhanhNguoiDung.getId())
                    );
                    setEventClickForItemSanPham();
                } catch (IOException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    showProductForSale(banHangService.getAllProductForSaleByChiNhanh(_chiNhanhNguoiDung.getId()));
                    setEventClickForItemSanPham();
                } catch (IOException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {
            if (!txtTimSp.getText().isBlank()) {
                try {
                    showProductForSale(banHangService.searchProductForSaleByTenSpAndChiNhanh(
                            txtTimSp.getText(), ((ChiNhanhViewModel_Hoang) modelComboChiNhanh.getSelectedItem()).getId())
                    );
                    setEventClickForItemSanPham();
                } catch (IOException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    showProductForSale(banHangService.getAllProductForSaleByChiNhanh(
                            ((ChiNhanhViewModel_Hoang) modelComboChiNhanh.getSelectedItem()).getId())
                    );
                    setEventClickForItemSanPham();
                } catch (IOException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }    }//GEN-LAST:event_txtTimSpKeyReleased

    private void cboChiNhanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChiNhanhActionPerformed
        try {
            modelComboArea = (DefaultComboBoxModel) new DefaultComboBoxModel<>(banHangService.
                    getAllKhuVucByChiNhanh(((ChiNhanhViewModel_Hoang) modelComboChiNhanh.getSelectedItem()).getId()).toArray());
            cboKhuVuc.setModel((DefaultComboBoxModel) modelComboArea);
            modelTableChonSp.setRowCount(0);
            resetPanelBanAndShow();
            showProductForSale(banHangService.getAllProductForSaleByChiNhanh(
                    ((ChiNhanhViewModel_Hoang) modelComboChiNhanh.getSelectedItem()).getId())
            );
            setEventClickForItemSanPham();
            showKhuyenMai(((ChiNhanhViewModel_Hoang) modelComboChiNhanh.getSelectedItem()).getId());
            Ca caRunning = caRepo.getCaRunningOfChiNhanh(((ChiNhanhViewModel_Hoang) modelComboChiNhanh.getSelectedItem()).getId());
            if (caRunning != null) {
                btnTT.setEnabled(true);
                btnOpenFormAddGuest.setEnabled(true);
                lblStateMoCa.setText("");
            } else {
                //btnTT.setEnabled(false);
                //  btnOpenFormAddGuest.setEnabled(false);
                lblStateMoCa.setText("Chưa mở ca");
            }
            caIsRunning = caRepo.getCaRunningOfChiNhanh(((ChiNhanhViewModel_Hoang) modelComboChiNhanh.getSelectedItem()).getId());
            checkTimeOfCa();
        } catch (IOException ex) {
            Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cboChiNhanhActionPerformed

    private void cboKhuVucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKhuVucActionPerformed
        if (cboKhuVuc.getItemCount() > 0) {
            resetPanelBanAndShow();
        }
    }//GEN-LAST:event_cboKhuVucActionPerformed

    private void btnOpenFormAddGuestMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOpenFormAddGuestMouseClicked
        DlogThemKhach.setUndecorated(true);
        DlogThemKhach.setVisible(true);
        DlogThemKhach.setSize(548, 242);
        DlogThemKhach.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnOpenFormAddGuestMouseClicked

    private void txtTienKhachDuaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDuaKeyReleased

        if (banHangService.checkSo(txtTienKhachDua.getText())) {
            double tienThua = Double.parseDouble(txtTienKhachDua.getText()) - Double.parseDouble(lblSoTienCanTra.getText());

            Locale locale = new Locale("vi", "VN");
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);

            lblTienThua.setText(currencyFormat.format(tienThua));
        } else {
            lblCanhBaoTienKhachDua.setText("Số tiền không hợp lệ");
            txtTienKhachDua.setText("");
        }
        if (txtTienKhachDua.getText().length() > 0) {
            lblCanhBaoTienKhachDua.setText("");
        } else if (txtTienKhachDua.getText().isBlank()) {
            lblTienThua.setText("0");
        }
    }//GEN-LAST:event_txtTienKhachDuaKeyReleased

    private void tblSpChonKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblSpChonKeyReleased
        int row = tblSpChon.getSelectedRow();
        double thanhTien = 0;
        if (!banHangService.checkSo(tblSpChon.getValueAt(row, 4).toString())) {
            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ");
            tblSpChon.setValueAt(1, row, 4);
        } else {
            if (Integer.parseInt(tblSpChon.getValueAt(row, 4).toString()) == 0) {
                modelTableChonSp.removeRow(row);
            } else {
                thanhTien = Double.parseDouble(tblSpChon.getValueAt(row, 4).toString()) * Double.valueOf(tblSpChon.getValueAt(row, 3).toString());
                tblSpChon.setValueAt(new BigDecimal(thanhTien), row, 5);
                totalMoney();
                totalMoneyAfterSale();
            }
        }
    }//GEN-LAST:event_tblSpChonKeyReleased

    private void tblSpChonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSpChonMouseClicked
        int row = tblSpChon.getSelectedRow();
        int column = tblSpChon.getSelectedColumn();
        if (column == 6) {
            boolean remove = (boolean) tblSpChon.getValueAt(row, column);
            if (remove == true) {
                modelTableChonSp.removeRow(row);
                totalMoney();
                totalMoneyAfterSale();
            }
        }
    }//GEN-LAST:event_tblSpChonMouseClicked

    private void txtTienKhachDuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienKhachDuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienKhachDuaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (txtTienKhachDua.getText().isBlank()) {
            lblCanhBaoTienKhachDua.setText("Vui lòng nhập tiền khách đưa");
        } else if (Double.parseDouble(txtTienKhachDua.getText()) < Double.parseDouble(lblSoTienCanTra.getText())) {
            lblCanhBaoTienKhachDua.setText("Số tiền chưa đủ thanh toán");
        } else {
            LoadingData loading = new LoadingData(null, true);
            SwingWorker sw = new SwingWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    lblCanhBaoTienKhachDua.setText("");
                    LocalDateTime timeNow = LocalDateTime.now();
                    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String strTimeNow = dateFormat.format(timeNow);
                    LocalDateTime ngayTaoHD = LocalDateTime.parse(strTimeNow, dateFormat);
                    String idHoaDon = null;
                    if (_nguoiDung != null) {
                        NhanVien nhanVien = banHangService.getNhanVienbyTaiKhoan(_nguoiDung.getId());
                        idHoaDon = banHangService.inserHoaDon(banHangService.autoGenMaHoaDon(), ngayTaoHD,
                                nhanVien.getId(), Integer.parseInt(lblSoBan.getText()));
                    } else {
                        idHoaDon = banHangService.inserHoaDon(banHangService.autoGenMaHoaDon(), ngayTaoHD,
                                null, Integer.parseInt(lblSoBan.getText()));
                    }

                    for (int i = 0; i < tblSpChon.getRowCount(); i++) {
                        banHangService.insertChiTietHoaDon(tblSpChon.getValueAt(i, 0).toString(),
                                idHoaDon, Integer.parseInt(tblSpChon.getValueAt(i, 4).toString()),
                                new BigDecimal(lblTongTien2.getText()), new BigDecimal(lblSoTienCanTra.getText()));
                        banHangService.updateNguyenLieuAfterSellSanPham(tblSpChon.
                                getValueAt(i, 0).toString(), Integer.parseInt(tblSpChon.getValueAt(i, 4).toString()));
                    }
                    if (_khach != null) {
                        Integer diemTichLuyHienTai = Integer.parseInt(lblDiemTichLuy.getText());
                        if (Float.parseFloat(lblTienCanThanhToan2.getText()) > banHangService.getGiaTriDoiDiem()) {
                            double diemMuaHang = Float.parseFloat(lblTienCanThanhToan2.getText()) / banHangService.getGiaTriDoiDiem();
                            Integer diemUpdate = diemTichLuyHienTai + (int) Math.floor(diemMuaHang);
                            banHangService.updateDiemKhachHang(_khach.getId(), diemUpdate);
                        } else {
                            banHangService.updateDiemKhachHang(_khach.getId(), Integer.parseInt(lblDiemTichLuy.getText()));
                        }
                    }
                    banHangService.updateTrangThaiBanBySoBan(Integer.parseInt(lblSoBan.getText()));
                    _ConfirmCloseThanhToan = true;
                    if (_ConfirmCloseThanhToan == true) {
                        DlogTraTien.setVisible(false);
                    }
                 
                    return null;

                }

                @Override
                protected void done() {
                    loading.dispose();
                    JOptionPane.showMessageDialog(DlogTraTien, "Thanh toán thành công");
                    
                    resetPanelBanAndShow();
                    clearFormTraTien();
                    clearTamTinh();
                }
            };
            sw.execute();
            loading.setVisible(true);

        }
        this.disable();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTTActionPerformed
        if (tblSpChon.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm");
        } else if (lblSoBan.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "vui lòng chọn số bàn");
        } else if (tblSpChon.getRowCount() > 0 && !lblSoBan.getText().isEmpty()) {
            DlogTraTien.setVisible(true);
            DlogTraTien.setSize(560, 444);
            DlogTraTien.setBackground(new Color(102, 102, 102));
            DlogTraTien.setLocationRelativeTo(null);
            setTraTien();
        } else {
            JOptionPane.showMessageDialog(this, "Vui chọn lòng sản phẩm và Số bàn");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnTTActionPerformed

    private void btnOpenFormAddGuestMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOpenFormAddGuestMouseEntered
        SVGIconHelper.setIcon(btnOpenFormAddGuest, "icon/add2.svg", 25, 25);        // TODO add your handling code here:
    }//GEN-LAST:event_btnOpenFormAddGuestMouseEntered

    private void btnOpenFormAddGuestMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOpenFormAddGuestMouseExited
        SVGIconHelper.setIcon(btnOpenFormAddGuest, "icon/add.svg", 25, 25);
    }//GEN-LAST:event_btnOpenFormAddGuestMouseExited

    private void lblCloseThemKhachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseThemKhachMouseClicked
        _ConfirmCloseThemKhach = true;
        if (_ConfirmCloseThemKhach == true) {
            DlogThemKhach.dispose();
        }
    }//GEN-LAST:event_lblCloseThemKhachMouseClicked

    private void lblCloseThemKhachMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseThemKhachMouseEntered
        lblCloseThemKhach.setBackground(Color.red);
    }//GEN-LAST:event_lblCloseThemKhachMouseEntered

    private void lblCloseThemKhachMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseThemKhachMouseExited

        lblCloseThemKhach.setBackground(Color.WHITE);
    }//GEN-LAST:event_lblCloseThemKhachMouseExited

    private void btnAddGuestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddGuestActionPerformed
        if (checkEmptyFormAddGuest() && checkMaKhach(txtMaKhach.getText())
                && checkFormatSdt(txtSdt.getText()) && checkExistedSdt(txtSdt.getText())) {
            ThemKhachViewModel khachView = new ThemKhachViewModel();
            khachView.setMa(txtMaKhach.getText());
            khachView.setSdt(txtSdt.getText());
            khachView.setHoTen(txtTenKhach.getText());
            khachView.setGioiTinh(rdoNam.isSelected() ? "Nam" : "Nữ");
            khachView.setQuocGia(cboQuocGia.getSelectedItem().toString());
            if (!txtThanhPho.getText().isBlank()) {
                khachView.setThanhPho(txtThanhPho.getText());
            }
            banHangService.insertKhachHang(khachView);
            _ConfirmCloseThemKhach = true;
            if (_ConfirmCloseThemKhach == true) {
                DlogThemKhach.setVisible(false);
            }
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công");
        }
    }//GEN-LAST:event_btnAddGuestActionPerformed

    private void DlogThemKhachWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_DlogThemKhachWindowLostFocus
        if (_ConfirmCloseThemKhach == true) {
            DlogThemKhach.setVisible(false);
            _ConfirmCloseThemKhach = false;
        } else {
            JOptionPane.showMessageDialog(DlogThemKhach, "Vui lòng đóng giao diện hiện tại");
            DlogThemKhach.setVisible(true);

        }        // TODO add your handling code here:
    }//GEN-LAST:event_DlogThemKhachWindowLostFocus

    private void lblSearchKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSearchKHMouseClicked
        _khach = banHangService.getKhachHangBySdt(txtTimKhach.getText());
        if (_khach != null) {
            txtHienThiKhach.setText("Mã khách hàng : " + _khach.getMa() + "\n" + "Tên khách hàng : " + _khach.getHoTen() + "\n"
                    + "Số điện thoại : " + _khach.getSdt() + "\n" + "Số điểm : " + _khach.getDiemTichLuy());

        } else {
            JOptionPane.showMessageDialog(this, "Thông tin không tồn tại. Vui lòng kiểm tra lại !");
            hideInfoKhach();
        }
    }//GEN-LAST:event_lblSearchKHMouseClicked
    private void setTraTien() {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);

        double tienTichLuy = 0;
        int diemTichLuy = 0;
        if (_khach != null) {
            diemTichLuy = _khach.getDiemTichLuy();
            tienTichLuy = _khach.getDiemTichLuy() * banHangService.getGiaTriDiem();
            lblDiemTichLuy.setText(String.valueOf(diemTichLuy));

            lblTienTichLuy.setText(currencyFormat.format(tienTichLuy));

        }
        lblSoTienCanTra2.setText(lblTienCanThanhToan.getText());
        if (cboDungDiem.isSelected()) {
            txtTienKhachDua.setText("0");
            lblTienThua.setText("0");
            double tienCanThanhToan = Double.parseDouble(lblSoTienCanTra.getText());
            float giaTriDiem = banHangService.getGiaTriDiem();
            if (tienTichLuy > tienCanThanhToan) {
                double tienConLai = tienTichLuy - tienCanThanhToan;
                int diemConLai = (int) (tienConLai / giaTriDiem);
                lblSoTienCanTra2.setText("0");
              
                lblDiemTichLuy.setText(String.valueOf(diemConLai));
                lblTienTichLuy.setText(currencyFormat.format(diemConLai * giaTriDiem));
            } else {
                tienCanThanhToan = tienCanThanhToan - tienTichLuy;

                lblSoTienCanTra2.setText(currencyFormat.format(tienCanThanhToan));
                lblSoTienCanTra.setText(String.valueOf(new BigDecimal(tienCanThanhToan)));
                lblDiemTichLuy.setText("0");
                lblTienTichLuy.setText("0");
            }
        } else {
            lblSoTienCanTra.setText(lblTienCanThanhToan2.getText());
            lblSoTienCanTra2.setText(lblTienCanThanhToan.getText());
            lblDiemTichLuy.setText(String.valueOf(diemTichLuy));
            lblTienTichLuy.setText(currencyFormat.format(tienTichLuy));
        }

    }
    private void cboDungDiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDungDiemActionPerformed
        setTraTien();

    }//GEN-LAST:event_cboDungDiemActionPerformed
    private void clearFormTraTien() {
        lblDiemTichLuy.setText("0");
        lblTienTichLuy.setText("0");
        txtTienKhachDua.setText("0");
        lblSoTienCanTra.setText("0");
        lblTienThua.setText("0");
    }
    private void btnTTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTTMouseEntered
        btnTT.setBackground(new Color(201, 214, 214));
        btnTT.setForeground(new Color(155, 49, 56));
        Font originalFont = btnTT.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.BOLD, originalFont.getSize() + 2);
        btnTT.setFont(boldFont);
        setIcon2(btnTT, "icon/pay-by-credit-card-svgrepo-com.svg", 60, 60);

    }//GEN-LAST:event_btnTTMouseEntered

    private void btnTTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTTMouseExited
        btnTT.setForeground(Color.WHITE);
        btnTT.setBackground(new Color(155, 49, 56));
        Font originalFont = btnTT.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.BOLD, originalFont.getSize() - 2);
        btnTT.setFont(boldFont);
        setIcon2(btnTT, "icon/pay-by-credit-card-svgrepo-com.svg", 50, 50);

    }//GEN-LAST:event_btnTTMouseExited
    private void setEventClickForItemSanPham() {
        NguyenLieuViewModel_Long nl = new NguyenLieuViewModel_Long();
        for (ItemSanPham item : listItemSanPham) {
            item.getLblAvatarSp().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (banHangService.checkDinhLuongPhaChex3(item.getId())) {
                        addProductForSaleToTable(item);
                        upTotalItemSanPham(item);
                        totalMoney();
                        totalMoneyAfterSale();
                    } else {
                        JOptionPane.showMessageDialog(cpListSp, "Số lượng nguyên liệu có thể không đủ");
                    }
                }
            });
        }
    }

    private void upTotalItemSanPham(ItemSanPham item) {
        int soLuong = 0;
        double thanhTien = 0;
        if (tblSpChon.getRowCount() > 0) {
            for (int i = 0; i < tblSpChon.getRowCount(); i++) {
                if (item.getId().equalsIgnoreCase(tblSpChon.getValueAt(i, 0).toString())) {
                    soLuong = Integer.parseInt(tblSpChon.getValueAt(i, 4).toString());
                    soLuong++;
                    tblSpChon.setValueAt(soLuong, i, 4);
                    thanhTien = Double.parseDouble(tblSpChon.getValueAt(i, 4).toString())
                            * Double.valueOf(tblSpChon.getValueAt(i, 3).toString());
                    tblSpChon.setValueAt(new BigDecimal(thanhTien), i, 5);
                    break;
                }
            }
        }
    }

    private void totalMoney() {
        double tongTien = 0;
        double tongTien2 = 0;
        for (int i = 0; i < tblSpChon.getRowCount(); i++) {
            tongTien += Double.valueOf(tblSpChon.getValueAt(i, 5).toString());
            tongTien2 += Double.valueOf(tblSpChon.getValueAt(i, 5).toString());
        }
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        lblTongTien2.setText(String.valueOf(new BigDecimal(tongTien2)));
        lblTongTien.setText(currencyFormat.format(tongTien));

    }

    private void totalMoneyAfterSale() {
        double tongTienChietKhau = 0;
        double tongTienPhaiTra = 0;
        double tongTienPhaiTra2 = 0;

        for (int i = 0; i < tblSpChon.getRowCount(); i++) {
            KhuyenMaiDangHoatDong kmView = banHangService.getKhuyenMaibySanPham(tblSpChon.getValueAt(i, 0).toString());

            if (kmView != null && kmView.getGiaTriChietKhau() != null) {
                double giaTriChietKhau = kmView.getGiaTriChietKhau();
                double donGia = Double.parseDouble(tblSpChon.getValueAt(i, 3).toString());
                int soLuong = Integer.parseInt(tblSpChon.getValueAt(i, 4).toString());

                double soTienChietKhau = (donGia / 100) * giaTriChietKhau;
                double soTienPhaiTra = donGia - soTienChietKhau;
                double soTienPhaiTra2 = donGia - soTienChietKhau;

                tongTienChietKhau += soTienChietKhau * soLuong;
                tongTienPhaiTra += soTienPhaiTra * soLuong;
                tongTienPhaiTra2 += soTienPhaiTra2 * soLuong;
            } else {
                double donGia = Double.parseDouble(tblSpChon.getValueAt(i, 3).toString());
                int soLuong = Integer.parseInt(tblSpChon.getValueAt(i, 4).toString());

                tongTienPhaiTra += donGia * soLuong;
                tongTienPhaiTra2 += donGia * soLuong;
            }
        }

        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);

        lblTienDuocGiamGia.setText(currencyFormat.format(tongTienChietKhau));
        lblTienCanThanhToan.setText(currencyFormat.format(tongTienPhaiTra));

        lblTienCanThanhToan2.setText(String.valueOf(new BigDecimal(tongTienPhaiTra2)));
    }

    private void addProductForSaleToTable(ItemSanPham item) {
        int soLuong = 0;
        boolean check = false;
        BigDecimal thanhTien = new BigDecimal(item.getLblGiaTienSp2().getText());
        for (int i = 0; i < tblSpChon.getRowCount(); i++) {
            if (item.getId().equalsIgnoreCase(tblSpChon.getValueAt(i, 0).toString())) {
                check = true;
                break;
            }
        }
        if (check != true) {
            modelTableChonSp.addRow(new Object[]{item.getId(), item.getMa(), item.getLblTenSp().getText(),
                item.getLblGiaTienSp2().getText(), 0, thanhTien});
        }
    }

    private void showProductForSale(List<ProductForSale> listProductForSale) throws IOException {
        pnlListSp.removeAll();
        pnlListSp.revalidate();
        pnlListSp.repaint();
        listItemSanPham.clear();

        if (listProductForSale != null) {
            for (ProductForSale product : listProductForSale) {
                ItemSanPham item = new ItemSanPham();
                item.setId(product.getIdSp());
                item.setMa(product.getMaSp());

                if (product.getTrangThai() != null) {
                    item.setTrangThai(product.getTrangThai());
                }

                if (product.getTenSp() != null) {
                    item.getLblTenSp().setText(product.getTenSp());
                }

                if (product.getTenKhuyenMai() != null) {
                    item.getLblTenKhuyenMai().setText(product.getTenKhuyenMai());
                    item.getLblSaleSp().setEnabled(true);
                } else {
                    item.getLblTenKhuyenMai().setText("   ");
                    item.getLblSaleSp().setEnabled(false);
                }

                if (product.getGiaBan() != null) {
                    Locale locale = new Locale("vi", "VN");
                    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
                    String giaBanFormatted = currencyFormat.format(product.getGiaBan());

                    item.getLblGiaSp().setText(giaBanFormatted);
                    item.getLblGiaTienSp2().setText(String.valueOf(product.getGiaBan()));
                }

                if (product.getAvatar() != null) {
                    Image image = new ImageIcon(product.getAvatar()).getImage()
                            .getScaledInstance(200, 180, Image.SCALE_SMOOTH);
                    item.getLblAvatarSp().setIcon(new ImageIcon(image));
                }

                pnlListSp.add(item);
                pnlListSp.revalidate();
                pnlListSp.repaint();
                listItemSanPham.add(item);
            }
        }
    }

    private void showKhuyenMai(String idChiNhanh) {
        List<KhuyenMaiDangHoatDong> listKmActive = banHangService.getAllKhuyenMaiByChiNhanh(idChiNhanh);
        String allKhuyenMai = "";
        for (KhuyenMaiDangHoatDong km : listKmActive) {
            allKhuyenMai += "- " + km.getTenKhuyenMai() + " : " + km.getMoTa() + "\n";
        }
        txtKhuyenMai.setText(allKhuyenMai);
    }

    private void txtTimSpMouseClicked(java.awt.event.MouseEvent evt) {
        txtTimSp.setForeground(Color.BLACK);
        txtTimSp.setText("");
    }

    private void txtTimKhachMouseClicked(java.awt.event.MouseEvent evt) {
        txtTimKhach.setForeground(Color.BLACK);
        txtTimKhach.setText("");
        hideInfoKhach();

    }

    private void setHoverOfSearch() {
        Color color = new Color(225, 225, 225);
        txtTimSp.setText("Nhập tên sản phẩm cần tìm");
        txtTimKhach.setText("Nhập số điện thoại cần tìm");
        txtTimSp.setForeground(color);
        txtTimKhach.setForeground(color);
    }

    private void formMouseClicked(java.awt.event.MouseEvent evt) {
        setHoverOfSearch();
    }

    private void pnlLeftMouseClicked(java.awt.event.MouseEvent evt) {
        setHoverOfSearch();
    }

    private void pnlHoaDonMouseClicked(java.awt.event.MouseEvent evt) {
        setHoverOfSearch();
    }

    private void resetPanelBanAndShow() {
        pnlBan.removeAll();
        pnlBan.revalidate();
        pnlBan.repaint();
        if (cboKhuVuc.getItemCount() > 0) {
            Area area = (Area) modelComboArea.getSelectedItem();
            showTableOfArea(area);
        }

    }

    private void addEventMouseClickToItemBan(ItemBan itemBan) {
        itemBan.getLblAvatar().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                itemBan.changeStateConfirm();
                if (itemBan.isConfirm() == true && itemBan.getTrangThai() == 0) {
                    lblSoBan.setText(itemBan.getLblSoBan().getText());
                } else if (itemBan.getTrangThai() == 1) {
                    int select = JOptionPane.showConfirmDialog(pnlBan, "Bạn muốn reset trạng thái bàn", "Reset trạng thái", JOptionPane.YES_NO_OPTION);
                    if (select == JOptionPane.YES_OPTION) {
                        banHangService.updateTrangThaiBanBySoBan(Integer.parseInt(itemBan.getLblSoBan().getText()));
                        resetPanelBanAndShow();
                    }
                } else {
                    lblSoBan.setText("-");
                }

            }
        });
        itemBan.getLblSoBan().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                itemBan.changeStateConfirm();
                if (itemBan.isConfirm() == true && itemBan.getTrangThai() == 0) {
                    lblSoBan.setText(itemBan.getLblSoBan().getText());
                } else if (itemBan.getTrangThai() == 1) {
                    int select = JOptionPane.showConfirmDialog(pnlBan, "Bạn muốn reset trạng thái bàn", "Reset trạng thái", JOptionPane.YES_NO_OPTION);
                    if (select == JOptionPane.YES_OPTION) {
                        banHangService.updateTrangThaiBanBySoBan(Integer.parseInt(itemBan.getLblSoBan().getText()));
                        resetPanelBanAndShow();
                    }
                } else {
                    lblSoBan.setText("-");
                }
            }
        });
    }

    private void showTableOfArea(Area area) {
        List<Table> listTable = banHangService.getAllBanByKhuVuc(area);

        Collections.sort(listTable, Comparator.comparing(Table::getSoBan));

        if (!listTable.isEmpty()) {
            for (Table table : listTable) {
                ItemBan itemBan = new ItemBan();
                itemBan.getLblSoBan().setText(table.getSoBan().toString());
                itemBan.setTrangThai(table.getTrangThaiBan());
                if (itemBan.getTrangThai() != 0) {
                    itemBan.setBackground(Color.red);
                    itemBan.getLblAvartar().setBackground(Color.red);
                }
                listItemBan.add(itemBan);
                pnlBan.add(itemBan);
                pnlBan.revalidate();
                pnlBan.repaint();
                addEventMouseClickToItemBan(itemBan);
            }
        } else {
            JOptionPane.showMessageDialog(pnlBan, "Khu vực này không có bàn");
        }
    }

    private void hideInfoKhach() {
        lblSoBan.setText("");
        _khach = null;
    }

    private void btnTimKhachActionPerformed(java.awt.event.ActionEvent evt) {
        _khach = banHangService.getKhachHangBySdt(txtTimKhach.getText());
        if (_khach != null) {
            lblSoBan.setText("Mã khách hàng : " + _khach.getMa() + "\n" + "Tên khách hàng : " + _khach.getHoTen() + "\n"
                    + "Số điện thoại : " + _khach.getSdt());

        } else {
            JOptionPane.showMessageDialog(this, "Thông tin không tồn tại. Vui lòng kiểm tra lại !");
            hideInfoKhach();
        }
    }

    private void txtTimKhachKeyReleased(java.awt.event.KeyEvent evt) {
        if (txtTimKhach.getText().isBlank()) {
            hideInfoKhach();
        }
    }

    private void lblCloseThanhToanMouseClicked(java.awt.event.MouseEvent evt) {
        _ConfirmCloseThanhToan = true;
        if (_ConfirmCloseThanhToan == true) {
            DlogTraTien.setVisible(false);
        }
    }

    private void btnOpenFormAddGuestActionPerformed(java.awt.event.ActionEvent evt) {
        DlogThemKhach.setVisible(true);
        DlogThemKhach.setSize(900, 360);
        DlogThemKhach.setLocationRelativeTo(null);
    }

    private boolean checkEmptyFormAddGuest() {
        boolean ok = true;
        if (txtMaKhach.getText().isBlank()) {
            lblCanhBaoMaKhach.setText("Thông tin bắt buộc");
            ok = false;
        } else {
            lblCanhBaoMaKhach.setText("");
        }
        String sdt = txtSdt.getText();
        if (sdt.isBlank()) {
            lblCanhBaoSdt.setText("Thông tin bắt buộc");
            ok = false;
        } else if (!sdt.matches("0\\d{9}")) {
            lblCanhBaoSdt.setText("Số điện thoại không hợp lệ");
            ok = false;
        } else {
            lblCanhBaoSdt.setText("");
        }
        if (txtTenKhach.getText().isBlank()) {
            lblCanhBaoTenKhach.setText("Thông tin bắt buộc");
            ok = false;
        } else {
            lblCanhBaoTenKhach.setText("");
        }
        return ok;
    }

    private boolean checkFormatSdt(String sdt) {
        if (!banHangService.checkFormatSdt(sdt)) {
            lblCanhBaoSdt.setText("Sdt không hợp lệ(0xxxxxxxxx)");
            return false;
        } else {
            lblCanhBaoSdt.setText("");
            return true;
        }

    }

    private boolean checkMaKhach(String maKhach) {
        if (banHangService.checkMaKhach(maKhach)) {
            lblCanhBaoMaKhach.setText("Mã khách đã tồn tại");
            return false;
        } else {
            lblCanhBaoMaKhach.setText("");
            return true;
        }
    }

    private boolean checkExistedSdt(String sdt) {
        if (banHangService.getKhachHangBySdt(sdt) != null) {
            lblCanhBaoSdt.setText("Sdt này đã được sử dụng");
            return false;
        } else {
            lblCanhBaoSdt.setText("");
            return true;
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog DlogThemKhach;
    private javax.swing.JDialog DlogTraTien;
    private javax.swing.JButton btnAddGuest;
    private javax.swing.JLabel btnOpenFormAddGuest;
    private javax.swing.JButton btnTT;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JComboBox<String> cboChiNhanh;
    private javax.swing.JCheckBox cboDungDiem;
    private javax.swing.JComboBox<String> cboKhuVuc;
    private javax.swing.JComboBox<String> cboQuocGia;
    private javax.swing.JScrollPane cpBan;
    private javax.swing.JScrollPane cpListSp;
    private fomVe.CurvesPanel curvesPanel1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
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
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLabel lblCanhBaoMaKhach;
    private javax.swing.JLabel lblCanhBaoSdt;
    private javax.swing.JLabel lblCanhBaoTenKhach;
    private javax.swing.JLabel lblCanhBaoTienKhachDua;
    private javax.swing.JLabel lblChiNhanh;
    private javax.swing.JLabel lblCloseThemKhach;
    private javax.swing.JLabel lblDiemTichLuy;
    private javax.swing.JLabel lblSearchKH;
    private javax.swing.JLabel lblSlide;
    private javax.swing.JLabel lblSoBan;
    private javax.swing.JLabel lblSoTienCanTra;
    private javax.swing.JLabel lblSoTienCanTra2;
    private javax.swing.JLabel lblStateMoCa;
    private javax.swing.JLabel lblTienCanThanhToan;
    private javax.swing.JLabel lblTienCanThanhToan2;
    private javax.swing.JLabel lblTienDuocGiamGia;
    private javax.swing.JLabel lblTienThua;
    private javax.swing.JLabel lblTienTichLuy;
    private javax.swing.JLabel lblTienTichLuy2;
    private javax.swing.JLabel lblTimKiem;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JLabel lblTongTien2;
    private fomVe.Panel panel1;
    private fomVe.Panel panel2;
    private fomVe.Panel panel3;
    private javax.swing.JPanel pnlBan;
    private javax.swing.JPanel pnlListSp;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblSpChon;
    private fomVe.TextFieldLogin textFieldLogin1;
    private javax.swing.JTextArea txtHienThiKhach;
    private javax.swing.JTextPane txtKhuyenMai;
    private fomVe.JxText txtMaKhach;
    private fomVe.JxText txtSdt;
    private fomVe.JxText txtTenKhach;
    private fomVe.JxText txtThanhPho;
    private javax.swing.JTextField txtTienKhachDua;
    private fomVe.JtextSearch1 txtTimKhach;
    private fomVe.JtextSearch txtTimSp;
    // End of variables declaration//GEN-END:variables
}
