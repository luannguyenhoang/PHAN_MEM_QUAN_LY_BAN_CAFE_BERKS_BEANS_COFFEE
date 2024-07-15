/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import domainmodel.NhaCungCap;
import domainmodel.NhanVien;
import domainmodel.TaiKhoanAdmin;
import domainmodel.TaiKhoanNguoiDung;
import fomVe.CustomHeaderRenderer;
import fomVe.SVGIconHelper;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import fomVe.SVGIconHelperButton;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.IBanHangService;
import service.IBanService;
import service.IHoaDon;
import service.IHoaDonChiTiet;
import service.IPhieuNhap;
import service.IPhieuTra;
import service.implement.BanHangService;
import service.implement.BanService;
import service.implement.ChiTietHoaDonService;
import service.implement.HoaDonService;
import service.implement.PhieuNhapService;
import service.implement.PhieuTraService;

import viewmodel.ChiNhanhViewModel_Hoang;
import viewmodel.ChiTietPhieuNhapViewModel;
import viewmodel.ChiTietPhieuTraViewModel;
import viewmodel.ChitietHoaDonViewModel;
import viewmodel.HoaDonViewModel;
import viewmodel.NguyenLieuViewModel_Hoang;
import viewmodel.NhaCungCapViewModel_Hoang;
import viewmodel.NhanVienViewModel_Hoang;
import viewmodel.PhieuNhapViewModel;
import viewmodel.PhieuTraViewModel;

/**
 *
 * @author hoang
 */
public class QLGiaoDich extends javax.swing.JPanel implements Runnable {

    /**
     * Creates new form QLGiaoDich1
     */
    private static FlatSVGIcon icon = null;

