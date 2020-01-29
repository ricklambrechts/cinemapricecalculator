import domain.Movie;
import domain.MovieScreening;
import domain.MovieTicket;
import domain.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class MainTest {

    @Test
    public void firstTest() {
        boolean bool = true;

        Assertions.assertTrue(bool);

        Movie movie = new Movie("Test movie");
        MovieScreening movieScreening = new MovieScreening(movie, LocalDateTime.now().minusDays(3), 10);
        MovieScreening movieScreening1 = new MovieScreening(movie, LocalDateTime.now(), 10);
        MovieTicket movieTicket = new MovieTicket(movieScreening, false, 0, 0);
        MovieTicket movieTicket1 = new MovieTicket(movieScreening1, true, 0, 0);

        Order order = new Order(1, false);
        order.addSeatReservation(movieTicket1);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);

        Double orderPrice = order.calculatePrice();
        System.out.println("Order Price: "+ orderPrice);
    }

}
