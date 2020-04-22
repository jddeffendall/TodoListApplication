import domain.TodoItem;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.PieDataset;
import utils.DatabaseUtils;
import utils.UIUtils;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class offlineChartUI extends JFrame {

    DatabaseUtils todoManager = new DatabaseUtils();

    public offlineChartUI(String title) {
        super(title);

        PieDataset data = getOfflinePieData();
        int totalCount = (todoManager.getAllItems()).size();

        JFreeChart chart = ChartFactory.createPieChart3D(
                "Total Items: " + totalCount,
                data,
                true,
                true,
                false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
        pack();

        setVisible(true);
    }


    private PieDataset getOfflinePieData() {
        UIUtils uiUtils = new UIUtils();

        List<TodoItem> itemsList = todoManager.getAllItems();
        TodoItem[] allUserTodosArray = new TodoItem[itemsList.size()];

        for (int i=0; i<itemsList.size(); i++) {
            allUserTodosArray[i] = itemsList.get(i);
        }

        Map<String, Integer> countedOfflineItems = uiUtils.calculateDataForPieChart(allUserTodosArray);
        return uiUtils.convertDataToPieDataset(countedOfflineItems);
    }

}
