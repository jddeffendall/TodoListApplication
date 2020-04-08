package utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonToObjectParserTest {

    @Test
    void jsonStringToObjects() {
        String sampleJson = "";
        JsonToObjectParser parser = new JsonToObjectParser();
        parser.JsonStringToObjects(sampleJson);
    }
}