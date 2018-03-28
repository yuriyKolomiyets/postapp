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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import static spark.Spark.*;

public class SparkServer {

    private final static Logger LOGGER = Log4JApp.getLogger(Log4JApp.class);

    private AppDbImpl appDb;
    private Map<String, User> users;

    // todo  modifier
    private MainController mainController = Factory.create(appDb);

    public SparkServer(int port, String staticFolder) {
        int port1 = port;
        String staticFolder1 = staticFolder;
        this.appDb = new AppDbImpl();
        port(port);

        if(staticFolder != null) {
            externalStaticFileLocation(staticFolder);
        }
    }

    public static void main(String[] args) {

        Properties appProperties = new Properties();
        try (InputStream io = new FileInputStream(args[0])) {
            appProperties.load(io);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        String pathToLog4jProperties = appProperties.getProperty("pathToLog4jProperties");
        String pathToUsersJSON = appProperties.getProperty("pathToUsersJSON");
        String pathToOrdersJSON = appProperties.getProperty("pathToOrdersJSON");
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

    public void initEnpoint() {
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
        boolean addUser = register(newUser.getEmail(), newUser.getPass());

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

    public boolean register(String email, String pass) {
        LOGGER.info(getClass());
        users.put(email, new User(email, pass));
        return true; // maybe we could change return val to User?
    }
}
