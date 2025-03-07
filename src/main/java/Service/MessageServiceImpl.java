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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllMessages'");
    }

    @Override
    public Message getMessageById(int message_id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMessageById'");
    }

    @Override
    public Message deleteMessageById(int message_id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteMessageById'");
    }

    @Override
    public Message updateMessageById(int message_id, String message_text) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateMessageById'");
    }

    @Override
    public List<Message> getAllMessagesFromUserById(int account_id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllMessagesFromUserById'");
    }
    
}
