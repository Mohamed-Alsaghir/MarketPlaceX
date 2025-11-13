package net.aldarraji.marketplacex.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.aldarraji.marketplacex.Config.DatabaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@CrossOrigin(origins = "", allowedHeaders = "*")
@RestController
@RequestMapping("api/product/")
public class ProductController {

    private final DatabaseConfig databaseConfig;

    @Autowired
    public ProductController(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @PostMapping("add/")
    public ResponseEntity<String> addProduct(@RequestBody Map<String, String> product) {
        String sql = "INSERT INTO product (type, price, yearOfProduction, color, condition, name, isAvailable, addedDate, owner_id, imageURL, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword());
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.get("type"));
            statement.setBigDecimal(2, new BigDecimal(product.get("price")));
            statement.setInt(3, Integer.parseInt(product.get("yearOfProduction")));
            statement.setString(4, product.get("color"));
            statement.setString(5, product.get("condition"));
            statement.setString(6, product.get("name"));
            statement.setBoolean(7, true);
            statement.setDate(8, Date.valueOf(product.get("addedDate"))); // Assuming format 'yyyy-mm-dd'
            statement.setInt(9, Integer.parseInt(product.get("owner_id")));
            statement.setString(10, product.get("imageURL"));
            statement.setString(11, product.get("description"));
            statement.executeUpdate();

            String sqlInterest = "SELECT owner_id FROM interest WHERE type = ?";
            try (PreparedStatement statementInterest = connection.prepareStatement(sqlInterest)) {
                statementInterest.setString(1, product.get("type"));
                ResultSet resultSetInterest = statementInterest.executeQuery();
                while (resultSetInterest.next()) {
                    int userId = resultSetInterest.getInt("owner_id");
                    // Create a message for each user
                    Map<String, Object> messageDetails = new HashMap<>();
                    messageDetails.put("title", "New product in your interest category");
                    messageDetails.put("message", "A new product of type " + product.get("type") + " has been added: " + product.get("name"));
                    messageDetails.put("user_id", String.valueOf(userId));


                    // Add the message to the database and get its id
                    String sqlAddMessage = "{? = call add_message_and_return_id(?, ?)}";
                    try (CallableStatement statementAddMessage = connection.prepareCall(sqlAddMessage)) {
                        statementAddMessage.registerOutParameter(1, Types.INTEGER);
                        statementAddMessage.setString(2, messageDetails.get("title").toString());
                        statementAddMessage.setString(3, messageDetails.get("message").toString());
                        statementAddMessage.execute();
                        int messageId = statementAddMessage.getInt(1);
                        // Add the message id and user id to the inbox
                        String sqlAddToInbox = "INSERT INTO inbox (user_id, message_id) VALUES (?, ?)";
                        try (PreparedStatement statementAddToInbox = connection.prepareStatement(sqlAddToInbox)) {
                            statementAddToInbox.setInt(1, userId);
                            statementAddToInbox.setInt(2, messageId);
                            statementAddToInbox.executeUpdate();
                        }
                    }
                }


            } catch (SQLException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            //notifyInterested(product.get("type"), product.get("name"), product.get("description"));
            return new ResponseEntity<>("Product added successfully", HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("changeAvailabilityAndOwner/")
    public ResponseEntity<String> setAvailability(@RequestBody Map<String, String> updatedInfo) {

        String sql = "UPDATE public.product SET isavailable = ? AND owner_id = ? WHERE product_id = ?";
        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword())) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, Boolean.parseBoolean(updatedInfo.get("isAvailable")));
            statement.setInt(2, Integer.parseInt(updatedInfo.get("owner_id")));
            statement.setInt(3, Integer.parseInt(updatedInfo.get("product_id")));
            int resultSet = statement.executeUpdate();
            if (resultSet == 0) {
                return new ResponseEntity<>("Order status not updated", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>("Order status updated successfully", HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }




        /*String sql = "INSERT INTO product (type, price, yearOfProduction, color, condition, name, isAvailable, addedDate, owner_id, imageURL, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword());
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.get("type"));
            statement.setBigDecimal(2, new BigDecimal(product.get("price")));
            statement.setInt(3, Integer.parseInt(product.get("yearOfProduction")));
            statement.setString(4, product.get("color"));
            statement.setString(5, product.get("condition"));
            statement.setString(6, product.get("name"));
            statement.setBoolean(7, Boolean.parseBoolean(product.get("isAvailable")));
            statement.setDate(8, Date.valueOf(product.get("addedDate"))); // Assuming format 'yyyy-mm-dd'
            statement.setInt(9, Integer.parseInt(product.get("owner_id")));
            statement.setString(10, product.get("imageURL"));
            statement.setString(11, product.get("description"));
            statement.executeUpdate();

            String sqlInterest = "SELECT owner_id FROM interest WHERE type = ?";
            try (PreparedStatement statementInterest = connection.prepareStatement(sqlInterest)) {
                statementInterest.setString(1, product.get("type"));
                ResultSet resultSetInterest = statementInterest.executeQuery();
                while (resultSetInterest.next()) {
                    int userId = resultSetInterest.getInt("owner_id");
                    // Create a message for each user
                    Map<String, Object> messageDetails = new HashMap<>();
                    messageDetails.put("title", "New product in your interest category");
                    messageDetails.put("message", "A new product of type " + product.get("type") + " has been added: " + product.get("name"));
                    messageDetails.put("user_id", String.valueOf(userId));


                    // Add the message to the database and get its id
                    String sqlAddMessage = "{? = call add_message_and_return_id(?, ?)}";
                    try (CallableStatement statementAddMessage = connection.prepareCall(sqlAddMessage)) {
                        statementAddMessage.registerOutParameter(1, Types.INTEGER);
                        statementAddMessage.setString(2, messageDetails.get("title").toString());
                        statementAddMessage.setString(3, messageDetails.get("message").toString());
                        statementAddMessage.execute();
                        int messageId = statementAddMessage.getInt(1);
                        // Add the message id and user id to the inbox
                        String sqlAddToInbox = "INSERT INTO inbox (user_id, message_id) VALUES (?, ?)";
                        try (PreparedStatement statementAddToInbox = connection.prepareStatement(sqlAddToInbox)) {
                            statementAddToInbox.setInt(1, userId);
                            statementAddToInbox.setInt(2, messageId);
                            statementAddToInbox.executeUpdate();
                        }
                    }
                }


            } catch (SQLException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            //notifyInterested(product.get("type"), product.get("name"), product.get("description"));
            return new ResponseEntity<>("Product added successfully", HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }*/
    }


    @GetMapping("search/{query}/")
    public ResponseEntity<String> searchProducts(@PathVariable String query) {
        String sql = "SELECT * FROM product WHERE name ILIKE ?";
        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword());
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, '%' + query + '%');
            ResultSet resultSet = statement.executeQuery();
            List<Map<String, String>> products = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, String> product = new HashMap<>();
                product.put("product_id", resultSet.getString("product_id"));
                product.put("type", resultSet.getString("type"));
                product.put("price", resultSet.getString("price"));
                product.put("yearOfProduction", resultSet.getString("yearOfProduction"));
                product.put("color", resultSet.getString("color"));
                product.put("condition", resultSet.getString("condition"));
                product.put("name", resultSet.getString("name"));
                product.put("isAvailable", resultSet.getString("isAvailable"));
                product.put("addedDate", resultSet.getString("addedDate"));
                product.put("owner_id", resultSet.getString("owner_id"));
                product.put("imageURL", resultSet.getString("imageURL"));
                product.put("description", resultSet.getString("description"));
                products.add(product);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(products);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (SQLException | JsonProcessingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("search/{category}/{minPrice}/{maxPrice}/{condition}/")
    public ResponseEntity<String> searchProducts(@PathVariable String category, @PathVariable String minPrice, @PathVariable String maxPrice, @PathVariable String condition) {
        String sql = "SELECT * FROM product WHERE name ILIKE ? AND price >= ? AND price <= ? AND condition = ?";
        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword());
             PreparedStatement statement = connection.prepareStatement(sql)) {
            System.out.println(category);
            System.out.println(new BigDecimal(minPrice));
            System.out.println(new BigDecimal(maxPrice));
            System.out.println(condition);
            statement.setString(1, '%' + category + '%');
            statement.setBigDecimal(2, new BigDecimal(minPrice));
            statement.setBigDecimal(3, new BigDecimal(maxPrice));
            statement.setString(4, condition);
            ResultSet resultSet = statement.executeQuery();
            List<Map<String, String>> products = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, String> product = new HashMap<>();
                product.put("product_id", resultSet.getString("product_id"));
                product.put("type", resultSet.getString("type"));
                product.put("price", resultSet.getString("price"));
                product.put("yearOfProduction", resultSet.getString("yearOfProduction"));
                product.put("color", resultSet.getString("color"));
                product.put("condition", resultSet.getString("condition"));
                product.put("name", resultSet.getString("name"));
                product.put("isAvailable", resultSet.getString("isAvailable"));
                product.put("addedDate", resultSet.getString("addedDate"));
                product.put("owner_id", resultSet.getString("owner_id"));
                product.put("imageURL", resultSet.getString("imageURL"));
                product.put("description", resultSet.getString("description"));
                products.add(product);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(products);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (SQLException | JsonProcessingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("remove/{productId}/")
    public ResponseEntity<String> removeProduct(@PathVariable String productId) {
        String sql = "DELETE FROM Product WHERE product_id = ?";
        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword());
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(productId));
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                return new ResponseEntity<>("Product removed successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
            }
        } catch (SQLException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("view/{productId}/")
    public ResponseEntity<String> viewProduct(@PathVariable String productId) {
        String sql = "SELECT * FROM Product WHERE product_id = ?";
        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword());
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(productId));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Map<String, String> product = new HashMap<>();
                product.put("product_id", resultSet.getString("product_id"));
                product.put("type", resultSet.getString("type"));
                product.put("price", resultSet.getString("price"));
                product.put("yearOfProduction", resultSet.getString("yearOfProduction"));
                product.put("color", resultSet.getString("color"));
                product.put("condition", resultSet.getString("condition"));
                product.put("name", resultSet.getString("name"));
                product.put("isAvailable", resultSet.getString("isAvailable"));
                product.put("addedDate", resultSet.getString("addedDate"));
                product.put("owner_id", resultSet.getString("owner_id"));
                product.put("imageURL", resultSet.getString("imageURL"));
                product.put("description", resultSet.getString("description"));
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(product);
                return new ResponseEntity<>(json, HttpStatus.OK);
            }
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        } catch (SQLException | JsonProcessingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("viewAll/")
public ResponseEntity<String> viewAllProducts() {
    String sql = "SELECT * FROM product WHERE isAvailable = true";
    try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword());
         PreparedStatement statement = connection.prepareStatement(sql)) {
        ResultSet resultSet = statement.executeQuery();
        List<Map<String, String>> products = new ArrayList<>();
        while (resultSet.next()) {
            Map<String, String> product = new HashMap<>();
            product.put("product_id", resultSet.getString("product_id"));
            product.put("type", resultSet.getString("type"));
            product.put("price", resultSet.getString("price"));
            product.put("yearOfProduction", resultSet.getString("yearOfProduction"));
            product.put("color", resultSet.getString("color"));
            product.put("condition", resultSet.getString("condition"));
            product.put("name", resultSet.getString("name"));
            product.put("isAvailable", resultSet.getString("isAvailable"));
            product.put("addedDate", resultSet.getString("addedDate"));
            product.put("owner_id", resultSet.getString("owner_id"));
            product.put("imageURL", resultSet.getString("imageURL"));
            product.put("description", resultSet.getString("description"));
            products.add(product);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(products);
        return new ResponseEntity<>(json, HttpStatus.OK);
    } catch (SQLException | JsonProcessingException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

}
