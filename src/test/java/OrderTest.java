import domain.Movie;
import domain.MovieScreening;
import domain.MovieTicket;
import domain.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.EnumSet;

public class OrderTest {

    private static Movie movie;

    @BeforeAll
    public static void beforeAllTests() {
        movie = new Movie("Test movie");
    }

    /**
     * This test will test if every second ticket is free for students
     */
    @Test
    public void everySecondTicketIsFreeForStudents() {

        // Get all days of week
        EnumSet<DayOfWeek> dows = EnumSet.allOf( DayOfWeek.class );

        // Loop through days of week
        for( DayOfWeek dow : dows ) {
            // Create movie screening of specific week day
            MovieScreening movieScreening = new MovieScreening(movie, LocalDateTime.now().with(dow), 10);

            // Create movie tickets
            MovieTicket movieTicket = new MovieTicket(movieScreening, false, 0, 0);
            MovieTicket movieTicket2 = new MovieTicket(movieScreening, false, 0, 0);

            // Create order with movie tickets
            Order order = new Order(1, true);
            order.addSeatReservation(movieTicket);
            order.addSeatReservation(movieTicket2);

            // Check if price equals
            Assertions.assertEquals(10.0, order.calculatePrice());
        }
    }

}
