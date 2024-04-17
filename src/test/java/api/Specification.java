package api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class Specification {
    public static RequestSpecification requestSpecification(String url){    //Задаем параметры
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .setContentType(ContentType.JSON)
                .build();
    }
    public static ResponseSpecification responseSpecificationOK200(){       //Если запрос возвращает код 200 выполнение программы продолжается, иначе остановка без выполнения.
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }
    public static ResponseSpecification responseSpecificationError400(){    //Если запрос возвращает код 400 выполнение программы продолжается, иначе остановка без выполнения.
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .build();
    }
    public static void installSpecification(RequestSpecification request,ResponseSpecification response){   //Установка спецификаций
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }
}
