package api;

import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class RegresTest {
    //Используя сервис https://reqres.in получить список пользователей со второй страницы.
    //Убедиться что имена файлов аватаров пользователей включают ID пользователей.
    //Убедиться что email пользователей имеет окончание reqres.in
    private static final String URL = "https://reqres.in";
    @Test
    public void checkAvatarAndIdTest(){
        List<UserData> users = given()  //Статичный метод given() RestAssured
                .when()
                .contentType(ContentType.JSON) //Выбор типа ответа
                .get(URL + "/api/users?page=2")//Указываем запрос и ссылку
                .then().log().all()             //Выводим лог в консоль
                .extract().body().jsonPath().getList("data", UserData.class); //Извлекаем Json в формате UserData
        //users.forEach(x-> Assert.assertTrue(x.getAvatar().contains(x.getId().toString()))); //Убедиться что имена файлов аватаров пользователей включают ID пользователей.

        //Assert.assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("@reqres.in"))); //Убедиться что email пользователей имеет окончание reqres.in

        List <String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());
        List <String> ids = users.stream().map(x->x.getId().toString()).collect(Collectors.toList());
        for (int i = 0; i < avatars.size(); i++) {
            Assert.assertTrue(avatars.get(i).contains(ids.get(i)));
        }
    }
}
