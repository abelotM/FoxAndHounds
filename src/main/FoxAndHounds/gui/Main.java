package main.FoxAndHounds.gui;

import java.awt.FontFormatException;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

    public static void main(String[] args) {

        try {
            Frame frame = new Frame();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}