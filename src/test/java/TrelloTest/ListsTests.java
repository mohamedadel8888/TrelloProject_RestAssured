package TrelloTest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.Utils;
import utils.Data;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static utils.Data.*;
import static utils.Utils.*;

public class ListsTests extends BaseTest {

    @Test (priority = 1)
    public void createList(){
        Response response =
                given()
                        .queryParam("key", key)
                        .queryParam("token", token)
                        .queryParam("idBoard",board_id)
                        .queryParam("name",listName)
                        .when()
                        .contentType(ContentType.JSON)
                        .post(listsUrl);
        response
                .then()
                .assertThat()
                .statusCode(200)
                .body("name",equalTo(listName))
                .body("idBoard",equalTo(board_id));
        list_id = response.path("id");
    }

    @Test (dependsOnMethods = "createList",priority = 1)
    public void updateList(){
        Response response =
                given()
                        .baseUri(listsUrl)
                        .queryParam("key", key)
                        .queryParam("token", token)
                        .queryParam("name", updatedListName)
                        .when()
                        .contentType(ContentType.JSON)
                        .put("/"+ list_id);
        response
                .then()
                .assertThat()
                .statusCode(200)
                .body("id",equalTo(list_id))
                .body("name",equalTo(updatedListName));
    }

    @Test(dependsOnMethods = "createList", priority = 2)
    public void getList(){
        Response response =
                given()
                        .baseUri(listsUrl)
                        .queryParam("key", key)
                        .queryParam("token", token)
                        .when()
                        .contentType(ContentType.JSON)
                        .get("/"+list_id);
        response
                .then()
                .assertThat()
                .statusCode(200)
                .body("id",equalTo(list_id))
                .body("name",equalTo(updatedListName));
    }

    @Test(dependsOnMethods = "createList",priority = 3)
    public void archiveList(){
        Response response =
                given()
                        .baseUri(listsUrl)
                        .queryParam("key", key)
                        .queryParam("token", token)
                        .queryParam("value",true)
                        .when()
                        .contentType(ContentType.JSON)
                        .put("/"+list_id+"/closed");
        response
                .then()
                .assertThat()
                .statusCode(200)
                .body("id",equalTo(list_id))
                .body("closed",equalTo(true));
    }

}