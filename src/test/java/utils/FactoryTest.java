package utils;

import appDb.AppDbImpl;
import controller.MainController;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class FactoryTest {

    @Test
    public void create() {
        MainController mainController = Factory.create(new AppDbImpl());
        assertThat(mainController, notNullValue());
    }
}