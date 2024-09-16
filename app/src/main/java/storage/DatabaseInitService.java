package storage;

import org.flywaydb.core.Flyway;

public class DatabaseInitService {

    public void initDb () {

        try {
            Flyway flyway = Flyway
                    .configure()
                    .dataSource(StorageConstance.CONNECTION_URL, null, null)
                    .load();

            flyway.migrate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
