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
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }

        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Get All Messages
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
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
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
     * @return
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
}
