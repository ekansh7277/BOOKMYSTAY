/**
 * RoomNotAvailableException - Thrown when a requested room type has no availability.
 */
public class RoomNotAvailableException extends Exception {
    public RoomNotAvailableException(String roomType) {
        super("No rooms available for type: \"" + roomType + "\".");
    }
}
