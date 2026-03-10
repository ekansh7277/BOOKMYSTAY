/**
 * UseCase2RoomInitialization - Demonstrates room types and static availability
 */
public class UseCase2RoomInitialization {
    public static void main(String[] args) {
        int singleRoomAvailability = 10;
        int doubleRoomAvailability = 8;
        int suiteRoomAvailability = 5;

        Room single = new SingleRoom();
        Room double_ = new DoubleRoom();
        Room suite = new SuiteRoom();

        System.out.println("=== Room Inventory ===\n");

        single.displayDetails();
        System.out.println("Available: " + singleRoomAvailability + "\n");

        double_.displayDetails();
        System.out.println("Available: " + doubleRoomAvailability + "\n");

        suite.displayDetails();
        System.out.println("Available: " + suiteRoomAvailability);
    }
}
