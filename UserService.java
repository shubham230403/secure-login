package com.loginapp;

import java.sql.*;

public class UserService {
    private final String DB_URL = "jdbc:sqlite:data/users.db";

    public UserService() {
        createUserTable();
    }

    private void createUserTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS users (username TEXT PRIMARY KEY, salt TEXT, hashed_password TEXT)";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registerUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String salt = PasswordUtils.generateSalt();
            String hashedPassword = PasswordUtils.hashPassword(password, salt);

            String sql = "INSERT INTO users(username, salt, hashed_password) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, salt);
            pstmt.setString(3, hashedPassword);
            pstmt.executeUpdate();
            System.out.println("User registered successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean loginUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT salt, hashed_password FROM users WHERE username=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String salt = rs.getString("salt");
                String storedHashedPassword = rs.getString("hashed_password");
                String inputHashedPassword = PasswordUtils.hashPassword(password, salt);
                return storedHashedPassword.equals(inputHashedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
