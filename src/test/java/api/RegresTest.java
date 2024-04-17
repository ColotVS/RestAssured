package api;

import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class RegresTest {
    //Используя сервис https://reqres.in получить список пользователей со второй страницы.
    //Убедиться что имена файлов аватаров пользователей включают ID пользователей.
    //Убедиться что email пользователей имеет окончание regres.in
    private static final String URL = "https://reqres.in";
    @Test
    public void checkAvatarAndIdTest(){
        List<UserData> users = given()  //Статичный метод given() RestAssured
                .when()
                .contentType(ContentType.JSON) //Выбор типа ответа
                .get(URL + "/api/users?page=2")//Указываем запрос и ссылку
                .then().log().all()             //Выводим лог в консоль
                .extract().body().jsonPath().getList("data", UserData.class); //Извлекаем Json в формате UserData
        users.forEach(x-> Assert.assertTrue(x.getAvatar().contains(x.getId().toString()))); //Убедиться что имена файлов аватаров пользователей включают ID пользователей.

    }
}
