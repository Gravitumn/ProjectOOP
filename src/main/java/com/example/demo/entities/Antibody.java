package com.example.demo.entities;

import com.example.demo.gameState.Board;
import com.example.demo.parseEngine.Factory;
import com.example.demo.parseEngine.SyntaxErrorException;
import com.example.demo.utility.Pair;

public class Antibody extends Entities{


    private int killGain;
    private int moveCost;
    private int cost;

    public Antibody(String geneticCode,Pair<Integer,Integer> location){
        super(geneticCode, location);
    }

    @Override
    public boolean Attack(int direction) throws SyntaxErrorException {
        int distance = nearbyEntity(direction);
        int type = distance % 10;
        distance = distance / 10;
        int startX = this.location.fst();
        int startY = this.location.snd();
        if(distance!=0) {
            int xIncrement = factory.newIncrement(direction).xIncrement();
            int yIncrement = factory.newIncrement(direction).yIncrement();
            int targetX = startX + (distance * xIncrement);
            int targetY = startY + (distance * yIncrement);

            if(type == 1) {
                Virus target = (Virus) Board.getEntity(targetX, targetY);
                if(target.attacked(this)){
                    this.hp += killGain;
                    if(hp > maxHp) hp = maxHp;
                    Board.delete(target);
                }
            }
            else{
                Antibody target = (Antibody) Board.getEntity(targetX,targetY);
                if(target.attacked(this)){
                    Board.delete(target);
                }
            }
            return true;
        }
        return false;   //return false when no target found.
    }

    public boolean forceMove(Pair<Integer,Integer> newlocation){
        if(this.hp - this.moveCost > 0){
            if(Board.move(this,newlocation)){
                this.hp -= moveCost;
                return true;
            }
        }
        return false;
    }




    public int getAtk(){
        return atk;
    }


    public void setHP(int hp) {
        maxHp = hp;
        this.hp = hp;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void setKillGain(int killGain) {
        this.killGain = killGain;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setMoveCost(int moveCost) {
        this.moveCost = moveCost;
    }

    public int getCost() {
        return cost;
    }

    public int getMoveCost() {
        return moveCost;
    }
}
