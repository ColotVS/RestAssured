package Petstore;

import api.Specification;
import org.junit.Test;

import static io.restassured.RestAssured.given;

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
        PetRegister pet = new PetRegister(123,"Шери","available");
        given()
                .body(pet)
                .when()
                .post("/pet")
                .then().log().all();
    }
}
