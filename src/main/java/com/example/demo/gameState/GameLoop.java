package com.example.demo.gameState;

import com.example.demo.entities.Antibody;
import com.example.demo.entities.Entity;
import com.example.demo.entities.Virus;
import com.example.demo.parseEngine.CmdParser;
import com.example.demo.parseEngine.EvalError;
import com.example.demo.parseEngine.SyntaxErrorException;
import com.example.demo.utility.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameLoop {

    ConfigReader cf;
    Board board;
    GameState state;

    public GameLoop(GameState state){
        cf = new ConfigReader();
        Pair<Integer,Integer> size = cf.getCoordinate();
        this.state = state;
        this.board = new Board(size.fst(), size.snd(), state);
    }

    public void gameStart() throws SyntaxErrorException, EvalError {
        /*while(!virusList.isEmpty() && !antibodyList.isEmpty() ){
            for (Entity entity : queue) {
                CmdParser parser = new CmdParser(entity);
                parser.parseProgram();
            }
        }*/
    }
}
