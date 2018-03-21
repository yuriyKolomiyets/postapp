package controller;

import exceptions.AppException;
import model.Order;
import model.User;

import java.time.LocalDateTime;
import java.util.Map;

public interface MainController {

    Map<String, User> getAllUsers() throws AppException;
    Map<Integer, Order> getAllOrders() throws AppException;

    User getById(int id) throws AppException;
    Order getOrderById(int id) throws AppException;

    Map<Integer, Order> filterByName(String name) throws AppException;
    Map<Integer, Order> filterByCity(String city) throws AppException;
    Map<Integer, Order> filterByReceiver(String receiverName) throws AppException;
    Map<Integer, Order> filterByDate(LocalDateTime dateTime) throws AppException;
}
