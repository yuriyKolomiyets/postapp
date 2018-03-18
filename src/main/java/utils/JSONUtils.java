package utils;

import com.google.gson.Gson;
import model.Order;
import model.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class JSONUtils {

    private static Gson gson = new Gson();

   public static<T> T  fromJson(String json, Class<T> tClass) {
        return gson.fromJson(json, tClass);
    }

   public static String toJson(Object src) {
        return gson.toJson(src);
    }

    public static List<User> readUsersFromFile(String path, Class<User[]> objClass) throws IOException {

        User[] cache = gson.fromJson(new FileReader(path), objClass);

        return new ArrayList<>(asList(cache));

    }

    public static List<Order> readOrdersFromFile(String path, Class<Order[]> objClass) throws IOException {

        Order[] cache = gson.fromJson(new FileReader(path), objClass);

        return new ArrayList<>(asList(cache));


    }

    public static void writeListIntoFile(String path, List<?> list) throws IOException {

        Writer writer = new FileWriter(path, false);

        writer.write(gson.toJson(list));

        writer.flush();

        // todo use flash and close the stream

    }

    // todo apache commons, guava have a lot of utils and useful methods

    public static List<Order> getOrdersFromDb(String path) throws IOException {

        List<Order> orders = readOrdersFromFile(path, Order[].class);
        return orders;

    }


    public static List <User> getUsersFromDb(String path) throws IOException {

        List<User> users = readUsersFromFile(path, User[].class);
        return users;
    }

    public static void saveUsersToDb(String userDbPath, Map<String, User> users) {

        List<User> usersList = new ArrayList<>(users.values());

        try {
            writeListIntoFile(userDbPath, usersList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveOrdersToDb(String ordersDbPath, Map<Integer, Order> orders) {
        List<Order> orderList = new ArrayList<>(orders.values());

        try {
            writeListIntoFile(ordersDbPath, orderList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
