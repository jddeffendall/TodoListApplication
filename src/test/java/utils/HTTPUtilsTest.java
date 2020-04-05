package utils;

import domain.TodoItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HTTPUtilsTest {

    HTTPUtils httpUtils;
    JsonToObjectParser parser;

    @BeforeEach
    void setup() {
        httpUtils = new HTTPUtils();
        parser = new JsonToObjectParser();
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
        LocalDateTime testTime = LocalDateTime.now().plusMinutes(5);
        String result = httpUtils.addTodoItem("Write tests", testTime);
        TodoItem[] testArray = parser.JsonStringToObjects(result);
        assertEquals(testArray[0].getDescription(), "Write tests");
    }

    @Test
    void deleteTodoItem() {
    }
}