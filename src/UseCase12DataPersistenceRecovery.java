import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * UseCase12DataPersistenceRecovery - Saves and restores system state using serialization.
 * Handles missing or corrupted files safely during recovery.
 */
public class UseCase12DataPersistenceRecovery {

    private static final String STATE_FILE = "system_state.dat";

    /**
     * Serializes and saves the system state to a file.
     * @param state the current system state to persist
     */
    public void saveState(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(STATE_FILE))) {
            oos.writeObject(state);
            System.out.println("System state saved to: " + STATE_FILE);
        } catch (IOException e) {
            System.out.println("ERROR saving state: " + e.getMessage());
        }
    }

    /**
     * Deserializes and restores system state from file.
     * Returns null safely if file is missing or corrupted.
     * @return restored SystemState, or null if recovery fails
     */
    public SystemState loadState() {
        File file = new File(STATE_FILE);

        if (!file.exists()) {
            System.out.println("No saved state found. Starting fresh.");
            return null;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(file))) {
            SystemState state = (SystemState) ois.readObject();
            System.out.println("System state restored from: " + STATE_FILE);
            return state;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR loading state (corrupted file): " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        UseCase12DataPersistenceRecovery persistence =
                new UseCase12DataPersistenceRecovery();

        // --- Build state to persist ---
        Reservation r1 = new Reservation("R001", "Alice",   "Single Room");
        Reservation r2 = new Reservation("R002", "Bob",     "Double Room");
        r1.confirm("S001");
        r2.confirm("D001");

        List<Reservation> history = new ArrayList<>();
        history.add(r1);
        history.add(r2);

        HashMap<String, Integer> inventorySnapshot = new HashMap<>();
        inventorySnapshot.put("Single Room", 9);
        inventorySnapshot.put("Double Room", 7);
        inventorySnapshot.put("Suite Room",  5);

        SystemState stateToSave = new SystemState(history, inventorySnapshot);

        System.out.println("=== Data Persistence & System Recovery ===\n");

        // --- Save ---
        System.out.println("--- Saving State ---");
        persistence.saveState(stateToSave);

        // --- Restore ---
        System.out.println("\n--- Restoring State ---");
        SystemState restored = persistence.loadState();

        if (restored != null) {
            System.out.println("\nRestored Booking History:");
            restored.getBookingHistory().forEach(r -> System.out.println("  " + r));

            System.out.println("\nRestored Inventory:");
            restored.getInventorySnapshot()
                    .forEach((type, count) ->
                            System.out.println("  " + type + ": " + count + " available"));
        }
    }
}
