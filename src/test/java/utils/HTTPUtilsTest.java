package utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HTTPUtilsTest {

    HTTPUtils httpUtils;

    @BeforeEach
    void setup() {
        httpUtils = new HTTPUtils();
    }

    @Test
    void getTodoItemJsonString() throws IOException {
        String result = httpUtils.getTodoItemJsonString(1);
        assertEquals("{\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"Explain the project\",\n" +
                "  \"owner\": \"hergin\"\n" +
                "}", result);
    }

    @Test
    void getAllUserTodosJsonString() {
    }

    @Test
    void addTodoItem() throws IOException {
        LocalDateTime testTime = LocalDateTime.now();
        httpUtils.addTodoItem("Write tests", testTime);
        JsonToObjectParser = 
    }

    @Test
    void deleteTodoItem() {
    }
}