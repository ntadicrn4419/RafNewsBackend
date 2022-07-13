package com.example.raf_news_backend.repositories.user;

import com.example.raf_news_backend.entities.User;
import com.example.raf_news_backend.repositories.MySqlAbstractRepository;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserRepository extends MySqlAbstractRepository implements IUserRepository{

    @Override
    public User findUser(String email) {
        User user = new User();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM users where email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                String uEmail = resultSet.getString("email");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String type = resultSet.getString("type");
                boolean active = resultSet.getBoolean("active");
                String password = resultSet.getString("password");

                user.setEmail(uEmail);
                user.setFirstname(firstname);
                user.setLastname(lastname);
                user.setType(type);
                user.setActive(active);
                user.setPassword(password);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from users");
            while (resultSet.next()) {
                User u = new User();
                u.setEmail(resultSet.getString("email"));
                u.setFirstname(resultSet.getString("firstname"));
                u.setLastname(resultSet.getString("lastname"));
                u.setType(resultSet.getString("type"));
                u.setActive(resultSet.getBoolean("active"));
                u.setPassword(resultSet.getString("password"));
                users.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return users;
    }

    @Override
    public User addUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO users (email, firstname, lastname, type, active, password) VALUES(?, ?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getFirstname());
            preparedStatement.setString(3, user.getLastname());
            preparedStatement.setString(4, user.getType());
            preparedStatement.setBoolean(5, user.getActive());
            preparedStatement.setString(6, DigestUtils.sha256Hex(user.getPassword()));

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public void updateUser(String oldEmail, String firstname, String lastname, String email, String password, String type, Boolean active) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("UPDATE users SET email=?, firstname=?, lastname=?, type=?, active=?, password=? WHERE email=? ");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, firstname);
            preparedStatement.setString(3, lastname);
            preparedStatement.setString(4, type);
            preparedStatement.setBoolean(5, active);
            preparedStatement.setString(6, DigestUtils.sha256Hex(password));
            preparedStatement.setString(7, oldEmail);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

    }

    @Override
    public boolean activateDeactivateUser(String email) {

        User u = findUser(email);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE users SET active=? WHERE email=?");
            preparedStatement.setBoolean(1, !u.getActive());
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
        return !u.getActive();
    }
}
