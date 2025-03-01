package jm.task.core.jdbc.service;


import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao us = new UserDaoJDBCImpl(Util.getCon());


    @Override
    public void createUsersTable() {
        us.createUsersTable();
    }

    @Override
    public void dropUsersTable() {
        us.dropUsersTable();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        us.saveUser(name, lastName, age);
    }

    @Override
    public void removeUserById(long id) {
        us.removeUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return (us.getAllUsers());
    }

    @Override
    public void cleanUsersTable() {
        us.cleanUsersTable();
    }
}
