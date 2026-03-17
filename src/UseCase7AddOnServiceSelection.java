import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UseCase7AddOnServiceSelection - Attaches optional services to reservations.
 * Does NOT modify booking or inventory state.
 */
public class UseCase7AddOnServiceSelection {

    private Map<String, List<Service>> reservationServices = new HashMap<>();

    /**
     * Adds a service to the specified reservation.
     * @param reservationId the target reservation ID
     * @param service       the service to attach
     */
    public void addService(String reservationId, Service service) {
        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    /**
     * Calculates total add-on cost for a reservation.
     * @param reservationId the reservation ID
     * @return total cost of all attached services
     */
    public double getTotalAddOnCost(String reservationId) {
        List<Service> services = reservationServices.getOrDefault(reservationId, new ArrayList<>());
        return services.stream().mapToDouble(Service::getCost).sum();
    }

    /**
     * Displays all services attached to a reservation.
     * @param reservationId the reservation ID
     */
    public void displayServices(String reservationId) {
        List<Service> services = reservationServices.getOrDefault(reservationId, new ArrayList<>());
        System.out.println("Services for Reservation [" + reservationId + "]:");
        if (services.isEmpty()) {
            System.out.println("  No add-on services selected.");
            return;
        }
        services.forEach(s -> System.out.println("  - " + s));
        System.out.printf("  Total Add-On Cost: $%.2f%n", getTotalAddOnCost(reservationId));
    }

    public static void main(String[] args) {
        UseCase7AddOnServiceSelection selector = new UseCase7AddOnServiceSelection();

        selector.addService("R001", new Service("Breakfast",      15.0));
        selector.addService("R001", new Service("Airport Pickup", 30.0));
        selector.addService("R002", new Service("Spa Package",    50.0));
        selector.addService("R003", new Service("Breakfast",      15.0));
        selector.addService("R003", new Service("Late Checkout",  20.0));
        selector.addService("R003", new Service("Room Service",   25.0));

        System.out.println("=== Add-On Service Selection ===\n");
        selector.displayServices("R001");
        System.out.println();
        selector.displayServices("R002");
        System.out.println();
        selector.displayServices("R003");
    }
}
