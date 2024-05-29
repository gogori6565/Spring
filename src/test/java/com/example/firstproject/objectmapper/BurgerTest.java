package com.example.firstproject.objectmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BurgerTest {

    @Test
    public void 자바_객체를_JSON으로_변환() throws JsonProcessingException {
        //준비
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> ingredients = Arrays.asList("통새우패티", "순쇠고기 패티", "토마토", "스파이시 어니언 소스");
        Burger burger = new Burger("맥도날드 슈비버거", 5500, ingredients);

        //수행
        String json = objectMapper.writeValueAsString(burger); //burger 객체를 넣어주면 문자열의 JSON 스트링을 반환

        //예상
        String expected = "{\"name\":\"맥도날드 슈비버거\",\"price\":5500,\"ingredients\":[\"통새우패티\",\"순쇠고기 패티\",\"토마토\",\"스파이시 어니언 소스\"]}";

        //검증
        assertEquals(expected, json);

        //JSON 이쁘게 출력시키기
        JsonNode jsonNode = objectMapper.readTree(json);
        System.out.println(jsonNode.toPrettyString());
    }

    @Test
    public void JSON을_자바_객체로_변환() throws JsonProcessingException {
        //준비
        ObjectMapper objectMapper = new ObjectMapper();

        /*
        {
            "name" : "맥도날드 슈비버거",
            "price" : 5500,
            "ingredients" : [ "통새우 패티", "순쇠고기 패티", "토마토", "스파이시 어니언 소스" ]
        }
        */
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("name", "맥도날드 슈비버거"); //json node 만들기
        objectNode.put("price", 5500);

        ArrayNode arrayNode = objectMapper.createArrayNode();
        arrayNode.add("통새우 패티");
        arrayNode.add("순쇠고기 패티");
        arrayNode.add("토마토");
        arrayNode.add("스파이시 어니언 소스");

        objectNode.set("ingredients", arrayNode);

        String json = objectNode.toString();

        //수행
        Burger burger = objectMapper.readValue(json, Burger.class); //readValue(JSON, JSON으로_만들_클래스_타입)

        //예상
        List<String> ingredients = Arrays.asList("통새우 패티", "순쇠고기 패티", "토마토", "스파이시 어니언 소스");
        Burger expected = new Burger("맥도날드 슈비버거", 5500, ingredients);

        //검증
        assertEquals(expected.toString(), burger.toString());

        //JSON 이쁘게 출력시키기
        JsonNode jsonNode = objectMapper.readTree(json);
        System.out.println(jsonNode.toPrettyString());

        System.out.println(burger.toString());
    }
}