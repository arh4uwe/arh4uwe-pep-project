package Service;

import DAO.AccountDAO;
import DAO.AccountDAOImpl;
import Model.Account;

public class AccountServiceImpl implements AccountService {
    private static AccountDAO dao = new AccountDAOImpl();

    @Override
    public Account registerUser(Account account) {
        // Check that the account username exists and is not blank
        if (account.getUsername() == null || account.getUsername().equals(""))
            return null;
        
        // Check that the password exists and is at least 4 characters long
        if (account.getPassword() == null || account.getPassword().length() < 4)
            return null;
        
        // Check that there is no already existing account with the given username
        if (dao.getAccountByUsername(account.getUsername()) != null)
            return null;
        
        // Create an account and return the newly created Account
        return dao.addAccount(account);
    }

    @Override
    public Account login(Account account) {
        /*
         * Return the account in the database with the given Account object's username and password, 
         * or null if no such account exists.
         */
        return dao.getAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
    }
    
}
