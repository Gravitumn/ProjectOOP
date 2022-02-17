package com.example.demo.gameState;

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

    protected static final String inFile = "src/main/java/com/example/demo/config.txt";

    public static void Setup(){
        try(FileReader fr = new FileReader(inFile);
            Scanner s = new Scanner(fr) ){
            System.out.println(s.nextInt());
        }catch(IOException e){e.printStackTrace();}
    }

    public static void main(String[] args){
        ConfigReader.Setup();
    }
}
