package domain;

import com.google.gson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class OrderExport {

    private static final Logger logger = LogManager.getLogManager().getLogger(OrderExport.class.getName());

    private Order order;
    private TicketExportFormat ticketExportFormat;
    private FileWriter fileWriter;

    public OrderExport(Order order, TicketExportFormat ticketExportFormat) {
        this.ticketExportFormat = ticketExportFormat;
        this.order = order;
    }

    public OrderExport(Order order, TicketExportFormat ticketExportFormat, FileWriter fileWriter) {
        this.ticketExportFormat = ticketExportFormat;
        this.fileWriter = fileWriter;
        this.order = order;
    }

    public void export() {
        String fileName = "exports/Order_" + order.getOrderNr();
        String extension = (ticketExportFormat == TicketExportFormat.JSON ? ".json" : ".txt");

        try (FileWriter fileWriter = new FileWriter(fileName + extension)) {
            if(ticketExportFormat == TicketExportFormat.JSON) {
                // Gson settings
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .excludeFieldsWithoutExposeAnnotation()
                        .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (LocalDateTime src, Type typeOfSrc, JsonSerializationContext context ) -> new JsonPrimitive(src.toString()))
                        .serializeNulls()
                        .disableHtmlEscaping()
                        .create();

                // Tickets to json file
                gson.toJson(order.getTickets(), fileWriter);

                fileWriter.flush();
                return;
            }
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (MovieTicket movieTicket: order.getTickets()) {
                printWriter.println(movieTicket.toString());
            }
            printWriter.close();
            fileWriter.flush();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not open json file", e);
        }
    }

}
