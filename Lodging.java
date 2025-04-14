public class Lodging extends Service {
    private int roomPrice;

    public Lodging(String name, long latitude, long longitude, int roomPrice) {
        super(name, latitude, longitude);
        if (roomPrice <= 0) {
            throw new IllegalArgumentException("Invalid room price!");
        }
        this.roomPrice = roomPrice;
    }

    public int getRoomPrice() {
        return roomPrice;
    }
}