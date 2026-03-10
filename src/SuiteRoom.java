/**
 * SuiteRoom - Luxury suite room
 */
public class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 500, 300.0);
    }

    @Override
    public String getRoomType() {
        return "Suite Room";
    }
}
