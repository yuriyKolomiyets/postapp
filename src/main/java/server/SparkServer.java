package server;

import appDb.AppDbImpl;
import controller.MainController;
import exceptions.LoginCredentialException;
import model.User;
import org.apache.log4j.Logger;
import spark.Request;
import spark.Response;
import utils.Factory;
import utils.JSONUtils;
import utils.Log4JApp;

import static spark.Spark.*;

public class SparkServer {

    private final int port;
    private final String staticFolder;
    private final static Logger LOGGER = Log4JApp.getLogger(Log4JApp.class);


    private AppDbImpl appDb;

    // todo  modifier
    private MainController mainController = Factory.create(appDb);

    public SparkServer(int port, String staticFolder) {
        this.port = port;
        this.staticFolder = staticFolder;
        this.appDb = new AppDbImpl();
        port(port);

        if(staticFolder != null) {
            externalStaticFileLocation(staticFolder);
        }
    }

    public static void main(String[] args) {

        String SERVER_PORT = System.getenv("PORT");

        if(SERVER_PORT == null){
            SERVER_PORT = "5000";
        }

        SparkServer server = new SparkServer(Integer.parseInt(SERVER_PORT),
                SparkServer.class.getResource("/view").getFile());
        server.initEnpoint();
    }

    public void stopServer(){
        stop();
    }

    private void initEnpoint() {
        post("/login", this::login);
        post("/register", this::register);
    }

    private Object login(Request request, Response response) {
        User loginUser = JSONUtils.fromJson(request.body(), User.class);

        try {
            String key = appDb.createAccessToken(loginUser);
        } catch (LoginCredentialException e) {
            LOGGER.error("Authorisation failed");
        }
        // gson.toJson(new Message("succes or token"))
        // todo return object decorated in json
        return response;
    }

    private Object register(Request request, Response response) {
        // todo create Gson only one time and keep as a singleton

        String jsonRequest = request.body();
        User newUser = JSONUtils.fromJson(jsonRequest, User.class);
        boolean addUser = appDb.register(newUser.getEmail(), newUser.getPass());

        if(addUser) {
            response.body("User successfully registered");
            LOGGER.info("User successfully registered");
        } else {
            response.body("User not added to db due to an error");
            LOGGER.error("User not added to db due to an error");
        }

        // todo return message after register logic
        return response;
    }
}
