/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import chart.ModelChart;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import domainmodel.TaiKhoanAdmin;
import domainmodel.TaiKhoanNguoiDung;
import fomVe.CustomHeaderRenderer;
import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import service.IThongKeService;
import service.implement.ThongKeService;
import viewmodel.ThongKeChiNhanh;
import viewmodel.ThongKeSanPhamBanChay;
import viewmodel.ThongKeTheoThoiGianViewModel;

/**
 *
 * @author hoang
 */
public class ThongKe extends javax.swing.JPanel implements Runnable {

    /**
     * Creates new form ThongKe
     */
    private IThongKeService thongKeService;
    private DefaultTableModel modelTableDoanhThuThoiGian;
    private DefaultTableModel modelTableSanPhamBanChay;
    private DefaultTableModel modelTableThongKeChiNhanh;
    private static FlatSVGIcon icon = null;

    public static void setIcon2(JButton label, String path, int width, int height) {
        try {
            icon = new FlatSVGIcon(path, width, height);
            label.setIcon(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ThongKe(TaiKhoanAdmin admin, TaiKhoanNguoiDung nguoiDung) {
        initComponents();
        pnlBieuDoDoanhThuChiNhanh.addLegend("Tổng doanh thu theo chi nhánh", new Color(139, 229, 222));
        pnlBieuDoSpBanChay.addLegend("Top 35 sản phẩm bán chạy nhất", new Color(189, 135, 245));
        pnlBieuDoDoanhThuThoiGian.addLegend("Tổng doanh thu", new Color(135, 189, 245));
        thongKeService = new ThongKeService();
        pnlBieuDoDoanhThuChiNhanh.start();
        pnlBieuDoSpBanChay.start();
        pnlBieuDoDoanhThuThoiGian.start();
        modelTableDoanhThuThoiGian = new DefaultTableModel();
        modelTableSanPhamBanChay = new DefaultTableModel();
        modelTableThongKeChiNhanh = new DefaultTableModel();
        setIcon2(btnLocDoanhThu, "icon/filter-svgrepo-com.svg", 20, 20);
        JTableHeader tableHeader2 = tblDoanhThuChiNhanh.getTableHeader();
        tableHeader2.setBackground(new Color(143, 45, 52));
        tableHeader2.setForeground(Color.WHITE);
        tableHeader2.setBorder(null);
        TableCellRenderer defaultHeaderRenderer2 = tblDoanhThuChiNhanh.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer2 = new CustomHeaderRenderer(defaultHeaderRenderer2);
        tblDoanhThuChiNhanh.getTableHeader().setDefaultRenderer(customHeaderRenderer2);

        JTableHeader tableHeader3 = tblSanPhamBanChay.getTableHeader();
        tableHeader3.setBackground(new Color(143, 45, 52));
        tableHeader3.setForeground(Color.WHITE);
        tableHeader3.setBorder(null);
        TableCellRenderer defaultHeaderRenderer3 = tblSanPhamBanChay.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer3 = new CustomHeaderRenderer(defaultHeaderRenderer3);
        tblSanPhamBanChay.getTableHeader().setDefaultRenderer(customHeaderRenderer3);

        JTableHeader tableHeader4 = tblThongKeTheoThoiGian.getTableHeader();
        tableHeader4.setBackground(new Color(143, 45, 52));
        tableHeader4.setForeground(Color.WHITE);
        tableHeader4.setBorder(null);
        TableCellRenderer defaultHeaderRenderer4 = tblThongKeTheoThoiGian.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer4 = new CustomHeaderRenderer(defaultHeaderRenderer4);
        tblThongKeTheoThoiGian.getTableHeader().setDefaultRenderer(customHeaderRenderer4);

        Thread loadThongKeSp = new Thread(this);
        loadThongKeSp.start();
        Thread loadThongKeChiNhanh = new Thread(new Runnable() {
            @Override
            public void run() {
                showSanPhamBanChay(thongKeService.getAllSanPhamBanChay());

            }
        });
        loadThongKeChiNhanh.start();
    }

    @Override
    public void run() {
        showDoanhThuTheoChiNhanh(thongKeService.getAllThongKeChiNhanh());
    }

    private void showChartDoanhThuTheoThoiGian(List<ThongKeTheoThoiGianViewModel> listThongKe) {
        pnlBieuDoDoanhThuThoiGian.clear();
        pnlBieuDoDoanhThuThoiGian.start();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (ThongKeTheoThoiGianViewModel thoiGian : listThongKe) {
            String date = dateFormat.format(thoiGian.getNgay());
            dataset.setValue(thoiGian.getTongTienHang().subtract(thoiGian.getTongTienChietKhau()), "Tổng tiền hàng", date);
        }
        for (int i = 0; i < dataset.getColumnCount(); i++) {
            String category = dataset.getColumnKey(i).toString();
            double[] values = new double[dataset.getRowCount()];
            for (int j = 0; j < values.length; j++) {
                values[j] = dataset.getValue(j, i).doubleValue();
            }
            ModelChart data = new ModelChart(category, values);
            pnlBieuDoDoanhThuThoiGian.addData(data);
        }
        pnlBieuDoDoanhThuThoiGian.revalidate();
        pnlBieuDoDoanhThuThoiGian.repaint();
        pnlBieuDoDoanhThuThoiGian.start();

    }

    private void showDoanhThuTheoThoiGian(Date start, Date end) {
        modelTableDoanhThuThoiGian.setRowCount(0);
        modelTableDoanhThuThoiGian = (DefaultTableModel) tblThongKeTheoThoiGian.getModel();
        List<ThongKeTheoThoiGianViewModel> thongKeView = thongKeService.getAllThongKeByDate(start, end);
        for (ThongKeTheoThoiGianViewModel thongKeTheoThoiGianViewModel : thongKeView) {
            modelTableDoanhThuThoiGian.addRow(thongKeTheoThoiGianViewModel.getThongKeTheoThoiGian());
        }
        showChartDoanhThuTheoThoiGian(thongKeView);

    }

    private void showChartSanPhamBanChay(List<ThongKeSanPhamBanChay> listSpBanChay) {
        Collections.sort(listSpBanChay, (sp1, sp2) -> Integer.compare(sp2.getSoLuongbanRa(), sp1.getSoLuongbanRa()));
        List<ThongKeSanPhamBanChay> top35SpBanChay = new ArrayList<>();
        for (int i = 0; i < Math.min(35, listSpBanChay.size()); i++) {
            top35SpBanChay.add(listSpBanChay.get(i));
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (ThongKeSanPhamBanChay spBanChay : top35SpBanChay) {
            dataset.addValue(spBanChay.getSoLuongbanRa(), "Số lượng bán", spBanChay.getMaSp());
        }

        for (int i = 0; i < dataset.getColumnCount(); i++) {
            String category = dataset.getColumnKey(i).toString();
            double[] values = new double[dataset.getRowCount()];
            for (int j = 0; j < values.length; j++) {
                values[j] = dataset.getValue(j, i).doubleValue();
            }
            ModelChart data = new ModelChart(category, values);
            pnlBieuDoSpBanChay.addData(data);
        }

        pnlBieuDoSpBanChay.revalidate();
        pnlBieuDoSpBanChay.repaint();
        pnlBieuDoSpBanChay.start();
    }

    private void showSanPhamBanChay(List<ThongKeSanPhamBanChay> listSpBanChay) {
        modelTableSanPhamBanChay = (DefaultTableModel) tblSanPhamBanChay.getModel();
        modelTableSanPhamBanChay.setRowCount(0);
        for (ThongKeSanPhamBanChay spBanChay : listSpBanChay) {
            modelTableSanPhamBanChay.addRow(spBanChay.getThongKeSanPhamBanChay());
        }
        showChartSanPhamBanChay(listSpBanChay);

    }

    private void showChartDoanhThuChiNhanh(List<ThongKeChiNhanh> listThongKeChiNhanh) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (ThongKeChiNhanh chiNhanh : listThongKeChiNhanh) {
            dataset.setValue(chiNhanh.getTongDoanhThu(), "Tổng doanh thu", chiNhanh.getMaChiNhanh());

        }

        for (int i = 0; i < dataset.getColumnCount(); i++) {
            String category = dataset.getColumnKey(i).toString();
            double[] values = new double[dataset.getRowCount()];
            for (int j = 0; j < values.length; j++) {
                values[j] = dataset.getValue(j, i).doubleValue();
            }
            ModelChart data = new ModelChart(category, values);
            pnlBieuDoDoanhThuChiNhanh.addData(data);
        }
        pnlBieuDoDoanhThuChiNhanh.revalidate();
        pnlBieuDoDoanhThuChiNhanh.repaint();
        pnlBieuDoDoanhThuChiNhanh.start();

    }

    private void showDoanhThuTheoChiNhanh(List<ThongKeChiNhanh> listThongKeChiNhanh) {
        modelTableThongKeChiNhanh = (DefaultTableModel) tblDoanhThuChiNhanh.getModel();
        modelTableThongKeChiNhanh.setRowCount(0);
        for (ThongKeChiNhanh chiNhanh : listThongKeChiNhanh) {
            modelTableThongKeChiNhanh.addRow(chiNhanh.getThongKeChiNhanh());
        }
        showChartDoanhThuChiNhanh(listThongKeChiNhanh);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        dateChooserStart = new com.toedter.calendar.JDateChooser();
        dateChooserEnd = new com.toedter.calendar.JDateChooser();
        btnLocDoanhThu = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThongKeTheoThoiGian = new javax.swing.JTable();
        pnlBieuDoDoanhThuThoiGian = new chart.Chart();
        lblCanhBaoLoc = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPhamBanChay = new javax.swing.JTable();
        pnlBieuDoSpBanChay = new chart.Chart();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDoanhThuChiNhanh = new javax.swing.JTable();
        pnlBieuDoDoanhThuChiNhanh = new chart.Chart();

        setBackground(new java.awt.Color(51, 51, 51));

        jTabbedPane1.setBackground(new java.awt.Color(51, 51, 51));
        jTabbedPane1.setForeground(new java.awt.Color(51, 255, 255));
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        dateChooserStart.setDateFormatString("yyyy-MM-dd");

        dateChooserEnd.setDateFormatString("yyyy-MM-dd");

        btnLocDoanhThu.setBackground(new java.awt.Color(143, 45, 52));
        btnLocDoanhThu.setForeground(new java.awt.Color(255, 255, 255));
        btnLocDoanhThu.setText("Lọc");
        btnLocDoanhThu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLocDoanhThu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocDoanhThuActionPerformed(evt);
            }
        });

        tblThongKeTheoThoiGian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Thời gian", "SL đơn hàng", "Tổng tiền hàng", "Tổng chiết khấu", "Tổng doanh thu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblThongKeTheoThoiGian.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblThongKeTheoThoiGian);

