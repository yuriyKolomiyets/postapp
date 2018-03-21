package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    User testUser = new User("test3@gmail.com", "123456");

    @Test
    public void getId() {
        testUser.setId(1);
        assertEquals(1, testUser.getId());
    }

    @Test
    public void setId() {
        testUser.setId(1);
        assertEquals(1, testUser.getId());
    }

    @Test
    public void getEmail() {
        assertEquals("test3@gmail.com", testUser.getEmail());
    }

    @Test
    public void getPass() {
        assertEquals("123456", testUser.getPass());
    }
}