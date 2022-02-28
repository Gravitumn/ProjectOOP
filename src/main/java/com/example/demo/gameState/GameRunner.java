package com.example.demo.gameState;

import com.example.demo.utility.Pair;

public class GameRunner extends Thread{
    GameState state;
    boolean stop = false;

    public GameRunner(GameState state){
        this.state = state;
    }

    public void gameStart(){
        Thread gameLoop = new GameLoop(state);
    }

    public void toggleStop(){
        if(stop)stop=false;
        stop=true;
    }
}