    public static void setIcon2(JButton label, String path, int width, int height) {
        try {
            icon = new FlatSVGIcon(path, width, height);
            label.setIcon(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    TaiKhoanAdmin _admin;
    TaiKhoanNguoiDung _nguoiDung;
    DefaultTableModel modelPhieuNhap = new DefaultTableModel();
    DefaultTableModel modelChiTietPhieuNhap = new DefaultTableModel();
    DefaultTableModel modelPhieuTra = new DefaultTableModel();
    DefaultTableModel modelChiTietPhieuTra = new DefaultTableModel();
    DefaultTableModel modelHoaDon = new DefaultTableModel();
    DefaultTableModel modelHDCT = new DefaultTableModel();
    DefaultTableModel modelNguyenLieu = new DefaultTableModel();
    DefaultTableModel modelNguyenLieuTra = new DefaultTableModel();
    DefaultComboBoxModel<NhaCungCapViewModel_Hoang> comboNhaCungCap;
    DefaultComboBoxModel<NhanVienViewModel_Hoang> comboNhanVien;
    DefaultComboBoxModel<NguyenLieuViewModel_Hoang> comboNguyenLieu;
    DefaultComboBoxModel<NhaCungCapViewModel_Hoang> comboNhaCungCapTra;
    DefaultComboBoxModel<NhanVienViewModel_Hoang> comboNhanVienTra;
    DefaultComboBoxModel<NguyenLieuViewModel_Hoang> comboNguyenLieuTra;
    DefaultComboBoxModel<ChiNhanhViewModel_Hoang> comboChiNhanh;
    DefaultComboBoxModel<ChiNhanhViewModel_Hoang> comboChiNhanhTra;
    IHoaDon hoaDonService = new HoaDonService();
    IHoaDonChiTiet hoaDonChiTietService = new ChiTietHoaDonService();
    List<HoaDonViewModel> lstHoaDon = new ArrayList<>();
    List<ChitietHoaDonViewModel> lstChiTietHD = new ArrayList();
    private IBanHangService banHangService;
    IPhieuNhap phieuNhapSevice = new PhieuNhapService();
    Set<ChiTietPhieuNhapViewModel> lstChiTietPhieuNhap = new HashSet<>();
    Set<PhieuNhapViewModel> lstPhieuNhap = new HashSet<>();

    IPhieuTra phieuTraService = new PhieuTraService();
    Set<PhieuTraViewModel> lstPhieuTra = new HashSet<>();
    IBanHangService iBanHang = new BanHangService();
    IBanService iBanService = new BanService();
    Set<ChiNhanhViewModel_Hoang> lstChiNhanh = new HashSet<>();
    private int hoveredRow = -1, hoveredColumn = -1;

    public QLGiaoDich(TaiKhoanAdmin admin, TaiKhoanNguoiDung nguoiDung) {
        initComponents();
        _admin = admin;
        _nguoiDung = nguoiDung;
        modelNguyenLieu = (DefaultTableModel) tblNguyenLieu.getModel();
        modelNguyenLieuTra = (DefaultTableModel) tblNguyenLieuTra.getModel();
        comboNhaCungCap = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNhaCungCap().toArray());
        cboNhaCungCapNhap.setModel((DefaultComboBoxModel) comboNhaCungCap);
        banHangService = new BanHangService();
        comboNhaCungCapTra = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNhaCungCap().toArray());
        cboNhaCungCapTra.setModel((DefaultComboBoxModel) comboNhaCungCapTra);
        lstHoaDon = hoaDonService.getAllHoaDon();
        loadTableHoaDon(lstHoaDon);
        Thread loadGIaoDich = new Thread(this);
        loadGIaoDich.start();
//        tblHoaDon.setRowHeight(30);
//        tblHoaDonChiTiet.setRowHeight(30);
//        tblPhieuNhap.setRowHeight(30);
//        tblPhieuNhapChiTiet.setRowHeight(30);
//        tblNguyenLieu.setRowHeight(20);
//        tblNguyenLieuTra.setRowHeight(20);
//        tblPhieuTra.setRowHeight(20);
//        tblChiTietPhieuTra.setRowHeight(20);
        SVGIconHelper.setIcon(lblTimKiemPhieuTra, "icon/search-alt-svgrepo-com.svg", 25, 25);
        setIcon2(btnLocHoaDon, "icon/filter-svgrepo-com.svg", 20, 20);
        SVGIconHelper.setIcon(lblTotalMoney, "icon/a.svg", 30, 30);
        SVGIconHelperButton.setIcon(btnExportPdf, "icon/printf.svg", 25, 25);

        SVGIconHelperButton.setIcon(btnExport, "icon/excel.svg", 20, 20);
        SVGIconHelperButton.setIcon(btnExport1, "icon/excel.svg", 20, 20);

        SVGIconHelperButton.setIcon(btnImport, "icon/excel.svg", 20, 20);
        SVGIconHelperButton.setIcon(btnImport1, "icon/excel.svg", 20, 20);

        SVGIconHelperButton.setIcon(btnHoanThanhPhieuNhap, "icon/v2.svg", 20, 20);
        SVGIconHelperButton.setIcon(btnHoanThanhPhieuTra, "icon/v2.svg", 20, 20);

        SVGIconHelperButton.setIcon(btnTimKiemPhieuNhap, "icon/search.svg", 20, 20);

        SVGIconHelperButton.setIcon(btnHuyPhieuTra, "icon/cancel.svg", 20, 20);
        SVGIconHelperButton.setIcon(btnHuyPhieuNhap, "icon/cancel.svg", 20, 20);
        SVGIconHelperButton.setIcon(btnTaoPhieuNhap, "icon/pen.svg", 20, 20);
        SVGIconHelperButton.setIcon(btnTaoPhieuTra, "icon/pen.svg", 20, 20);
        SVGIconHelperButton.setIcon(btnCapNhatPhieuNhap, "icon/update.svg", 20, 20);
        SVGIconHelperButton.setIcon(btnCapNhatPhieuTra, "icon/update.svg", 20, 20);

        JTableHeader tableHeader4 = tblHoaDon.getTableHeader();
        tableHeader4.setBackground(new Color(143, 45, 52));
        tableHeader4.setForeground(Color.WHITE);
        tableHeader4.setBorder(null);
        TableCellRenderer defaultHeaderRenderer4 = tblHoaDon.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer4 = new CustomHeaderRenderer(defaultHeaderRenderer4);
        tblHoaDon.getTableHeader().setDefaultRenderer(customHeaderRenderer4);

        JTableHeader tableHeader2 = tblHoaDonChiTiet.getTableHeader();
        tableHeader2.setBackground(new Color(143, 45, 52));
        tableHeader2.setForeground(Color.WHITE);
        tableHeader2.setBorder(null);
        TableCellRenderer defaultHeaderRenderer2 = tblHoaDonChiTiet.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer2 = new CustomHeaderRenderer(defaultHeaderRenderer2);
        tblHoaDonChiTiet.getTableHeader().setDefaultRenderer(customHeaderRenderer2);

        JTableHeader tableHeader3 = tblNguyenLieu.getTableHeader();
        tableHeader3.setBackground(new Color(143, 45, 52));
        tableHeader3.setForeground(Color.WHITE);
        tableHeader3.setBorder(null);
        TableCellRenderer defaultHeaderRenderer3 = tblNguyenLieu.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer3 = new CustomHeaderRenderer(defaultHeaderRenderer3);
        tblNguyenLieu.getTableHeader().setDefaultRenderer(customHeaderRenderer3);

        JTableHeader tableHeader5 = tblNguyenLieuTra.getTableHeader();
        tableHeader5.setBackground(new Color(143, 45, 52));
        tableHeader5.setForeground(Color.WHITE);
        tableHeader5.setBorder(null);
        TableCellRenderer defaultHeaderRenderer5 = tblNguyenLieuTra.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer5 = new CustomHeaderRenderer(defaultHeaderRenderer5);
        tblNguyenLieuTra.getTableHeader().setDefaultRenderer(customHeaderRenderer5);

        JTableHeader tableHeader6 = tblPhieuNhap.getTableHeader();
        tableHeader6.setBackground(new Color(143, 45, 52));
        tableHeader6.setForeground(Color.WHITE);
        tableHeader6.setBorder(null);
        TableCellRenderer defaultHeaderRenderer6 = tblPhieuNhap.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer6 = new CustomHeaderRenderer(defaultHeaderRenderer6);
        tblPhieuNhap.getTableHeader().setDefaultRenderer(customHeaderRenderer6);

        JTableHeader tableHeader7 = tblPhieuNhapChiTiet.getTableHeader();
        tableHeader7.setBackground(new Color(143, 45, 52));
        tableHeader7.setForeground(Color.WHITE);
        tableHeader7.setBorder(null);
        TableCellRenderer defaultHeaderRenderer7 = tblPhieuNhapChiTiet.getTableHeader().getDefaultRenderer();
        CustomHeaderRenderer customHeaderRenderer7 = new CustomHeaderRenderer(defaultHeaderRenderer7);
        tblPhieuNhapChiTiet.getTableHeader().setDefaultRenderer(customHeaderRenderer7);
    }

    @Override
    public void run() {
        if (_admin != null) {
            //Chi nhánh
            comboChiNhanh = (DefaultComboBoxModel) new DefaultComboBoxModel<>(iBanHang.getAllChiNhanh().toArray());
            cboChiNhanhNhap.setModel((DefaultComboBoxModel) comboChiNhanh);
            comboChiNhanhTra = (DefaultComboBoxModel) new DefaultComboBoxModel<>(iBanHang.getAllChiNhanh().toArray());
            cboChiNhanhTra.setModel((DefaultComboBoxModel) comboChiNhanhTra);
            //Nhân viên
            comboNhanVien = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNhanVienByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId()).toArray());
            cboNhanVienNhap.setModel((DefaultComboBoxModel) comboNhanVien);
            comboNhanVienTra = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNhanVienByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanhTra.getSelectedItem()).getId()).toArray());
            cboNhanVienTra.setModel((DefaultComboBoxModel) comboNhanVienTra);
            //Nguyên liệu
            comboNguyenLieu = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNguyenLieuByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId()).toArray());
            cboNguyenLieuNhap.setModel((DefaultComboBoxModel) comboNguyenLieu);
            comboNguyenLieuTra = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNguyenLieuByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanhTra.getSelectedItem()).getId()).toArray());
            cboNguyenLieuTra.setModel((DefaultComboBoxModel) comboNguyenLieuTra);
            loadAll((((ChiNhanhViewModel_Hoang) comboChiNhanhTra.getSelectedItem()).getId()));
            loadAllTra((((ChiNhanhViewModel_Hoang) comboChiNhanhTra.getSelectedItem()).getId()));

        } else {
            lblCNNhap.setVisible(false);
            cboChiNhanhNhap.setVisible(false);
            lblCNTra.setVisible(false);
            cboChiNhanhTra.setVisible(false);
            comboNhanVien = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNhanVienByChiNhanh(iBanService.getChiNhanhByTaiKhoan(_nguoiDung.getId()).getId()).toArray());
            cboNhanVienNhap.setModel((DefaultComboBoxModel) comboNhanVien);

            comboNhanVienTra = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNhanVienByChiNhanh(iBanService.getChiNhanhByTaiKhoan(_nguoiDung.getId()).getId()).toArray());
            cboNhanVienTra.setModel((DefaultComboBoxModel) comboNhanVienTra);

            comboNguyenLieu = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNguyenLieuByChiNhanh(iBanService.getChiNhanhByTaiKhoan(_nguoiDung.getId()).getId()).toArray());
            cboNguyenLieuNhap.setModel((DefaultComboBoxModel) comboNguyenLieu);

            comboNguyenLieuTra = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNguyenLieuByChiNhanh(iBanService.getChiNhanhByTaiKhoan(_nguoiDung.getId()).getId()).toArray());
            cboNguyenLieuTra.setModel((DefaultComboBoxModel) comboNguyenLieuTra);
            loadAll(iBanService.getChiNhanhByTaiKhoan(_nguoiDung.getId()).getId());
            loadAllTra(iBanService.getChiNhanhByTaiKhoan(_nguoiDung.getId()).getId());
        }
    }

    private void loadAll(String idChiNhanh) {
        lstPhieuNhap = phieuNhapSevice.getAllPhieuNhapByChiNhanh(idChiNhanh);
        loadTablePhieuNhap(lstPhieuNhap);
    }

    private void loadAllTra(String idCN) {
        lstPhieuTra = phieuTraService.getAllPhieuTraByChiNhanh(idCN);
        loadTablePhieuTra(lstPhieuTra);
    }

    private void loadTableHoaDon(List<HoaDonViewModel> lstHoaDon) {
        modelHoaDon = (DefaultTableModel) tblHoaDon.getModel();
        modelHoaDon.setRowCount(0);
        for (HoaDonViewModel x : lstHoaDon) {
            modelHoaDon.addRow(x.getDataHoaDonView());
        }
    }

    private void loadTablePhieuNhap(Set<PhieuNhapViewModel> lstPhieuNhap) {
        modelPhieuNhap = (DefaultTableModel) tblPhieuNhap.getModel();
        modelPhieuNhap.setRowCount(0);
        for (PhieuNhapViewModel x : lstPhieuNhap) {
            modelPhieuNhap.addRow(x.getDataPhieuNhapView());
        }
    }

    private void loadTablePhieuTra(Set<PhieuTraViewModel> lstPhieuTra) {
        modelPhieuTra = (DefaultTableModel) tblPhieuTra.getModel();
        modelPhieuTra.setRowCount(0);
        for (PhieuTraViewModel x : lstPhieuTra) {
            modelPhieuTra.addRow(x.getPhieuTrahangView());
        }
    }

    private NhanVienViewModel_Hoang findbyName(String MaNV) {
        for (int i = 0; i < comboNhanVien.getSize(); i++) {
            NhanVienViewModel_Hoang nv = comboNhanVien.getElementAt(i);
            if (MaNV.equalsIgnoreCase(nv.getMa())) {
                return nv;
            }
        }
        return null;
    }

    private NhanVienViewModel_Hoang findbyNameTra(String MaNV) {
        for (int i = 0; i < comboNhanVienTra.getSize(); i++) {
            NhanVienViewModel_Hoang nv = comboNhanVienTra.getElementAt(i);
            if (MaNV.equalsIgnoreCase(nv.getMa())) {
                return nv;
            }
        }
        return null;
    }

    private void fillTablePhieuTra(int index) {
        if (tblPhieuTra.getRowCount() >= 0) {
            txtMaPhieuTra.setText(tblPhieuTra.getValueAt(index, 1).toString());
            for (int i = 0; i < phieuNhapSevice.getAllNhaCungCap().size(); i++) {
                if (phieuNhapSevice.getAllNhaCungCap().get(i).getMa().equals(tblPhieuTra.getValueAt(index, 2))) {
                    cboNhaCungCapTra.setSelectedIndex(i);
                }
            }
            cboNhanVienTra.setSelectedItem(findbyNameTra(tblPhieuTra.getValueAt(index, 4).toString()));
            dateNgayTra.setDate((Date) tblPhieuTra.getValueAt(index, 6));
        }
    }

    private void showChiTietByPhieuNhap(String idPN) {
        modelChiTietPhieuNhap = (DefaultTableModel) tblPhieuNhapChiTiet.getModel();
        modelChiTietPhieuNhap.setRowCount(0);
        PhieuNhapViewModel pn = phieuNhapSevice.getPhieuNhapById(idPN);
        Set<ChiTietPhieuNhapViewModel> chiTietView = phieuNhapSevice.getPhieuNhapByChiTietPhieuNhap(idPN);
        for (ChiTietPhieuNhapViewModel ctView : chiTietView) {
            modelChiTietPhieuNhap.addRow(ctView.getDataChiTietPhieuNhapView());
        }
    }

    private void showNguyenLieuByPhieuNhap(String idPN) {
        modelNguyenLieu = (DefaultTableModel) tblNguyenLieu.getModel();
        modelNguyenLieu.setRowCount(0);
        PhieuNhapViewModel pn = phieuNhapSevice.getPhieuNhapById(idPN);
        Set<ChiTietPhieuNhapViewModel> chiTietView = phieuNhapSevice.getPhieuNhapByChiTietPhieuNhap(idPN);
        for (ChiTietPhieuNhapViewModel ctView : chiTietView) {
            modelNguyenLieu.addRow(ctView.getDataNguyenLieuView());
        }
    }

    private void clearFormPhieuNhap() {
        txtMaPhieuNhap.setText("");
        cboNguyenLieuNhap.setSelectedIndex(0);
        cboNhanVienNhap.setSelectedIndex(0);
        cboNhaCungCapNhap.setSelectedIndex(0);
        dateNgayNhap.setDate(null);
        modelNguyenLieu.setRowCount(0);
        modelChiTietPhieuNhap.setRowCount(0);
        txtTimKiemPhieuNhap.setText("");
    }

    private boolean checkEmpty() {
        if (txtMaPhieuNhap.getText().isEmpty() || cboNhaCungCapNhap.getSelectedItem() == null || cboNguyenLieuNhap.getSelectedItem() == null || cboNhanVienNhap.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Dũ liệu trống");
            return false;
        } else if (tblNguyenLieu.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm nguyên liệu");
            return false;
        } else if (dateNgayNhap.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Sai định dạng ngày tháng");
            return false;
        } else if (txtMaPhieuNhap.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Ký tự không hợp lệ");
            return false;
        } else if (txtMaPhieuNhap.getText().length() > 10) {
            JOptionPane.showMessageDialog(this, "Mã phiếu nhập không vượt quá 10 ký tự");
            return false;
        }
        return true;
    }

    private boolean checkEmptyPhieuTra() {
        int row = tblNguyenLieuTra.getSelectedRow();
        if (txtMaPhieuTra.getText().isEmpty() || cboNhaCungCapTra.getSelectedItem() == null || cboNguyenLieuTra.getSelectedItem() == null || cboNhanVienTra.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Dũ liệu trống");
            return false;
        } else if (tblNguyenLieuTra.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm nguyên liệu");
            return false;
        } else if (dateNgayTra.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Sai định dạng ngày tháng");
            return false;
        } else if (txtMaPhieuTra.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Ký tự không hợp lệ");
            return false;
        } else if (txtMaPhieuTra.getText().length() > 10) {
            JOptionPane.showMessageDialog(this, "Mã phiếu nhập không vượt quá 10 ký tự");
            return false;
        } else if (tblNguyenLieuTra.getValueAt(row, 5).toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa nhập lý do");
            return false;
        }
        return true;
    }

    public boolean checkMaPhieuTra(String maPT) {
        if (phieuTraService.getPhieuTraByMa(maPT) != null) {
            JOptionPane.showMessageDialog(this, "Mã phiếu trả đã tồn tại");
            return true;
        }
        return false;
    }

    public boolean checkMaPhieuNhap(String maPN) {
        if (phieuNhapSevice.getPhieuNhapByMa(maPN) != null) {
            JOptionPane.showMessageDialog(this, "Mã phiếu nhập đã tồn tại");
            return true;
        }
        return false;
    }

    private boolean checkSoluong() {
        int row = tblNguyenLieu.getSelectedRow();
        float soLuong;
        try {
            soLuong = Float.parseFloat(tblNguyenLieu.getValueAt(row, 3).toString());
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập lại số lượng");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lại số lượng");
            return false;
        }
        return true;
    }

    private boolean checkSoluongTra() {
        int row = tblNguyenLieuTra.getSelectedRow();
        float soLuong;
        try {
            soLuong = Float.parseFloat(tblNguyenLieuTra.getValueAt(row, 3).toString());
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập lại số lượng");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lại số lượng");
            return false;
        }
        return true;
    }

    private boolean checkDonGia() {
        int row = tblNguyenLieu.getSelectedRow();
        float donGia;
        try {
            donGia = Float.parseFloat(tblNguyenLieu.getValueAt(row, 5).toString());
            if (donGia <= 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập lại đơn giá");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lại đơn giá");
        }
        return true;
    }

    public PageFormat getPageFormat(PrinterJob pj) {
        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();
        double middleHeight = 8.0;
        double headerHeight = 2.0;
        double footerHeight = 2.0;
        double width = convert_CM_To_PPI(8);      //printer know only point per inch.default value is 72ppi
        double height = convert_CM_To_PPI(headerHeight + middleHeight + footerHeight);
        paper.setSize(width, height);
        paper.setImageableArea(
                0,
                10,
                width,
                height - convert_CM_To_PPI(1)
        );   //define boarder size    after that print area width is about 180 points

        pf.setOrientation(PageFormat.PORTRAIT);           //select orientation portrait or landscape but for this time portrait
        pf.setPaper(paper);

        return pf;
    }

    protected static double convert_CM_To_PPI(double cm) {
        return toPPI(cm * 0.393600787);
    }

    protected static double toPPI(double inch) {
        return inch * 72d;
    }

    public class BillPrintable implements Printable {

        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            // CTHD
            int rowHD = tblHoaDon.getSelectedRow();
            lstChiTietHD = hoaDonChiTietService.getHoaDonChiTietByMaHoaDon(tblHoaDon.getValueAt(rowHD, 0).toString());
            lstHoaDon = hoaDonService.getAllHoaDon();
            HoaDonViewModel ctView = hoaDonService.getHoaDonByMa(tblHoaDon.getValueAt(rowHD, 0).toString());
            ctView = lstHoaDon.get(rowHD);
            String tenNv = ctView.getTenNhanVien();
            int soBan = ctView.getSoBan();
            String tenSP = null;
            int soLuong = 0;
            Locale locale = new Locale("vi", "VN");
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
            BigDecimal thanhTienSauKM = null;
            BigDecimal donGia = null;
            float sum = 0;
            int result = NO_SUCH_PAGE;
            if (pageIndex == 0) {
                Graphics2D g2d = (Graphics2D) graphics;
                double width = pageFormat.getImageableWidth();
                g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());

                try {
                    int y = 20;
                    int yShift = 10;
                    int headerRectHeight = 15;

                    g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
                    //  g2d.drawImage(icon.getImage(), 60, 20, 120, 30, null);
                    y += yShift + 30;
                    g2d.drawString("-------------------------------------", 12, y);
                    y += yShift;
                    g2d.drawString("               NHÓM 3        ", 12, y);
                    y += yShift;
                    g2d.drawString("      FPT POLYTECHNIC HÀ NỘI ", 12, y);
                    y += yShift;
                    g2d.drawString("   Địa chỉ: Nam Từ Liêm, Hà Nội ", 12, y);
                    y += yShift;
                    g2d.drawString("   www.facebook.com/berksbeans ", 12, y);
                    y += yShift;
                    g2d.drawString("        SĐT: +84357702364      ", 12, y);
                    y += yShift;
                    g2d.drawString("Thu ngân: " + tenNv + "", 12, y);
                    y += yShift;
                    g2d.drawString("Bàn:" + soBan + "", 12, y);
                    y += yShift;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                    Date currentDate = new Date();
                    String ngayIn = dateFormat.format(currentDate);
                    String gioIn = timeFormat.format(currentDate);
                    g2d.drawString("Ngày in: " + ngayIn, 12, y);
                    y += yShift;
                    g2d.drawString("Giờ in: " + gioIn, 12, y);
                    y += yShift;

                    g2d.drawString("-------------------------------------", 12, y);
                    y += headerRectHeight;
                    g2d.drawString(" Tên sản phẩm              Thành tiền   ", 10, y);
                    y += yShift;
                    g2d.drawString("-------------------------------------", 10, y);
                    y += headerRectHeight;

                    for (int s = 0; s < lstChiTietHD.size(); s++) {
                        tenSP = lstChiTietHD.get(s).getTenSanPham();
                        soLuong = lstChiTietHD.get(s).getSoLuongMua();
                        donGia = lstChiTietHD.get(s).getGiaBan();

                        thanhTienSauKM = lstChiTietHD.get(s).totol();

                        float thanhTienSauKm = thanhTienSauKM.floatValue();

                        sum += thanhTienSauKm;

                        g2d.drawString(" " + tenSP + "(" + soLuong + ")                            ", 10, y);
                        y += yShift;
                        g2d.drawString(" " + "Đơn giá: " + currencyFormat.format(donGia) + "   ", 10, y);

                        g2d.drawString("" + currencyFormat.format(thanhTienSauKM) + "", 160, y);
                        y += yShift;
                    }
                    g2d.drawString("-------------------------------------", 10, y);
                    y += yShift;
                    g2d.drawString(" Tổng tiền:                " + currencyFormat.format(sum) + "   ", 10, y);
                    y += yShift;
                    g2d.drawString("-------------------------------------", 10, y);
                    y += yShift;
                    g2d.drawString("-------------------------------------", 10, y);
                    g2d.drawString("*************************************", 10, y);
                    y += yShift;
                    g2d.drawString("     XIN CẢM ƠN VÀ HẸN GẶP LẠI            ", 10, y);
                    y += yShift;
                    g2d.drawString("*************************************", 10, y);
                    y += yShift;
                    g2d.drawString("          SOFTWARE BY:GROUP 3         ", 10, y);
                    y += yShift;
                    g2d.drawString("      CONTACT: fpoly@fpt.edu.vn       ", 10, y);
                    y += yShift;

                } catch (Exception e) {
                    e.printStackTrace();
                }

                result = PAGE_EXISTS;
            }
            return result;
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
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        panel2 = new fomVe.Panel();
        dateFrom = new com.toedter.calendar.JDateChooser();
        dateTo = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnLocHoaDon = new javax.swing.JButton();
        btnExportPdf = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        panel1 = new fomVe.Panel();
        lblTongTien = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblHoaDonChiTiet = new javax.swing.JTable();
        lblTotalMoney = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        panel3 = new fomVe.Panel();
        cboChiNhanhNhap = new javax.swing.JComboBox<>();
        cboNhaCungCapNhap = new javax.swing.JComboBox<>();
        cboNhanVienNhap = new javax.swing.JComboBox<>();
        dateNgayNhap = new com.toedter.calendar.JDateChooser();
        cboNguyenLieuNhap = new javax.swing.JComboBox<>();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblNguyenLieu = new javax.swing.JTable();
        btnTaoPhieuNhap = new javax.swing.JButton();
        btnHuyPhieuNhap = new javax.swing.JButton();
        lblCNNhap = new javax.swing.JLabel();
        txtMaPhieuNhap = new fomVe.JxText();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        panel5 = new fomVe.Panel();
        btnExport = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPhieuNhap = new javax.swing.JTable();
        jRadioButton1 = new javax.swing.JRadioButton();
        rdoHuyPhieuNhap = new javax.swing.JRadioButton();
        rdoPhieuNhapTam = new javax.swing.JRadioButton();
        rdoHoanThanhPhieuNhap = new javax.swing.JRadioButton();
        btnCapNhatPhieuNhap = new javax.swing.JButton();
        btnHoanThanhPhieuNhap = new javax.swing.JButton();
        panel31 = new fomVe.Panel3();
        btnTimKiemPhieuNhap = new javax.swing.JButton();
        txtTimKiemPhieuNhap = new fomVe.JxText();
        panel4 = new fomVe.Panel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblPhieuNhapChiTiet = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        panel6 = new fomVe.Panel();
        cboChiNhanhTra = new javax.swing.JComboBox<>();
        cboNhaCungCapTra = new javax.swing.JComboBox<>();
        cboNhanVienTra = new javax.swing.JComboBox<>();
        dateNgayTra = new com.toedter.calendar.JDateChooser();
        cboNguyenLieuTra = new javax.swing.JComboBox<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblNguyenLieuTra = new javax.swing.JTable();
        btnHuyPhieuTra = new javax.swing.JButton();
        btnTaoPhieuTra = new javax.swing.JButton();
        lblCNTra = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtMaPhieuTra = new fomVe.JxText();
        jPanel8 = new javax.swing.JPanel();
        panel7 = new fomVe.Panel();
        rdoTatCaPhieuTra = new javax.swing.JRadioButton();
        rdoHuyPhieuTra = new javax.swing.JRadioButton();
        rdoPhieuTraTam = new javax.swing.JRadioButton();
        rdoHoanThanhPhieuTra = new javax.swing.JRadioButton();
        btnHoanThanhPhieuTra = new javax.swing.JButton();
        btnCapNhatPhieuTra = new javax.swing.JButton();
        btnImport1 = new javax.swing.JButton();
        btnExport1 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblPhieuTra = new javax.swing.JTable();
        panel9 = new fomVe.Panel();
        lblTimKiemPhieuTra = new javax.swing.JLabel();
        txtTimKiemPhieuTra = new fomVe.JtextSearch();
        panel8 = new fomVe.Panel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblChiTietPhieuTra = new javax.swing.JTable();

        setBackground(new java.awt.Color(51, 51, 51));

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jTabbedPane1.setBackground(new java.awt.Color(51, 51, 51));
        jTabbedPane1.setForeground(new java.awt.Color(51, 255, 255));
        jTabbedPane1.setOpaque(true);

        jPanel2.setOpaque(false);

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel2.setBackground(new java.awt.Color(102, 102, 102));

        dateFrom.setBackground(new java.awt.Color(102, 102, 102));
        dateFrom.setDateFormatString("dd-MM-yyyy");
        dateFrom.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        dateTo.setBackground(new java.awt.Color(102, 102, 102));
        dateTo.setDateFormatString("dd-MM-yyyy");
        dateTo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Từ ngày :");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Đến ngày :");

        btnLocHoaDon.setBackground(new java.awt.Color(155, 49, 56));
        btnLocHoaDon.setFont(new java.awt.Font("Leelawadee UI Semilight", 0, 18)); // NOI18N
        btnLocHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnLocHoaDon.setText("Lọc");
        btnLocHoaDon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLocHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocHoaDonActionPerformed(evt);
            }
        });

        btnExportPdf.setBackground(new java.awt.Color(155, 49, 56));
        btnExportPdf.setFont(new java.awt.Font("Leelawadee UI Semilight", 0, 18)); // NOI18N
        btnExportPdf.setForeground(new java.awt.Color(255, 255, 255));
        btnExportPdf.setText("In hóa đơn");
        btnExportPdf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExportPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportPdfActionPerformed(evt);
            }
        });

        tblHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Ngày tạo", "Mã nhân viên", "Tên nhân viên", "Số bàn", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.setGridColor(new java.awt.Color(108, 83, 54));
        tblHoaDon.setSelectionBackground(new java.awt.Color(108, 83, 54));
        tblHoaDon.getTableHeader().setReorderingAllowed(false);
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblHoaDon);

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addComponent(dateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19)
                                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addGroup(panel2Layout.createSequentialGroup()
                                        .addComponent(dateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnLocHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExportPdf, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateFrom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLocHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(btnExportPdf, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 690));

        panel1.setBackground(new java.awt.Color(102, 102, 102));

        lblTongTien.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblTongTien.setForeground(new java.awt.Color(0, 255, 51));
        lblTongTien.setText("-");

        tblHoaDonChiTiet.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblHoaDonChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Số lượng mua", "Giá bán", "Thành tiền", "Thành tiền sau KM"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDonChiTiet.setSelectionBackground(new java.awt.Color(108, 83, 54));
        tblHoaDonChiTiet.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(tblHoaDonChiTiet);

        lblTotalMoney.setFont(new java.awt.Font("Segoe UI", 0, 19)); // NOI18N
        lblTotalMoney.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalMoney.setText("Tổng tiền :");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(lblTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jPanel4.add(panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(682, 0, 580, 690));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        jTabbedPane1.addTab("Hóa đơn", jPanel2);

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));

        panel3.setBackground(new java.awt.Color(255, 255, 255));

        cboChiNhanhNhap.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboChiNhanhNhap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cboChiNhanhNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChiNhanhNhapActionPerformed(evt);
            }
        });

        cboNhanVienNhap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        dateNgayNhap.setBackground(new java.awt.Color(255, 255, 255));
        dateNgayNhap.setDateFormatString("dd-MM-yyyy");

        cboNguyenLieuNhap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cboNguyenLieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNguyenLieuNhapActionPerformed(evt);
            }
        });

        tblNguyenLieu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Mã nguyên liệu", "Tên nguyên liệu", "Số lượng nhập", "Đơn vị tính", "Đơn giá", "Gỡ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNguyenLieu.setSelectionBackground(new java.awt.Color(108, 83, 54));
        tblNguyenLieu.getTableHeader().setReorderingAllowed(false);
        tblNguyenLieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNguyenLieuMouseClicked(evt);
            }
        });
        tblNguyenLieu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblNguyenLieuKeyReleased(evt);
            }
        });
        jScrollPane9.setViewportView(tblNguyenLieu);
        if (tblNguyenLieu.getColumnModel().getColumnCount() > 0) {
            tblNguyenLieu.getColumnModel().getColumn(0).setMinWidth(0);
            tblNguyenLieu.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblNguyenLieu.getColumnModel().getColumn(0).setMaxWidth(0);
            tblNguyenLieu.getColumnModel().getColumn(6).setPreferredWidth(5);
        }

        btnTaoPhieuNhap.setBackground(new java.awt.Color(155, 49, 56));
        btnTaoPhieuNhap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTaoPhieuNhap.setForeground(new java.awt.Color(255, 255, 255));
        btnTaoPhieuNhap.setText("Tạo phiếu nhập");
        btnTaoPhieuNhap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTaoPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoPhieuNhapActionPerformed(evt);
            }
        });

        btnHuyPhieuNhap.setBackground(new java.awt.Color(155, 49, 56));
        btnHuyPhieuNhap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnHuyPhieuNhap.setForeground(new java.awt.Color(255, 255, 255));
        btnHuyPhieuNhap.setText("Hủy");
        btnHuyPhieuNhap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHuyPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyPhieuNhapActionPerformed(evt);
            }
        });

        lblCNNhap.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCNNhap.setText("Chi nhánh");

        txtMaPhieuNhap.setPrompt("Mã phiếu nhập");

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Nhà cung cấp :");

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Mã nhân viên :");

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Ngày lập phiếu nhập :");

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Mã nguyên liệu nhập :");

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addComponent(btnTaoPhieuNhap)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHuyPhieuNhap))
                    .addComponent(txtMaPhieuNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cboNhaCungCapNhap, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(dateNgayNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE))
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboNhanVienNhap, 0, 210, Short.MAX_VALUE)
                            .addComponent(cboNguyenLieuNhap, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                        .addComponent(lblCNNhap)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboChiNhanhNhap, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboChiNhanhNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCNNhap))
                .addGap(43, 43, 43)
                .addComponent(txtMaPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboNhaCungCapNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboNhanVienNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dateNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboNguyenLieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTaoPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuyPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(166, 166, 166))
        );

        jPanel6.setBackground(new java.awt.Color(51, 51, 51));
        jPanel6.setLayout(new java.awt.GridLayout(2, 1, 0, 10));

        panel5.setBackground(new java.awt.Color(255, 255, 255));

        btnExport.setBackground(new java.awt.Color(10, 118, 64));
        btnExport.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnExport.setForeground(new java.awt.Color(255, 255, 255));
        btnExport.setText("Xuất excel");
        btnExport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        btnImport.setBackground(new java.awt.Color(10, 118, 64));
        btnImport.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnImport.setForeground(new java.awt.Color(255, 255, 255));
        btnImport.setText("Mở excel");
        btnImport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportActionPerformed(evt);
            }
        });

        tblPhieuNhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã phiếu nhập", "Mã nhà cung cấp", "Tên nhà cung cấp", "Mã nhân viên", "Tên nhân viên", "Ngày nhập", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPhieuNhap.setSelectionBackground(new java.awt.Color(108, 83, 54));
        tblPhieuNhap.getTableHeader().setReorderingAllowed(false);
        tblPhieuNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhieuNhapMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPhieuNhap);
        if (tblPhieuNhap.getColumnModel().getColumnCount() > 0) {
            tblPhieuNhap.getColumnModel().getColumn(0).setMinWidth(0);
            tblPhieuNhap.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblPhieuNhap.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        buttonGroup2.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jRadioButton1.setText("Tất cả");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdoHuyPhieuNhap);
        rdoHuyPhieuNhap.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoHuyPhieuNhap.setText("Đã hủy");
        rdoHuyPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoHuyPhieuNhapActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdoPhieuNhapTam);
        rdoPhieuNhapTam.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoPhieuNhapTam.setText("Phiếu tạm");
        rdoPhieuNhapTam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoPhieuNhapTamActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdoHoanThanhPhieuNhap);
        rdoHoanThanhPhieuNhap.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoHoanThanhPhieuNhap.setText("Đã hoàn thành");
        rdoHoanThanhPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoHoanThanhPhieuNhapActionPerformed(evt);
            }
        });

        btnCapNhatPhieuNhap.setBackground(new java.awt.Color(155, 49, 56));
        btnCapNhatPhieuNhap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCapNhatPhieuNhap.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhatPhieuNhap.setText("Cập nhật phiếu nhập");
        btnCapNhatPhieuNhap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCapNhatPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatPhieuNhapActionPerformed(evt);
            }
        });

        btnHoanThanhPhieuNhap.setBackground(new java.awt.Color(155, 49, 56));
        btnHoanThanhPhieuNhap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnHoanThanhPhieuNhap.setForeground(new java.awt.Color(255, 255, 255));
        btnHoanThanhPhieuNhap.setText("Hoàn thành");
        btnHoanThanhPhieuNhap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHoanThanhPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoanThanhPhieuNhapActionPerformed(evt);
            }
        });

        btnTimKiemPhieuNhap.setBackground(new java.awt.Color(155, 49, 56));
        btnTimKiemPhieuNhap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimKiemPhieuNhap.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiemPhieuNhap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTimKiemPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemPhieuNhapActionPerformed(evt);
            }
        });

        txtTimKiemPhieuNhap.setPrompt("Tìm kiếm phiếu nhập ...");

        javax.swing.GroupLayout panel31Layout = new javax.swing.GroupLayout(panel31);
        panel31.setLayout(panel31Layout);
        panel31Layout.setHorizontalGroup(
            panel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel31Layout.createSequentialGroup()
                .addComponent(btnTimKiemPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiemPhieuNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel31Layout.setVerticalGroup(
            panel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTimKiemPhieuNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
            .addComponent(btnTimKiemPhieuNhap, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panel5Layout = new javax.swing.GroupLayout(panel5);
        panel5.setLayout(panel5Layout);
        panel5Layout.setHorizontalGroup(
            panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(panel5Layout.createSequentialGroup()
                        .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel5Layout.createSequentialGroup()
                                .addComponent(jRadioButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdoHuyPhieuNhap))
                            .addComponent(btnExport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel5Layout.createSequentialGroup()
                                .addComponent(rdoPhieuNhapTam)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdoHoanThanhPhieuNhap))
                            .addGroup(panel5Layout.createSequentialGroup()
                                .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCapNhatPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnHoanThanhPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(136, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel5Layout.createSequentialGroup()
                        .addComponent(panel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14))))
        );
        panel5Layout.setVerticalGroup(
            panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(rdoHuyPhieuNhap)
                    .addComponent(rdoPhieuNhapTam)
                    .addComponent(rdoHoanThanhPhieuNhap))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhatPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHoanThanhPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel6.add(panel5);

        panel4.setBackground(new java.awt.Color(255, 255, 255));

        tblPhieuNhapChiTiet.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblPhieuNhapChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id phiếu nhập", "Id nguyên liệu", "Mã nguyên liệu", "Tên nguyên liệu", "Số lượng nhập", "Đơn vị tính", "Đơn giá", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPhieuNhapChiTiet.setSelectionBackground(new java.awt.Color(108, 83, 54));
        tblPhieuNhapChiTiet.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(tblPhieuNhapChiTiet);
        if (tblPhieuNhapChiTiet.getColumnModel().getColumnCount() > 0) {
            tblPhieuNhapChiTiet.getColumnModel().getColumn(0).setMinWidth(0);
            tblPhieuNhapChiTiet.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblPhieuNhapChiTiet.getColumnModel().getColumn(0).setMaxWidth(0);
            tblPhieuNhapChiTiet.getColumnModel().getColumn(1).setMinWidth(0);
            tblPhieuNhapChiTiet.getColumnModel().getColumn(1).setPreferredWidth(0);
            tblPhieuNhapChiTiet.getColumnModel().getColumn(1).setMaxWidth(0);
        }

        javax.swing.GroupLayout panel4Layout = new javax.swing.GroupLayout(panel4);
        panel4.setLayout(panel4Layout);
        panel4Layout.setHorizontalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 757, Short.MAX_VALUE)
        );
        panel4Layout.setVerticalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.add(panel4);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 757, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Phiếu nhập hàng", jPanel3);

        jPanel7.setBackground(new java.awt.Color(51, 51, 51));
        jPanel7.setOpaque(false);

        panel6.setBackground(new java.awt.Color(255, 255, 255));

        cboChiNhanhTra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboChiNhanhTra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cboChiNhanhTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChiNhanhTraActionPerformed(evt);
            }
        });

        cboNhaCungCapTra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboNhaCungCapTra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        cboNhanVienTra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboNhanVienTra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        dateNgayTra.setBackground(new java.awt.Color(255, 255, 255));
        dateNgayTra.setDateFormatString("dd-MM-yyyy");

        cboNguyenLieuTra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cboNguyenLieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNguyenLieuTraActionPerformed(evt);
            }
        });

        tblNguyenLieuTra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblNguyenLieuTra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IdNguyenLieu", "Mã nguyên liệu", "Tên nguyên liệu", "Số lượng trả", "Đơn vị tính", "Lý do", "Gỡ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNguyenLieuTra.setSelectionBackground(new java.awt.Color(108, 83, 54));
        tblNguyenLieuTra.getTableHeader().setReorderingAllowed(false);
        tblNguyenLieuTra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNguyenLieuTraMouseClicked(evt);
            }
        });
        tblNguyenLieuTra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblNguyenLieuTraKeyReleased(evt);
            }
        });
        jScrollPane6.setViewportView(tblNguyenLieuTra);
        if (tblNguyenLieuTra.getColumnModel().getColumnCount() > 0) {
            tblNguyenLieuTra.getColumnModel().getColumn(0).setMinWidth(0);
            tblNguyenLieuTra.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblNguyenLieuTra.getColumnModel().getColumn(0).setMaxWidth(0);
            tblNguyenLieuTra.getColumnModel().getColumn(6).setPreferredWidth(5);
        }

        btnHuyPhieuTra.setBackground(new java.awt.Color(155, 49, 56));
        btnHuyPhieuTra.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnHuyPhieuTra.setForeground(new java.awt.Color(255, 255, 255));
        btnHuyPhieuTra.setText("Hủy");
        btnHuyPhieuTra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHuyPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyPhieuTraActionPerformed(evt);
            }
        });

        btnTaoPhieuTra.setBackground(new java.awt.Color(155, 49, 56));
        btnTaoPhieuTra.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTaoPhieuTra.setForeground(new java.awt.Color(255, 255, 255));
        btnTaoPhieuTra.setText("Tạo phiếu trả");
        btnTaoPhieuTra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTaoPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoPhieuTraActionPerformed(evt);
            }
        });

        lblCNTra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCNTra.setForeground(new java.awt.Color(0, 0, 0));
        lblCNTra.setText("Chi nhánh");

        jLabel7.setText("Nhà cung cấp :");

        jLabel8.setText("Mã nhân viên :");

        jLabel9.setText("Ngày lập phiếu trả :");

        jLabel10.setText("Mã nguyên liệu trả :");

        txtMaPhieuTra.setPrompt("Mã phiếu trả");

        javax.swing.GroupLayout panel6Layout = new javax.swing.GroupLayout(panel6);
        panel6.setLayout(panel6Layout);
        panel6Layout.setHorizontalGroup(
            panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel6Layout.createSequentialGroup()
                .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnTaoPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHuyPhieuTra))
                    .addGroup(panel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel6Layout.createSequentialGroup()
                                .addComponent(lblCNTra)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboChiNhanhTra, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panel6Layout.createSequentialGroup()
                                .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(cboNhaCungCapTra, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(dateNgayTra, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel9))
                                .addGap(73, 73, 73)
                                .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboNhanVienTra, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboNguyenLieuTra, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(panel6Layout.createSequentialGroup()
                                        .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel8))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addComponent(txtMaPhieuTra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panel6Layout.setVerticalGroup(
            panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboChiNhanhTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCNTra))
                .addGap(53, 53, 53)
                .addComponent(txtMaPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboNhaCungCapTra, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboNhanVienTra, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboNguyenLieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTaoPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuyPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58))
        );

        jPanel8.setOpaque(false);

        panel7.setBackground(new java.awt.Color(102, 102, 102));

        buttonGroup1.add(rdoTatCaPhieuTra);
        rdoTatCaPhieuTra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoTatCaPhieuTra.setForeground(new java.awt.Color(255, 255, 255));
        rdoTatCaPhieuTra.setText("Tất cả");
        rdoTatCaPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoTatCaPhieuTraActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoHuyPhieuTra);
        rdoHuyPhieuTra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoHuyPhieuTra.setForeground(new java.awt.Color(255, 255, 255));
        rdoHuyPhieuTra.setText("Đã hủy");
        rdoHuyPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoHuyPhieuTraActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoPhieuTraTam);
        rdoPhieuTraTam.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoPhieuTraTam.setForeground(new java.awt.Color(255, 255, 255));
        rdoPhieuTraTam.setText("Phiếu tạm");
        rdoPhieuTraTam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoPhieuTraTamActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoHoanThanhPhieuTra);
        rdoHoanThanhPhieuTra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoHoanThanhPhieuTra.setForeground(new java.awt.Color(255, 255, 255));
        rdoHoanThanhPhieuTra.setText("Đã hoàn thành");
        rdoHoanThanhPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoHoanThanhPhieuTraActionPerformed(evt);
            }
        });

        btnHoanThanhPhieuTra.setBackground(new java.awt.Color(155, 49, 56));
        btnHoanThanhPhieuTra.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnHoanThanhPhieuTra.setForeground(new java.awt.Color(255, 255, 255));
        btnHoanThanhPhieuTra.setText("Hoàn thành");
        btnHoanThanhPhieuTra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHoanThanhPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoanThanhPhieuTraActionPerformed(evt);
            }
        });

        btnCapNhatPhieuTra.setBackground(new java.awt.Color(155, 49, 56));
        btnCapNhatPhieuTra.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCapNhatPhieuTra.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhatPhieuTra.setText("Cập nhật phiếu trả");
        btnCapNhatPhieuTra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCapNhatPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatPhieuTraActionPerformed(evt);
            }
        });

        btnImport1.setBackground(new java.awt.Color(10, 118, 64));
        btnImport1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnImport1.setForeground(new java.awt.Color(255, 255, 255));
        btnImport1.setText("Mở excel");
        btnImport1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImport1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImport1ActionPerformed(evt);
            }
        });

        btnExport1.setBackground(new java.awt.Color(10, 118, 64));
        btnExport1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnExport1.setForeground(new java.awt.Color(255, 255, 255));
        btnExport1.setText("Xuất excel");
        btnExport1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExport1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExport1ActionPerformed(evt);
            }
        });

        tblPhieuTra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblPhieuTra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã phiếu trả", "Mã nhà cung cấp", "Nhà cung cấp", "Mã nhân viên", "Tên nhân viên", "Ngày trả", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPhieuTra.setSelectionBackground(new java.awt.Color(108, 83, 54));
        tblPhieuTra.getTableHeader().setReorderingAllowed(false);
        tblPhieuTra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhieuTraMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblPhieuTra);

        panel9.setBackground(new java.awt.Color(255, 255, 255));

        lblTimKiemPhieuTra.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTimKiemPhieuTra.setText(" ");
        lblTimKiemPhieuTra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTimKiemPhieuTraMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblTimKiemPhieuTraMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblTimKiemPhieuTraMouseExited(evt);
            }
        });

        txtTimKiemPhieuTra.setBorder(null);

        javax.swing.GroupLayout panel9Layout = new javax.swing.GroupLayout(panel9);
        panel9.setLayout(panel9Layout);
        panel9Layout.setHorizontalGroup(
            panel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTimKiemPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiemPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel9Layout.setVerticalGroup(
            panel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTimKiemPhieuTra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiemPhieuTra, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel7Layout = new javax.swing.GroupLayout(panel7);
        panel7.setLayout(panel7Layout);
        panel7Layout.setHorizontalGroup(
            panel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel7Layout.createSequentialGroup()
                        .addComponent(rdoTatCaPhieuTra)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoHuyPhieuTra)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoPhieuTraTam)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoHoanThanhPhieuTra)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panel7Layout.createSequentialGroup()
                        .addGroup(panel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panel7Layout.createSequentialGroup()
                                .addComponent(btnExport1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnImport1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCapNhatPhieuTra)
                                .addGap(11, 11, 11)
                                .addComponent(btnHoanThanhPhieuTra))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap())))
        );
        panel7Layout.setVerticalGroup(
            panel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoTatCaPhieuTra)
                    .addComponent(rdoHuyPhieuTra)
                    .addComponent(rdoPhieuTraTam)
                    .addComponent(rdoHoanThanhPhieuTra))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnHoanThanhPhieuTra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExport1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhatPhieuTra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(btnImport1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel8.setBackground(new java.awt.Color(102, 102, 102));

        tblChiTietPhieuTra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblChiTietPhieuTra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "IdPhieuTra", "IdNguyenLieu", "Mã nguyên liệu", "Tên nguyên liệu", "Số lượng trả", "Đơn vị tính", "Đơn giá", "Thành tiền", "Lý do"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblChiTietPhieuTra.setSelectionBackground(new java.awt.Color(108, 83, 54));
        tblChiTietPhieuTra.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tblChiTietPhieuTra);
        if (tblChiTietPhieuTra.getColumnModel().getColumnCount() > 0) {
            tblChiTietPhieuTra.getColumnModel().getColumn(0).setMinWidth(0);
            tblChiTietPhieuTra.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblChiTietPhieuTra.getColumnModel().getColumn(0).setMaxWidth(0);
            tblChiTietPhieuTra.getColumnModel().getColumn(1).setMinWidth(0);
            tblChiTietPhieuTra.getColumnModel().getColumn(1).setPreferredWidth(0);
            tblChiTietPhieuTra.getColumnModel().getColumn(1).setMaxWidth(0);
        }

        javax.swing.GroupLayout panel8Layout = new javax.swing.GroupLayout(panel8);
        panel8.setLayout(panel8Layout);
        panel8Layout.setHorizontalGroup(
            panel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );
        panel8Layout.setVerticalGroup(
            panel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(panel7, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Phiếu trả hàng", jPanel7);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1282, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 763, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnLocHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocHoaDonActionPerformed
        // TODO add your handling code here:
        if (dateFrom.getDate() == null || dateTo.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Sai định dạng ngày tháng năm");
        } else {
            List<HoaDonViewModel> lstSearchHoaDon = hoaDonService.locHoaDon(dateFrom.getDate(), dateTo.getDate());
            loadTableHoaDon(lstSearchHoaDon);
        }
    }//GEN-LAST:event_btnLocHoaDonActionPerformed

    private void btnExportPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportPdfActionPerformed
        int row = tblHoaDon.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn hóa đơn");
        } else {
            PrinterJob pj = PrinterJob.getPrinterJob();
            pj.setPrintable(new BillPrintable(), getPageFormat(pj));
            try {
                pj.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnExportPdfActionPerformed
    private void loadDaHoanThanh(Set<PhieuNhapViewModel> lstPhieuNhap) {
        modelPhieuNhap = (DefaultTableModel) tblPhieuNhap.getModel();
        modelPhieuNhap.setRowCount(0);
        for (PhieuNhapViewModel x : lstPhieuNhap) {
            if (x.getTrangThai() == 3) {
                modelPhieuNhap.addRow(x.getDataPhieuNhapView());
            }
        }
    }

    private void loadHuyPhieuNhap(Set<PhieuNhapViewModel> lstPhieuNhap) {
        modelPhieuNhap = (DefaultTableModel) tblPhieuNhap.getModel();
        modelPhieuNhap.setRowCount(0);
        for (PhieuNhapViewModel x : lstPhieuNhap) {
            if (x.getTrangThai() == 0) {
                modelPhieuNhap.addRow(x.getDataPhieuNhapView());
            }
        }

    }

    private void loadPhieuTam(Set<PhieuNhapViewModel> lstPhieuNhap) {
        modelPhieuNhap = (DefaultTableModel) tblPhieuNhap.getModel();
        modelPhieuNhap.setRowCount(0);
        for (PhieuNhapViewModel x : lstPhieuNhap) {
            if (x.getTrangThai() == 1) {
                modelPhieuNhap.addRow(x.getDataPhieuNhapView());
            }
        }
    }

    private void loadTablePhieuTraTam(Set<PhieuTraViewModel> lstPhieuTra) {
        modelPhieuTra = (DefaultTableModel) tblPhieuTra.getModel();
        modelPhieuTra.setRowCount(0);
        for (PhieuTraViewModel x : lstPhieuTra) {
            if (x.getTrangThai() == 1) {
                modelPhieuTra.addRow(x.getPhieuTrahangView());
            }
        }
    }

    private void loadTablePhieuTraHoanThanh(Set<PhieuTraViewModel> lstPhieuTra) {
        modelPhieuTra = (DefaultTableModel) tblPhieuTra.getModel();
        modelPhieuTra.setRowCount(0);
        for (PhieuTraViewModel x : lstPhieuTra) {
            if (x.getTrangThai() == 3) {
                modelPhieuTra.addRow(x.getPhieuTrahangView());
            }
        }
    }

    private void loadTableHuyPhieuTra(Set<PhieuTraViewModel> lstPhieuTra) {
        modelPhieuTra = (DefaultTableModel) tblPhieuTra.getModel();
        modelPhieuTra.setRowCount(0);
        for (PhieuTraViewModel x : lstPhieuTra) {
            if (x.getTrangThai() == 0) {
                modelPhieuTra.addRow(x.getPhieuTrahangView());
            }
        }
    }

    private void fillDataPhieuNhap(int index) {
        int row = tblPhieuNhap.getSelectedRow();
        if (tblPhieuNhap.getRowCount() >= 0) {
            txtMaPhieuNhap.setText((String) tblPhieuNhap.getValueAt(index, 1).toString());
            for (int i = 0; i < phieuNhapSevice.getAllNhaCungCap().size(); i++) {
                if (phieuNhapSevice.getAllNhaCungCap().get(i).getMa().equals(tblPhieuNhap.getValueAt(index, 2))) {
                    cboNhaCungCapNhap.setSelectedIndex(i);
                }
            }
            cboNhanVienNhap.setSelectedItem(findbyName(tblPhieuNhap.getValueAt(index, 4).toString()));
            dateNgayNhap.setDate((Date) tblPhieuNhap.getValueAt(index, 6));
        }
    }

    private void showNguyenLieuPhieuTraByPhieuTra(String idPt) {
        modelChiTietPhieuTra = (DefaultTableModel) tblNguyenLieuTra.getModel();
        modelChiTietPhieuTra.setRowCount(0);
        Set<ChiTietPhieuTraViewModel> chiTietView = phieuTraService.getPhieuTraByChiTietPhieuTra(idPt);
        for (ChiTietPhieuTraViewModel ctView : chiTietView) {
            modelChiTietPhieuTra.addRow(ctView.getNguyenLieuByPhieuTrahangView());
        }
    }

    private void showChiTietPhieuTraByPhieuTra(String idPhieuTra) {
        modelChiTietPhieuTra = (DefaultTableModel) tblChiTietPhieuTra.getModel();
        modelChiTietPhieuTra.setRowCount(0);
        Set<ChiTietPhieuTraViewModel> chiTietView = phieuTraService.getPhieuTraByChiTietPhieuTra(idPhieuTra);
        for (ChiTietPhieuTraViewModel ctView : chiTietView) {
            modelChiTietPhieuTra.addRow(ctView.getChiTietPhieuTrahangView());
        }
    }

    private void clearPhieuTra() {
        txtMaPhieuTra.setText("");
        cboNhaCungCapTra.setSelectedIndex(0);
        cboNhanVienTra.setSelectedIndex(0);
        cboNguyenLieuTra.setSelectedIndex(0);
        dateNgayTra.setDate(null);
        modelNguyenLieuTra.setRowCount(0);
        modelChiTietPhieuTra.setRowCount(0);
        txtTimKiemPhieuTra.setText("");
    }

    private void loadTableHoaDonChiTiet(List<ChitietHoaDonViewModel> lstChiTietHD) {
        modelHDCT = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        modelHDCT.setRowCount(0);
        for (ChitietHoaDonViewModel x : lstChiTietHD) {
            modelHDCT.addRow(x.getDataHoaDonChiTietView());
        }
    }
    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        int row = tblHoaDon.getSelectedRow();
        lstChiTietHD = hoaDonChiTietService.getHoaDonChiTietByMaHoaDon(tblHoaDon.getValueAt(row, 0).toString());
        loadTableHoaDonChiTiet(lstChiTietHD);
        float tongTien = 0;

        BigDecimal thanhTienSauKM = null;
        for (int i = 0; i < lstChiTietHD.size(); i++) {
            thanhTienSauKM = lstChiTietHD.get(i).getThanhTienSauKM();
            float thanhTienSauKm = thanhTienSauKM.floatValue();
            tongTien += thanhTienSauKm;
        }
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        lblTongTien.setText(currencyFormat.format(thanhTienSauKM));
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void cboChiNhanhNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChiNhanhNhapActionPerformed
        // TODO add your handling code here:
        modelNguyenLieu.setRowCount(0);
        txtMaPhieuNhap.setText("");
        dateNgayNhap.setDate(null);
        comboNguyenLieu = (DefaultComboBoxModel) new DefaultComboBoxModel<>(
                phieuNhapSevice.getAllNguyenLieuByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId()).toArray());
        cboNguyenLieuNhap.setModel((DefaultComboBoxModel) comboNguyenLieu);
        comboNhanVien = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNhanVienByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId()).toArray());
        cboNhanVienNhap.setModel((DefaultComboBoxModel) comboNhanVien);
        loadTablePhieuNhap(phieuNhapSevice.getAllPhieuNhapByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId()));
    }//GEN-LAST:event_cboChiNhanhNhapActionPerformed

    private void cboNguyenLieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNguyenLieuNhapActionPerformed
        int count = 0;
        if (cboNguyenLieuNhap.getItemCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Chưa có nguyên liệu");
        } else {
            NguyenLieuViewModel_Hoang nguyenLieu = (NguyenLieuViewModel_Hoang) comboNguyenLieu.getSelectedItem();
            for (int i = 0; i < tblNguyenLieu.getRowCount(); i++) {
                if (nguyenLieu.getId().equalsIgnoreCase(tblNguyenLieu.getValueAt(i, 0).toString())) {
                    count++;
                    break;
                }
            }
            if (count == 0) {
                modelNguyenLieu.addRow(new Object[]{nguyenLieu.getId(), nguyenLieu.getMa(), nguyenLieu.getTen(), 1, nguyenLieu.getDonVitinh(), 1});
            }
        }
    }//GEN-LAST:event_cboNguyenLieuNhapActionPerformed

    private void tblNguyenLieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNguyenLieuMouseClicked
        int row = tblNguyenLieu.getSelectedRow();
        int column = tblNguyenLieu.getSelectedColumn();
        if (column == 6) {
            if (tblNguyenLieu.getSelectedColumn() == 6) {
                if (tblNguyenLieu.getValueAt(row, 6).equals(true)) {
                    modelNguyenLieu.removeRow(row);
                }
            }
        }
    }//GEN-LAST:event_tblNguyenLieuMouseClicked

    private void tblNguyenLieuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblNguyenLieuKeyReleased
        // TODO add your handling code here:
        int row = tblNguyenLieu.getSelectedRow();
        if (!checkSoluong()) {
            tblNguyenLieu.setValueAt(1, row, 3);

        } else if (!checkDonGia()) {
            tblNguyenLieu.setValueAt(1, row, 5);
        }
    }//GEN-LAST:event_tblNguyenLieuKeyReleased

    private void btnTaoPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoPhieuNhapActionPerformed

        if (checkEmpty() && !checkMaPhieuNhap(txtMaPhieuNhap.getText())) {
            String idPhieuNhap = null;
            idPhieuNhap = phieuNhapSevice.insertPhieuNhap(txtMaPhieuNhap.getText(), ((NhaCungCapViewModel_Hoang) cboNhaCungCapNhap.getSelectedItem()).getId(), ((NhanVienViewModel_Hoang) cboNhanVienNhap.getSelectedItem()).getId(),
                    dateNgayNhap.getDate(), 1);
            for (int i = 0; i < tblNguyenLieu.getRowCount(); i++) {
                phieuNhapSevice.insertCTPhieuNhap(idPhieuNhap, tblNguyenLieu.getValueAt(i, 0).toString(), Float.parseFloat(tblNguyenLieu.getValueAt(i, 3).toString()), Float.parseFloat(tblNguyenLieu.getValueAt(i, 5).toString()));
            }

            JOptionPane.showMessageDialog(this, "Tạo phiếu nhập thành công");
            loadAll((((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId()));
            rdoPhieuNhapTamActionPerformed(evt);
            rdoPhieuNhapTam.setSelected(true);
            clearFormPhieuNhap();
        }
    }//GEN-LAST:event_btnTaoPhieuNhapActionPerformed

    private void btnHuyPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyPhieuNhapActionPerformed
        int row = tblPhieuNhap.getSelectedRow();
        lstPhieuNhap = phieuNhapSevice.getAllPhieuNhapByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId());
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn phiếu nhập");
        } else {
            int chon = JOptionPane.showConfirmDialog(this, "Xác nhận hủy phiếu", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (chon == JOptionPane.YES_OPTION) {
                if (tblPhieuNhap.getValueAt(row, 7).toString().equalsIgnoreCase("Phiếu tạm")) {
                    JOptionPane.showMessageDialog(this, phieuNhapSevice.updateTrangThaiPhieuNhap(tblPhieuNhap.getValueAt(row, 1).toString(), 0));
                    loadAll((((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId()));
                    rdoHuyPhieuNhap.setSelected(true);
                    loadHuyPhieuNhap(lstPhieuNhap);
                    clearFormPhieuNhap();
                } else {
                    JOptionPane.showMessageDialog(this, "Phiếu đã nhập không thể hủy");
                }
            }
        }
    }//GEN-LAST:event_btnHuyPhieuNhapActionPerformed

    private void btnTimKiemPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemPhieuNhapActionPerformed
        if (txtTimKiemPhieuNhap.getText().isEmpty()) {
            loadTablePhieuNhap(phieuNhapSevice.getAllPhieuNhapByChiNhanh(((ChiNhanhViewModel_Hoang) cboChiNhanhNhap.getSelectedItem()).getId()));
        } else {
            Set<PhieuNhapViewModel> lstSearch = phieuNhapSevice.searchPhieuNhap(txtTimKiemPhieuNhap.getText());
            if (lstSearch.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tồn tại phiếu nhập");
            } else {
                loadTablePhieuNhap(lstSearch);
            }
        }
    }//GEN-LAST:event_btnTimKiemPhieuNhapActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        // TODO add your handling code here:

        FileOutputStream excelFOU = null;
        BufferedOutputStream excelBOU = null;
        XSSFWorkbook excelJTableExporter = null;
        JFileChooser excelFileChooser = new JFileChooser("C:\\Users\\ASUS\\Desktop\\TestExcel");
        excelFileChooser.setDialogTitle("Save As");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
        excelFileChooser.setFileFilter(fnef);
        int excelChosser = excelFileChooser.showSaveDialog(null);
        if (excelChosser == JFileChooser.APPROVE_OPTION) {

            try {
                excelJTableExporter = new XSSFWorkbook();
                XSSFSheet excelSheet = excelJTableExporter.createSheet("JTable Sheet");
                for (int i = 0; i < modelPhieuNhap.getRowCount(); i++) {
                    XSSFRow excelRow = excelSheet.createRow(i);
                    for (int j = 0; j < modelPhieuNhap.getColumnCount(); j++) {
                        XSSFCell excelCell = excelRow.createCell(j);
                        excelCell.setCellValue(modelPhieuNhap.getValueAt(i, j).toString());
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
    }//GEN-LAST:event_btnExportActionPerformed

    private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportActionPerformed
        modelPhieuNhap.setRowCount(0);
        File exelFile;
        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelJTableImport = null;

        String defauCurrentDirectoryPath = "C:\\Users\\ASUS\\Desktop\\TestExcel";
        JFileChooser exportFlieChooser = new JFileChooser(defauCurrentDirectoryPath);

        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
        exportFlieChooser.setFileFilter(fnef);
        exportFlieChooser.setDialogTitle("Select Excel file");
        int excelChooser = exportFlieChooser.showOpenDialog(null);

        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            try {
                exelFile = exportFlieChooser.getSelectedFile();
                excelFIS = new FileInputStream(exelFile);
                excelBIS = new BufferedInputStream(excelFIS);
                excelJTableImport = new XSSFWorkbook(excelBIS);
                XSSFSheet excelSheet = excelJTableImport.getSheetAt(0);

                for (int row = 0; row < excelSheet.getLastRowNum(); row++) {
                    XSSFRow excelRow = excelSheet.getRow(row);

                    XSSFCell excelMaPhieu = excelRow.getCell(0);
                    XSSFCell excelMaNguyenLieu = excelRow.getCell(1);
                    XSSFCell excelTenNguyenLieu = excelRow.getCell(2);
                    XSSFCell excelMaNcc = excelRow.getCell(3);
                    XSSFCell excelTenNCC = excelRow.getCell(4);
                    XSSFCell excelMaNV = excelRow.getCell(5);
                    XSSFCell excelTenNV = excelRow.getCell(6);
                    XSSFCell excelNgayNhap = excelRow.getCell(7);
                    XSSFCell excelSoLuong = excelRow.getCell(8);
                    XSSFCell excelDonGia = excelRow.getCell(9);
                    XSSFCell excelThanhTien = excelRow.getCell(10);
                    XSSFCell excelTrangThai = excelRow.getCell(11);
                    modelPhieuNhap.addRow(new Object[]{excelMaPhieu, excelMaNguyenLieu, excelTenNguyenLieu, excelMaNcc, excelTenNCC, excelMaNV, excelTenNV,
                        excelNgayNhap, excelSoLuong, excelDonGia, excelThanhTien, excelTrangThai});
                }
                JOptionPane.showMessageDialog(null, "Thành công");
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } finally {
                try {
                    if (excelFIS != null) {
                        excelFIS.close();
                    }
                    if (excelBIS != null) {
                        excelBIS.close();
                    }
                    if (excelJTableImport != null) {
                        excelJTableImport.close();
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }

            }
        }
    }//GEN-LAST:event_btnImportActionPerformed

    private void tblPhieuNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuNhapMouseClicked
        // TODO add your handling code here:
        int index = tblPhieuNhap.getSelectedRow();
        showChiTietByPhieuNhap(tblPhieuNhap.getValueAt(index, 0).toString());
        showNguyenLieuByPhieuNhap(tblPhieuNhap.getValueAt(index, 0).toString());
        fillDataPhieuNhap(index);
    }//GEN-LAST:event_tblPhieuNhapMouseClicked

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        loadTablePhieuNhap(lstPhieuNhap);
        clearFormPhieuNhap();
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void rdoHuyPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoHuyPhieuNhapActionPerformed
        loadHuyPhieuNhap(lstPhieuNhap);
        clearFormPhieuNhap();
    }//GEN-LAST:event_rdoHuyPhieuNhapActionPerformed

    private void rdoPhieuNhapTamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoPhieuNhapTamActionPerformed
        loadPhieuTam(lstPhieuNhap);
        clearFormPhieuNhap();
    }//GEN-LAST:event_rdoPhieuNhapTamActionPerformed

    private void rdoHoanThanhPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoHoanThanhPhieuNhapActionPerformed
        loadDaHoanThanh(lstPhieuNhap);
        clearFormPhieuNhap();
    }//GEN-LAST:event_rdoHoanThanhPhieuNhapActionPerformed

    private void btnCapNhatPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatPhieuNhapActionPerformed
        // TODO add your handling code here:
        if (checkEmpty()) {
            int row = tblPhieuNhap.getSelectedRow();
            if (tblPhieuNhap.getValueAt(row, 3) != null && tblPhieuNhap.getValueAt(row, 5) != null && tblPhieuNhap.getValueAt(row, 6) != null) {
                if (txtMaPhieuNhap.getText().equalsIgnoreCase(tblPhieuNhap.getValueAt(row, 1).toString())) {
                    phieuNhapSevice.updatePhieuNhap(tblPhieuNhap.getValueAt(row, 0).toString(), tblPhieuNhap.getValueAt(row, 1).toString(), ((NhaCungCapViewModel_Hoang) cboNhaCungCapNhap.getSelectedItem()).getId(), ((NhanVienViewModel_Hoang) cboNhanVienNhap.getSelectedItem()).getId(),
                            dateNgayNhap.getDate());
                    //            phieuNhapSevice.deleteChiTietPnbyidPn(tblPhieuNhap.getValueAt(row, 0).toString());
                    //            for (int i = 0; i < tblNguyenLieu.getRowCount(); i++) {
                    //                phieuNhapSevice.insertCTPhieuNhap(tblPhieuNhap.getValueAt(row, 0).toString(), tblNguyenLieu.getValueAt(i, 0).toString(), Float.parseFloat(tblNguyenLieu.getValueAt(i, 3).toString()), Float.parseFloat(tblNguyenLieu.getValueAt(row, 5).toString()));
                    //            }
                    clearFormPhieuNhap();
                    loadAll(((ChiNhanhViewModel_Hoang) cboChiNhanhNhap.getSelectedItem()).getId());
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                } else {
                    JOptionPane.showMessageDialog(this, "Không được sửa mã phiếu nhập");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Dữ liệu trống");
            }
        }
    }//GEN-LAST:event_btnCapNhatPhieuNhapActionPerformed

    private void btnHoanThanhPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoanThanhPhieuNhapActionPerformed
        int row = tblPhieuNhap.getSelectedRow();
        lstPhieuNhap = phieuNhapSevice.getAllPhieuNhapByChiNhanh(((ChiNhanhViewModel_Hoang) cboChiNhanhNhap.getSelectedItem()).getId());
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn phiếu nhập");
        } else {
            int chon = JOptionPane.showConfirmDialog(this, "Xác nhận hoàn thành phiếu", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (chon == JOptionPane.YES_OPTION) {
                if (tblPhieuNhap.getValueAt(row, 7).toString().equalsIgnoreCase("Phiếu tạm")) {
                    for (int i = 0; i < tblNguyenLieu.getRowCount(); i++) {
                        phieuNhapSevice.updateSoluongNguyenLieu(tblNguyenLieu.getValueAt(i, 0).toString(), Float.parseFloat(tblNguyenLieu.getValueAt(i, 3).toString()));
                    }
                    JOptionPane.showMessageDialog(this, "Đã hoàn thành phiếu và cập nhật số lượng trong kho");
                    JOptionPane.showMessageDialog(this, phieuNhapSevice.updateTrangThaiPhieuNhap(tblPhieuNhap.getValueAt(row, 1).toString(), 3));
                    loadAll(((ChiNhanhViewModel_Hoang) comboChiNhanh.getSelectedItem()).getId());
                    loadDaHoanThanh(lstPhieuNhap);
                    rdoHoanThanhPhieuNhap.setSelected(true);
                    clearFormPhieuNhap();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể hoàn thành phiếu");
                }
            }
        }
    }//GEN-LAST:event_btnHoanThanhPhieuNhapActionPerformed

    private void cboChiNhanhTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChiNhanhTraActionPerformed
        // TODO add your handling code here:
        modelNguyenLieuTra.setRowCount(0);
        txtMaPhieuTra.setText("");
        dateNgayTra.setDate(null);
        comboNguyenLieuTra = (DefaultComboBoxModel) new DefaultComboBoxModel<>(
                phieuNhapSevice.getAllNguyenLieuByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanhTra.getSelectedItem()).getId()).toArray());
        cboNguyenLieuTra.setModel((DefaultComboBoxModel) comboNguyenLieuTra);
        comboNhanVienTra = (DefaultComboBoxModel) new DefaultComboBoxModel<>(phieuNhapSevice.getAllNhanVienByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanhTra.getSelectedItem()).getId()).toArray());
        cboNhanVienTra.setModel((DefaultComboBoxModel) comboNhanVienTra);
        loadTablePhieuTra(phieuTraService.getAllPhieuTraByChiNhanh(((ChiNhanhViewModel_Hoang) comboChiNhanhTra.getSelectedItem()).getId()));
    }//GEN-LAST:event_cboChiNhanhTraActionPerformed

    private void cboNguyenLieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNguyenLieuTraActionPerformed
        int count = 0;
        if (cboNguyenLieuTra.getItemCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Chưa có nguyên liệu");
        } else {
            NguyenLieuViewModel_Hoang nguyenLieu = (NguyenLieuViewModel_Hoang) comboNguyenLieuTra.getSelectedItem();
            for (int i = 0; i < tblNguyenLieuTra.getRowCount(); i++) {
                if (nguyenLieu.getId().equalsIgnoreCase(tblNguyenLieuTra.getValueAt(i, 0).toString())) {
                    count++;
                    break;
                }
            }
            if (count == 0) {
                modelNguyenLieuTra.addRow(new Object[]{nguyenLieu.getId(), nguyenLieu.getMa(), nguyenLieu.getTen(), 1, nguyenLieu.getDonVitinh(), "Nhập lý do"});
            }
        }
    }//GEN-LAST:event_cboNguyenLieuTraActionPerformed

    private void tblNguyenLieuTraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNguyenLieuTraMouseClicked
        // TODO add your handling code here:
        int row = tblNguyenLieuTra.getSelectedRow();
        int column = tblNguyenLieuTra.getSelectedColumn();
        if (column == 6) {
            if (tblNguyenLieuTra.getSelectedColumn() == 6) {
                if (tblNguyenLieuTra.getValueAt(row, 6).equals(true)) {
                    modelNguyenLieuTra.removeRow(row);
                }
            }
        }
    }//GEN-LAST:event_tblNguyenLieuTraMouseClicked

    private void tblNguyenLieuTraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblNguyenLieuTraKeyReleased
        // TODO add your handling code here:
        int row = tblNguyenLieuTra.getSelectedRow();
        if (!checkSoluongTra()) {
            tblNguyenLieuTra.setValueAt(1, row, 3);
        }
    }//GEN-LAST:event_tblNguyenLieuTraKeyReleased

    private void btnHuyPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyPhieuTraActionPerformed
        int row = tblPhieuTra.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn phiếu trả");
        } else {
            if (tblPhieuTra.getValueAt(row, 7).toString().equalsIgnoreCase("Phiếu tạm")) {
                int chon = JOptionPane.showConfirmDialog(this, "Xác nhận hủy phiếu", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (chon == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(this, phieuTraService.updateTrangThaiPhieuTra(tblPhieuTra.getValueAt(row, 1).toString(), 0));
                    lstPhieuTra = phieuTraService.getAllPhieuTraByChiNhanh(((ChiNhanhViewModel_Hoang) cboChiNhanhTra.getSelectedItem()).getId());
                    loadAllTra(((ChiNhanhViewModel_Hoang) cboChiNhanhTra.getSelectedItem()).getId());
                    loadTableHuyPhieuTra(lstPhieuTra);
                    rdoHuyPhieuTra.setSelected(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không thể hủy");
            }
        }
    }//GEN-LAST:event_btnHuyPhieuTraActionPerformed

    private void btnTaoPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoPhieuTraActionPerformed
        // TODO add your handling code here:
        if (checkEmptyPhieuTra() && !checkMaPhieuTra(txtMaPhieuTra.getText())) {
            String idPhieuTra = null;
            idPhieuTra = phieuTraService.insertPhieuTra(txtMaPhieuTra.getText(), ((NhaCungCapViewModel_Hoang) cboNhaCungCapTra.getSelectedItem()).getId(), ((NhanVienViewModel_Hoang) cboNhanVienTra.getSelectedItem()).getId(),
                    dateNgayTra.getDate(), 1);
            for (int i = 0; i < tblNguyenLieuTra.getRowCount(); i++) {
                phieuTraService.insertCTPhieuTra(idPhieuTra, tblNguyenLieuTra.getValueAt(i, 0).toString(), Float.parseFloat(tblNguyenLieuTra.getValueAt(i, 3).toString()), tblNguyenLieuTra.getValueAt(i, 5).toString());
            }
            JOptionPane.showMessageDialog(this, "Thêm phiếu trả thành công");
            loadAllTra(((ChiNhanhViewModel_Hoang) comboChiNhanhTra.getSelectedItem()).getId());
            rdoPhieuTraTamActionPerformed(evt);
            rdoPhieuTraTam.setSelected(true);
            clearPhieuTra();
        }
    }//GEN-LAST:event_btnTaoPhieuTraActionPerformed

    private void rdoTatCaPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoTatCaPhieuTraActionPerformed
        loadTablePhieuTra(lstPhieuTra);
        clearPhieuTra();
    }//GEN-LAST:event_rdoTatCaPhieuTraActionPerformed

    private void rdoHuyPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoHuyPhieuTraActionPerformed
        loadTableHuyPhieuTra(lstPhieuTra);
    }//GEN-LAST:event_rdoHuyPhieuTraActionPerformed

    private void rdoPhieuTraTamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoPhieuTraTamActionPerformed
        loadTablePhieuTraTam(lstPhieuTra);
        clearPhieuTra();
    }//GEN-LAST:event_rdoPhieuTraTamActionPerformed

    private void rdoHoanThanhPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoHoanThanhPhieuTraActionPerformed

    }//GEN-LAST:event_rdoHoanThanhPhieuTraActionPerformed

    private void btnHoanThanhPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoanThanhPhieuTraActionPerformed
        int row = tblPhieuTra.getSelectedRow();
        lstPhieuTra = phieuTraService.getAllPhieuTraByChiNhanh(((ChiNhanhViewModel_Hoang) cboChiNhanhTra.getSelectedItem()).getId());
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn phiếu trả");
        } else {
            if (tblPhieuTra.getValueAt(row, 7).toString().equalsIgnoreCase("Phiếu tạm")) {
                int chon = JOptionPane.showConfirmDialog(this, "Xác nhận hoàn thành phiếu", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (chon == JOptionPane.YES_OPTION) {
                    for (int i = 0; i < tblNguyenLieuTra.getRowCount(); i++) {
                        phieuTraService.updateSoluongNguyenLieuTra(tblNguyenLieuTra.getValueAt(i, 0).toString(), Float.parseFloat(tblNguyenLieuTra.getValueAt(i, 3).toString()));
                        clearPhieuTra();
                        break;
                    }
                    JOptionPane.showMessageDialog(this, "Đã hoàn thành phiếu và cập nhật số lượng trong kho");
                    JOptionPane.showMessageDialog(this, phieuTraService.updateTrangThaiPhieuTra(tblPhieuTra.getValueAt(row, 1).toString(), 3));
                    loadAllTra(((ChiNhanhViewModel_Hoang) comboChiNhanhTra.getSelectedItem()).getId());
                    loadTablePhieuTraHoanThanh(lstPhieuTra);
                    rdoHoanThanhPhieuTra.setSelected(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không thể hoàn thành");
            }
        }
    }//GEN-LAST:event_btnHoanThanhPhieuTraActionPerformed

    private void btnCapNhatPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatPhieuTraActionPerformed
        // TODO add your handling code here:
        if (checkEmptyPhieuTra()) {
            int row = tblPhieuTra.getSelectedRow();
            if (tblPhieuTra.getValueAt(row, 2) != null && tblPhieuTra.getValueAt(row, 3) != null && tblPhieuTra.getValueAt(row, 5) != null && tblPhieuTra.getValueAt(row, 6) != null) {
                phieuTraService.updatePhieuTra(tblPhieuTra.getValueAt(row, 0).toString(), tblPhieuTra.getValueAt(row, 1).toString(), ((NhaCungCapViewModel_Hoang) cboNhaCungCapTra.getSelectedItem()).getId(), ((NhanVienViewModel_Hoang) cboNhanVienTra.getSelectedItem()).getId(),
                        dateNgayTra.getDate());
                phieuTraService.deleteChiTietPnbyidPT(tblPhieuTra.getValueAt(row, 0).toString());
                for (int i = 0; i < tblNguyenLieuTra.getRowCount(); i++) {
                    phieuTraService.insertCTPhieuTra(tblPhieuTra.getValueAt(row, 0).toString(), tblNguyenLieuTra.getValueAt(i, 0).toString(), Float.parseFloat(tblNguyenLieuTra.getValueAt(i, 3).toString()), tblNguyenLieuTra.getValueAt(row, 5).toString());
                }
                clearPhieuTra();
                loadAllTra(((ChiNhanhViewModel_Hoang) cboChiNhanhNhap.getSelectedItem()).getId());
                JOptionPane.showMessageDialog(this, "Cập nhật thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Dữ liệu trống");
            }
        }
    }//GEN-LAST:event_btnCapNhatPhieuTraActionPerformed

    private void btnImport1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImport1ActionPerformed
        // TODO add your handling code here:
        modelPhieuTra.setRowCount(0);
        File exelFile;
        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelJTableImport = null;

        String defauCurrentDirectoryPath = "C:\\Users\\ASUS\\Desktop\\TestExcel";
        JFileChooser exportFlieChooser = new JFileChooser(defauCurrentDirectoryPath);

        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
        exportFlieChooser.setFileFilter(fnef);
        exportFlieChooser.setDialogTitle("Select Excel file");
        int excelChooser = exportFlieChooser.showOpenDialog(null);

        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            try {
                exelFile = exportFlieChooser.getSelectedFile();
                excelFIS = new FileInputStream(exelFile);
                excelBIS = new BufferedInputStream(excelFIS);
                excelJTableImport = new XSSFWorkbook(excelBIS);
                XSSFSheet excelSheet = excelJTableImport.getSheetAt(0);

                for (int row = 0; row < excelSheet.getLastRowNum(); row++) {
                    XSSFRow excelRow = excelSheet.getRow(row);

                    XSSFCell excelMaPhieu = excelRow.getCell(0);
                    XSSFCell excelMaNguyenLieu = excelRow.getCell(1);
                    XSSFCell excelTenNguyenLieu = excelRow.getCell(2);
                    XSSFCell excelMaNcc = excelRow.getCell(3);
                    XSSFCell excelTenNCC = excelRow.getCell(4);
                    XSSFCell excelMaNV = excelRow.getCell(5);
                    XSSFCell excelTenNV = excelRow.getCell(6);
                    XSSFCell excelNgayNhap = excelRow.getCell(7);
                    XSSFCell excelSoLuong = excelRow.getCell(8);
                    XSSFCell excelLyDo = excelRow.getCell(9);
                    XSSFCell excelTrangThai = excelRow.getCell(11);
                    modelPhieuTra.addRow(new Object[]{excelMaPhieu, excelMaNguyenLieu, excelTenNguyenLieu, excelMaNcc, excelTenNCC, excelMaNV, excelTenNV,
                        excelNgayNhap, excelSoLuong, excelLyDo, excelTrangThai});
                }
                JOptionPane.showMessageDialog(null, "Thành công");
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } finally {
                try {
                    if (excelFIS != null) {
                        excelFIS.close();
                    }
                    if (excelBIS != null) {
                        excelBIS.close();
                    }
                    if (excelJTableImport != null) {
                        excelJTableImport.close();
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }

            }
        }
    }//GEN-LAST:event_btnImport1ActionPerformed

    private void btnExport1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExport1ActionPerformed
        FileOutputStream excelFOU = null;
        BufferedOutputStream excelBOU = null;
        XSSFWorkbook excelJTableExporter = null;
        JFileChooser excelFileChooser = new JFileChooser("C:\\Users\\ASUS\\Desktop\\TestExcel");
        excelFileChooser.setDialogTitle("Save As");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
        excelFileChooser.setFileFilter(fnef);
        int excelChosser = excelFileChooser.showSaveDialog(null);
        if (excelChosser == JFileChooser.APPROVE_OPTION) {

            try {
                excelJTableExporter = new XSSFWorkbook();
                XSSFSheet excelSheet = excelJTableExporter.createSheet("JTable Sheet");
                for (int i = 0; i < modelPhieuTra.getRowCount(); i++) {
                    XSSFRow excelRow = excelSheet.createRow(i);
                    for (int j = 0; j < modelPhieuTra.getColumnCount(); j++) {
                        XSSFCell excelCell = excelRow.createCell(j);
                        excelCell.setCellValue(modelPhieuTra.getValueAt(i, j).toString());
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
    }//GEN-LAST:event_btnExport1ActionPerformed

    private void tblPhieuTraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuTraMouseClicked
        int row = tblPhieuTra.getSelectedRow();
        showChiTietPhieuTraByPhieuTra(tblPhieuTra.getValueAt(row, 0).toString());
        showNguyenLieuPhieuTraByPhieuTra(tblPhieuTra.getValueAt(row, 0).toString());
        fillTablePhieuTra(row);
    }//GEN-LAST:event_tblPhieuTraMouseClicked

    private void lblTimKiemPhieuTraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTimKiemPhieuTraMouseClicked
        if (txtTimKiemPhieuTra.getText().isEmpty()) {
            loadTablePhieuTra(phieuTraService.getAllPhieuTraByChiNhanh(((ChiNhanhViewModel_Hoang) cboChiNhanhTra.getSelectedItem()).getId()));
        } else {
            Set<PhieuTraViewModel> lstSearchPhieuTra = phieuTraService.searchPhieuTra(txtTimKiemPhieuTra.getText());
            if (lstSearchPhieuTra.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tồn tại mã phiếu này");
            } else {
                loadTablePhieuTra(lstSearchPhieuTra);
            }
        }
    }//GEN-LAST:event_lblTimKiemPhieuTraMouseClicked

    private void lblTimKiemPhieuTraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTimKiemPhieuTraMouseEntered
        SVGIconHelper.setIcon(lblTimKiemPhieuTra, "icon/search-alt-svgrepo-com2.svg", 25, 25);
    }//GEN-LAST:event_lblTimKiemPhieuTraMouseEntered

    private void lblTimKiemPhieuTraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTimKiemPhieuTraMouseExited
        SVGIconHelper.setIcon(lblTimKiemPhieuTra, "icon/search-alt-svgrepo-com.svg", 25, 25);
    }//GEN-LAST:event_lblTimKiemPhieuTraMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhatPhieuNhap;
    private javax.swing.JButton btnCapNhatPhieuTra;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnExport1;
    private javax.swing.JButton btnExportPdf;
    private javax.swing.JButton btnHoanThanhPhieuNhap;
    private javax.swing.JButton btnHoanThanhPhieuTra;
    private javax.swing.JButton btnHuyPhieuNhap;
    private javax.swing.JButton btnHuyPhieuTra;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnImport1;
    private javax.swing.JButton btnLocHoaDon;
    private javax.swing.JButton btnTaoPhieuNhap;
    private javax.swing.JButton btnTaoPhieuTra;
    private javax.swing.JButton btnTimKiemPhieuNhap;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cboChiNhanhNhap;
    private javax.swing.JComboBox<String> cboChiNhanhTra;
    private javax.swing.JComboBox<PhieuNhapViewModel> cboNguyenLieuNhap;
    private javax.swing.JComboBox<String> cboNguyenLieuTra;
    private javax.swing.JComboBox<NhaCungCap> cboNhaCungCapNhap;
    private javax.swing.JComboBox<String> cboNhaCungCapTra;
    private javax.swing.JComboBox<NhanVien> cboNhanVienNhap;
    private javax.swing.JComboBox<String> cboNhanVienTra;
    private com.toedter.calendar.JDateChooser dateFrom;
    private com.toedter.calendar.JDateChooser dateNgayNhap;
    private com.toedter.calendar.JDateChooser dateNgayTra;
    private com.toedter.calendar.JDateChooser dateTo;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblCNNhap;
    private javax.swing.JLabel lblCNTra;
    private javax.swing.JLabel lblTimKiemPhieuTra;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JLabel lblTotalMoney;
    private fomVe.Panel panel1;
    private fomVe.Panel panel2;
    private fomVe.Panel panel3;
    private fomVe.Panel3 panel31;
    private fomVe.Panel panel4;
    private fomVe.Panel panel5;
    private fomVe.Panel panel6;
    private fomVe.Panel panel7;
    private fomVe.Panel panel8;
    private fomVe.Panel panel9;
    private javax.swing.JRadioButton rdoHoanThanhPhieuNhap;
    private javax.swing.JRadioButton rdoHoanThanhPhieuTra;
    private javax.swing.JRadioButton rdoHuyPhieuNhap;
    private javax.swing.JRadioButton rdoHuyPhieuTra;
    private javax.swing.JRadioButton rdoPhieuNhapTam;
    private javax.swing.JRadioButton rdoPhieuTraTam;
    private javax.swing.JRadioButton rdoTatCaPhieuTra;
    private javax.swing.JTable tblChiTietPhieuTra;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblHoaDonChiTiet;
    private javax.swing.JTable tblNguyenLieu;
    private javax.swing.JTable tblNguyenLieuTra;
    private javax.swing.JTable tblPhieuNhap;
    private javax.swing.JTable tblPhieuNhapChiTiet;
    private javax.swing.JTable tblPhieuTra;
    private fomVe.JxText txtMaPhieuNhap;
    private fomVe.JxText txtMaPhieuTra;
    private fomVe.JxText txtTimKiemPhieuNhap;
    private fomVe.JtextSearch txtTimKiemPhieuTra;
    // End of variables declaration//GEN-END:variables
}
