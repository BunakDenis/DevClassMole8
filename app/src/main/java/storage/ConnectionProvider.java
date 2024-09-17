package storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionProvider {

    private List<Connection> connections;

    public ConnectionProvider() {
        connections = new ArrayList<>();
    }

    public Connection createConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(StorageConstance.CONNECTION_URL);
        connections.add(conn);
        return conn;
    }

    public void close() throws SQLException {

        for (Connection connection : connections) {
            if (connection.isClosed()) {
                continue;
            }

            connection.close();

        }

    }

}
