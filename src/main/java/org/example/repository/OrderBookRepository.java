package org.example.repository;

import org.example.model.OrderBookData;

import java.util.Map;
import java.util.Set;

public class OrderBookRepository {
    private Map<String, OrderBookData> orderBooks;

    public OrderBookRepository(Map<String, OrderBookData> orderBooks) {
        this.orderBooks = orderBooks;
    }

    public Set<String> findAllKeys() {
        return orderBooks.keySet();
    }

    public void clear() {
        orderBooks.replaceAll((k, v) -> null);
    }

    public OrderBookData getByKey(String key) {
        return orderBooks.get(key);
    }

    public void save(String key, OrderBookData orderBookData) {
        orderBooks.put(key, orderBookData);
    }
}
