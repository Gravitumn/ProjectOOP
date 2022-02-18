package com.example.demo.entities;

import com.example.demo.gameState.Board;
import com.example.demo.parseEngine.Factory;
import com.example.demo.parseEngine.SyntaxErrorException;
import com.example.demo.utility.Increment;
import com.example.demo.utility.Pair;

import java.util.ArrayList;
import java.util.Collections;

public class Virus extends Entities {

    private int lifeGain;

    public Virus(String geneticCode,Pair<Integer,Integer> location){
        super(geneticCode,location);
    }

    @Override
    public boolean Attack(int direction) throws SyntaxErrorException {
        int distance = nearbyEntity(direction);
        int startX = this.location.fst();
        int startY = this.location.snd();
        if(distance!=0) {
            int xIncrement = factory.newIncrement(direction).xIncrement();
            int yIncrement = factory.newIncrement(direction).yIncrement();
            int targetX = startX + (distance * xIncrement);
            int targetY = startY + (distance * yIncrement);

            Entity target = Board.getEntity(targetX,targetY);
            if(target.attacked(this) && target instanceof Antibody){  //if virus killed target
                turnVirus((Antibody) target);
            }
            return true;    //return true when attack hits
        }
        return false;   //return false when no target found.
    }

    public void setLifeGain(int LifeGain){
        this.lifeGain = LifeGain;
    }

    private void turnVirus(Antibody a){
        Board.delete(a);
        new Virus(geneticCode,a.getLocation());
        a = null;
    }
}
