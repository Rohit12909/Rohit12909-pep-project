package Controller;

import java.util.List;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController 
{
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController()
    {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() 
    {
        Javalin app = Javalin.create();

        // Post handlers
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);

        // Get Handlers
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromUserHandler);

        // Delete Handlers
        app.delete("/messages/{message_id}", this::deleteMessageByIDHandler);

        // Patch Handlers
        app.patch("/messages/{message_id}", this::patchMessageByIDHandler);


        return app;
    }

    /**
     * Handler to post a new account
     * @param ctx
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object
     */
    private void postAccountHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.createAccount(account);

        if (addedAccount != null)
        {
            ctx.json(mapper.writeValueAsString(addedAccount));
        }
        else
        {
            ctx.status(400);
        }
    }

    /**
     * Handler to post a login
     * @param ctx
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object
     */
    private void postLoginHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loggedInAccount = accountService.loginAccount(account);

        if (loggedInAccount != null)
        {
            ctx.json(mapper.writeValueAsString(loggedInAccount));
        }
        else
        {
            ctx.status(401);
        }
    }

    /**
     * Handler to post a new message
     * @param ctx
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object
     */
    private void postMessageHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message createdMessage = messageService.createMessage(message);

        if (createdMessage != null)
        {
            ctx.json(mapper.writeValueAsString(createdMessage));
        }
        else
        {
            ctx.status(400);
        }
    }

    /**
     * Handler to retrieve all messages
     * @param ctx
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object
     */
    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException
    {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    /**
     * Handler to retrieve message by its ID
     * @param ctx
     * @throws JsonProcessingException
     */
    private void getMessageByIDHandler(Context ctx) throws JsonProcessingException
    {
        Message message = messageService.getMessageByID(Integer.parseInt(ctx.pathParam("message_id")));

        if (message != null)
        {
            ctx.json(message);
        }
        else
        {
            ctx.json("");
        }   
    }

    /**
     * Handler to delete a message by its ID
     * @param ctx
     * @throws JsonProcessingException
     */
    private void deleteMessageByIDHandler(Context ctx) throws JsonProcessingException
    {
        Message deletedMessage = messageService.deleteMessageByID(Integer.parseInt(ctx.pathParam("message_id")));

        if (deletedMessage != null)
        {
            ctx.json(deletedMessage);
        }
        else
        {
            ctx.json("");
        } 
    }

    /**
     * Handler to update message found by its message_id
     * @param ctx
     * @throws JsonProcessingException
     */
    private void patchMessageByIDHandler(Context ctx) throws JsonProcessingException
    {
        String newMessageText = ctx.body().toString();
        newMessageText = newMessageText.substring(18);
        newMessageText = newMessageText.substring(0, newMessageText.length() - 3);
        
        Message message = messageService.updateMessageByID(Integer.parseInt(ctx.pathParam("message_id")), newMessageText);

        if (message != null)
        {
            ctx.json(message);
        }
        else
        {
            ctx.status(400);
        } 
    }

    /**
     * Handler to get all messages from a particular user
     * @param ctx
     * @throws JsonProcessingException
     */
    private void getAllMessagesFromUserHandler(Context ctx) throws JsonProcessingException
    {
        int accountID = Integer.parseInt(ctx.pathParam("account_id"));

        List<Message> allMessagesFromUser = messageService.getAllMessagesFromUser(accountID);

        ctx.json(allMessagesFromUser);
    }



}