package ucr.proyectobd1.data;

import org.junit.jupiter.api.Test;
import ucr.proyectobd1.model.District;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DistrictDataTest {

    DistrictData districtData = new DistrictData();

    @Test
    void insertDistrict() {
        District district = new District("CAR1-1", "ORIENTAL", "CART1");
        District district2 = new District("CART1-2", "OCCIDENTAL", "CART1");
        District district3 = new District("CART1-3", "ORIENTAL", "CART1");
        District district4 = new District("CART1-4", "EL CARMEN", "CART1");
        District district5 = new District("CART1-5", "AGUA CALIENTE", "CART1");
        District district6 = new District("CART1-6", "DULCE NOMBRE", "CART1");
        District district7 = new District("CART1-7", "LLANO GRANDE", "CART1");
        District district8 = new District("CART2-1", "PARAISO", "CART2");
        District district9 = new District("CART2-2", "OROSI", "CART2");
        District district10 = new District("CART2-3", "CACHI", "CART2");
        District district11 = new District("CART2-4", "UJARRAS", "CART2");
        District district12 = new District("CART2-5", "CERVANTES", "CART2");
        District district13 = new District("CART2-6", "BIRRISITO", "CART2");
        District district14 = new District("CART3-1", "TRES RIOS", "CART3");
        District district15 = new District("CART3-2", "SAN DIEGO", "CART3");
        District district16 = new District("SJ1-1", "SAN JOSE", "SJ1");
        District district17 = new District("SJ1-2", "HOSPITAL", "SJ1");
        District district18 = new District("SJ1-3", "MERCED", "SJ1");
        districtData.insertDistrict(district);
        districtData.insertDistrict(district2);
        districtData.insertDistrict(district3);
        districtData.insertDistrict(district4);
        districtData.insertDistrict(district5);
        districtData.insertDistrict(district6);
        districtData.insertDistrict(district7);
        districtData.insertDistrict(district8);
        districtData.insertDistrict(district9);
        districtData.insertDistrict(district10);
        districtData.insertDistrict(district11);
        districtData.insertDistrict(district12);
        districtData.insertDistrict(district13);
        districtData.insertDistrict(district14);
        districtData.insertDistrict(district15);
        districtData.insertDistrict(district16);
        districtData.insertDistrict(district17);
        districtData.insertDistrict(district18);
    }

    @Test
    void existDistrict(){
        boolean exists1 = districtData.existDistrict("11111");
        System.out.println(exists1);
        boolean exists2 = districtData.existDistrict("CART2-1");
        System.out.println(exists2);
    }

    @Test
    void deleteDistrict(){
        districtData.deleteDistrict("CART2-6");
    }

    @Test
    void getDistricts() {
        List<District> districtsC = districtData.getDistricts("CART1");
        System.out.println("DISTRITOS DE CARTAGO: ");
        for(int i =0; i < districtsC.size(); i++){
            System.out.println(districtsC.get(i).toString());
        }

        List<District> districtsP = districtData.getDistricts("CART2");
        System.out.println("DISTRITOS DE PARAÍSO: ");
        for(int i =0; i < districtsP.size(); i++){
            System.out.println(districtsP.get(i).toString());
        }
    }
}