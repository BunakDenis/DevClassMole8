package cli;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CliState {

    protected final CliFSM fsm;

    public void newTicketRequested() {

    }

    public void ticketOrdered() {

    }

    public void planetStatesRequested() {

    }

    public void planetStatesPrinted() {

    }

    public void unknownCommand(String cmd) {

    }

    public void init() {

    }
}
