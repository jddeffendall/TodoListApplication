package utils;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import domain.TodoItem;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public String addTodoItem(String description, String dueDate) throws IOException {
        Map<String, Object> data = new LinkedHashMap<>();

        LocalDateTime createdDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd yyyy HH:mm a");
        String created = createdDate.format(formatter);

        data.put("title", description);
        data.put("due", dueDate);
        data.put("created", created);
        data.put("completed", false);
        data.put("overdue", false);
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

    public boolean completeTodoItem(TodoItem item) throws IOException {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("title", item.getTitle());
        data.put("due", item.getDueDate());
        data.put("created", item.getCreatedDate());
        data.put("completed", true);
        data.put("overdue", item.getOverdue());
        data.put("owner", item.getOwner());
        data.put("id", item.getId());

        HttpContent content = new UrlEncodedContent(data);
        HttpRequest putRequest = requestFactory.buildPutRequest(
                new GenericUrl(todosURL + item.getId()), content);
        try {
            String rawResponse = putRequest.execute().parseAsString();
        } catch (HttpResponseException e) {
            return false;
        }
        return true;
    }
}
