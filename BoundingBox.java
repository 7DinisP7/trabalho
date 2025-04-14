public class BoundingBox {
    private long topLatitude;
    private long leftLongitude;
    private long bottomLatitude;
    private long rightLongitude;

    public BoundingBox(long topLatitude, long leftLongitude, long bottomLatitude, long rightLongitude) {
        if (topLatitude <= bottomLatitude || leftLongitude >= rightLongitude) {
            throw new IllegalArgumentException("Invalid bounds.");
        }
        this.topLatitude = topLatitude;
        this.leftLongitude = leftLongitude;
        this.bottomLatitude = bottomLatitude;
        this.rightLongitude = rightLongitude;
    }

    public boolean isInside(long latitude, long longitude) {
        return latitude <= topLatitude && latitude >= bottomLatitude &&
               longitude >= leftLongitude && longitude <= rightLongitude;
    }
}