package client;

import db.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {


    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";


    public String getById(long id) {
        String selectQ = "SELECT * FROM CLIENT WHERE client_id = ?";
        Client client = new Client();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(selectQ)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                client.setClientId(resultSet.getLong("client_id"));
                client.setName(resultSet.getString("name"));
            }
            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return client.toString();

    }


    public long addClient(String name) {
        long genId = 0;
        String insertQ = "INSERT INTO CLIENT (name) VALUES(?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQ, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    genId = generatedKeys.getLong(1);
                    preparedStatement.close();
                } else {
                    System.out.println("No id generated");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return genId;
    }

    public void setName(long id, String name) {
        String changeNameQ = "Update CLIENT SET name = ? WHERE client_id = ?";

        String changeNameQ1 = changeNameQ;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(changeNameQ1)) {

            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Client> getAllClients() {
        String getAllQ = "SELECT * FROM CLIENT";
        List<Client> result = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAllQ);

            while (resultSet.next()) {
                Client client = new Client();

                client.setName(resultSet.getString("name"));
                client.setClientId(resultSet.getLong("client_id"));

                result.add(client);
            }
            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;

    }
}
