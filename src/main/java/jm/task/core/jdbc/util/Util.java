package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    private static String url = "jdbc:mysql://localhost:3306/new_schema1?useSSL=false&amp";
    private static String user = "root";
    private static String password = "fGhr34kjdv.3f?";

    private Util() {
    }

    private static Connection con;

    public static Connection getCon() {
        try {
            con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных: " + e);
        }
        return (con);
    }

    public static void closeConnection() throws SQLException {
        con.close();
    }

}


