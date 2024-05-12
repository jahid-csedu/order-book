package org.example;

import org.example.config.ConfigurationManager;
import org.example.repository.OrderBookRepository;
import org.example.service.OrderBookManagerService;
import org.example.util.PrinterService;
import org.example.util.VolumeChangeCalculator;

import java.util.HashMap;

public class OrderBook {
    public static void main(String[] args) throws InterruptedException {
        PrinterService printerService = new PrinterService();
        OrderBookRepository orderBookRepository = new OrderBookRepository(new HashMap<>());
        VolumeChangeCalculator volumeChangeCalculator = new VolumeChangeCalculator(orderBookRepository, printerService);

        new OrderBookManagerService(printerService, orderBookRepository);

        while (true) {
            Thread.sleep(ConfigurationManager.getVolumeChangeCalculationInterval() * 1000);
            volumeChangeCalculator.calculateVolumeChangeForAllSymbols();
        }
    }
}