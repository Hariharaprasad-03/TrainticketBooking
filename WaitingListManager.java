package train_ticket;

public class WaitingListManager {
    
    private TicketSystem ticketSystem;

    protected WaitingListManager(){
        ticketSystem=TicketSystem.getInstance();
    }
    protected void processWaitingList(){
        for (Ticket waiting : ticketSystem.WaitingList.values()){
            boolean isvalid;
            char source = waiting.getsource(), destination = waiting.getdestination();
            int seats = waiting.getseats();

            // seat availability check (method re-use)
            isvalid = ticketSystem.checkSeatAvailability(source, destination, seats);

            if (isvalid){
                ticketSystem.setSeatsBooked(ticketSystem.getSeatsBooked()-seats);
                ticketSystem.decreaseSeatAvailability(source, destination, seats);
                updateTickettoBookingList(waiting);
            }
        }
    }
    private void updateTickettoBookingList(Ticket waiting){
        Integer pnr=waiting.getpnrnumber();
        waiting.setTicketStatus(TicketStatus.Booked);
        ticketSystem.addToBookedTickets(pnr, waiting);
        ticketSystem.WaitingList.remove(pnr);
        System.out.println("Booking Confirmed for pnr number "+pnr);

    }
     protected void waitingListEntry(char source, char destination, int seats) {
        WaitingList wl = new WaitingList(source, destination, seats);
        wl.execute();
    }
    protected void waitingListRemoval(int pnr, int seatsToCancel, Ticket waitingListTicket) {
        int seatsBooked  = waitingListTicket.getseats();
        if(seatsBooked == seatsToCancel) {
            ticketSystem.WaitingList.remove(pnr);
            System.out.println("Canceled ticket in waiting list with pnr "+ pnr);
        }
        else {
            waitingListTicket.setseats(waitingListTicket.getseats() - seatsToCancel);
            System.out.println("Partially canceled ticket in waiting list with pnr "+ pnr);
        }
        // update waiting list seat tracker (assuming user can't able to give seats more than he booked)
        ticketSystem.setSeatsBooked(ticketSystem.getSeatsBooked() - seatsToCancel);
    }
}
