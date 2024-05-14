package Petstore;

import api.Specification;
import org.junit.Test;

public class PetstroreTest {
    private final static String URL = "https://petstore.swagger.io/v2";

    /**
     * Работа с методом POST
     * Добавление нового животного
     * Позитивный сценарий
     */
    @Test
    public void checkAddNewPetTest(){
        Specification.installSpecification(Specification.requestSpecification(URL),Specification.responseSpecificationUnique(200));
    }
}
