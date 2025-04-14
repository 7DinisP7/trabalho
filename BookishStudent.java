import java.util.ArrayList;
import java.util.List;

public class BookishStudent extends Student {
    private List<Service> visitedLeisureServices;

    public BookishStudent(String name, Lodging home) {
        super(name, home);
        this.visitedLeisureServices = new ArrayList<>();
    }

    @Override
    public void visit(Service service) {
        if (service instanceof Leisure) {
            visitedLeisureServices.add(service);
        }
    }

    public List<Service> getVisitedLeisureServices() {
        return visitedLeisureServices;
    }
}