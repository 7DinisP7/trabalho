public class ThriftyStudent extends Student {
    private Eating cheapestEating;
    private Lodging cheapestLodging;

    public ThriftyStudent(String name, Lodging home) {
        super(name, home);
    }

    @Override
    public void visit(Service service) {
        if (service instanceof Eating) {
            Eating eating = (Eating) service;
            if (cheapestEating == null || eating.getMenuPrice() < cheapestEating.getMenuPrice()) {
                cheapestEating = eating;
            }
        } else if (service instanceof Lodging) {
            Lodging lodging = (Lodging) service;
            if (cheapestLodging == null || lodging.getRoomPrice() < cheapestLodging.getRoomPrice()) {
                cheapestLodging = lodging;
            }
        }
    }

    public Eating getCheapestEating() {
        return cheapestEating;
    }

    public Lodging getCheapestLodging() {
        return cheapestLodging;
    }
}