package ucr.proyectobd1.model;

public class InvoiceConcept {
    private String invoiceNumber;
    private String description;
    private double amount;

    public InvoiceConcept(String invoiceNumber, String description, double amount) {
        this.invoiceNumber = invoiceNumber;
        this.description = description;
        this.amount = amount;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Invoice Number: " + invoiceNumber + '\n' +
                "Description: " + description + '\n' +
                "Amount: " + amount;
    }
}
