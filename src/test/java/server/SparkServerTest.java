package server;

import exceptions.AppException;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static spark.Spark.get;

// in new test class run spark -> send requests -> check results -> down spark

public class SparkServerTest {

    String SERVER_PORT = "5000";
    public SparkServer server = new SparkServer(Integer.parseInt(SERVER_PORT),
            SparkServer.class.getResource("/view").getFile());

     @Before
     public void before() throws AppException {
        server.initEnpoint();
     }

    @After
    public void tearDown() throws Exception {
     server.stopServer();
    }


    @Test
    public void main() {

        get("/index.html", (request, response) -> "Server is up");
        get("/home", (request, response) -> "Server is up");
    }
}
