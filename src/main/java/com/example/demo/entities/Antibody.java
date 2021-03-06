package com.example.demo.entities;

import com.example.demo.gameState.ConfigReader;
import com.example.demo.parseEngine.SyntaxErrorException;
import com.example.demo.utility.Pair;

public class Antibody extends Entities{
    private int killGain;
    private int moveCost;
    private int cost;
    private int creditGain;

    public Antibody(String geneticCode, Pair<Integer,Integer> location, int board){
        super(geneticCode, location,board);
    }

    @Override
    public boolean Attack(int direction) throws SyntaxErrorException {
        int distance = nearbyEntity(direction);
        int type = distance % 10;
        distance = distance / 10;
        int startX = this.location.getfst();
        int startY = this.location.getsnd();
        if(distance!=0) {
            int xIncrement = factory.newIncrement(direction).xIncrement();
            int yIncrement = factory.newIncrement(direction).yIncrement();
            int targetX = startX + (distance * xIncrement);
            int targetY = startY + (distance * yIncrement);

            if(type == 1) {
                Virus target = (Virus) board.getEntity(targetX, targetY);
                if(target.attacked(this)){
                    this.hp += killGain;
                    if(hp > maxHp) hp = maxHp;
                    board.delete(target);
                }
            }
            else{
                Antibody target = (Antibody) board.getEntity(targetX,targetY);
                if(target.attacked(this)){
                    board.delete(target);
                }
            }
            return true;
        }
        return false;   //return false when no target found.
    }

    public boolean forceMove(Pair<Integer,Integer> newlocation){
        if(this.hp - this.moveCost > 0){
            if(board.move(this,newlocation)){
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

    public int getCreditGain(){return creditGain;}

    @Override
    public void setUpEntity(ConfigReader cf) {
        this.hp = cf.getAntibodyHP();
        this.atk = cf.getAntiAtk();
        this.moveCost = cf.getMoveCost();
        this.maxHp = cf.getAntibodyHP();
        this.cost = cf.getCost();
        this.killGain = cf.getAntiGain();
        this.creditGain = cf.getCreditGain();
    }
}
