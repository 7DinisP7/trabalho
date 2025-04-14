public class Leisure extends Service {
    private int ticketPrice;
    private int studentDiscount;

    public Leisure(String name, long latitude, long longitude, int ticketPrice, int studentDiscount) {
        super(name, latitude, longitude);
        if (ticketPrice <= 0) {
            throw new IllegalArgumentException("Invalid ticket price!");
        }
        if (studentDiscount < 0 || studentDiscount > 100) {
            throw new IllegalArgumentException("Invalid discount!");
        }
        this.ticketPrice = ticketPrice;
        this.studentDiscount = studentDiscount;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public int getStudentDiscount() {
        return studentDiscount;
    }
}