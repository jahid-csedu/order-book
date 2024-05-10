package org.example.util;

import org.example.model.Order;
import org.example.model.OrderBookData;
import org.example.repository.OrderBookRepository;
import org.example.service.PrinterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VolumeChangeCalculatorTest {

    @Mock
    private OrderBookRepository orderBookRepository;

    @Mock
    private PrinterService printerService;

    private VolumeChangeCalculator volumeChangeCalculator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        volumeChangeCalculator = new VolumeChangeCalculator(orderBookRepository, printerService);
    }

    @Test
    void testCalculateVolumeChangeForAllSymbols() {
        OrderBookData orderBookData1 = new OrderBookData(
                List.of(new Order(BigDecimal.valueOf(10), BigDecimal.valueOf(5))),
                List.of(new Order(BigDecimal.valueOf(11), BigDecimal.valueOf(3)))
        );
        OrderBookData orderBookData2 = new OrderBookData(
                List.of(new Order(BigDecimal.valueOf(20), BigDecimal.valueOf(8))),
                List.of(new Order(BigDecimal.valueOf(21), BigDecimal.valueOf(4)))
        );

        when(orderBookRepository.findAllKeys()).thenReturn(Set.of("BTCUSDT", "ETHUSDT"));
        when(orderBookRepository.getByKey("BTCUSDT")).thenReturn(orderBookData1);
        when(orderBookRepository.getByKey("ETHUSDT")).thenReturn(orderBookData2);

        volumeChangeCalculator.calculateVolumeChangeForAllSymbols();

        verify(printerService).printVolumeChange(eq("BTCUSDT"), eq(BigDecimal.valueOf(83)));
        verify(printerService).printVolumeChange(eq("ETHUSDT"), eq(BigDecimal.valueOf(244)));

        verify(orderBookRepository).clear();
    }
}
