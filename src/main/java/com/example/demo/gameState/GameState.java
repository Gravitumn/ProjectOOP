package com.example.demo.gameState;

import com.example.demo.entities.Antibody;
import com.example.demo.entities.Entity;
import com.example.demo.entities.Virus;
import com.example.demo.utility.Pair;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class GameState {

    protected float gameSpeed;
    protected int credits;
    protected Set<Antibody> Shop;
    protected Set<Entity> queue = new HashSet<>();
    protected Set<Antibody> antibodyList = new HashSet<>();
    protected Set<Virus> virusList = new HashSet<>();

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
}
