package utils;

import domain.TodoItem;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.util.HashMap;
import java.util.Map;

public class UIUtils {

    public UIUtils() {
    }

    public String[][] formatDataForTable(TodoItem[] data) {
        String[][] formattedData = new String[data.length][7];

        for (int i = 0; i < data.length; i++) {
            TodoItem tempTodo = data[i];

            formattedData[i][0] = tempTodo.getId();
            formattedData[i][1] = tempTodo.getTitle();
            formattedData[i][2] = tempTodo.getCreatedDate();
            formattedData[i][3] = tempTodo.getDueDate();
            formattedData[i][4] = tempTodo.getCompleted();
            formattedData[i][5] = tempTodo.getOverdue();
            formattedData[i][6] = tempTodo.getCompletedDate();
        }

        return formattedData;
    }

    public Map<String, Integer> calculateDataForPieChart(TodoItem[] items) {
        Map<String, Integer> result = new HashMap<>();
        int pendingCount = 0;
        int completedCount = 0;
        int overdueCount = 0;

        for (TodoItem i : items) {
            if (i.getCompleted().equals("true")) {
                completedCount++;
            }
            else if (i.getCompleted().equals("false") && i.getOverdue().equals("true")) {
                overdueCount++;
            }
            else if (i.getCompleted().equals("false") && i.getOverdue().equals("false")) {
                pendingCount++;
            }
        }

        result.put("Completed", completedCount);
        result.put("Pending", pendingCount);
        result.put("Overdue", overdueCount);

        return result;
    }

    public PieDataset convertDataToPieDataset(Map<String, Integer> countedItems) {
        DefaultPieDataset result = new DefaultPieDataset();

        for (Map.Entry<String, Integer> e : countedItems.entrySet()) {
            result.setValue(e.getKey(), e.getValue());
        }

        return result;
    }

    public int countTotalItems(Map<String, Integer> items) {
        return items.size();
    }
}
