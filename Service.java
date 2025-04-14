public abstract class Service {
    private String name;
    private long latitude;
    private long longitude;
    private int starEvaluation;
    private int evaluationCount;

    public Service(String name, long latitude, long longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.starEvaluation = 4; // Default stars for new service
        this.evaluationCount = 1;
    }

    public String getName() {
        return name;
    }

    public long getLatitude() {
        return latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public int getStarEvaluation() {
        return Math.round((float) starEvaluation / evaluationCount);
    }

    public void addEvaluation(int stars) {
        if (stars < 1 || stars > 5) {
            throw new IllegalArgumentException("Invalid evaluation!");
        }
        starEvaluation += stars;
        evaluationCount++;
    }
}