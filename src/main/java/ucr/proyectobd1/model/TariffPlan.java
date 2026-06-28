package ucr.proyectobd1.model;

public class TariffPlan {
    private String code;
    private String name;
    private String description;
    private float share;
    private int gigabytes;
    private int minutes;
    private int ms;
    private float costEC;
    private String codeCC;

    public TariffPlan(String code, String name, String description, float share, int gigabytes, int minutes, int ms, float costEC, String codeCC) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.share = share;
        this.gigabytes = gigabytes;
        this.minutes = minutes;
        this.ms = ms;
        this.costEC = costEC;
        this.codeCC = codeCC;
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

    public float getShare() {
        return share;
    }

    public void setShare(float share) {
        this.share = share;
    }

    public int getGigabytes() {
        return gigabytes;
    }

    public void setGigabytes(int gigabytes) {
        this.gigabytes = gigabytes;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getMs() {
        return ms;
    }

    public void setMs(int ms) {
        this.ms = ms;
    }

    public float getCostEC() {
        return costEC;
    }

    public void setCostEC(int costEC) {
        this.costEC = costEC;
    }

    public String getCodeCC() {
        return codeCC;
    }

    public void setCodeCC(String codeCC) {
        this.codeCC = codeCC;
    }
}
