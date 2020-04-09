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

    public String[][] formatDataForTable(TodoItem[] data) {
        String[][] formattedData = null;

        for (int i = 0; i < data.length; i++) {
            TodoItem tempTodo = data[i];
            formattedData[i][0] = tempTodo.getCreatedDate();
            formattedData[i][1] = tempTodo.getTitle();
            formattedData[i][2] = tempTodo.getDueDate();
        }

        return formattedData;
    }

    //created, desc, due
}
