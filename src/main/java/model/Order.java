package model;

import utils.ModelUtils;

import java.time.LocalDateTime;

public class Order {

    private static final int DAYS = 31;
    private int id;
    private String senderName;
    private String receiverName;
    private String targetCity;
    private LocalDateTime sendDate;
    private OrderStatus orderStatus;

    public Order(String senderName, String receiverName, String targetCity) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.sendDate = LocalDateTime.now().minusDays((int)(Math.random() * DAYS));
        this.targetCity = targetCity;
        this.id = ModelUtils.genId();
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getTargetCity() {
        return targetCity;
    }

    public void setTargetCity(String targetCity) {
        this.targetCity = targetCity;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", senderName='" + senderName + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", targetCity='" + targetCity + '\'' +
                '}';
    }
}
