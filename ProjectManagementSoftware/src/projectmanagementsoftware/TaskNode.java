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
        if (!taskString.matches(".*(.*,.*,.*,.*,.*\\{.*\\}.*,.*\\[.*,.*\\].*,.*\\[.*,.*\\].*,.*)")) {
            System.out.println("Unable to create a node using the string: " + taskString);
        } else {
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
            String predecessorString = nodeComponents[4].substring(1, predecessorListEndIndex);
            //dismantling the predecessor list
            String[] uncheckedPredecessorStrings = predecessorString.split(",");
            //removes the unecessary whitespace
            ArrayList<String> predecessorStrings = new ArrayList<>();
            for (String predecessor : uncheckedPredecessorStrings) {
                predecessorStrings.add(predecessor.trim());
            }
            taskPredecessors = new ArrayList<>();
            taskPredecessors.addAll(predecessorStrings);
            for (int i = 0; i < taskPredecessors.size(); i++) {
                if (!taskPredecessors.get(i).matches("\\d?+(\\.\\d+)*")) {
                    taskPredecessors.remove(i);
                    i--;
                }
            }
            //removing the predecessor list from the string
            nodeComponents[4] = nodeComponents[4].substring(predecessorListEndIndex + 1);
            predecessorListEndIndex = nodeComponents[4].indexOf(",") + 1; //to remove the comma
            nodeComponents[4] = nodeComponents[4].substring(predecessorListEndIndex);

            //setting the components list to purely the graph coords
            //wbt coordinates 
            nodeComponents = nodeComponents[4].split("]", 2);
            String wbtCoordsString = nodeComponents[0].substring(nodeComponents[0].indexOf("[") + 1);
            String[] wbtCoordsStrings = wbtCoordsString.split(",");
            wbtCoordsStrings[0] = wbtCoordsStrings[0].trim();
            wbtCoordsStrings[1] = wbtCoordsStrings[1].trim();
            wbtCoOrds = new int[]{Integer.parseInt(wbtCoordsStrings[0]), Integer.parseInt(wbtCoordsStrings[1])};
            //pert coordinates
            nodeComponents = nodeComponents[1].split("]", 2);
            String pertCoordsString = nodeComponents[0].substring(nodeComponents[0].indexOf("[") + 1);
            String[] pertCoordsStrings = pertCoordsString.split(",");
            pertCoordsStrings[0] = wbtCoordsStrings[0].trim();
            pertCoordsStrings[1] = wbtCoordsStrings[1].trim();
            pertCoOrds = new int[]{Integer.parseInt(pertCoordsStrings[0]), Integer.parseInt(pertCoordsStrings[1])};
            nodeComponents[1] = nodeComponents[1].replaceAll("\\D", "");
            ganttPosition = Integer.parseInt(nodeComponents[1]);
        }
        
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
    
    public Date getStartDate() {
        return startDate;
    }
    
    public Date getEndDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.DATE, taskDuration);
        Date endDate = cal.getTime();
        return endDate;
    }
    
    public String getStartDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        return sdf.format(startDate);
    }
    
    public String getEndDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        return sdf.format(getEndDate());
    }
    
    public String toOutputString() {
        String outputString = "(";
        outputString += taskID + ",";
        outputString += taskTitle + ",";
        //date related        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        outputString += sdf.format(startDate) + ",";
        outputString += taskDuration + ",";
        //covert the list of predecessors into a string
        outputString += "{";
        for (int i = 0; i < taskPredecessors.size(); i++) {
            outputString += taskPredecessors.get(i) + ",";
        }
        if (taskPredecessors.size() > 0) {
            outputString = outputString.substring(0, outputString.length() - 1);
        }
        outputString += "},";
        outputString += Arrays.toString(wbtCoOrds) + ",";
        outputString += Arrays.toString(pertCoOrds) + ",";
        outputString += ganttPosition;
        outputString += ")";
        return outputString;
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
