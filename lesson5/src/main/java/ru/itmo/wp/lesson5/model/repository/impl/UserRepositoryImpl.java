package ru.itmo.wp.lesson5.model.repository.impl;

import ru.itmo.wp.lesson5.model.database.DatabaseUtils;
import ru.itmo.wp.lesson5.model.domain.User;
import ru.itmo.wp.lesson5.model.exception.RepositoryException;
import ru.itmo.wp.lesson5.model.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;

public class UserRepositoryImpl implements UserRepository {
    private final DataSource dataSource = DatabaseUtils.getDataSource();

    @Override
    public void register(User user, String passwordSha) {
        // INSERT INTO `User` (`id`, `login`, `passwordSha`, `creationTime`) VALUES (NULL, `testtt`, `123`, NOW());
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO `User` (`login`, `passwordSha`, `creationTime`) VALUES (?, ?, NOW())",
                    Statement.RETURN_GENERATED_KEYS )) {
                statement.setString(1, user.getLogin());
                statement.setString(2, passwordSha);
                if (statement.executeUpdate() == 1) {
                    try (ResultSet keys = statement.getGeneratedKeys()) {
                        if (keys.next()) {
                            long id = keys.getLong(1);
                            user.setId(id);
                            //user.setCreationTime(findByLogin(user.getLogin()).getCreationTime());
                        } else {
                            throw new RepositoryException("Expected returned key on inserted User.");
                        }
                    }
                } else {
                    throw new RepositoryException("Expected exactly 1 inserted User.");
                }
            }

        } catch (SQLException e) {
            throw new RepositoryException("Can't insert User.", e);
        }
    }

    @Override
    public User findByLogin(String login) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM User WHERE login=?")) {
                statement.setString(1, login);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return toUser(statement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User by login.", e);
        }
    }

    @Override
    public User findByLoginAndPasswordSha(String login, String passwordSha) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM User WHERE login=? AND passwordSha=?")) {
                statement.setString(1, login);
                statement.setString(2, passwordSha);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return toUser(statement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User by login.", e);
        }
    }

    private User toUser(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            User user = new User();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                switch (metaData.getColumnName(i)) {
                    case "id" -> user.setId(resultSet.getLong(i));
                    case "login" -> user.setLogin(resultSet.getString(i));
                    case "creationTime" -> user.setCreationTime(resultSet.getTimestamp(i));
                    case "passwordSha" -> {
                    }
                    default -> throw new RepositoryException("Unexpected column " + metaData.getColumnName(i) + ".");
                }
            }
            return user;
        } else {
            return null;
        }
    }
}
