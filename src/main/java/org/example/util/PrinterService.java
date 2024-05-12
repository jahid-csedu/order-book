package org.example.util;

import org.example.model.Order;
import org.example.util.logger.CustomLogger;

import java.math.BigDecimal;
import java.util.List;

public class PrinterService {
    public void printOrderBook(String symbol, List<Order> bids, List<Order> asks) {
        CustomLogger.info("Order Book for " + symbol);
        CustomLogger.info("Bids:");
        printOrderBooks(bids);

        CustomLogger.info("Asks:");
        printOrderBooks(asks);
    }

    public void printVolumeChange(String symbol, BigDecimal volumeChange) {
        CustomLogger.info("Total volume change for " + symbol + ": " + volumeChange);
    }

    private static void printOrderBooks(List<Order> bids) {
        for (Order bid : bids) {
            BigDecimal price = bid.price();
            BigDecimal quantity = bid.quantity();
            CustomLogger.info("Price: " + price + " Quantity: " + String.format("%.8f", quantity));
        }
    }
}
