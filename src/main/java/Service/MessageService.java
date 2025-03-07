package Service;

import java.util.List;

import Model.Message;

public interface MessageService {
    /**
     * Persists the given Message in the database, but only if the message text isn't blank, 
     * the message text is under 255 characters, and the message is associated with a real, 
     * existing user. If all these conditions are met, a Message object representing the new 
     * message in the database is returned. Otherwise, this method returns null.
     * 
     * @param message a Message to persist in the database
     * @return the newly added Message, or null if no message was added
     */
    Message createMessage(Message message);

    /**
     * Returns a List of all Messages in the database.
     * 
     * @return a List of all Messages in the database
     */
    List<Message> getAllMessages();

    /**
     * Returns the Message with the given ID, or null if no such message exists.
     * 
     * @param message_id a numerical message ID
     * @return the Message with the given ID
     */
    Message getMessageById(int message_id);

    /**
     * Deletes the Message with the given ID. If deletion is successful, this method 
     * returns the now-deleted Message. If deletion fails, or if no Message with the given 
     * ID exists, this method returns null.
     * 
     * @param message_id a numerical message ID
     * @return the now-deleted Message with the given ID
     */
    Message deleteMessageById(int message_id);

    /**
     * Updates the message text of the message in the database with the given ID, 
     * given that such a message exists and the given new message text is non-empty and 
     * under 255 characters. If the update is successful, this method returns the 
     * newly updated Message. Otherwise, this method returns null.
     * 
     * @param message_id a numerical message ID
     * @param message_text the new message text to replace the old text in the message
     * @return a Message object representing the newly updated message
     */
    Message updateMessageById(int message_id, String message_text);

    /**
     * Returns a List of all Messages posted by the user with the given account ID.
     * 
     * @param account_id a numerical account ID
     * @return a List of all Messages posted by the user with the given ID
     */
    List<Message> getAllMessagesFromUserById(int account_id);
}
