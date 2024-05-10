package org.example.model;

import java.util.Arrays;
import java.util.Objects;

public record ApiResponse(
        String e, // Event type
        long E,   // Event time
        String s, // Symbol
        long U,   // First update ID
        long u,   // Final update ID
        String[][] b, // Bid orders
        String[][] a  // Ask orders
) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiResponse that = (ApiResponse) o;
        return E == that.E && U == that.U && u == that.u && Objects.equals(e, that.e) && Objects.equals(s, that.s) && Arrays.equals(b, that.b) && Arrays.equals(a, that.a);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(e, E, s, U, u);
        result = 31 * result + Arrays.deepHashCode(b);
        result = 31 * result + Arrays.deepHashCode(a);
        return result;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "e='" + e + '\'' +
                ", E=" + E +
                ", s='" + s + '\'' +
                ", U=" + U +
                ", u=" + u +
                ", b=" + Arrays.deepToString(b) +
                ", a=" + Arrays.deepToString(a) +
                '}';
    }
}
