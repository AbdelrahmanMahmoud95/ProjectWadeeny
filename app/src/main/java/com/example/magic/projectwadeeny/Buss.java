package com.example.magic.projectwadeeny;


import java.util.ArrayList;

/**
 * Created by MAGIC on 11/29/2017.
 */

public class Buss {

    String price;
    ArrayList<String> path;

    public Buss() {

    }

    public Buss( String price, ArrayList<String> path) {
        this.price = price;
        this.path = path;
    }


    public String getPrice() {
        return price;
    }

    public ArrayList<String> getPath() {
        return path;
    }


    public void setPrice(String price) {
        this.price = price;
    }

    public void setPath(ArrayList<String> path) {
        this.path = path;
    }
}
