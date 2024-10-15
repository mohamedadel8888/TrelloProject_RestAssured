package utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static utils.Data.*;
import static utils.Utils.*;

public class SetUp {
    public static Response createBoard(String boardName){
        return given()
                .queryParam("key",key)
                .queryParam("token", token)
                .queryParam("name",boardName)
                .when()
                .contentType(ContentType.JSON)
                .post(boardsUrl);
    }

    public static Response createList(String listName){

        return given()
                .queryParam("key", key)
                .queryParam("token", token)
                .queryParam("idBoard", board_id)
                .queryParam("name",listName)
                .when()
                .contentType(ContentType.JSON)
                .post(listsUrl);
    }

    public static Response createCard(){

        return given()
                .baseUri(cardsUrl)
                .queryParam("key", key)
                .queryParam("token", token)
                .queryParam("idList", list_id)
                .when()
                .contentType(ContentType.JSON)
                .post();
    }


    public static Response deleteBoard(String boardID)
    {
        return given()
                .baseUri(boardsUrl)
                .queryParam("key", key)
                .queryParam("token", token)
                .when()
                .contentType(ContentType.JSON)
                .delete(boardID);
    }

    public static Response deleteCard(String cardID)
    {
        return given()
                .baseUri(Utils.cardsUrl)
                .queryParam("key", key)
                .queryParam("token", token)
                .when().contentType(ContentType.JSON)
                .delete("/"+ cardID);
    }

}