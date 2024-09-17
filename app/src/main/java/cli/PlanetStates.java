package cli;

import storage.StorageConstance;
import ticket.Planet;
import ticket.TicketDaoService;

public class PlanetStates extends CliState{
    public PlanetStates(CliFSM fsm) {
        super(fsm);
    }

    @Override
    public void init() {
        System.out.println("Enter TO planet:");

        Planet planet = new PlanetChooser(fsm.getSc()).ask();

        try {
            TicketDaoService ticketDaoService = new TicketDaoService(
                    fsm.getConProv().createConnection()
            );

            long ticketCountToPlanet = ticketDaoService.getTicketCountToPlanet(planet);
            System.out.println(planet.toString() + " found. Ticket count : " + ticketCountToPlanet);
            fsm.setState(new IdleState(fsm));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}