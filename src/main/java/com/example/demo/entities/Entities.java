package com.example.demo.entities;

import com.example.demo.gameState.Board;
import com.example.demo.parseEngine.Factory;
import com.example.demo.parseEngine.SyntaxErrorException;
import com.example.demo.utility.Pair;

import java.util.ArrayList;
import java.util.Collections;

public class Entities implements Entity {

    protected Board board;
    protected int hp;
    protected int maxHp;
    protected int atk;
    protected final Factory factory = Factory.instance();
    protected String geneticCode;
    protected Pair<Integer,Integer> location;

    public Entities(String geneticCode,Pair<Integer,Integer> location,Board board){
        if(board.addEntity(this,location)) {
            this.board = board;
            this.geneticCode = geneticCode;
            this.location = location;
        }
    }

    @Override
    public void changeLocation(int direction) throws SyntaxErrorException {
        int xCoordinate = this.location.fst();
        int yCoordinate = this.location.snd();
        int xIncrement = factory.newIncrement(direction).xIncrement();
        int yIncrement = factory.newIncrement(direction).yIncrement();

        xCoordinate+=xIncrement;
        yCoordinate+=yIncrement;

        Pair<Integer,Integer> newLocation = factory.newPair(xCoordinate,yCoordinate);
        if(board.move(this,newLocation)){
            this.location = newLocation;
        }
    }

    @Override
    public boolean Attack(int direction) throws SyntaxErrorException {
        return false;
    }

    @Override
    public int nearbyEntity(int direction) throws SyntaxErrorException {
        int startX = this.location.fst();
        int startY = this.location.snd();
        Pair<Integer,Integer> boardSize = board.size();

        int xIncrement = factory.newIncrement(direction).xIncrement();
        int yIncrement = factory.newIncrement(direction).yIncrement();

        startX += xIncrement;
        startY += yIncrement;
        while(!board.IsVirus(startX,startY) && !board.IsAntibody(startX,startY)){
            startX+=xIncrement;
            startY+=yIncrement;
            if(startX > boardSize.fst() || startY > boardSize.snd() || startX < 0 || startY < 0 ||
                    startX==boardSize.fst()-1 || startY== boardSize.snd()-1){
                return 0;
            }
        }
        int distance = board.findDistance(this.location, factory.newPair(startX,startY));
        if(board.IsVirus(startX,startY))
            return 10*distance +1;
        else
            return 10*distance+2;
    }

    @Override
    public int nearbyVirus() throws SyntaxErrorException {
        ArrayList<Integer> distance = new ArrayList<>();
        Pair<Integer,Integer> boardSize = board.size();

        for(int i=1;i<=8;i++){
            boolean stop = false;
            int startX = this.location.fst();
            int startY = this.location.snd();
            int xIncrement = factory.newIncrement(i).xIncrement();
            int yIncrement = factory.newIncrement(i).yIncrement();
            startX += xIncrement;
            startY += yIncrement;
            while(!board.IsVirus(startX,startY) && !stop){
                startX+=xIncrement;
                startY+=yIncrement;
                if(startX > boardSize.fst() || startY > boardSize.snd() || startX < 0 || startY < 0){
                    stop = true;
                }
            }
            if(!stop) {
                distance.add(10 * board.findDistance(this.location, factory.newPair(startX, startY)) + i);
            }
        }
        if(distance.isEmpty())
            return 0;
        else
            return Collections.min(distance);
    }

    @Override
    public int nearbyAntibody() throws SyntaxErrorException {
        ArrayList<Integer> distance = new ArrayList<>();
        Pair<Integer,Integer> boardSize = board.size();

        for(int i=1;i<=8;i++){
            boolean stop = false;
            int startX = this.location.fst();
            int startY = this.location.snd();
            int xIncrement = factory.newIncrement(i).xIncrement();
            int yIncrement = factory.newIncrement(i).yIncrement();
            startX += xIncrement;
            startY += yIncrement;
            while(!board.IsAntibody(startX,startY) && !stop){
                startX+=xIncrement;
                startY+=yIncrement;
                if(startX > boardSize.fst() || startY > boardSize.snd() || startX < 0 || startY < 0){
                    stop = true;
                }
            }
            if(!stop) {
                distance.add(10 * board.findDistance(this.location, factory.newPair(startX, startY)) + i);
            }
        }
        if(distance.isEmpty())
            return 0;
        else
            return Collections.min(distance);
    }

    @Override
    public boolean attacked(Virus attacker){
        this.hp -= attacker.getAtk();
        return this.hp <= 0;    //return true when hp reach 0.
    }

    @Override
    public boolean attacked(Antibody attacker){
        this.hp -= attacker.getAtk();
        return this.hp <= 0;    //return true when hp reach 0.
    }

    //------------------------------------------------ setter & getter ------------------------------------------------

    @Override
    public String getCode() {
        return this.geneticCode;
    }

    @Override
    public Pair<Integer, Integer> getLocation() {
        return this.location;
    }
}
