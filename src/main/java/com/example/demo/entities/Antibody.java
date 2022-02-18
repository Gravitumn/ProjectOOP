package com.example.demo.entities;

import com.example.demo.gameState.Board;
import com.example.demo.parseEngine.Factory;
import com.example.demo.parseEngine.SyntaxErrorException;
import com.example.demo.utility.Pair;

public class Antibody extends Entities{

    private static int killGain;
    private static int moveCost;
    private static int cost;

    public Antibody(String geneticCode,Pair<Integer,Integer> location){
        super(geneticCode, location);
    }

    @Override
    public boolean Attack(int direction) throws SyntaxErrorException {
        if(super.Attack(direction)){
            this.hp += killGain;
            if(hp > maxHp)hp = maxHp;
            return true;
        }
        return false;
    }

    public static void setKillGain(int killGain) {
        Antibody.killGain = killGain;
    }

    public static void setCost(int cost) {
        Antibody.cost = cost;
    }

    public static void setMoveCost(int moveCost) {
        Antibody.moveCost = moveCost;
    }

    public static int getCost() {
        return cost;
    }

    public static int getMoveCost() {
        return moveCost;
    }
}
