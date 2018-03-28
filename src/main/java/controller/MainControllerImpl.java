package controller;

import appDb.AppDbImpl;
import exceptions.AppException;
import exceptions.NoAccessException;
import exceptions.OrderNotFoundException;
import exceptions.UserNotFoundException;
import model.Order;
import model.OrderStatus;
import model.User;
import org.apache.log4j.Logger;
import utils.JSONUtils;
import utils.Log4JApp;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MainControllerImpl implements MainController {

    private AppDbImpl appDb;
    private final static Logger LOGGER = Log4JApp.getLogger(Log4JApp.class);
    private Map<Integer, Order> orders;

    public MainControllerImpl(AppDbImpl appDb) {
        this.appDb = appDb;
    }

    @Override
    public Map<String, User> getAllUsers() throws AppException {
        if (appDb == null) throw new AppException("DB is empty");
        else return appDb.getUsers();
    }

    @Override
    public Map<Integer, Order> getAllOrders() throws AppException{
        if (appDb == null) throw new AppException("DB is empty");
        else return appDb.getOrders();
    }

    @Override
    public User getById(int id) throws AppException {
        if (appDb == null) throw new AppException("DB is empty");
        return appDb.getUsers().values().stream().filter(user -> user.getId() == id).
                findFirst().orElseThrow(() -> new UserNotFoundException("No user Found"));
    }

    @Override
    public Order getOrderById(int id) throws AppException {
        if (appDb == null) throw new AppException("DB is empty");
        return appDb.getOrders().values().stream().filter(order -> order.getId() == id).
                findFirst().orElseThrow(() -> new OrderNotFoundException("No order Found"));
    }

    @Override
    public Map<Integer, Order> filterByName(String name) throws AppException {
        if (appDb == null) throw new AppException("DB is empty");

        return appDb.getOrders().values().stream().filter(order -> order.getSenderName().equals(name))
                .collect(Collectors.toMap(Order::getId, order -> order));
    }

    @Override
    public Map<Integer, Order> filterByCity(String city) throws AppException {
        if (appDb == null) throw new AppException("DB is empty");

        Map<Integer, Order> map = new HashMap<>();
        for (Order order : appDb.getOrders().values()) {
            if (order.getTargetCity().equals(city)) {
                if (map.put(order.getId(), order) != null) {
                    throw new IllegalStateException("Duplicate key");
                }
                map.put(order.getId(), order);
            }
        }
        return map;
    }

    @Override
    public Map<Integer, Order> filterByReceiver(String receiverName)throws AppException {
        if (appDb == null) throw new AppException("DB is empty");

        return appDb.getOrders().values().stream().filter(order -> order.getReceiverName().equals(receiverName))
                .collect(Collectors.toMap(Order::getId, order -> order));
    }

    @Override
    public Map<Integer, Order> filterByDate(LocalDateTime dateTime) throws AppException {
        if (appDb == null) throw new AppException("DB is empty");

        return appDb.getOrders().values().stream().filter(order -> order.getSendDate().equals(dateTime))
                .collect(Collectors.toMap(Order::getId, order -> order));
    }

    public String changeOrderStatus(Order order, OrderStatus orderStatus, String accessToken) throws AppException, IOException {
        if (!appDb.hasToken(accessToken)) {
            throw new AppException("no access, login first");
        }
        order.setOrderStatus(orderStatus);
        return String.valueOf(orderStatus);
    }

    public Order addOrder(Order order, String accessToken) throws AppException {
        if (!appDb.hasToken(accessToken)) {
            LOGGER.error("no access, login first");
            throw new NoAccessException("no access, login first");
        }

        orders = appDb.getOrdersFromDb(appDb.getordersDbPath());
        orders.put(order.getId(), order);
        JSONUtils.saveOrdersToDb(appDb.getordersDbPath(), orders);
        LOGGER.info("Method" + getClass());

        return order;
    }

    public Order removeOrder(Order order, String accessToken) throws AppException {
        orders = appDb.getOrdersFromDb(appDb.getordersDbPath() );
        if (!appDb.hasToken(accessToken)) {
            LOGGER.error("no access, login first");
            throw new AppException("no access, login first");
        }
        orders.remove(order.getId());
        JSONUtils.saveOrdersToDb(appDb.getordersDbPath(), orders);
        LOGGER.info("Method" + getClass());
        return order;
    }



}
