package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Service.AccountService;
import Service.AccountServiceImpl;
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
        }
    }

}