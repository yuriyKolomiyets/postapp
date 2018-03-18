package utils;

import model.User;

import java.io.IOException;
import java.util.List;
import java.util.Random;


// todo save with the last id into json
public class ModelUtils {

    // todo cover case when data will be loaded from the db file and application restarts
    // todo in desribed case you probably have no uniques ids
    private static Random random = new Random();

    public static int genId() {
        return  (int)(Math.random() * 50000);
    }

    public static int genUserId() {
        int maxId = 0;
        List<User> users = null;
        try {
            users = JSONUtils.getUsersFromDb("user_db.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (User iterUser : users) {
            if (iterUser.getId() == maxId) {
                maxId++;
            }
        }
        return maxId;
    }
}