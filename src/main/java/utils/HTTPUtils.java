package utils;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class HTTPUtils {

    HttpRequestFactory requestFactory;
    String baseURL = "https://todoserver222.herokuapp.com/";
    String todosURL = baseURL + "todos/";
    String teamURL = "https://todoserver222.herokuapp.com/TeamTwo/todos";

    public HTTPUtils() {
        requestFactory = new NetHttpTransport().createRequestFactory();
    }

    public String getTodoItemJsonString(int id) throws IOException {
        HttpRequest getRequest = requestFactory.buildGetRequest(
                new GenericUrl(todosURL + id));
        String rawResponse = getRequest.execute().parseAsString();
        return rawResponse;
    }

    public String getAllUserTodosJsonString() throws IOException {
        HttpRequest getRequest = requestFactory.buildGetRequest(
                new GenericUrl(teamURL));
        String rawResponse = getRequest.execute().parseAsString();
        return rawResponse;
    }

    public String addTodoItem(String description, String dueDate, String created) throws IOException {
        Map<String, Object> data = new LinkedHashMap<>();

        data.put("title", description);
        data.put("due", dueDate);
        data.put("created", created);
        data.put("owner", "TeamTwo");

        HttpContent content = new UrlEncodedContent(data);
        HttpRequest postRequest = requestFactory.buildPostRequest(
                new GenericUrl(todosURL),content);
        String rawResponse = postRequest.execute().parseAsString();
        return rawResponse;
    }

    public boolean deleteTodoItem(int id) throws IOException {
        try {
            HttpRequest deleteRequest = requestFactory.buildDeleteRequest(
                    new GenericUrl(todosURL + id));
            String rawResponse = deleteRequest.execute().parseAsString();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
