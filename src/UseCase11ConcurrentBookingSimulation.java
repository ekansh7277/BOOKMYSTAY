import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * UseCase11ConcurrentBookingSimulation - Simulates multiple guests booking simultaneously.
 * Uses synchronized blocks to protect shared inventory and prevent double booking.
 */
public class UseCase11ConcurrentBookingSimulation {

    private final RoomInventory inventory;
    private final List<String> confirmedBookings =
            Collections.synchronizedList(new ArrayList<>());
    private final LinkedBlockingQueue<Reservation> bookingQueue =
            new LinkedBlockingQueue<>();

    public UseCase11ConcurrentBookingSimulation(RoomInventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Thread-safe booking method. Checks availability and allocates room atomically.
     * @param reservation the booking request
     */
    public synchronized void book(Reservation reservation) {
        String roomType = reservation.getRoomType();
        int available = inventory.getAvailability(roomType);

        if (available <= 0) {
            System.out.println(Thread.currentThread().getName()
                    + " | FAILED  [" + reservation.getReservationId() + "] "
                    + reservation.getGuestName() + " – No " + roomType + " available.");
            return;
        }

        inventory.updateAvailability(roomType, available - 1);
        reservation.confirm(roomType.substring(0, 1) + String.format("%03d", available));
        confirmedBookings.add(reservation.getReservationId());

        System.out.println(Thread.currentThread().getName()
                + " | BOOKED  " + reservation);
    }

    /**
     * Guest thread that submits a booking request.
     */
    private class GuestThread extends Thread {
        private final Reservation reservation;

        GuestThread(String name, Reservation reservation) {
            super(name);
            this.reservation = reservation;
        }

        @Override
        public void run() {
            book(reservation);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        RoomInventory inventory = new RoomInventory();
        inventory.updateAvailability("Single Room", 2);

        UseCase11ConcurrentBookingSimulation simulation =
                new UseCase11ConcurrentBookingSimulation(inventory);

        System.out.println("=== Concurrent Booking Simulation ===");
        System.out.println("Single Room availability: 2 (5 guests competing)\n");

        Thread[] guests = {
                simulation.new GuestThread("Guest-Alice",   new Reservation("R001", "Alice",   "Single Room")),
                simulation.new GuestThread("Guest-Bob",     new Reservation("R002", "Bob",     "Single Room")),
                simulation.new GuestThread("Guest-Charlie", new Reservation("R003", "Charlie", "Single Room")),
                simulation.new GuestThread("Guest-Diana",   new Reservation("R004", "Diana",   "Single Room")),
                simulation.new GuestThread("Guest-Eve",     new Reservation("R005", "Eve",     "Single Room"))
        };

        for (Thread t : guests) t.start();
        for (Thread t : guests) t.join();

        System.out.println("\n=== Final Inventory ===");
        inventory.displayInventory();
        System.out.println("\nTotal Confirmed: " + simulation.confirmedBookings.size());
    }
}
