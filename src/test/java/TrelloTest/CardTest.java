package TrelloTest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.Data;
import utils.Utils;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static utils.Data.*;
import static utils.Utils.*;

public class CardTest extends BaseTest{
    @Test(priority = 1)
    public void createList(){
        Response response =
                given()
                        .queryParam("key", key)
                        .queryParam("token", token)
                        .queryParam("idBoard",board_id)
                        .queryParam("name", listName)
                .when()
                        .contentType(ContentType.JSON)
                        .post(listsUrl);
        response.then()
                .assertThat()
                .statusCode(200)
                .body("name",equalTo(listName))
                .body("idBoard",equalTo(board_id));
        list_id=response.path("id");
    }
    @Test(dependsOnMethods = "createList", priority = 1)
    public void createCard(){
        Response response =
                given()
                        .baseUri(Utils.cardsUrl)
                        .queryParam("key",key)
                        .queryParam("token", token)
                        .queryParam("idList",list_id)
                        .when()
                        .contentType(ContentType.JSON)
                        .post();
        response.then()
                .assertThat()
                .statusCode(200)
                .body("idList",equalTo(list_id));
        card_id = response.path("id");
    }


    @Test(dependsOnMethods = "createCard", priority = 1)
    public void updateCard(){
        Response response =
                given()
                        .baseUri(cardsUrl)
                        .queryParam("key",key)
                        .queryParam("token", token)
                        .queryParam("name", cardName)
                        .when()
                        .contentType(ContentType.JSON)
                        .put("/"+ card_id);
        response
                .then()
                .assertThat()
                .statusCode(200)
                .body("id",equalTo(card_id));
    }

    @Test(dependsOnMethods = "createCard", priority = 2)
    public void getCard(){
        Response response =
                given()
                        .baseUri(cardsUrl)
                        .queryParam("key", key)
                        .queryParam("token",token)
                        .when()
                        .contentType(ContentType.JSON)
                        .get("/"+ card_id);
        response
                .then()
                .assertThat()
                .statusCode(200)
                .body("id",equalTo(card_id));
    }

    @Test(priority = 3)
    public void deleteCard() {
        Response response =
                given()
                        .baseUri(cardsUrl)
                        .queryParam("key",key)
                        .queryParam("token", token)
                        .when()
                        .contentType(ContentType.JSON)
                        .delete("/"+ card_id);
        response
                .then()
                .assertThat()
                .statusCode(200);
    }
}
