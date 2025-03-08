package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.AccountServiceImpl;
import Service.MessageService;
import Service.MessageServiceImpl;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private static ObjectMapper om = new ObjectMapper();
    private static AccountService accountService = new AccountServiceImpl();
    private static MessageService messageService = new MessageServiceImpl();

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registrationHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountIdHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registrationHandler(Context context) {
        String json = context.body();

        try {
            Account accountToCreate = om.readValue(json, Account.class);
            Account createdAccount = accountService.registerUser(accountToCreate);

            /*
             * If createdAccount is null, registration failed.
             * Update status to 400 (Client Error).
             */
            if (createdAccount == null)
                context.status(400);
            /*
             * Otherwise, registration succeeded.
             * The response body will contain the created account in JSON format.
             */
            else
                context.json(createdAccount);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            context.status(500);
        }
    }

    private void loginHandler(Context context) {
        String json = context.body();

        try {
            Account accountToAuthenticate = om.readValue(json, Account.class);
            Account authenticatedAccount = accountService.login(accountToAuthenticate);

            /*
             * If authenticatedAccount is null, login failed.
             * Update status to 401 (Unauthorized).
             */
            if (authenticatedAccount == null)
                context.status(401);
            /*
             * Otherwise, registration succeeded.
             * The response body will contain the authenticated account in JSON format.
             */
            else
                context.json(authenticatedAccount);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            context.status(500);
        }
    }

    private void createMessageHandler(Context context) {
        String json = context.body();

        try {
            Message messageToCreate = om.readValue(json, Message.class);
            Message createdMessage = messageService.createMessage(messageToCreate);

            /*
             * If createdMessage is null, message creation failed.
             * Update status to 400 (Client Error).
             */
            if (createdMessage == null)
                context.status(400);
            /*
             * Otherwise, message creation succeeded.
             * The response body will contain the newly created message in JSON format.
             */
            else
                context.json(createdMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            context.status(500);
        }
    }

    private void getAllMessagesHandler(Context context) {
        context.json(messageService.getAllMessages());
    }

    private void getMessageByIdHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        /*
         * The message can only be returned as JSON if it isn't null.
         * Otherwise, do nothing, and the response body will be empty.
         */
        if (message != null)
            context.json(message);
    }

    private void deleteMessageByIdHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.deleteMessageById(messageId);
        /*
         * The message can only be returned as JSON if it isn't null.
         * Otherwise, do nothing, and the response body will be empty.
         */
        if (message != null)
            context.json(message);
    }

    private void updateMessageByIdHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        String json = context.body();

        try {
            Message messageToUpdate = om.readValue(json, Message.class);
            String messageText = messageToUpdate.getMessage_text();

            Message updatedMessage = messageService.updateMessageById(messageId, messageText);

            /*
             * If updatedMessage is null, message update failed.
             * Update status to 400 (Client Error).
             */
            if (updatedMessage == null)
                context.status(400);
            /*
             * Otherwise, message update succeeded.
             * The response body will contain the newly updated Message object in JSON format.
             */
            else
                context.json(updatedMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            context.status(500);
        }
    }

    private void getMessagesByAccountIdHandler(Context context) {
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        context.json(messageService.getAllMessagesFromUserById(accountId));
    }

}