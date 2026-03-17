import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * UseCase10BookingCancellation - Cancels confirmed bookings and rolls back inventory.
 * Uses a Stack to track released room IDs for rollback support.
 * Prevents duplicate cancellation of the same reservation.
 */
public class UseCase10BookingCancellation {

    private Map<String, Reservation> confirmedBookings = new HashMap<>();
    private Stack<String> rollbackRoomIds = new Stack<>();
    private RoomInventory inventory;

    public UseCase10BookingCancellation(RoomInventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Registers a confirmed reservation for cancellation eligibility.
     * @param reservation a confirmed reservation
     */
    public void registerBooking(Reservation reservation) {
        if (reservation.isConfirmed()) {
            confirmedBookings.put(reservation.getReservationId(), reservation);
        }
    }

    /**
     * Cancels a booking, releases the room ID, and restores inventory.
     * @param reservationId the ID of the reservation to cancel
     */
    public void cancelBooking(String reservationId) {
        if (!confirmedBookings.containsKey(reservationId)) {
            System.out.println("CANCEL FAILED: Reservation [" + reservationId
                    + "] not found or already cancelled.");
            return;
        }

        Reservation reservation = confirmedBookings.remove(reservationId);
        String roomType = reservation.getRoomType();
        String roomId   = reservation.getAllocatedRoomId();

        int current = inventory.getAvailability(roomType);
        inventory.updateAvailability(roomType, current + 1);

        rollbackRoomIds.push(roomId);

        System.out.println("CANCELLED [" + reservationId + "] "
                + reservation.getGuestName()
                + " | Room " + roomId + " released back to inventory.");
    }

    /**
     * Displays the rollback stack of released room IDs.
     */
    public void displayRollbackStack() {
        System.out.println("\nRollback Stack (most recent release on top): " + rollbackRoomIds);
    }

    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();

        Reservation r1 = new Reservation("R001", "Alice",   "Single Room");
        Reservation r2 = new Reservation("R002", "Bob",     "Double Room");
        Reservation r3 = new Reservation("R003", "Charlie", "Suite Room");

        r1.confirm("S001");
        r2.confirm("D001");
        r3.confirm("U001");

        UseCase10BookingCancellation cancellation =
                new UseCase10BookingCancellation(inventory);

        cancellation.registerBooking(r1);
        cancellation.registerBooking(r2);
        cancellation.registerBooking(r3);

        System.out.println("=== Booking Cancellation & Inventory Rollback ===\n");
        System.out.println("Inventory Before Cancellation:");
        inventory.displayInventory();

        System.out.println();
        cancellation.cancelBooking("R001");
        cancellation.cancelBooking("R003");
        cancellation.cancelBooking("R001"); // duplicate – should fail

        System.out.println("\nInventory After Cancellation:");
        inventory.displayInventory();

        cancellation.displayRollbackStack();
    }
}
