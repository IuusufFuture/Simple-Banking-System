package banking.controller;
import java.sql.*;

public class DataBaseController {

    private static String urlToDatabaseCreation;

    public static void createDBandTable(String fileName) {
        urlToDatabaseCreation = "jdbc:sqlite:" + fileName;

        String queryCreateTable = "CREATE TABLE IF NOT EXISTS card (\n"
                + "	id INTEGER,\n"
                + "	number TEXT NOT NULL,\n"
                + "	pin TEXT NOT NULL,\n"
                + "	balance INTEGER DEFAULT 0,\n"
                + "PRIMARY KEY('id' AUTOINCREMENT)"
                + ");";

        try (Connection conn = DriverManager.getConnection(urlToDatabaseCreation);
             Statement stmt = conn.createStatement()) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
            stmt.executeUpdate(queryCreateTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insertData(String cardNumber, String pin, Integer balance) {
        String queryInsert = "INSERT INTO card(id, number, pin, balance) VALUES(?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(urlToDatabaseCreation);
             PreparedStatement prepStmt = conn.prepareStatement(queryInsert)) {
            //prepStmt.setInt(1, id);
            prepStmt.setString(2, cardNumber);
            prepStmt.setString(3, pin);
            prepStmt.setInt(4, balance);
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void addBalanceAmount(String cardNumber, int deposit) {
        String queryInsert = "UPDATE card " +
                "SET balance = balance + ? " +
                "WHERE number = ?";

        try (Connection conn = DriverManager.getConnection(urlToDatabaseCreation);
             PreparedStatement pstmt = conn.prepareStatement(queryInsert)) {

            // set the corresponding param
            pstmt.setString(2, cardNumber);
            pstmt.setInt(1, deposit);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteAccount(String cardNumber) {
        String sql = "DELETE FROM card WHERE number = ?";

        try (Connection conn = DriverManager.getConnection(urlToDatabaseCreation);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, cardNumber);
            // execute the delete statement
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
