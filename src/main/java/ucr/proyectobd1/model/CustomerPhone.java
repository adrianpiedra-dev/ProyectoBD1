package ucr.proyectobd1.model;

public class CustomerPhone {
    private String customerId;
    private String phone;

    public CustomerPhone(String customerId, String phone) {
        this.customerId = customerId;
        this.phone = phone;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Customer ID: " + customerId + '\n' +
                "Phone: " + phone;
    }
}
