package org.example.util;

import org.example.model.Order;
import org.example.model.OrderBookData;
import org.example.repository.OrderBookRepository;
import org.example.service.PrinterService;

import java.math.BigDecimal;

public class VolumeChangeCalculator {
    private final OrderBookRepository orderBookRepository;
    private final PrinterService printerService;

    public VolumeChangeCalculator(OrderBookRepository orderBookRepository, PrinterService printerService) {
        this.orderBookRepository = orderBookRepository;
        this.printerService = printerService;
    }

    public void calculateVolumeChangeForAllSymbols() {
        for (String symbol : orderBookRepository.findAllKeys()) {
            OrderBookData orderBookData = orderBookRepository.getByKey(symbol);
            BigDecimal totalVolumeChange = calculateVolumeChange(orderBookData);
            printerService.printVolumeChange(symbol, totalVolumeChange);
        }
        orderBookRepository.clear();
    }

    private BigDecimal calculateVolumeChange(OrderBookData orderBookData) {
        BigDecimal totalVolumeChange = BigDecimal.ZERO;

        if(orderBookData == null) {
            return totalVolumeChange;
        }

        for (Order bid : orderBookData.bids()) {
            totalVolumeChange = totalVolumeChange.add(bid.price().multiply(bid.quantity()));
        }

        for (Order ask : orderBookData.asks()) {
            totalVolumeChange = totalVolumeChange.add(ask.price().multiply(ask.quantity()));
        }

        return totalVolumeChange;
    }
}

