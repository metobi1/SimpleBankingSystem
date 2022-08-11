package banking;

import org.sqlite.SQLiteDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

    private final String fileName;
    private final SQLiteDataSource dataSource =
            new SQLiteDataSource();

    public DataBase(String fileName) {
        this.fileName = fileName;
    }

    private SQLiteDataSource loadUrl() {
        String url = String.format("jdbc:sqlite:%s", fileName);
        dataSource.setUrl(url);
        return dataSource;
    }

    public void loadUpdateStatement(String action) {

        try (Connection con = loadUrl().getConnection()){
            try (Statement statement = con.createStatement()) {
                if ("create".equals(action)) {
                    createTable(statement);
                } else {
                    insertOrUpdate(statement, action);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String loadReturnStatement(String comm, String type, String col) {

        try (Connection con = loadUrl().getConnection()){
            try (Statement statement = con.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(comm)){
                    if (resultSet.isBeforeFirst()) {
                        if ("string".equals(type)) {
                            return resultSet.getString(col);
                        } else if ("int".equals(type)) {
                            return String.valueOf(resultSet.getInt(col));
                        }
                    } else {
                        return "";
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    public void transaction(String sendAction, String receiveAction) {
        try (Connection con = loadUrl().getConnection()){
            con.setAutoCommit(false);
            try (Statement statement = con.createStatement()) {
                insertOrUpdate(statement, sendAction);
                insertOrUpdate(statement, receiveAction);
                con.commit();
            } catch (SQLException e) {
                try {
                    System.err.print("Transaction is being rolled back");
                    con.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createTable(Statement statement) {
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

    public void insertOrUpdate(Statement statement, String insertOrUpdate) {
        try {
            statement.executeUpdate(insertOrUpdate);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
