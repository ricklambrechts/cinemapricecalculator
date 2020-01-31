package domain;

import com.google.gson.annotations.Expose;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class MovieScreening
{
    @Expose
    private Movie movie;

    @Expose
    private LocalDateTime dateAndTime;
    private double pricePerSeat;
    private DayOfWeek dayOfWeek;

    public MovieScreening(Movie movie, LocalDateTime dateAndTime, double pricePerSeat)
    {
        this.movie = movie;
        movie.addScreening(this);

        this.dateAndTime = dateAndTime;
        this.pricePerSeat = pricePerSeat;

        if(dateAndTime != null) {
            this.dayOfWeek = DayOfWeek.from(dateAndTime);
        }
    }

    public double getPricePerSeat()
    {
        return pricePerSeat;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public boolean isOnWeekendDays()
    {
        if(dayOfWeek == null) {
            return false;
        }

        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    public boolean isOnMonTroughThuDays() {
        if(dayOfWeek == null) {
            return false;
        }

        return dayOfWeek == DayOfWeek.MONDAY || dayOfWeek == DayOfWeek.TUESDAY || dayOfWeek == DayOfWeek.WEDNESDAY || dayOfWeek == DayOfWeek.THURSDAY;
    }

    public boolean isOnWeekDays()
    {
        if(dayOfWeek == null) {
            return false;
        }

        return !isOnWeekendDays();
    }

    @Override
    public String toString() {
        return movie.getTitle() + " - " + dateAndTime;
    }
}
