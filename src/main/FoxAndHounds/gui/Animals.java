package main.FoxAndHounds.gui;

import java.awt.Color;
import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class Animals extends JLabel {
    private ImageIcon icon;
    private Color backGround;

    public Animals(String name) {

        icon = new  ImageIcon(getClass().getResource("/FoxAndHounds/gui" + name));
        backGround = Color.BLACK;
        setIcon(icon);
    }

    public ImageIcon returnImage() {
        return icon;
    }
    public Color returnColo() {
        return backGround;
    }
}