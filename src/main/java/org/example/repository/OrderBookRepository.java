package org.example.repository;

import org.example.model.OrderBookData;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OrderBookRepository {
    private Map<String, OrderBookData> orderBooks;

    public OrderBookRepository() {
        this.orderBooks = new HashMap<>();
    }

    public Set<String> findAllKeys() {
        return orderBooks.keySet();
    }

    public void clear() {
        orderBooks.clear();
    }

    public OrderBookData getByKey(String key) {
        return orderBooks.get(key);
    }

    public void save(String key, OrderBookData orderBookData) {
        orderBooks.put(key, orderBookData);
    }

    public Map<String, OrderBookData> findAll() {
        return orderBooks;
    }
}
