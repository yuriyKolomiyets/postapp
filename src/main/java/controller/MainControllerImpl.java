package controller;

import appDb.AppDbImpl;
import exceptions.AppException;
import exceptions.OrderNotFoundException;
import exceptions.UserNotFoundException;
import model.Order;
import model.OrderStatus;
import model.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MainControllerImpl implements MainController {

    private AppDbImpl appDb;

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

        Map<Integer, Order> result = new HashMap<>();

        appDb.getOrders().values().stream().filter(order -> order.getSenderName().equals(name))
                .forEach(order -> result.put(order.getId(), order));
        return result;
    }

    @Override
    public Map<Integer, Order> filterByCity(String city) throws AppException {
        if (appDb == null) throw new AppException("DB is empty");

        Map<Integer, Order> result = new HashMap<>();

        appDb.getOrders().values().stream().filter(order -> order.getTargetCity().equals(city))
                .forEach(order -> result.put(order.getId(), order));
        return result;
    }

    @Override
    public Map<Integer, Order> filterByReceiver(String receiverName)throws AppException {
        if (appDb == null) throw new AppException("DB is empty");

        Map<Integer, Order> result = new HashMap<>();

        appDb.getOrders().values().stream().filter(order -> order.getReceiverName().equals(receiverName))
                .forEach(order -> result.put(order.getId(), order));
        return result;
    }

    @Override
    public Map<Integer, Order> filterByDate(LocalDateTime dateTime) throws AppException {
        if (appDb == null) throw new AppException("DB is empty");

        Map<Integer, Order> result = new HashMap<>();

        appDb.getOrders().values().stream().filter(order -> order.getSendDate().equals(dateTime))
                .forEach(order -> result.put(order.getId(), order));
        return result;
    }

    public String changeOrderStatus(Order order, OrderStatus orderStatus, String accessToken) throws AppException, IOException {
        if (!appDb.hasToken(accessToken)) {
            throw new AppException("no access, login first");
        }
        order.setOrderStatus(orderStatus);
        return String.valueOf(orderStatus);
    }


}
