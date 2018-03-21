package model;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class CustomerTest {

    Customer testUser = new Customer("test3@gmail.com", "123456", "344", "Igor");


    @Test
    public void getPhone() {
        assertEquals("123456", testUser.getPhone());
    }

    @Test
    public void getName() {
        assertEquals("Igor", testUser.getName());
    }
}