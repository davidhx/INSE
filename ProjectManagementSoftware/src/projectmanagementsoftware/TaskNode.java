/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagementsoftware;

import java.util.ArrayList;
//import java.util.Calendar;
//import java.text.SimpleDateFormat;
//import java.util.Date;

/**
 *
 * @author up726086
 */
public class TaskNode {

    //Text Related variables
    private String taskID, taskTitle;
    private ArrayList<String> taskPredecessors;
    //Date calculation variables
//    private String startDate;
//    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");  //end date is unecessary 
//    private Calendar cal;
    private int taskDuration;

    //graph positions
    private int[] wbtCoOrds, pertCoOrds;
    private int ganttPosition;

    private TaskNode() {
        taskID = taskTitle = "";
        taskPredecessors = new ArrayList<>();
//        startDate = "00/00/0000";
//        cal = cal.getInstance();
//        cal.set();
        taskDuration = 0;
        wbtCoOrds = new int[2];
        pertCoOrds = new int[2];
        ganttPosition = 0;
    }

    //accessor methods
    public String getTaskId() {
        return taskID;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public ArrayList<String> getTaskPredecessors() {
        return taskPredecessors;
    }

//    public String getStartDate() {
//        return startDate;
//    }
//
//    public String getEndDate() {
//
//        c.setTime(new Date());
//        c.add(Calendar.DATE, 5);
//    }
    //
    public void setTaskId(String newTaskID) {
        taskID=newTaskID;
    }

    public void setTaskTitle(String newTaskTitle) {
        taskTitle=newTaskTitle;
    }

    public void getTaskPredecessors( ArrayList<String> newTaskPredecessors) {
        taskPredecessors= newTaskPredecessors;
    }
}
