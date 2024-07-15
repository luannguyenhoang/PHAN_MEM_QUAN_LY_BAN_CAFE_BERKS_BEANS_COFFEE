package fomVe;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;

public class SVGIconHelper {
    private static FlatSVGIcon icon = null;

    public static void setIcon(JLabel label, String path, int width, int height) {
        try {
            icon = new FlatSVGIcon(path, width, height);
            label.setIcon(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
