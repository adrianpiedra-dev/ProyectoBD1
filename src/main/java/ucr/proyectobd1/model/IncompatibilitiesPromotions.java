package ucr.proyectobd1.model;

public class IncompatibilitiesPromotions {
    private String promotion1;
    private String promotion2;

    public IncompatibilitiesPromotions(String promotion1, String promotion2) {
        this.promotion1 = promotion1;
        this.promotion2 = promotion2;
    }

    public String getPromotion1() {
        return promotion1;
    }

    public void setPromotion1(String promotion1) {
        this.promotion1 = promotion1;
    }

    public String getPromotion2() {
        return promotion2;
    }

    public void setPromotion2(String promotion2) {
        this.promotion2 = promotion2;
    }
}
