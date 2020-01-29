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

        tickets = new ArrayList<MovieTicket>();
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
        double price = 0.0;

        // Check if tickets is empty
        if(tickets.isEmpty()) {
            return price;
        }

        // Calculate price
        for(MovieTicket movieTicket: tickets) {
            price += movieTicket.getPrice();
        }

        int freeTicketsCount;
        int weekendDayDiscountTicketsCount = 0;
        int ticketsCount = tickets.size();

        // Sort movie tickets so that the cheapest tickets are in front of array
        Collections.sort(tickets);

        if(isStudentOrder) {
            // Elk 2e ticket is gratis voor studenten (elke dag van de week)

            // Calculate free tickets
            freeTicketsCount = getFreeTicketCount(ticketsCount);
        } else {
            // of als het een voorstelling betreft op een doordeweekse dag (ma/di/wo/do) voor iedereen.
            int weeklyDayTicketCount = getWeeklyDayTicketCount(tickets);

            // Calculate free tickets
            freeTicketsCount = getFreeTicketCount(weeklyDayTicketCount);

            weekendDayDiscountTicketsCount = getWeekendDayTicketCount(tickets);
        }

        // If free tickets, then remove ticket price of price
        for (int i = 0; i < freeTicketsCount; i++) {
            price -= tickets.get(i).getPrice();
        }

        // Check for weekendDayTickets if 6 or more
        if(weekendDayDiscountTicketsCount >= 6) {
            // Do price conversion 10% off...
            for (int i = freeTicketsCount; i < ticketsCount; i++) {
                MovieTicket movieTicket = tickets.get(i);
                DayOfWeek dayOfWeek = DayOfWeek.from(movieTicket.getDateTime());
                if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                    double discount = movieTicket.getPrice() / 100 * 10;
                    price -= discount;
                }
            }
        }

        return price;
    }

    public void export(TicketExportFormat exportFormat)
    {
        // Bases on the string respresentations of the tickets (toString), write
        // the ticket to a file with naming convention Order_<orderNr>.txt of
        // Order_<orderNr>.json
    }

    private int getFreeTicketCount(int ticketCount)
    {
        return (int) Math.floor((double) ticketCount / 2);
    }

    private int getWeeklyDayTicketCount(List<MovieTicket> tickets)
    {
        DayOfWeek[] weeklyDays = new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY};
        List<DayOfWeek> weeklyDaysList = Arrays.asList(weeklyDays);

        return getDayOfWeekTicketCount(tickets, weeklyDaysList);
    }

    private int getWeekendDayTicketCount(List<MovieTicket> tickets) {
        DayOfWeek[] weeklyDays = new DayOfWeek[]{DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};
        List<DayOfWeek> weeklyDaysList = Arrays.asList(weeklyDays);

        return getDayOfWeekTicketCount(tickets, weeklyDaysList);
    }

    private int getDayOfWeekTicketCount(List<MovieTicket> tickets, List<DayOfWeek> daysOfWeek) {
        int dayOfWeekTickets = 0;
        for(MovieTicket movieTicket: tickets) {
            DayOfWeek dayOfWeek = DayOfWeek.from(movieTicket.getDateTime());
            // Check if dayOfWeek is in list of days of week
            if(daysOfWeek.contains(dayOfWeek)) {
                dayOfWeekTickets++;
            }
        }
        return dayOfWeekTickets;
    }
}
