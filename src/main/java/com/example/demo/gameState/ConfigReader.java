package com.example.demo.gameState;

import com.example.demo.parseEngine.Factory;
import com.example.demo.utility.Pair;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ConfigReader {

    int x,y;
    int spawnRate;
    int Credit,Cost;
    int virusHP,antibodyHP;
    int virusAtk,virusGain;
    int antiAtk,antiGain;
    int moveCost;
    int creditGain;
    Factory factory=Factory.instance();

    protected final String inFile = "src/main/java/com/example/demo/config.txt";

    private void Setup(){
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
            creditGain=s.nextInt();

        }catch(IOException e){e.printStackTrace();}
    }

    public ConfigReader(){
        this.Setup();
    }

    public Pair<Integer, Integer> getCoordinate() {
        return factory.newPair(x,y);
    }

    public int getSpawnRate() {
        return spawnRate;
    }

    public int getCredit() {
        return Credit;
    }

    public int getCost() {
        return Cost;
    }

    public int getVirusHP() {
        return virusHP;
    }

    public int getAntibodyHP() {
        return antibodyHP;
    }

    public int getVirusAtk() {
        return virusAtk;
    }

    public int getVirusGain() {
        return virusGain;
    }

    public int getAntiAtk() {
        return antiAtk;
    }

    public int getAntiGain() {
        return antiGain;
    }

    public int getMoveCost() {
        return moveCost; //ez
    }

    public int getCreditGain(){return creditGain;}
}

