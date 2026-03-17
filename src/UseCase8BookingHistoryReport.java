import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * UseCase8BookingHistoryReport - Stores confirmed reservations and generates reports.
 * Maintains chronological booking history for admin retrieval.
 */
public class UseCase8BookingHistoryReport {

    private List<Reservation> bookingHistory = new ArrayList<>();

    /**
     * Records a confirmed reservation into history.
     * @param reservation the confirmed reservation to store
     */
    public void recordBooking(Reservation reservation) {
        if (reservation.isConfirmed()) {
            bookingHistory.add(reservation);
        }
    }

    /**
     * Displays all bookings in chronological order.
     */
    public void displayHistory() {
        System.out.println("=== Booking History ===");
        if (bookingHistory.isEmpty()) {
            System.out.println("No bookings recorded.");
            return;
        }
        for (int i = 0; i < bookingHistory.size(); i++) {
            System.out.println((i + 1) + ". " + bookingHistory.get(i));
        }
    }

    /**
     * Generates a summary report grouped by room type.
     */
    public void generateSummaryReport() {
        System.out.println("\n=== Summary Report ===");
        System.out.println("Total Bookings: " + bookingHistory.size());

        Map<String, Long> countByType = bookingHistory.stream()
                .collect(Collectors.groupingBy(Reservation::getRoomType, Collectors.counting()));

        System.out.println("\nBookings by Room Type:");
        countByType.forEach((type, count) ->
                System.out.println("  " + type + ": " + count + " booking(s)"));
    }

    public static void main(String[] args) {
        UseCase8BookingHistoryReport report = new UseCase8BookingHistoryReport();

        Reservation r1 = new Reservation("R001", "Alice",   "Single Room");
        Reservation r2 = new Reservation("R002", "Bob",     "Double Room");
        Reservation r3 = new Reservation("R003", "Charlie", "Suite Room");
        Reservation r4 = new Reservation("R004", "Diana",   "Single Room");
        Reservation r5 = new Reservation("R005", "Eve",     "Double Room");

        r1.confirm("S001");
        r2.confirm("D001");
        r3.confirm("U001");
        r4.confirm("S002");
        // r5 is intentionally left unconfirmed

        report.recordBooking(r1);
        report.recordBooking(r2);
        report.recordBooking(r3);
        report.recordBooking(r4);
        report.recordBooking(r5);

        report.displayHistory();
        report.generateSummaryReport();
    }
}
