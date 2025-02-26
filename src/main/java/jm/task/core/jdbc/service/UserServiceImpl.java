package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

import java.util.List;
import java.sql.*;

public class UserServiceImpl implements UserService {
    UserDaoJDBCImpl us = new UserDaoJDBCImpl();

    public void createUsersTable() {
        us.createUsersTable();
    }

    public void dropUsersTable() {
        us.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        us.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        us.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return (us.getAllUsers());
    }

    public void cleanUsersTable() {
        us.cleanUsersTable();
    }
}
