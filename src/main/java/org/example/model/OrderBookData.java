package org.example.model;

import java.util.ArrayList;
import java.util.List;

public record OrderBookData(
        List<Order> bids,
        List<Order> asks
) {
}
