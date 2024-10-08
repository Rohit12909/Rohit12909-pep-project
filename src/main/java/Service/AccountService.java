package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;


public class AccountService
{
    private AccountDAO accountDAO;

    /**
     * no-args constructor for creating a new AccountService with a new AccountDAO
     */
    public AccountService()
    {
        accountDAO = new AccountDAO();
    }

    /**
     * Constructor for an AccountService when an AccountDAO is provided
     */
    public AccountService(AccountDAO accountDAO)
    {
        this.accountDAO = accountDAO;
    }

    /**
     * Create new account, check if it already exists in the database
     * @param account
     * @return newly created account
     */
    public Account createAccount(Account account)
    {
        
        if ((!account.getUsername().isBlank()) && (account.getPassword().length() >= 4) && !AccountExists(account))
        {
            return accountDAO.createAccount(account);
        }      

        return null;
    }

    /**
     * Login to an already existing account
     */
    public Account loginAccount(Account account)
    {
        if (AccountExists(account))
        {
            return accountDAO.loginAccount(account);
        }
        
        return null;
    }

    private boolean AccountExists(Account account)
    {
        List<Account> allAccounts = accountDAO.getAllAccounts();
        boolean exists = false;

        for (Account a : allAccounts)
        {
            if (account.equals(a))
            {
                exists = true;
                break;
            }
        }

        return exists;
    }
}
