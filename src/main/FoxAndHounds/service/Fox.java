package main.FoxAndHounds.service;

import java.io.Serializable;
import java.util.Vector;


public class Fox extends Movement implements Serializable, Cloneable {

    public Fox() {
        qValues = new double[Status.NUM_STATES][4];
    }


    public synchronized double[] qValues(Status state) {
        double[] result = new double[4];
        int stateIndex = state.toInt();
        Vector<Status> neighbours = state.foxNeighbours(true);
        for (int resultPos = 0, qValuePos = 0; resultPos < 4; ++resultPos) {
            if (neighbours.elementAt(resultPos) == null) {
                result[resultPos] = 0;
            } else {
                result[resultPos] = qValues[stateIndex][qValuePos++];
            }
        }
        return result;
    }


    public Status startGame() {
        return move(new Status());
    }

    protected Vector<Status> neighbours(Status state) {
        return state.foxNeighbours(false);
    }



    public Object clone() {
        return super.clone();
    }
}