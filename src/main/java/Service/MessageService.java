package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService 
{
    private MessageDAO messageDAO;

    /**
     * no-args constructor for creating a new MessageService with a new MessageDAO
     */
    public MessageService()
    {
        messageDAO = new MessageDAO();
    }

    /**
     * Constructor for an MessageService when an MessageDAO is provided
     * @param messageDAO
     */
    public MessageService(MessageDAO messageDAO)
    {
        this.messageDAO = messageDAO;
    }

    /**
     * Create new message, check if the user who wrote the messages exists
     * @param message
     * @return newly created message
     */
    public Message createMessage(Message message)
    {
        if (!message.getMessage_text().isBlank() && message.getMessage_text().length() <= 255 && message.getPosted_by() > 0)
        {
            return messageDAO.createMessage(message);
        }

        return null;
    }

    /**
     * Get All Messages
     * @return all messages
     */
    public List<Message> getAllMessages()
    {
        return messageDAO.getAllMessages();
    }

    /**
     * Get Message by its ID
     * @param messageID
     * @return message found by its ID
     */
    public Message getMessageByID(int messageID)
    {
        return messageDAO.getMessageByID(messageID);
    }

    /**
     * Delete a Message by its ID
     * @param message
     * @return the message that was deleted
     */
    public Message deleteMessageByID(int messageID)
    {
        Message message = getMessageByID(messageID);

        if (message != null)
        {
            return messageDAO.deleteMessage(message);
        }
        
        return null;
    }

    /**
     * Update message's text identified by its message_id
     * @param messageID
     * @param newMessageText
     * @return updated message
     */
    public Message updateMessageByID(int messageID, String newMessageText)
    {
        Message message = getMessageByID(messageID);

        if (message != null && !newMessageText.isEmpty() && !newMessageText.isBlank() && newMessageText.length() <= 255)
        {
            return messageDAO.updateMessage(message, newMessageText);
        }

        return null;
    }

    /**
     * Get all messages from a particular user
     * @param accountID
     * @return a list of all messages from a particular user
     */
    public List<Message> getAllMessagesFromUser(int accountID)
    {
        return messageDAO.getAllMessagesFromUser(accountID);
    }

}
