package utils;

import appDb.AppDbImpl;
import controller.MainController;
import controller.MainControllerImpl;

public class Factory {

    public static MainController create(AppDbImpl appDb) {
            return new MainControllerImpl(appDb);
    }
}

