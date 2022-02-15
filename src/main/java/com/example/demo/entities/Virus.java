package com.example.demo.entities;

import com.example.demo.utility.Pair;

public class Virus implements Entity {
    private String geneticCode;
    private Pair<Integer,Integer> location;

    @Override
    public String getCode() {
        return this.geneticCode;
    }


    public Pair<Integer,Integer> getLocation(){
        return this.location;
    }


}
