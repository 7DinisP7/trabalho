import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandInterpreter {
    private BoundingBox boundingBox;
    protected Map<String, Service> services;
    protected Map<String, Student> students;

    public CommandInterpreter() {
        this.services = new HashMap<>();
        this.students = new HashMap<>();
    }

    // Command: bounds
    public void bounds(long topLat, long leftLong, long bottomLat, long rightLong, String name) {
        try {
            this.boundingBox = new BoundingBox(topLat, leftLong, bottomLat, rightLong);
            services.clear();
            students.clear();
            System.out.println(name + " created.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid bounds.");
        }
    }

    // Command: eating
    public void addEatingService(String name, long latitude, long longitude, int menuPrice) {
        if (boundingBox == null) {
            System.out.println("System bounds not defined.");
            return;
        }
        if (!boundingBox.isInside(latitude, longitude)) {
            System.out.println(name + " location invalid!");
            return;
        }
        if (services.containsKey(name)) {
            System.out.println(name + " already exists!");
            return;
        }
        if (menuPrice <= 0) {
            System.out.println("Invalid menu price!");
            return;
        }

        services.put(name, new Eating(name, latitude, longitude, menuPrice));
        System.out.println(name + " added.");
    }

    // Command: lodging
    public void addLodgingService(String name, long latitude, long longitude, int roomPrice) {
        if (boundingBox == null) {
            System.out.println("System bounds not defined.");
            return;
        }
        if (!boundingBox.isInside(latitude, longitude)) {
            System.out.println(name + " location invalid!");
            return;
        }
        if (services.containsKey(name)) {
            System.out.println(name + " already exists!");
            return;
        }
        if (roomPrice <= 0) {
            System.out.println("Invalid room price!");
            return;
        }

        services.put(name, new Lodging(name, latitude, longitude, roomPrice));
        System.out.println(name + " added.");
    }

    // Command: leisure
    public void addLeisureService(String name, long latitude, long longitude, int ticketPrice, int studentDiscount) {
        if (boundingBox == null) {
            System.out.println("System bounds not defined.");
            return;
        }
        if (!boundingBox.isInside(latitude, longitude)) {
            System.out.println(name + " location invalid!");
            return;
        }
        if (services.containsKey(name)) {
            System.out.println(name + " already exists!");
            return;
        }
        if (ticketPrice <= 0) {
            System.out.println("Invalid ticket price!");
            return;
        }
        if (studentDiscount < 0 || studentDiscount > 100) {
            System.out.println("Invalid discount!");
            return;
        }

        services.put(name, new Leisure(name, latitude, longitude, ticketPrice, studentDiscount));
        System.out.println(name + " added.");
    }

    // Command: services
    public void listServices() {
        if (services.isEmpty()) {
            System.out.println("No services yet!");
            return;
        }
        services.forEach((name, service) -> {
            String type = service.getClass().getSimpleName().toLowerCase();
            System.out.println(name + ": " + type + " " + service.getLatitude() + " " + service.getLongitude() + ".");
        });
    }

    // Command: student
    public void addStudent(String type, String name, String homeName) {
        if (boundingBox == null) {
            System.out.println("System bounds not defined.");
            return;
        }
        if (students.containsKey(name)) {
            System.out.println(name + " already exists!");
            return;
        }
        if (!services.containsKey(homeName) || !(services.get(homeName) instanceof Lodging)) {
            System.out.println("Lodging " + homeName + " does not exist!");
            return;
        }

        Lodging home = (Lodging) services.get(homeName);
        Student student;

        switch (type.toLowerCase()) {
            case "bookish":
                student = new BookishStudent(name, home);
                break;
            case "outgoing":
                student = new OutgoingStudent(name, home);
                break;
            case "thrifty":
                student = new ThriftyStudent(name, home);
                break;
            default:
                System.out.println("Invalid student type!");
                return;
        }

        students.put(name, student);
        System.out.println(name + " added.");
    }

    // Command: students
    public void listStudents() {
        if (students.isEmpty()) {
            System.out.println("No students yet!");
            return;
        }
        students.forEach((name, student) -> {
            String type = student.getClass().getSimpleName().replace("Student", "").toLowerCase();
            System.out.println(name + ": " + type + " at " + student.getCurrentLocation().getName() + ".");
        });
    }

    // Command: leave
    public void removeStudent(String name) {
        if (!students.containsKey(name)) {
            System.out.println(name + " does not exist!");
            return;
        }
        students.remove(name);
        System.out.println(name + " has left.");
    }

    // Command: go
    public void go(String studentName, String locationName) {
        if (!students.containsKey(studentName)) {
            System.out.println(studentName + " does not exist!");
            return;
        }

        Student student = students.get(studentName);

        if (locationName.equalsIgnoreCase("home")) {
            if (student.getCurrentLocation().equals(student.getHome())) {
                System.out.println("Already there!");
            } else {
                student.setCurrentLocation(student.getHome());
                System.out.println(studentName + " is now at home.");
            }
            return;
        }

        if (!services.containsKey(locationName)) {
            System.out.println("Unknown " + locationName + "!");
            return;
        }

        Service location = services.get(locationName);

        if (student.getCurrentLocation().equals(location)) {
            System.out.println("Already there!");
        } else {
            student.setCurrentLocation(location);
            student.visit(location);
            System.out.println(studentName + " is now at " + locationName + ".");

            // Special case for thrifty students
            if (student instanceof ThriftyStudent) {
                ThriftyStudent thrifty = (ThriftyStudent) student;
                if (location instanceof Eating && ((Eating) location).getMenuPrice() > thrifty.getCheapestEating().getMenuPrice()) {
                    System.out.println(studentName + " is distracted!");
                }
                if (location instanceof Lodging && ((Lodging) location).getRoomPrice() > thrifty.getCheapestLodging().getRoomPrice()) {
                    System.out.println(studentName + " is distracted!");
                }
            }
        }
    }

    // Command: move
    public void move(String studentName, String lodgingName) {
        if (!students.containsKey(studentName)) {
            System.out.println(studentName + " does not exist!");
            return;
        }

        if (!services.containsKey(lodgingName) || !(services.get(lodgingName) instanceof Lodging)) {
            System.out.println("Lodging " + lodgingName + " does not exist!");
            return;
        }

        Student student = students.get(studentName);
        Lodging newHome = (Lodging) services.get(lodgingName);

        if (student.getHome().equals(newHome)) {
            System.out.println("That is " + studentName + "'s home!");
            return;
        }

        // Special case for thrifty students
        if (student instanceof ThriftyStudent) {
            ThriftyStudent thrifty = (ThriftyStudent) student;
            if (newHome.getRoomPrice() >= thrifty.getCheapestLodging().getRoomPrice()) {
                System.out.println("Move is not acceptable for " + studentName + "!");
                return;
            }
        }

        student.setHome(newHome);
        student.setCurrentLocation(newHome);
        System.out.println("Lodging " + lodgingName + " is now " + studentName + "'s home. " + studentName + " is home.");
    }

    // Command: where
    public void where(String studentName) {
        if (!students.containsKey(studentName)) {
            System.out.println(studentName + " does not exist!");
            return;
        }

        Student student = students.get(studentName);
        Service location = student.getCurrentLocation();
        System.out.println(studentName + " is at " + location.getName() + " (" + location.getLatitude() + ", " + location.getLongitude() + ").");
    }

    // Command: visited
    public void visited(String studentName) {
        if (!students.containsKey(studentName)) {
            System.out.println(studentName + " does not exist!");
            return;
        }

        Student student = students.get(studentName);

        if (student instanceof ThriftyStudent) {
            System.out.println(studentName + " is thrifty!");
            return;
        }

        if (student instanceof BookishStudent) {
            BookishStudent bookish = (BookishStudent) student;
            if (bookish.getVisitedLeisureServices().isEmpty()) {
                System.out.println(studentName + " has not visited any locations!");
                return;
            }
            for (Service service : bookish.getVisitedLeisureServices()) {
                System.out.println(service.getName());
            }
        } else if (student instanceof OutgoingStudent) {
            OutgoingStudent outgoing = (OutgoingStudent) student;
            if (outgoing.getVisitedServices().isEmpty()) {
                System.out.println(studentName + " has not visited any locations!");
                return;
            }
            for (Service service : outgoing.getVisitedServices()) {
                System.out.println(service.getName());
            }
        }
    }

    // Command: star
    public void rateService(int stars, String serviceName) {
        if (!services.containsKey(serviceName)) {
            System.out.println(serviceName + " does not exist!");
            return;
        }

        if (stars < 1 || stars > 5) {
            System.out.println("Invalid evaluation!");
            return;
        }

        Service service = services.get(serviceName);
        service.addEvaluation(stars);
        System.out.println("Your evaluation has been registered!");
    }

    // Command: ranking
    public void listRankedServices() {
        if (services.isEmpty()) {
            System.out.println("No services in the system.");
            return;
        }

        System.out.println("Services sorted in descending order");

        services.values().stream()
                .sorted((s1, s2) -> {
                    int comparison = Integer.compare(s2.getStarEvaluation(), s1.getStarEvaluation());
                    if (comparison == 0) {
                        return s1.getName().compareTo(s2.getName()); // Tie-breaking by name
                    }
                    return comparison;
                })
                .forEach(service -> {
                    System.out.println(service.getName() + ": " + service.getStarEvaluation());
                });
    }

    // Command: ranked
    public void listServicesByTypeAndStars(String type, int stars) {
        if (stars < 1 || stars > 5) {
            System.out.println("Invalid stars!");
            return;
        }

        List<Service> filteredServices = services.values().stream()
                .filter(service -> service.getClass().getSimpleName().equalsIgnoreCase(type))
                .filter(service -> service.getStarEvaluation() == stars)
                .toList();

        if (filteredServices.isEmpty()) {
            System.out.println("No " + type + " services with " + stars + " average!");
            return;
        }

        System.out.println(type + " services with " + stars + " average");
        filteredServices.forEach(service -> System.out.println(service.getName()));
    }

    // Command: find
    public void findService(String studentName, String serviceType) {
        if (!students.containsKey(studentName)) {
            System.out.println(studentName + " does not exist!");
            return;
        }

        Student student = students.get(studentName);

        // Filter services by type
        List<Service> filteredServices = services.values().stream()
                .filter(service -> service.getClass().getSimpleName().equalsIgnoreCase(serviceType))
                .toList();

        if (filteredServices.isEmpty()) {
            System.out.println("No " + serviceType + " services!");
            return;
        }

        Service relevantService = null;

        if (student instanceof ThriftyStudent) {
            // Find cheapest service for thrifty students
            relevantService = findCheapestService(filteredServices, serviceType);
            // Update thrifty student's information if necessary
            if (student instanceof ThriftyStudent && relevantService != null) {
                ThriftyStudent thrifty = (ThriftyStudent) student;
                if (serviceType.equalsIgnoreCase("eating")) {
                    Eating eating = (Eating) relevantService;
                    if (thrifty.getCheapestEating() == null || eating.getMenuPrice() < thrifty.getCheapestEating().getMenuPrice()) {
                        thrifty.visit(eating);
                        System.out.println(studentName + " updated.");
                    }
                } else if (serviceType.equalsIgnoreCase("lodging")) {
                    Lodging lodging = (Lodging) relevantService;
                    if (thrifty.getCheapestLodging() == null || lodging.getRoomPrice() < thrifty.getCheapestLodging().getRoomPrice()) {
                        thrifty.visit(lodging);
                        System.out.println(studentName + " updated.");
                    }
                }
            }
        } else {
            // Find nearest service for bookish and outgoing students
            relevantService = findNearestService(filteredServices, student.getCurrentLocation());
        }

        if (relevantService != null) {
            System.out.println(relevantService.getName() + ".");
        }
    }

    // Helper method: Find the cheapest service for thrifty students
    private Service findCheapestService(List<Service> services, String serviceType) {
        return services.stream()
                .min((s1, s2) -> {
                    int price1 = getServicePrice(s1, serviceType);
                    int price2 = getServicePrice(s2, serviceType);
                    return Integer.compare(price1, price2);
                })
                .orElse(null);
    }

    // Helper method: Extract price for a service based on type
    private int getServicePrice(Service service, String serviceType) {
        if (serviceType.equalsIgnoreCase("eating") && service instanceof Eating) {
            return ((Eating) service).getMenuPrice();
        } else if (serviceType.equalsIgnoreCase("lodging") && service instanceof Lodging) {
            return ((Lodging) service).getRoomPrice();
        }
        return Integer.MAX_VALUE; // For invalid cases
    }

    // Helper method: Find the nearest service for bookish and outgoing students
    private Service findNearestService(List<Service> services, Service currentLocation) {
        return services.stream()
                .min((s1, s2) -> {
                    long distance1 = calculateManhattanDistance(currentLocation, s1);
                    long distance2 = calculateManhattanDistance(currentLocation, s2);
                    return Long.compare(distance1, distance2);
                })
                .orElse(null);
    }

    // Helper method: Calculate Manhattan Distance between two services
    private long calculateManhattanDistance(Service s1, Service s2) {
        return Math.abs(s1.getLatitude() - s2.getLatitude()) + Math.abs(s1.getLongitude() - s2.getLongitude());
    }
}