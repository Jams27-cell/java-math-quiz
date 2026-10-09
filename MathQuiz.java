package Task09_3;

import java.sql.*;
import java.util.Random;

public class MathQuiz {

    private final Connection conn;
    private final Random random = new Random();

    private String username;
    private int score;
    private int answer;

    public MathQuiz() throws SQLException {
        conn = DriverManager.getConnection(
                "jdbc:derby:PlayerDB;create=true", "pdc", "pdc");

        try (ResultSet tables = conn.getMetaData().getTables(
                null, null, "USERINFO", new String[]{"TABLE"})) {
            if (!tables.next()) {
                try (Statement statement = conn.createStatement()) {
                    statement.executeUpdate(
                            "CREATE TABLE UserInfo "
                            + "(userid VARCHAR(12), password VARCHAR(12), score INT)");
                }
            }
        }
    }

    public boolean login(String username, String password) throws SQLException {
        if (username == null || username.isBlank()
                || password == null || password.isBlank()
                || username.length() > 12 || password.length() > 12) {
            return false;
        }

        try (PreparedStatement query = conn.prepareStatement(
                "SELECT password, score FROM UserInfo WHERE userid = ?")) {
            query.setString(1, username);

            try (ResultSet result = query.executeQuery()) {
                if (result.next()) {
                    if (!password.equals(result.getString("password"))) {
                        return false;
                    }
                    score = result.getInt("score");
                } else {
                    try (PreparedStatement insert = conn.prepareStatement(
                            "INSERT INTO UserInfo (userid, password, score) "
                            + "VALUES (?, ?, 0)")) {
                        insert.setString(1, username);
                        insert.setString(2, password);
                        insert.executeUpdate();
                    }
                    score = 0;
                }
            }
        }

        this.username = username;
        return true;
    }

    public int[] newQuestion() {
        int first = random.nextInt(100);
        int second = random.nextInt(100);
        answer = first + second;
        return new int[]{first, second};
    }

    public void checkAnswer(int userAnswer) {
        score += userAnswer == answer ? 10 : -10;
    }

    public int getScore() {
        return score;
    }

    public void saveScore() throws SQLException {
        try (PreparedStatement update = conn.prepareStatement(
                "UPDATE UserInfo SET score = ? WHERE userid = ?")) {
            update.setInt(1, score);
            update.setString(2, username);
            update.executeUpdate();
        }
    }

    public void close() throws SQLException {
        conn.close();
    }
}