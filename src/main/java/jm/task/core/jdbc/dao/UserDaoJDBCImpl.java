package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String CREATE_TABLE = "CREATE TABLE `pp_1.1.3-4`.`users` (`id` INT NOT NULL AUTO_INCREMENT, `name` VARCHAR(45) NOT NULL, `lastname` VARCHAR(45) NOT NULL, `age` INT NOT NULL, PRIMARY KEY (`id`, `name`, `lastname`, `age`)) ENGINE = InnoDB  DEFAULT CHARACTER SET = utf8";

    private static final String DROP_TABLE = "DROP TABLE `pp_1.1.3-4`.`users`";
    private static final String INSERT_NEW = "INSERT INTO users (`name`, `lastname`, `age`) VALUES(?,?,?)";
    private static final String DELETE_USER = "DELETE FROM users WHERE ID=?";
    private static final String GET_USERS = "SELECT id, name, lastname, age FROM users";
    private static final String CLEAN_TABLE = "TRUNCATE `pp_1.1.3-4`.`users`";
    private Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }
    boolean tableExists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[] {"TABLE"});

        return resultSet.next();
    }
    public void createUsersTable() {
        try (Statement createStatement = connection.createStatement()) {
            if (!(tableExists(connection, "USERS"))) {
                createStatement.executeUpdate(CREATE_TABLE);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE)) {
            if (tableExists(connection, "USERS")) {
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_USERS)) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CLEAN_TABLE)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
