package domain;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;
import java.util.Objects;

public class MovieScreening
{
    @Expose
    private Movie movie;

    @Expose
    private LocalDateTime dateAndTime;
    private double pricePerSeat;

    public MovieScreening(Movie movie, LocalDateTime dateAndTime, double pricePerSeat)
    {
        this.movie = movie;
        movie.addScreening(this);

        this.dateAndTime = dateAndTime;
        this.pricePerSeat = pricePerSeat;
    }

    public double getPricePerSeat()
    {
        return pricePerSeat;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    @Override
    public String toString() {
        return movie.getTitle() + " - " + dateAndTime;
    }

    @Override
    public boolean equals(Object obj) {
        // self check
        if (this == obj)
            return true;
        // null check
        if (obj == null)
            return false;
        // type check and cast
        if (getClass() != obj.getClass())
            return false;

        MovieScreening movieScreening = (MovieScreening) obj;

        return Objects.equals(movie, movieScreening.movie)
                && Objects.equals(dateAndTime, movieScreening.dateAndTime)
                && Objects.equals(pricePerSeat, movieScreening.pricePerSeat);
    }
}
