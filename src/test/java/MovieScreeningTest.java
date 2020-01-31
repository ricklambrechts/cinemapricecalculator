import domain.Movie;
import domain.MovieScreening;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class MovieScreeningTest {

    private static Movie movie;

    @BeforeAll
    public static void beforeAllTests() {
        movie = new Movie("Test movie");
    }

    /**
     * This test will test the basic movie ticket
     */
    @Test
    public void movieScreeningDateTimeAndPricePerSeatTest() {
        // LocalDateTime of now
        LocalDateTime localDateTime = LocalDateTime.now();

        // Create movie ticket
        MovieScreening movieScreening = new MovieScreening(movie, localDateTime, 10.0);

        // Do assertions
        Assertions.assertEquals(localDateTime, movieScreening.getDateAndTime());
        Assertions.assertEquals(10.0, movieScreening.getPricePerSeat());
    }

    /**
     * This test will test the is on weekend logic on empty date
     */
    @Test
    public void movieScreeningEmptyDateTest() {
        // Create movie ticket
        MovieScreening movieScreening = new MovieScreening(movie, null, 10.0);

        // Do assertions
        Assertions.assertNull(movieScreening.getDateAndTime());
        Assertions.assertEquals(10.0, movieScreening.getPricePerSeat());
        Assertions.assertFalse(movieScreening.isOnWeekendDays());
        Assertions.assertFalse(movieScreening.isOnMonTroughThuDays());
        Assertions.assertFalse(movieScreening.isOnWeekDays());
    }

    /**
     * This test will test the is on weekend logic based on saturday
     */
    @Test
    public void movieScreeningIsOnWeekendSaturdayTest() {
        // LocalDateTime of saturday
        LocalDateTime localDateTime = LocalDateTime.now().with(DayOfWeek.SATURDAY);

        // Create movie ticket
        MovieScreening movieScreening = new MovieScreening(movie, localDateTime, 10.0);

        // Do assertions
        Assertions.assertEquals(localDateTime, movieScreening.getDateAndTime());
        Assertions.assertEquals(10.0, movieScreening.getPricePerSeat());
        Assertions.assertTrue(movieScreening.isOnWeekendDays());
        Assertions.assertFalse(movieScreening.isOnMonTroughThuDays());
        Assertions.assertFalse(movieScreening.isOnWeekDays());
    }

    /**
     * This test will test the is on weekend logic based on sunday
     */
    @Test
    public void movieScreeningIsOnWeekendSundayTest() {
        // LocalDateTime of sunday
        LocalDateTime localDateTime = LocalDateTime.now().with(DayOfWeek.SUNDAY);

        // Create movie ticket
        MovieScreening movieScreening = new MovieScreening(movie, localDateTime, 10.0);

        // Do assertions
        Assertions.assertEquals(localDateTime, movieScreening.getDateAndTime());
        Assertions.assertEquals(10.0, movieScreening.getPricePerSeat());
        Assertions.assertTrue(movieScreening.isOnWeekendDays());
        Assertions.assertFalse(movieScreening.isOnMonTroughThuDays());
        Assertions.assertFalse(movieScreening.isOnWeekDays());
    }

    /**
     * This test will test the logic based on monday
     */
    @Test
    public void movieScreeningIsOnMondayTest() {
        // LocalDateTime of monday
        LocalDateTime localDateTime = LocalDateTime.now().with(DayOfWeek.MONDAY);

        // Create movie ticket
        MovieScreening movieScreening = new MovieScreening(movie, localDateTime, 10.0);

        // Do assertions
        Assertions.assertEquals(localDateTime, movieScreening.getDateAndTime());
        Assertions.assertEquals(10.0, movieScreening.getPricePerSeat());
        Assertions.assertFalse(movieScreening.isOnWeekendDays());
        Assertions.assertTrue(movieScreening.isOnMonTroughThuDays());
        Assertions.assertTrue(movieScreening.isOnWeekDays());
    }

    /**
     * This test will test the logic based on tuesday
     */
    @Test
    public void movieScreeningIsOnTuesdayTest() {
        // LocalDateTime of tuesday
        LocalDateTime localDateTime = LocalDateTime.now().with(DayOfWeek.TUESDAY);

        // Create movie ticket
        MovieScreening movieScreening = new MovieScreening(movie, localDateTime, 10.0);

        // Do assertions
        Assertions.assertEquals(localDateTime, movieScreening.getDateAndTime());
        Assertions.assertEquals(10.0, movieScreening.getPricePerSeat());
        Assertions.assertFalse(movieScreening.isOnWeekendDays());
        Assertions.assertTrue(movieScreening.isOnMonTroughThuDays());
        Assertions.assertTrue(movieScreening.isOnWeekDays());
    }

    /**
     * This test will test the logic based on tuesday
     */
    @Test
    public void movieScreeningIsOnWednesdayTest() {
        // LocalDateTime of wednesday
        LocalDateTime localDateTime = LocalDateTime.now().with(DayOfWeek.WEDNESDAY);

        // Create movie ticket
        MovieScreening movieScreening = new MovieScreening(movie, localDateTime, 10.0);

        // Do assertions
        Assertions.assertEquals(localDateTime, movieScreening.getDateAndTime());
        Assertions.assertEquals(10.0, movieScreening.getPricePerSeat());
        Assertions.assertFalse(movieScreening.isOnWeekendDays());
        Assertions.assertTrue(movieScreening.isOnMonTroughThuDays());
        Assertions.assertTrue(movieScreening.isOnWeekDays());
    }

    /**
     * This test will test the logic based on tuesday
     */
    @Test
    public void movieScreeningIsOnThursdayTest() {
        // LocalDateTime of thursday
        LocalDateTime localDateTime = LocalDateTime.now().with(DayOfWeek.THURSDAY);

        // Create movie ticket
        MovieScreening movieScreening = new MovieScreening(movie, localDateTime, 10.0);

        // Do assertions
        Assertions.assertEquals(localDateTime, movieScreening.getDateAndTime());
        Assertions.assertEquals(10.0, movieScreening.getPricePerSeat());
        Assertions.assertFalse(movieScreening.isOnWeekendDays());
        Assertions.assertTrue(movieScreening.isOnMonTroughThuDays());
        Assertions.assertTrue(movieScreening.isOnWeekDays());
    }

    /**
     * This test will test the logic based on tuesday
     */
    @Test
    public void movieScreeningIsOnFridayTest() {
        // LocalDateTime of friday
        LocalDateTime localDateTime = LocalDateTime.now().with(DayOfWeek.FRIDAY);

        // Create movie ticket
        MovieScreening movieScreening = new MovieScreening(movie, localDateTime, 10.0);

        // Do assertions
        Assertions.assertEquals(localDateTime, movieScreening.getDateAndTime());
        Assertions.assertEquals(10.0, movieScreening.getPricePerSeat());
        Assertions.assertFalse(movieScreening.isOnWeekendDays());
        Assertions.assertFalse(movieScreening.isOnMonTroughThuDays());
        Assertions.assertTrue(movieScreening.isOnWeekDays());
    }

    /**
     * This test will test the to string method
     */
    @Test
    public void movieScreeningToStringTest() {
        // LocalDateTime of now
        LocalDateTime localDateTime = LocalDateTime.of(2020,1,11,12,1);

        // Create movie ticket
        MovieScreening movieScreening = new MovieScreening(movie, localDateTime, 10.0);

        // Do assertions
        Assertions.assertEquals(localDateTime, movieScreening.getDateAndTime());
        Assertions.assertEquals(10.0, movieScreening.getPricePerSeat());
        Assertions.assertEquals("Test movie - 2020-01-11T12:01", movieScreening.toString());
    }

    /**
     * This test will test the to string method with null datetime
     */
    @Test
    public void movieScreeningToStringEmptyDateTest() {
        // Create movie ticket
        MovieScreening movieScreening = new MovieScreening(movie, null, 10.0);

        // Do assertions
        Assertions.assertNull(movieScreening.getDateAndTime());
        Assertions.assertEquals(10.0, movieScreening.getPricePerSeat());
        Assertions.assertFalse(movieScreening.isOnWeekendDays());
        Assertions.assertFalse(movieScreening.isOnMonTroughThuDays());
        Assertions.assertFalse(movieScreening.isOnWeekDays());
        Assertions.assertEquals("Test movie - null", movieScreening.toString());
    }

}