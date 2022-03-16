package com.example.demo.gameState;

import com.example.demo.entities.Antibody;
import com.example.demo.entities.Entity;
import com.example.demo.entities.Virus;
import com.example.demo.parseEngine.CmdParser;
import com.example.demo.parseEngine.EvalError;
import com.example.demo.parseEngine.SyntaxErrorException;
import com.example.demo.utility.Pair;

import java.util.*;

public class GameLoop extends Thread{

    private final int universe;
    private final ConfigReader cf;
    private final Board board;
    private final GameState state;
//    boolean stop = false;

    public GameLoop(int universe){
        this.universe = universe;
        cf = new ConfigReader();
        this.state = GameState.instance(universe);
        this.board = Board.instance(universe);
    }

    public void run() {
        try {
            while(state.antibodyList.isEmpty()){
                System.out.println("Waiting for player's first antibody.");

            }
            while (!state.virusList.isEmpty() && !state.antibodyList.isEmpty()) {
                double spawnrate = cf.getSpawnRate()*100;
                int chance = new Random().nextInt(100)+1;
                if(spawnrate >= chance){
                    Set<Pair<Integer,Integer>> availableSpace = board.getAvailableSpace();
                    int rand = new Random().nextInt(availableSpace.size());
                    Pair<Integer,Integer> randLoc = new ArrayList<>(availableSpace).get(rand);
                    new Virus("",randLoc,universe);
                }

                for (Entity entity : state.queue) {
                    CmdParser parser = new CmdParser(entity);
                    parser.parseProgram();
                    Thread.sleep((long) state.gameSpeed*100);
                }
            }
        }
        catch (SyntaxErrorException | EvalError | InterruptedException e){e.printStackTrace();}
    }

    public static void main(String[] args){
        GameLoop gl = new GameLoop(1);
        gl.start();
    }
}
