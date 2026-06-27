package ucr.proyectobd1.model;

import java.sql.Date;

public class Customer {
    private String id;
    private String name;
    private String lastName1;
    private String lastName2;
    private String address;
    private String codDistrict;
    private String email;
    private String typeCustomer;

    public Customer(String id, String name, String lastName1, String lastName2, String address, String codDistrict, String email, String typeCustomer) {
        this.id = id;
        this.name = name;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
        this.address = address;
        this.codDistrict = codDistrict;
        this.email = email;
        this.typeCustomer = typeCustomer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName1() {
        return lastName1;
    }

    public void setLastName1(String lastName1) {
        this.lastName1 = lastName1;
    }

    public String getLastName2() {
        return lastName2;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCodDistrict() {
        return codDistrict;
    }

    public void setCodDistrict(String codDistrict) {
        this.codDistrict = codDistrict;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTypeCustomer() {
        return typeCustomer;
    }

    public void setTypeCustomer(String typeCustomer) {
        this.typeCustomer = typeCustomer;
    }

    @Override
    public String toString() {
        return
                "Id: " + id + '\n' +
                "Nombre: " + name + '\n' +
                "Apellido: " + lastName1 + '\n' +
                "Apellido: " + lastName2 + '\n' +
                "Dirección: '" + address + '\n' +
                "Código Distrito: " + codDistrict + '\n' +
                "Correo: " + email + '\n' +
                "Tipo de Cliente:" + typeCustomer;
    }
}
