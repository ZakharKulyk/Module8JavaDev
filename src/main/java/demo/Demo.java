package demo;

import client.Client;
import client.ClientService;
import db.Database;



public class Demo {
    public static void main(String[] args) {
        Database database = new Database();
        database.initDb();

        ClientService clientService = new ClientService();
        clientService.deleteById(4);
    }
}
