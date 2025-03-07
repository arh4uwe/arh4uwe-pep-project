package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.AccountDAOImpl;
import DAO.MessageDAO;
import DAO.MessageDAOImpl;
import Model.Message;

public class MessageServiceImpl implements MessageService {

    private static final int MAX_MESSAGE_LENGTH = 254;

    private static AccountDAO accountDAO = new AccountDAOImpl();
    private static MessageDAO messageDAO = new MessageDAOImpl();

    @Override
    public Message createMessage(Message message) {
        // Check that message text is not blank
        if (message.getMessage_text() == null || message.getMessage_text().equals("")
                || message.getMessage_text().length() > MAX_MESSAGE_LENGTH)
            return null;
        
        /*
         * Check that this message is associated with an account ID 
         * in use by an actual, existing user
         */
        if (accountDAO.getAccountById(message.getPosted_by()) == null)
            return null;
        
        return messageDAO.addMessage(message);
    }

    @Override
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    @Override
    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    @Override
    public Message deleteMessageById(int message_id) {
        return messageDAO.deleteMessageById(message_id);
    }

    @Override
    public Message updateMessageById(int message_id, String message_text) {
        // Check that there exists a message with the given ID
        if (messageDAO.getMessageById(message_id) == null)
            return null;
        
        // Check that the message text is not blank and is under 255 characters
        if (message_text == null || message_text.equals("")
                || message_text.length() > MAX_MESSAGE_LENGTH)
            return null;
        
        return messageDAO.updateMessageById(message_id, message_text);
    }

    @Override
    public List<Message> getAllMessagesFromUserById(int account_id) {
        return messageDAO.getMessagesByAccountId(account_id);
    }
    
}
