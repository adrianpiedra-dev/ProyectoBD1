package ucr.proyectobd1.model;

import java.sql.Date;

public class Package {
    private String code;
    private String name;
    private int validity;
    private double price;
    private int quantity;

    public Package(String code, String name, int validity, double price, int quantity) {
        this.code = code;
        this.name = name;
        this.validity = validity;
        this.price = price;
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Code: " + code + '\n' +
                "Name: " + name + '\n' +
                "Validity (days): " + validity + '\n' +
                "Price: " + price + '\n' +
                "Quantity: " + quantity;
    }
}
