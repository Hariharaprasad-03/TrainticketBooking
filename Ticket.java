package train_ticket;

public class Ticket {
    private static int pnrGenerator =1;

    private static final int pnrNumber = pnrGenerator++;
    private  final char source ;
    private  final char destination ;
    private  int seats;
    private TicketStatus ticketStatus;

    protected Ticket(char source, char destination, int seats, TicketStatus ticketStatus) {
        this.source = source;
        this.destination = destination;
        this.seats = seats;
        this.ticketStatus = ticketStatus;
    }
    
    public int getpnrnumber (){
        return pnrNumber;
    }

    public char getsource(){
        return source;
    }

    public char getdestination(){
        return destination;
    }

    public void setseats (int seats){
        this.seats =seats;
    }

    public int getseats(){
        return seats;

    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "pnrNumber=" + pnrNumber +
                ", source=" + source +
                ", destination=" + destination +
                ", seats=" + seats +
                ", ticketStatus=" + ticketStatus +
                '}';
    }

}
