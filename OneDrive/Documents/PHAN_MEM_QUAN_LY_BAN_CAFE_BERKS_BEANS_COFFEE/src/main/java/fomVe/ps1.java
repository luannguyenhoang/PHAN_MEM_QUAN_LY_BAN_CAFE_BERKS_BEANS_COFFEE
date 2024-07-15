/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fomVe;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author hoang
 */
public class ps1 extends JPasswordField {

    public ps1() {
        super();
        setColumns(10);
        setText("Mật khẩu mới");
        setForeground(Color.GRAY);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (getText().equals("Mật khẩu mới")) {
                    setText("");
                    setForeground(Color.BLACK);
                }
            }
        });
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (getPassword().equals("")) {
                    setText("Mật khẩu mới");
                    setForeground(Color.GRAY);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        setBackground(Color.WHITE);
        g2.setColor(Color.GRAY);
        Insets st = getInsets();
        FontMetrics fm = g2.getFontMetrics();
        if (!hasFocus() && getText().isEmpty()) {
            g2.drawString("Mật khẩu mới", st.left, (getHeight() / 2) + (fm.getAscent() / 2) - 1);
        }
    }
}
