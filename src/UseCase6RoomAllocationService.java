import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * UseCase6RoomAllocationService - Dequeues booking requests, checks availability,
 * generates unique room IDs, prevents duplicate allocation, and updates inventory.
 */
public class UseCase6RoomAllocationService {

    private Set<String> allocatedRoomIds = new HashSet<>();
    private HashMap<String, Set<String>> roomTypeToAllocatedRooms = new HashMap<>();
    private RoomInventory inventory;

    public UseCase6RoomAllocationService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Processes all requests in the queue and allocates rooms where available.
     * @param bookingQueue queue of pending reservations
     */
    public void processQueue(Queue<Reservation> bookingQueue) {
        while (!bookingQueue.isEmpty()) {
            Reservation reservation = bookingQueue.poll();
            allocate(reservation);
        }
    }

    /**
     * Allocates a room for a single reservation if available.
     * @param reservation the booking request to process
     */
    private void allocate(Reservation reservation) {
        String roomType = reservation.getRoomType();
        int available = inventory.getAvailability(roomType);

        if (available <= 0) {
            System.out.println("FAILED  [" + reservation.getReservationId() + "] "
                    + reservation.getGuestName() + " – No " + roomType + " available.");
            return;
        }

        String roomId = generateRoomId(roomType);

        if (allocatedRoomIds.contains(roomId)) {
            System.out.println("DUPLICATE [" + roomId + "] – Skipping.");
            return;
        }

        allocatedRoomIds.add(roomId);
        roomTypeToAllocatedRooms
                .computeIfAbsent(roomType, k -> new HashSet<>())
                .add(roomId);

        inventory.updateAvailability(roomType, available - 1);
        reservation.confirm(roomId);

        System.out.println("CONFIRMED " + reservation);
    }

    /**
     * Generates a unique room ID based on room type and current allocation count.
     * @param roomType the type of room
     * @return unique room ID string
     */
    private String generateRoomId(String roomType) {
        String prefix = roomType.substring(0, 1).toUpperCase();
        int count = roomTypeToAllocatedRooms.getOrDefault(roomType, new HashSet<>()).size() + 1;
        return prefix + String.format("%03d", count);
    }

    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();

        Queue<Reservation> bookingQueue = new LinkedList<>();
        bookingQueue.add(new Reservation("R001", "Alice",   "Single Room"));
        bookingQueue.add(new Reservation("R002", "Bob",     "Double Room"));
        bookingQueue.add(new Reservation("R003", "Charlie", "Suite Room"));
        bookingQueue.add(new Reservation("R004", "Diana",   "Single Room"));
        bookingQueue.add(new Reservation("R005", "Eve",     "Suite Room"));

        System.out.println("=== Room Allocation Service ===\n");
        UseCase6RoomAllocationService service = new UseCase6RoomAllocationService(inventory);
        service.processQueue(bookingQueue);

        System.out.println("\n=== Updated Inventory ===");
        inventory.displayInventory();
    }
}
