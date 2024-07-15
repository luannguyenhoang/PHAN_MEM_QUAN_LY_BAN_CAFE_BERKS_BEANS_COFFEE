/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableModel;

import fomVe.SVGIconHelper;
import domainmodel.TaiKhoanAdmin;
import domainmodel.TaiKhoanNguoiDung;
import fomVe.CustomHeaderRenderer;
import fomVe.SVGIconHelperButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import service.IBanHangService;
import service.IChiTietSpService;
import service.ISanPhamService;
import service.implement.BanHangService;
import service.implement.ChiTietSpService;
import service.implement.SanPhamService;
import viewmodel.ChiNhanhViewModel_Hoang;
import viewmodel.ChiTietSPViewModel;
import viewmodel.NguyenLieuDangSuDung;
import viewmodel.SanPhamViewModel;

/**
 *
 * @author hoang
 */
public class QLSanPham extends javax.swing.JPanel implements Runnable {

    /**
     * Creates new form NewJPanel
     */
    private DefaultTableModel modelTableSanPham;
    private DefaultTableModel modelTableDinhLuong;
    private ISanPhamService sanPhamService;
    private IChiTietSpService chiTietSPService;
    private DefaultComboBoxModel<NguyenLieuDangSuDung> modelComBoNguyenLieu;
    private DefaultComboBoxModel<ChiNhanhViewModel_Hoang> modelComBoChiNhanh;
    private IBanHangService banHangService;
    private byte[] _arrAvatar;
    private ImageIcon defaultAvatar;
    TaiKhoanAdmin _admin;
    TaiKhoanNguoiDung _nguoiDung;
    private static FlatSVGIcon icon = null;

    public static void setIcon2(JButton label, String path, int width, int height) {
        try {
            icon = new FlatSVGIcon(path, width, height);
            label.setIcon(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public QLSanPham(TaiKhoanAdmin admin, TaiKhoanNguoiDung nguoiDung) {
        initComponents();
        _admin = admin;
        _nguoiDung = nguoiDung;
        banHangService = new BanHangService();
        sanPhamService = new SanPhamService();
        chiTietSPService = new ChiTietSpService();
        modelTableSanPham = new DefaultTableModel();
        modelTableDinhLuong = new DefaultTableModel();
        modelTableDinhLuong = (DefaultTableModel) tblDinhLuong.getModel();
        modelTableSanPham = (DefaultTableModel) tblSanPham.getModel();
        Thread loadData = new Thread(this);
        AutoCompleteDecorator.decorate(cboChiNhanh);
        AutoCompleteDecorator.decorate(cboNguyenLieu);
        setIcon2(btnUpload, "icon/upload-svgrepo-com.svg", 25, 25);
        SVGIconHelperButton.setIcon(btnCapNhat, "icon/update.svg", 25, 25);
        SVGIconHelperButton.setIcon(btnThemSp, "icon/plus.svg", 25, 25);
        SVGIconHelperButton.setIcon(btnXoa, "icon/delete.svg", 25, 25);
        SVGIconHelperButton.setIcon(btnClear, "icon/reset.svg", 27, 27);
        loadData.start();
        cboChiNhanh.setRenderer(new BasicComboBoxRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setFocusable(false);
                return this;
            }
        });
        cboNguyenLieu.setRenderer(new BasicComboBoxRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setFocusable(false);
                return this;
            }
        });
        JTableHeader tableHeader = tblDinhLuong.getTableHeader();
        tableHeader.setBackground(new Color(143, 45, 52));
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setBorder(null);
        TableCellRenderer defaultHeaderRenderer = tblDinhLuong.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer = new CustomHeaderRenderer(defaultHeaderRenderer);
        tblDinhLuong.getTableHeader().setDefaultRenderer(customHeaderRenderer);
        setIcon2(btnUpload, "icon/upload-svgrepo-com.svg", 25, 25);
        JTableHeader tableHeader2 = tblSanPham.getTableHeader();
        tableHeader2.setBackground(new Color(143, 45, 52));
        tableHeader2.setForeground(Color.WHITE);
        tableHeader2.setBorder(null);
        TableCellRenderer defaultHeaderRenderer2 = tblSanPham.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer2 = new CustomHeaderRenderer(defaultHeaderRenderer2);
        tblSanPham.getTableHeader().setDefaultRenderer(customHeaderRenderer2);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDinhLuong = new javax.swing.JTable();
        rdoDangBan = new javax.swing.JRadioButton();
        rdoDaXoa = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblAnh = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnThemSp = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        cboNguyenLieu = new javax.swing.JComboBox<>();
        cboChiNhanh = new javax.swing.JComboBox<>();
        btnUpload = new javax.swing.JButton();
        panel1 = new fomVe.Panel();
        txtTenSp = new domainmodel.txtText();
        panel2 = new fomVe.Panel();
        txtGiaBan = new domainmodel.txtText();
        panel3 = new fomVe.Panel();
        txtMaSp = new domainmodel.txtText();
        btnClear = new javax.swing.JButton();

