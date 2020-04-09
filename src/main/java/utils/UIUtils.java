package utils;

import domain.TodoItem;
import org.javatuples.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UIUtils {

    public UIUtils() {
    }

    public String[][] formatDataForTable(List<TodoItem> data) {
        String[][] formattedData = null;

        for (int i = 0; i < data.size(); i++) {
            TodoItem tempTodo = data.get(i);
            formattedData[i][0] = tempTodo.getCreatedDate();
            formattedData[i][1] = tempTodo.getDescription();
            formattedData[i][2] = tempTodo.getDueDate();
        }

        return formattedData;
    }
}
