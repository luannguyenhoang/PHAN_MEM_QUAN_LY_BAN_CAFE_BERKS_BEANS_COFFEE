/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import fomVe.SVGIconHelper;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import domainmodel.TaiKhoanAdmin;
import domainmodel.TaiKhoanNguoiDung;
import javax.swing.*;
import service.ILogin;
import service.implement.LoginSerVice;
import viewmodel.ChiNhanhViewModel_Hoang;
import domainmodel.NhanVien;
import static view.Login.setIcon2;

public class OverView extends javax.swing.JFrame {

    TaiKhoanAdmin _admin;
    TaiKhoanNguoiDung _nguoiDung;
    private String chucVu;
    ILogin logService = new LoginSerVice();
    private DefaultComboBoxModel<ChiNhanhViewModel_Hoang> comboModel = new DefaultComboBoxModel();
    Color colorEntered;
    BanHang _banHang;
    QLKhuyenMai _khuyenMai;
    QLGiaoDich _qlgiaoDich;
    QLNguoiDung _qlNguoiDung;
    QLNguyenLieu _qlnguyenLieu;
    QLSanPham _qlSanPham;
    QLDoiTac _qlDoiTac;
    QLNhanVien _qlNhanVien;
    NhanVien nv = null;
    private static FlatSVGIcon icon = null;

