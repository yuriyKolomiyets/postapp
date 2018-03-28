package utils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PathUtilsTest {

    @Test
    public void getUsersDbPath() {
        assertEquals("src/user_db.txt", PathUtils.getUsersDbPath());
    }

    @Test
    public void getOrdersDbPath() {
        assertEquals("src/order_db.txt", PathUtils.getOrdersDbPath());

    }
}