package DAO;

import java.util.List;

import Model.Message;

/**
 * Interface for accessing the message table.
 */
public interface MessageDAO {
    /**
     * Adds the given Message to the message table.
     * The message_id in the given Message is ignored, and a new message_id 
     * is assigned by the SQL server.
     * 
     * @param message a Message to add to the message table
     * @return the Message that was added to the table, or null if addition was unsuccessful
     */
    Message addMessage(Message message);

    /**
     * Returns a List of all Messages in the message table.
     * 
     * @return a List of all Messages in the message table
     */
    List<Message> getAllMessages();

    /**
     * Returns the Message in the message table with the given message ID,
     * or null if no such Message exists.
     * 
     * @param message_id a numerical message ID
     * @return the Message in the message table with the given ID
     */
    Message getMessageById(int message_id);

    /**
     * Deletes the Message in the message table with the given message ID,
     * if such a Message exists.
     * 
     * @param message_id the ID of the Message to delete
     * @return the Message that was deleted, or null if deletion was unsuccessful
     */
    Message deleteMessageById(int message_id);

    /**
     * Updates the message text of the Message with the given message ID, 
     * if such a Message exists. The Message will have its message text changed to 
     * the given message text.
     * 
     * @param message_id the ID of the Message to update
     * @param message_text the new message text for the Message being updated
     * @return a new Message object containing the updated message data
     */
    Message updateMessageById(int message_id, String message_text);

    /**
     * Returns a List of all Messages posted by the Account with the given ID.
     * 
     * @param posted_by the numerical ID of an Account
     * @return a List of all Messages posted by the Account with the given ID
     */
    List<Message> getMessagesByAccountId(int posted_by);
}
