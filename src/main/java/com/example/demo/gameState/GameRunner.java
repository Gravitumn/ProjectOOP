package com.example.demo.gameState;

import com.example.demo.API.EntityController;
import com.example.demo.utility.Pair;
import org.springframework.boot.SpringApplication;

public class GameRunner extends Thread{
    private GameState state;
    private int universe;
    private boolean stop = false;

    public GameRunner(int universe){
        this.universe = universe;
        this.state = GameState.instance(universe);
    }

    public void gameStart(String[] args){
        Thread gameLoop = new GameLoop(universe);
        gameLoop.start();
        SpringApplication.run(EntityController.class,args);
    }

    public void toggleStop(){
        if(stop)stop=false;
        stop=true;
    }
}
