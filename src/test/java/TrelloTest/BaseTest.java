package TrelloTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.Data;
import utils.Utils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class BaseTest {
    @BeforeClass
    public void createBoard() {
        Response response =
                given()
                .queryParam("key" ,Utils.key)
                .queryParam("token" , Utils .token)
                .queryParam("name" , Data.boardName)
                .when()
                .contentType(ContentType.JSON)
                .post(Utils.boardsUrl);
        response
                .then()
                .assertThat().statusCode(200)
                .body("name",equalTo(Data.boardName));
        Data.board_id = response.path("id");
    }
    @AfterClass
    public void deleteBoard() {
        Response response =
                given().
                        baseUri(Utils.boardsUrl).
                        queryParam("key", Utils.key).
                        queryParam("token", Utils.token).
                        when().
                        contentType(ContentType.JSON).
                        delete("/"+ Data.board_id);
        response
                .then().
                assertThat().
                statusCode(200);
    }
}
