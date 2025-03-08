package DAO;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAOImpl implements MessageDAO {

    @Override
    public Message addMessage(Message message) {
        // the Message object to return
        Message ret = null;

        try {
            // Connect to the database
            Connection conn = ConnectionUtil.getConnection();

            // Create the query
            String query = "INSERT INTO message (posted_by, message_text, time_posted_epoch) ";
            query += "VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            // Set query parameters
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            // Run the query
            int checkInsert = ps.executeUpdate();

            /*
             * Check if the update was successful.
             * If it was, checkInsert will be 1; otherwise, it will be 0.
             */
            if (checkInsert != 0) {
                /*
                 * Return the newly created Message.
                 * We have access to this Message via the generated keys.
                 */
                ResultSet rs = ps.getGeneratedKeys();

                /*
                * Use ResultSet.next() to check if there is data.
                * If possible, advance the cursor to the first row.
                */
                if (rs.next()) {
                    // Create a new Message object with the data from the ResultSet.
                    int generatedId = rs.getInt("message_id");
                    ret = new Message(
                        generatedId,
                        message.getPosted_by(),
                        message.getMessage_text(),
                        message.getTime_posted_epoch()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return ret;
    }

    @Override
    public List<Message> getAllMessages() {
        // the List to return
        List<Message> ret = new ArrayList<>();

        try {
            // Connect to the database
            Connection conn = ConnectionUtil.getConnection();

            // Create the query
            String query = "SELECT * FROM message";
            Statement st = conn.createStatement();

            // Execute the query
            ResultSet rs = st.executeQuery(query);

            // Use a while loop with ResultSet.next() because there may be multiple rows
            while (rs.next()) {
                // Create a new Message object with the data from the current row
                ret.add(new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    @Override
    public Message getMessageById(int message_id) {
        // the Message to return
        Message ret = null;

        try {
            // Connect to the database
            Connection conn = ConnectionUtil.getConnection();

            // Create the query
            String query = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            // Set query parameter
            ps.setInt(1, message_id);

            // Execute the query
            ResultSet rs = ps.executeQuery();

            /*
             * Use ResultSet.next() to check if there is data.
             * If possible, advance the cursor to the first row.
             */
            if (rs.next()) {
                // Create a new Account object with the data from the ResultSet.
                ret = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    @Override
    public Message deleteMessageById(int message_id) {
        /*
         * First, store the Message with the given ID 
         * so that we can return it even after it's been deleted. 
         * This variable will be null if there is no such Message.
         */
        Message oldMessage = getMessageById(message_id);

        try {
            // Connect to the database
            Connection conn = ConnectionUtil.getConnection();
            
            // Create the query
            String query = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            // Set query parameter
            ps.setInt(1, message_id);

            // Execute the query
            int checkDelete = ps.executeUpdate();

            // checkDelete will be 0 if delete failed
            if (checkDelete != 0) {
                return oldMessage;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Message updateMessageById(int message_id, String message_text) {
        // The Message object to return
        Message ret = null;

        try {
            // Connect to the database
            Connection conn = ConnectionUtil.getConnection();

            // Create the query
            String query = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            // Set query parameters
            ps.setString(1, message_text);
            ps.setInt(2, message_id);

            // Run the query
            int checkUpdate = ps.executeUpdate();

            // checkUpdate will be 0 if update failed
            if (checkUpdate != 0) {
                /*
                 * Return the newly updated Message.
                 * We do not have direct access to this Message, 
                 * so we have to make another SQL query.
                 */
                ret = getMessageById(message_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    @Override
    public List<Message> getMessagesByAccountId(int posted_by) {
        // the List to return
        List<Message> ret = new ArrayList<>();

        try {
            // Connect to the database
            Connection conn = ConnectionUtil.getConnection();

            // Create the query
            String query = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            // Set query parameter
            ps.setInt(1, posted_by);

            // Execute the query
            ResultSet rs = ps.executeQuery();

            // Use a while loop with ResultSet.next() because there may be multiple rows
            while (rs.next()) {
                // Create a new Message object with the data from the current row
                ret.add(new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }
    
}
