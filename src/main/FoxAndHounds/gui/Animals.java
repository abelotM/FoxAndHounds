package main.FoxAndHounds.gui;

import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Animals extends JLabel {

    private ImageIcon icon;
    private Color backGround;

    public Animals(String name) {

        icon = new  ImageIcon(getClass().getResource("/" + name));
        setIcon(icon);
    }

    public ImageIcon returnImage() {return icon;}
    public Color returnColo() {return backGround;}
}