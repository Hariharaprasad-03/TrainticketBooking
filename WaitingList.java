package train_ticket;

public class WaitingList {
    private char source;
    private char destination;
    private int seats;
    private TicketSystem ticketSystem;

    WaitingList(char source, char destination, int seats) {
        this.source = source;
        this.destination = destination;
        this.seats = seats;
        this.ticketSystem = TicketSystem.getInstance();
    }
    private void addtoWaitingList(){
        Ticket ticket =new Ticket(source, destination, seats,TicketStatus.WaitingList);
        int pnrNumber = ticket.getpnrnumber();
        ticketSystem.WaitingList.put(pnrNumber, ticket);
        ticketSystem.seatsBooked += seats;

        System.out.println("Added to Waiting List with pnr number "+pnrNumber);
    }
    public void execute() {
        this.addtoWaitingList();
    }
}

    
    

