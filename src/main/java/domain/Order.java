package domain;

import com.google.gson.*;
import com.google.gson.annotations.Expose;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    }

    /**
     * This function will calculate the order price
     * To check the price buildup you could use // System.out.println("Ticket " + i + ": Price " + movieTicket.getPrice() + " , Extra price: " + movieTicketExtraPrice + " , Discount: " + discount);
     * @return
     */
    public double calculatePrice()
    {
        // Check if tickets is empty
        if(tickets.isEmpty()) {
            return 0.0;
        }

        // Sort movie tickets so that the cheapest tickets are in front of array
        Collections.sort(tickets);

        // Hold variables
        double price = 0.0;
        int freeTicketsCount = getFreeTicketCount(isStudentOrder, tickets);
        int weekendDayDiscountTicketsCount = getWeekendDayTicketCount(isStudentOrder, tickets);
        int ticketsCount = tickets.size();

        // Calculate price
        for (int i = 0; i < ticketsCount; i++) {
            // If there are free tickets. Don't calculate tickets in price so continue.
            if(i < freeTicketsCount) {
                continue;
            }

            MovieTicket movieTicket = tickets.get(i);
            double movieTicketExtraPrice = getExtraTicketPrice(isStudentOrder, movieTicket.isPremiumTicket());
            double movieTicketPrice = movieTicket.getPrice() + movieTicketExtraPrice;

            // Check for weekendDayTickets if 6 or more
            if(weekendDayDiscountTicketsCount >= 6) {
                DayOfWeek dayOfWeek = DayOfWeek.from(movieTicket.getDateTime());
                // Check if day of week is saturday of sunday
                if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                    movieTicketPrice -= movieTicketPrice / 100 * 10;
                }
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

        switch (exportFormat) {
            case JSON: {
                try {
                    FileWriter fileWriter = new FileWriter(fileName + ".json");
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
                    fileWriter.close();
                } catch (IOException e) {
                    logger.log(Level.WARNING, "Could not open json file", e);
                }
                break;
            }
            case PLAINTEXT: {
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter(fileName + ".txt");
                    PrintWriter printWriter = new PrintWriter(fileWriter);
                    for (MovieTicket movieTicket: tickets) {
                        printWriter.println(movieTicket.toString());
                    }
                    printWriter.close();
                } catch (IOException e) {
                    logger.log(Level.WARNING, "Could not open plaintext file", e);
                }
                break;
            }
        }

    }

    private int getFreeTicketCount(boolean isStudentOrder, ArrayList<MovieTicket> movieTickets)
    {
        // Calculate free tickets, every 2'nd ticket is free for students
        if(isStudentOrder) {
            return calculateFreeTicketCount(movieTickets.size());
        }

        // If movie on (mon/tue/wed/thu) then every 2'nd ticket is free.
        int weeklyDayTicketCount = getWeeklyDayTicketCount(movieTickets);

        return calculateFreeTicketCount(weeklyDayTicketCount);
    }

    private int calculateFreeTicketCount(int ticketCount)
    {
        return (int) Math.floor((double) ticketCount / 2);
    }

    private int getWeeklyDayTicketCount(List<MovieTicket> tickets)
    {
        DayOfWeek[] weeklyDays = new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY};
        return getDayOfWeekTicketCount(tickets, weeklyDays);
    }

    private int getWeekendDayTicketCount(boolean isStudentOrder, List<MovieTicket> tickets) {
        if(isStudentOrder) {
            return 0;
        }

        DayOfWeek[] weekendDays = new DayOfWeek[]{DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};
        return getDayOfWeekTicketCount(tickets, weekendDays);
    }

    private int getDayOfWeekTicketCount(List<MovieTicket> tickets, DayOfWeek[] daysOfWeek) {
        List<DayOfWeek> daysOfWeekList = Arrays.asList(daysOfWeek);

        int dayOfWeekTickets = 0;
        for(MovieTicket movieTicket: tickets) {
            DayOfWeek dayOfWeek = DayOfWeek.from(movieTicket.getDateTime());
            // Check if dayOfWeek is in list of days of week
            if(daysOfWeekList.contains(dayOfWeek)) {
                dayOfWeekTickets++;
            }
        }
        return dayOfWeekTickets;
    }

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
