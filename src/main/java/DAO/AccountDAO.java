package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class AccountDAO
{

    /**
     * Method to get all accounts in the table to check for duplicates in the table.
     */
    public List<Account> getAllAccounts()
    {
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();

        try
        {
            String sql = "select * from Account;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                Account account = new Account(rs.getString("username"), rs.getString("password"));
                accounts.add(account);
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    /**
     * Create New Account Object and insert it into the Account database
     * @param acc
     * @return New Account Object
     */
    public Account createAccount(Account acc)
    {
        Connection connection = ConnectionUtil.getConnection();

        try
        {
            String sql = "insert into Account (username, password) values (?, ?);";

            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, acc.getUsername());
            ps.setString(2, acc.getPassword());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next())
            {
                int generated_account_id = (int) rs.getLong("account_id");
                return new Account(generated_account_id, acc.getUsername(), acc.getPassword());
            }

        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*
     * 
     */
    public Account loginAccount(Account account)
    {
        Connection connection = ConnectionUtil.getConnection();

        try
        {
            String sql = "select * from Account where username = ? and password = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }

        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
