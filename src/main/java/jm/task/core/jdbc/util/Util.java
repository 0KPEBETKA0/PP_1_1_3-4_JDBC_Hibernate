package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/pp_1.1.3-4";
    private static final String USERNAME = "roots";
    private static final String PASSWORD = "root";

    private static Connection connection;
    public Util() {

    }

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
