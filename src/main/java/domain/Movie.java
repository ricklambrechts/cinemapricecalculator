package domain;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Objects;

public class Movie
{
    @Expose
    private String title;
    private ArrayList<MovieScreening> screenings;

    public Movie(String title)
    {
        this.title = title;

        screenings = new ArrayList<>();
    }

    public String getTitle()
    {
        return title;
    }

    public void addScreening(MovieScreening screening)
    {
        screenings.add(screening);
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

        Movie movie = (Movie) obj;

        return Objects.equals(title, movie.title)
                && Objects.equals(screenings, movie.screenings);
    }
}
