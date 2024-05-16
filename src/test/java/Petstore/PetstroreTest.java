package Petstore;

import api.Specification;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * Работа с методом POST
     * Добавление нового животного
     * Негативный сценарий
     */
    @Test
    public void checkUnSucAddNewPetTest(){
        Specification.installSpecification(Specification.requestSpecification(URL),Specification.responseSpecificationUnique(500));
        Map<String,String> pet = new HashMap<>();
        pet.put("id","invalidInput%^&"); //Вводим некорректные данные id
        pet.put("name","Demon");
        pet.put("status","available");
        given()
                .body(pet)
                .when()
                .post("/pet")
                .then().log().all();
    }

    /**
     * Работа с методом PUT
     * Обновление информации о животном
     * Позитивный сценарий
     */
    @Test
    public void checkUpdateAddPetTest(){
        Specification.installSpecification(Specification.requestSpecification(URL),Specification.responseSpecificationUnique(200));
        PetUpdate pet = new PetUpdate(123,"Шеричка","available");
        given()
                .body(pet)
                .when()
                .put("/pet")
                .then().log().all();
    }

    /**
     * Работа с методом PUT
     * Обновление информации о животном
     * Негативный сценарий
     */
    @Test
    public void checkUnSucUpdateTest(){
        Specification.installSpecification(Specification.requestSpecification(URL),Specification.responseSpecificationUnique(500));
        Map<String,String> pet = new HashMap<>();
        pet.put("id","invalidInput%^&"); //Вводим некорректные данные id
        pet.put("name","Шеричка");
        pet.put("status","available");
        given()
                .body(pet)
                .when()
                .put("/pet")
                .then().log().all();
    }
}
