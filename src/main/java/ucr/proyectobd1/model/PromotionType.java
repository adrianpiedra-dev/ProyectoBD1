package ucr.proyectobd1.model;

public class PromotionType {
    private String codeTP;
    private float percentage;

    public PromotionType(String codeTP, float percentage) {
        this.codeTP = codeTP;
        this.percentage = percentage;
    }

    public String getCodeTP() {
        return codeTP;
    }

    public void setCodeTP(String codeTP) {
        this.codeTP = codeTP;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }
}
