/**
 * RoomSearchService - Provides read-only access to room inventory
 * Filters and displays available rooms without modifying inventory
 */
public class RoomSearchService {
    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void displayAvailableRooms() {
        String[] roomTypes = {"Single Room", "Double Room", "Suite Room"};
        double[] prices = {100.0, 150.0, 300.0};

        for (int i = 0; i < roomTypes.length; i++) {
            int availability = inventory.getAvailability(roomTypes[i]);
            if (availability > 0) {
                System.out.println("Room Type: " + roomTypes[i]);
                System.out.println("Price: $" + prices[i]);
                System.out.println("Available: " + availability);
                System.out.println();
            }
        }
    }
}
