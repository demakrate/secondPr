package jm.task.core.jdbc.util;
import java.util.*;
import java.sql.*;


public class Util implements AutoCloseable {
    private static String url = "jdbc:mysql://localhost:3306/new_schema1?useSSL=false&amp";
    private static String user = "root";
    private static String password = "fGhr34kjdv.3f?";

    private static Connection con;
    public static Connection getCon() throws SQLException {
        con = DriverManager.getConnection(url, user, password);
        return (con);

    }

    public void close() throws SQLException {
        con.close();
    }

}


