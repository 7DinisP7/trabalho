public abstract class Student {
    private String name;
    private Service currentLocation;
    private Service home;

    public Student(String name, Service home) {
        this.name = name;
        this.home = home;
        this.currentLocation = home;
    }

    public String getName() {
        return name;
    }

    public Service getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Service location) {
        this.currentLocation = location;
    }

    public Service getHome() {
        return home;
    }

    public void setHome(Service home) {
        this.home = home;
        this.currentLocation = home;
    }

    public abstract void visit(Service service);
}