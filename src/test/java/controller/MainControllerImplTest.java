package controller;

import appDb.AppDbImpl;
import com.google.gson.Gson;
import exceptions.AppException;
import model.Order;
import model.User;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static utils.TestUtils.restoreOrderDb;
import static utils.TestUtils.restoreUserDb;


public class MainControllerImplTest {

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

        appDb = null;
    }


    @Test
    public void getAllUsers() throws AppException, IOException {
        assertEquals(2, mainController.getAllUsers().size());

    }

    @Test
    public void getAllOrders() throws AppException, IOException {
        assertEquals(4, mainController.getAllOrders().size());
    }

    @Test
    public void addOrder() throws AppException {

        int testOrderId = testOrder.getId();
        assertTrue(appDb.getOrders().containsKey(testOrderId));
    }

    @Test
    public void removeOrder() throws AppException {
        String token = appDb.createAccessToken(testUser);
        mainController.removeOrder(testOrder, token);
        assertEquals(3,appDb.getOrders().size());
    }

    @Test
    public void getById() throws AppException, IOException {
        assertEquals(testUser, mainController.getById(testUser.getId()));
    }

    @Test
    public void getOrderbyId() throws AppException, IOException {
        assertEquals(testOrder.getId(), mainController.getOrderById(testOrder.getId()).getId());

    }

    @Test
    public void filterByName() throws AppException, IOException {
        String token = appDb.createAccessToken(testUser);
        Order testOrder2 = new Order("Oleg", "Andrey", "Kyiv");
        mainController.addOrder(new Order("Andrey", "Andrey", "Kyiv"), token);
        mainController.addOrder(new Order("Oleg", "Andrey", "Kyiv"), token);
        mainController.addOrder(testOrder2, token);

        assertThat(mainController.filterByName("Andrey").size(), CoreMatchers.equalTo(1));
        assertThat(mainController.filterByName("Oleg").size(), CoreMatchers.equalTo(4));

    }

    @Test
    public void filterByCity() throws AppException, IOException {
        String token = appDb.createAccessToken(testUser);
        Order testOrder2 = new Order("Oleg", "Andrey", "Kyiv");
        mainController.addOrder(new Order("Andrey", "Andrey", "Lviv"), token);
        mainController.addOrder(new Order("Oleg", "Andrey", "Kyiv"), token);
        mainController.addOrder(testOrder2, token);

        assertThat(mainController.filterByCity("Kyiv").size(), CoreMatchers.equalTo(6));
        assertThat(mainController.filterByCity("Lviv").size(), CoreMatchers.equalTo(1));

    }

    @Test
    public void filterByReciever() throws AppException, IOException {
        String token = appDb.createAccessToken(testUser);
        Order testOrder2 = new Order("Oleg", "Andrey", "Kyiv");
        mainController.addOrder(new Order("Andrey", "Andrey", "Lviv"), token);
        mainController.addOrder(new Order("Oleg", "Andrey", "Kyiv"), token);
        mainController.addOrder(testOrder2, token);

        assertThat(mainController.filterByReceiver("Andrey").size(), CoreMatchers.equalTo(7));
        assertThat(mainController.filterByReceiver("Lviv").size(), CoreMatchers.equalTo(0));
    }

    @Test
    public void filterByDate() throws AppException, IOException {
        String token = appDb.createAccessToken(testUser);
        Order testOrder2 = new Order("Oleg", "Andrey", "Copengagen");
        mainController.addOrder(new Order("Andrey", "Andrey", "Lviv"), token);
        mainController.addOrder(new Order("Oleg", "Andrey", "Kyiv"), token);
        testOrder2.setSendDate(LocalDateTime.now());
        mainController.addOrder(testOrder2, token);

        assertThat(mainController.filterByDate(LocalDateTime.now()).size(), CoreMatchers.equalTo(0));

    }


}