    public static void setIcon2(JButton label, String path, int width, int height) {
        try {
            icon = new FlatSVGIcon(path, width, height);
            label.setIcon(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public OverView(TaiKhoanAdmin admin, TaiKhoanNguoiDung nguoiDung) {
        setUndecorated(true);
        initComponents();
        _admin = admin;
        _nguoiDung = nguoiDung;
        btnThuNho.setFocusPainted(false);
        btnPhongTo.setFocusPainted(false);
        btnClose.setFocusPainted(false);
        btnPhongTo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnThuNho.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setExtendedState(this.MAXIMIZED_BOTH);
        SVGIconHelper.setIcon(lblTenTaiKhoan, "icon/user-circle-svgrepo-com.svg", 30, 30);
        SVGIconHelper.setIcon(jLabel4, "icon/phone-call (1).svg", 12, 12);
        SVGIconHelper.setIcon(lblBanHang, "icon/ticket-8-svgrepo-com-1.svg", 25, 25);
        SVGIconHelper.setIcon(lblkm, "icon/sale-svgrepo-com.svg", 25, 25);
        SVGIconHelper.setIcon(lblThongKe, "icon/statistical-chart-svgrepo-com.svg", 25, 25);
        SVGIconHelper.setIcon(lblBan, "icon/table-svgrepo-com.svg", 25, 25);
        SVGIconHelper.setIcon(lblQlnv, "icon/employee-settings-svgrepo-com.svg", 25, 25);
        SVGIconHelper.setIcon(lblQlCa, "icon/shift-change-svgrepo-com.svg", 25, 25);
        SVGIconHelper.setIcon(lblQlsp, "icon/product-svgrepo-com.svg", 25, 25);
        SVGIconHelper.setIcon(lblqlDoiTac, "icon/client-financing-01-svgrepo-com.svg", 30, 30);
        SVGIconHelper.setIcon(lblQlGiaoDich, "icon/transaction.svg", 25, 25);
        SVGIconHelper.setIcon(lblQlNguyenLieu, "icon/package.svg", 25, 25);
        SVGIconHelper.setIcon(lblQlNguoiDung, "icon/AA.svg", 25, 25);
        SVGIconHelper.setIcon(lblQlChiNhanh, "icon/shop-svgrepo-com.svg", 25, 25);

        SVGIconHelper.setIcon(lblVaiTro, "icon/location-pin-svgrepo-com.svg", 25, 25);
        SVGIconHelper.setIcon(lblMayCaffee, "icon/coffee-maker-svgrepo-com.svg", 90, 90);
        SVGIconHelper.setIcon(lblCocCaffee, "icon/coffee-cup-svgrepo-com.svg", 50, 50);
        setIcon2(btnClose, "icon/close-md-svgrepo-com.svg", 20, 20);
        setIcon2(btnPhongTo, "icon/resize2-svgrepo-com.svg", 20, 20);

        BanHang bh = new BanHang(_admin, _nguoiDung);
        setMainPanel(bh);
        new Timer(1000, new ActionListener() {
            SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
            SimpleDateFormat format2 = new SimpleDateFormat(" dd/MM/yyyy");

            @Override
            public void actionPerformed(ActionEvent e) {
                lblDongHo.setText(format.format(new Date()));
                lblDate.setText(format2.format(new Date()));

            }
        }).start();
        if (admin != null) {
            lblTenTaiKhoan.setText(admin.getTenTK());
            chucVu = "Ông chủ";
            lblVaiTro.setText("Center");

        } else if (nguoiDung != null) {
            nv = logService.getNhanVienbyTaiKhoan(nguoiDung.getId());
            lblTenTaiKhoan.setText(nguoiDung.getNhanVien().getHoTen());
            chucVu = logService.getChucVubyIdNhanVien(nv.getId()).getTen();
            lblVaiTro.setText(logService.getChiNhanhByNhanVien(nv.getId()).getMa());
            if (!chucVu.equals("Ông chủ") && !chucVu.equals("Quản lý")) {
                lblqlDoiTac.setText("Không khả dụng");
                lblqlDoiTac.setForeground(Color.RED);
                lblQlsp.setText("Không khả dụng");
                lblQlsp.setForeground(Color.RED);
                lblQlCa.setText("Không khả dụng");
                lblQlCa.setForeground(Color.RED);
                lblQlNguoiDung.setText("Không khả dụng");
                lblQlNguoiDung.setForeground(Color.RED);
                lblThongKe.setText("Không khả dụng");
                lblThongKe.setForeground(Color.RED);
                lblQlnv.setText("Không khả dụng");
                lblQlnv.setForeground(Color.RED);
                lblkm.setText("Không khả dụng");
                lblkm.setForeground(Color.RED);
                lblBan.setText("Không khả dụng");
                lblBan.setForeground(Color.RED);
                lblQlChiNhanh.setText("Không khả dụng");
                lblQlChiNhanh.setForeground(Color.RED);
                lblQlNguyenLieu.setText("Không khả dụng");
                lblQlNguyenLieu.setForeground(Color.RED);
            }
        }
        if (nv != null) {
            _khuyenMai = new QLKhuyenMai(chucVu, nv.getChiNhanh());
        } else {
            _khuyenMai = new QLKhuyenMai(chucVu);
        }

    }

    private void setMainPanel(Component component) {
        pblmain.removeAll();
        pblmain.add(component);
        pblmain.repaint();
        pblmain.revalidate();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeder = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnThuNho = new javax.swing.JButton();
        btnPhongTo = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblTenTaiKhoan = new javax.swing.JLabel();
        lblVaiTro = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        lblDongHo = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        lblCocCaffee = new javax.swing.JLabel();
        lblBangHieu = new javax.swing.JLabel();
        lblMayCaffee = new javax.swing.JLabel();
        pnlLeft = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlMain = new javax.swing.JPanel();
        banHang = new javax.swing.JPanel();
        lblBanHang = new javax.swing.JLabel();
        thongKe = new javax.swing.JPanel();
        lblThongKe = new javax.swing.JLabel();
        khuyenMai = new javax.swing.JPanel();
        lblkm = new javax.swing.JLabel();
        ban = new javax.swing.JPanel();
        lblBan = new javax.swing.JLabel();
        qlNhanVien = new javax.swing.JPanel();
        lblQlnv = new javax.swing.JLabel();
        qlCaLam = new javax.swing.JPanel();
        lblQlCa = new javax.swing.JLabel();
        qlSanPham = new javax.swing.JPanel();
        lblQlsp = new javax.swing.JLabel();
        QLgiaodich = new javax.swing.JPanel();
        lblQlGiaoDich = new javax.swing.JLabel();
        qlDoiTac = new javax.swing.JPanel();
        lblqlDoiTac = new javax.swing.JLabel();
        qlNguyenLieu = new javax.swing.JPanel();
        lblQlNguyenLieu = new javax.swing.JLabel();
        qlNguoiDung = new javax.swing.JPanel();
        lblQlNguoiDung = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        qlChiNhanh = new javax.swing.JPanel();
        lblQlChiNhanh = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        pblmain = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlHeder.setBackground(new java.awt.Color(133, 87, 41));

        jPanel5.setBackground(new java.awt.Color(133, 87, 41));

        jPanel3.setBackground(new java.awt.Color(133, 87, 41));
        jPanel3.setLayout(new java.awt.GridLayout(1, 3));

        btnThuNho.setBackground(new java.awt.Color(133, 87, 41));
        btnThuNho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/minus-sign.png"))); // NOI18N
        btnThuNho.setBorder(null);
        btnThuNho.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnThuNhoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnThuNhoMouseExited(evt);
            }
        });
        btnThuNho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThuNhoActionPerformed(evt);
            }
        });
        jPanel3.add(btnThuNho);

        btnPhongTo.setBackground(new java.awt.Color(133, 87, 41));
        btnPhongTo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/maximize.png"))); // NOI18N
        btnPhongTo.setBorder(null);
        btnPhongTo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPhongToMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPhongToMouseExited(evt);
            }
        });
        jPanel3.add(btnPhongTo);

        btnClose.setBackground(new java.awt.Color(133, 87, 41));
        btnClose.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnClose.setForeground(new java.awt.Color(0, 0, 0));
        btnClose.setBorder(null);
        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCloseMouseExited(evt);
            }
        });
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        jPanel3.add(btnClose);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Palace Script MT", 0, 110)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("- Berk's Beans Coffee -");

        jPanel4.setBackground(new java.awt.Color(133, 87, 41));

        lblTenTaiKhoan.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        lblTenTaiKhoan.setForeground(new java.awt.Color(255, 255, 255));
        lblTenTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/taiKhoan.png"))); // NOI18N
        lblTenTaiKhoan.setText("Hoten");

        lblVaiTro.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblVaiTro.setForeground(new java.awt.Color(255, 255, 255));
        lblVaiTro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/viTri.png"))); // NOI18N
        lblVaiTro.setText("chiNhanh");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTenTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblVaiTro, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTenTaiKhoan)
                .addGap(18, 18, 18)
                .addComponent(lblVaiTro)
                .addGap(18, 18, 18))
        );

        jPanel6.setBackground(new java.awt.Color(133, 87, 41));
        jPanel6.setForeground(new java.awt.Color(133, 87, 41));

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 0, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Hotline :  035 . 770 . 2364");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel8.setBackground(new java.awt.Color(133, 87, 41));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBackground(new java.awt.Color(22, 35, 43));
        jPanel9.setLayout(new java.awt.GridLayout(0, 1));

        lblDongHo.setFont(new java.awt.Font("Segoe UI Light", 1, 20)); // NOI18N
        lblDongHo.setForeground(new java.awt.Color(255, 255, 255));
        lblDongHo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDongHo.setText("00:00:00");
        lblDongHo.setFocusable(false);
        lblDongHo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel9.add(lblDongHo);

        lblDate.setBackground(new java.awt.Color(255, 255, 255));
        lblDate.setFont(new java.awt.Font("Segoe UI Light", 0, 15)); // NOI18N
        lblDate.setForeground(new java.awt.Color(255, 255, 255));
        lblDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDate.setText("28/06/2004");
        jPanel9.add(lblDate);

        jPanel8.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 110, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/alarm-clock (1).png"))); // NOI18N
        jPanel8.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, -1, 80));

        jPanel10.setBackground(new java.awt.Color(22, 35, 43));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(133, 87, 41), 10));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel8.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 100, 40));

        lblCocCaffee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/iced-coffee.png"))); // NOI18N

        lblBangHieu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBangHieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/shop-sign.png"))); // NOI18N

        javax.swing.GroupLayout pnlHederLayout = new javax.swing.GroupLayout(pnlHeder);
        pnlHeder.setLayout(pnlHederLayout);
        pnlHederLayout.setHorizontalGroup(
            pnlHederLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHederLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCocCaffee)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMayCaffee, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 767, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(pnlHederLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBangHieu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHederLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        pnlHederLayout.setVerticalGroup(
            pnlHederLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlHederLayout.createSequentialGroup()
                .addGroup(pnlHederLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHederLayout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(lblBangHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHederLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(pnlHederLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCocCaffee, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblMayCaffee, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        getContentPane().add(pnlHeder, java.awt.BorderLayout.PAGE_START);

        pnlLeft.setBackground(new java.awt.Color(25, 25, 25));
        pnlLeft.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("SimSun-ExtB", 0, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Function Category");
        pnlLeft.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, 260, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("------------------------------------------------");
        pnlLeft.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 230, 20));

        pnlMain.setBackground(new java.awt.Color(25, 25, 25));
        pnlMain.setLayout(new java.awt.GridLayout(12, 0));

        banHang.setBackground(new java.awt.Color(25, 25, 25));
        banHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                banHangMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                banHangMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                banHangMouseExited(evt);
            }
        });
        banHang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblBanHang.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        lblBanHang.setForeground(new java.awt.Color(255, 255, 255));
        lblBanHang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBanHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/shopping-bag (1).png"))); // NOI18N
        lblBanHang.setText("Bán hàng");
        lblBanHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBanHangMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblBanHangMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblBanHangMouseExited(evt);
            }
        });
        banHang.add(lblBanHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 250, 60));

        pnlMain.add(banHang);

        thongKe.setBackground(new java.awt.Color(25, 25, 25));
        thongKe.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblThongKe.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        lblThongKe.setForeground(new java.awt.Color(255, 255, 255));
        lblThongKe.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bar-chart.png"))); // NOI18N
        lblThongKe.setText("Thống kê");
        lblThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblThongKeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblThongKeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblThongKeMouseExited(evt);
            }
        });
        thongKe.add(lblThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 250, 60));

        pnlMain.add(thongKe);

        khuyenMai.setBackground(new java.awt.Color(25, 25, 25));
        khuyenMai.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblkm.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        lblkm.setForeground(new java.awt.Color(255, 255, 255));
        lblkm.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblkm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/tag.png"))); // NOI18N
        lblkm.setText("Khuyến mãi");
        lblkm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblkmMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblkmMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblkmMouseExited(evt);
            }
        });
        khuyenMai.add(lblkm, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 250, 60));

        pnlMain.add(khuyenMai);

        ban.setBackground(new java.awt.Color(25, 25, 25));
        ban.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblBan.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        lblBan.setForeground(new java.awt.Color(255, 255, 255));
        lblBan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/chair.png"))); // NOI18N
        lblBan.setText("Bàn");
        lblBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblBanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblBanMouseExited(evt);
            }
        });
        ban.add(lblBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 250, 60));

        pnlMain.add(ban);

        qlNhanVien.setBackground(new java.awt.Color(25, 25, 25));
        qlNhanVien.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblQlnv.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        lblQlnv.setForeground(new java.awt.Color(255, 255, 255));
        lblQlnv.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblQlnv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/productivity.png"))); // NOI18N
        lblQlnv.setText("Quản lý nhân viên");
        lblQlnv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQlnvMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQlnvMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQlnvMouseExited(evt);
            }
        });
        qlNhanVien.add(lblQlnv, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 250, 60));

        pnlMain.add(qlNhanVien);

        qlCaLam.setBackground(new java.awt.Color(25, 25, 25));
        qlCaLam.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblQlCa.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        lblQlCa.setForeground(new java.awt.Color(255, 255, 255));
        lblQlCa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblQlCa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/shift.png"))); // NOI18N
        lblQlCa.setText("Quản lý ca làm");
        lblQlCa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQlCaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQlCaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQlCaMouseExited(evt);
            }
        });
        qlCaLam.add(lblQlCa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 250, 60));

        pnlMain.add(qlCaLam);

        qlSanPham.setBackground(new java.awt.Color(25, 25, 25));
        qlSanPham.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblQlsp.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        lblQlsp.setForeground(new java.awt.Color(255, 255, 255));
        lblQlsp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblQlsp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/supply-chain.png"))); // NOI18N
        lblQlsp.setText("Quản lý sản phẩm");
        lblQlsp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQlspMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQlspMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQlspMouseExited(evt);
            }
        });
        qlSanPham.add(lblQlsp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 250, 60));

        pnlMain.add(qlSanPham);

        QLgiaodich.setBackground(new java.awt.Color(25, 25, 25));
        QLgiaodich.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblQlGiaoDich.setBackground(new java.awt.Color(255, 255, 255));
        lblQlGiaoDich.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        lblQlGiaoDich.setForeground(new java.awt.Color(255, 255, 255));
        lblQlGiaoDich.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/transaction.png"))); // NOI18N
        lblQlGiaoDich.setText("Quản lý giao dịch");
        lblQlGiaoDich.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQlGiaoDichMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQlGiaoDichMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQlGiaoDichMouseExited(evt);
            }
        });
        QLgiaodich.add(lblQlGiaoDich, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 250, 60));

        pnlMain.add(QLgiaodich);

        qlDoiTac.setBackground(new java.awt.Color(25, 25, 25));
        qlDoiTac.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblqlDoiTac.setBackground(new java.awt.Color(255, 255, 255));
        lblqlDoiTac.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        lblqlDoiTac.setForeground(new java.awt.Color(255, 255, 255));
        lblqlDoiTac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/handshake.png"))); // NOI18N
        lblqlDoiTac.setText("Quản lý đối tác");
        lblqlDoiTac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblqlDoiTacMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblqlDoiTacMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblqlDoiTacMouseExited(evt);
            }
        });
        qlDoiTac.add(lblqlDoiTac, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 250, 60));

        pnlMain.add(qlDoiTac);

        qlNguyenLieu.setBackground(new java.awt.Color(25, 25, 25));
        qlNguyenLieu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblQlNguyenLieu.setBackground(new java.awt.Color(255, 255, 255));
        lblQlNguyenLieu.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        lblQlNguyenLieu.setForeground(new java.awt.Color(255, 255, 255));
        lblQlNguyenLieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/briefcase.png"))); // NOI18N
        lblQlNguyenLieu.setText("Quản lý nguyên liệu");
        lblQlNguyenLieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQlNguyenLieuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQlNguyenLieuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQlNguyenLieuMouseExited(evt);
            }
        });
        qlNguyenLieu.add(lblQlNguyenLieu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 250, 60));

        pnlMain.add(qlNguyenLieu);

        qlNguoiDung.setBackground(new java.awt.Color(25, 25, 25));
        qlNguoiDung.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblQlNguoiDung.setBackground(new java.awt.Color(255, 255, 255));
        lblQlNguoiDung.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        lblQlNguoiDung.setForeground(new java.awt.Color(255, 255, 255));
        lblQlNguoiDung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/profile.png"))); // NOI18N
        lblQlNguoiDung.setText("Quản lý tài khoản");
        lblQlNguoiDung.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQlNguoiDungMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQlNguoiDungMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQlNguoiDungMouseExited(evt);
            }
        });
        qlNguoiDung.add(lblQlNguoiDung, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 250, 60));

        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("jLabel3");
        jPanel13.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 0, 130, 50));

        qlNguoiDung.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 240, 50));

        pnlMain.add(qlNguoiDung);

        qlChiNhanh.setBackground(new java.awt.Color(25, 25, 25));
        qlChiNhanh.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblQlChiNhanh.setBackground(new java.awt.Color(255, 255, 255));
        lblQlChiNhanh.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        lblQlChiNhanh.setForeground(new java.awt.Color(255, 255, 255));
        lblQlChiNhanh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/coffee-shop.png"))); // NOI18N
        lblQlChiNhanh.setText("Quản lý chi nhánh");
        lblQlChiNhanh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQlChiNhanhMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQlChiNhanhMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQlChiNhanhMouseExited(evt);
            }
        });
        qlChiNhanh.add(lblQlChiNhanh, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 250, 60));

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("jLabel3");
        jPanel7.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 0, 130, 50));

        qlChiNhanh.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 240, 50));

        pnlMain.add(qlChiNhanh);

        pnlLeft.add(pnlMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, -1, 700));

        getContentPane().add(pnlLeft, java.awt.BorderLayout.LINE_START);

        pblmain.setLayout(new java.awt.BorderLayout());
        getContentPane().add(pblmain, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void banHangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_banHangMouseEntered
        BanHang bh = new BanHang(_admin, _nguoiDung);
        setMainPanel(bh);
    }//GEN-LAST:event_banHangMouseEntered

    private void banHangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_banHangMouseExited

    }//GEN-LAST:event_banHangMouseExited

    private void lblBanHangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBanHangMouseEntered
        Font originalFont = lblBanHang.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.BOLD, originalFont.getSize() + 2);
        lblBanHang.setFont(boldFont);
        banHang.setBackground(new Color(51, 51, 51));
        SVGIconHelper.setIcon(lblBanHang, "icon/ticket-8-svgrepo-com-1.svg", 32, 32);
    }//GEN-LAST:event_lblBanHangMouseEntered

    private void lblBanHangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBanHangMouseExited
        Font originalFont = lblBanHang.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.PLAIN, originalFont.getSize() - 2);
        lblBanHang.setFont(boldFont);
        banHang.setBackground(new Color(25, 25, 25));
        SVGIconHelper.setIcon(lblBanHang, "icon/ticket-8-svgrepo-com-1.svg", 25, 25);
    }//GEN-LAST:event_lblBanHangMouseExited

    private void lblThongKeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThongKeMouseEntered
        Font originalFont = lblThongKe.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.BOLD, originalFont.getSize() + 2);
        lblThongKe.setFont(boldFont);
        thongKe.setBackground(new Color(51, 51, 51));
        SVGIconHelper.setIcon(lblThongKe, "icon/statistical-chart-svgrepo-com.svg", 32, 32);

    }//GEN-LAST:event_lblThongKeMouseEntered

    private void lblThongKeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThongKeMouseExited
        Font originalFont = lblThongKe.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.PLAIN, originalFont.getSize() - 2);
        lblThongKe.setFont(boldFont);
        thongKe.setBackground(new Color(25, 25, 25));
        SVGIconHelper.setIcon(lblThongKe, "icon/statistical-chart-svgrepo-com.svg", 25, 25);


    }//GEN-LAST:event_lblThongKeMouseExited

    private void lblkmMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblkmMouseEntered
        Font originalFont = lblkm.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.BOLD, originalFont.getSize() + 2);
        lblkm.setFont(boldFont);
        khuyenMai.setBackground(new Color(51, 51, 51));
        SVGIconHelper.setIcon(lblkm, "icon/sale-svgrepo-com.svg", 32, 32);
    }//GEN-LAST:event_lblkmMouseEntered

    private void lblkmMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblkmMouseExited
        Font originalFont = lblkm.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.PLAIN, originalFont.getSize() - 2);
        lblkm.setFont(boldFont);
        SVGIconHelper.setIcon(lblkm, "icon/sale-svgrepo-com.svg", 25, 25);
        khuyenMai.setBackground(new Color(25, 25, 25));
    }//GEN-LAST:event_lblkmMouseExited

    private void lblBanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBanMouseEntered
        Font originalFont = lblBan.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.BOLD, originalFont.getSize() + 2);
        lblBan.setFont(boldFont);
        ban.setBackground(new Color(51, 51, 51));
        SVGIconHelper.setIcon(lblBan, "icon/table-svgrepo-com.svg", 32, 32);

    }//GEN-LAST:event_lblBanMouseEntered

    private void lblBanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBanMouseExited
        Font originalFont = lblBan.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.PLAIN, originalFont.getSize() - 2);
        lblBan.setFont(boldFont);
        ban.setBackground(new Color(25, 25, 25));
        SVGIconHelper.setIcon(lblBan, "icon/table-svgrepo-com.svg", 25, 25);

    }//GEN-LAST:event_lblBanMouseExited

    private void lblQlnvMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQlnvMouseEntered
        Font originalFont = lblQlnv.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.BOLD, originalFont.getSize() + 2);
        lblQlnv.setFont(boldFont);
        qlNhanVien.setBackground(new Color(51, 51, 51));
        SVGIconHelper.setIcon(lblQlnv, "icon/employee-settings-svgrepo-com.svg", 32, 32);

    }//GEN-LAST:event_lblQlnvMouseEntered

    private void lblQlnvMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQlnvMouseExited
        Font originalFont = lblQlnv.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.PLAIN, originalFont.getSize() - 2);
        lblQlnv.setFont(boldFont);
        qlNhanVien.setBackground(new Color(25, 25, 25));
        SVGIconHelper.setIcon(lblQlnv, "icon/employee-settings-svgrepo-com.svg", 25, 25);

    }//GEN-LAST:event_lblQlnvMouseExited

    private void lblQlspMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQlspMouseEntered
        Font originalFont = lblQlsp.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.BOLD, originalFont.getSize() + 2);
        lblQlsp.setFont(boldFont);
        qlSanPham.setBackground(new Color(51, 51, 51));
        SVGIconHelper.setIcon(lblQlsp, "icon/product-svgrepo-com.svg", 32, 32);

    }//GEN-LAST:event_lblQlspMouseEntered

    private void lblQlspMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQlspMouseExited
        Font originalFont = lblQlsp.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.PLAIN, originalFont.getSize() - 2);
        lblQlsp.setFont(boldFont);
        qlSanPham.setBackground(new Color(25, 25, 25));
        SVGIconHelper.setIcon(lblQlsp, "icon/product-svgrepo-com.svg", 25, 25);

    }//GEN-LAST:event_lblQlspMouseExited

    private void btnThuNhoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThuNhoMouseEntered

        btnThuNho.setBackground(new Color(255, 218, 185));
    }//GEN-LAST:event_btnThuNhoMouseEntered

    private void btnThuNhoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThuNhoMouseExited

        btnThuNho.setBackground(new Color(133, 87, 41));
    }//GEN-LAST:event_btnThuNhoMouseExited

    private void btnThuNhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThuNhoActionPerformed
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_btnThuNhoActionPerformed

    private void btnPhongToMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPhongToMouseEntered
        btnPhongTo.setBackground(new Color(255, 218, 185));
    }//GEN-LAST:event_btnPhongToMouseEntered

    private void btnPhongToMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPhongToMouseExited
        btnPhongTo.setBackground(new Color(133, 87, 41));
    }//GEN-LAST:event_btnPhongToMouseExited

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        int result = JOptionPane.showConfirmDialog(this, "Bạn có muốn thoát khỏi App ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            Login l = new Login();
            l.setVisible(true);
            this.setVisible(false);
        }
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseEntered
        btnClose.setBackground(Color.red);
    }//GEN-LAST:event_btnCloseMouseEntered

    private void btnCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseExited
        btnClose.setBackground(new Color(133, 87, 41));

    }//GEN-LAST:event_btnCloseMouseExited

    private void lblQlCaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQlCaMouseEntered
        Font originalFont = lblQlCa.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.BOLD, originalFont.getSize() + 2);
        lblQlCa.setFont(boldFont);
        qlCaLam.setBackground(new Color(51, 51, 51));
        SVGIconHelper.setIcon(lblQlCa, "icon/shift-change-svgrepo-com.svg", 32, 32);

    }//GEN-LAST:event_lblQlCaMouseEntered

    private void lblQlCaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQlCaMouseExited
        Font originalFont = lblQlCa.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.PLAIN, originalFont.getSize() - 2);
        lblQlCa.setFont(boldFont);
        qlCaLam.setBackground(new Color(25, 25, 25));
        SVGIconHelper.setIcon(lblQlCa, "icon/shift-change-svgrepo-com.svg", 25, 25);

    }//GEN-LAST:event_lblQlCaMouseExited

    private void lblQlGiaoDichMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQlGiaoDichMouseEntered
        Font originalFont = lblQlGiaoDich.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.BOLD, originalFont.getSize() + 2);
        lblQlGiaoDich.setFont(boldFont);
        SVGIconHelper.setIcon(lblQlGiaoDich, "icon/transaction.svg", 32, 32);

        QLgiaodich.setBackground(new Color(51, 51, 51));
    }//GEN-LAST:event_lblQlGiaoDichMouseEntered

    private void lblQlGiaoDichMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQlGiaoDichMouseExited
        Font originalFont = lblQlGiaoDich.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.PLAIN, originalFont.getSize() - 2);
        lblQlGiaoDich.setFont(boldFont);
        QLgiaodich.setBackground(new Color(25, 25, 25));
        SVGIconHelper.setIcon(lblQlGiaoDich, "icon/transaction.svg", 25, 25);

    }//GEN-LAST:event_lblQlGiaoDichMouseExited

    private void lblqlDoiTacMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblqlDoiTacMouseEntered
        Font originalFont = lblqlDoiTac.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.BOLD, originalFont.getSize() + 2);
        lblqlDoiTac.setFont(boldFont);
        qlDoiTac.setBackground(new Color(51, 51, 51));
        SVGIconHelper.setIcon(lblqlDoiTac, "icon/client-financing-01-svgrepo-com.svg", 40, 40);

    }//GEN-LAST:event_lblqlDoiTacMouseEntered

    private void lblqlDoiTacMouseExited(java.awt.event.MouseEvent evt) {
        Font originalFont = lblqlDoiTac.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.PLAIN, originalFont.getSize() - 2);
        lblqlDoiTac.setFont(boldFont);
        qlDoiTac.setBackground(new Color(25, 25, 25));
        SVGIconHelper.setIcon(lblqlDoiTac, "icon/client-financing-01-svgrepo-com.svg", 30, 30);

    }

    private void lblQlNguyenLieuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQlNguyenLieuMouseEntered
        Font originalFont = lblQlNguyenLieu.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.BOLD, originalFont.getSize() + 2);
        lblQlNguyenLieu.setFont(boldFont);
        qlNguyenLieu.setBackground(new Color(51, 51, 51));
        SVGIconHelper.setIcon(lblQlNguyenLieu, "icon/package.svg", 36, 36);

    }//GEN-LAST:event_lblQlNguyenLieuMouseEntered

    private void lblQlNguyenLieuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQlNguyenLieuMouseExited
        Font originalFont = lblQlNguyenLieu.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.PLAIN, originalFont.getSize() - 2);
        lblQlNguyenLieu.setFont(boldFont);
        qlNguyenLieu.setBackground(new Color(25, 25, 25));
        SVGIconHelper.setIcon(lblQlNguyenLieu, "icon/package.svg", 25, 25);

    }//GEN-LAST:event_lblQlNguyenLieuMouseExited

    private void lblQlNguoiDungMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQlNguoiDungMouseEntered
        Font originalFont = lblQlNguoiDung.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.BOLD, originalFont.getSize() + 2);
        lblQlNguoiDung.setFont(boldFont);
        qlNguoiDung.setBackground(new Color(51, 51, 51));
        SVGIconHelper.setIcon(lblQlNguoiDung, "icon/AA.svg", 32, 32);

    }//GEN-LAST:event_lblQlNguoiDungMouseEntered

    private void lblQlNguoiDungMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQlNguoiDungMouseExited
        Font originalFont = lblQlNguoiDung.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.PLAIN, originalFont.getSize() - 2);
        lblQlNguoiDung.setFont(boldFont);
        qlNguoiDung.setBackground(new Color(25, 25, 25));
        SVGIconHelper.setIcon(lblQlNguoiDung, "icon/AA.svg", 25, 25);

    }//GEN-LAST:event_lblQlNguoiDungMouseExited

    private void lblQlChiNhanhMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQlChiNhanhMouseEntered
        Font originalFont = lblQlChiNhanh.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.BOLD, originalFont.getSize() + 2);
        lblQlChiNhanh.setFont(boldFont);
        qlChiNhanh.setBackground(new Color(51, 51, 51));
        SVGIconHelper.setIcon(lblQlChiNhanh, "icon/shop-svgrepo-com.svg", 32, 32);

    }//GEN-LAST:event_lblQlChiNhanhMouseEntered

    private void lblQlChiNhanhMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQlChiNhanhMouseExited
        Font originalFont = lblQlChiNhanh.getFont();
        Font boldFont = new Font(originalFont.getFontName(), Font.PLAIN, originalFont.getSize() - 2);
        lblQlChiNhanh.setFont(boldFont);
        qlChiNhanh.setBackground(new Color(25, 25, 25));
        SVGIconHelper.setIcon(lblQlChiNhanh, "icon/shop-svgrepo-com.svg", 25, 25);


    }//GEN-LAST:event_lblQlChiNhanhMouseExited

    private void lblBanHangMouseClicked(java.awt.event.MouseEvent evt) {
        LoadingData loading = new LoadingData(this, true);
        SwingWorker sw = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {

                BanHang bh = new BanHang(_admin, _nguoiDung);
                setMainPanel(bh);
                return null;
            }

            @Override
            protected void done() {
                loading.dispose();
            }
        };
        sw.execute();
        loading.setVisible(true);
    }

    private void banHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_banHangMouseClicked

    }//GEN-LAST:event_banHangMouseClicked

    private void lblThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThongKeMouseClicked

        if (chucVu.equalsIgnoreCase("Ông chủ") || chucVu.equalsIgnoreCase("Quản lý")) {
            ThongKe tk = new ThongKe(_admin, _nguoiDung);
            setMainPanel(tk);
        } else {
            JOptionPane.showMessageDialog(this, "Bạn không đủ thẩm quyền");
        }
    }//GEN-LAST:event_lblThongKeMouseClicked

    private void lblQlspMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQlspMouseClicked

        if (chucVu.equalsIgnoreCase("Ông chủ") || chucVu.equalsIgnoreCase("Quản lý")) {

            LoadingData loading = new LoadingData(this, true);
            SwingWorker sw = new SwingWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    QLSanPham qlsp = new QLSanPham(_admin, _nguoiDung);
                    setMainPanel(qlsp);
                    return null;
                }

                @Override
                protected void done() {
                    loading.dispose();
                }
            };
            sw.execute();
            loading.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Bạn không đủ thẩm quyền");
        }
    }//GEN-LAST:event_lblQlspMouseClicked

    private void lblQlNguyenLieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQlNguyenLieuMouseClicked

        if (chucVu.equalsIgnoreCase("Ông chủ") || chucVu.equalsIgnoreCase("Quản lý")) {
            QLNguyenLieu qlnl = new QLNguyenLieu(_admin, _nguoiDung);
            setMainPanel(qlnl);
        } else {
            JOptionPane.showMessageDialog(this, "Bạn không đủ thẩm quyền");
        }
    }//GEN-LAST:event_lblQlNguyenLieuMouseClicked

    private void lblQlNguoiDungMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQlNguoiDungMouseClicked
        if (chucVu.equalsIgnoreCase("Ông chủ") || chucVu.equalsIgnoreCase("Quản lý")) {
            QLNguoiDung qlnd = new QLNguoiDung(_admin, _nguoiDung);
            setMainPanel(qlnd);
        } else {
            JOptionPane.showMessageDialog(this, "Bạn không đủ thẩm quyền");
        }

    }//GEN-LAST:event_lblQlNguoiDungMouseClicked

    private void lblQlChiNhanhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQlChiNhanhMouseClicked
        QLChiNhanh qlnd = new QLChiNhanh(_admin, _nguoiDung);
        setMainPanel(qlnd);
    }//GEN-LAST:event_lblQlChiNhanhMouseClicked

    private void lblQlGiaoDichMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQlGiaoDichMouseClicked
        LoadingData loading = new LoadingData(this, true);
        SwingWorker sw = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                QLGiaoDich qlgd = new QLGiaoDich(_admin, _nguoiDung);
                setMainPanel(qlgd);
                return null;
            }

            @Override
            protected void done() {
                loading.dispose();
            }
        };
        sw.execute();
        loading.setVisible(true);


    }//GEN-LAST:event_lblQlGiaoDichMouseClicked

    private void lblBanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBanMouseClicked

        if (chucVu.equalsIgnoreCase("Ông chủ") || chucVu.equalsIgnoreCase("Quản lý")) {
            QLBan qlb = new QLBan(_admin, _nguoiDung);
            setMainPanel(qlb);
        } else {
            JOptionPane.showMessageDialog(this, "Bạn không đủ thẩm quyền");
        }
    }//GEN-LAST:event_lblBanMouseClicked

    private void lblkmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblkmMouseClicked
        if (chucVu.equalsIgnoreCase("Ông chủ") || chucVu.equalsIgnoreCase("Quản lý")) {

            LoadingData loading = new LoadingData(this, true);
            SwingWorker sw = new SwingWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    if (nv != null) {
                        _khuyenMai = new QLKhuyenMai(chucVu, nv.getChiNhanh());
                    } else {
                        _khuyenMai = new QLKhuyenMai(chucVu);
                    }
                    setMainPanel(_khuyenMai);
                    return null;
                }

                @Override
                protected void done() {
                    loading.dispose();
                }
            };
            sw.execute();
            loading.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(this, "Bạn không đủ thẩm quyền");
        }
    }//GEN-LAST:event_lblkmMouseClicked

    private void lblQlnvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQlnvMouseClicked
        if (chucVu.equalsIgnoreCase("Ông chủ") || chucVu.equalsIgnoreCase("Quản lý")) {
            if (nv != null) {
                System.out.println("Hello");
                _qlNhanVien = new QLNhanVien(chucVu, nv.getChiNhanh());
            } else {
                _qlNhanVien = new QLNhanVien(chucVu);
            }
            setMainPanel(_qlNhanVien);
        } else {
            JOptionPane.showMessageDialog(this, "Bạn không đủ thẩm quyền");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_lblQlnvMouseClicked

    private void lblQlCaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQlCaMouseClicked
        if (chucVu.equalsIgnoreCase("Ông chủ") || chucVu.equalsIgnoreCase("Quản lý")) {
            QLCaLam qlcl = new QLCaLam(_admin, _nguoiDung);
            setMainPanel(qlcl);
        } else {
            JOptionPane.showMessageDialog(this, "Bạn không đủ thẩm quyền");
        }
    }//GEN-LAST:event_lblQlCaMouseClicked

    private void lblqlDoiTacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblqlDoiTacMouseClicked

        if (chucVu.equalsIgnoreCase("Ông chủ") || chucVu.equalsIgnoreCase("Quản lý")) {
            if (nv != null) {
                _qlDoiTac = new QLDoiTac(chucVu, nv.getChiNhanh());
            } else {
                _qlDoiTac = new QLDoiTac(chucVu);
            }
            setMainPanel(_qlDoiTac);
        } else {
            JOptionPane.showMessageDialog(this, "Bạn không đủ thẩm quyền");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_lblqlDoiTacMouseClicked

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel QLgiaodich;
    private javax.swing.JPanel ban;
    private javax.swing.JPanel banHang;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnPhongTo;
    private javax.swing.JButton btnThuNho;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel khuyenMai;
    private javax.swing.JLabel lblBan;
    private javax.swing.JLabel lblBanHang;
    private javax.swing.JLabel lblBangHieu;
    private javax.swing.JLabel lblCocCaffee;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblDongHo;
    private javax.swing.JLabel lblMayCaffee;
    private javax.swing.JLabel lblQlCa;
    private javax.swing.JLabel lblQlChiNhanh;
    private javax.swing.JLabel lblQlGiaoDich;
    private javax.swing.JLabel lblQlNguoiDung;
    private javax.swing.JLabel lblQlNguyenLieu;
    private javax.swing.JLabel lblQlnv;
    private javax.swing.JLabel lblQlsp;
    private javax.swing.JLabel lblTenTaiKhoan;
    private javax.swing.JLabel lblThongKe;
    private javax.swing.JLabel lblVaiTro;
    private javax.swing.JLabel lblkm;
    private javax.swing.JLabel lblqlDoiTac;
    private javax.swing.JPanel pblmain;
    private javax.swing.JPanel pnlHeder;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel qlCaLam;
    private javax.swing.JPanel qlChiNhanh;
    private javax.swing.JPanel qlDoiTac;
    private javax.swing.JPanel qlNguoiDung;
    private javax.swing.JPanel qlNguyenLieu;
    private javax.swing.JPanel qlNhanVien;
    private javax.swing.JPanel qlSanPham;
    private javax.swing.JPanel thongKe;
    // End of variables declaration//GEN-END:variables
}
