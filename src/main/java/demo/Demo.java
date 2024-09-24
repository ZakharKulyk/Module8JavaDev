package demo;

import client.Client;
import client.ClientService;
import db.Database;

import java.util.List;

public class Demo {
    public static void main(String[] args) {
        Database database = new Database();
        database.initDb();

        ClientService clientService = new ClientService();
        List<Client> allClients = clientService.getAllClients();
        allClients.stream().forEach(System.out::println);
    }
}
