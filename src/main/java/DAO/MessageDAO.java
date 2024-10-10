package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO 
{
    /**
     * Create New Message
     * @param message
     * @return Newly created message
     */
    public Message createMessage(Message message)
    {
        Connection connection = ConnectionUtil.getConnection();

        try
        {
            String sql = "insert into Message (posted_by, message_text, time_posted_epoch) values (?, ?, ?);";

            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            while (rs.next())
            {
                int generated_message_id = (int) rs.getLong("message_id");
                return new Message(generated_message_id,
                                   message.getPosted_by(),
                                   message.getMessage_text(), 
                                   message.getTime_posted_epoch());
            }

        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Get All Messages
     * @return A list of all messages
     */
    public List<Message> getAllMessages()
    {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try
        {
            String sql = "select * from Message;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                Message message = new Message(rs.getInt("message_id"),
                                              rs.getInt("posted_by"),
                                              rs.getString("message_text"),
                                              rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return messages;
    }

    /**
     * Retrieve a message using its ID
     * @param messageID id used to identify the message to be returned
     * @return Message that was found by its ID
     */
    public Message getMessageByID(int messageID)
    {
        Connection connection = ConnectionUtil.getConnection();

        try
        {
            String sql = "select * from Message where message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, messageID);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                return new Message(rs.getInt("message_id"), 
                                   rs.getInt("posted_by"),
                                   rs.getString("message_text"), 
                                   rs.getLong("time_posted_epoch"));
            }

        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Delete a message identified by a message ID
     * @param message
     * @return The message that was deleted
     */
    public Message deleteMessage(Message message)
    {
        Connection connection = ConnectionUtil.getConnection();

        try
        {
            String sql = "delete from Message where message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, message.getMessage_id());
            
            ps.executeUpdate();

            return message;

        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Update a message's text identified by a message id
     * @param message Object getting updated
     * @param newMessage message_text that will replace the original message
     * @return updated message
     */
    public Message updateMessage(Message message, String newMessage)
    {
        Connection connection = ConnectionUtil.getConnection();

        try
        {
            String sql = "update Message set message_text = ? where message_id = ?;";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, newMessage);
            ps.setInt(2, message.getMessage_id());

            ps.executeUpdate();

            String getUpdatedMessage = "select * from Message where message_id = ?;";

            ps = connection.prepareStatement(getUpdatedMessage);

            ps.setInt(1, message.getMessage_id());

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                message.setMessage_id(rs.getInt("message_id"));
                message.setPosted_by(rs.getInt("posted_by"));
                message.setMessage_text(rs.getString("message_text"));
                message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));

                return message;
            }


        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Retrieve all Messages from a particular user
     * @param accountID
     * @return a list of all messages from a particular user
     */
    public List<Message> getAllMessagesFromUser(int accountID)
    {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> allMessagesByUser = new ArrayList<>();

        try
        {
            String sql = "select * from Message where posted_by = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, accountID);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                Message message = new Message(rs.getInt("message_id"), 
                                              rs.getInt("posted_by"), 
                                              rs.getString("message_text"), 
                                              rs.getLong("time_posted_epoch"));
                
                allMessagesByUser.add(message);
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return allMessagesByUser;
    }
}
