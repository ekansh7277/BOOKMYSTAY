/**
 * InvalidRoomTypeException - Thrown when an unrecognized room type is requested.
 */
public class InvalidRoomTypeException extends Exception {
    public InvalidRoomTypeException(String roomType) {
        super("Invalid room type: \"" + roomType + "\". Accepted types: Single Room, Double Room, Suite Room.");
    }
}
