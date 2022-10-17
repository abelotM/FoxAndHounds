package main.FoxAndHounds.gui;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;
import main.FoxAndHounds.service.Status;
import main.FoxAndHounds.service.Coordinates;


public class Board extends JPanel {
    private Animals fox;
    private Animals[] hounds = new Animals[4];

    public Board() {
    }

    public void init(MouseControl mouse) {
        setLayout(new GridLayout(8, 8));
        for(int i = 0; i < 64; i++) {
            JPanel square = new JPanel();
            if ((i / 8 + i) % 2 == 1) {
                square.setBackground(Color.BLACK);
                square.setName(String.valueOf((63 - i) / 8 * 4 + i % 8 / 2));
                square.addMouseListener(mouse);
            } else {
                square.setBackground(Color.WHITE);
            }
            add(square);
        }

        fox = new Animals("fox.png");
        for (int i = 0; i < 4; ++i) {
            hounds[i] = new Animals("hound.png");
        }
    }

    public void addAnimal(Animals piece, int squareId) {
        ((JPanel)getComponent(squareId)).add(piece);
    }

    private int idByCoordinates(Coordinates coordinates) {
        int row = coordinates.getRow();
        return (7 - row) * 8 + coordinates.getColumn() * 2 + row % 2;
    }

    public void showState(Status state) {
        addAnimal(fox, idByCoordinates(state.getFox()));
        Coordinates[] houndsCoordinates = state.getHounds();
        for (int i = 0; i < 4; ++i) {
            addAnimal(hounds[i], idByCoordinates(houndsCoordinates[i]));
        }
        repaint();
    }
}