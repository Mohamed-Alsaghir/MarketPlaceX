package net.aldarraji.marketplacex.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.aldarraji.marketplacex.Config.DatabaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "", allowedHeaders = "*")
@RestController() //localhost:8080/api
@RequestMapping("api/user/")
public class UserController {
    private final DatabaseConfig databaseConfig;

    @Autowired
    public UserController(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    //User
    @PostMapping("signin/")
    public ResponseEntity<String> signIn(@RequestBody Map<String, String> signin) {
        String selectSql = "SELECT * FROM public.user WHERE username = ? AND password = ?";
        String updateSql = "UPDATE public.user SET isloggedin = true WHERE user_id = ?";

        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword())) {
            PreparedStatement selectStatement = connection.prepareStatement(selectSql);
            selectStatement.setString(1, signin.get("username"));
            selectStatement.setString(2, signin.get("password"));
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String username = resultSet.getString("username");
                String fullname = resultSet.getString("fullname");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                String dateOfBirth = resultSet.getString("dateofbirth");

                PreparedStatement updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setInt(1, userId);
                updateStatement.executeUpdate();

                // Create a map for the user data
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("user_id", userId);
                userMap.put("username", username);
                userMap.put("fullname", fullname);
                userMap.put("email", email);
                userMap.put("address", address);
                userMap.put("dateofbirth", dateOfBirth);
                userMap.put("isLoggedIn", true);

                // Convert map to JSON string
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(userMap);

                return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (SQLException | JsonProcessingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("signup/")
    public ResponseEntity<String> signUp(@RequestBody Map<String, String> signup){
        String sql = "INSERT INTO public.user (fullname, username, password, email, address, dateofbirth, isloggedin) VALUES (?,?,?,?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword())) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, signup.get("fullname"));
            statement.setString(2, signup.get("username"));
            statement.setString(3, signup.get("password"));
            statement.setString(4, signup.get("email"));
            statement.setString(5, signup.get("address"));
            statement.setDate(6, Date.valueOf(signup.get("dateofbirth")));
            statement.setBoolean(7, false);
            statement.executeUpdate();
            return new ResponseEntity<>("User was added successfully", HttpStatus.OK);
        } catch (SQLException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("info/")
    public ResponseEntity<String> getUserInfo(@RequestParam("username") String username) {
        String sql = "SELECT * FROM public.user WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(databaseConfig.getPostgresURL(), databaseConfig.getPostgresUser(), databaseConfig.getPostgresPassword())) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String fullname = resultSet.getString("fullname");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                Date dateOfBirth = resultSet.getDate("dateOfBirth");
                boolean isLoggedIn = resultSet.getBoolean("isLoggedIn");

                // Create a map for the user data
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("id", userId);
                userMap.put("username", username);
                userMap.put("fullname", fullname);
                userMap.put("email", email);
                userMap.put("address", address);
                userMap.put("dateOfBirth", dateOfBirth);
                userMap.put("isLoggedIn", isLoggedIn);

                // Convert map to JSON string
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(userMap);

                return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (SQLException | JsonProcessingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }








}


