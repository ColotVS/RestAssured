package api2;

import api.Specification;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class RegresNoPojoTest{
    private static final String URL = "https://reqres.in";
    @Test
    public void checkAvatarsNoPojoTest(){
        //Используя сервис https://reqres.in получить список пользователей со второй страницы.
        //Убедиться что имена файлов аватаров пользователей включают ID пользователей.
        //Убедиться что email пользователей имеет окончание reqres.in
        Specification.installSpecification(Specification.requestSpecification(URL),Specification.responseSpecificationUnique(200));
        Response response = given() //Сохраняем результат ответа в класс Response
                .when()
                .get("/api/users?page=2")
                .then().log().all()
                .body("page", equalTo(2))   //Проверка ответа запроса, что значение page = 2;
                .body("data.id", notNullValue())    //Проверка ответа запроса, что поле id в блоке data не вернуло null
                .body("data.email", notNullValue())    //Проверка ответа запроса, что поле email в блоке data не вернуло null
                .body("data.first_name", notNullValue())    //Проверка ответа запроса, что поле first_name в блоке data не вернуло null
                .body("data.last_name", notNullValue())    //Проверка ответа запроса, что поле last_name в блоке data не вернуло null
                .body("data.avatar", notNullValue())    //Проверка ответа запроса, что поле avatar в блоке data не вернуло null
                .extract().response();
    }
}
