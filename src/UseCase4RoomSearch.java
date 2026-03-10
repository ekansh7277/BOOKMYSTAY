/**
 * UseCase4RoomSearch - Demonstrates room search and availability check
 */
public class UseCase4RoomSearch {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        RoomSearchService searchService = new RoomSearchService(inventory);

        System.out.println("=== Available Rooms ===\n");
        searchService.displayAvailableRooms();
    }
}
