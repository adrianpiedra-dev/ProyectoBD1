package ucr.proyectobd1.model;

public class Canton {
    private String code;
    private String name;
    private String province;

    public Canton(String code, String name, String province) {
        this.code = code;
        this.name = name;
        this.province = province;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return
                "Codigo: " + code + '\n' +
                "Nombre: " + name + '\n' +
                "Provincia: " + province;
    }
}
