package ucr.proyectobd1.model;

public class District {
    private String cod;
    private String name;
    private String codCanton;

    public District(String cod, String name, String codCanton) {
        this.cod = cod;
        this.name = name;
        this.codCanton = codCanton;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodCanton() {
        return codCanton;
    }

    public void setCodCanton(String codCanton) {
        this.codCanton = codCanton;
    }

    @Override
    public String toString() {
        return
                "Código = " + cod + '\n' +
                "Nombre = " + name + '\n' +
                "Código Cantón: " + codCanton;
    }
}
