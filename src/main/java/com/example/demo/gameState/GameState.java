package com.example.demo.gameState;

import com.example.demo.entities.Antibody;
import com.example.demo.entities.Entity;
import com.example.demo.entities.Virus;
import com.example.demo.utility.Pair;

import java.util.*;

public class GameState {

    private static Map<Integer,GameState> instances = new HashMap<>();

    protected float gameSpeed;
    protected int credits;
    protected Set<Antibody> Shop;
    protected Set<Entity> queue = new HashSet<>();
    protected Set<Antibody> antibodyList = new HashSet<>();
    protected Set<Virus> virusList = new HashSet<>();

    private GameState(int u){}

    public static GameState instance(int u){
        if(!instances.containsKey(u)){
            instances.put(u,new GameState(u));
        }
        return instances.get(u);
    }

    public Set<Virus> getVirusList() {
        return virusList;
    }

    public Set<Antibody> getAntibodyList() {
        return antibodyList;
    }

    public void setGameSpeed(float gameSpeed) {
            this.gameSpeed = gameSpeed;
    }

    public float getGameSpeed() {
        return gameSpeed;
    }

    public int getCredits() {
        return credits;
    }

    public Set<Antibody> getShop() {
        return Shop;
    }

    public Set<Entity> getQueue() {
        return queue;
    }

}
