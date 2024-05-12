package org.example.service;

import com.google.gson.Gson;
import org.example.client.OrderBookClient;
import org.example.config.ConfigurationManager;
import org.example.model.ApiResponse;
import org.example.model.Order;
import org.example.model.OrderBookData;
import org.example.repository.OrderBookRepository;
import org.example.util.PrinterService;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class OrderBookManagerService {
    private static final String DEPTH_UPDATE = "depthUpdate";
    private final PrinterService printerService;
    private final OrderBookRepository orderBookRepository;

    public OrderBookManagerService(PrinterService printerService, OrderBookRepository orderBookRepository) {
        this.printerService = printerService;
        this.orderBookRepository = orderBookRepository;

        connectToClient(ConfigurationManager.getBTCUSDTApiEndpoint());
        connectToClient(ConfigurationManager.getETHUSDTApiEndpoint());
    }

    private void connectToClient(String endpoint) {
        try {
            URI uri = new URI(endpoint);
            OrderBookClient client = new OrderBookClient(uri, this::handleMessage);
            client.connect();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URI: " + endpoint, e);
        }
    }

    private void handleMessage(String message) {
        ApiResponse apiResponse = new Gson().fromJson(message, ApiResponse.class);
        if (apiResponse.e().equals(DEPTH_UPDATE)) {
            handleDepthUpdate(apiResponse);
        }
    }

    private void handleDepthUpdate(ApiResponse apiResponse) {
        String symbol = apiResponse.s();
        List<Order> bids = parseQuantityAndPrice(apiResponse.b());
        List<Order> asks = parseQuantityAndPrice(apiResponse.a());
        printerService.printOrderBook(symbol, bids, asks);
        updateOrderBook(symbol, bids, asks);
    }

    private List<Order> parseQuantityAndPrice(String[][] priceAndQuantities) {
        List<Order> orders = new ArrayList<>();
        for (String[] priceAndQuantity : priceAndQuantities) {
            BigDecimal price = new BigDecimal(priceAndQuantity[0]);
            BigDecimal quantity = new BigDecimal(priceAndQuantity[1]);
            orders.add(new Order(price, quantity));
        }
        return orders;
    }

    private void updateOrderBook(String symbol, List<Order> bids, List<Order> asks) {
        OrderBookData currentOrderBookData = orderBookRepository.getByKey(symbol);
        OrderBookData updatedOrderBookData;

        if (currentOrderBookData == null) {
            updatedOrderBookData = new OrderBookData(bids, asks);
        } else {
            updatedOrderBookData = new OrderBookData(
                    mergeOrders(currentOrderBookData.bids(), bids),
                    mergeOrders(currentOrderBookData.asks(), asks)
            );
        }

        orderBookRepository.save(symbol, updatedOrderBookData);
    }

    private List<Order> mergeOrders(List<Order> existingOrders, List<Order> newOrders) {
        List<Order> mergedOrders = new ArrayList<>(existingOrders);
        mergedOrders.addAll(newOrders);
        return mergedOrders;
    }


}
