package org.example;

import org.example.repository.OrderBookRepository;
import org.example.service.OrderBookManagerService;
import org.example.service.PrinterService;
import org.example.util.VolumeChangeCalculator;

public class OrderBook {
    private static final long VOLUME_CHANGE_INTERVAL_SECONDS = 10;

    public static void main(String[] args) {
        PrinterService printerService = new PrinterService();
        OrderBookRepository orderBookRepository = new OrderBookRepository();
        VolumeChangeCalculator volumeChangeCalculator = new VolumeChangeCalculator(orderBookRepository, printerService);
        OrderBookManagerService orderBookManagerService = new OrderBookManagerService(printerService, orderBookRepository);

        while (true) {
            try {
                Thread.sleep(VOLUME_CHANGE_INTERVAL_SECONDS * 1000);
                volumeChangeCalculator.calculateVolumeChangeForAllSymbols();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}