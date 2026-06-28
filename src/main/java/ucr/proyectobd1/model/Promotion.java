package ucr.proyectobd1.model;

import java.sql.Date;

public class Promotion {
    private String code;
    private String name;
    private String description;
    private Date iDate;
    private Date fDate;
    private float pDiscount;
    private String typePromotion;

    public Promotion(String code, String name, String description, Date iDate, Date fDate, float pDiscount, String typePromotion) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.iDate = iDate;
        this.fDate = fDate;
        this.pDiscount = pDiscount;
        this.typePromotion = typePromotion;
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

    public Date getiDate() {
        return iDate;
    }

    public void setiDate(Date iDate) {
        this.iDate = iDate;
    }

    public Date getfDate() {
        return fDate;
    }

    public void setfDate(Date fDate) {
        this.fDate = fDate;
    }

    public float getpDiscount() {
        return pDiscount;
    }

    public void setpDiscount(float pDiscount) {
        this.pDiscount = pDiscount;
    }

    public String getTypePromotion() {
        return typePromotion;
    }

    public void setTypePromotion(String typePromotion) {
        this.typePromotion = typePromotion;
    }
}
