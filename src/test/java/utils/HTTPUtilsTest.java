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

    }

    @Test
    void addTodoItem() throws IOException {
        String dueDate = "Friday";
        String createdDate = "Now";
        var resultID = httpUtils.addTodoItem("Add todo test", dueDate, createdDate);
        var expected = "{\n" +
                "  \"title\": \"Add todo test\",\n" +
                "  \"due\": \"Friday\",\n" +
                "  \"created\": \"Now\",\n" +
                "  \"owner\": \"TeamTwo\",\n" +
                "  \"id\": " + resultID + "\n" +
                "}";
        var actual = httpUtils.getTodoItemJsonString(resultID);
        assertEquals(expected, actual);
    }

    @Test
    void deleteTodoItem() throws IOException {
        var resultID = httpUtils.addTodoItem("Delete tests", "Friday", "Tuesday");
        var deletedResult = httpUtils.deleteTodoItem(resultID);
        assertTrue(deletedResult);
    }
}