package ru.netology.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;


public class RestApi {
    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(8080)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static String fillPaymentForm(DataGenerator.CardInformation information) {
        return given()
                .spec(requestSpec)
                .body(information)

                .when()
                .post("/api/v1/pay")

                .then()
                .statusCode(200)
                .extract().response().asString();
    }

    public static String fillPaymentFormCredit(DataGenerator.CardInformation information) {
        return given()
                .spec(requestSpec)
                .body(information)

                .when()
                .post("/api/v1/credit")

                .then()
                .statusCode(200)
                .extract().response().asString();
    }
}