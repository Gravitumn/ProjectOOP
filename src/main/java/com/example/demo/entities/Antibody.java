package com.example.demo.entities;

import com.example.demo.gameState.Board;
import com.example.demo.parseEngine.Factory;
import com.example.demo.parseEngine.SyntaxErrorException;
import com.example.demo.utility.Pair;

public class Antibody extends Entities{
    private Factory factory = Factory.instance();
    private String geneticCode;
    private Pair<Integer,Integer> location;

    public Antibody(Pair<Integer,Integer> location){
        this.location = location;
    }
}
