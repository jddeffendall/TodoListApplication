package utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import domain.TodoItem;
import org.javatuples.Pair;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonToObjectParser {


    public JsonObject parseData(InputStream input) {
        Reader reader = new InputStreamReader(input);
        JsonElement e = JsonParser.parseReader(reader);
        JsonObject object = e.getAsJsonObject();


        if (!object.has("complete!")) {
            JsonObject query = object.getAsJsonObject("query");
            return query;
        } else {
            return null;
        }

    }

    public TodoItem[] JsonStringToObjects(String userJson) {
        Gson gson = new Gson();
        TodoItem[] todoArray = gson.fromJson(userJson, TodoItem[].class);
        return todoArray;
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
