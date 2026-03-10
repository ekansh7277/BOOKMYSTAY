/**
 * SingleRoom - Single occupancy room
 */
public class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 200, 100.0);
    }

    @Override
    public String getRoomType() {
        return "Single Room";
    }
}
