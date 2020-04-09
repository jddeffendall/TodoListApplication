package utils;

import domain.TodoItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonToObjectParserTest {

    @Test
    void jsonStringToObjects() {
        String sampleJson = "[\n" +
                "  {\n" +
                "    \"title\": \"Add todo test\",\n" +
                "    \"due\": \"Friday\",\n" +
                "    \"created\": \"Now\",\n" +
                "    \"owner\": \"TeamTwo\",\n" +
                "    \"id\": 4\n" +
                "  }\n" +
                "]";
        JsonToObjectParser parser = new JsonToObjectParser();
        TodoItem[] todos = parser.JsonStringToObjects(sampleJson);
        assertEquals(todos[0].getId(), "4");
    }
}