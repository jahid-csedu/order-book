package org.example.model;

import java.math.BigDecimal;

public record Order(
        BigDecimal price,
        BigDecimal quantity
) {
}
