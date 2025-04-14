import java.util.ArrayList;
import java.util.List;

public class OutgoingStudent extends Student {
    private List<Service> visitedServices;

    public OutgoingStudent(String name, Lodging home) {
        super(name, home);
        this.visitedServices = new ArrayList<>();
    }

    @Override
    public void visit(Service service) {
        visitedServices.add(service);
    }

    public List<Service> getVisitedServices() {
        return visitedServices;
    }
}