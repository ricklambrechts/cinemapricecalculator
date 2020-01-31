package domain;

import com.google.gson.*;
import com.google.gson.annotations.Expose;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Order
{
    static final Logger logger = LogManager.getLogManager().getLogger(Order.class.getName());

    @Expose
    private int orderNr;
    private boolean isStudentOrder;
    private int freeTicketsCount;
    private int discountTicketsCount;

    @Expose
    private ArrayList<MovieTicket> tickets;

    public Order(int orderNr, boolean isStudentOrder)
    {
        this.orderNr = orderNr;
        this.isStudentOrder = isStudentOrder;

        tickets = new ArrayList<>();
    }

    public int getOrderNr()
    {
        return orderNr;
    }

    public void addSeatReservation(MovieTicket ticket)
    {
        tickets.add(ticket);
        updateCounters(ticket);
    }

    private void updateCounters(MovieTicket movieTicket)
    {
        updateFreeTicketsCounter(movieTicket);
        updateDiscountTicketsCounter(movieTicket);
    }

    private void updateFreeTicketsCounter(MovieTicket movieTicket) {
        if(isStudentOrder) {
            freeTicketsCount = calculateFreeTicketCount(tickets.size());
            return;
        }

        MovieScreening movieScreening = movieTicket.getMovieScreening();
        if(movieScreening != null && movieScreening.isOnMonTroughThuDays()) {
            // If movie on (mon/tue/wed/thu) then every 2'nd ticket is free.
            freeTicketsCount = calculateFreeTicketCount(getWeeklyDayTicketCount(tickets));
        }
    }

    private void updateDiscountTicketsCounter(MovieTicket movieTicket) {
        if(isStudentOrder) {
            discountTicketsCount = 0;
            return;
        }

        MovieScreening movieScreening = movieTicket.getMovieScreening();
        if(movieScreening != null && movieScreening.isOnWeekendDays()) {
            discountTicketsCount = getWeekendDayTicketCount(tickets);
        }
    }

    /**
     * This function will calculate the order price
     * To check the price buildup you could use // System.out.println("Ticket " + i + ": Price " + movieTicketPrice + " , Extra price: " + movieTicketExtraPrice + " , Discount: " + movieTicket.getPrice() / 100 * 10);
     * @return double Price in double
     */
    public double calculatePrice()
    {
        // Check if tickets is empty
        // A
        if(tickets.isEmpty()) {
            return 0.0;
        }

        // Sort movie tickets so that the cheapest tickets are in front of array
        tickets.sort(MovieTicket::comparePriceTo);

        // Hold variables
        double price = 0.0;

        // Calculate price
        // B
        for (int i = 0; i < tickets.size(); i++) {
            // If there are free tickets. Don't calculate tickets in price so continue.
            if(i < freeTicketsCount) {
                continue;
            }

            MovieTicket movieTicket = tickets.get(i);
            double movieTicketExtraPrice = getExtraTicketPrice(isStudentOrder, movieTicket.isPremiumTicket());
            double movieTicketPrice = movieTicket.getPrice() + movieTicketExtraPrice;

            // Check for weekendDayTickets if 6 or more
            // C
            MovieScreening movieScreening = movieTicket.getMovieScreening();
            if(discountTicketsCount >= 6 && movieScreening != null && movieScreening.isOnWeekendDays()) {
                movieTicketPrice -= movieTicketPrice / 100 * 10;
            }
            price += movieTicketPrice;
        }

        return price;
    }

    public void export(TicketExportFormat exportFormat)
    {
        // Bases on the string respresentations of the tickets (toString), write
        // the ticket to a file with naming convention Order_<orderNr>.txt of
        // Order_<orderNr>.json

        String fileName = "exports/Order_" + orderNr;
        String extension = (exportFormat == TicketExportFormat.JSON ? ".json" : ".txt");

        try (FileWriter fileWriter = new FileWriter(fileName + extension)) {
            if(exportFormat == TicketExportFormat.JSON) {
                // Gson settings
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .excludeFieldsWithoutExposeAnnotation()
                        .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (LocalDateTime src, Type typeOfSrc, JsonSerializationContext context ) -> new JsonPrimitive(src.toString()))
                        .serializeNulls()
                        .disableHtmlEscaping()
                        .create();

                // Tickets to json file
                gson.toJson(tickets, fileWriter);

                fileWriter.flush();
                return;
            }

            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (MovieTicket movieTicket: tickets) {
                printWriter.println(movieTicket.toString());
            }
            printWriter.close();
            fileWriter.flush();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not open json file", e);
        }
    }

    private int calculateFreeTicketCount(int ticketCount)
    {
        return (int) Math.floor((double) ticketCount / 2);
    }

    private int getWeeklyDayTicketCount(List<MovieTicket> tickets)
    {
        int dayOfWeekTickets = 0;
        for(MovieTicket movieTicket: tickets) {
            MovieScreening movieScreening = movieTicket.getMovieScreening();
            if(movieScreening != null && movieScreening.isOnMonTroughThuDays()) {
                dayOfWeekTickets++;
            }
        }
        return dayOfWeekTickets;
    }

    private int getWeekendDayTicketCount(List<MovieTicket> tickets) {
        int dayOfWeekTickets = 0;
        for(MovieTicket movieTicket: tickets) {
            MovieScreening movieScreening = movieTicket.getMovieScreening();
            if(movieScreening != null && movieScreening.isOnWeekendDays()) {
                dayOfWeekTickets++;
            }
        }
        return dayOfWeekTickets;
    }

    /**
     * This function returns the extra price of an premium ticket
     * TODO: Move logic to MovieTicket (Because it is ticket logic)
     * @param isStudentOrder boolean
     * @param isPremiumTicket boolean
     * @return int Extra price
     */
    private int getExtraTicketPrice(boolean isStudentOrder, boolean isPremiumTicket) {
        if(!isPremiumTicket) {
            return 0;
        }

        if(!isStudentOrder) {
            // A premium ticket for non-students 3,- extra
            return 3;
        }

        // A premium ticket for students 2,- extra
        return 2;
    }
}
