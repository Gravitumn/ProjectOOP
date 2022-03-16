package com.example.demo.gameState;

import com.example.demo.entities.Antibody;
import com.example.demo.entities.Entity;
import com.example.demo.entities.Virus;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GameState {
    private static Map<Integer,GameState> instances = new HashMap<>();

    protected float gameSpeed;
    protected int credits;
    protected Set<String> AntibodyShop;
    protected Set<String> VirusShop;
    protected Set<Entity> queue = new HashSet<>();
    protected Set<Antibody> antibodyList = new HashSet<>();
    protected Set<Virus> virusList = new HashSet<>();

    private GameState(int u){
        try{
            File myObj = new File("src/main/java/com/example/demo/antibody1.txt");
            Scanner myReader = new Scanner(myObj);
            String data="";
            while (myReader.hasNextLine()) {
                data = data+" "+myReader.nextLine();
            }
            AntibodyShop.add(data);
            myObj = new File("src/main/java/com/example/demo/antibody2.txt");
            myReader = new Scanner(myObj);
            data="";
            while (myReader.hasNextLine()) {
                data = data+" "+myReader.nextLine();
            }
            AntibodyShop.add(data);
            myObj = new File("src/main/java/com/example/demo/antibody3.txt");
            myReader = new Scanner(myObj);
            data="";
            while (myReader.hasNextLine()) {
                data = data+" "+myReader.nextLine();
            }
            AntibodyShop.add(data);

            myObj = new File("src/main/java/com/example/demo/virus1.txt");
            myReader = new Scanner(myObj);
            data="";
            while (myReader.hasNextLine()) {
                data = data+" "+myReader.nextLine();
            }
            VirusShop.add(data);
            myObj = new File("src/main/java/com/example/demo/virus2.txt");
            myReader = new Scanner(myObj);
            data="";
            while (myReader.hasNextLine()) {
                data = data+" "+myReader.nextLine();
            }
            VirusShop.add(data);
            myObj = new File("src/main/java/com/example/demo/virus3.txt");
            myReader = new Scanner(myObj);
            data="";
            while (myReader.hasNextLine()) {
                data = data+" "+myReader.nextLine();
            }
            VirusShop.add(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

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

    public Set<String> getAntibodyShop() {
        return AntibodyShop;
    }

    public Set<Entity> getQueue() {
        return queue;
    }

}
