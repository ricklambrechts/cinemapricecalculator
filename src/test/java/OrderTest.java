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
     * This test will test the empty order price
     */
    @Test
    public void emptyOrder() {
        // Create order
        Order order = new Order(1, true);
        // Check if price equals
        Assertions.assertEquals(0.0, order.calculatePrice());
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

    @Test
    public void saturdayWeekendGroupDiscountForNonStudentsTest() {
        // Create movie screening of specific week day
        MovieScreening movieScreening = new MovieScreening(movie, LocalDateTime.now().with(DayOfWeek.SATURDAY), 10);

        // Create movie tickets
        MovieTicket movieTicket = new MovieTicket(movieScreening, false, 0, 0);

        // Create order with movie tickets
        Order order = new Order(1, false);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);

        // Check if price equals
        Assertions.assertEquals(54.0, order.calculatePrice());
    }

    @Test
    public void sundayWeekendGroupDiscountForNonStudentsTest() {
        // Create movie screening of specific week day
        MovieScreening movieScreening = new MovieScreening(movie, LocalDateTime.now().with(DayOfWeek.SUNDAY), 10);

        // Create movie tickets
        MovieTicket movieTicket = new MovieTicket(movieScreening, false, 0, 0);

        // Create order with movie tickets
        Order order = new Order(1, false);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);

        // Check if price equals
        Assertions.assertEquals(54.0, order.calculatePrice());
    }

    @Test
    public void weekendGroupDiscountForNonStudentsTest() {
        // Create movie screening of specific week day
        MovieScreening movieScreening = new MovieScreening(movie, LocalDateTime.now().with(DayOfWeek.SATURDAY), 10);
        MovieScreening movieScreening2 = new MovieScreening(movie, LocalDateTime.now().with(DayOfWeek.SUNDAY), 10);

        // Create movie tickets
        MovieTicket movieTicket = new MovieTicket(movieScreening, false, 0, 0);
        MovieTicket movieTicket2 = new MovieTicket(movieScreening2, false, 0, 0);

        // Create order with movie tickets
        Order order = new Order(1, false);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket2);
        order.addSeatReservation(movieTicket2);
        order.addSeatReservation(movieTicket2);

        // Check if price equals
        Assertions.assertEquals(54.0, order.calculatePrice());
    }

    @Test
    public void weekendGroupDiscountForNonStudentsWithExtraWeekDayTicketTest() {
        // Create movie screening of specific week day
        MovieScreening movieScreening = new MovieScreening(movie, LocalDateTime.now().with(DayOfWeek.SATURDAY), 10);
        MovieScreening movieScreening2 = new MovieScreening(movie, LocalDateTime.now().with(DayOfWeek.SUNDAY), 10);
        MovieScreening movieScreening3 = new MovieScreening(movie, LocalDateTime.now().with(DayOfWeek.MONDAY), 10);

        // Create movie tickets
        MovieTicket movieTicket = new MovieTicket(movieScreening, false, 0, 0);
        MovieTicket movieTicket2 = new MovieTicket(movieScreening2, false, 0, 0);
        MovieTicket movieTicket3 = new MovieTicket(movieScreening3, false, 0, 0);

        // Create order with movie tickets
        Order order = new Order(1, false);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket2);
        order.addSeatReservation(movieTicket2);
        order.addSeatReservation(movieTicket2);
        order.addSeatReservation(movieTicket3);

        // Check if price equals
        Assertions.assertEquals(64.0, order.calculatePrice());
    }

    @Test
    public void weekendGroupDiscountForNonStudentsWithExtraWeekDayTicketSecondTicketFreeTest() {
        // Create movie screening of specific week day
        MovieScreening movieScreening = new MovieScreening(movie, LocalDateTime.now().with(DayOfWeek.SATURDAY), 10);
        MovieScreening movieScreening2 = new MovieScreening(movie, LocalDateTime.now().with(DayOfWeek.SUNDAY), 10);
        MovieScreening movieScreening3 = new MovieScreening(movie, LocalDateTime.now().with(DayOfWeek.MONDAY), 10);
        MovieScreening movieScreening4 = new MovieScreening(movie, LocalDateTime.now().with(DayOfWeek.TUESDAY), 10);

        // Create movie tickets
        MovieTicket movieTicket = new MovieTicket(movieScreening, false, 0, 0);
        MovieTicket movieTicket2 = new MovieTicket(movieScreening2, false, 0, 0);
        MovieTicket movieTicket3 = new MovieTicket(movieScreening3, false, 0, 0);
        MovieTicket movieTicket4 = new MovieTicket(movieScreening4, false, 0, 0);

        // Create order with movie tickets
        Order order = new Order(1, false);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket2);
        order.addSeatReservation(movieTicket2);
        order.addSeatReservation(movieTicket2);
        order.addSeatReservation(movieTicket3);
        order.addSeatReservation(movieTicket4);

        // Check if price equals
        // Cheapest ticket is free. So the 9 euro ticket is free, not the 10 euro one.
        Assertions.assertEquals(65.0, order.calculatePrice());
    }

    @Test
    public void weekendGroupDiscountForNonStudents5TicketsTest() {
        // Create movie screening of specific week day
        MovieScreening movieScreening = new MovieScreening(movie, LocalDateTime.now().with(DayOfWeek.SATURDAY), 10);
        MovieScreening movieScreening2 = new MovieScreening(movie, LocalDateTime.now().with(DayOfWeek.SUNDAY), 10);

        // Create movie tickets
        MovieTicket movieTicket = new MovieTicket(movieScreening, false, 0, 0);
        MovieTicket movieTicket2 = new MovieTicket(movieScreening2, false, 0, 0);

        // Create order with movie tickets
        Order order = new Order(1, false);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket2);
        order.addSeatReservation(movieTicket2);

        // Check if price equals
        Assertions.assertEquals(50.0, order.calculatePrice());
    }

    @Test
    public void orderNumber1Test() {
        // Create order with movie tickets
        Order order = new Order(1, false);

        // Check if price equals
        Assertions.assertEquals(1, order.getOrderNr());
    }

    @Test
    public void orderNumber2Test() {
        // Create order with movie tickets
        Order order = new Order(2, false);

        // Check if price equals
        Assertions.assertEquals(2, order.getOrderNr());
    }

    @Test
    public void premiumTicketTest() {
        // Create movie screening of specific week day
        MovieScreening movieScreening = new MovieScreening(movie, LocalDateTime.now().with(DayOfWeek.MONDAY), 10);

        // Create movie tickets
        MovieTicket movieTicket = new MovieTicket(movieScreening, true, 0, 0);

        // Create order with movie tickets
        Order order = new Order(1, true);
        order.addSeatReservation(movieTicket);

        // Check if price equals
        Assertions.assertEquals(12.0, order.calculatePrice());
    }

    @Test
    public void twoPremiumStudentTicketTest() {
        // Create movie screening of specific week day
        MovieScreening movieScreening = new MovieScreening(movie, LocalDateTime.now().with(DayOfWeek.MONDAY), 10);

        // Create movie tickets
        MovieTicket movieTicket = new MovieTicket(movieScreening, true, 0, 0);

        // Create order with movie tickets
        Order order = new Order(1, true);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);

        // Check if price equals
        Assertions.assertEquals(12.0, order.calculatePrice());
    }

    @Test
    public void twoPremiumTicketTest() {
        // Create movie screening of specific week day
        MovieScreening movieScreening = new MovieScreening(movie, LocalDateTime.now().with(DayOfWeek.MONDAY), 10);

        // Create movie tickets
        MovieTicket movieTicket = new MovieTicket(movieScreening, true, 0, 0);

        // Create order with movie tickets
        Order order = new Order(1, false);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);

        // Check if price equals
        Assertions.assertEquals(13.0, order.calculatePrice());
    }

    @Test
    public void emptyMovieScreeningTest() {
        // Create movie tickets
        MovieTicket movieTicket = new MovieTicket(null, false, 0, 0);

        // Create order with movie tickets
        Order order = new Order(1, false);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);

        // Check if price equals
        Assertions.assertEquals(0.0, order.calculatePrice());
    }

    @Test
    public void emptyMovieScreeningPremiumTicketsTest() {
        // Create movie tickets
        MovieTicket movieTicket = new MovieTicket(null, true, 0, 0);

        // Create order with movie tickets
        Order order = new Order(1, false);
        order.addSeatReservation(movieTicket);
        order.addSeatReservation(movieTicket);

        // Check if price equals
        Assertions.assertEquals(6.0, order.calculatePrice());
    }
}
