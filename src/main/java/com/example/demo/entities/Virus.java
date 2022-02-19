package com.example.demo.entities;

import com.example.demo.gameState.Board;
import com.example.demo.parseEngine.Factory;
import com.example.demo.parseEngine.SyntaxErrorException;
import com.example.demo.utility.Increment;
import com.example.demo.utility.Pair;

import java.util.ArrayList;
import java.util.Collections;

public class Virus extends Entities {

    private static int atk;
    private static int maxHp;
    private int lifeGain;

    public Virus(String geneticCode,Pair<Integer,Integer> location){
        super(geneticCode,location);
    }

    @Override
    public boolean Attack(int direction) throws SyntaxErrorException {
        int distance = nearbyEntity(direction);
        int type = distance % 10;
        distance = distance / 10;
        int startX = this.location.fst();
        int startY = this.location.snd();
        if(distance!=0) {
            lifeSteal();
            int xIncrement = factory.newIncrement(direction).xIncrement();
            int yIncrement = factory.newIncrement(direction).yIncrement();
            int targetX = startX + (distance * xIncrement);
            int targetY = startY + (distance * yIncrement);

            if(type == 1) {
                Virus target = (Virus) Board.getEntity(targetX, targetY);
                target.attacked(this);
            }
            else{
                Antibody target = (Antibody) Board.getEntity(targetX,targetY);
                if (target.attacked(this)) {  //if virus killed target
                    target.turnVirus(this.geneticCode);
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
        Virus.atk = atk;
    }
}
