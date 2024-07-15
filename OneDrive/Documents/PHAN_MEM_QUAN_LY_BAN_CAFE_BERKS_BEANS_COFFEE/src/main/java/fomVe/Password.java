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

/**
 *
 * @author hoang
 */
public class Password extends JPasswordField {

    private static final String PLACEHOLDER_TEXT = "Password";
    private boolean showingPlaceholder;

    public Password() {
        super();
        setEchoChar((char) 0);
        showingPlaceholder = true;
        setForeground(Color.GRAY);
        setText(PLACEHOLDER_TEXT);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (showingPlaceholder) {
                    setEchoChar('*');
                    setText("");
                    setForeground(Color.BLACK);
                    showingPlaceholder = false;
                }
            }
        });
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (getPassword().length == 0) {
                    setEchoChar((char) 0);
                    setText(PLACEHOLDER_TEXT);
                    setForeground(Color.GRAY);
                    showingPlaceholder = true;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (!hasFocus() && getPassword().length == 0 && showingPlaceholder) {
            g2.setColor(Color.GRAY);
            setBackground(Color.WHITE);
            Insets st = getInsets();
            FontMetrics fm = g2.getFontMetrics();
            int x = st.left;
            int y = (getHeight() / 2) + (fm.getAscent() / 2) - 1;
            g2.drawString(PLACEHOLDER_TEXT, x, y);
        }
    }
}
