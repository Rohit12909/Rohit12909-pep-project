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


    public Account createAccount(Account account)
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
 
        if ((!account.getUsername().isBlank()) && (account.getPassword().length() >= 4) && !exists)
        {
            return accountDAO.createAccount(account);
        }      

        return null;
    }
    
}
