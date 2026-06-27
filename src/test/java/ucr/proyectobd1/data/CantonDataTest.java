package ucr.proyectobd1.data;

import org.junit.jupiter.api.Test;
import ucr.proyectobd1.model.Canton;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CantonDataTest {

    CantonData cantonData = new CantonData();
    @Test
    void insertCanton() {
        Canton canton = new Canton("CAR1", "CARTAGO", "CARTAGO");
        cantonData.insertCanton(canton);
    }

    @Test
    void existCanton(){
        boolean exist = cantonData.existCanton("CAR1");
        System.out.println(exist);
    }

    @Test
    void deleteCanton() {
        Canton canton = new Canton("CAR1", "CARTAGO", "CARTAGO");
        cantonData.deleteCanton("CAR1");
    }

    @Test
    void viewCantons() {
        List<Canton> cantons = cantonData.searchCanton("CARTAGO");

        for(int i= 0; i < cantons.size(); i++){
            System.out.println(cantons.get(i).toString());
        }
    }
}