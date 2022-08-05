package banking;

import org.sqlite.SQLiteDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class DataBase {

    private final String fileName;
    private SQLiteDataSource dataSource =
            new SQLiteDataSource();
    private Statement statement = null;

    public DataBase(String fileName) {
        this.fileName = fileName;
    }

    private SQLiteDataSource loadUrl() {
        String url = String.format("jdbc:sqlite:%s", fileName);
        dataSource.setUrl(url);
        return dataSource;
    }

    private Connection connectDataBase() {
        try {
            Connection con = loadUrl().getConnection();
           return con;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void loadJdbcStatement() {
        Connection con = connectDataBase();
        try {
            if (con != null) {
                statement = con.createStatement();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createTable() {
        try {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                    "id INTEGER," +
                    "number TEXT," +
                    "pin TEXT," +
                    "balance INTEGER DEFAULT 0)");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertValues(int id, String cardNum,
                             String pin, int balance) {
        String updateValues = String.format("INSERT INTO card VALUES " +
                "(%d, %s, %s, %d)", id, cardNum, pin, balance);
        try {
            statement.executeUpdate(updateValues);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ResultSet getResultSet() {
        try {
            ResultSet accounts = statement.executeQuery("SELECT * FROM card");
            return accounts;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String[] getAccount(String card, String pin) {
        try {
            ResultSet resultSet = getResultSet();
            while (resultSet.next()) {
                String cCardStr = resultSet.getString("number");
                String cCardPin = resultSet.getString("pin");
                int balance = resultSet.getInt("balance");
                String bal = String.valueOf(balance);
                if (Objects.equals(card, cCardStr) && Objects.equals(pin, cCardPin)) {
                    return new String[] {cCardStr, cCardPin, bal};
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
