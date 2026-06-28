package ucr.proyectobd1.model;

import java.sql.Date;

public class Points {
    private String mobileLinePhone;
    private Date date;
    private double quantity;
    private double balance;

    public Points(String mobileLinePhone, Date date, double quantity, double balance) {
        this.mobileLinePhone = mobileLinePhone;
        this.date = date;
        this.quantity = quantity;
        this.balance = balance;
    }

    public String getMobileLinePhone() {
        return mobileLinePhone;
    }

    public void setMobileLinePhone(String mobileLinePhone) {
        this.mobileLinePhone = mobileLinePhone;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Mobile Line: " + mobileLinePhone + '\n' +
                "Date: " + date + '\n' +
                "Quantity: " + quantity + '\n' +
                "Balance: " + balance;
    }
}
