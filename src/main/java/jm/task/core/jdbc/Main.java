package jm.task.core.jdbc;

import jm.task.core.jdbc.util.Util;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    private static UserServiceImpl workWithUsers = new UserServiceImpl();

    public static void main(String[] args) {

        workWithUsers.createUsersTable();
        workWithUsers.saveUser("Dmitriy", "Polyakov", (byte) 21);
        workWithUsers.saveUser("Haron", "God", (byte) 126);
        workWithUsers.saveUser("Kolt", "Gromov", (byte) 54);
        workWithUsers.saveUser("Neymar", "Junior", (byte) 33);
        workWithUsers.getAllUsers();
        workWithUsers.cleanUsersTable();
        workWithUsers.dropUsersTable();
        try {
            Util.closeConnection();
        } catch (SQLException e) {
            System.out.println("Ошибка при закрытии соединения:" + e);
        }
    }
}
