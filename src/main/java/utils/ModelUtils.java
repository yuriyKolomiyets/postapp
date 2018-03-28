package utils;

import model.Order;
import model.User;

import java.io.IOException;
import java.util.List;

public class ModelUtils {

    private static int id;
    private final static String USERS_DB_PATH = PathUtils.getUsersDbPath();
    private final static String ORDERS_DB_PATH = PathUtils.getOrdersDbPath();

    private static int getMaxUserIdInDb() {
        List<User> users = null;
        try {
            users = JSONUtils.getUsersFromDb(USERS_DB_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users != null ? users.stream()
                .max((user1, user2) -> (Integer.compare(user1.getId(), user2.getId())))
                .get().getId() : 0;
    }

    private static int getMaxOrderIdInDb() {
        List<Order> orders = null;
        try {
            orders = JSONUtils.getOrdersFromDb(ORDERS_DB_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders != null ? orders.stream()
                .max((order1, order2) -> (Integer.compare(order1.getId(), order2.getId())))
                .get().getId() : 0;
    }

    public static int genUserId() {
        int maxIdInDb = getMaxUserIdInDb();
        return id < maxIdInDb ? (id = maxIdInDb + 1) : id++;
    }

    public static int genOrderId() {
        int maxIdInDb = getMaxOrderIdInDb();
        return id < maxIdInDb ? (id = maxIdInDb + 1) : id++;
    }
}