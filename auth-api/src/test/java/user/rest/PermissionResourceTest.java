package user.rest;

import auth.dto.LoginRequestDto;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.transaction.annotations.Rollback;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import user.dto.PermissionDto;

import java.io.InputStream;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static javax.management.Query.and;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
@Slf4j
public class PermissionResourceTest {

    private static String TOKEN_VALUE = "";

    @BeforeAll
    public static void setBaseUrl(){
        RestAssured.baseURI = "http://localhost:8082";
    }


    @BeforeAll
    public static void setUpToken() {
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername("admin");
        loginRequestDto.setPassword("admin");
        TOKEN_VALUE = given().body(loginRequestDto)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when().post("/auth/login")
                .then()
                .statusCode(200)
                .and()
                .extract()
                .path("token");
    }

    private RequestSpecification requestWithAuth(){
        return  RestAssured.given().headers(Map.of("x-auth-token",TOKEN_VALUE));
    }


    @Test
    void testGetPermissions() {
        InputStream permissionsSchema = getClass().getClassLoader().getResourceAsStream("permissions-schema.json");
        assert permissionsSchema != null;
        requestWithAuth()
                .log().all()
                .queryParams(Map.of("code","u"))
                .when().get("/permission/permissions")
                .prettyPeek()
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(permissionsSchema))
                .and()
                .extract()
                .path("metadata");


    }

    @Test

    void testCreatePermission() {
        PermissionDto dto = new PermissionDto();
        dto.setCode("TEST");
        dto.setDescription("Used for testing");


        Response post = requestWithAuth()
                .log().all()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/permission/permissions");

        post.then().statusCode(200);
        Long permissionId = post.body().jsonPath().getLong("id");

        requestWithAuth().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParams(Map.of("id",permissionId))
                .when().delete("/permission/permissions/{id}");



    }
}
