package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAOImpl implements AccountDAO {

    @Override
    public Account addAccount(Account account) {
        try {
            // Connect to the database
            Connection conn = ConnectionUtil.getConnection();

            // Create the query
            String query = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);

            // Set query parameters
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            // Run the query
            int checkInsert = ps.executeUpdate();

            /*
             * Check if the update was successful.
             * If it was, checkInsert will be 1; otherwise, it will be 0.
             */
            if (checkInsert != 0) {
                /*
                 * Return the newly created Account.
                 * We do not have direct access to this Account, 
                 * so we have to make another SQL query.
                 */
                return getAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Account getAccountById(int account_id) {
        // the Account to return
        Account ret = null;

        try {
            // Connect to the database
            Connection conn = ConnectionUtil.getConnection();

            // Create the query
            String query = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            // Set query parameter
            ps.setInt(1, account_id);

            // Run the query
            ResultSet rs = ps.executeQuery();

            /*
             * Use ResultSet.next() to check if there is data.
             * If possible, advance the cursor to the first row.
             */
            if (rs.next()) {
                // Create a new Account object with the data from the ResultSet.
                ret = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    @Override
    public Account getAccountByUsername(String username) {
        // the Account to return
        Account ret = null;

        try {
            // Connect to the database
            Connection conn = ConnectionUtil.getConnection();

            // Create the query
            String query = "SELECT * FROM account WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            // Set query parameter
            ps.setString(1, username);

            // Run the query
            ResultSet rs = ps.executeQuery();

            /*
             * Use ResultSet.next() to check if there is data.
             * If possible, advance the cursor to the first row.
             */
            if (rs.next()) {
                // Create a new Account object with the data from the ResultSet.
                ret = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    @Override
    public Account getAccountByUsernameAndPassword(String username, String password) {
        // the Account to return
        Account ret = null;

        try {
            // Connect to the database
            Connection conn = ConnectionUtil.getConnection();

            // Create the query
            String query = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            // Set query parameters
            ps.setString(1, username);
            ps.setString(2, password);

            // Run the query
            ResultSet rs = ps.executeQuery();

            /*
             * Use ResultSet.next() to check if there is data.
             * If possible, advance the cursor to the first row.
             */
            if (rs.next()) {
                // Create a new Account object with the data from the ResultSet.
                ret = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

}
