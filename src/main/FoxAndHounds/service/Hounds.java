package main.FoxAndHounds.service;

import java.io.Serializable;
import java.util.Vector;

public class Hounds extends Movement implements Serializable, Cloneable {

    public Hounds(){
        qValues = new double[Status.NUM_STATES][8];
    }


    public synchronized double[][] qValues(Status state) {
        double[][] result = new double[4][2];
        int stateIndex = state.toInt();
        Vector<Status> neighbours = state.houndsNeighbours(true);
        for (int resultPos = 0, qValuePos = 0; resultPos < 8; ++resultPos) {
            if (neighbours.elementAt(resultPos) == null) {
                result[resultPos / 2][resultPos % 2] = 0;
            } else {
                result[resultPos / 2][resultPos % 2]
                        = qValues[stateIndex][qValuePos++];
            }
        }
        return result;
    }

    protected Vector<Status> neighbours(Status state) {
        return state.houndsNeighbours(false);
    }


    public Object clone() {
        return super.clone();
    }
}