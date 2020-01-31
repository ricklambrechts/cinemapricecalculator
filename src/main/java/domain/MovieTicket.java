package domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.Objects;

public class MovieTicket implements Comparable<MovieTicket>
{

    @Expose
    private MovieScreening movieScreening;

    @Expose
    @SerializedName("premiumTicket")
    private boolean isPremiumTicket;

    @Expose
    private int seatRow;

    @Expose
    private int seatNr;

    public MovieTicket(
            MovieScreening movieScreening,
            boolean isPremiumTicket,
            int seatRow,
            int seatNr)
    {
        this.movieScreening = movieScreening;
        this.isPremiumTicket = isPremiumTicket;
        this.seatRow = seatRow;
        this.seatNr = seatNr;
    }

    public boolean isPremiumTicket()
    {
        return isPremiumTicket;
    }

    public double getPrice()
    {
        return movieScreening.getPricePerSeat();
    }

    public LocalDateTime getDateTime() {
        return movieScreening.getDateAndTime();
    }

    @Override
    public String toString() {
        return movieScreening.toString() + " - row " + seatRow + ", seat " + seatNr +
                (isPremiumTicket ? " (Premium)" : "");
    }

    @Override
    public int compareTo(MovieTicket o) {
        return Double.compare(getPrice(), o.getPrice());
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

        MovieTicket movieTicket = (MovieTicket) obj;

        return Objects.equals(movieScreening, movieTicket.movieScreening)
                && Objects.equals(seatNr, movieTicket.seatNr)
                && Objects.equals(seatRow, movieTicket.seatRow)
                && Objects.equals(isPremiumTicket, movieTicket.isPremiumTicket);
    }
}
