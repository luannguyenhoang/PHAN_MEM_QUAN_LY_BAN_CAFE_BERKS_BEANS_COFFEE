package fomVe;

import javax.swing.table.TableCellRenderer;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
public class CustomHeaderRenderer implements TableCellRenderer {
    private final TableCellRenderer defaultRenderer;

    public CustomHeaderRenderer(TableCellRenderer defaultRenderer) {
        this.defaultRenderer = defaultRenderer;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) defaultRenderer.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        label.setBorder(BorderFactory.createEmptyBorder()); // Bỏ border

        return label;
    }

}
