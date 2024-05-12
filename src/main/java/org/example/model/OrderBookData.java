package org.example.model;

import java.util.Collections;
import java.util.List;

public record OrderBookData(
        List<Order> bids,
        List<Order> asks
) {
    public OrderBookData {
        if (bids == null) {
            bids = Collections.emptyList();
        }
        if (asks == null) {
            asks = Collections.emptyList();
        }
    }
}
