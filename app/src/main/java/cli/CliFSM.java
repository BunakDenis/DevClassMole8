package cli;

import lombok.Getter;
import lombok.Setter;
import storage.ConnectionProvider;

import java.util.Scanner;

public class CliFSM {

    private CliState state;
    @Getter
    private Scanner sc;
    @Getter
    private ConnectionProvider conProv;

    public CliFSM(ConnectionProvider conProv) {
        state = new IdleState(this);
        this.conProv = conProv;

        sc = new Scanner(System.in);

        startInputLoop();

    }

    private void startInputLoop() {
        while (true) {
            String command = sc.nextLine();

            switch (command) {
                case "exit":
                    System.exit(0);
                    break;

                case "addTicket":
                    newTicketRequested();
                    break;

                case "planetStats":
                    planetStatesRequested();
                    break;

                default:
                    unknownCommand(command);
            }

        }
    }

    public void newTicketRequested() {
        state.newTicketRequested();
    }

    public void ticketOrdered() {
        state.ticketOrdered();
    }

    public void planetStatesRequested() {
        state.planetStatesRequested();
    }

    public void planetStatesPrinted() {
        state.planetStatesPrinted();
    }

    public void unknownCommand(String cmd) {
        state.unknownCommand(cmd);
    }

    public void setState(CliState cliState) {
        this.state = cliState;

        state.init();
    }

}
