package Service;

import Model.Account;

public interface AccountService {
    /**
     * Creates a new account with the username and password of the given Account object, 
     * given that the username is not blank, the password is at least 4 characters long, 
     * and an account with that username doesn't already exist.
     * 
     * If these conditions are met, this method will return an Account object representing 
     * the newly created Account. Otherwise, this method will return null.
     * 
     * @param account an Account to register
     * @return the newly registered Account
     */
    Account registerUser(Account account);

    /**
     * Logs in using the username and password of the given Account. Login is successful 
     * if the username and password match one of the accounts in the database. In this case, 
     * that Account will be returned. Otherwise, null will be returned.
     * 
     * @param account an Account object containing a username and password to validate
     * @return the Account with the given Account's username and password, if such an Account exists
     */
    Account login(Account account);
}
