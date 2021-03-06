package UI;

import domain.TodoItem;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import utils.HTTPUtils;
import utils.JsonToObjectParser;
import utils.UIUtils;

import javax.swing.*;
import java.io.IOException;

import java.util.Map;

public class chartUI extends JFrame {

    public chartUI(String title) throws IOException {
        super(title);

        PieDataset data = getPieData();

        UIUtils uiUtils = new UIUtils();
        JsonToObjectParser parser = new JsonToObjectParser();
        HTTPUtils httpUtils = new HTTPUtils();

        String rawData = httpUtils.getAllUserTodosJsonString();
        TodoItem[] items = parser.JsonStringToObjectArray(rawData);

        JFreeChart chart = ChartFactory.createPieChart3D(
                "Total Items: " + items.length,
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

    private PieDataset getPieData() {
        String rawData;

        HTTPUtils httpUtils = new HTTPUtils();
        UIUtils uiUtils = new UIUtils();
        JsonToObjectParser parser = new JsonToObjectParser();

        try {
            rawData = httpUtils.getAllUserTodosJsonString();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Couldn't get data!");
            return new DefaultPieDataset();
        }

        TodoItem[] items = parser.JsonStringToObjectArray(rawData);
        Map<String, Integer> countedItems = uiUtils.calculateDataForPieChart(items);

        return uiUtils.convertDataToPieDataset(countedItems);
    }

}
