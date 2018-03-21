package model;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class CustomerTest {

    private Customer testUser = new Customer("test3@gmail.com", "123456", "344", "Igor");


    @Test
    public void getPhone() {
        assertEquals("344", testUser.getPhone());
    }

    @Test
    public void getName() {
        assertEquals("Igor", testUser.getName());
    }
}