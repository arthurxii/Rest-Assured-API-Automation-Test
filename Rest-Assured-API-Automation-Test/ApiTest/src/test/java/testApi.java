import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;

public class testApi {
    @BeforeClass
    public void setBaseUri(){
        baseURI = "https://reqres.in/api/users";
    }

    @Test
    public void getPrimeiraPagina(){
        given()
            .param("page", "1")
        .when()
            .get(baseURI)
        .then()
            .log().all()
        .assertThat()
            .statusCode(200)
        .and()
        .assertThat()
        .body("page", equalTo(1));

    }

    @Test
    public void getUsuario(){
        given()
        .when()
            .get(baseURI + "/2")
        .then()
            .log().all()
        .assertThat()
            .statusCode(200)
        .and()
        .assertThat()
            .body("data.id", equalTo(2))
        .and()
        .assertThat()
            .body("data.first_name", equalTo("Janet"));

    }

    @Test
    public void getListaUsuarios(){
        given()
        .when()
            .get(baseURI + "/?page=2")
        .then()
            .log().all()
        .assertThat()
            .statusCode(200)
        .and()
        .assertThat()
            .body("page", equalTo(2))
        .and()
        .assertThat()
            .body("total_pages", equalTo(2));

    }

    @Test
    public void postCriarUsuarioTest(){
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "Arthur");
        requestParams.put("job", "Quality Assurance Analyst");

        given()
            .body(requestParams.toString())
        .when()
            .post(baseURI)
        .then()
            .log().all()
        .assertThat()
            .statusCode(201)
        .and()
            .contentType(ContentType.JSON)
        .assertThat()
            .body(containsString("createdAt"));
    }

    @Test
    public void putUsuarioTest(){
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "Arthur Henrique");
        requestParams.put("job", "QA");

        given()
            .body(requestParams.toString())
        .when()
            .put(baseURI + "/2")
        .then()
            .log().all()
            .assertThat()
            .statusCode(200)
        .and()
            .contentType(ContentType.JSON)
        .assertThat()
            .body(containsString("updatedAt"));
    }

    @Test
    public void patchUsuarioTest(){
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "Arthur Henrique Dias da Silva");
        requestParams.put("job", "Software Test Engineer");

        given()
            .body(requestParams.toString())
        .when()
            .contentType(ContentType.JSON)
            .patch("https://reqres.in/api/users/1")
        .then()
            .log().all()
        .assertThat()
            .statusCode(200)
        .and()
        .assertThat()
            .body(containsString("updatedAt"));
    }

    @Test
    public void deletarUsuarioTest(){
        given().
        when().
            delete(baseURI + "/2").
        then().
            log().all()
        .assertThat()
            .statusCode(204);
    }

}
