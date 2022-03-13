package com.example.demo.gameState;

import com.example.demo.utility.Pair;

public class GameRunner extends Thread{
    private GameState state;
    private int universe;
    private boolean stop = false;

    public GameRunner(int universe){
        this.universe = universe;
        this.state = GameState.instance(universe);
    }

    public void gameStart(){
        Thread gameLoop = new GameLoop(universe);
        gameLoop.start();
    }

    public void toggleStop(){
        if(stop)stop=false;
        stop=true;
    }
}
