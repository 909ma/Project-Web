package com.mycompany.mycompany;

import java.sql.*;

public class MovieDAO {
    public static String getYesterdayTopMovie() {
        String movieTitle = null;

        // �����ͺ��̽� ���� ����
        String url = "jdbc:mysql://localhost:3306/myproject";
        String username = "root";
        String password = "1234";

        // �����ͺ��̽� ���� �� ���� ����
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT movieNm FROM dailymovie WHERE targetDt = ? AND rank = 1";
            // ���� ��¥ ���
            java.util.Date yesterday = new java.util.Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
            java.sql.Date targetDate = new java.sql.Date(yesterday.getTime());

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setDate(1, targetDate);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        movieTitle = resultSet.getString("movieNm");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movieTitle;
    }
}
