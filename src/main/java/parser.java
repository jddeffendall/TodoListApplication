
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class parser {


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


}
