package utils;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import domain.TodoItem;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class HTTPUtils {

    HttpRequestFactory requestFactory;
    String baseURL = "https://todoserver-team2.herokuapp.com/";
    String todosURL = "https://todoserver-team2.herokuapp.com/todos/";
    String teamURL = "https://todoserver-team2.herokuapp.com/Team2/todos/";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd yyyy HH:mm");

    public HTTPUtils() {
        requestFactory = new NetHttpTransport().createRequestFactory();
    }

    public String getTodoItemJsonString(String id) throws IOException {
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
        String created = createdDate.format(formatter);

        data.put("title", description);
        data.put("due", dueDate);
        data.put("created", created);
        data.put("completed", false);
        data.put("overdue", false);
        data.put("completed date", "Incomplete");
        data.put("owner", "Team2");

        HttpContent content = new UrlEncodedContent(data);
        HttpRequest postRequest = requestFactory.buildPostRequest(
                new GenericUrl(todosURL),content);
        String rawResponse = postRequest.execute().parseAsString();
        return rawResponse;
    }

    public String addTodoItem(String title, String due, String created, String completed, String overdue, String completedDate, String owner) throws IOException {
        Map<String, Object> data = new LinkedHashMap<>();

        data.put("title", title);
        data.put("due", due);
        data.put("created", created);
        data.put("completed", completed);
        data.put("overdue", overdue);
        data.put("completed date", completedDate);
        data.put("owner", owner);

        HttpContent content = new UrlEncodedContent(data);
        HttpRequest postRequest = requestFactory.buildPostRequest(
                new GenericUrl(todosURL),content);
        String rawResponse = postRequest.execute().parseAsString();
        return rawResponse;
    }

    public boolean deleteTodoItem(String id) throws IOException {
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

        LocalDateTime now = LocalDateTime.now();
        String completedDate = now.format(formatter);

        data.put("title", item.getTitle());
        data.put("due", item.getDueDate());
        data.put("created", item.getCreatedDate());
        data.put("completed", true);
        data.put("overdue", item.getOverdue());
        data.put("completed date", completedDate);
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

    public boolean setTodoItemOverdue(TodoItem item) throws IOException {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("title", item.getTitle());
        data.put("due", item.getDueDate());
        data.put("created", item.getCreatedDate());
        data.put("completed", item.getCompleted());
        data.put("overdue", true);
        data.put("completed date", item.getCompletedDate());
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

    public boolean snooze(TodoItem item) throws IOException {
        Map<String, Object> data = new LinkedHashMap<>();

        String oldDueDateString = item.getDueDate();
        LocalDateTime oldDueDate = LocalDateTime.parse(oldDueDateString, formatter);
        LocalDateTime newDueDate = oldDueDate.plusMinutes(15);
        String newDateString = newDueDate.format(formatter);

        data.put("title", item.getTitle());
        data.put("due", newDateString);
        data.put("created", item.getCreatedDate());
        data.put("completed",item.getCompleted());
        data.put("overdue",item.getOverdue());
        data.put("completed date", item.getCompletedDate());
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

    public boolean checkConnection() {
        try {
            URL url = new URL("https://todoserver-team2.herokuapp.com/");
            URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }
}