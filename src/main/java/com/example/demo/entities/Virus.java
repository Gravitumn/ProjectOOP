package com.example.demo.entities;

import com.example.demo.gameState.Board;
import com.example.demo.utility.Pair;

public class Virus implements Entity {
    private String geneticCode;
    private Pair<Integer,Integer> location;

    @Override
    public String getCode() {
        return this.geneticCode;
    }

    @Override
    public Pair<Integer,Integer> getLocation(){
        return this.location;
    }

    public void changeLocation(Pair<Integer,Integer> newLocation){
        if(Board.move(this,this.location)){
            this.location = newLocation;
        }
    }
}
