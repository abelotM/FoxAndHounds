package main.FoxAndHounds.gui;


import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {

    public static void main(String[] args) throws SQLException {



        try {       Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/./foxandhounds", "sa", "admin");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                int win = resultSet.getInt("WIN");

                User user = new User(id, name, win);
                System.out.println(user);

            }
            Frame frame = new Frame();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

}