package com.example.demo.gameState;

import com.example.demo.parseEngine.Factory;
import com.example.demo.utility.Pair;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ConfigReader {

    static int x,y;
    static int spawnRate;
    static int Credit,Cost;
    static int virusHP,antibodyHP;
    static int virusAtk,virusGain;
    static int antiAtk,antiGain;
    static int moveCost;
    static Factory factory=Factory.instance();

    protected static final String inFile = "src/main/java/com/example/demo/config.txt";

    public static void Setup(){
        try(FileReader fr = new FileReader(inFile);
            Scanner s = new Scanner(fr) ){
            System.out.println(s.nextInt());
            x=s.nextInt();
            y=s.nextInt();
            spawnRate=s.nextInt();
            Credit=s.nextInt();
            Cost=s.nextInt();
            virusHP=s.nextInt();
            antibodyHP=s.nextInt();
            virusAtk=s.nextInt();
            virusGain=s.nextInt();
            antiAtk=s.nextInt();
            antiGain=s.nextInt();
            moveCost=s.nextInt();

        }catch(IOException e){e.printStackTrace();}
    }

    public static void main(String[] args){
        ConfigReader.Setup();
    }

    public static Pair<Integer, Integer> getCoordinate() {
        return factory.newPair(x,y);
    }

    public static int getSpawnRate() {
        return spawnRate;
    }

    public static int getCredit() {
        return Credit;
    }

    public static int getCost() {
        return Cost;
    }

    public static int getVirusHP() {
        return virusHP;
    }

    public static int getAntibodyHP() {
        return antibodyHP;
    }

    public static int getVirusAtk() {
        return virusAtk;
    }

    public static int getVirusGain() {
        return virusGain;
    }

    public static int getAntiAtk() {
        return antiAtk;
    }

    public static int getAntiGain() {
        return antiGain;
    }

    public static int getMoveCost() {
        return moveCost; //ez
    }
}

