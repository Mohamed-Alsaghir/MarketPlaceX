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
@RequestMapping("api/interest/")
public class InterestController {
    private final DatabaseConfig databaseConfig;

    @Autowired
    public InterestController(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @GetMapping("viewMyInterests/{userId}/")
    public ResponseEntity<String> viewAllInterests(@PathVariable String userId) {
        //connect to postgresql and get all interests from interest table, where column name is interest_name, interest_description, interest_image, interest_id
        //return all interests in json format
        String sql = "SELECT * FROM interest WHERE owner_id = ?";
        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword())) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(userId));
            ResultSet result = statement.executeQuery();
            //foreach result, add to json object
            List<Map<String, String>> interests = new ArrayList<>();
            while (result.next()) {
                Map<String, String> interest = new HashMap<>();
                interest.put("interestId", result.getString("interest_id"));
                interest.put("type", result.getString("type"));
                interest.put("addedDate", result.getString("addeddate"));
                interest.put("ownerId", result.getString("owner_id"));
                interests.add(interest);
            }
            //convert interests into json
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(interests);
            //return json
            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (SQLException | JsonProcessingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("view/{interestId}/")
    public ResponseEntity<String> viewOneInterest(@PathVariable String interestId) {
        //connect to postgresql and get all interests from interest table, where column name is interest_name, interest_description, interest_image, interest_id
        //return all interests in json format
        String sql = "SELECT * FROM interest WHERE interest_id = ?";
        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword())) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(interestId));
            ResultSet result = statement.executeQuery();
            //foreach result, add to json object
            Map<String, String> interests = new HashMap<>();
            while (result.next()) {
                interests.put("type", result.getString("type"));
                interests.put("addedDate", result.getString("addedDate"));
                interests.put("owner_id", result.getString("owner_id"));
            }
            //convert interests into json
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(interests);
            //return json
            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (SQLException | JsonProcessingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("add/")
    public ResponseEntity<String> addInterest(@RequestBody Map<String, String> interest) {
        //connect to postgresql and add interest to interest table, where column name is interest_name, interest_description, interest_image, interest_id
        //return all interests in json format
        String sql = "INSERT INTO interest (type, addedDate, owner_id) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword())) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, interest.get("type"));
            java.sql.Date date = java.sql.Date.valueOf(interest.get("addedDate"));
            statement.setDate(2, date);
            statement.setInt(3, Integer.parseInt(interest.get("owner_id")));
            statement.executeUpdate();
            return new ResponseEntity<>("User was added successfully", HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping("remove/{interestId}/")
    public ResponseEntity<String> removeInterest(@PathVariable String interestId) {
        //connect to postgresql and delete interest from interest table, where column name is interest_name, interest_description, interest_image, interest_id
        //return all interests in json format
        String sql = "DELETE FROM interest WHERE interest_id = ?";
        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword())) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(interestId));
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return new ResponseEntity<>(String.format("No interest with id: %s was found", interestId), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(String.format("Interest of id: %s was deleted successfully", interestId), HttpStatus.OK);
            }
            //return new ResponseEntity<>(String.format("Interest of id: %d was deleted successfully", Integer.parseInt(interestId)), HttpStatus.OK);
        } catch (SQLException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

