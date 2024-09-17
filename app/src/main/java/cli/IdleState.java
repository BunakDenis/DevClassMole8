package cli;

public class IdleState extends CliState {
    public IdleState(CliFSM fsm) {
        super(fsm);
    }

    @Override
    public void unknownCommand(String cmd) {
        System.out.println("Unknown command - " + cmd);
    }

    @Override
    public void newTicketRequested() {
        fsm.setState(new AddTicketStates(fsm));
    }

    @Override
    public void planetStatesRequested() {
        fsm.setState(new PlanetStates(fsm));
    }
}
