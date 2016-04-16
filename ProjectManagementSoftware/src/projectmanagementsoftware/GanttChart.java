/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagementsoftware;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;

public class GanttChart {

    /**
     * Creates a new demo.
     *
     * @param title the frame title.
     */
    public static ChartPanel update(final String title, final ArrayList<TaskNode> currentProject) {

        final IntervalCategoryDataset dataset = createDataset(currentProject);
        final JFreeChart chart = createChart(title,dataset);

        final ChartPanel chartPanel = new ChartPanel(chart);

        return chartPanel;

    }

    /**
     * Creates a sample dataset for a Gantt chart.
     *
     * @return The dataset.
     */
    public static IntervalCategoryDataset createDataset(ArrayList<TaskNode> currentProject) {
        final TaskSeries s1 = new TaskSeries("Scheduled");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        currentProject.stream().forEach((task) -> {
            s1.add(new Task(task.getTaskTitle(), task.getStartDate(), task.getEndDate()));
        });

        final TaskSeriesCollection collection = new TaskSeriesCollection();
        collection.add(s1);

        return collection;
    }

    /**
     * Utility method for creating <code>Date</code> objects.
     *
     * @param day the date.
     * @param month the month.
     * @param year the year.
     *
     * @return a date.
     */
    private static Date date(final int day, final int month, final int year) {

        final Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        final Date result = calendar.getTime();
        return result;

    }

    /**
     * Creates a chart.
     *
     * @param dataset the dataset.
     *
     * @return The chart.
     */
    private static JFreeChart createChart(final String title,final IntervalCategoryDataset dataset) {
        final JFreeChart chart = ChartFactory.createGanttChart(
                title, // chart title
                "Task", // domain axis label
                "Date", // range axis label
                dataset, // data
                false, // include legend
                true, // tooltips
                false // urls
        );
//        chart.getCategoryPlot().getDomainAxis().setMaxCategoryLabelWidthRatio(10.0f);
        return chart;
    }

}
