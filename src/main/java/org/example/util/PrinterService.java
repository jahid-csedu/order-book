package org.example.util;

import org.example.model.Order;

import java.math.BigDecimal;
import java.util.List;

public class PrinterService {
    public void printOrderBook(String symbol, List<Order> bids, List<Order> asks) {
        System.out.println("Order Book for " + symbol);
        System.out.println("Bids:");
        printOrderBooks(bids);

        System.out.println("Asks:");
        printOrderBooks(asks);
    }

    public void printVolumeChange(String symbol, BigDecimal volumeChange) {
        System.out.println("Total volume change for " + symbol + ": " + volumeChange);
    }

    private static void printOrderBooks(List<Order> bids) {
        for (Order bid : bids) {
            BigDecimal price = bid.price();
            BigDecimal quantity = bid.quantity();
            System.out.println("Price: " + price + " Quantity: " + String.format("%.8f", quantity));
        }
    }
}
