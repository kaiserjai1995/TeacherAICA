package edu.its.solveexponents.teacheraica.model;

import java.util.ArrayList;

/**
 * Created by jairus on 8/7/16.
 */

public class Problem {
    private int problemID;
    private String problem;
    private String date;
    private String status;
    private String time_created;
    private String time_stopped;

    private ArrayList<String> solution;

    public Problem() {
        solution = new ArrayList<>();
    }

    public void setproblemID(int problemID) {
        this.problemID = problemID;
    }

    public int getProblemID() {
        return this.problemID;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getProblem() {
        return this.problem;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    public void setSolution(ArrayList<String> solution) {
        for (String step : solution) {
            this.solution.add(step);
        }
    }

    public ArrayList<String> getSolution() {
        return this.solution;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime_created() {
        return time_created;
    }

    public void setTime_created(String time_created) {
        this.time_created = time_created;
    }

    public String getTime_stopped() {
        return time_stopped;
    }

    public void setTime_stopped(String time_stopped) {
        this.time_stopped = time_stopped;
    }

}
