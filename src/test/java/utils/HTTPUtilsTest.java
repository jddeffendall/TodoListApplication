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
        String result = httpUtils.addTodoItem("TEST", "05 25 2020 12:00");
        TodoItem resultItem = parser.JsonStringToOneObject(result);
        assertEquals("TEST", resultItem.getTitle());
        httpUtils.deleteTodoItem(resultItem.getId());
    }

    @Test
    void addTodoItem() throws IOException {
        var result = httpUtils.addTodoItem("Add todo test", "05 25 2020 12:00");
        TodoItem expected = parser.JsonStringToOneObject(result);
        assertEquals("Add todo test", expected.getTitle());
        httpUtils.deleteTodoItem(expected.getId());
    }

    @Test
    void deleteTodoItem() throws IOException {
        var result = httpUtils.addTodoItem("Delete tests", "05 25 2020 12:00");
        TodoItem item = parser.JsonStringToOneObject(result);
        var deletedResult = httpUtils.deleteTodoItem(item.getId());
        assertTrue(deletedResult);
    }


    @Test
    void deleteTodoItem_InvalidID() throws IOException {
        var deletedResult = httpUtils.deleteTodoItem("100000");
        assertFalse(deletedResult);
    }

    @Test
    void completeTodoItem() throws IOException {
        var result = httpUtils.addTodoItem("Complete item", "04 25 2020 12:00");
        TodoItem item = parser.JsonStringToOneObject(result);
        var updatedResult = httpUtils.completeTodoItem(item);
        assertTrue(updatedResult);
        httpUtils.deleteTodoItem(item.getId());
    }

    @Test
    void setOverdueTodoItem() throws IOException {
        var result = httpUtils.addTodoItem("Test", "04 17 2020 12:00");
        TodoItem item = parser.JsonStringToOneObject(result);
        var updatedResult = httpUtils.setTodoItemOverdue(item);
        var updatedJson = httpUtils.getTodoItemJsonString(item.getId());
        TodoItem updatedItem = parser.JsonStringToOneObject(updatedJson);
        assertEquals("true", updatedItem.getOverdue());
        httpUtils.deleteTodoItem(updatedItem.getId());
    }

    @Test
    void snoozeTodoItem() throws IOException {
        var result = httpUtils.addTodoItem("Test", "04 17 2020 12:00");
        TodoItem item = parser.JsonStringToOneObject(result);
        var updatedResult = httpUtils.snooze(item);
        var updatedJson = httpUtils.getTodoItemJsonString(item.getId());
        TodoItem updatedItem = parser.JsonStringToOneObject(updatedJson);
        assertNotEquals(item.getDueDate(), updatedItem.getDueDate());
        httpUtils.deleteTodoItem(updatedItem.getId());
    }


}