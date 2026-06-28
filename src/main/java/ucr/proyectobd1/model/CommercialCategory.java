package ucr.proyectobd1.model;

public class CommercialCategory {
    private String code;
    private String description;
    private double speedMbps;

    public CommercialCategory(String code, String description, double speedMbps) {
        this.code = code;
        this.description = description;
        this.speedMbps = speedMbps;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSpeedMbps() {
        return speedMbps;
    }

    public void setSpeedMbps(double speedMbps) {
        this.speedMbps = speedMbps;
    }

    @Override
    public String toString() {
        return "Code: " + code + '\n' +
                "Description: " + description + '\n' +
                "Speed (Mbps): " + speedMbps;
    }
}
