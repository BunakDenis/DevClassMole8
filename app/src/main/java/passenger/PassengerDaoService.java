package passenger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PassengerDaoService {

    PreparedStatement createSt;
    PreparedStatement getByPassportSt;
    PreparedStatement getMaxIdSt;

    public PassengerDaoService(Connection connection) throws SQLException {
        createSt = connection.prepareStatement(
                "INSERT INTO passenger (name, passport) VALUES (?, ?)"
        );

        getByPassportSt = connection.prepareStatement(
                "SELECT id, name FROM passenger WHERE passport = ?"
        );

        getMaxIdSt = connection.prepareStatement(
                "SELECT max(id) AS MaxId FROM passenger"
        );

    }

    public long create(Passenger passenger) throws SQLException {

        createSt.setString(1, passenger.getName());
        createSt.setString(2, passenger.getPassport());
        createSt.executeUpdate();

        try(ResultSet rs = getMaxIdSt.executeQuery()) {

            rs.next();
            return rs.getLong("MaxId");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1l;
    }

    public Passenger getByPassport(String passport) throws SQLException {

        Passenger passenger = new Passenger();

        getByPassportSt.setString(1, passport);

        try (ResultSet rs = getByPassportSt.executeQuery()) {

            if (!rs.next()) {
                return null;
            }

            passenger.setId(rs.getLong("id"));
            passenger.setName(rs.getString("name"));
            passenger.setPassport(passport);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return passenger;
    }
}
