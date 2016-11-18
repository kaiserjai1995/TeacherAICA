package edu.its.solveexponents.teacheraica.model;

import java.util.ArrayList;

/**
 * Created by jairus on 8/7/16.
 */

public class Problem {
    private int problemID;
    private String problem;
    private String date;
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
}
