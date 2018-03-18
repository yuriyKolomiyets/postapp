package utils;

import com.google.gson.Gson;
import model.Order;
import model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    private final static String usersDbPath = "user_db.txt";
    private final static String ordersDbPath = "order_db.txt";

    public static void restoreUserDb() {

        User user1 = new User("test@gmail.com", "123456");
        User user2 = new User("test@gmail.com", "123456");
        User user3 = new User("test@gmail.com", "123456");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        // todo all paths should be visible and extracted to appropriated place

        try {
            JSONUtils.writeListIntoFile(usersDbPath, users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void restoreOrderDb() {
        Gson gson = new Gson();

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
