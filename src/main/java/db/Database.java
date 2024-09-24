package db;

import org.flywaydb.core.Flyway;

public class Database {

    public  void initDb(){
        try {
            Flyway.configure()
                    .dataSource("jdbc:h2:~/test", "sa", "")
                    .load()
                    .migrate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
