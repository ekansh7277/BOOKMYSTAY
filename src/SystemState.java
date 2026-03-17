import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * SystemState - Serializable snapshot of booking history and inventory.
 * Used for file-based persistence and system recovery.
 */
public class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Reservation> bookingHistory;
    private HashMap<String, Integer> inventorySnapshot;

    public SystemState(List<Reservation> bookingHistory,
                       HashMap<String, Integer> inventorySnapshot) {
        this.bookingHistory    = bookingHistory;
        this.inventorySnapshot = inventorySnapshot;
    }

    public List<Reservation> getBookingHistory()          { return bookingHistory; }
    public HashMap<String, Integer> getInventorySnapshot() { return inventorySnapshot; }
}
