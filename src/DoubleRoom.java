/**
 * DoubleRoom - Double occupancy room
 */
public class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 300, 150.0);
    }

    @Override
    public String getRoomType() {
        return "Double Room";
    }
}
