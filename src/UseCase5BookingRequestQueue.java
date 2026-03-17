import java.util.LinkedList;
import java.util.Queue;

/**
 * UseCase5BookingRequestQueue - Accepts booking requests in FIFO order.
 * Does NOT allocate rooms or modify inventory.
 */
public class UseCase5BookingRequestQueue {
    public static void main(String[] args) {
        Queue<Reservation> bookingQueue = new LinkedList<>();

        bookingQueue.add(new Reservation("R001", "Alice",   "Single Room"));
        bookingQueue.add(new Reservation("R002", "Bob",     "Double Room"));
        bookingQueue.add(new Reservation("R003", "Charlie", "Suite Room"));
        bookingQueue.add(new Reservation("R004", "Diana",   "Single Room"));

        System.out.println("=== Booking Request Queue (FIFO) ===");
        System.out.println("Total requests: " + bookingQueue.size() + "\n");

        for (Reservation r : bookingQueue) {
            System.out.println(r);
        }
    }
}
