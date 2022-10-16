package main.FoxAndHounds.service;

import java.util.Vector;
import java.util.Arrays;

public class Status {


    public static final int NUM_STATES = 32 * 31 * 30 * 29 * 16 / 4 / 3 / 2 + 1;


    private Coordinates fox = null;
    private Coordinates[] hounds = new Coordinates[4];


    public Status() {
        for (int i = 0; i < 4; ++i) {
            hounds[i] = new Coordinates(7, i);
        }
    }


    public Status(int foxColumn) {
        this();
        fox = new Coordinates(0, foxColumn);
    }


    private Status(Status state) {
        if (state.fox != null) {
            fox = new Coordinates(state.fox);
        }
        for (int i = 0; i < 4; ++i) {
            hounds[i] = new Coordinates(state.hounds[i]);
        }
    }


    public boolean isFinal() {
        return foxWon() || houndsWon();
    }


    public Vector<Status> foxNeighbours(boolean includeNulls) {
        Vector<Status> neighbours = new Vector<Status>();
        if (fox == null) {
            for (int i = 0; i < 4; ++i) {
                neighbours.add(new Status(i));
            }
            return neighbours;
        }
        Coordinates moved;

        moved = new Coordinates(fox.getRow() + 1, fox.getColumn());
        Status sameColumnState = (isHoundAt(moved) ? null : moveFox(moved));
        if (fox.getRow() % 2 == 0) {
            moved = new Coordinates(fox.getRow() + 1, fox.getColumn() - 1);
            if (fox.getColumn() > 0 && !isHoundAt(moved)) {
                neighbours.add(moveFox(moved));
            } else if (includeNulls) {
                neighbours.add(null);
            }
            if (sameColumnState != null || includeNulls) {
                neighbours.add(sameColumnState);
            }
        } else {
            if (sameColumnState != null || includeNulls) {
                neighbours.add(sameColumnState);
            }
            moved = new Coordinates(fox.getRow() + 1, fox.getColumn() + 1);
            if (fox.getColumn() < 3 && !isHoundAt(moved)) {
                neighbours.add(moveFox(moved));
            } else if (includeNulls) {
                neighbours.add(null);
            }
        }

        moved = new Coordinates(fox.getRow() - 1, fox.getColumn());
        sameColumnState = (isHoundAt(moved) ? null : moveFox(moved));
        if (fox.getRow() > 0) {
            if (fox.getRow() % 2 == 0) {
                moved = new Coordinates(fox.getRow() - 1, fox.getColumn() - 1);
                if (fox.getColumn() > 0 && !isHoundAt(moved)) {
                    neighbours.add(moveFox(moved));
                } else if (includeNulls) {
                    neighbours.add(null);
                }
                if (sameColumnState != null || includeNulls) {
                    neighbours.add(sameColumnState);
                }
            } else {
                if (sameColumnState != null || includeNulls) {
                    neighbours.add(sameColumnState);
                }
                moved = new Coordinates(fox.getRow() - 1, fox.getColumn() + 1);
                if (fox.getColumn() < 3 && !isHoundAt(moved)) {
                    neighbours.add(moveFox(moved));
                }
            }
        }
        return neighbours;
    }


    public Vector<Status> houndsNeighbours(boolean includeNulls) {
        Vector<Status> neighbours = new Vector<Status>();
        for (int i = 0; i < 4; ++i) {
            Coordinates moved;
            if (hounds[i].getRow() > 0) {
                moved = new Coordinates(hounds[i].getRow() - 1,
                        hounds[i].getColumn());
                if (!isHoundAt(moved) && !moved.equals(fox)) {
                    neighbours.add(moveHound(i, moved));
                } else if (includeNulls) {
                    neighbours.add(null);
                }
                if (hounds[i].getRow() % 2 == 1) {
                    if (hounds[i].getColumn() < 3) {
                        moved = new Coordinates(hounds[i].getRow() - 1,
                                hounds[i].getColumn() + 1);
                        if (!isHoundAt(moved) && !moved.equals(fox)) {
                            neighbours.add(moveHound(i, moved));
                        } else if (includeNulls) {
                            neighbours.add(null);
                        }
                    } else if (includeNulls) {
                        neighbours.add(null);
                    }
                } else {
                    if (hounds[i].getColumn() > 0) {
                        moved = new Coordinates(hounds[i].getRow() - 1,
                                hounds[i].getColumn() - 1);
                        if (!isHoundAt(moved) && !moved.equals(fox)) {
                            neighbours.add(moveHound(i, moved));
                        } else if (includeNulls) {
                            neighbours.add(null);
                        }
                    } else if (includeNulls) {
                        neighbours.add(null);
                    }
                }
            } else if (includeNulls) {
                neighbours.add(null);
                neighbours.add(null);
            }
        }
        return neighbours;
    }


    public Status moveFox(Coordinates coordinates) {
        Status state = new Status(this);
        state.fox = coordinates;
        return state;
    }


    private Status moveHound(int houndIndex, Coordinates coordinates) {
        Status state = new Status(this);
        state.hounds[houndIndex] = coordinates;
        Arrays.sort(state.hounds);
        return state;
    }


    public boolean foxWon() {
        if (fox == null) {
            return false;
        }
        if (houndsNeighbours(false).size() == 0) {
            return true;
        }
        for (Coordinates hound : hounds) {
            if (hound.getRow() > fox.getRow()) {
                return false;
            }
        }
        return true;
    }


    public boolean houndsWon() {
        return foxNeighbours(false).size() == 0;
    }


    private boolean isHoundAt(Coordinates coordinates) {
        for (Coordinates hound : hounds) {
            if (hound.equals(coordinates)) {
                return true;
            }
        }
        return false;
    }

    public Coordinates getFox() {
        return fox;
    }

    public Coordinates[] getHounds() {
        return hounds;
    }

    private static int[][] C = new int[33][5]; // number of combinations
    static {
        for (int i = 0; i < 33; ++i) {
            C[i][0] = 1;
        }
        for (int i = 1; i < 33; ++i) {
            for (int j = 1; j < 5; ++j) {
                C[i][j] = C[i - 1][j - 1] + C[i - 1][j];
            }
        }
    }


    public int toInt() {
        if (fox == null) {
            return NUM_STATES - 1;
        }
        int intState = 0;
        int foxCoordinate = fox.getRow() / 2 * 4 + fox.getColumn();
        for (int i = 0; i < 4; ++i) {
            intState += C[hounds[i].getRow() * 4 + hounds[i].getColumn()][i + 1];
        }
        return intState * 16 + foxCoordinate;
    }

    public boolean foxTurn() {
        if (fox == null) {
            return true;
        }
        int sum = fox.getRow();
        for (Coordinates hound : hounds) {
            sum += hound.getRow();
        }
        return sum % 2 == 0;
    }

    public boolean equals(Status state) {
        for (int i = 0; i < 4; ++i) {
            if (!hounds[i].equals(state.hounds[i])) {
                return false;
            }
        }
        return fox.equals(state.fox);
    }
}