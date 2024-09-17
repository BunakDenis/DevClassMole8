package cli;

import passenger.Passenger;
import passenger.PassengerDaoService;
import storage.StorageConstance;
import ticket.Planet;
import ticket.Ticket;
import ticket.TicketDaoService;

import java.util.Scanner;

public class AddTicketStates extends CliState {
    public AddTicketStates(CliFSM fsm) {
        super(fsm);
    }

    @Override
    public void init() {

            System.out.println("Enter passenger passport:");

            Scanner sc = fsm.getSc();
            long passengerId = 0;

            String line = sc.nextLine();

            try {
                PassengerDaoService psDaoServ = new PassengerDaoService(
                        fsm.getConProv().createConnection()
                );

                Passenger passenger = psDaoServ.getByPassport(line.toUpperCase());

                if (passenger != null) {
                    System.out.println("Passenger " + passenger.getPassport() + " found. Enter FROM Planet:");
                } else {
                    passenger = new Passenger();
                    passenger.setPassport(line);
                    System.out.println("Enter passenger name: ");
                    String passengerName = sc.nextLine();

                    passenger.setName(passengerName);
                    passengerId = psDaoServ.create(passenger);
                    passenger.setId(passengerId);
                    System.out.println("Passenger saved. Enter FROM Planet:");
                }

                Planet planetFrom = new PlanetChooser(sc).ask();

                System.out.println("Planet " + planetFrom + " found. Enter TO Planet: ");

                Planet planetTo = new PlanetChooser(sc).ask();

                TicketDaoService ticketDaoService = new TicketDaoService(
                        fsm.getConProv().createConnection()
                );

                Ticket ticket = new Ticket();
                ticket.setPassenger_id(passenger.getId());
                ticket.setFrom(planetFrom);
                ticket.setTo(planetTo);

                long ticketId = ticketDaoService.create(ticket);


                System.out.println(planetTo + " found. Ticket ordered, ID : " + ticketId);
                fsm.setState(new IdleState(fsm));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}
