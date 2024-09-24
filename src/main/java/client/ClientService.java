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
        String selectQ = "SELECT * FROM CLIENTS WHERE client_id = ?";
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
        String insertQ = "INSERT INTO CLIENTS (name) VALUES(?)";
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
        String changeNameQ = "Update CLIENTS SET name = ? WHERE client_id = ?";

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
        String getAllQ = "SELECT * FROM CLIENTS";
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

    public void deleteById(long id) {
        String deleteFromClientById = "Delete From CLIENTS WHERE CLIENT_ID = "+id;
        String deleteFromProjectById = "Delete From Project WHERE CLIENT_ID ="+id;
        String deleteFromProjectWorker = "Delete From PROJECT_WORKER WHERE project_id in (SELECT project_id FROM PROJECT WHERE CLIENT_ID ="+id+")";

        if (checkIfClientHasProject(id)) {

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                connection.setAutoCommit(false);
                try {
                    Statement statement = connection.createStatement();
                    statement.addBatch(deleteFromProjectWorker);
                    statement.addBatch(deleteFromProjectById);
                    statement.addBatch(deleteFromClientById);

                    statement.executeBatch();
                }catch (Exception ex){
                    connection.rollback();
                }
                finally {
                    connection.setAutoCommit(true);
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }else {
            try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                Statement statement = connection.createStatement();
                statement.executeUpdate(deleteFromClientById);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }




    }

    private boolean checkIfClientHasProject(long id) {
        String getRelatedProject = "Select * from PROJECT WHERE CLIENT_ID = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(getRelatedProject);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
