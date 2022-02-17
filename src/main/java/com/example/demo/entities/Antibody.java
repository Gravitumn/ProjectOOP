package com.example.demo.entities;

import com.example.demo.gameState.Board;
import com.example.demo.parseEngine.Factory;
import com.example.demo.parseEngine.SyntaxErrorException;
import com.example.demo.utility.Pair;

public class Antibody extends Entities{

    public Antibody(String geneticCode,Pair<Integer,Integer> location){
        super(geneticCode, location);
    }
}