        lblCanhBaoLoc.setForeground(new java.awt.Color(255, 51, 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(dateChooserStart, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(dateChooserEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(btnLocDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCanhBaoLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pnlBieuDoDoanhThuThoiGian, javax.swing.GroupLayout.DEFAULT_SIZE, 1243, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLocDoanhThu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateChooserStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateChooserEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCanhBaoLoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBieuDoDoanhThuThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Doanh thu theo thời gian", jPanel3);

        tblSanPhamBanChay.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sản phẩm", "Tên sản phẩm", "SL bán ra", "Tổng Tiền hàng", "Tổng Doanh thu", "SL đơn hàng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPhamBanChay.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblSanPhamBanChay);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1243, Short.MAX_VALUE)
                    .addComponent(pnlBieuDoSpBanChay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlBieuDoSpBanChay, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sản phẩm bán chạy", jPanel1);

        tblDoanhThuChiNhanh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã chi nhánh", "SL đơn hàng", "Tổng chiết khấu", "Tổng Doanh thu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDoanhThuChiNhanh.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tblDoanhThuChiNhanh);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1243, Short.MAX_VALUE)
                    .addComponent(pnlBieuDoDoanhThuChiNhanh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlBieuDoDoanhThuChiNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Doanh thu theo chi nhánh", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnLocDoanhThuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocDoanhThuActionPerformed
        if (dateChooserStart.getDate() != null && dateChooserEnd != null) {

            LoadingData loading = new LoadingData(null, true);
            SwingWorker worker = new SwingWorker<>() {
                @Override
                protected Object doInBackground() throws Exception {
                    pnlBieuDoDoanhThuThoiGian.start();
                    showDoanhThuTheoThoiGian(dateChooserStart.getDate(), dateChooserEnd.getDate());
                    pnlBieuDoDoanhThuThoiGian.start();
                    lblCanhBaoLoc.setText("");
                    return null;
                }

                @Override
                protected void done() {
                    loading.dispose();
                }
            };
            worker.execute();
            loading.setVisible(true);
        } else {
            lblCanhBaoLoc.setText("Vui lòng chọn thời gian");
        }
    }//GEN-LAST:event_btnLocDoanhThuActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        
        pnlBieuDoSpBanChay.start();
        pnlBieuDoDoanhThuChiNhanh.start();
        pnlBieuDoDoanhThuThoiGian.start();
    }//GEN-LAST:event_jTabbedPane1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLocDoanhThu;
    private com.toedter.calendar.JDateChooser dateChooserEnd;
    private com.toedter.calendar.JDateChooser dateChooserStart;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblCanhBaoLoc;
    private chart.Chart pnlBieuDoDoanhThuChiNhanh;
    private chart.Chart pnlBieuDoDoanhThuThoiGian;
    private chart.Chart pnlBieuDoSpBanChay;
    private javax.swing.JTable tblDoanhThuChiNhanh;
    private javax.swing.JTable tblSanPhamBanChay;
    private javax.swing.JTable tblThongKeTheoThoiGian;
    // End of variables declaration//GEN-END:variables
}
