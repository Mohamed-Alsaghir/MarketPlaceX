package net.aldarraji.marketplacex.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.aldarraji.marketplacex.Config.DatabaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController() //localhost:8080/api
@RequestMapping("api/inbox/")
public class InboxController {
    private final DatabaseConfig databaseConfig;
    private static InboxController instance;

    @Autowired
    private InboxController(DatabaseConfig databaseConfig) {
        this.databaseConfig = DatabaseConfig.getInstance();
    }

    public static synchronized InboxController getInstance(DatabaseConfig databaseConfig) {
        if (instance == null) {
            instance = new InboxController(databaseConfig);
        }
        return instance;
    }


    //inbox
    @PostMapping("send/")
    public ResponseEntity<String> send(@RequestBody Map<String, Object> messageDetails) {

        String sql = "{? = call add_message_and_return_id(?, ?)}";
        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword())) {
            CallableStatement statement = connection.prepareCall(sql);
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setString(2, messageDetails.get("title").toString());
            statement.setString(3, messageDetails.get("message").toString());
            statement.execute();
            int returnedId = statement.getInt(1);
            if (returnedId == 0) {
                return new ResponseEntity<>("Message not sent", HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                String sql2 = "INSERT INTO inbox (user_id, message_id) VALUES (?, ?)";
                try (Connection connection2 = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword())) {
                    PreparedStatement statement2 = connection.prepareStatement(sql2);
                    statement2.setInt(1, Integer.parseInt(messageDetails.get("user_id").toString()));
                    statement2.setInt(2, returnedId);
                    statement2.executeUpdate();
                    return new ResponseEntity<>("Message sent successfully", HttpStatus.OK);
                } catch (SQLException e) {
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Message failed to send", HttpStatus.OK);









        /*String sql = "INSERT INTO public.message (title, text) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword())) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, messageDetails.get("title").toString());
            statement.setString(2, messageDetails.get("text").toString());
            int ResultSet = statement.executeUpdate();
            if (ResultSet == 0) {
                return new ResponseEntity<>("Message not sent", HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                String sql2 = "SELECT messag_id FROM inbox WHERE title=? AND text=?";
                try (Connection connection2 = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword())) {
                    PreparedStatement statement2 = connection.prepareStatement(sql);
                    statement2.setInt(1, Integer.parseInt(messageDetails.get("user_id").toString()));
                    statement2.setString(2, messageDetails.get("title").toString());
                    statement2.setString(3, messageDetails.get("text").toString());
                    statement2.executeUpdate();
                    return new ResponseEntity<>("Message sent successfully", HttpStatus.OK);
                } catch (SQLException e) {
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            return new ResponseEntity<>("Message sent successfully", HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String sql3 = "INSERT INTO inbox (user_id, title, text) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword())) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(messageDetails.get("user_id").toString()));
            statement.setString(2, messageDetails.get("title").toString());
            statement.setString(3, messageDetails.get("text").toString());
            statement.executeUpdate();
            return new ResponseEntity<>("Message sent successfully", HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }*/
    }

    @GetMapping ("fetchMessages/{user_id}/")
    public ResponseEntity<String> fetchMessages(@PathVariable String user_id) {

        String sql = "SELECT * FROM getUserMessagesFromInbox2(?);";
        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword())) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(user_id));
            ResultSet result = statement.executeQuery();
            //foreach result, add to json object
            List<Map<String, String>> messagesList = new ArrayList<>();
            while (result.next()) {
                Map<String, String> message = new HashMap<>();
                message.put("title", result.getString("title"));
                message.put("text", result.getString("text"));
                messagesList.add(message);
            }
            //convert interests into json
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(messagesList);
            //return json
            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (SQLException | JsonProcessingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }











        /*String sql = "SELECT * FROM inbox WHERE user_id = ?";
        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword())) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(user_id));
            ResultSet result = statement.executeQuery();
            //foreach result, add to json object
            List<Map<String, String>> messagesList = new ArrayList<>();
            while (result.next()) {
                Map<String, String> message = new HashMap<>();
                message.put("title", result.getString("title"));
                message.put("text", result.getString("text"));
                messagesList.add(message);
            }
            //convert interests into json
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(messagesList);
            //return json
            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (SQLException | JsonProcessingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }*/
    }

    @GetMapping ("view/{user_id}/{message_id}/")
    public ResponseEntity < String > viewMessage (@PathVariable String user_id, @PathVariable String message_id){
        String sql = "SELECT title, text FROM getOneMessageFromOneUser(?,?);";
        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword())) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(user_id));
            statement.setInt(2, Integer.parseInt(message_id));
            ResultSet result = statement.executeQuery();
            //foreach result, add to json object
            Map<String, String> message = new HashMap<>();
            while (result.next()) {
                message.put("title", result.getString("title"));
                message.put("text", result.getString("text"));
            }
            //convert interests into json
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(message);
            //return json
            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (SQLException | JsonProcessingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


        /*String sql = "SELECT * FROM inbox WHERE user_id = ? AND message_id = ?";
        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword())) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(user_id));
            statement.setInt(2, Integer.parseInt(message_id));
            ResultSet result = statement.executeQuery();
            //foreach result, add to json object
            Map < String, String > messages = new HashMap < > ();
            while (result.next()) {
                messages.put("title", result.getString("title"));
                messages.put("text", result.getString("text"));
            }
            //convert interests into json
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(messages);
            //return json
            return new ResponseEntity < > (json, HttpStatus.OK);
        } catch (SQLException | JsonProcessingException e) {
            return new ResponseEntity < > (e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }*/
    }

    @GetMapping ("markAsRead/{message_id}/")
    public ResponseEntity < String > markAsRead (@PathVariable String message_id){
        String sql = "UPDATE public.message SET isread = true WHERE message_id = ?;";
        //String sql = "UPDATE inbox SET isRead = true WHERE user_id = ? AND id = ?";
        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword())) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(message_id));
            int resultSet = statement.executeUpdate();
            if (resultSet == 0) {
                return new ResponseEntity < > ("Message not marked as read", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity < > ("Message marked as read", HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity < > (e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}