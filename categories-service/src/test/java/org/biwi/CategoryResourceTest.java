package org.biwi;

import io.quarkus.test.junit.QuarkusTest;
import org.biwi.models.Category;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryResourceTest {
    static Category c = new Category("Desporto");

    @Test
    @Order(1)
    public void testAddEndpoint() {
        Map<String, String> data = new HashMap<>();
        data.put("name", c.getName());

        c = given()
                .contentType("application/json")
                .body(data)
                .when().post("/v1/categories/")
                .then()
                .statusCode(200)
                .body("name", equalTo(c.getName()))
                .extract()
                .as(Category.class);
    }

    @Test
    @Order(2)
    public void testGetEndpoint() {
        given()
                .pathParam("id", c.id)
                .when().get("/v1/categories/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo(c.getName()));
    }

    @Test
    @Order(3)
    public void testListEndpoint() {
        given()
                .when().get("/v1/categories/")
                .then()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @Order(4)
    public void testEditEndpoint() {
        Map<String, String> data = new HashMap<>();
        data.put("name", c.getName() + "2");

        given()
                .contentType("application/json")
                .body(data)
                .pathParam("id", c.id)
                .when().put("/v1/categories/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo(c.getName() + "2"));
    }

    @Test
    @Order(5)
    public void testDeleteEndpoint() {
        given()
                .pathParam("id", c.id)
                .when().delete("/v1/categories/{id}")
                .then()
                .statusCode(204);
    }
}