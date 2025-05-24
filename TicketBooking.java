package train_ticket;



public class TicketBooking {

    private char source;
    private char destination;
    private int seats;
    private TicketSystem ticketsystem;

    TicketBooking(char source, char destination, int seats) {
        this.source = source;
        this.destination = destination;
        this.seats = seats;
        this.ticketsystem = TicketSystem.getInstance();
    }

    private void bookTicket(){
        if (ticketsystem.checkSeatAvailability(source, destination, seats)){
            Ticket ticket=new Ticket(source, destination, seats,   TicketStatus.Booked);
            int pnr = ticket.getpnrnumber();
            ticketsystem.addToBookedTickets(pnr, ticket);
            System.out.println("Ticket Booked! your PNR number is "+ pnr);
            ticketsystem.decreaseSeatAvailability(source,destination,seats);
        }
        else{
            if (ticketsystem.getSeatsBooked()+seats >2){
                System.out.println("no seats avalilable from"+ source + "-->" +destination);


            }
            else {
                // move to waiting list
                WaitingListManager waitingListManager = new WaitingListManager();
                waitingListManager.waitingListEntry(source, destination, seats);
            }
        }
    }
    protected void execute() {
        this.bookTicket();
    }
    
}
