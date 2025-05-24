package train_ticket;

public class TicketCanceling {
    private int pnr;
    private int seats;
    private TicketSystem ticketSystem;

    TicketCanceling(int pnr, int seats) {
        this.pnr = pnr;
        this.seats = seats;
        this.ticketSystem = TicketSystem.getInstance();
    }

    private void cancelTicket(){
        Ticket ticket=ticketSystem.getTicket(pnr);
        WaitingListManager waitingListManager = new WaitingListManager();


        if (ticket!=null ){
            if (ticket.getTicketStatus()==TicketStatus.WaitingList) {
                waitingListManager.waitingListRemoval(pnr, seats, ticket);
                return;
            }
            int bookedseats=ticket.getseats();
            char source = ticket.getsource(), destination = ticket.getdestination();

            if (bookedseats>seats){
                ticket.setseats(bookedseats-seats);
                ticketSystem.storePartiallyCanceledSeats(pnr, seats); // stores partially cancelled seats
                System.out.println("Partially cancelled pnr "+ pnr);
            }
             else {
                ticketSystem.cancelledProcess(pnr, ticket);
                System.out.println("Cancelled Ticket pnr "+ pnr);
            }
            ticketSystem.increaseSeatAvailability(source, destination,seats);
            waitingListManager.processWaitingList();
            
        }
        else{
            System.out.println("Ticket with pnr "+pnr+" not found");
        }

    }
    public void execute(){
        this.cancelTicket();
    }
    
}
