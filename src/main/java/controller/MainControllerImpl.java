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

// todo add validation for input arguments
public class MainControllerImpl implements MainController {

    private AppDbImpl appDb;

    public MainControllerImpl(AppDbImpl appDb) {
        this.appDb = appDb;
    }

    @Override
    public Map<String, User> getAllUsers() throws AppException {
        return appDb.getUsers();
    }

    @Override
    public Map<Integer, Order> getAllOrders() throws AppException{
        return appDb.getOrders();
    }

    @Override
    public User getById(int id) throws UserNotFoundException {

        // todo java 8 at the end apply findFirst

        return appDb.getUsers().values().stream().filter(user -> user.getId() == id).
                findFirst().orElseThrow(() -> new UserNotFoundException("No user Found"));
    }

    @Override
    public Order getOrderById(int id) throws OrderNotFoundException {

        return appDb.getOrders().values().stream().filter(order -> order.getId() == id).
                findFirst().orElseThrow(() -> new OrderNotFoundException("No user Found"));
    }

    @Override
    public Map<Integer, Order> filterByName(String name) throws OrderNotFoundException {
        Map<Integer, Order> result = new HashMap<>();

        appDb.getOrders().values().stream().filter(order -> order.getSenderName().equals(name))
                .forEach(order -> result.put(order.getId(), order));
        return result;
    }

    @Override
    public Map<Integer, Order> filterByCity(String city) throws OrderNotFoundException {
        Map<Integer, Order> result = new HashMap<>();

        appDb.getOrders().values().stream().filter(order -> order.getTargetCity().equals(city))
                .forEach(order -> result.put(order.getId(), order));
        return result;
    }

    @Override
    public Map<Integer, Order> filterByReceiver(String receiverName) throws OrderNotFoundException {
        Map<Integer, Order> result = new HashMap<>();
        appDb.getOrders().values().stream().filter(order -> order.getReceiverName().equals(receiverName))
                .forEach(order -> result.put(order.getId(), order));
        return result;
    }

    @Override
    public Map<Integer, Order> filterByDate(LocalDateTime dateTime) throws OrderNotFoundException {
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
