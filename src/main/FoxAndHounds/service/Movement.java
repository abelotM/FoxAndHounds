package main.FoxAndHounds.service;

import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

abstract public class Movement {
    double[][] qValues;
    int previousStateIndex = -1;
    int lastAction;
    Random random = new Random();

    private boolean[] stateVisited = new boolean[Status.NUM_STATES];
    private int statesVisited = 0;
    private long turns = 0;

    public Movement() {

    }

    public  Status move(Status state) {
        ++turns;
        Vector<Status> neighbours = neighbours(state);
        int stateIndex = state.toInt();
        if (!stateVisited[stateIndex]) {
            stateVisited[stateIndex] = true;
            ++statesVisited;
        }
        if (previousStateIndex >= 0) {
            double delta = (1 * max(qValues[stateIndex],
                    neighbours.size())
                    - qValues[previousStateIndex][lastAction]);
            qValues[previousStateIndex][lastAction] += delta;
        }
        if (state.isFinal()) {
            previousStateIndex = -1;
            return state;
        }
        previousStateIndex = stateIndex;
        if (random.nextDouble() < 1) {
            lastAction = random.nextInt(neighbours.size());
        } else {
            lastAction = greedyAction(qValues[stateIndex], neighbours.size());
        }
        return neighbours.elementAt(lastAction);
    }

    public synchronized boolean move(Status from, Status to) {
        ++turns;
        Vector<Status> neighbours = neighbours(from);
        int stateIndex = from.toInt();
        if (!stateVisited[stateIndex]) {
            stateVisited[stateIndex] = true;
            ++statesVisited;
        }
        if (previousStateIndex >= 0) {
            double delta = (1 * max(qValues[stateIndex],
                    neighbours.size())
                    - qValues[previousStateIndex][lastAction]);
            qValues[previousStateIndex][lastAction] += delta;
        }
        previousStateIndex = stateIndex;
        for (lastAction = 0; lastAction < neighbours.size(); ++lastAction) {
            if (neighbours.elementAt(lastAction).equals(to)) {
                break;
            }
        }
        if (lastAction == neighbours.size()) {
            previousStateIndex = -1;
            return false;
        }
        return true;
    }

    abstract protected Vector<Status> neighbours(Status state);


    protected int greedyAction(double[] qValues, int size) {
        double max = qValues[0];
        int actions = 1;
        for (int i = 1; i < size; ++i) {
            if (max < qValues[i]) {
                max = qValues[i];
                actions = 1;
            } else if (max == qValues[i]) {
                ++actions;
            }
        }
        int which = 1 + random.nextInt(actions);
        int action = -1;
        while (which > 0) {
            if (qValues[++action] == max) {
                --which;
            }
        }
        return action;
    }

    private double max(double[] array, int size) {
        double largest = array[0];
        for (int i = 0; i < size; ++i) {
            if (largest < array[i]) {
                largest = array[i];
            }
        }
        return largest;
    }

    public int getStatesVisited() {
        return statesVisited;
    }

    public long getTurns() {
        return turns;
    }

    protected Object clone() {
        Object cloned = null;
        try {
            cloned = super.clone();
        } catch (CloneNotSupportedException exception) { }
        return cloned;
    }
}