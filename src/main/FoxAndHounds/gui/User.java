package main.FoxAndHounds.gui;

import java.util.StringJoiner;

public class User {

    private int id;
    private String name;
    private int win;

    public User(int id, String name, int win) {
        this.id = id;
        this.name = name;
        this.win = win;
    }

    @Override
    public String toString() {
        return new StringJoiner(",", User.class.getSimpleName() + "[", "]")
            .add("id=" + id)
            .add("name=" + name + "")
            .add("win=" + win)
            .toString();
    }
}