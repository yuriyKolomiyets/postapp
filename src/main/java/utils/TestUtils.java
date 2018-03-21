package utils;

import model.Order;
import model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    private final static String usersDbPath = "user_db.txt";
    private final static String ordersDbPath = "order_db.txt";

    public static void restoreUserDb() {
        List<User> users = new ArrayList<>();
        users.add(new User("test@gmail.com", "123456"));
        users.add(new User("test@gmail.com", "123456"));
        users.add(new User("test@gmail.com", "123456"));

        try {
            JSONUtils.writeListIntoFile(usersDbPath, users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void restoreOrderDb() {
        Order order1 = new Order("Oleg", "Andrey", "Kyiv");
        Order order2 = new Order("Yuriy", "Andrey", "Kyiv");
        Order order3 = new Order("Ivan", "Andrey", "Kyiv");

        List<Order> orders = new ArrayList<>();

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        order1.setId(1);
        order2.setId(2);
        order3.setId(3);

        try {
            JSONUtils.writeListIntoFile(ordersDbPath, orders);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
