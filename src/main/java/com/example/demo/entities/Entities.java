package com.example.demo.entities;

import com.example.demo.gameState.Board;
import com.example.demo.parseEngine.Factory;
import com.example.demo.parseEngine.SyntaxErrorException;
import com.example.demo.utility.Pair;

import java.util.ArrayList;
import java.util.Collections;

public class Entities implements Entity {

    protected final int atk = 10;
    protected int hp = 100;
    private final Factory factory = Factory.instance();
    private String geneticCode;
    private Pair<Integer,Integer> location;

    public Entities(String geneticCode,Pair<Integer,Integer> location){
        if(Board.addEntity(this,location)) {
            this.geneticCode = geneticCode;
            this.location = location;
        }
    }

    @Override
    public String getCode() {
        return this.geneticCode;
    }

    @Override
    public Pair<Integer, Integer> getLocation() {
        return this.location;
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
        if(Board.move(this,newLocation)){
            this.location = newLocation;
        }
    }

    @Override
    public void Attack(int direction) throws SyntaxErrorException {
        int distance = nearbyEntity(direction);
        int startX = this.location.fst();
        int startY = this.location.snd();
        if(distance!=0) {
            int xIncrement = factory.newIncrement(direction).xIncrement();
            int yIncrement = factory.newIncrement(direction).yIncrement();
            int targetX = startX + (distance * xIncrement);
            int targetY = startY + (distance * yIncrement);

            Entity target = Board.getEntity(targetX,targetY);
            target.attacked(this);
        }
    }

    @Override
    public int nearbyEntity(int direction) throws SyntaxErrorException {
        int startX = this.location.fst();
        int startY = this.location.snd();
        Pair<Integer,Integer> boardSize = Board.size();

        int xIncrement = factory.newIncrement(direction).xIncrement();
        int yIncrement = factory.newIncrement(direction).yIncrement();

        startX += xIncrement;
        startY += yIncrement;
        while(!Board.IsVirus(startX,startY) && !Board.IsAntibody(startX,startY)){
            startX+=xIncrement;
            startY+=yIncrement;
            if(startX > boardSize.fst() || startY > boardSize.snd() || startX < 0 || startY < 0 ||
                    startX==boardSize.fst()-1 || startY== boardSize.snd()-1){
                return 0;
            }
        }
        int distance = Board.findDistance(this.location, factory.newPair(startX,startY));
        if(Board.IsVirus(startX,startY))
            return 10*distance +1;
        else
            return 10*distance+2;
    }

    @Override
    public int nearbyVirus() throws SyntaxErrorException {
        ArrayList<Integer> distance = new ArrayList<>();
        int startX = this.location.fst();
        int startY = this.location.snd();
        Pair<Integer,Integer> boardSize = Board.size();

        for(int i=1;i<=8;i++){
            int xIncrement = factory.newIncrement(i).xIncrement();
            int yIncrement = factory.newIncrement(i).yIncrement();
            while(!Board.IsVirus(startX,startY)){
                startX += xIncrement;
                startY += yIncrement;
                startX+=xIncrement;
                startY+=yIncrement;
                if(startX > boardSize.fst() || startY > boardSize.snd() || startX < 0 || startY < 0){
                    break;
                }
            }
            distance.add(10*Board.findDistance(this.location, factory.newPair(startX,startY))+1);
        }
        if(distance.isEmpty())
            return 0;
        else
            return Collections.min(distance);
    }

    @Override
    public int nearbyAntibody() throws SyntaxErrorException {
        ArrayList<Integer> distance = new ArrayList<>();
        int startX = this.location.fst();
        int startY = this.location.snd();
        Pair<Integer,Integer> boardSize = Board.size();

        for(int i=1;i<=8;i++){
            int xIncrement = factory.newIncrement(i).xIncrement();
            int yIncrement = factory.newIncrement(i).yIncrement();
            while(!Board.IsAntibody(startX,startY)){
                startX += xIncrement;
                startY += yIncrement;
                startX+=xIncrement;
                startY+=yIncrement;
                if(startX > boardSize.fst() || startY > boardSize.snd() || startX < 0 || startY < 0){
                    break;
                }
            }
            distance.add(10*Board.findDistance(this.location, factory.newPair(startX,startY))+2);
        }
        if(distance.isEmpty())
            return 0;
        else
            return Collections.min(distance);
    }

    @Override
    public int getAtk(){
        return this.atk;
    }

    @Override
    public void attacked(Entity attacker) {
        this.hp -= attacker.getAtk();
    }
}
