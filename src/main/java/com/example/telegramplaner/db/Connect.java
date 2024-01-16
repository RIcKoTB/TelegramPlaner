package com.example.telegramplaner.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Клас Connect містить метод для підключення до бази даних.
 */
public class Connect {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/generation_sitemap";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    /**
     * Встановлює з'єднання з базою даних.
     *
     * @return Об'єкт Connection для з'єднання з базою даних.
     */
    public static Connection connect() {
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            return connection;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}