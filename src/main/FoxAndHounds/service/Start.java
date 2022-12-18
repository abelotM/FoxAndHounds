package main.FoxAndHounds.service;

public class Start implements Runnable {
    private Status state;
    private Fox fox;
    private Hounds hounds;
    private boolean foxTurn = true;
    // private int delay = 1000000; // delay between moves, in milliseconds
    private boolean isRunning = false;
    private boolean notTerminated = true;

    public Start(Fox fox, Hounds hounds) {
        this.fox = fox;
        this.hounds = hounds;
        state = fox.startGame();
    }

    public void run() {
        while (notTerminated) {
            synchronized(this) {
                while (!isRunning) {
                    try {
                        wait();
                    } catch (InterruptedException e) { }
                }
            }
            if (notTerminated) {
                step();

            }
        }
    }

    public Status getState() {
        return state;
    }

    public  void step() {
        if (foxTurn) {
            state = fox.move(state);
        } else {
            state = hounds.move(state);
        }
        if (state.isFinal()) {
            // update final Q-values and restart the game
            hounds.move(state);
            fox.move(state);
            state = fox.startGame();
            foxTurn = false;
        }
        foxTurn = !foxTurn;
    }


    public void step(int coordinate) {
        if (foxTurn) {
            Status nextCurrentState = state.moveFox(new Coordinates(coordinate / 4,
                    coordinate % 4));
            for (Status s : state.foxNeighbours(false)) {
                if (s.equals(nextCurrentState)) {
                    fox.move(state, nextCurrentState);
                    if (nextCurrentState.isFinal()) {

                        // update final Q-values and restart the game
                        hounds.move(nextCurrentState);
                        fox.move(nextCurrentState);
                        state = fox.startGame();
                    } else {
                        state = hounds.move(nextCurrentState);
                        if (state.isFinal()) {
                            hounds.move(state);
                            fox.move(state);
                            state = fox.startGame();
                        }
                    }
                    break;
                }
            }
        }
    }

    public synchronized void start() {
        isRunning = true;
        notifyAll();
    }

    public void stop() {
        isRunning = false;
    }

    public synchronized void terminate() {
        notTerminated = false;
        if (!isRunning) {
            start();
        }
    }



    public boolean isRunning() {
        return isRunning;
    }


}