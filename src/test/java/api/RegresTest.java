package api;


import org.junit.Assert;
import org.junit.Test;

import java.util.List;


import static io.restassured.RestAssured.given;

public class RegresTest {
    private static final String URL = "https://reqres.in";
    @Test
    public void checkAvatarAndIdTest(){
        //Используя сервис https://reqres.in получить список пользователей со второй страницы.
        //Убедиться что имена файлов аватаров пользователей включают ID пользователей.
        //Убедиться что email пользователей имеет окончание reqres.in
        Specification.installSpecification(Specification.requestSpecification(URL),Specification.responseSpecificationUnique(200));
        List<UserData> users = given()  //Статичный метод given() RestAssured
                .when()
                .get("/api/users?page=2")//Указываем запрос и ссылку
                .then().log().all()             //Выводим лог в консоль
                .extract().body().jsonPath().getList("data", UserData.class); //Извлекаем Json в формате UserData
        users.forEach(x-> Assert.assertTrue(x.getAvatar().contains(x.getId().toString()))); //Убедиться что имена файлов аватаров пользователей включают ID пользователей.

        Assert.assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("@reqres.in"))); //Убедиться что email пользователей имеет окончание reqres.in

        List <String> avatars = users.stream().map(UserData::getAvatar).toList();
        List <String> ids = users.stream().map(x->x.getId().toString()).toList();
        for (int i = 0; i < avatars.size(); i++) {
            Assert.assertTrue(avatars.get(i).contains(ids.get(i)));
        }
    }
    @Test
    public void successRegisterTest(){
        //Используя сервис https://reqres.in протестировать регистрацию пользователя в системе
        //Необходимо создание 2 тестов:
        //- успешная регистрация;
        //Проверить коды ошибок
        Specification.installSpecification(Specification.requestSpecification(URL),Specification.responseSpecificationUnique(200));
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        Register user = new Register("eve.holt@reqres.in","pistol");
        SuccessRegister successRegister = given()
                .body(user)
                .when()
                .post("/api/register")
                .then().log().all()
                .extract().as(SuccessRegister.class);       //Используя сервис https://reqres.in протестировать регистрацию пользователя в системе

        Assert.assertNotNull(successRegister.getId());      //Проверка, что пришёл не пустой ответ
        Assert.assertNotNull(successRegister.getToken());

        Assert.assertEquals(id,successRegister.getId());    //Успешная регистрация;
        Assert.assertEquals(token,successRegister.getToken());
    }

    @Test
    public void unSuccessRegisterTest(){
        //- регистрация с ошибкой из-за отсутствия пароля.
        Specification.installSpecification(Specification.requestSpecification(URL),Specification.responseSpecificationUnique(400));

        Register user = new Register("sydney@fife","");

        UnSuccessRegister unSuccessRegister = given()
                .body(user)
                .when()
                .post("/api/register")
                .then().log().all()
                .extract().as(UnSuccessRegister.class);

        Assert.assertEquals("Missing password",unSuccessRegister.getError());
    }

    @Test
    public void colorsDataTest(){
        //Используя сервис https://reqres.in убедиться, что операция LIST<RESOURCE> возвращает данные отсортированные по годам
        Specification.installSpecification(Specification.requestSpecification(URL),Specification.responseSpecificationUnique(200));

        List<ColorsData> data = given()
                .when()
                .get("/api/unknown")
                .then().log().all()
                .extract().body().jsonPath().getList("data", ColorsData.class);
        List<Integer> years = data.stream().map(ColorsData::getYear).toList(); //Получаем список всех годов
        List<Integer> sortedYears = years.stream().sorted().toList();           //Сортируем полученный список
        Assert.assertEquals(sortedYears,years);                                 //Сравниваем отсортированный и полученный списки
        System.out.println(years);
        System.out.println(sortedYears);
    }
    @Test
    public void deleteUserTest(){
        //Используя сервис https://reqres.in попробовать удалить второго пользователя и сравнить статус-код
        Specification.installSpecification(Specification.requestSpecification(URL),Specification.responseSpecificationUnique(204)); //При выполнении ожидаем 204 статус-код

        given()
                .when()
                .delete("/api/users/2")
                .then().log().all();
    }
}
