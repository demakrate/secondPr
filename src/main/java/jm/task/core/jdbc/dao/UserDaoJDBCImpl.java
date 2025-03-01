package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;

    public UserDaoJDBCImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createUsersTable() {

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Users_Table (ID BIGINT, NAME TEXT, LAST_NAME TEXT, AGE SMALLINT)"
            );
            connection.commit();
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            System.out.println("Ошибка при работе с базой данных: ");
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS Users_Table");
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            System.out.println("Ошибка при работе с базой данных: ");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Statement statement = connection.createStatement();
             PreparedStatement prepState = connection.prepareStatement(
                     "INSERT INTO Users_Table (ID, NAME, LAST_NAME, AGE) VALUES (?, ?, ?, ?)");
             ResultSet rs = statement.executeQuery("select count(*) from Users_Table")) {
            long id = rs.next() ? rs.getLong(1) : 0;
            prepState.setLong(1, id + 1);
            prepState.setString(2, name);
            prepState.setString(3, lastName);
            prepState.setByte(4, age);
            prepState.executeUpdate();
            connection.commit();
            System.out.println("User с именем — ".concat(name).concat(" добавлен в базу данных"));
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.out.println("Ошибка при работе с базой данных: ");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        try (PreparedStatement preparedStatement = connection
                .prepareStatement("DELETE FROM Users_Table WHERE Id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("Пользователь удален");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.out.println("Ошибка при работе с базой данных: ");
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Users_Table")) {
            while (resultSet.next()) {
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                byte age = resultSet.getByte(4);
                User user = new User(name, lastName, age);
                user.setId(resultSet.getLong(1));
                users.add(user);
            }
            if (users.isEmpty()) {
                System.out.println("Пользователи отсутствуют");
            } else {
                for (int i = 0; i < users.size(); i++)
                    System.out.println(users.get(i).toString());
            }
            connection.commit();

        } catch (SQLException e) {
            System.out.println("Ошибка при работе с базой данных: ");
            e.printStackTrace();
        }
        return (users);
    }

    @Override
    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("Truncate table Users_Table");
            connection.commit();
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.out.println("Ошибка при работе с базой данных: ");
            e.printStackTrace();
        }

    }


}
