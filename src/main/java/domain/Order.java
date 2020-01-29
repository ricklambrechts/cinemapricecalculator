package domain;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Order
{
    private int orderNr;
    private boolean isStudentOrder;

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
            double movieTicketPrice = movieTicket.getPrice() + getExtraTicketPrice(isStudentOrder, movieTicket.isPremiumTicket());

            double discount = 0.0;
            // Check for weekendDayTickets if 6 or more
            if(weekendDayDiscountTicketsCount >= 6) {
                DayOfWeek dayOfWeek = DayOfWeek.from(movieTicket.getDateTime());
                // Check if day of week is saturday of sunday
                if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                    discount = movieTicketPrice / 100 * 10;
                    movieTicketPrice -= discount;
                }
            }
            price += movieTicketPrice;

            System.out.println("Ticket " + i + ": Price " + movieTicket.getPrice() + " , Extra price: " + getExtraTicketPrice(isStudentOrder, movieTicket.isPremiumTicket()) + " , Discount: " + discount);
        }

        return price;
    }

    public void export(TicketExportFormat exportFormat)
    {
        // Bases on the string respresentations of the tickets (toString), write
        // the ticket to a file with naming convention Order_<orderNr>.txt of
        // Order_<orderNr>.json
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
            // Een premium ticket is voor niet-studenten 3,- duurder dan de standaardprijs
            return 3;
        }

        // Een premium ticket is voor studenten 2,- duurder dan de standaardprijs
        return 2;
    }
}
