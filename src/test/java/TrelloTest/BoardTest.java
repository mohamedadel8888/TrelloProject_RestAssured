package TrelloTest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.Data;
import utils.Utils;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static utils.Data.updatedBoardName;
import static utils.Utils.key;
import static utils.Utils.token;

public class BoardTest extends BaseTest{


    @Test (priority = 1)
    public void getBoard(){
        Response response =
                given()
                        .baseUri(Utils.boardsUrl)
                        .queryParam("key", key)
                        .queryParam("token", token).
                when()
                        .contentType(ContentType.JSON)
                        .get("/"+ Data.board_id);
        response.then()
                .assertThat().statusCode(200)
                .body("id",equalTo(Data.board_id))
                .body("name",equalTo(Data.boardName));
    }

    @Test(priority = 2)
    public void updateBoard(){
        Response response =
                given().
                        baseUri(Utils.boardsUrl).
                        queryParam("key", key).
                        queryParam("token", token).
                        queryParam("name", updatedBoardName).
                        when().
                        contentType(ContentType.JSON).
                        put("/"+Data.board_id);

        response.prettyPrint();

        response.then().
                assertThat().
                statusCode(200).
                body("name",equalTo(updatedBoardName)).
                body("id",equalTo(Data.board_id));
    }
}
