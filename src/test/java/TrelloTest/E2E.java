package TrelloTest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import utils.SetUp;
import static utils.Data.*;
import static utils.Utils.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class E2E {

    @BeforeClass
    public void setUp() {

        //Create Board
        Response boardresponse =
                SetUp.createBoard(boardName);
        boardresponse
                .then()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo(boardName));
        board_id = boardresponse.path("id");


        //Create List
        Response listresponse =
                SetUp.createList(listName);
        listresponse
                .then()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo(listName))
                .body("idBoard", equalTo(board_id));
        list_id = listresponse.path("id");


        //Create Card
        Response cardresponse =
                SetUp.createCard();
        cardresponse
                .then()
                .assertThat()
                .statusCode(200)
                .body("idList", equalTo(list_id));
        card_id = cardresponse.path("id");
    }


    @Test(priority = 1)
    public void getBoard() {
        Response response =
                given()
                        .baseUri(boardsUrl)
                        .queryParam("key",key)
                        .queryParam("token", token)
                        .when().contentType(ContentType.JSON)
                        .get("/" + board_id);
        response
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(board_id))
                .body("name", equalTo(boardName));
    }

    @Test(priority = 2)
    public void updateBoard() {
        Response response =
                given()
                        .baseUri(boardsUrl)
                        .queryParam("key", key)
                        .queryParam("token",token)
                        .queryParam("name", updatedBoardName)
                        .when()
                        .contentType(ContentType.JSON)
                        .put("/" + board_id);
        response
                .then()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo(updatedBoardName))
                .body("id", equalTo(board_id));
    }

    @Test(priority = 2)
    public void updateList() {
        Response response =
                given()
                        .baseUri(listsUrl)
                        .queryParam("key", key)
                        .queryParam("token",token)
                        .queryParam("name", updatedListName)
                        .when()
                        .contentType(ContentType.JSON)
                        .put("/" + list_id);
        response
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(list_id))
                .body("name", equalTo(updatedListName));
    }

    @Test(priority = 3)
    public void updateCard() {
        Response response =
                given()
                        .baseUri(cardsUrl)
                        .queryParam("key", key)
                        .queryParam("token", token)
                        .queryParam("name", cardName)
                        .when().contentType(ContentType.JSON)
                        .put("/" + card_id);
        response
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(card_id));
    }

    @Test(priority = 4)
    public void getList() {
        Response response =
                given()
                        .baseUri(listsUrl)
                        .queryParam("key",key)
                        .queryParam("token", token)
                        .when()
                        .contentType(ContentType.JSON)
                        .get("/" + list_id);
        response
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(list_id))
                .body("name", equalTo(updatedListName));
    }

    @Test(priority = 5)
    public void getCard() {
        Response response =
                given()
                        .baseUri(cardsUrl)
                        .queryParam("key", key)
                        .queryParam("token", token)
                        .when()
                        .contentType(ContentType.JSON)
                        .get("/" + card_id);
        response
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(card_id));
    }



    @AfterClass
    public void tearDownSetUp() {
        //Delete Card
        Response cardresponse = SetUp.deleteCard(card_id);
        cardresponse.then()
                .assertThat()
                .statusCode(200);

        //Delete Board
        Response boardresponse = SetUp.deleteBoard(board_id);
        boardresponse
                .then()
                .assertThat()
                .statusCode(200)
                .body("_value", equalTo(null));
    }
}