        setBackground(new java.awt.Color(51, 51, 51));
        setPreferredSize(new java.awt.Dimension(1255, 755));

        jScrollPane1.setBorder(null);

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Mã sản phẩm", "Tên sản phẩm", "Giá sản phẩm"
            }
        ));
        tblSanPham.setSelectionForeground(new java.awt.Color(102, 204, 255));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);
        if (tblSanPham.getColumnModel().getColumnCount() > 0) {
            tblSanPham.getColumnModel().getColumn(0).setMinWidth(0);
            tblSanPham.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblSanPham.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jScrollPane2.setBorder(null);

        tblDinhLuong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Mã nguyên liệu", "Tên nguyên liệu", "Số lượng", "Gỡ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDinhLuong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDinhLuongMouseClicked(evt);
            }
        });
        tblDinhLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblDinhLuongKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblDinhLuong);
        if (tblDinhLuong.getColumnModel().getColumnCount() > 0) {
            tblDinhLuong.getColumnModel().getColumn(0).setMinWidth(0);
            tblDinhLuong.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblDinhLuong.getColumnModel().getColumn(0).setMaxWidth(0);
            tblDinhLuong.getColumnModel().getColumn(2).setResizable(false);
            tblDinhLuong.getColumnModel().getColumn(3).setResizable(false);
            tblDinhLuong.getColumnModel().getColumn(3).setPreferredWidth(10);
            tblDinhLuong.getColumnModel().getColumn(4).setResizable(false);
            tblDinhLuong.getColumnModel().getColumn(4).setPreferredWidth(5);
        }

        rdoDangBan.setBackground(new java.awt.Color(51, 51, 51));
        buttonGroup1.add(rdoDangBan);
        rdoDangBan.setForeground(new java.awt.Color(255, 255, 255));
        rdoDangBan.setText("Đang bán");
        rdoDangBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDangBanActionPerformed(evt);
            }
        });

        rdoDaXoa.setBackground(new java.awt.Color(51, 51, 51));
        buttonGroup1.add(rdoDaXoa);
        rdoDaXoa.setForeground(new java.awt.Color(255, 255, 255));
        rdoDaXoa.setText("Đã xóa");
        rdoDaXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDaXoaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Chọn nguyên liệu :");

        jLabel2.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Chọn chi nhánh :");

        jLabel3.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Danh sách nguyên liệu :");

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("- Danh sách sản phẩm -");

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        lblAnh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        lblAnh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAnh.setMaximumSize(new java.awt.Dimension(200, 300));
        lblAnh.setMinimumSize(new java.awt.Dimension(200, 300));
        lblAnh.setPreferredSize(new java.awt.Dimension(200, 330));
        lblAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAnhMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAnhMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 185, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        jPanel2.setLayout(new java.awt.GridLayout(1, 3, 15, 0));

        btnThemSp.setBackground(new java.awt.Color(155, 49, 56));
        btnThemSp.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        btnThemSp.setForeground(new java.awt.Color(255, 255, 255));
        btnThemSp.setText("Thêm");
        btnThemSp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSpActionPerformed(evt);
            }
        });
        jPanel2.add(btnThemSp);

        btnXoa.setBackground(new java.awt.Color(155, 49, 56));
        btnXoa.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel2.add(btnXoa);

        btnCapNhat.setBackground(new java.awt.Color(155, 49, 56));
        btnCapNhat.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        btnCapNhat.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });
        jPanel2.add(btnCapNhat);

        cboNguyenLieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboNguyenLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNguyenLieuActionPerformed(evt);
            }
        });

        cboChiNhanh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboChiNhanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChiNhanhActionPerformed(evt);
            }
        });

        btnUpload.setBackground(new java.awt.Color(155, 49, 56));
        btnUpload.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        btnUpload.setForeground(new java.awt.Color(255, 255, 255));
        btnUpload.setText("Upload");
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });

        panel1.setBackground(new java.awt.Color(255, 255, 255));

        txtTenSp.setBackground(new java.awt.Color(255, 255, 255));
        txtTenSp.setForeground(new java.awt.Color(0, 0, 0));
        txtTenSp.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        txtTenSp.setPrompt("Tên sản phẩm");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTenSp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTenSp, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                .addContainerGap())
        );

        panel2.setBackground(new java.awt.Color(255, 255, 255));

        txtGiaBan.setBackground(new java.awt.Color(255, 255, 255));
        txtGiaBan.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        txtGiaBan.setPrompt("Giá bán");

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtGiaBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtGiaBan, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        panel3.setBackground(new java.awt.Color(255, 255, 255));

        txtMaSp.setBackground(new java.awt.Color(255, 255, 255));
        txtMaSp.setForeground(new java.awt.Color(255, 0, 0));
        txtMaSp.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        txtMaSp.setOpaque(true);
        txtMaSp.setPrompt("Mã sản phẩm");

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMaSp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMaSp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnClear.setBackground(new java.awt.Color(46, 144, 232));
        btnClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClearMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(172, 172, 172)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(98, 98, 98))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(panel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(panel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(panel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cboChiNhanh, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(70, 70, 70)
                                        .addComponent(cboNguyenLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(311, 311, 311)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnUpload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rdoDangBan)
                        .addGap(42, 42, 42)
                        .addComponent(rdoDaXoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdoDangBan)
                        .addComponent(rdoDaXoa))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboChiNhanh)
                                    .addComponent(cboNguyenLieu))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rdoDangBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDangBanActionPerformed
        clearForm();
        rdoDangBan.setForeground(new Color(0, 255, 255));
        rdoDaXoa.setForeground(Color.white);
        btnXoa.setEnabled(true);
        btnCapNhat.setEnabled(true);
        btnThemSp.setEnabled(true);
        ShowSanPhamToTable(sanPhamService.getAllSanPhamDangBanByChiNhanh(((ChiNhanhViewModel_Hoang) modelComBoChiNhanh.getSelectedItem()).getId()));// TODO add your handling code here:
    }//GEN-LAST:event_rdoDangBanActionPerformed

    private void lblAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblAnhMouseClicked

    private void lblAnhMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMouseEntered

    }//GEN-LAST:event_lblAnhMouseEntered

    private void lblAnhMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMouseExited

    }//GEN-LAST:event_lblAnhMouseExited

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        int row = tblSanPham.getSelectedRow();
        showChiTietSanPham(tblSanPham.getValueAt(row, 0).toString());        // TODO add your handling code here:
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void rdoDaXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDaXoaActionPerformed
        clearForm();
        rdoDaXoa.setForeground(new Color(0, 255, 255));
        rdoDangBan.setForeground(Color.white);
        btnXoa.setEnabled(false);
        btnCapNhat.setEnabled(false);
        btnThemSp.setEnabled(false);
        ShowSanPhamToTable(sanPhamService.getAllSanPhamDaXoaByChiNhanh(((ChiNhanhViewModel_Hoang) modelComBoChiNhanh.getSelectedItem()).getId()));
    }//GEN-LAST:event_rdoDaXoaActionPerformed

    private void tblDinhLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDinhLuongKeyReleased
        int row = tblDinhLuong.getSelectedRow();
        String soLuong = tblDinhLuong.getValueAt(row, 3).toString();
        if (!checkNumber(soLuong)) {
            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ !");
            tblDinhLuong.setValueAt(1, row, 3);
        }
    }//GEN-LAST:event_tblDinhLuongKeyReleased

    private void tblDinhLuongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDinhLuongMouseClicked
        int row = tblDinhLuong.getSelectedRow();
        int column = tblDinhLuong.getSelectedColumn();
        if (column == 4) {
            if (tblDinhLuong.getSelectedColumn() == 4) {
                if (tblDinhLuong.getValueAt(row, 4).equals(true)) {
                    modelTableDinhLuong.removeRow(row);
                }
            }
        }
    }//GEN-LAST:event_tblDinhLuongMouseClicked

    private void cboNguyenLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNguyenLieuActionPerformed
        int count = 0;
        if (cboNguyenLieu.getItemCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng bổ sung nguyên liệu");
        } else {
            NguyenLieuDangSuDung nguyenLieu = (NguyenLieuDangSuDung) modelComBoNguyenLieu.getSelectedItem();
            for (int i = 0; i < tblDinhLuong.getRowCount(); i++) {
                if (nguyenLieu.getId().equalsIgnoreCase(tblDinhLuong.getValueAt(i, 0).toString())) {
                    count++;
                    break;
                }
            }
            if (count == 0) {
                modelTableDinhLuong.addRow(new Object[]{nguyenLieu.getId(), nguyenLieu.getMa(), nguyenLieu.getTen(), 1});
            }
        }
    }//GEN-LAST:event_cboNguyenLieuActionPerformed

    private void cboChiNhanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChiNhanhActionPerformed
        modelTableDinhLuong.setRowCount(0);
        lblAnh.setIcon(defaultAvatar);
        txtMaSp.setText("");
        txtTenSp.setText("");
        txtGiaBan.setText("");
        modelComBoNguyenLieu = (DefaultComboBoxModel) new DefaultComboBoxModel<>(
                sanPhamService.getAllNguyenLieuByChiNhanh(((ChiNhanhViewModel_Hoang) modelComBoChiNhanh.getSelectedItem()).getId()).toArray());
        cboNguyenLieu.setModel((DefaultComboBoxModel) modelComBoNguyenLieu);
        rdoDangBan.setSelected(true);
        ShowSanPhamToTable(sanPhamService.getAllSanPhamDangBanByChiNhanh(((ChiNhanhViewModel_Hoang) modelComBoChiNhanh.getSelectedItem()).getId()));

    }//GEN-LAST:event_cboChiNhanhActionPerformed

    private void btnThemSpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSpActionPerformed
        if (checkEmpty() && checkNumber(txtGiaBan.getText()) && !checkMaSp(txtMaSp.getText())) {
            sanPhamService.insertSanPham(txtMaSp.getText(), txtTenSp.getText(), Float.parseFloat(txtGiaBan.getText()), _arrAvatar);
            for (int i = 0; i < tblDinhLuong.getRowCount(); i++) {
                chiTietSPService.insertChiTietSanPham(Float.parseFloat(tblDinhLuong.getValueAt(i, 3).toString()),
                        sanPhamService.getSanPhamByMa(txtMaSp.getText()).getIdSp(), tblDinhLuong.getValueAt(i, 0).toString());
            }
            rdoDangBanActionPerformed(evt);
            clearForm();
            JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemSpActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int row = tblSanPham.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa");
        } else {
            sanPhamService.deleteSanPham(tblSanPham.getValueAt(row, 0).toString());
            rdoDangBanActionPerformed(evt);
            clearForm();
            JOptionPane.showMessageDialog(this, "Xóa phẩm thành công");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        int row = tblSanPham.getSelectedRow();
        if (tblDinhLuong.getRowCount() > 0 && row != -1) {
            if (tblSanPham.getValueAt(row, 2) != null && tblSanPham.getValueAt(row, 3) != null) {
                sanPhamService.UpdateSanPham(
                        tblSanPham.getValueAt(row, 0).toString(),
                        tblSanPham.getValueAt(row, 1).toString(),
                        tblSanPham.getValueAt(row, 2).toString(),
                        Float.parseFloat(tblSanPham.getValueAt(row, 3).toString()), _arrAvatar);
                chiTietSPService.deleteChiTietSpByIdSp(tblSanPham.getValueAt(row, 0).toString());
                for (int i = 0; i < tblDinhLuong.getRowCount(); i++) {
                    chiTietSPService.insertChiTietSanPham(Float.parseFloat(tblDinhLuong.getValueAt(i, 3).toString()),
                            tblSanPham.getValueAt(row, 0).toString(), tblDinhLuong.getValueAt(i, 0).toString());
                }
                rdoDangBanActionPerformed(evt);
                clearForm();
                JOptionPane.showMessageDialog(this, "Cập nhật phẩm thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Dữ liệu trống");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm và thêm định lượng");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed
        FileDialog fileDialog = new FileDialog((Frame) null, "Chọn tệp", FileDialog.LOAD);
        fileDialog.setVisible(true);

        String directory = fileDialog.getDirectory();
        String filename = fileDialog.getFile();

        if (filename != null) {
            String filePath = directory + filename;
            File file = new File(filePath);

            Image image = new ImageIcon(file.getAbsolutePath()).getImage().getScaledInstance(230, 230, Image.SCALE_SMOOTH);
            lblAnh.setIcon(new ImageIcon(image));

            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", baos);
                _arrAvatar = baos.toByteArray();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnUploadActionPerformed

    private void btnClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearMouseClicked
        txtMaSp.setText("");
        txtTenSp.setText("");
        txtGiaBan.setText("");
        lblAnh.setIcon(null);
        DefaultTableModel model = (DefaultTableModel) tblDinhLuong.getModel();
        model.setRowCount(0);
    }//GEN-LAST:event_btnClearMouseClicked
    @Override
    public void run() {
        if (_admin != null) {
            cboChiNhanh.setVisible(true);
            modelComBoChiNhanh = (DefaultComboBoxModel) new DefaultComboBoxModel<>(banHangService.getAllChiNhanh().toArray());
            cboChiNhanh.setModel((DefaultComboBoxModel) modelComBoChiNhanh);
            modelComBoNguyenLieu = (DefaultComboBoxModel) new DefaultComboBoxModel<>(
                    sanPhamService.getAllNguyenLieuByChiNhanh(((ChiNhanhViewModel_Hoang) modelComBoChiNhanh.getSelectedItem()).getId()).toArray());
            cboNguyenLieu.setModel((DefaultComboBoxModel) modelComBoNguyenLieu);
            ShowSanPhamToTable(sanPhamService.getAllSanPhamDangBanByChiNhanh(((ChiNhanhViewModel_Hoang) modelComBoChiNhanh.getSelectedItem()).getId()));
        } else {
            cboChiNhanh.setVisible(false);
            modelComBoNguyenLieu = (DefaultComboBoxModel) new DefaultComboBoxModel<>(
                    sanPhamService.getAllNguyenLieuByChiNhanh(banHangService.getChiNhanhbyTaiKhoan(_nguoiDung.getId()).getId()).toArray());
            cboNguyenLieu.setModel((DefaultComboBoxModel) modelComBoNguyenLieu);
            ShowSanPhamToTable(sanPhamService.getAllSanPhamDangBanByChiNhanh(banHangService.getChiNhanhbyTaiKhoan(_nguoiDung.getId()).getId()));

        }
        Image image = new ImageIcon(getClass().getClassLoader().getResource("icon/add-image.png")).getImage();
        defaultAvatar = new ImageIcon(image.getScaledInstance(187, 186, Image.SCALE_SMOOTH));
        lblAnh.setIcon(defaultAvatar);
    }

    private void ShowSanPhamToTable(List<SanPhamViewModel> list) {
        modelTableSanPham.setRowCount(0);
        for (SanPhamViewModel sp : list) {
            modelTableSanPham.addRow(new Object[]{sp.getIdSp(), sp.getMaSp(),
                sp.getTenSp(), sp.getGiaBan()});
        }
    }

    private void lbUploadMouseEntered(java.awt.event.MouseEvent evt) {
        btnUpload.setForeground(Color.GREEN);
    }

    private void lbUploadMouseExited(java.awt.event.MouseEvent evt) {
        btnUpload.setForeground(Color.BLACK);
    }

    private void showChiTietSanPham(String idSp) {
        modelTableDinhLuong.setRowCount(0);
        SanPhamViewModel spView = sanPhamService.getSanPhamById(idSp);
        Set<ChiTietSPViewModel> chiTietView = chiTietSPService.getChiTietSpByIdSanPham(idSp);
        for (ChiTietSPViewModel ctView : chiTietView) {
            modelTableDinhLuong.addRow(new Object[]{ctView.getIdNguyenLieu(), ctView.getMaNguyenLieu(),
                ctView.getTenNguyenLieu(), ctView.getDinhLuong()});
        }
        _arrAvatar = spView.getAvatar();
        if (spView.getAvatar() != null) {
            Image image = new ImageIcon(_arrAvatar).getImage().getScaledInstance(200, 186, Image.SCALE_SMOOTH);
            lblAnh.setIcon(new ImageIcon(image));
        } else {
            lblAnh.setIcon(defaultAvatar);
        }
        txtMaSp.setText(spView.getMaSp());
        txtTenSp.setText(spView.getTenSp());
        txtGiaBan.setText(spView.getGiaBan().toString());

    }

    public boolean checkNumber(String mark) {
        Pattern regexInt = Pattern.compile("^[0-9]+$");
        Pattern regexDouble = Pattern.compile("^[0-9]+(\\.)[0-9]+$");
        if (!regexInt.matcher(mark).find() && !regexDouble.matcher(mark).find()) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkEmpty() {
        if (txtMaSp.getText().isBlank() || txtTenSp.getText().isBlank() || txtGiaBan.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Dữ liệu trống");
            return false;
        } else if (tblDinhLuong.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm định lượng");
            return false;
        }
        return true;
    }

    private boolean checkMaSp(String maSp) {
        if (sanPhamService.getSanPhamByMa(maSp) != null) {
            JOptionPane.showMessageDialog(this, "Mã sản phẩm đã từng được sử dụng");
            return true;
        } else {
            return false;
        }
    }

    private void clearForm() {
        //   cboNguyenLieu.setSelectedIndex(0);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnThemSp;
    private javax.swing.JButton btnUpload;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboChiNhanh;
    private javax.swing.JComboBox<String> cboNguyenLieu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAnh;
    private fomVe.Panel panel1;
    private fomVe.Panel panel2;
    private fomVe.Panel panel3;
    private javax.swing.JRadioButton rdoDaXoa;
    private javax.swing.JRadioButton rdoDangBan;
    private javax.swing.JTable tblDinhLuong;
    private javax.swing.JTable tblSanPham;
    private domainmodel.txtText txtGiaBan;
    private domainmodel.txtText txtMaSp;
    private domainmodel.txtText txtTenSp;
    // End of variables declaration//GEN-END:variables

}
