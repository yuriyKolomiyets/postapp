package appDb;

import exceptions.AppException;
import exceptions.LoginCredentialException;
import exceptions.UserNotFoundException;
import model.Order;
import model.User;

import java.util.Map;

public interface AppDb {

    User addUser(User user) throws AppException;

    User removeUser(User user) throws UserNotFoundException;

    Map<String, User> getUsers() throws AppException;

    void setUsers(Map<String, User> users);

    Map<String, User> getUsersFromDb(String userDbPath) throws AppException;

    Map<Integer, Order> getOrdersFromDb(String ordersDbPath) throws AppException;

    Map<Integer, Order> getOrders()throws AppException;

    String createAccessToken(User user) throws LoginCredentialException;

    boolean hasToken(String accessToken)throws AppException;

    boolean register(String email, String pass) throws AppException;

}
