package com.example.demo.entities;

import com.example.demo.gameState.Board;
import com.example.demo.gameState.ConfigReader;
import com.example.demo.parseEngine.Factory;
import com.example.demo.parseEngine.SyntaxErrorException;
import com.example.demo.utility.Increment;
import com.example.demo.utility.Pair;

import java.util.ArrayList;
import java.util.Collections;

public class Virus extends Entities {

    private int lifeGain;

    public Virus(String geneticCode,Pair<Integer,Integer> location,Board board){
        super(geneticCode,location,board);
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
                Virus target = (Virus) board.getEntity(targetX, targetY);
                if(target.attacked(this)){
                    target.killed();
                }
            }
            else{
                lifeSteal();
                Antibody target = (Antibody) board.getEntity(targetX,targetY);
                if (target.attacked(this)) {  //if virus killed target
                    board.turnVirus(target,this.geneticCode);
                }
            }
            return true;    //return true when attack hits
        }
        return false;   //return false when no target found.
    }

    public void setLifeGain(int LifeGain){
        this.lifeGain = LifeGain;
    }

    private void lifeSteal(){
        this.hp += lifeGain;
        if(hp > maxHp) hp = maxHp;
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

    public void killed(){
        board.delete(this);
    }

    @Override
    public void setUpEntity(ConfigReader cf){
        this.hp = cf.getVirusHP();
        this.atk = cf.getVirusAtk();
        this.maxHp = cf.getVirusHP();
        this.lifeGain = cf.getVirusGain();
    }
}
