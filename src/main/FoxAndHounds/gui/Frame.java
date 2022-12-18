package main.FoxAndHounds.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame implements ActionListener {

    JButton resetButton;
    Board boardPanel;
    public Frame(){
        setTitle("Fox and Hounds");
        setSize(480, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Board boardPanel = new Board();
        MouseControl mouse = new MouseControl(boardPanel);


        this.setLayout(new FlowLayout());
        resetButton = new JButton();
        resetButton.setText("Reset");
        resetButton.setSize(100, 50);
        resetButton.setLocation(0, 200);
        resetButton.addActionListener(this);
        this.add(resetButton);
        this.add(boardPanel);
        this.add(mouse);
        this.repaint();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==resetButton) {
            this.remove(boardPanel);
            boardPanel = new Board();
            add(boardPanel);
            SwingUtilities.updateComponentTreeUI(this);
        }
    }
}