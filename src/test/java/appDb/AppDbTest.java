package appDb;

import controller.MainController;
import controller.MainControllerImpl;
import exceptions.AppException;
import exceptions.UserNotFoundException;
import model.Order;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utils.TestUtils.restoreOrderDb;
import static utils.TestUtils.restoreUserDb;

// todo use test db file
public class AppDbTest {

    AppDbImpl appDb = new AppDbImpl();
    MainController mainController = new MainControllerImpl(appDb);
    User testUser = new User("test3@gmail.com", "123456");
    Order testOrder = new Order("Oleg", "Andrey", "Kyiv");

    @Before
    public void before() throws AppException {

        appDb.addUser(testUser);
        String token = appDb.createAccessToken(testUser);
        mainController.addOrder(testOrder, token);
    }

   @After
    public void after(){
      restoreUserDb();
      restoreOrderDb();

    }

    @Test
    public void addUser() throws AppException {
        User testUser1 = new User("test4@gmail.com", "123456");
        appDb.addUser(testUser1);
        User testUser2 = new User("test5@gmail.com", "123456");
        appDb.addUser(testUser2);
        assertTrue(appDb.getUsers().containsKey("test4@gmail.com"));
    }

    @Test
    public void removeUser() throws UserNotFoundException {
        appDb.removeUser(testUser);
        assertEquals(1, appDb.getUsers().size());
    }


    @Test
    public void getUsers() throws AppException {
        appDb.addUser(testUser);
        assertTrue(appDb.getUsers().containsKey("test3@gmail.com"));
    }

}