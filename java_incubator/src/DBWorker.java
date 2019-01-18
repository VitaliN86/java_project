import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DBWorker {
    private Connection connection;
    private static DBWorker instance;

    private DBWorker(int port, String dbName, String login, String pass) {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:" + port + "/" + dbName, login, pass);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database!");
        }
    }

    public static DBWorker getInstance() {
        if (instance == null) {
            instance = new DBWorker(5432, "java_incubator", "postgres", "postgres");
        }
        return instance;
    }

    public List<URLInformation> getURLs() throws SQLException {
        List<URLInformation> res = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM public.url_info order by id;");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String url = resultSet.getString("url");
            LocalDate date = resultSet.getObject("date", LocalDate.class);
            Integer status = resultSet.getInt("status");
            if (resultSet.wasNull()) {
                status = null;
            }
            res.add(new URLInformation(id, url, date, status));
        }
        return res;
    }

    public void changeURLInfo(int id, Integer newStatus, LocalDate newDate) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "update public.url_info set status = ?, date = ? where id = ?;";
        PreparedStatement stat = connection.prepareStatement(query);
        if (newStatus == null) {
            stat.setNull(1, Types.INTEGER);
        } else {
            stat.setInt(1, newStatus);
        }
        stat.setObject(2, newDate);
        stat.setInt(3, id);
        stat.executeUpdate();
        stat.close();
    }
}
