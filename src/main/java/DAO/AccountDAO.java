package DAO;

import Model.Account;

/**
 * Interface for accessing the account table.
 */
public interface AccountDAO {
    /**
     * Adds the given Account to the account table.
     * The account_id in the given Account is ignored, and a new account_id 
     * is assigned by the SQL server.
     * 
     * @param account an Account to add to the account table
     * @return the Account that was added to the table, or null if addition was unsuccessful
     */
    Account addAccount(Account account);

    /**
     * Returns the Account in the account table with the given ID, 
     * or null if there is no such Account.
     * 
     * @param account_id a numerical account ID
     * @return the Account in the account table with the given ID
     */
    Account getAccountById(int account_id);

    /**
     * Returns the Account in the account table with the given username and password,
     * or null if there is no such Account.
     * 
     * @param username the username to search for
     * @param password the password to search for
     * @return the Account in the account table with the given username and password
     */
    Account getAccountByUsernameAndPassword(String username, String password);
}
