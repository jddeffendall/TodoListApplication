package utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sun.tools.javac.comp.Todo;
import domain.TodoItem;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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

    public List<TodoItem> JsonStringToObjects(String userJson) {
        Gson gson = new Gson();
        Type todoListType = new TypeToken<ArrayList<TodoItem>>(){}.getType();
        List<TodoItem> todoList = gson.fromJson(userJson, todoListType);
        return todoList;
    }


}
