package com.example.demo.entities;

import com.example.demo.gameState.Board;
import com.example.demo.parseEngine.Factory;
import com.example.demo.parseEngine.SyntaxErrorException;
import com.example.demo.utility.Increment;
import com.example.demo.utility.Pair;

import java.util.ArrayList;
import java.util.Collections;

public class Virus extends Entities {
    private Factory factory = Factory.instance();
    private String geneticCode;
    private Pair<Integer,Integer> location;

    public Virus(String geneticCode,Pair<Integer,Integer> firstloc){
        this.geneticCode = geneticCode;
        this.location = firstloc;
    }

}
