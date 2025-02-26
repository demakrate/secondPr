package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (Connection connection = Util.getCon()) {
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, "new_schema1",
                    "Users_Table", null);
            if (tables.next()) {
                System.out.println("Таблица уже существует");

            } else {
                Statement statement = connection.createStatement();
                statement.executeUpdate(
                        "CREATE TABLE Users_Table (ID BIGINT, NAME TEXT, LAST_NAME TEXT, AGE SMALLINT)"
                );
                System.out.println("Таблица создана");
            }

        } catch (SQLException e) {
            System.out.println("Ошибка при работе с базой данных: ");
            e.printStackTrace();
        }
    }


    public void dropUsersTable() {
        try (Connection connection = Util.getCon()) {
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "Users_Table", null);
            if (tables.next()) {
                Statement statement = connection.createStatement();
                statement.executeUpdate("DROP TABLE Users_Table");
                connection.close();
                System.out.println("Таблица удалена");
            } else {
                System.out.println("Таблицы не существует");
            }

        } catch (SQLException e) {
            System.out.println("Ошибка при работе с базой данных: ");
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (Connection connection = Util.getCon()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select count(*) from Users_Table");

            long id = rs.next() ? rs.getLong(1) : 0;
            String sql = "INSERT INTO Users_Table (ID, NAME, LAST_NAME, AGE) VALUES (?, ?, ?, ?)";
            PreparedStatement prepState = connection.prepareStatement(sql);
            prepState.setLong(1, id + 1);
            prepState.setString(2, name);
            prepState.setString(3, lastName);
            prepState.setByte(4, age);

            prepState.executeUpdate();
            System.out.println("User с именем — ".concat(name).concat(" добавлен в базу данных"));
        } catch (SQLException e) {
            System.out.println("Ошибка при работе с базой данных: ");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getCon()) {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM Users_Table WHERE Id = ".concat(String.valueOf(id)));
            System.out.println("Пользователь удален");
        } catch (SQLException e) {
            System.out.println("Ошибка при работе с базой данных: ");
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getCon()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users_Table");
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
        } catch (SQLException e) {
            System.out.println("Ошибка при работе с базой данных: ");
            e.printStackTrace();
        }
        return (users);
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getCon()) {
            Statement statement = connection.createStatement();
            statement.execute("Truncate table Users_Table");
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            System.out.println("Ошибка при работе с базой данных: ");
            e.printStackTrace();
        }
    }


}
