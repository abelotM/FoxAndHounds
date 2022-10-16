package main.FoxAndHounds.gui;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    public Frame(){
        setTitle("Fox and Hounds");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Board boardPanel = new Board();
        MouseControl mouse = new MouseControl(boardPanel);

        setLayout(new FlowLayout());
        add(boardPanel);
        repaint();
        setVisible(true);
    }
}