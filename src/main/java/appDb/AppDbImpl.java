package appDb;

import controller.MainControllerImpl;
import exceptions.AppException;
import exceptions.LoginCredentialException;
import model.Order;
import model.User;
import org.apache.log4j.Logger;
import utils.JSONUtils;
import utils.Log4JApp;
import utils.MyAction;
import utils.PathUtils;

import java.io.IOException;
import java.util.*;

public class AppDbImpl implements AppDb {

    private final String USERS_DB_PATH;
    private final String ORDERS_DB_PATH;
    private final static Logger LOGGER = Log4JApp.getLogger(Log4JApp.class);

    private Map<String, User> users;
    private Map<Integer, Order> orders;
    private Map<String, User> accessTokenUserMap;

    public AppDbImpl() {
        this.users = new HashMap<>();
        this.orders = new HashMap<>();
        this.USERS_DB_PATH = PathUtils.getUsersDbPath();
        this.ORDERS_DB_PATH = "order_db.txt";
        accessTokenUserMap = new HashMap<>();
    }

    private Object invokeUserAction(MyAction action) {
        users = getUsersFromDb(USERS_DB_PATH);
        Object ret = action.invoke();
        JSONUtils.saveUsersToDb(USERS_DB_PATH, users);
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
        return getUsersFromDb(USERS_DB_PATH);
    }

    @Override
    public void setUsers(Map<String, User> users) {
        this.users = users;
        LOGGER.info(getClass());
    }

    @Override
    public Map<Integer, Order> getOrders() {
        LOGGER.info(getClass());
        return getOrdersFromDb(ORDERS_DB_PATH);
    }

    public String getordersDbPath() {
        LOGGER.info(getClass());
        return ORDERS_DB_PATH;
    }

    @Override
    public Map<String, User> getUsersFromDb(String userDbPath) {
        try {
            List<User> usersList = JSONUtils.getUsersFromDb(userDbPath);
            usersList.forEach(user -> users.put(user.getEmail(), user));

        } catch (IOException e) {
            LOGGER.error("DB not found");
        }
        LOGGER.info("Method" + getClass());
        return users;
    }

    @Override
    public Map<Integer, Order> getOrdersFromDb(String ordersDbPath) {
        try {
            List<Order> ordersList = JSONUtils.getOrdersFromDb(ordersDbPath);
            ordersList.forEach(order -> orders.put(order.getId(), order));
        } catch (IOException e) {
            LOGGER.error("DB not found");
        }
        LOGGER.info("Method" + getClass());
        return orders;
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
        LOGGER.info(getClass());
        return accessTokenUserMap.containsKey(accessToken);
    }

}
