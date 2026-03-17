import java.util.Arrays;
import java.util.List;

/**
 * UseCase9ErrorHandlingValidation - Validates booking input and handles errors gracefully.
 * Implements fail-fast design with custom exceptions.
 */
public class UseCase9ErrorHandlingValidation {

    private static final List<String> VALID_ROOM_TYPES =
            Arrays.asList("Single Room", "Double Room", "Suite Room");

    private RoomInventory inventory;

    public UseCase9ErrorHandlingValidation(RoomInventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Validates and processes a booking request.
     * @param guestName the name of the guest
     * @param roomType  the requested room type
     * @throws InvalidRoomTypeException  if the room type is not recognized
     * @throws RoomNotAvailableException if no rooms of that type are available
     * @throws IllegalArgumentException  if guest name is blank
     */
    public void validateAndBook(String guestName, String roomType)
            throws InvalidRoomTypeException, RoomNotAvailableException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new IllegalArgumentException("Guest name must not be empty.");
        }

        if (!VALID_ROOM_TYPES.contains(roomType)) {
            throw new InvalidRoomTypeException(roomType);
        }

        int available = inventory.getAvailability(roomType);
        if (available <= 0) {
            throw new RoomNotAvailableException(roomType);
        }

        inventory.updateAvailability(roomType, available - 1);
        System.out.println("Booking validated for " + guestName + " | " + roomType);
    }

    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        inventory.updateAvailability("Suite Room", 0);

        UseCase9ErrorHandlingValidation validator =
                new UseCase9ErrorHandlingValidation(inventory);

        System.out.println("=== Error Handling & Validation ===\n");

        String[][] testCases = {
                {"Alice",   "Single Room"},
                {"",        "Double Room"},
                {"Charlie", "Penthouse"},
                {"Diana",   "Suite Room"},
                {"Eve",     "Single Room"}
        };

        for (String[] tc : testCases) {
            try {
                validator.validateAndBook(tc[0], tc[1]);
            } catch (IllegalArgumentException e) {
                System.out.println("VALIDATION ERROR: " + e.getMessage());
            } catch (InvalidRoomTypeException e) {
                System.out.println("INVALID ROOM: " + e.getMessage());
            } catch (RoomNotAvailableException e) {
                System.out.println("NOT AVAILABLE: " + e.getMessage());
            }
        }

        System.out.println("\n=== Inventory After Validation ===");
        inventory.displayInventory();
    }
}
