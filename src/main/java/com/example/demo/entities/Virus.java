package com.example.demo.entities;

import com.example.demo.gameState.Board;
import com.example.demo.parseEngine.Factory;
import com.example.demo.utility.Pair;

public class Virus implements Entity {
    private Factory factory = Factory.instance();
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

    public void changeLocation(int direction){
        int xCoordinate = this.location.fst();
        int yCoordinate = this.location.snd();
        switch (direction){
            case 1 : yCoordinate++;
            case 2 : xCoordinate++;yCoordinate++;
            case 3 : xCoordinate++;
            case 4 : xCoordinate++;yCoordinate--;
            case 5 : yCoordinate--;
            case 6 : xCoordinate--;yCoordinate--;
            case 7 : xCoordinate--;
            case 8 : xCoordinate--;yCoordinate++;
        }
        Pair<Integer,Integer> newLocation = factory.newPair(xCoordinate,yCoordinate);
        if(Board.move(this,newLocation)){
            this.location = newLocation;
        }
    }
}
