package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

public class Main {
    private static UserDaoJDBCImpl workWithUsers = new UserDaoJDBCImpl();
    public static void main(String[] args) {
        workWithUsers.createUsersTable();
        workWithUsers.saveUser("Dmitriy", "Polyakov", (byte) 21);
        workWithUsers.saveUser("Haron", "God", (byte) 126);
        workWithUsers.saveUser("Kolt", "Gromov", (byte) 54);
        workWithUsers.saveUser("Neymar", "Junior", (byte) 33);
        workWithUsers.getAllUsers();
        workWithUsers.cleanUsersTable();
        workWithUsers.dropUsersTable();
    }
}
