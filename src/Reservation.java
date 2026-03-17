import java.io.Serializable;

/**
 * Reservation - Represents a guest booking request.
 * Used across multiple use cases as the core booking model.
 */
public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;
    private boolean confirmed;
    private String allocatedRoomId;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.confirmed = false;
        this.allocatedRoomId = null;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName()     { return guestName; }
    public String getRoomType()      { return roomType; }
    public boolean isConfirmed()     { return confirmed; }
    public String getAllocatedRoomId() { return allocatedRoomId; }

    public void confirm(String roomId) {
        this.confirmed = true;
        this.allocatedRoomId = roomId;
    }

    @Override
    public String toString() {
        return "[" + reservationId + "] " + guestName + " | " + roomType
                + " | Confirmed: " + confirmed
                + (allocatedRoomId != null ? " | Room: " + allocatedRoomId : "");
    }
}
