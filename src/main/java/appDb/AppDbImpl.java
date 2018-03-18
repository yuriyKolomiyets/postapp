package appDb;

import exceptions.AppException;
import exceptions.LoginCredentialException;
import exceptions.NoAccessException;
import model.Customer;
import model.Order;
import model.User;
import org.apache.log4j.Logger;
import utils.JSONUtils;
import utils.Log4JApp;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// todo figure out how to set info to logging once to all methods

public class AppDbImpl implements AppDb {

    private final String usersDbPath;
    private final String ordersDbPath;
    private final static Logger LOGGER = Log4JApp.getLogger(Log4JApp.class);

    private Map<String, User> users;
    private Map<Integer, Order> orders;
    private Map<String, User> accessTokenUserMap;

    public AppDbImpl() {
        this.users = new HashMap<>();
        this.orders = new HashMap<>();
        this.usersDbPath = "user_db.txt";
        this.ordersDbPath = "order_db.txt";
        accessTokenUserMap = new HashMap<>();
    }

    public User addUser(User user) throws AppException {
        users = getUsersFromDb(usersDbPath);
        users.put(user.getEmail(), user);
        JSONUtils.saveUsersToDb(usersDbPath, users);
        LOGGER.info("Method" + getClass());
        return user;
    }

    public User removeUser(User user) {

        users = getUsersFromDb(usersDbPath);
        users.remove(user.getEmail());
        JSONUtils.saveUsersToDb(usersDbPath, users);
        LOGGER.info("Method" + getClass());
        return user;
    }

    public Map<String, User> getUsers() {
        LOGGER.info("Method" + getClass());
        return getUsersFromDb(usersDbPath);
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
        LOGGER.info("Method" + getClass());
    }

    public Map<String, User> getUsersFromDb(String userDbPath) {
        try {
            List<User> usersList = JSONUtils.getUsersFromDb(userDbPath);
            usersList.forEach(user -> users.put(user.getEmail(), user));

        } catch (IOException e) {
            LOGGER.error("This is Error message");
        }
        LOGGER.info("Method" + getClass());
        return users;
    }


    public Order addOrder(Order order, String accessToken) throws AppException {

        // todo this is business logic
        if (!hasToken(accessToken)) {
            LOGGER.error("no access, login first");
            throw new NoAccessException("no access, login first");
        }

        orders = getOrdersFromDb(ordersDbPath);
        orders.put(order.getId(), order);
        JSONUtils.saveOrdersToDb(ordersDbPath, orders);
        LOGGER.info("Method" + getClass());

        return order;
    }

    public Map<Integer, Order> getOrdersFromDb(String ordersDbPath) {
        try {
            List<Order> ordersList = JSONUtils.getOrdersFromDb(ordersDbPath);
            ordersList.forEach(order -> orders.put(order.getId(), order));
        } catch (IOException e) {
            LOGGER.error("This is Error message");
        }
        LOGGER.info("Method" + getClass());
        return orders;
    }

    public Order removeOrder(Order order, String accessToken) throws AppException {
        orders = getOrdersFromDb(ordersDbPath);
        if (!hasToken(accessToken)) {
            LOGGER.error("no access, login first");
            throw new AppException("no access, login first");
        }
        orders.remove(order.getId());
        JSONUtils.saveOrdersToDb(ordersDbPath, orders);
        LOGGER.info("Method" + getClass());
        return order;
    }

    public Map<Integer, Order> getOrders() {
        return getOrdersFromDb(ordersDbPath);
    }


    public String createAccessToken(User user) throws LoginCredentialException {
        User found =
                users.values()
                        .stream().filter(u -> u !=null)
                        .filter(u -> user.getEmail().equals(u.getEmail()))
                        .filter(u -> user.getPass().equals(u.getPass()))
                        .findFirst().orElse(null);

        if (found == null) {
            LOGGER.error("invalid login or pass");
            throw new LoginCredentialException("invalid login or pass");
        } else {
            String accessKey = UUID.randomUUID().toString();
            accessTokenUserMap.put(accessKey, found);
            return accessKey;
        }
    }

    public boolean hasToken(String accessToken) {
        return accessTokenUserMap.containsKey(accessToken);
    }

    public boolean register(String email, String pass) {
        users = getUsersFromDb(usersDbPath);
//        users.put(email, new Customer(email, pass));
        users.put(email, new User(email, pass));
        JSONUtils.saveUsersToDb("TeamOne/user_db.txt", users);
        LOGGER.info("Method" + getClass());

        return true;
    }


}
