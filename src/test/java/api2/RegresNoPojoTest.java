package api2;

import api.Specification;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Response response = given() //Сохраняем результат ответа в интерфейс Response
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
        JsonPath jsonPath = response.jsonPath(); //Извлекаем ответ в jsonPath
        List<String> avatars = jsonPath.get("data.avatar"); //Получаем список всех полей аватар
        List<Integer> ids = jsonPath.get("data.id");        //Получаем список всех полей id
        for (int i = 0; i < avatars.size(); i++) {
            Assert.assertTrue(avatars.get(i).contains(ids.get(i).toString())); //Убедиться что имена файлов аватаров пользователей включают ID пользователей.
        }
        List<String> emails = jsonPath.get("data.email");
        Assert.assertTrue(emails.stream().allMatch(x->x.endsWith("@reqres.in")));   //Убедиться что email пользователей имеет окончание reqres.in
    }
    @Test
    public void successRegisterNoPojoTest(){
        //Используя сервис https://reqres.in протестировать регистрацию пользователя в системе
        //Необходимо создание 2 тестов:
        //- успешная регистрация;
        //Проверить коды ошибок
        Specification.installSpecification(Specification.requestSpecification(URL),Specification.responseSpecificationUnique(200));
        Map<String,String> user = new HashMap<>(); //Создаём хэш-карту для указания запроса
        user.put("email","eve.holt@reqres.in");    //Заполняем хэш-карту требуемыми данными
        user.put("password","pistol");
        given()         //Пример post запроса
                .body(user)
                .when()
                .post("/api/register")
                .then().log().all()
                .body("id",equalTo(4))
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));

        //Аналогичную проверку можно сделать через Response

        Response response = given()
                .body(user)
                .when()
                .post("/api/register")
                .then()
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        int id = jsonPath.get("id");
        String token = jsonPath.get("token");
        Assert.assertEquals(4,id);
        Assert.assertEquals("QpwL5tke4Pnpja7X4",token);
    }
    @Test
    public void unSuccessRegisterNoPojoTest() {
        //- регистрация с ошибкой из-за отсутствия пароля.
        Specification.installSpecification(Specification.requestSpecification(URL),Specification.responseSpecificationUnique(400));
        Map<String,String> user = new HashMap<>();
        user.put("email","eve.holt@reqres.in");
        user.put("password","");
        given()
                .body(user)
                .when()
                .post("/api/register")
                .then().log().all()
                .body("error", equalTo("Missing password"));
    }
}
