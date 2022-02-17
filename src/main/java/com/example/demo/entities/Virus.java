package com.example.demo.entities;

import com.example.demo.gameState.Board;
import com.example.demo.parseEngine.Factory;
import com.example.demo.parseEngine.SyntaxErrorException;
import com.example.demo.utility.Increment;
import com.example.demo.utility.Pair;

import java.util.ArrayList;
import java.util.Collections;

public class Virus extends Entities {

    public int lifeGain = 5;

    public Virus(String geneticCode,Pair<Integer,Integer> location){
        super(geneticCode,location);
        this.hp += lifeGain;
    }
}
