package ticket;

import lombok.Data;

@Data
public class Ticket {
    long id;
    long passenger_id;
    Planet from;
    Planet to;
}
