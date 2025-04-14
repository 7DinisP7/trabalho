public class Eating extends Service {
    private int menuPrice;

    public Eating(String name, long latitude, long longitude, int menuPrice) {
        super(name, latitude, longitude);
        if (menuPrice <= 0) {
            throw new IllegalArgumentException("Invalid menu price!");
        }
        this.menuPrice = menuPrice;
    }

    public int getMenuPrice() {
        return menuPrice;
    }
}