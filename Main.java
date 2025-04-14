import java.util.Scanner;

public class Main {
    private static boolean running = true;
    private static CommandInterpreter commandInterpreter = new CommandInterpreter();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to HomeAway From Home!");

        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            handleCommand(input);
        }

        scanner.close();
    }

    private static void handleCommand(String input) {
        String[] tokens = input.split("\\s+");
        String command = tokens[0].toLowerCase();

        try {
            switch (command) {
                case "help":
                    printHelp();
                    break;
                case "exit":
                    exitProgram();
                    break;
                case "bounds":
                    handleBounds(tokens);
                    break;
                case "eating":
                    handleEating(tokens);
                    break;
                case "lodging":
                    handleLodging(tokens);
                    break;
                case "leisure":
                    handleLeisure(tokens);
                    break;
                case "services":
                    commandInterpreter.listServices();
                    break;
                case "student":
                    handleStudent(tokens);
                    break;
                case "students":
                    commandInterpreter.listStudents();
                    break;
                case "leave":
                    handleLeave(tokens);
                    break;
                case "go":
                    handleGo(tokens);
                    break;
                case "move":
                    handleMove(tokens);
                    break;
                case "where":
                    handleWhere(tokens);
                    break;
                case "visited":
                    handleVisited(tokens);
                    break;
                case "star":
                    handleStar(tokens);
                    break;
                case "ranking":
                    commandInterpreter.listRankedServices();
                    break;
                case "ranked":
                    handleRanked(tokens);
                    break;
                case "find":
                    handleFind(tokens);
                    break;
                default:
                    System.out.println("Unknown command. Type help to see available commands.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void handleBounds(String[] tokens) {
        if (tokens.length != 6) {
            System.out.println("Invalid bounds command. Usage: bounds <topLat> <leftLong> <bottomLat> <rightLong> <name>");
            return;
        }
        long topLat = Long.parseLong(tokens[1]);
        long leftLong = Long.parseLong(tokens[2]);
        long bottomLat = Long.parseLong(tokens[3]);
        long rightLong = Long.parseLong(tokens[4]);
        String name = tokens[5];
        commandInterpreter.bounds(topLat, leftLong, bottomLat, rightLong, name);
    }

    private static void handleEating(String[] tokens) {
        if (tokens.length != 5) {
            System.out.println("Invalid eating command. Usage: eating <latitude> <longitude> <menuPrice> <name>");
            return;
        }
        long latitude = Long.parseLong(tokens[1]);
        long longitude = Long.parseLong(tokens[2]);
        int menuPrice = Integer.parseInt(tokens[3]);
        String name = tokens[4];
        commandInterpreter.addEatingService(name, latitude, longitude, menuPrice);
    }

    private static void handleLodging(String[] tokens) {
        if (tokens.length != 5) {
            System.out.println("Invalid lodging command. Usage: lodging <latitude> <longitude> <roomPrice> <name>");
            return;
        }
        long latitude = Long.parseLong(tokens[1]);
        long longitude = Long.parseLong(tokens[2]);
        int roomPrice = Integer.parseInt(tokens[3]);
        String name = tokens[4];
        commandInterpreter.addLodgingService(name, latitude, longitude, roomPrice);
    }

    private static void handleLeisure(String[] tokens) {
        if (tokens.length != 6) {
            System.out.println("Invalid leisure command. Usage: leisure <latitude> <longitude> <ticketPrice> <studentDiscount> <name>");
            return;
        }
        long latitude = Long.parseLong(tokens[1]);
        long longitude = Long.parseLong(tokens[2]);
        int ticketPrice = Integer.parseInt(tokens[3]);
        int studentDiscount = Integer.parseInt(tokens[4]);
        String name = tokens[5];
        commandInterpreter.addLeisureService(name, latitude, longitude, ticketPrice, studentDiscount);
    }

    private static void handleStudent(String[] tokens) {
        if (tokens.length != 4) {
            System.out.println("Invalid student command. Usage: student <type> <name> <home>");
            return;
        }
        String type = tokens[1];
        String name = tokens[2];
        String home = tokens[3];
        commandInterpreter.addStudent(type, name, home);
    }

    private static void handleLeave(String[] tokens) {
        if (tokens.length != 2) {
            System.out.println("Invalid leave command. Usage: leave <name>");
            return;
        }
        String name = tokens[1];
        commandInterpreter.removeStudent(name);
    }

    private static void handleGo(String[] tokens) {
        if (tokens.length != 3) {
            System.out.println("Invalid go command. Usage: go <studentName> <locationName>");
            return;
        }
        String studentName = tokens[1];
        String locationName = tokens[2];
        commandInterpreter.go(studentName, locationName);
    }

    private static void handleMove(String[] tokens) {
        if (tokens.length != 3) {
            System.out.println("Invalid move command. Usage: move <studentName> <lodgingName>");
            return;
        }
        String studentName = tokens[1];
        String lodgingName = tokens[2];
        commandInterpreter.move(studentName, lodgingName);
    }

    private static void handleWhere(String[] tokens) {
        if (tokens.length != 2) {
            System.out.println("Invalid where command. Usage: where <studentName>");
            return;
        }
        String studentName = tokens[1];
        commandInterpreter.where(studentName);
    }

    private static void handleVisited(String[] tokens) {
        if (tokens.length != 2) {
            System.out.println("Invalid visited command. Usage: visited <studentName>");
            return;
        }
        String studentName = tokens[1];
        commandInterpreter.visited(studentName);
    }

    private static void handleStar(String[] tokens) {
        if (tokens.length != 3) {
            System.out.println("Invalid star command. Usage: star <stars> <serviceName>");
            return;
        }
        int stars = Integer.parseInt(tokens[1]);
        String serviceName = tokens[2];
        commandInterpreter.rateService(stars, serviceName);
    }

    private static void handleRanked(String[] tokens) {
        if (tokens.length != 3) {
            System.out.println("Invalid ranked command. Usage: ranked <type> <stars>");
            return;
        }
        String type = tokens[1];
        int stars = Integer.parseInt(tokens[2]);
        commandInterpreter.listServicesByTypeAndStars(type, stars);
    }

    private static void handleFind(String[] tokens) {
        if (tokens.length != 3) {
            System.out.println("Invalid find command. Usage: find <studentName> <serviceType>");
            return;
        }
        String studentName = tokens[1];
        String serviceType = tokens[2];
        commandInterpreter.findService(studentName, serviceType);
    }

    private static void printHelp() {
        System.out.println("bounds - Defines the geographic bounding rectangle of the system");
        System.out.println("eating - Adds a new service of type eating to the system");
        System.out.println("lodging - Adds a new service of type lodging to the system");
        System.out.println("leisure - Adds a new service of type leisure to the system");
        System.out.println("services - Displays the list of services in the system");
        System.out.println("student - Adds a student to the system");
        System.out.println("students - Lists all the students in the community");
        System.out.println("leave - Removes a student from the system");
        System.out.println("go - Changes the location of a student to a service, or home");
        System.out.println("move - Changes the home of a student");
        System.out.println("star - Evaluates a service");
        System.out.println("where - Locates a student");
        System.out.println("visited - Lists locations visited by one student");
        System.out.println("ranking - Lists services ordered by star");
        System.out.println("ranked - Lists services of a certain type, with a specific star evaluation");
        System.out.println("find - Finds the most relevant service of a certain type, for a specific student");
        System.out.println("help - Shows the available commands");
        System.out.println("exit - Terminates the execution of the program");
    }

    private static void exitProgram() {
        System.out.println("Bye!");
        running = false;
    }
}