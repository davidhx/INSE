/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagementsoftware;

import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 *
 * @author up726086
 */
public class TaskNode {

    //Text Related variables
    private String taskID, taskTitle;
    private ArrayList<String> taskPredecessors;
    //Date calculation variables
    private Date startDate;  //end date is unecessary 
    private int taskDuration;

    //graph positions
    private int[] wbtCoOrds, pertCoOrds;
    private int ganttPosition;

    public TaskNode() {
        taskID = taskTitle = "";
        taskPredecessors = new ArrayList<>();
        startDate = new Date();
        taskDuration = 0;
        wbtCoOrds = new int[2];
        pertCoOrds = new int[2];
        ganttPosition = 0;
    }

    public TaskNode(String taskString) {

        //Splitting the task string up to the point of the lists
        String[] nodeComponents = taskString.split(",", 5);

        //getting the taskID
        taskID = nodeComponents[0].trim();

        //getting the title
        taskTitle = nodeComponents[1].trim();

        //getting the date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            startDate = sdf.parse(nodeComponents[2]);
        } catch (Exception e) {
            startDate = null;
        }

        //get the task duration
        taskDuration = Integer.parseInt(nodeComponents[3]);

        //getting the predecessor list
        int predecessorListEndIndex = nodeComponents[4].indexOf("}");
        String predecessorString = nodeComponents[4].substring(1,predecessorListEndIndex);
        //dismantling the predecessor list
        String[] predecessorStrings = predecessorString.split(",");
        taskPredecessors = new ArrayList<>();
        taskPredecessors.addAll(Arrays.asList(predecessorStrings));

        for (int i=0; i<taskPredecessors.size();i++) {
            if (!taskPredecessors.get(i).matches("\\d+(\\.\\d+)*")) {
                taskPredecessors.remove(i);
                i--;
            }
        }
        //removing the predecessor lsit from the string
        nodeComponents[4] = nodeComponents[4].substring(predecessorListEndIndex + 1);
        predecessorListEndIndex = nodeComponents[4].indexOf(",") + 1; //to remove the comma
        nodeComponents[4] = nodeComponents[4].substring(predecessorListEndIndex);
        
        
        //setting the components list to purely the graph coords
        nodeComponents = nodeComponents[4].split("]", 2);
        String wbtCoordsString = nodeComponents[0].substring(nodeComponents[0].indexOf("[")+1);
        String[] wbtCoordsStrings = wbtCoordsString.split(",");
        wbtCoOrds = new int[] {Integer.parseInt(wbtCoordsStrings[0]),Integer.parseInt(wbtCoordsStrings[1])} ;                 
        System.out.println(Arrays.toString(wbtCoOrds));  //debugging
        nodeComponents = nodeComponents[1].split("]", 2);
        String pertCoordsString = nodeComponents[0].substring(nodeComponents[0].indexOf("[")+1);
        String[] pertCoordsStrings = pertCoordsString.split(",");
        pertCoOrds = new int[] {Integer.parseInt(pertCoordsStrings[0]),Integer.parseInt(pertCoordsStrings[1])} ;                 
        System.out.println(Arrays.toString(pertCoOrds));  //debugging
        nodeComponents[1] = nodeComponents[1].replaceAll("\\D", "");
        ganttPosition=Integer.parseInt(nodeComponents[1]);
        System.out.println(ganttPosition);  //debugging

    }

    public TaskNode(String newTaskID, String newTaskTitle, Date newStartDate, int newTaskDuration,
            ArrayList<String> newTaskPredecessors,
            int[] newWbtCoords, int[] newPertCoords, int newGanttPosition) {
        taskID = newTaskID;
        taskTitle = newTaskTitle;
        startDate = newStartDate;
        taskDuration = newTaskDuration;
        taskPredecessors = newTaskPredecessors;
        wbtCoOrds = newWbtCoords;
        pertCoOrds = newPertCoords;
        ganttPosition = newGanttPosition;
    }

    //accessor methods
    public String getTaskId() {
        return taskID;
    }

    public String getParentId() {
        int lastFullStopIndex = taskID.lastIndexOf(".");
        if (lastFullStopIndex == -1) {
            return "0";
        }
        return taskID.substring(0, lastFullStopIndex);
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public ArrayList<String> getTaskPredecessors() {
        return taskPredecessors;
    }

    public String getStartDate() {
        return startDate.toString();
    }

    public String getEndDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.DATE, taskDuration);
        Date endDate = cal.getTime();
        return endDate.toString();
    }

    //
    public void setTaskId(String newTaskID) {
        taskID = newTaskID;
    }

    public void setTaskTitle(String newTaskTitle) {
        taskTitle = newTaskTitle;
    }

    public void setTaskPredecessors(ArrayList<String> newTaskPredecessors) {
        taskPredecessors = newTaskPredecessors;
    }
}
