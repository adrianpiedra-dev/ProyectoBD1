package ucr.proyectobd1.model;

public class Service {
    private String code;
    private String name;
    private String description;
    private double monthlyCost;
    private String categoryCode;

    public Service(String code, String name, String description, double monthlyCost, String categoryCode) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.monthlyCost = monthlyCost;
        this.categoryCode = categoryCode;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getMonthlyCost() {
        return monthlyCost;
    }

    public void setMonthlyCost(double monthlyCost) {
        this.monthlyCost = monthlyCost;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    @Override
    public String toString() {
        return "Code: " + code + '\n' +
                "Name: " + name + '\n' +
                "Description: " + description + '\n' +
                "Monthly Cost: " + monthlyCost + '\n' +
                "Category Code: " + categoryCode;
    }
}
