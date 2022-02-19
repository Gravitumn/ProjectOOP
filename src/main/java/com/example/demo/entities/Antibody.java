package com.example.demo.entities;

import com.example.demo.gameState.Board;
import com.example.demo.parseEngine.Factory;
import com.example.demo.parseEngine.SyntaxErrorException;
import com.example.demo.utility.Pair;

public class Antibody extends Entities{

    private static int maxHp;
    private static int atk;
    private static int killGain;
    private static int moveCost;
    private static int cost;

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
                }
            }
            else{
                Antibody target = (Antibody) Board.getEntity(targetX,targetY);
                target.attacked(this);
            }
            return true;
        }
        return false;   //return false when no target found.
    }

    protected void turnVirus(String geneticCode){

    }




    public int getAtk(){
        return Antibody.atk;
    }


    public void setHP(int hp) {
        maxHp = hp;
        this.hp = hp;
    }

    public void setAtk(int atk) {
        Antibody.atk = atk;
    }

    public static void setKillGain(int killGain) {
        Antibody.killGain = killGain;
    }

    public static void setCost(int cost) {
        Antibody.cost = cost;
    }

    public static void setMoveCost(int moveCost) {
        Antibody.moveCost = moveCost;
    }

    public static int getCost() {
        return cost;
    }

    public static int getMoveCost() {
        return moveCost;
    }
}
