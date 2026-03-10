/**
 * UseCase3InventorySetup - Demonstrates centralized inventory management
 */
public class UseCase3InventorySetup {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();

        System.out.println("Initial Inventory:");
        inventory.displayInventory();

        System.out.println("\nChecking availability:");
        System.out.println("Single Room: " + inventory.getAvailability("Single Room"));

        System.out.println("\nUpdating inventory...");
        inventory.updateAvailability("Single Room", 9);

        System.out.println("\nUpdated Inventory:");
        inventory.displayInventory();
    }
}
