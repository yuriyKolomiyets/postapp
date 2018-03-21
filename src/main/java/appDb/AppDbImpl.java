package appDb;

import exceptions.AppException;
import exceptions.LoginCredentialException;
import exceptions.NoAccessException;
import model.Order;
import model.User;
import org.apache.log4j.Logger;
import utils.JSONUtils;
import utils.Log4JApp;
import utils.MyAction;

import java.io.IOException;
import java.util.*;

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

    private Object invokeUserAction(MyAction action) {
        users = getUsersFromDb(usersDbPath);
        Object ret = action.invoke();
        JSONUtils.saveUsersToDb(usersDbPath, users);
        LOGGER.info(getClass());
        return ret;
    }

    @Override
    public User addUser(User user) throws AppException {
        return (User) invokeUserAction(() -> users.put(user.getEmail(), user));
    }

    @Override
    public User removeUser(User user) {
        return (User) invokeUserAction(() -> users.remove(user.getEmail()));
    }

    @Override
    public Map<String, User> getUsers() {
        LOGGER.info(getClass());
        return getUsersFromDb(usersDbPath);
    }

    @Override
    public void setUsers(Map<String, User> users) {
        this.users = users;
        LOGGER.info(getClass());
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
    public Map<Integer, Order> getOrders() {
        return getOrdersFromDb(ordersDbPath);
    }

    @Override
    public String createAccessToken(User user) throws LoginCredentialException {
        User found =
                users.values()
                        .stream().filter(Objects::nonNull)
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

    @Override
    public boolean hasToken(String accessToken) {
        return accessTokenUserMap.containsKey(accessToken);
    }

    public boolean register(String email, String pass) {
        return (Boolean) invokeUserAction(() -> users.put(email, new User(email, pass)));
    }

}
