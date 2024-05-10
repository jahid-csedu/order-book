package org.example.service;

import com.google.gson.Gson;
import org.example.client.OrderBookClient;
import org.example.model.ApiResponse;
import org.example.model.Order;
import org.example.model.OrderBookData;
import org.example.repository.OrderBookRepository;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class OrderBookManagerService {
    private static final String BTC_USDT_ENDPOINT = "wss://stream.binance.com:9443/ws/btcusdt@depth";
    private static final String ETH_USDT_ENDPOINT = "wss://stream.binance.com:9443/ws/ethusdt@depth";
    private static final String DEPTH_UPDATE = "depthUpdate";
    private final PrinterService printerService;
    private final OrderBookRepository orderBookRepository;

    public OrderBookManagerService(PrinterService printerService, OrderBookRepository orderBookRepository) {
        this.printerService = printerService;
        this.orderBookRepository = orderBookRepository;

        connectToClient(BTC_USDT_ENDPOINT);
        connectToClient(ETH_USDT_ENDPOINT);
    }

    private void connectToClient(String endpoint) {
        try {
            URI uri = new URI(endpoint);
            OrderBookClient ethUsdtClient = new OrderBookClient(uri, this::handleMessage);
            ethUsdtClient.connect();
        } catch (URISyntaxException e) {
            System.err.println("Error occured: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void handleMessage(String message) {
        ApiResponse apiResponse = new Gson().fromJson(message, ApiResponse.class);
        if (apiResponse.e().equals(DEPTH_UPDATE)) {
            handleDepthUpdate(apiResponse);
        }
    }

    private void handleDepthUpdate(ApiResponse apiResponse) {
        List<Order> bids = parseQuantityAndPrice(apiResponse.b());
        List<Order> asks = parseQuantityAndPrice(apiResponse.a());
        printerService.printOrderBook(apiResponse.s(), bids, asks);
        updateOrderBook(apiResponse.s(), bids, asks);
    }

    private List<Order> parseQuantityAndPrice(String[][] bidsArray) {
        List<Order> bids = new ArrayList<>();
        for (String[] bid : bidsArray) {
            BigDecimal price = new BigDecimal(bid[0]);
            BigDecimal quantity = new BigDecimal(bid[1]);
            bids.add(new Order(price, quantity));
        }
        return bids;
    }

    private void updateOrderBook(String symbol, List<Order> bids, List<Order> asks) {
        OrderBookData orderBookData = orderBookRepository.getByKey(symbol);
        if (orderBookData == null) {
            orderBookData = new OrderBookData(bids, asks);
        } else {
            orderBookData.bids().addAll(bids);
            orderBookData.asks().addAll(asks);
        }
        orderBookRepository.save(symbol, orderBookData);
    }

}
