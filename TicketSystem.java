package train_ticket;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class TicketSystem {

    protected Map<Integer,Ticket>TicketsBooked = new HashMap<>();
    protected Map<Integer,Ticket>TicketsCanceled = new HashMap<>();
    protected ConcurrentHashMap<Integer,Ticket> WaitingList = new ConcurrentHashMap<>();

    protected int[] seatsAvailable = new int[5];
    protected Map<Integer,Integer> PartialyCanceled = new HashMap<>();

    protected int seatsBooked=0;

    //singleton logic 

    protected static TicketSystem instance=null;

    private TicketSystem() {                    // constructor private 
        Arrays.fill(seatsAvailable, 8);
    }

    public static TicketSystem getInstance(){
        if (instance==null){
            instance = new TicketSystem();
        }
        return instance;
    }

    //
    protected void setSeatsBooked(int seatsBooked) {
        this.seatsBooked = seatsBooked;
    }

    protected int getSeatsBooked() {
        return seatsBooked;
    }

    // --- methods used in booking class ---
    // used in Booking as well as WaitingListManager class

    public void addToBookedTickets(int newPnr, Ticket ticket) {
        TicketsBooked.put(newPnr, ticket);
    }

     protected void decreaseSeatAvailability(char source, char destination, int seats) {
        for(int i=source - 'A';i<destination - 'A';i++) {
            seatsAvailable[i] -= seats;
        }
    }

    // used in Booking as well as WaitingListManager class

    protected boolean checkSeatAvailability(char source, char destination, int seats) {
        for(int i=source - 'A';i<destination - 'A';i++) {
            if(seatsAvailable[i] < seats) {
                return false;
            }
        }
        return true;
    }
     protected Ticket getTicket(int pnr) {
        Ticket bookedTicket = TicketsBooked.get(pnr);
        return bookedTicket != null ? bookedTicket : WaitingList.get(pnr);
    }

    protected void increaseSeatAvailability(char source, char destination, int seats) {
       for (int i= source-'A';i<destination-'A';i++){
            seatsAvailable[i]+=seats;
       }
    }
    protected void storePartiallyCanceledSeats(int pnr, int seats) {
        PartialyCanceled.merge(pnr, seats, Integer::sum);
        // Same as --> partiallyCanceled.put(pnr, partiallyCanceled.getOrDefault(pnr, 0) + seats);
    }
    protected void cancelledProcess(int pnr ,Ticket ticket){
        Integer getseats= PartialyCanceled.get(pnr);
        int addToseats = getseats == null ? 0:getseats;
        ticket.setseats(ticket.getseats()+addToseats);
        addToCanceledTickets(pnr, ticket);

    }
    protected void addToCanceledTickets(int pnr,Ticket t1){
        t1.setTicketStatus(TicketStatus.Canceled);
        TicketsCanceled.put(pnr,t1);
        removefromBookedTickets(pnr,t1);

    }
    protected void removefromBookedTickets(int pnr ,Ticket t1){
        TicketsBooked.remove(pnr);

    }

// prints all details
    public void printChart() {
        System.out.println("\nTickets Booked :");
        TicketsBooked.values().forEach(System.out::println);

        System.out.println("\nTickets Canceled :");
        TicketsCanceled.values().forEach(System.out::println);

        System.out.println("\nTickets in Waiting List :");
        WaitingList.values().forEach(System.out::println);

        System.out.println("\nSeat Availability : "+Arrays.toString(seatsAvailable));

        System.out.println("\n\t\tSeats Booked : ");
        System.out.println("\t1\t2\t3\t4\t5\t6\t7\t8");
        for(char c ='A';c<='E';c++) {
            System.out.print(c);
            int seatsBooked = 8 - seatsAvailable[c - 'A']; // total seats (8) - available seats = booked seats
            for(int i=0;i<seatsBooked;i++) {
                System.out.print("\t*");
            }
            System.out.println();
        }  
}
}