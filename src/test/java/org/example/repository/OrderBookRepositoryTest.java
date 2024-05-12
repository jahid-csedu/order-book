package org.example.repository;

import org.example.model.Order;
import org.example.model.OrderBookData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OrderBookRepositoryTest {
    private Map<String, OrderBookData> orderBooks;

    private OrderBookRepository orderBookRepository;

    @BeforeEach
    void setup() {
        orderBooks = new HashMap<>();
        OrderBookData orderBookData1 = new OrderBookData(
                List.of(new Order(BigDecimal.valueOf(10), BigDecimal.valueOf(5))),
                List.of(new Order(BigDecimal.valueOf(11), BigDecimal.valueOf(3)))
        );
        OrderBookData orderBookData2 = new OrderBookData(
                List.of(new Order(BigDecimal.valueOf(20), BigDecimal.valueOf(8))),
                List.of(new Order(BigDecimal.valueOf(21), BigDecimal.valueOf(4)))
        );
        orderBooks.put("BTCUSDT", orderBookData1);
        orderBooks.put("ETHUSDT", orderBookData2);

        orderBookRepository = new OrderBookRepository(orderBooks);
    }

    @Test
    void testFindAllKeys() {
        var expected = Set.of("BTCUSDT", "ETHUSDT");

        Set<String> actual = orderBookRepository.findAllKeys();

        assertEquals(expected, actual);
    }

    @Test
    void testGetByKey() {
        var expected = new OrderBookData(
                List.of(new Order(BigDecimal.valueOf(10), BigDecimal.valueOf(5))),
                List.of(new Order(BigDecimal.valueOf(11), BigDecimal.valueOf(3)))
        );

        OrderBookData actual = orderBookRepository.getByKey("BTCUSDT");

        assertEquals(expected, actual);
    }

    @Test
    void testSave() {
        OrderBookData orderBookData = new OrderBookData(List.of(), List.of());
        orderBookRepository.save("TEST", orderBookData);

        assertEquals(3, orderBookRepository.findAllKeys().size());
    }

    @Test
    void testClear() {
        orderBookRepository.clear();

        assertNull(orderBookRepository.getByKey("BTCUSDT"));
        assertNull(orderBookRepository.getByKey("ETHUSDT"));
    }

}