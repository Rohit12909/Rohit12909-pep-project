package Controller;

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
public class SocialMediaController {
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
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::postAccountHandler);
        //app.get("/login", this::exampleHandler);
        //app.get("/messages", this::exampleHandler);
        //app.get("/messages/{message_id}", this::exampleHandler);
        //app.get("/accounts/{account_id}/messages", this::exampleHandler);


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


}