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
     */
    public List<Message> getAllMessages()
    {
        return messageDAO.getAllMessages();
    }

    /**
     * Get Message by its ID
     * @param messageID
     */
    public Message getMessageByID(int messageID)
    {
        return messageDAO.getMessageByID(messageID);
    }

    /**
     * Delete a Message by its ID
     * @param message
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

}
