package model;

public class Customer extends User {
    private String phone;
    private String name;

    public Customer() {
    }

    public Customer(String phone, String name) {
        this.phone = phone;
        this.name = name;
    }

    public Customer(String email, String pass, String phone, String name) {
        super(email, pass);
        this.phone = phone;
        this.name = name;
    }

    public String editUserName() {
        return null;

    }

    public String editUserPhone() {
        return null;

    }

    public String editUserEmail() {
        return null;

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
