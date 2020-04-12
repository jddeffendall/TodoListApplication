package utils;

import com.google.gson.*;
import domain.TodoItem;
import org.javatuples.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonToObjectParser {

    public TodoItem[] JsonStringToObjectArray(String userJson) {
        Gson gson = new Gson();
        TodoItem[] todoArray = gson.fromJson(userJson, TodoItem[].class);
        return todoArray;
    }

    public TodoItem JsonStringToOneObject(String userJson) {
        Gson gson = new Gson();
        TodoItem todoItem = gson.fromJson(userJson, TodoItem.class);
        return todoItem;
    }

    public List<Pair<String, String>> extractData(String jsonString) {
        JsonParser jsonParser = new JsonParser();
        JsonElement rootElement = jsonParser.parse(jsonString);
        JsonObject rootObject = rootElement.getAsJsonObject();

        List<Pair<String, String>> resultList = new ArrayList<>();

        for (Map.Entry<String, JsonElement> e : rootObject.entrySet()) {
            resultList.add(new Pair<String, String>(e.getKey(), e.getValue().toString()));
        }
        return resultList;
    }
}
