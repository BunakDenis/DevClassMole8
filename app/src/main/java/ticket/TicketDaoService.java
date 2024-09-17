package ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TicketDaoService {

    PreparedStatement createSt;
    PreparedStatement getMaxIdSt;
    PreparedStatement getTicketCountToPlanetSt;
    PreparedStatement getAllSt;
    ExecutorService executorService;

    public TicketDaoService(Connection connection) throws SQLException {
        executorService = Executors.newSingleThreadScheduledExecutor();
        createSt = connection.prepareStatement(
                "INSERT INTO ticket (passenger_id, to_planet, from_planet) VALUES (?, ?, ?)"
        );

        getMaxIdSt = connection.prepareStatement(
                "SELECT max(id) AS MaxID from ticket"
        );

        getTicketCountToPlanetSt = connection.prepareStatement(
                "SELECT count(*) AS cnt FROM ticket WHERE to_planet = ?"
        );

        getAllSt = connection.prepareStatement(
                "SELECT * FROM ticket"
        );

    }

    public long create(Ticket ticket) throws SQLException, ExecutionException, InterruptedException {

        Future<Long> ft = executorService.submit(() -> {
            createSt.setLong(1, ticket.getPassenger_id());
            createSt.setString(2, ticket.getTo().toString());
            createSt.setString(3, ticket.getFrom().toString());
            createSt.executeUpdate();

            try (ResultSet rs = getMaxIdSt.executeQuery()) {

                rs.next();

                long result = rs.getLong("MaxId");
                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return -1l;
        });
        return ft.get();
    }

    public long getTicketCountToPlanet(Planet planet) throws SQLException {

        getTicketCountToPlanetSt.setString(1, planet.toString());

        try(ResultSet rs = getTicketCountToPlanetSt.executeQuery()) {
            if (!rs.next()) {
                return -1;
            }
            return rs.getLong("cnt");
        }
    }

    public List<Ticket> getAll() {
        List<Ticket> result = new ArrayList<>();

        try(ResultSet rs = getAllSt.executeQuery()) {
            while (rs.next()) {
                Ticket ticket = new Ticket();

                ticket.setId(rs.getLong("id"));
                ticket.setPassenger_id(rs.getLong("passenger_id"));
                ticket.setFrom(Planet.valueOf(rs.getString("from_planet")));
                ticket.setTo(Planet.valueOf(rs.getString("to_planet")));

                result.add(ticket);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
