package com.example.magic.projectwadeeny;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by MAGIC on 11/2/2017.
 */

public class Data {

    String endingPoint;
    ArrayList<String> path;
    String startingPoint;

    public Data() {

    }

    public Data(String endingPoint, ArrayList<String> path, String startingPoint) {
        this.endingPoint = endingPoint;
        this.path = path;
        this.startingPoint = startingPoint;
    }

    public String getEndingPoint() {
        return endingPoint;
    }

    public ArrayList<String> getPath() {
        return path;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setEndingPoint(String endingPoint) {
        this.endingPoint = endingPoint;
    }

    public void setPath(ArrayList<String> path) {
        this.path = path;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }
}
