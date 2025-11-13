package net.aldarraji.marketplacex.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.aldarraji.marketplacex.Config.DatabaseConfig;
import net.aldarraji.marketplacex.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "", allowedHeaders = "*")
@RestController() //localhost:8080/api
@RequestMapping("api/order/")
public class OrderController {
    private final DatabaseConfig databaseConfig;

    @Autowired
    public OrderController(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @PostMapping("placeOrder/")
    public ResponseEntity<List<Map<String, String>>> placeOrder(@RequestBody List<OrderItem> order) {
        String sql = "INSERT INTO public.order (product_id, order_date, buyer_id, seller_id) VALUES (?, ?, ?, ?)";
        List<Map<String, String>> responseList = new ArrayList<>();
        for (int i = 0; i < order.size(); i++) {
            System.out.println(order.get(i).getProductId());
            System.out.println(order.get(i).getBuyerId());
            System.out.println(order.get(i).getSellerId());
        }

        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword())) {
            for (OrderItem orderItem : order) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, orderItem.getProductId());
                    statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now())); // order_date
                    statement.setInt(3, (orderItem.getBuyerId()));
                    statement.setInt(4, (orderItem.getSellerId()));

                    int resultSet = statement.executeUpdate();
                    Map<String, String> responseItem = new HashMap<>();
                    responseItem.put("product_id", String.valueOf(orderItem.getProductId()));
                    responseItem.put("buyer_id", String.valueOf(orderItem.getBuyerId()));
                    responseItem.put("seller_id", String.valueOf(orderItem.getSellerId()));
                    responseItem.put("status", resultSet == 0 ? "failed" : "success");

                    responseList.add(responseItem);
                } catch (SQLException e) {
                    Map<String, String> errorResponse = new HashMap<>();
                    errorResponse.put("product_id", String.valueOf(orderItem.getProductId()));
                    errorResponse.put("buyer_id", String.valueOf(orderItem.getBuyerId()));
                    errorResponse.put("seller_id", String.valueOf(orderItem.getSellerId()));
                    errorResponse.put("error", e.getMessage());
                    responseList.add(errorResponse);
                }
            }
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        } catch (SQLException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            responseList.add(errorResponse);
            return new ResponseEntity<>(responseList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping("setStatus/")
    public ResponseEntity<String> setStatus(@RequestBody Map<String, String> statusUpdate) {
        //connect to postgresql and update status in orders table
        String sql = "UPDATE public.order SET status = ?, sold_date = ? WHERE order_id = ?";
        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword())) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, statusUpdate.get("status"));
            statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            statement.setInt(3, Integer.parseInt(statusUpdate.get("order_id")));
            int resultSet = statement.executeUpdate();
            if (resultSet == 0) {
                return new ResponseEntity<>("Order status not updated", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>("Order status updated successfully", HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //get all orders
    @GetMapping("getMyOrders/{myId}/")
    public ResponseEntity<String> getMyOrders(@PathVariable Integer myId) {
        String sql = "SELECT * FROM public.order WHERE buyer_id = ?";
        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword())) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, myId);
            ResultSet resultSet = statement.executeQuery();
            List<Map<String, String>> orders = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, String> order = new HashMap<>();
                order.put("order_id", resultSet.getString("order_id"));
                order.put("order_date", resultSet.getString("order_date"));
                order.put("sold_date", resultSet.getString("sold_date"));
                order.put("buyer_id", resultSet.getString("buyer_id"));
                order.put("seller_id", resultSet.getString("seller_id"));
                order.put("status", resultSet.getString("status"));
                order.put("product_id", resultSet.getString("product_id"));
                orders.add(order);
            }
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(orders), HttpStatus.OK);
        } catch (SQLException | JsonProcessingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getMyPurchaseRequests/{myId}/")
    public ResponseEntity<String> getMyPurchaseRequests(@PathVariable Integer myId) {
        String sql = "SELECT * FROM public.order WHERE seller_id = ?";
        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword())) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, myId);
            ResultSet resultSet = statement.executeQuery();
            List<Map<String, String>> orders = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, String> order = new HashMap<>();
                order.put("order_id", resultSet.getString("order_id"));
                order.put("order_date", resultSet.getString("order_date"));
                order.put("sold_date", resultSet.getString("sold_date"));
                order.put("buyer_id", resultSet.getString("buyer_id"));
                order.put("seller_id", resultSet.getString("seller_id"));
                order.put("status", resultSet.getString("status"));
                order.put("product_id", resultSet.getString("product_id"));
                orders.add(order);
            }
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(orders), HttpStatus.OK);
        } catch (SQLException | JsonProcessingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

