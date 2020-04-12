package utils;

import domain.TodoItem;
public class UIUtils {

    public UIUtils() {
    }

    public String[][] formatDataForTable(TodoItem[] data) {
        String[][] formattedData = new String[data.length][4];

        for (int i = 0; i < data.length; i++) {
            TodoItem tempTodo = data[i];

            formattedData[i][0] = tempTodo.getCreatedDate();
            formattedData[i][1] = tempTodo.getTitle();
            formattedData[i][2] = tempTodo.getDueDate();
            formattedData[i][3] = Integer.toString(tempTodo.getId());
        }
        return formattedData;
    }
}
